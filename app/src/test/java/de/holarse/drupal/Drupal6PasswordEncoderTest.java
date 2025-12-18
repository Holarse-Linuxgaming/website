package de.holarse.drupal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author comrad
 */
public class Drupal6PasswordEncoderTest {

    private PasswordEncoder pe;
    
    @BeforeEach
    public void setup() {
        pe = new Drupal6PasswordEncoder();
    }
    
    @Test
    public void testEncode() {
        final String expected = "e8636ea013e682faf61f56ce1cb1ab5c"; // geheim
        final String raw = "geheim";
        
        assertEquals(expected, pe.encode(raw));
    }

    @Test
    public void testMatches() {
        final String savedPwd = "e8636ea013e682faf61f56ce1cb1ab5c"; // geheim
        final String raw = "geheim";
        
        assertTrue(pe.matches(raw, savedPwd));
    }
    
    @Test
    public void testNotMatches() {
        final String savedPwd = "e8636ea013e682faf61f56ce1cb1ab5c"; // geheim
        final String raw = "nichtdaspasswort";
        
        assertFalse(pe.matches(raw, savedPwd));
    }    
    
}
