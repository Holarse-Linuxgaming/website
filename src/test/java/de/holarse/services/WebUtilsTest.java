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
import org.junit.Ignore;
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
        assertEquals("linux_windows_macosx", WebUtils.slugify("Linux, Windows, MacOSX"));
    }   
    
    @Test
    public void testSlugifyRemovePeriod() {
        assertEquals("linux_version_von_aspyrs_neustem_horrortitel_observer_kommt_am_2410", WebUtils.slugify("Linux-Version von Aspyr's neustem Horrortitel observer_ kommt am 24.10."));
    }     

    @Test
    public void testSlugifyRemoveHashtag() {
        assertEquals("ich_bin_ohne_hashtag", WebUtils.slugify("Ich bin ohne #Hashtag"));
    }        

    @Ignore
    @Test
    public void testSlugifyRemoveHashtagAndStrip1() {    
        assertEquals("holarse_wochenend_rückblick_2020_06_drückblick_besiege_verlässt_early_access_luna_shadow_dust", WebUtils.slugify("Holarse Wochenend-Rückblick 2020-06 #Drückblick - Besiege verlässt Early Access, LUNA The Shadow Dust veröffentlicht, neuer Simutrans-Release, Humble Bundle jetzt auf deutsch, mal wieder ein Indie-Pick uvm."));
    }        

    @Ignore
    @Test
    public void testSlugifyRemoveHashtagAndStrip2() {    
        assertEquals("holarse_wochenend_rückblick_2020_05_drückblick_avorion_bald_10_empires_ruins_verspricht_linux", WebUtils.slugify("Holarse Wochenend-Rückblick 2020-05 #Drückblick - Avorion bald 1.0, Empires in Ruins verspricht Linux, Starcom: Nexus hat Linux, Universim nun in Beta und UnityStation Public Alpha für SpaceStation13-Fans, Icculus aktualisiert Descent uvm."));
    }        
    
    @Test
    public void testSlugifyRemoveHashtagAndStrip3() {    
        assertEquals("holarse_wochenend_rückblick_2020_04_drückblick_daedalic_mit_zwei_neuen_spielen_für_linux_little", WebUtils.slugify("Holarse Wochenend-Rückblick 2020-04 #Drückblick - Daedalic mit zwei neuen Spielen für Linux, Little Racers STREET erhält spontanen Port dank FNA - Godot Engine 3.2 - NVIDIA stampft Treiber für ältere Karten ein"));
    }        
    
    @Ignore    
    @Test
    public void testSlugifyRemoveHashtagAndStrip4() {    
        assertEquals("holarse_wochenend_rückblick_3_im_januar_2020_drückblick_ärger_um_rocket_league_half_life_serie", WebUtils.slugify("Holarse Wochenend-Rückblick 3 im Januar 2020 #Drückblick - Ärger um Rocket League - Half-Life-Serie bis Alyx-Release kostenlos spielbar - Wine 5 und der Lunar Sale startet"));
    }        

    @Ignore    
    @Test
    public void testSlugifyRemoveHashtagAndStrip5() {
        assertEquals("holarse_wochenend_rückblick_51_im_januar_2020_drückblick_der_erste_und_beste_drückblick_des", WebUtils.slugify("Holarse Wochenend-Rückblick 51 im Januar 2020 #Drückblick - Der erste und beste Drückblick des Jahres - Open Source-Update-Fest"));
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
