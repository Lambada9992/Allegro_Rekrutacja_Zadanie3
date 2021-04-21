package pl.allegro.github.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.allegro.github.dto.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserReposAndStarsController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{user}/repos")
    public List<Repository> getRepos(@PathVariable("user") String user){
        String url = String.format("https://api.github.com/users/%s/repos",user);
        List<Repository> userRepositories;
        try {
            userRepositories = Arrays.asList(restTemplate.getForObject(url,Repository[].class));
        }catch (HttpClientErrorException e){
            return null;
        }

        return userRepositories;
    }

}
