package de.holarse.web.controller;

import de.holarse.test.TestHelper;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class StaticControllerTest {
    
    StaticController controller;
    
    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new StaticController();
    }
    
    @Test
    public void testDatenschutzPage() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/datenschutz")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("layouts/bare"));
        assertEquals("sites/static/datenschutz", TestHelper.getContentView(result));
    }        
    
    @Test
    public void testCommunityRegelnPage() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/community-regeln")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("layouts/bare"));
        assertEquals("sites/static/community-regeln", TestHelper.getContentView(result));
    }  

        @Test
    public void testImprintPage() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/impressum")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("layouts/bare"));
        assertEquals("sites/static/impressum", TestHelper.getContentView(result));
    }  
    
}
