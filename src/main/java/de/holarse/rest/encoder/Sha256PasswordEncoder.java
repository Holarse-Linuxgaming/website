package de.holarse.rest.encoder;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Sha256PasswordEncoder implements PasswordEncoder {

    Logger log = LoggerFactory.getLogger(Sha256PasswordEncoder.class);    
    
    @Override
    public String encode(final CharSequence rawPassword) {
        return DigestUtils.sha256Hex(rawPassword.toString());
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return encodedPassword.equalsIgnoreCase(DigestUtils.sha256Hex(rawPassword.toString()));
    }
}