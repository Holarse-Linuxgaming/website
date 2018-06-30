package de.holarse.renderer.plain;

import de.holarse.renderer.Renderer;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class PlainRendererTest {
    
    private Renderer renderer;
    
    @Before
    public void before() {
        renderer = new PlainRenderer();
    }

    @Test
    public void testRenderNull() {
        assertEquals("", renderer.render(null));
    }
    
    @Test
    public void testRenderEmpty() {
        assertEquals("", renderer.render(""));
    }
    
    //@Test
    public void testRenderNormalText() {
        final String input = "Hallo, Welt! Das sind normale Satzzeichen. Die müssen bleiben.";
        final String output = input;
        
        assertEquals(output, renderer.render(input));
    }  
    
    //@Test
    public void testRenderNormalTextWithLink() {
        final String input = "Hallo, Welt! Klick auf diesen [http://www.prolinux.de Link].";
        final String output = "Hallo, Welt! Klick auf diesen Link.";
        
        assertEquals(output, renderer.render(input));
    }       
    
    
}
