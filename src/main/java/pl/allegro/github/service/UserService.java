package pl.allegro.github.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.allegro.github.dto.Repository;
import pl.allegro.github.dto.Stars;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private final String userReposApiUrl = "https://api.github.com/users/%s/repos";

    @Autowired
    private RestTemplate restTemplate;

    public List<Repository> getRepositories(String user){
        String url = String.format(userReposApiUrl,user);
        List<Repository> userRepositories;
        try {
            userRepositories = Arrays.asList(restTemplate.getForObject(url,Repository[].class));
        }catch (HttpClientErrorException e){
            return null;
        }

        return userRepositories;
    }

    public Stars getStars(String user){
        List<Repository> userRepositories = getRepositories(user);
        if (userRepositories==null) return null;

        Stars result = new Stars();
        userRepositories.forEach(repo -> {
            result.setStars(result.getStars() + repo.getStars());
        });

        return result;
    }

}
