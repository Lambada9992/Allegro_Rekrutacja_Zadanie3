package pl.allegro.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Stars {
    @JsonProperty("stars")
    @Getter
    @Setter
    Integer stars = 0;

}
