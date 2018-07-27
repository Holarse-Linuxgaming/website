package de.holarse.services;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class WebUtilServiceTest {

    private WebUtilService service;
    
    @Before
    public void setup() {
        service = new WebUtilService();
    }
    
    @Test
    public void testExtractYoutubeIdNull() {
        try {
            assertNull(service.extractYoutubeId(null));
        } catch (Exception e) {
            fail();
        }        
    }
    
    @Test
    public void testExtractYoutubeIdFromDirectUrl() {
        try {
            assertEquals("e4rkQATKWRs", service.extractYoutubeId("https://www.youtube.com/watch?v=e4rkQATKWRs"));
        } catch (Exception e) {
            fail();
        }
    }    
    
    @Test    
    public void testExtractYoutubeIdFromUrlWithParameters() {
        try {        
            assertEquals("e4rkQATKWRs", service.extractYoutubeId("https://www.youtube.com/watch?v=e4rkQATKWRs&feature=youtu.be&t=75"));
        } catch (Exception e) {
            fail();
        }        
    }

    @Test    
    public void testExtractYoutubeIdFromPlaylist() {
        try {        
            assertEquals("0V4MxD9R0cQ", service.extractYoutubeId("https://www.youtube.com/watch?v=0V4MxD9R0cQ&list=PLG429-Qzz7zp5s90e6VgNTDRBvA2XWW5f&index=1"));
        } catch (Exception e) {
            fail();
        }
    }    
    
    @Test    
    public void testExtractYoutubeIdFromShortenedUrl() {
        try {        
            assertEquals("0V4MxD9R0cQ", service.extractYoutubeId("https://youtu.be/0V4MxD9R0cQ"));
        } catch (Exception e) {
            fail();
        }
    }        
    
}
