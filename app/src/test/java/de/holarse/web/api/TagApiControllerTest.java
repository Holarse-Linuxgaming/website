package de.holarse.web.api;

import de.holarse.backend.db.ApiUser;
import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.backend.view.TagRecommendation;

import static de.holarse.config.RoleUserTypes.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;

import de.holarse.test.TestHelper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class TagApiControllerTest {

    private final static transient Logger log = LoggerFactory.getLogger(TagApiControllerTest.class);

    @Mock
    SearchRepository searchRepositoryMock;
    
    TagApiController controller;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new TagApiController();
    }
    @Test
    @WithAnonymousUser
    public void testRequestWithoutLogin() throws Exception {
        final String searchTerm = "döner";
        controller.searchRepository = searchRepositoryMock;
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/webapi/tags/autocomplete").param("query", searchTerm).with(csrf())).andExpect(status().is(200)); // TODO: Sollte 302 und dann die Login-Seite sein...
        // TODO: Should return redirect to login
    }

    @Test
    @WithMockUser("admin")
    public void testRequestWithLogin() throws Exception {
        final String searchTerm = "döner";

        when(searchRepositoryMock.autocompleteTags(searchTerm)).thenReturn(new ArrayList<>());
        controller.searchRepository = searchRepositoryMock;

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        ResultActions result = mockMvc.perform(get("/webapi/tags/autocomplete").param("query", searchTerm).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(resp -> assertThat("should be json", resp.getResponse().getContentType().equals(MediaType.APPLICATION_JSON_VALUE)));

        result.andExpect(jsonPath("$", hasSize(0)));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void testSingleResultWithLogin() throws Exception {
        controller.searchRepository = searchRepositoryMock;

        final String searchTerm = "döner";
        final List<TagRecommendation> mockResult = List.of(new TagRecommendation("döner", 1));
        
        when(searchRepositoryMock.autocompleteTags(Mockito.anyString())).thenReturn(mockResult);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        ResultActions result = mockMvc.perform(get("/webapi/tags/autocomplete").param("query", searchTerm).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(resp -> assertThat("should be json", resp.getResponse().getContentType().equals(MediaType.APPLICATION_JSON_VALUE)));

        result.andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithAnonymousUser
    public void testSingleResultWithoutLogin() throws Exception {
        controller.searchRepository = searchRepositoryMock;

        final String searchTerm = "döner";
        final List<TagRecommendation> mockResult = List.of(new TagRecommendation("döner", 1));

        when(searchRepositoryMock.autocompleteTags(Mockito.anyString())).thenReturn(mockResult);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        ResultActions result = mockMvc.perform(
                get("/webapi/tags/autocomplete").param("query", searchTerm).
                with(csrf()));

        log.debug("{}", result.andReturn().getResponse().getContentAsString());

        //result.andExpect(status().is3xxRedirection());
    }

}
