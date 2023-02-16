package de.holarse.web.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class SlugServiceTest {

    private SlugService slugService;
    
    @BeforeEach
    public void setup() {
        slugService = new SlugService();
    }
    
    @Test
    public void testSlugifyNull() {
        String input = null;
        assertEquals("", slugService.slugify(input), "should not fail");
    }
    
@Test
    public void testSlugifyAscii() {
        assertEquals("abcdefg", slugService.slugify("abcdefg"));
    }

    @Test
    public void testSlugifySmallify() {
        assertEquals("ftl", slugService.slugify("FTL"));
    }

    @Test
    public void testSlugifySmallifyMixed() {
        assertEquals("slingshot", slugService.slugify("Slingshot"));
    }

    @Test
    public void testSlugifySpace() {
        assertEquals("ion_maiden", slugService.slugify("Ion Maiden"));
    }

    @Test
    public void testSlugifySpaces() {
        assertEquals("out_park_baseball_19", slugService.slugify("Out Of The Park Baseball 19"));
    }

    @Test
    public void testSlugifyRemoveApostrophes() {
        assertEquals("americas_army", slugService.slugify("America's Army"));
    }

    /**
     * Dieses Slug entspricht den Slugs z.b. aus der Suchliste. Im Wiki wird der Doppelpunkt beibehalten.
     * Wir vereinheitlichen das allerdings auf ohne Doppelpunkte.
     */
    @Test
    public void testSlugifyRemoveColons() {
        assertEquals("ash_gods_redemption", slugService.slugify("Ash of Gods: Redemption"));
    }
    
    @Test
    public void testSlugifyRemoveColons2() {
        assertEquals("imperator_rome", slugService.slugify("Imperator: Rome"));
    }
    
    @Test
    public void testSlugifyRemoveColonsNoSpace() {
        assertEquals("imperator_rome", slugService.slugify("Imperator:Rome"));
    }    
    
    @Test
    public void testSlugifyReplaceDashes() {
        assertEquals("command_and_conquer_tiberian_sun", slugService.slugify("Command and Conquer - Tiberian Sun"));
    }   
    
    @Test
    public void testSlugifyReplaceLowerDash() {
        assertEquals("linux_version_von_aspyrs_neustem_horrortitel_observer_kommt_am_2410", slugService.slugify("Linux-Version von Aspyr's neustem Horrortitel observer_ kommt am 24.10."));
    }    
    
    @Test
    public void testSlugifyRemoveComma() {
        assertEquals("linux_windows_macosx", slugService.slugify("Linux, Windows, MacOSX"));
    }   
    
    @Test
    public void testSlugifyRemovePeriod() {
        assertEquals("linux_version_von_aspyrs_neustem_horrortitel_observer_kommt_am_2410", slugService.slugify("Linux-Version von Aspyr's neustem Horrortitel observer_ kommt am 24.10."));
    }     

    @Test
    public void testSlugifyRemoveHashtag() {
        assertEquals("ich_bin_ohne_hashtag", slugService.slugify("Ich bin ohne #Hashtag"));
    }        

    @Test
    public void testSlugifyRemoveHashtagAndStrip1() {    
        assertEquals("holarse_wochenend_rückblick_2020_06_drückblick_besiege_verlässt_early_access_luna_shadow_dust", 
        slugService.slugify("Holarse Wochenend-Rückblick 2020-06 #Drückblick - Besiege verlässt Early Access, LUNA The Shadow Dust veröffentlicht, neuer Simutrans-Release, Humble Bundle jetzt auf deutsch, mal wieder ein Indie-Pick uvm."));
    }        

    @Test
    public void testSlugifyRemoveHashtagAndStrip2() {    
        assertEquals("holarse_wochenend_rückblick_2020_05_drückblick_avorion_bald_10_empires_ruins_verspricht_linux", 
        slugService.slugify("Holarse Wochenend-Rückblick 2020-05 #Drückblick - Avorion bald 1.0, Empires in Ruins verspricht Linux, Starcom: Nexus hat Linux, Universim nun in Beta und UnityStation Public Alpha für SpaceStation13-Fans, Icculus aktualisiert Descent uvm."));
    }        
    
    @Test
    public void testSlugifyRemoveHashtagAndStrip3() {    
        assertEquals("holarse_wochenend_rückblick_2020_04_drückblick_daedalic_mit_zwei_neuen_spielen_für_linux_little", 
        slugService.slugify("Holarse Wochenend-Rückblick 2020-04 #Drückblick - Daedalic mit zwei neuen Spielen für Linux, Little Racers STREET erhält spontanen Port dank FNA - Godot Engine 3.2 - NVIDIA stampft Treiber für ältere Karten ein"));
    }        
    
    @Test
    public void testSlugifyRemoveHashtagAndStrip4() {    
        assertEquals("holarse_wochenend_rückblick_3_im_januar_2020_drückblick_ärger_um_rocket_league_half_life_serie", 
        slugService.slugify("Holarse Wochenend-Rückblick 3 im Januar 2020 #Drückblick - Ärger um Rocket League - Half-Life-Serie bis Alyx-Release kostenlos spielbar - Wine 5 und der Lunar Sale startet"));
    }        

    @Test
    public void testSlugifyRemoveHashtagAndStrip5() {
        assertEquals("holarse_wochenend_rückblick_51_im_januar_2020_drückblick_der_erste_und_beste_drückblick_des", slugService.slugify("Holarse Wochenend-Rückblick 51 im Januar 2020 #Drückblick - Der erste und beste Drückblick des Jahres - Open Source-Update-Fest"));
    }

    @Test
    public void testSlugifyRemoveHashtagAndStrip6() {
        assertEquals("linuxspiele_wochenend_rückblick_352019_warzone_2100_mit_release_330_und_rennspiel_yorg_bietet", 
        slugService.slugify("Linuxspiele-Wochenend-Rückblick 35/2019 - Warzone 2100 mit Release 3.3.0 und Rennspiel Yorg bietet lokalen Multiplayer und Rabtte bei der Kalypso-Woche und im RPG-Bundle"));
    }   
    
    @Test
    public void testSlugifyChineseCharacters() {
        assertEquals("holarse_wochenend_rückblick_2021_07_drückblick_新年快乐_stellaris_dreht_mit_neuem_dlc_nemesis_auf", 
            slugService.slugify("Holarse Wochenend-Rückblick 2021-07 #Drückblick - 新年快乐 - Stellaris dreht mit neuem DLC Nemesis auf - X4 erkundet den Geburtsort der Menschheit - Weltraum ohne Ende: StarDrive 2 und SpaceHaven mit neuen Updates - Wir sind Vikinger, eine Millionen Hornträge"));
    }
    
    @Test
    public void testSlugifyRemoveExclamationMark() {
        assertEquals("linuxspiele_adventskalender_2017_heute_ist_weihnachten_verlosung_nur_bis_15_uhr", slugService.slugify("Linuxspiele-Adventskalender 2017: Heute ist Weihnachten - Verlosung nur bis 15 Uhr!"));
    }       
    
    @Test
    public void testSlugifyRemoveBraketLeft() {
        assertEquals("event0", slugService.slugify("Event[0]"));
    }     
    
    @Test
    public void testSlugifyRemoveBraketRight() {
        assertEquals("event0", slugService.slugify("Event[0]"));
    }        
    
    @Test
    public void testSlugifyRemoveCurlyBraketLeft() {
        assertEquals("hallo", slugService.slugify("hallo}{"));
    }     
    
    @Test
    public void testSlugifyRemoveCurlyBraketRight() {
        assertEquals("hallo", slugService.slugify("hallo}{"));
    }     
    
    @Test
    public void testSlugifyRemovePlus() {
        assertEquals("linux_spass", slugService.slugify("Linux + Spass"));
    }       
    
    @Test
    public void testSlugifyRemoveAmpersand() {
        assertEquals("command_conquer_tiberian_sun", slugService.slugify("Command & Conquer - Tiberian Sun"));
    }     

    @Test
    public void testSlugifyRemoveFillwordOf() {
        assertEquals("lords_xulima", slugService.slugify("Lords of Xulima"));
    }
    
    @Test
    public void testSlugifyRemoveFillwordThe() {
        assertEquals("rise_tomb_raider", slugService.slugify("Rise of the Tomb Raider"));
    }   
    
    @Test
    public void testSlugifyRemoveFillwordTo() {
        assertEquals("moon", slugService.slugify("To the Moon"));
    }     
    
    @Test
    public void testSlugifyKeepFillwordAnd() {
        assertEquals("command_and_conquer_alarmstufe_rot", slugService.slugify("Command and Conquer: Alarmstufe Rot"));
    }   
    
    @Test
    public void testSlugifyRemoveTheAtBeginning() {
        assertEquals("dwarves", slugService.slugify("The Dwarves"));
    }  
    
    @Test
    public void testSlugifyRemoveThisAtBeginning() {
        assertEquals("war_mine", slugService.slugify("This War Of Mine"));
    }  
    
    @Test
    public void testSlugifyKeepUmlaut() {
        assertEquals("brütal_legend", slugService.slugify("Brütal Legend"));
    }       
    
}
