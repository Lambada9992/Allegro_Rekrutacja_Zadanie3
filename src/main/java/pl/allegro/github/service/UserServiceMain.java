package pl.allegro.github.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.allegro.github.dto.Repository;
import pl.allegro.github.dto.Stars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Primary
@Service
public class UserServiceMain implements UserService{

    @Getter
    private final String pageApiArguments= "?page=%d&per_page=%d";
    private final Integer pageSize = 50;
    @Getter
    private final String userReposApiUrl = "https://api.github.com/users/%s/repos";

    @Autowired
    private RestTemplate restTemplate;

    public List<Repository> getRepositories(String user){
        List<Repository> userRepositories = new ArrayList<>();
        try {
            int pageIndex = 1;
            while (userRepositories.size()==(pageIndex-1)*getPageSize()) {
                String url = String.format(userReposApiUrl+pageApiArguments,user,pageIndex,getPageSize());
                userRepositories.addAll(Arrays.asList(restTemplate.getForObject(url, Repository[].class)));
                pageIndex++;
            }
        }catch (HttpClientErrorException e){
            return null;
        }
        return userRepositories;
    }

    public Stars getStars(String user){
        List<Repository> userRepositories = getRepositories(user);
        if (userRepositories==null) return null;

        Stars result = new Stars();
        result.setStars(userRepositories.stream().mapToInt(repo -> repo.getStars()).sum());

        return result;
    }

    public Integer getPageSize() {
        //page size in range from (1,100) 100 is a limit on github
        if(pageSize<1) return 1;
        return pageSize>100?100:pageSize;
    }
}
