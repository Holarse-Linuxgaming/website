package de.holarse.services;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class YoutubeServiceTest {

    private YoutubeService service;
    
    @Before
    public void setup() {
        service = new YoutubeService();
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
    
//    http://youtu.be/AAAAAAAAA01
//http://www.youtube.com/embed/watch?feature=player_embedded&v=AAAAAAAAA02
//http://www.youtube.com/embed/watch?v=AAAAAAAAA03
//http://www.youtube.com/embed/v=AAAAAAAAA04
//http://www.youtube.com/watch?v=AAAAAAAAA06
//www.youtube.com/watch?v=AAAAAAAAA07
//www.youtu.be/AAAAAAAAA08
//youtu.be/AAAAAAAAA09
//http://www.youtube.com/watch?feature=player_embedded&v=AAAAAAAAA05
//http://www.youtube.com/watch?v=i-AAAAAAA14&feature=related
//http://www.youtube.com/attribution_link?u=/watch?v=AAAAAAAAA15&feature=share&a=9QlmP1yvjcllp0h3l0NwuA
//http://www.youtube.com/attribution_link?a=fF1CWYwxCQ4&u=/watch?v=AAAAAAAAA16&feature=em-uploademail
//http://www.youtube.com/attribution_link?a=fF1CWYwxCQ4&feature=em-uploademail&u=/watch?v=AAAAAAAAA17
//http://www.youtube.com/v/A-AAAAAAA18?fs=1&rel=0
//youtube.com/watch?v=AAAAAAAAA10
//http://www.youtube.com/watch/AAAAAAAAA11
//http://www.youtube.com/v/AAAAAAAAA12
//http://www.youtube.com/v/AAAAAAAAA13
    
}
