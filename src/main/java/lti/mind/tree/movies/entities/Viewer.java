package lti.mind.tree.movies.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Viewer extends Person{
    @ManyToMany
    @JoinTable(
            name = "movie_like",
            joinColumns = @JoinColumn(name = "viewer_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> likedMovies;

    public Viewer(String name, String lastName, List<Movie> likedMovies) {
        super(name, lastName);
        this.likedMovies = likedMovies;
    }
}
