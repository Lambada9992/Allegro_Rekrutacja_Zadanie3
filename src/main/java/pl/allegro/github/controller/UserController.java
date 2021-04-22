package pl.allegro.github.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.allegro.github.dto.Repository;
import pl.allegro.github.dto.Stars;
import pl.allegro.github.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/{user}/repos")
    public List<Repository> getRepos(@PathVariable("user") String user){
        return userService.getRepositories(user);
    }

    @RequestMapping("/{user}/stars")
    public Stars getStars(@PathVariable("user") String user){
        return userService.getStars(user);
    }

}
