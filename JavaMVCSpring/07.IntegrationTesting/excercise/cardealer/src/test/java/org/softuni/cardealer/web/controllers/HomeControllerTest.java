package org.softuni.cardealer.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    private static final String INDEX_URL = "/";
    private static final String HOME_URL = "/home";

    @Autowired
    private MockMvc mockMvc;



    @Test
    public void index_returnCorrectView() throws Exception {
        mockMvc
                .perform(get(INDEX_URL))
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser("spring")
    public void home_returnCorrectView() throws Exception {
        mockMvc
                .perform(get(HOME_URL))
                .andExpect(view().name("home"));
    }
}