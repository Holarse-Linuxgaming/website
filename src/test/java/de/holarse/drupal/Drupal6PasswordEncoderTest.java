package de.holarse.drupal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author comrad
 */
public class Drupal6PasswordEncoderTest {

    private PasswordEncoder pe;
    
    @Before
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
