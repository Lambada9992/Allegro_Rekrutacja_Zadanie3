package pl.allegro.github.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.allegro.github.dto.Repository;
import pl.allegro.github.dto.Stars;
import pl.allegro.github.service.UserService;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
public class UserControllerTest {

    @MockBean
    @Qualifier("userServiceMain")
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return valid list of repos")
    void getRepos() throws Exception {
        Repository repo1 = new Repository();
        Repository repo2 = new Repository();
        Repository repo3 = new Repository();
        repo1.setName("n1");
        repo2.setName("n2");
        repo3.setName("n3");
        repo1.setStars(1);
        repo2.setStars(2);
        repo3.setStars(3);
        List<Repository> repositoryList = Arrays.asList(repo1, repo2, repo3);
        Mockito.when(userService.getRepositories(Mockito.anyString())).thenReturn(repositoryList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/a/repos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("n1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stars", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("n2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].stars", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is("n3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].stars", Matchers.is(3)));
    }

    @Test
    @DisplayName("Should return valid number of stars")
    void getStars() throws Exception {
        Stars stars = new Stars();
        stars.setStars(7);
        Mockito.when(userService.getStars(Mockito.anyString())).thenReturn(stars);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/a/stars"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", Matchers.is(7)));
    }

    @Test
    @DisplayName("getRepos should return code 500")
    void getRepos1() throws Exception {
        Mockito.when(userService.getRepositories(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/a/repos"))
                .andExpect(MockMvcResultMatchers.status().is(500));
    }

    @Test
    @DisplayName("get stars should return code 500")
    void getStars1() throws Exception {
        Mockito.when(userService.getStars(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/a/stars"))
                .andExpect(MockMvcResultMatchers.status().is(500));

    }


}