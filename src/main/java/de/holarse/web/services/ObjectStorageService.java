package de.holarse.web.services;

import de.holarse.backend.db.Attachment;
import de.holarse.backend.view.AttachmentView;
import de.holarse.backend.view.ScreenshotView;
import de.holarse.web.controller.commands.FileUploadForm;
import org.apache.commons.codec.digest.DigestUtils;
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

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.Base64;

/**
 * Service zum Speichern von Daten in einem S3-kompatiblem Bucket
 */
@Service
public class ObjectStorageService {

    private final static transient Logger logger = LoggerFactory.getLogger(ObjectStorageService.class);

    @Value("${oci.s3.accessKeyId}")
    private String accessKeyId;
    @Value("${oci.s3.secretKeyId}")
    private String secretKeyId;

    @Value("${oci.s3.namespace}")
    private String namespace;
    @Value("${oci.s3.region}")
    private String region;
    @Value("${oci.s3.bucket}")
    private String bucket;
    @Value("${oci.s3.par_prefix}")
    private String parPrefix;

    private S3Client _s3Client = null;

    public String writeToCloud(final FileUploadForm form) {
        final S3Client s3ClientInstance = getClient();
        if (s3ClientInstance == null) {
            throw new IllegalStateException("no s3 client available");
        }

        // From Base64 to bytes
        final byte[] data = Base64.getDecoder().decode(form.getData());
        final RequestBody body = RequestBody.fromBytes(data);


        final String digest = DigestUtils.md5Hex(data);
        logger.trace("Converted base64 to bytes, having digest: {}", digest);
        final String ext = FilenameUtils.getExtension(form.getName());
        final String key = String.format("%s.%s", digest, ext);

        final PutObjectRequest req = PutObjectRequest.builder().bucket(bucket).key(key).build();
        s3ClientInstance.putObject(req, body);
        logger.info("Saved digest {} in the object storage (filename: {}).", digest, key);
        return key;
    }

    ///n/fr6v1cmrytqp/b/holarse-test/o/
    public ScreenshotView patchUrl(final ScreenshotView ssv) {
        ssv.setData(String.format("%s/n/%s/b/%s/o/%s", parPrefix, namespace, bucket, ssv.getData()));
        return ssv;
    }

    private byte[] fromBase64(final String data) {
        return Base64.getDecoder().decode(data);
    }

    private S3Client getClient() {
        if (this._s3Client == null) {
            final AwsCredentialsProvider credentials = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretKeyId));
            final String endpoint = String.format("https://%s.compat.objectstorage.%s.oraclecloud.com/", namespace, region);
            this._s3Client = S3Client.builder().credentialsProvider(credentials).region(Region.US_EAST_1).endpointOverride(URI.create(endpoint)).forcePathStyle(true).build();
        }

        return this._s3Client;
    }

}
