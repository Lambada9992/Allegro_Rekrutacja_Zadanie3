package pl.allegro.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class User {

    int numberOfRepos = 0;

    @JsonProperty("numberOfRepos")
    public int getNumberOfRepos() {
        return numberOfRepos;
    }

    @JsonProperty("public_repos")
    public void setNumberOfRepos(int numberOfRepos) {
        this.numberOfRepos = numberOfRepos;
    }
}
