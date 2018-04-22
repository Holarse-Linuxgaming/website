package de.holarse.renderer.wiki;

import de.holarse.services.NodeService;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class WikiRendererTest {

    private WikiRenderer renderer;

    @Before
    public void setUp() {
        renderer = new WikiRenderer();
        renderer.setNodeService(new NodeService());
    }

    @Test
    public void testRendererOnNull() {
        assertEquals("", renderer.render(null));
    }

    @Test
    public void testRendererOnEmpty() {
        assertEquals("", renderer.render(""));
    }

    @Test
    public void testRendererOnBlank() {
        assertEquals("", renderer.render("           "));
    }

    @Test
    public void testHeader2() {
        assertEquals("<h2>Test</h2>", renderer.render("== Test =="));
    }

    @Test    
    public void testHeader3() {
        assertEquals("<h3>Test</h3>", renderer.render("=== Test ==="));
    }

    @Test    
    public void testHeader4() {
        assertEquals("<h4>Test</h4>", renderer.render("==== Test ===="));
    }

    @Test    
    public void testHeader5() {
        assertEquals("<h5>Test</h5>", renderer.render("===== Test ====="));
    }
    
    @Test
    public void testHeader2Trimmed() {
        assertEquals("<h2>Test</h2>", renderer.render("==Test=="));
    }

    @Test    
    public void testHeader3Trimmed() {
        assertEquals("<h3>Test</h3>", renderer.render("===Test==="));
    }

    @Test    
    public void testHeader4Trimmed() {
        assertEquals("<h4>Test</h4>", renderer.render("====Test===="));
    }

    @Test    
    public void testHeader5Trimmed() {
        assertEquals("<h5>Test</h5>", renderer.render("=====Test====="));
    }    
    
    @Test    
    public void testInternalLink() {
        assertEquals("<a href=\"/wiki/hallo_welt\">Hallo Welt</a>", renderer.render("[[Hallo Welt]]"));
    }    
    
    @Test    
    public void testTwoInternalLinksWithTestBetween() {
        assertEquals(
                    "Heute gehen wir von <a href=\"/wiki/alpha_centauri\">Alpha Centauri</a> nach <a href=\"/wiki/omega_golem\">Omega: Golen</a>"
                +   " und <a href=\"/wiki/21_jump_street\">21 Jump Street</a> und noch viel weiter.", 
                renderer.render("Heute gehen wir von [[Alpha Centauri]] nach [[Omega: Golem]] und [[21 Jump Street]] und noch viel weiter."));
    }        
    
    @Test    
    public void testInternalLinkWithColon() {
        assertEquals("<a href=\"/wiki/neverwinter_nights_enhanced_edition\">Neverwinter Nights: Enhanced Edition</a>", renderer.render("[[ Neverwinter Nights: Enhanced Edition ]] "));
    }        
    
    @Test    
    public void testInternalLinkWithDifferentName() {
        assertEquals("<a href=\"/wiki/hallo\">Welt</a>", renderer.render("[[Hallo|Welt]]"));
    }    

    @Test    
    public void testExternalLink() {
        assertEquals("<a href=\"http://heise.de\">http://heise.de</a>", renderer.render("[http://heise.de]"));
    }            
    
    @Test    
    public void testExternalLinkWithLabelWhitespace() {
        assertEquals("<a href=\"http://heise.de\">Seite</a>", renderer.render("[http://heise.de Seite]"));
    }     
    
    @Test    
    public void testExternalLinkWithLabelPipe() {
        assertEquals("<a href=\"http://heise.de\">Seite</a>", renderer.render("[http://heise.de|Seite]"));
    }    
    
    @Test
    public void testNewLine() {
        assertEquals("hallo<br />welt", renderer.render("hallo\nwelt"));
    }
     

}
