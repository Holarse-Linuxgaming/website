package de.holarse.renderer.html;

import de.holarse.renderer.Renderer;
import de.holarse.renderer.plain.PlainRenderer;

import static org.junit.Assert.*;
import org.junit.Before;

public class PlainRendererTest {

    private Renderer renderer;

    @Before
    public void setUp() {
        renderer = new PlainRenderer();
    }

    //@Test
    public void testRendererOnNull() {
        assertEquals("", renderer.render(null));
    }

    //@Test
    public void testRendererOnEmpty() {
        assertEquals("", renderer.render(""));
    }

    //@Test
    public void testRendererOnBlank() {
        assertEquals("", renderer.render("           "));
    }

    /**
     * HEADER
     */
    
    //@Test
    public void testHeader2() {
        assertEquals("Test<br />", renderer.render("== Test =="));
    }

    //@Test    
    public void testHeader3() {
        assertEquals("Test<br />", renderer.render("=== Test ==="));
    }
    
    //@Test    
    public void testHeader3WithText() {
        assertEquals("Das Spiel<br />CodeRED - Alien Arena ist ein kostenloser", renderer.render("===Das Spiel===\nCodeRED - Alien Arena ist ein kostenloser"));
    }    

    //@Test    
    public void testHeader4() {
        assertEquals("Test<br />", renderer.render("==== Test ===="));
    }

    //@Test    
    public void testHeader5() {
        assertEquals("Test<br />", renderer.render("===== Test ====="));
    }
    
    //@Test
    public void testHeader2Trimmed() {
        assertEquals("Test<br />", renderer.render("==Test=="));
    }

    //@Test    
    public void testHeader3Trimmed() {
        assertEquals("<h3>Test</h3>", renderer.render("===Test==="));
    }

    //@Test    
    public void testHeader4Trimmed() {
        assertEquals("Test<br />", renderer.render("====Test===="));
    }

    //@Test    
    public void testHeader5Trimmed() {
        assertEquals("Test<br />", renderer.render("=====Test====="));
    }    

//    
//    //@Test    
//    public void testHeaderMixedWithLink() {
//        assertEquals("<h3><a href=\"/html/lala\">Lala</a></h3>", renderer.render("===[[Lala]]==="));
//    }    
//    
    /**
     * LINKS
     */
    
    //@Test    
    public void testTwoInternalLinksWithTestBetween() {
        assertEquals(
                    "Heute gehen wir von Alpha Centauri nach Omega: Golem"
                +   " und 21 Jump Street und noch viel weiter.", 
                renderer.render("Heute gehen wir von [[Alpha Centauri]] nach [[Omega: Golem]] und [[21 Jump Street]] und noch viel weiter."));
    }        
    
    //@Test    
    public void testInternalLinkWithColon() {
        assertEquals("Neverwinter Nights: Enhanced Edition", renderer.render("[[Neverwinter Nights: Enhanced Edition]]"));
    }    
    
    // Test für Workaround in Drupal
    //@Test    
    public void testInternalLinkWithColonWhitespaces() {
        assertEquals("Neverwinter Nights: Enhanced Edition", renderer.render("[[ Neverwinter Nights: Enhanced Edition ]]"));
    }        

    //@Test    
    public void testExternalPlainLinkWithoutLabel() {
        assertEquals("Hier geht es weiter: http://webseite.com", renderer.render("Hier geht es weiter: http://webseite.com"));
    }           
    
    //@Test    
    public void testExternalSslLinkWithoutLabel() {
        assertEquals("Hier geht es weiter: https://startpage.com", renderer.render("Hier geht es weiter: https://startpage.com"));
    }     
    
    //@Test    
    public void testLinkAsImage() {
        assertEquals("Hier geht es weiter: https://startpage.com/image.jpg", renderer.render("Hier geht es weiter: https://startpage.com/image.jpg"));
    }         

    //@Test    
    public void testInternalLink() {
        assertEquals("HalloWelt", renderer.render("[[HalloWelt]]"));
    }          
    
    //@Test    
    public void testInternalLinkWithSpaces() {
        assertEquals("Hallo Welt", renderer.render("[[Hallo Welt]]"));
    }      
    
    //@Test    
    public void testInternalLinkWithDifferentName() {
        assertEquals("Welt", renderer.render("[[Hallo|Welt]]"));
    }    

