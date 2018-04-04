/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.services;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author comrad
 */
public class NodeServiceTest {

    private NodeService nodeService;

    @Before
    public void tearUp() {
        nodeService = new NodeService();
    }

    @Test
    public void testSlugifyAscii() {
        assertEquals("abcdefg", nodeService.slugify("abcdefg"));
    }

    @Test
    public void testSlugifySmallify() {
        assertEquals("ftl", nodeService.slugify("FTL"));
    }

    @Test
    public void testSlugifySmallifyMixed() {
        assertEquals("slingshot", nodeService.slugify("Slingshot"));
    }

    @Test
    public void testSlugifySpace() {
        assertEquals("ion_maiden", nodeService.slugify("Ion Maiden"));
    }

    @Test
    public void testSlugifySpaces() {
        assertEquals("out_park_baseball_19", nodeService.slugify("Out Of The Park Baseball 19"));
    }

    @Test
    public void testSlugifyRemoveApostrophes() {
        assertEquals("americas_army", nodeService.slugify("America's Army"));
    }

    @Test
    public void testSlugifyRemoveColons() {
        assertEquals("ash_gods_redemption", nodeService.slugify("Ash of Gods: Redemption"));
    }
    
    @Test
    public void testSlugifyReplaceDashes() {
        assertEquals("command_and_conquer_tiberian_sun", nodeService.slugify("Command and Conquer - Tiberian Sun"));
    }   
    
    @Test
    public void testSlugifyReplaceLowerDash() {
        assertEquals("linux_version_von_aspyrs_neustem_horrortitel_observer_kommt_am_2410", nodeService.slugify("Linux-Version von Aspyr's neustem Horrortitel observer_ kommt am 24.10."));
    }    
    
    @Test
    public void testSlugifyRemoveComma() {
        assertEquals("command_and_conquer_tiberian_sun", nodeService.slugify("Command and Conquer - Tiberian Sun"));
    }   
    
    @Test
    public void testSlugifyRemovePeriod() {
        assertEquals("linux_version_von_aspyrs_neustem_horrortitel_observer_kommt_am_2410", nodeService.slugify("Linux-Version von Aspyr's neustem Horrortitel observer_ kommt am 24.10."));
    }     
    
    @Test
    public void testSlugifyRemoveExclamationMark() {
        assertEquals("linuxspiele_adventskalender_2017_heute_ist_weihnachten_verlosung_nur_bis_15_uhr", nodeService.slugify("Linuxspiele-Adventskalender 2017: Heute ist Weihnachten - Verlosung nur bis 15 Uhr!"));
    }       
    
    @Test
    public void testSlugifyRemoveBraketLeft() {
        assertEquals("event0", nodeService.slugify("Event[0]"));
    }     
    
    @Test
    public void testSlugifyRemoveBraketRight() {
        assertEquals("event0", nodeService.slugify("Event[0]"));
    }        
    
    @Test
    public void testSlugifyRemoveCurlyBraketLeft() {
        assertEquals("hallo", nodeService.slugify("hallo}{"));
    }     
    
    @Test
    public void testSlugifyRemoveCurlyBraketRight() {
        assertEquals("hallo", nodeService.slugify("hallo}{"));
    }     
    
    @Test
    public void testSlugifyRemovePlus() {
        assertEquals("linux_spass", nodeService.slugify("Linux + Spass"));
    }       
    
    @Test
    public void testSlugifyRemoveAmpersand() {
        assertEquals("command_conquer_tiberian_sun", nodeService.slugify("Command & Conquer - Tiberian Sun"));
    }     

    @Test
    public void testSlugifyRemoveFillwordOf() {
        assertEquals("lords_xulima", nodeService.slugify("Lords of Xulima"));
    }
    
    @Test
    public void testSlugifyRemoveFillwordThe() {
        assertEquals("rise_tomb_raider", nodeService.slugify("Rise of the Tomb Raider"));
    }   
    
    @Test
    public void testSlugifyRemoveFillwordTo() {
        assertEquals("moon", nodeService.slugify("To the Moon"));
    }     
    
    @Test
    public void testSlugifyKeepFillwordAnd() {
        assertEquals("command_and_conquer_alarmstufe_rot", nodeService.slugify("Command and Conquer: Alarmstufe Rot"));
    }   
    
    @Test
    public void testSlugifyRemoveTheAtBeginning() {
        assertEquals("dwarves", nodeService.slugify("The Dwarves"));
    }  
    
    @Test
    public void testSlugifyRemoveThisAtBeginning() {
        assertEquals("war_mine", nodeService.slugify("This War Of Mine"));
    }  
    
    @Test
    public void testSlugifyKeepUmlaut() {
        assertEquals("brütal_legend", nodeService.slugify("Brütal Legend"));
    }    

}
