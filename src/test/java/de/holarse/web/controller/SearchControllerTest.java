/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.holarse.web.controller;

import de.holarse.backend.db.datasets.SearchResultView;
import de.holarse.backend.db.repositories.SearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

/**
 *
 * @author comrad
 */
public class SearchControllerTest {
    
    SearchController controller;
    
    @Mock
    SearchRepository searchRepository;
    
    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new SearchController();
    }
    
    @Test
    public void testSearchForm() throws Exception {
        final String searchTerm = "döner";
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(post("/search").param("query", searchTerm).with(csrf())).andExpect(status().is3xxRedirection());
                //assertEquals("sites/search/results", TestHelper.getContentView(result));
    }      
    
    
    
    
}
