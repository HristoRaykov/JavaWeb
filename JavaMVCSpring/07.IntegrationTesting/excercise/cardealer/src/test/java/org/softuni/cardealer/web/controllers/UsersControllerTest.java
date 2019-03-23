package org.softuni.cardealer.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UsersControllerTest {

    private static final String LOGIN_URL = "/users/login";
    private static final String REGISTER_URL = "/users/register";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;


    @Before
    public void setUp() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void login_returnsCorrectView() throws Exception {
        mockMvc.perform(get(LOGIN_URL))
                .andExpect(view().name("login"));
    }

    @Test
    public void register_returnCorrectView() throws Exception {
        mockMvc.perform(get(REGISTER_URL))
                .andExpect(view().name("register"));
    }

    @Test
    public void register_saveCorrectUser_Success() throws Exception {
        //act
        mockMvc
                .perform(post(REGISTER_URL)
                .param("username","Pesho")
                .param("password","1")
                .param("confirmPassword","1")
                .param("email","pesho@gmail.com")
                );

        //assert
        assertEquals(1, userRepository.count());
    }


    @Test
    public void register_redirectCorrect() throws Exception {
        mockMvc
                .perform(post(REGISTER_URL)
                        .param("username","Pesho")
                        .param("password","1")
                        .param("confirmPassword","1")
                        .param("email","pesho@gmail.com")
                )
        .andExpect(redirectedUrl("login"));
    }

}