package de.holarse.web.controller;

import de.holarse.backend.db.ApiUser;
import de.holarse.backend.db.repositories.ApiUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class WelcomeControllerTest {

    @Mock
    ApiUserRepository apiUserRepositoryMock;
    
    WelcomeController controller;
    
    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new WelcomeController();
        controller.apiUserRepository = apiUserRepositoryMock;
    }
    
    @Test
    public void textIndexPage() throws Exception {
        when(apiUserRepositoryMock.findByLogin("dummy")).thenReturn(Mockito.mock(ApiUser.class));
        
        
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("layouts/landing"));
    }
    
}
