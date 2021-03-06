package pl.allegro.github.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.allegro.github.dto.Repository;
import pl.allegro.github.dto.Stars;
import pl.allegro.github.service.UserService;

import java.util.List;

/**
 * The Class that is responsible for handling REST requests
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    @Qualifier("userServiceMain")
    private UserService userService;

    /**
     * Method responsible for responding to GetRequest
     *
     * @param user nickname/login on GitHub
     * @return List of repositories(names,stars)
     */
    @GetMapping("/{user}/repos")
    public ResponseEntity<List<Repository>> getRepos(@PathVariable("user") String user) {
        List<Repository> repositoryList = userService.getRepositories(user);
        if (repositoryList == null) {
            return ResponseEntity.status(500).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(repositoryList);
        }

    }

    /**
     * Method responsible for responding to GetRequest
     *
     * @param user nickname/login on GitHub
     * @return Star object which contains number of stars
     */
    @GetMapping("/{user}/stars")
    public ResponseEntity<Stars> getStars(@PathVariable("user") String user) {
        Stars stars = userService.getStars(user);
        if (stars == null) {
            return ResponseEntity.status(500).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(stars);
        }

    }

}
