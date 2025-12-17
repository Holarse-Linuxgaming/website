package de.holarse.web.controller;

import de.holarse.web.api.TagApiControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

public class ProfileControllerTest {

    private final static transient Logger log = LoggerFactory.getLogger(ProfileControllerTest.class);


    ProfileController controller;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new ProfileController();
    }

    @Test
    public void testProtectedProfilePage() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        ResultActions result = mockMvc.perform(get("/profile").with(anonymous()));

        log.debug("{}", result.andReturn().getResponse().getContentAsString());

        //result.andExpect(status().is3xxRedirection());
    }

}
