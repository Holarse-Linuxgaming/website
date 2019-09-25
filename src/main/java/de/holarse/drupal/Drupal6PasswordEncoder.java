package de.holarse.drupal;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Drupal6PasswordEncoder implements PasswordEncoder {

    Logger log = LoggerFactory.getLogger(Drupal6PasswordEncoder.class);
    
    @Override
    public String encode(final CharSequence rawPassword) {
        return DigestUtils.md5Hex(rawPassword.toString());
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        final String hashed = DigestUtils.md5Hex(rawPassword.toString());
        log.debug("Trying to match MD5 passwords: given '" + rawPassword + "', hashed: " + hashed + ", persistened: " + encodedPassword);
        return encodedPassword.equalsIgnoreCase(hashed);
    }
    
}
