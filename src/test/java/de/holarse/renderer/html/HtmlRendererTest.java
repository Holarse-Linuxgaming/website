package de.holarse.renderer.html;

import de.holarse.renderer.Renderer;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;

public class HtmlRendererTest {

    private Renderer renderer;

    @Before
    public void setUp() {
        renderer = new HtmlRenderer();
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
    public void testHeader3WithText() {
        assertEquals("<h3>Das Spiel</h3><br />CodeRED - Alien Arena ist ein kostenloser", renderer.render("===Das Spiel===\nCodeRED - Alien Arena ist ein kostenloser"));
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

    @Ignore
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
                    "Heute gehen wir von <a href=\"/wiki/alpha_centauri\">Alpha Centauri</a> nach <a href=\"/wiki/omega_golem\">Omega: Golem</a>"
                +   " und <a href=\"/wiki/21_jump_street\">21 Jump Street</a> und noch viel weiter.",
                renderer.render("Heute gehen wir von [[Alpha Centauri]] nach [[Omega: Golem]] und [[21 Jump Street]] und noch viel weiter."));
    }        
    
    @Test    
    public void testInternalLinkWithColon() {
        assertEquals("<a href=\"/wiki/neverwinter_nights_enhanced_edition\">Neverwinter Nights: Enhanced Edition</a>", renderer.render("[[Neverwinter Nights: Enhanced Edition]]"));
    }    
    
    // Test für Workaround in Drupal
    @Test    
    public void testInternalLinkWithColonWhitespaces() {
        assertEquals("<a href=\"/wiki/neverwinter_nights_enhanced_edition\">Neverwinter Nights: Enhanced Edition</a>", renderer.render("[[ Neverwinter Nights: Enhanced Edition ]]"));
    }        

    @Test    
    public void testExternalPlainLinkWithoutLabel() {
        assertEquals("Hier geht es weiter: <a href=\"http://webseite.com\">http://webseite.com</a>", renderer.render("Hier geht es weiter: http://webseite.com"));
    }           
    
    @Test    
    public void testExternalSslLinkWithoutLabel() {
        assertEquals("Hier geht es weiter: <a href=\"https://startpage.com\">https://startpage.com</a>", renderer.render("Hier geht es weiter: https://startpage.com"));
    }     
    
    @Test    
    public void testLinkAsImage() {
        assertEquals("Hier geht es weiter: <img src=\"https://startpage.com/image.jpg\" />", renderer.render("Hier geht es weiter: https://startpage.com/image.jpg"));
    }         

    @Test    
    public void testInternalLink() {
        assertEquals("<a href=\"/wiki/hallowelt\">HalloWelt</a>", renderer.render("[[HalloWelt]]"));
    }          
    
    @Test    
    public void testInternalLinkWithSpaces() {
        assertEquals("<a href=\"/wiki/hallo_welt\">Hallo Welt</a>", renderer.render("[[Hallo Welt]]"));
    }      
    
    @Test    
    public void testInternalLinkWithDifferentName() {
        assertEquals("<a href=\"/wiki/hallo\">Welt</a>", renderer.render("[[Hallo|Welt]]"));
    }    

    @Test    
    public void testExternalLink() {
        assertEquals("<a href=\"http://heise.de\" class=\"external-link\" target=\"_blank\">http://heise.de</a>", renderer.render("[http://heise.de]"));
    }       
    
    @Test    
    public void testExternalSslLink() {
        assertEquals("<a href=\"https://heise.de\" class=\"external-link\" target=\"_blank\">https://heise.de</a>", renderer.render("[https://heise.de]"));
    }          
    
    @Test    
    public void testExternalLinkWithLabelWhitespace() {
        assertEquals("<a href=\"http://heise.de\" class=\"external-link\" target=\"_blank\">Seite</a>", renderer.render("[http://heise.de Seite]"));
    }    
    
    @Test    
    public void testExternalLinkWithLabelWhitespaces() {
        assertEquals("<a href=\"http://heise.de\" class=\"external-link\" target=\"_blank\">Seite mit Leerzeichen</a>", renderer.render("[http://heise.de Seite mit Leerzeichen]"));
    }   
    
    @Test    
    public void testExternalLinkWithLabelWhitespacesAndColon() {
        assertEquals("<a href=\"http://heise.de\" class=\"external-link\" target=\"_blank\">Seite mit Leerzeichen: So gut</a>", renderer.render("[http://heise.de Seite mit Leerzeichen: So gut]"));
    }     
    
    @Test    
    public void testExternalLinkWithLabelPipe() {
        assertEquals("<a href=\"http://heise.de\" class=\"external-link\" target=\"_blank\">Seite</a>", renderer.render("[http://heise.de|Seite]"));
    }    
    
    /**
     * LISTS
     */
    
    @Test    
    public void testList() {
        assertEquals("<ul><li>eins</li><li>zwei</li><li>drei</li></ul>", renderer.render("* eins\n* zwei\n* drei\n\n"));
    }     
    
    @Ignore
    @Test
    public void testIsNotAList() {
        String text = "Der Editor läuft auf Windows, OS X, Linux, *BSD und Haiku.";
        assertEquals(text, renderer.render(text));
    }

