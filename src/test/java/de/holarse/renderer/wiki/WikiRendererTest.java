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

    /**
     * HEADER
     */
    
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
    public void testHeaderMixedWithLink() {
        assertEquals("<h3><a href=\"/wiki/lala\">Lala</a></h3>", renderer.render("===[[Lala]]==="));
    }    
    
    /**
     * LINKS
     */
    
    @Test    
    public void testTwoInternalLinksWithTestBetween() {
        assertEquals(
                    "Heute gehen wir von <a href=\"/wiki/alpha_centauri\">Alpha Centauri</a> nach <a href=\"/wiki/omega_golem\">Omega: Golen</a>"
                +   " und <a href=\"/wiki/21_jump_street\">21 Jump Street</a> und noch viel weiter.", 
                renderer.render("Heute gehen wir von [[Alpha Centauri]] nach [[Omega: Golem]] und [[21 Jump Street]] und noch viel weiter."));
    }        
    
    @Test    
    public void testInternalLinkWithColon() {
        assertEquals("<a href=\"/wiki/neverwinter_nights_enhanced_edition\">Neverwinter Nights: Enhanced Edition</a>", renderer.render("[[Neverwinter Nights: Enhanced Edition]] "));
    }    
    
    // Test für Workaround in Drupal
    @Test    
    public void testInternalLinkWithColonWhitespaces() {
        assertEquals("<a href=\"/wiki/neverwinter_nights_enhanced_edition\">Neverwinter Nights: Enhanced Edition</a>", renderer.render("[[ Neverwinter Nights: Enhanced Edition ]] "));
    }        

    @Test    
    public void testExternalPlainLinkWithoutLabel() {
        assertEquals("Hier geht es weiter: <a href=\"http://webseite.com\">http://webseite.com</a>.", renderer.render("Hier geht es weiter: http://webseite.com."));
    }           
    
    @Test    
    public void testExternalSslLinkWithoutLabel() {
        assertEquals("Hier geht es weiter: <a href=\"https://startpage.com\">https://startpage.com</a>.", renderer.render("Hier geht es weiter: https://startpage.com."));
    }     
    
    @Test    
    public void testLinkAsImage() {
        assertEquals("Hier geht es weiter: <img src=\"https://startpage.com/image.jpg\" />.", renderer.render("Hier geht es weiter: https://startpage.com/image.jpg."));
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
    public void testExternalLinkWithLabelWhitespaces() {
        assertEquals("<a href=\"http://heise.de\">Seite mit Leerzeichen</a>", renderer.render("[http://heise.de Seite mit Leerzeichen]"));
    }   
    
    @Test    
    public void testExternalLinkWithLabelWhitespacesAndColon() {
        assertEquals("<a href=\"http://heise.de\">Seite mit Leerzeichen: So gut</a>", renderer.render("[http://heise.de Seite mit Leerzeichen: So gut]"));
    }     
    
    @Test    
    public void testExternalLinkWithLabelPipe() {
        assertEquals("<a href=\"http://heise.de\">Seite</a>", renderer.render("[http://heise.de|Seite]"));
    }    
    
    /**
     * LISTS
     */
    
    @Test    
    public void testList() {
        assertEquals("Folgende Liste: <ul><li>eins</li><li>zwei</li><li>drei</li></ul>", renderer.render("Folgende Liste: \n* eins\n* zwei\n* drei\n"));
    }     
    
    @Test    
    public void testDeeperList() {
        assertEquals("Folgende Liste: <ul><li>eins</li><li>zwei<ul><li>zweieinhalb</li></ul></li><li>drei</li></ul>", renderer.render("Folgende Liste: \n* eins\n* zwei\n** zweieinhalb\n* drei\n"));
    }      

    @Test    
    public void testNumericList() {
        assertEquals("Folgende Liste: <ol><li>eins</li><li>zwei</li><li>drei</li></ol>", renderer.render("Folgende Liste: \n# eins\n# zwei\n# drei\n"));
    }     
    
    @Test    
    public void testDeeperNumericList() {
        assertEquals("Folgende Liste: <ol><li>eins</li><li>zwei<ul><li>zweieinhalb</li></ol></li><li>drei</li></ol>", renderer.render("Folgende Liste: \n# eins\n# zwei\n## zweieinhalb\n# drei\n"));
    }          
    
    /**
     * TEXT MODIFICATION
     */
    
    @Test
    public void testTicks() {
        assertEquals("'hallo'", renderer.render("'hallo'"));
    }
    
    @Test
    public void testItalic() {
        assertEquals("<i>hallo</i>", renderer.render("''hallo''"));
    }    
    
    @Test
    public void testBold() {
        assertEquals("<b>hallo</b>", renderer.render("'''hallo'''"));
    }        
    
//    @Test
//    public void testBoldWithTicks() {
//        assertEquals("<b>'hallo'</b>", renderer.render("''''hallo''''"));
//    }            
    
    /**
     * Code
     */
    @Test
    public void testCodeInline() {
        assertEquals("Das ist ein <pre>Codetext</pre>.", renderer.render("Das ist ein [code]Codetext[/code]."));
    }
    
    @Test
    public void testCodeBlock() {
        assertEquals("Und hier ein Codeblock: \n<pre>\ncd $HOME\n./run\n</pre>.", renderer.render("Und hier ein Codeblock: \n[code]\ncd $HOME\n./run\n[/code]."));
    }    
    
    /**
     * OTHER
     */
    
    @Test
    public void testNewLine() {
        assertEquals("hallo<br />welt", renderer.render("hallo\nwelt"));
    }    
    
    @Test
    public void testBreak() {
        assertEquals("", renderer.render("<!--break-->"));
    }

}
