package pl.allegro.github.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.allegro.github.dto.Repository;
import pl.allegro.github.dto.User;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserServiceMainTest {

    @InjectMocks
    private UserServiceMain userServiceMain;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    @DisplayName("Should return null list of repositories if the Http exception occur while getting repositories")
    void ShouldReturnNullListIfTheHttpExceptionOccurWhileGettingRepositories() {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(User.class)))
                .thenReturn(new User(3));
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(Repository[].class)))
                .thenThrow(HttpClientErrorException.class);

        Assertions.assertNull(userServiceMain.getRepositories("a"));
    }

    @Test
    @DisplayName("Should return null list of repositories if the Http exception occur while getting user")
    void ShouldReturnNullListIfTheHttpExceptionOccurWhileGettingUser() {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(User.class)))
                .thenThrow(HttpClientErrorException.class);
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(Repository[].class)))
                .thenReturn(new Repository[]{});

        Assertions.assertNull(userServiceMain.getRepositories("a"));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 49, 50, 51, 101, 303})
    @DisplayName("Should return valid list of repositories")
    void ShouldReturnValidListOfRepositories(int numberOfRepos) {
        List<Repository> repos = new ArrayList<>();
        String user = "a";

        for (int i = 0; i < numberOfRepos; i++) {
            repos.add(new Repository(Integer.toString(i), i));
        }

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(User.class)))
                .thenReturn(new User(numberOfRepos));

        for (int i = 1; i <= (repos.size() / userServiceMain.getPageSize()) + 2; i++) {

            String url = String.format(userServiceMain.getUserReposApiUrl() + userServiceMain.getPageApiArguments()
                    , user, i, userServiceMain.getPageSize());

            List<Repository> sublist = repos.subList(Math.min((i - 1) * userServiceMain.getPageSize(), repos.size()),
                    Math.min(i * userServiceMain.getPageSize(), repos.size()));

            Mockito.when(restTemplate.getForObject(Mockito.eq(url), Mockito.eq(Repository[].class)))
                    .thenReturn(sublist.toArray(new Repository[sublist.size()]));
        }


        List<Repository> response = userServiceMain.getRepositories(user);

        Assertions.assertEquals(response, repos);
    }

    @Test
    @DisplayName("Should return page size between 1 and 100")
    void shouldReturnPageSizeBetween1and100() {
        Assertions.assertTrue(userServiceMain.getPageSize() <= 100);
        Assertions.assertTrue(userServiceMain.getPageSize() >= 1);
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 49, 50, 51, 102, 303})
    @DisplayName("Should return valid number of stars")
    void ShouldReturnValidNumberOfStars(int numberOfRepos) {
        List<Repository> repos = new ArrayList<>();
        String user = "a";
        int sum = 0;

        for (int i = 0; i < numberOfRepos; i++) {
            repos.add(new Repository(Integer.toString(i), i));
            sum += i;
        }

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(User.class)))
                .thenReturn(new User(numberOfRepos));

        for (int i = 1; i <= (repos.size() / userServiceMain.getPageSize()) + 2; i++) {

            String url = String.format(userServiceMain.getUserReposApiUrl() + userServiceMain.getPageApiArguments()
                    , user, i, userServiceMain.getPageSize());

            List<Repository> sublist = repos.subList(Math.min((i - 1) * userServiceMain.getPageSize(), repos.size()),
                    Math.min(i * userServiceMain.getPageSize(), repos.size()));

            Mockito.when(restTemplate.getForObject(Mockito.eq(url), Mockito.eq(Repository[].class)))
                    .thenReturn(sublist.toArray(new Repository[sublist.size()]));
        }

        Assertions.assertEquals(sum, userServiceMain.getStars(user).getStars());
    }

    @Test
    @DisplayName("Should return null stars If the Http exception occur while getting repositories")
    void shouldReturnNullStarIfHttpExceptionOccurWhileGettingRepos() {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(User.class)))
                .thenReturn(new User(3));

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(Repository[].class)))
                .thenThrow(HttpClientErrorException.class);

        Assertions.assertNull(userServiceMain.getStars("a"));
    }

    @Test
    @DisplayName("Should return null stars If the Http exception occur while getting user")
    void shouldReturnNullStarIfHttpExceptionOccurWhileGettingUser() {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(User.class)))
                .thenThrow(HttpClientErrorException.class);

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(Repository[].class)))
                .thenReturn(new Repository[]{});

        Assertions.assertNull(userServiceMain.getStars("a"));
    }

}