    @Ignore    
    @Test    
    public void testDeeperList() {
        assertEquals("Folgende Liste: <ul><li>eins</li><li>zwei<ul><li>zweieinhalb</li></ul></li><li>drei</li></ul>", renderer.render("Folgende Liste: \n* eins\n* zwei\n** zweieinhalb\n* drei\n"));
    }      

    @Test    
    public void testNumericList() {
        assertEquals("<ol><li>eins</li><li>zwei</li><li>drei</li></ol>", renderer.render("# eins\n# zwei\n# drei\n\n"));
    }     
    
    @Ignore
    @Test    
    public void testDeeperNumericList() {
        assertEquals("Folgende Liste: <ol><li>eins</li><li>zwei<ul><li>zweieinhalb</li></ol></li><li>drei</li></ol>", renderer.render("Folgende Liste: \n# eins\n# zwei\n## zweieinhalb\n# drei\n"));
    }     
    
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
        
        String expected = "<ul><li>das</li><li>ist eine</li><li>liste</li><li>mit punkten</li></ul><br />nun folgt etwas text:<br /><br /><ol><li>eins</li><li>zwei</li><li>drei</li><li>vier</li></ol>";
        
        assertEquals(expected, renderer.render(input));
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
    
    @Ignore
    @Test
    public void testBoldWithTicks() {
        assertEquals("<b>'hallo'</b>", renderer.render("''''hallo''''"));
    }            
    
    /**
     * Code
     */
    @Test
    public void testCodeInline() {
        assertEquals("Das ist ein <pre><code class=\"language-bash\">Codetext</code></pre>.", renderer.render("Das ist ein [code]Codetext[/code]."));
    }
    
    @Test
    public void testCodeBlock() {
        assertEquals("Und hier ein Codeblock: <br /><pre><code class=\"language-bash\">\ncd $HOME\n./run\n</code></pre>.", renderer.render("Und hier ein Codeblock: \n[code]\ncd $HOME\n./run\n[/code]."));
    }    
    
    /**
     * OTHER
     */
    
    @Test
    public void testNewLine() {
        assertEquals("hallo<br />welt", renderer.render("hallo\nwelt"));
    }    
    
    // Two Newlines
    @Ignore
    @Test
    public void testParagraph() {
        String text = "Ich bin ein Satz in einem\n\neigenen Absatz\n\nund hier gehts weiter.";
        assertEquals("<p>Ich bin ein Satz in einem</p><p>eigenen Absatz</p><p>und hier gehts weiter.", renderer.render(text));
    }
    
    @Test
    public void testBreak() {
        assertEquals("<br />", renderer.render("<!--break-->"));
    }
    
    @Ignore
    @Test
    public void testEscapeScript() {
        String text = "Hallo <script>alert(\"error\");</script> Welt";
        String expc = "Hallo &lt;script&gt;alert(\"error\");&lt;/script&gt; Welt";
        assertEquals(expc, renderer.render(text));
    }

    @Test
    public void testVersionShouldNotBeALink() {
	String text = "Das ist Version 1.2.3 des Projekts.";
	String expected = "Das ist Version 1.2.3 des Projekts.";
	assertEquals(expected, renderer.render(text));
    }
    
    /*** ERROR CASES ***/
    
    /**
     * Issue #106
     * Nach dem Beginn eines Fettdrucks bleibt dieser bestehen.
     */
    @Ignore
    @Test
    public void testBug106() {
        String text = "Um nun \"zod\" und den \"zod_launcher\" selbst bauen zu können, muss man zuvor die Datei '''zod_launcherFrm.cpp''' im Ordner \"zod_launcher_src\" anpassen.";
        String expc = "Um nun \"zod\" und den \"zod_launcher\" selbst bauen zu können, muss man zuvor die Datei <b>zod_launcherFrm.cpp</b> im Ordner \"zod_launcher_src\" anpassen.";
        
        assertEquals(expc, renderer.render(text));        
    } 
    
