package de.holarse.web.controller;

import de.holarse.backend.db.repositories.NewsRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;

/**
 *
 * @author comrad
 */
public class NewsControllerTest {
    
//    private NewsController controller;
//    
//    @Mock
//    private NewsRepository newsRepositoryMock;
//    
//    @BeforeEach
//    public void setup() throws Exception {
//        MockitoAnnotations.openMocks(this);
//        controller = new NewsController();
//        controller.newsRepository = newsRepositoryMock;
//    }
//    
//    @Test
//    public void testIndex() throws Exception {
//        when(newsRepositoryMock.findFrontpageItems(PageRequest.of(1, 5))).thenReturn(new ArrayList<>());
//        
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();        
//        mockMvc.perform(get("/news")).andExpect(status().isOk());
//    }     
//    
//    @Test
//    public void testShowExisting() throws Exception {
//        when(newsRepositoryMock.findBySlug(ArgumentMatchers.anyString())).thenReturn();
//        
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//        mockMvc.perform(get("/news/hello-world")).andExpect(status().isOk());
//    }      
//    
//    @Test
//    public void testShowNotExisting() throws Exception {
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//        mockMvc.perform(get("/news/i-do-not-exist")).andExpect(status().isNotFound());
//    }          
//
//    @Test
//    @WithAnonymousUser    
//    public void testShowNotPublishedAsUser() throws Exception {
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//        mockMvc.perform(get("/news/unpublished-news")).andExpect(status().isOk());
//    }              
//    
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void testShowNotPublishedAsAdmin() throws Exception {
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//        mockMvc.perform(get("/news/published-news")).andExpect(status().isOk());
//    }                  
    
}
