package de.holarse.web.services;

import de.holarse.backend.view.ScreenshotView;
import de.holarse.web.controller.commands.FileUploadForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Base64;
import org.springframework.util.DigestUtils;

/**
 * Service zum Speichern von Daten in einem S3-kompatiblem Bucket
 */
@Service
public class ObjectStorageService {

    private final static transient Logger logger = LoggerFactory.getLogger(ObjectStorageService.class);

    private final static transient Region S3_COMPATIBILITY_REGION = Region.US_EAST_1;

    @Value("${s3.accessKeyId}")
    private String accessKeyId;
    @Value("${s3.secretKeyId}")
    private String secretKeyId;

    @Value("${s3.namespace}")
    private String namespace;
    @Value("${s3.region}")
    private String region;
    @Value("${s3.bucket}")
    private String bucket;
    @Value("${s3.par_prefix}")
    private String parPrefix;

    private record FilePair(Path file, byte[] data) {}

    private S3Client _s3Client = null;

    public String writeToCloud(final Path file) throws IOException {
        final int bufferSize = 4096;
        int len = 0;
        final byte[] buffer = new byte[bufferSize];

        final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024*1024*1024); // 1MB Output Array

        try (final BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file.toFile()))) {
            while ((len = bin.read(buffer, 0, bufferSize)) != -1) {
                baos.write(buffer, 0, len);
            }
        } catch (IOException ex) {
            logger.error(String.format("Error during file (%s) read", file), ex);
            throw ex;
        }

        return writeToCloud(new FilePair(file, baos.toByteArray()));
    }

    public String writeToCloud(final FileUploadForm form) {
        return writeToCloud(new FilePair(Path.of(form.getName()), Base64.getDecoder().decode(form.getData())));
    }

    protected String getUnifiedName(final Path file, final byte[] data) {
        final String digest = DigestUtils.md5DigestAsHex(data);
        final String ext = FilenameUtils.getExtension(file.toString());
        return String.format("%s.%s", digest, ext); // Filename
    }

    protected String writeToCloud(final FilePair filePair) {
        final S3Client s3ClientInstance = getClient();
        if (s3ClientInstance == null) {
            throw new IllegalStateException("no s3 client available");
        }

        final String key = getUnifiedName(filePair.file(), filePair.data());

        // Output request
        final RequestBody body = RequestBody.fromBytes(filePair.data());
        final PutObjectRequest req = PutObjectRequest.builder().bucket(bucket).key(key).build();
        s3ClientInstance.putObject(req, body);
        logger.info("Saved in the object storage (filename: {}).", key);
        return key;        
    }


    ///n/fr6v1cmrytqp/b/holarse-test/o/
    public ScreenshotView patchUrl(final ScreenshotView ssv) {
        ssv.setData(String.format("%s/n/%s/b/%s/o/%s", parPrefix, namespace, bucket, ssv.getData()));
        return ssv;
    }

    private S3Client getClient() {
        if (this._s3Client == null) {
            final AwsCredentialsProvider credentials = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretKeyId));
            final String endpoint = String.format("https://%s.compat.objectstorage.%s.oraclecloud.com/", namespace, region);
            this._s3Client = S3Client.builder().credentialsProvider(credentials).region(S3_COMPATIBILITY_REGION).endpointOverride(URI.create(endpoint)).forcePathStyle(true).build();
        }

        return this._s3Client;
    }

}