    @Test
    public void testCitiesSyklines() {
        String text = "<!--break-->\n" +
"===Das Spiel===\n" +
"Cities: Skylines war 2015 das Spiel auf das ehemalige Windows-Städtebauer, *hust* [[SimCity]] *hust*, gewartet hatten. Während [[Sim City 3000]] nur noch schwerlich zur Mitarbeit unter aktuellen Linuxbetriebssystemen bewegt werden konnte und nach diversen zaghaften Versuchen der Open Source Community eine Städtebausimulation zu etablieren, es seinen [[FreeReign]], [[OpenCity]] aber auch [[LinCityNG]] genannt, wurde die Ankündigung von Cities: Skylines zum Hoffnungsschimmer aller Linux-Hobbybürgermeister. Mit der Veröffentlichung am 10. März 2015 ([https://www.holarse-linuxgaming.de/content/cities_skylines_erscheint_am_10_m%C3%A4rz_mit_linux_version Holarse Shortnews]) konnten nun auch Linuxer Tage und Nächte in ihre digitalen Großstädte investieren. \n" +
"Es wird lediglich ein Einzelspielermodus angeboten. Im Koop eine Stadt zu verwalten oder sich mit Nachbarstädten zu verbinden wurde nicht implementiert.\n" +
"\n" +
"===Gameplay===\n" +
"Cities: Skylines zeigt in vielerlei Hinsicht Aspekte eines Easy-to-learn;hard-to-master Titels. Man beginnt mit freier Fläche und wenigen Bauoptionen. Je mehr Einwohner man anlocken kann, desto mehr Bauoptionen aber auch Probleme werden frei geschaltet. Von anfangs naheliegenden Problemchen wie Strom- und Wasserversorgung müssen später möglichst alle Viertel gut an die soziale Infrastruktur wie Schulen, Krankenhäuser und öffentlicher Nahverkehr angebunden sein. Ist das nicht der Fall sind unbeliebte Stadtviertel mit niedrigen Grundstückspreisen die Folge, was sich wiederum negativ auf die möglichen Einnahmen auswirkt. Geld ist erwartungsgemäß generell ein heikles Thema. Präsentiert man potenziellen Einwohnern in Stadtform den Himmel auf Erden, darf davon ausgegangen werden dass man diesen Luxus nicht sonderlich lange finanzieren kann. Spart man am falschen Ende versiegt die Frischwasserzufuhr oder die unterbezahlte Polizei kann die Kriminalität nicht mehr im Zaum halten. Kürzt man dann noch das Geld für das Gesundheitswesen hortet man auf Dauer zwar Geld aber eben auch eine Menge keuchender, ausgedörter und kriminalisierter Bewohner. Das goldene Mittelmaß zu finden entspricht dem hard-to-master-Teil des Spiels.\n" +
"Nicht falsch verstehen, scheitern dauert lange und man bekommt meistens irgendwie doch noch die Kurve am Ende aber wenn man sich beim Herauszoomen sein Werk so ansieht mit seinen vielen Ecken und Kanten, seinen vielen verbauten Straßen und der Umweltverschmutzung inmitten des Wohngebietes, wächst spätestens dann der Wunsch ganz sachte die Neustart-Taste zu betätigen. Spätestens der Versuch in eine verkorkste Großstadt nachträglich noch ein zufriedenstellendes Nahverkehrsnetz zu basteln triggert dann letztlich den Alles-auf-Anfang Button.\n" +
"\n" +
"===Installation===\n" +
"Stichwort [[Steam]]. Eine Nicht-Steam Variante wird nach unseren Informationen nicht vertrieben. Ebenfalls auf [[Steam]] werden diverse DLCs angeboten, die den Umfang des eigentlichen Spiels um weitere Inhalte ergänzen. Erworben können Steam-Keys auch beim hauseigenen Paradox Plaza-Store und im Humble Store.\n" +
"\n" +
"===Benötigte Hardware===\n" +
"Zum Spielen benötigt man laut Hersteller mindestens einen Intel Core 2 Duo mit 3,00GHZ pro Kern bzw. einen AMD Athlon 64 X2 6400+. \n" +
"Bei der eingesetzten Grafikkarte sollte es sich mindestens um eine GeForce GTX 260 oder eine ATI Radeon HD 5670 mit 512MB Speichern handeln. Intel-HD-Chips werden offiziell nicht unterstützt, in einem Testszenario verweigerte das Spiel mit einem Intel-HD 4200er Chip den Start.\n" +
"\n" +
"Wie so häufig gilt auch hier, je mehr desto besser. Größere Städte mit ihrem hochdetailierten Eigenleben neigen schnell dazu die FPS zu schmälern. Wer auch im späteren Spielverlauf ohne Diashow-Effekt daddeln möchte sollte genug Hardwarereserven haben oder muss sich mit niedrigeren Details zufrieden geben. Eine gute CPU ist hierbei entscheidend.\n" +
"\n" +
"===Getestete Geräte===\n" +
"[[TUXEDO Book XC1706]], i7-6700HQ, 64GB RAM, Nvidia Geforce GTX 970M → in Ordnung\n" +
"[[TUXEDO Book XC1507]], i7-7700HQ, 32GB RAM, Nvidia Geforce GTX 1060M → in Ordnung (max. Details)\n" +
"AMD Ryzen 7 1800X, 32GB RAM, Nvidia GeForce 960 GTX →in Ordnung (max. Details)\n" +
"AMD Desktop-PC, A8-3850, 8GB RAM, Nvidia Geforce 650GTX → in Ordnung (mit Abstrichen)\n" +
"AMD Desktop-PC, A8-3850, 8GB RAM, AMD Radeon RX460 → in Ordnung (mit Abstrichen)\n" +
"Intel Desktop-PC, C2D E8500, 4GB RAM, AMD Radeon HD 5770 → startet nicht\n" +
"AMD Desktop-PC, Athlon 3200+, 2GB RAM, Nvidia 7900GTO → startet nicht\n" +
"Acer Aspire One 756, Intel Pentium 987, 4GB Ram, Intel-HD → startet nicht";
        
        renderer.render(text);
    }

}
