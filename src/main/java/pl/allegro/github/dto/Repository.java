package pl.allegro.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
public class Repository {
    @JsonProperty("name")
    @Getter
    @Setter
    String name;

    Integer stars;

    @JsonProperty("stars")
    public Integer getStars() {
        return stars;
    }

    @JsonProperty("stargazers_count")
    public void setStars(Integer stars) {
        this.stars = stars;
    }
}
