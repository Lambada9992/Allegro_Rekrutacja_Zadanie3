package pl.allegro.github.service;

import org.springframework.web.client.HttpClientErrorException;
import pl.allegro.github.dto.Repository;
import pl.allegro.github.dto.Stars;
import pl.allegro.github.dto.User;

import java.util.List;

public interface UserService {
    public List<Repository> getRepositories(String user);

    public Stars getStars(String user);

    public User getUser(String user) throws HttpClientErrorException;
}
