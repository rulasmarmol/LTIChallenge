package lti.mind.tree.movies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ViewerDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private String name;
    private String lastName;
    private List<MovieDTO> likedMovies;
}
