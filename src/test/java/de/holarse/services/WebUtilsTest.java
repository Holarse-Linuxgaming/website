/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.services;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author comrad
 */
public class WebUtilsTest {
    
    @Test
    public void testSlugifyAscii() {
        assertEquals("abcdefg", WebUtils.slugify("abcdefg"));
    }

    @Test
    public void testSlugifySmallify() {
        assertEquals("ftl", WebUtils.slugify("FTL"));
    }

    @Test
    public void testSlugifySmallifyMixed() {
        assertEquals("slingshot", WebUtils.slugify("Slingshot"));
    }

    @Test
    public void testSlugifySpace() {
        assertEquals("ion_maiden", WebUtils.slugify("Ion Maiden"));
    }

    @Test
    public void testSlugifySpaces() {
        assertEquals("out_park_baseball_19", WebUtils.slugify("Out Of The Park Baseball 19"));
    }

    @Test
    public void testSlugifyRemoveApostrophes() {
        assertEquals("americas_army", WebUtils.slugify("America's Army"));
    }

    @Test
    public void testSlugifyRemoveColons() {
        assertEquals("ash_gods_redemption", WebUtils.slugify("Ash of Gods: Redemption"));
    }
    
    @Test
    public void testSlugifyReplaceDashes() {
        assertEquals("command_and_conquer_tiberian_sun", WebUtils.slugify("Command and Conquer - Tiberian Sun"));
    }   
    
    @Test
    public void testSlugifyReplaceLowerDash() {
        assertEquals("linux_version_von_aspyrs_neustem_horrortitel_observer_kommt_am_2410", WebUtils.slugify("Linux-Version von Aspyr's neustem Horrortitel observer_ kommt am 24.10."));
    }    
    
    @Test
    public void testSlugifyRemoveComma() {
        assertEquals("command_and_conquer_tiberian_sun", WebUtils.slugify("Command and Conquer - Tiberian Sun"));
    }   
    
    @Test
    public void testSlugifyRemovePeriod() {
        assertEquals("linux_version_von_aspyrs_neustem_horrortitel_observer_kommt_am_2410", WebUtils.slugify("Linux-Version von Aspyr's neustem Horrortitel observer_ kommt am 24.10."));
    }     
    
    @Test
    public void testSlugifyRemoveExclamationMark() {
        assertEquals("linuxspiele_adventskalender_2017_heute_ist_weihnachten_verlosung_nur_bis_15_uhr", WebUtils.slugify("Linuxspiele-Adventskalender 2017: Heute ist Weihnachten - Verlosung nur bis 15 Uhr!"));
    }       
    
    @Test
    public void testSlugifyRemoveBraketLeft() {
        assertEquals("event0", WebUtils.slugify("Event[0]"));
    }     
    
    @Test
    public void testSlugifyRemoveBraketRight() {
        assertEquals("event0", WebUtils.slugify("Event[0]"));
    }        
    
    @Test
    public void testSlugifyRemoveCurlyBraketLeft() {
        assertEquals("hallo", WebUtils.slugify("hallo}{"));
    }     
    
    @Test
    public void testSlugifyRemoveCurlyBraketRight() {
        assertEquals("hallo", WebUtils.slugify("hallo}{"));
    }     
    
    @Test
    public void testSlugifyRemovePlus() {
        assertEquals("linux_spass", WebUtils.slugify("Linux + Spass"));
    }       
    
    @Test
    public void testSlugifyRemoveAmpersand() {
        assertEquals("command_conquer_tiberian_sun", WebUtils.slugify("Command & Conquer - Tiberian Sun"));
    }     

    @Test
    public void testSlugifyRemoveFillwordOf() {
        assertEquals("lords_xulima", WebUtils.slugify("Lords of Xulima"));
    }
    
    @Test
    public void testSlugifyRemoveFillwordThe() {
        assertEquals("rise_tomb_raider", WebUtils.slugify("Rise of the Tomb Raider"));
    }   
    
    @Test
    public void testSlugifyRemoveFillwordTo() {
        assertEquals("moon", WebUtils.slugify("To the Moon"));
    }     
    
    @Test
    public void testSlugifyKeepFillwordAnd() {
        assertEquals("command_and_conquer_alarmstufe_rot", WebUtils.slugify("Command and Conquer: Alarmstufe Rot"));
    }   
    
    @Test
    public void testSlugifyRemoveTheAtBeginning() {
        assertEquals("dwarves", WebUtils.slugify("The Dwarves"));
    }  
    
    @Test
    public void testSlugifyRemoveThisAtBeginning() {
        assertEquals("war_mine", WebUtils.slugify("This War Of Mine"));
    }  
    
    @Test
    public void testSlugifyKeepUmlaut() {
        assertEquals("brütal_legend", WebUtils.slugify("Brütal Legend"));
    }   
    
}
