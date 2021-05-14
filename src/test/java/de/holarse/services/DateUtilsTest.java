/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.services;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.OffsetDateTime;

/**
 *
 * @author comrad
 */
public class DateUtilsTest {
    
    @Test
    public void testFormatAgo1() {
        assertEquals("Gerade eben", DateUtils.formatAgo(OffsetDateTime.now().minusSeconds(30)));
    }

    @Test
    public void testFormatAgo2() {
        assertEquals("Vor 5 Minuten", DateUtils.formatAgo(OffsetDateTime.now().minusMinutes(5)));
    }

    @Test
    public void testFormatAgo3() {
        assertEquals("Vor 35 Minuten", DateUtils.formatAgo(OffsetDateTime.now().minusMinutes(35)));
    }    

    @Test
    public void testFormatAgo4() {
        assertEquals("Vor einer Stunde", DateUtils.formatAgo(OffsetDateTime.now().minusHours(1)));
    }       
    
    @Test
    public void testFormatAgo5() {
        assertEquals("Vor 3 Stunden", DateUtils.formatAgo(OffsetDateTime.now().minusHours(3)));
    }   
    
    @Test
    public void testFormatAgo6() {
        assertEquals("Vor 23 Stunden", DateUtils.formatAgo(OffsetDateTime.now().minusHours(23)));
    }  
    
    @Test
    public void testFormatAgo7() {
        assertEquals("Gestern", DateUtils.formatAgo(OffsetDateTime.now().minusDays(1)));
    }   
    
    @Test
    public void testFormatAgo8() {
        assertEquals("Vor 29 Tagen", DateUtils.formatAgo(OffsetDateTime.now().minusDays(29)));
    }     
    
    @Test
    public void testFormatAgo9() {
        assertEquals("Vor einem Monat", DateUtils.formatAgo(OffsetDateTime.now().minusDays(35)));
    }   
    
    @Test
    public void testFormatAgo10() {
        assertEquals("Vor 2 Monaten", DateUtils.formatAgo(OffsetDateTime.now().minusMonths(3))); // Der heutige Tag zählt mit
    }     
    
    @Test
    public void testFormatAgo10a() {
        assertEquals("Vor 3 Monaten", DateUtils.formatAgo(OffsetDateTime.now().minusMonths(3).minusDays(1)));
    }     

    @Test
    public void testFormatAgo11() {
        assertEquals("Vor 11 Monaten", DateUtils.formatAgo(OffsetDateTime.now().minusMonths(11)));
    }     

    @Test
    public void testFormatAgo12() {
        assertEquals("Vor einem Jahr", DateUtils.formatAgo(OffsetDateTime.now().minusMonths(13)));
    }     
    
    @Test
    public void testFormatAgo13() {
        assertEquals("Vor 2 Jahren", DateUtils.formatAgo(OffsetDateTime.now().minusMonths(25)));
    }     
}
