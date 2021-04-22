package pl.allegro.github.service;

import pl.allegro.github.dto.Repository;
import pl.allegro.github.dto.Stars;

import java.util.List;

public interface UserService {
    public List<Repository> getRepositories(String user);
    public Stars getStars(String user);
}