    //@Test    
    public void testExternalLink() {
        assertEquals("http://heise.de", renderer.render("[http://heise.de]"));
    }       
    
    //@Test    
    public void testExternalSslLink() {
        assertEquals("https://heise.de", renderer.render("[https://heise.de]"));
    }          
    
    //@Test    
    public void testExternalLinkWithLabelWhitespace() {
        assertEquals("Seite", renderer.render("[http://heise.de Seite]"));
    }    
    
    //@Test    
    public void testExternalLinkWithLabelWhitespaces() {
        assertEquals("Seite mit Leerzeichen", renderer.render("[http://heise.de Seite mit Leerzeichen]"));
    }   
    
    //@Test    
    public void testExternalLinkWithLabelWhitespacesAndColon() {
        assertEquals("Seite mit Leerzeichen: So gut", renderer.render("[http://heise.de Seite mit Leerzeichen: So gut]"));
    }     
    
    //@Test    
    public void testExternalLinkWithLabelPipe() {
        assertEquals("Seite", renderer.render("[http://heise.de|Seite]"));
    }    
    
    /**
     * LISTS
     */
    
    //@Test    
    public void testList() {
        assertEquals("eins, zwei, drei<br />", renderer.render("* eins\n* zwei\n* drei\n\n"));
    }     
    
//    //@Test    
//    public void testDeeperList() {
//        assertEquals("Folgende Liste: <ul><li>eins</li><li>zwei<ul><li>zweieinhalb</li></ul></li><li>drei</li></ul>", renderer.render("Folgende Liste: \n* eins\n* zwei\n** zweieinhalb\n* drei\n"));
//    }      

    //@Test    
    public void testNumericList() {
        assertEquals("eins, zwei, drei", renderer.render("# eins\n# zwei\n# drei\n\n"));
    }     
    
//    //@Test    
//    public void testDeeperNumericList() {
//        assertEquals("Folgende Liste: <ol><li>eins</li><li>zwei<ul><li>zweieinhalb</li></ol></li><li>drei</li></ol>", renderer.render("Folgende Liste: \n# eins\n# zwei\n## zweieinhalb\n# drei\n"));
//    }     
    
    //@Test
    public void testListEnding() {
        String input = "* das\n" +
"* ist eine\n" +
"* liste\n" +
"* mit punkten\n" +
"\n" +
"\n" +
"nun folgt etwas text:\n" +
"\n" +
"# eins\n" +
"# zwei\n" +
"#drei\n" +
"# vier\n";
        
        String expected = "das</li><li>ist eine</li><li>liste</li><li>mit punkten</li></ul><br />nun folgt etwas text:<br /><br /><ol><li>eins</li><li>zwei</li><li>drei</li><li>vier</li></ol>";
        
        assertEquals(expected, renderer.render(input));
    }
    
    /**
     * TEXT MODIFICATION
     */
    
    //@Test
    public void testTicks() {
        assertEquals("'hallo'", renderer.render("'hallo'"));
    }
    
    //@Test
    public void testItalic() {
        assertEquals("hallo", renderer.render("''hallo''"));
    }    
    
    //@Test
    public void testBold() {
        assertEquals("hallo", renderer.render("'''hallo'''"));
    }        
    
//    //@Test
//    public void testBoldWithTicks() {
//        assertEquals("<b>'hallo'</b>", renderer.render("''''hallo''''"));
//    }            
    
    /**
     * Code
     */
    //@Test
    public void testCodeInline() {
        assertEquals("Das ist ein Codetext.", renderer.render("Das ist ein [code]Codetext[/code]."));
    }
    
    //@Test
    public void testCodeBlock() {
        assertEquals("Und hier ein Codeblock: <br /><pre>\ncd $HOME\n./run\n</pre>.", renderer.render("Und hier ein Codeblock: \n[code]\ncd $HOME\n./run\n[/code]."));
    }    
    
    /**
     * OTHER
     */
    
    //@Test
    public void testNewLine() {
        assertEquals("hallo<br />welt", renderer.render("hallo\nwelt"));
    }    
    
    //@Test
    public void testBreak() {
        assertEquals("<br />", renderer.render("<!--break-->"));
    }

}
