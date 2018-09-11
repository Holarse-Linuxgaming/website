package de.holarse.drupal;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Drupal6PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(final CharSequence rawPassword) {
        return DigestUtils.md5Hex(rawPassword.toString());
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return encodedPassword.equalsIgnoreCase(DigestUtils.md5Hex(rawPassword.toString()));
    }
    
}
