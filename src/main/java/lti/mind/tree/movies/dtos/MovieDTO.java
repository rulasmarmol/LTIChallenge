package lti.mind.tree.movies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lti.mind.tree.movies.entities.enums.FilmRating;
import lti.mind.tree.movies.entities.enums.Genre;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private String title;
    private String titleOnScreen;
    private FilmRating filmRating;
    private int releaseYear;
    private Genre genre;
    private int duration;
    private String overview;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int likes;
}
