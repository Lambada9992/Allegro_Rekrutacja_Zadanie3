package pl.allegro.github.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.allegro.github.dto.Repository;
import pl.allegro.github.dto.Stars;
import pl.allegro.github.dto.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Primary
@Service
public class UserServiceMain implements UserService {

    private final Integer pageSize = 100;

    @Getter
    private final String pageApiArguments = "?page=%d&per_page=%d";
    @Getter
    private final String userReposApiUrl = "https://api.github.com/users/%s/repos";
    @Getter
    private final String userApiUrl = "https://api.github.com/users/%s";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * A method that makes a REST request to GitHub and returns List of user repositories
     *
     * @param user nickname/login on GitHub
     * @return List of user repositories/Null when there some error occur
     */
    public List<Repository> getRepositories(String user) {
        List<Repository> userRepositories = new ArrayList<>();
        try {
            int pageIndex = 1;
            int nOfRepos = getUser(user).getNumberOfRepos();

            do {
                String url = String.format(userReposApiUrl + pageApiArguments, user, pageIndex, getPageSize());
                userRepositories.addAll(Arrays.asList(restTemplate.getForObject(url, Repository[].class)));
                pageIndex++;
            } while (userRepositories.size() < nOfRepos);

        } catch (HttpClientErrorException e) {
            return null;
        }
        return userRepositories;
    }


    /**
     * A method that makes a REST request to GitHub and returns user number of stars
     *
     * @param user nickname/login on GitHub
     * @return Star Object which contains(number of user stars)/Null when there some error occur
     */
    public Stars getStars(String user) {
        List<Repository> userRepositories = getRepositories(user);
        if (userRepositories == null) return null;

        Stars result = new Stars();
        result.setStars(userRepositories.stream().mapToInt(repo -> repo.getStars()).sum());

        return result;
    }

    /**
     * A method that makes a REST request to GitHub and returns user info
     *
     * @param user nickname/login on GitHub
     * @return User Object which contains(number of repositories)
     * @throws HttpClientErrorException When the chosen user doesn't exist on GitHub or there was a connection error.
     */
    public User getUser(String user) throws HttpClientErrorException {
        User result = null;
        String url = String.format(userApiUrl, user);
        result = restTemplate.getForObject(url, User.class);
        return result;
    }

    /**
     * Page size in range from (1,100)
     * 100 is an upper limit because it is a max page size on GitHub
     *
     * @return page size
     */
    public Integer getPageSize() {
        if (pageSize < 1) return 1;
        return pageSize > 100 ? 100 : pageSize;
    }
}
