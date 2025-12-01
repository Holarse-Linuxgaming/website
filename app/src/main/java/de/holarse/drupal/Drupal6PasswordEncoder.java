package de.holarse.drupal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

public class Drupal6PasswordEncoder implements PasswordEncoder {

    Logger log = LoggerFactory.getLogger(Drupal6PasswordEncoder.class);
    
    @Override
    public String encode(final CharSequence rawPassword) {
        return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        final String hashed = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
        log.debug("Trying to match MD5 passwords: given '" + rawPassword + "', hashed: " + hashed + ", persistened: " + encodedPassword);
        return encodedPassword.equalsIgnoreCase(hashed);
    }
    
}
