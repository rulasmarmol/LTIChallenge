package lti.mind.tree.movies.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lti.mind.tree.movies.entities.enums.FilmRating;
import lti.mind.tree.movies.entities.enums.Genre;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String titleOnScreen;
    @Enumerated(EnumType.STRING)
    private FilmRating filmRating;
    private int releaseYear;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private int duration;
    private String overview;
    @ManyToOne(targetEntity = Director.class, cascade = CascadeType.ALL)
    private Director director;
    @ManyToMany(targetEntity = Actor.class)
    private Set<Actor> cast;
    @ManyToMany(mappedBy = "likedMovies")
    private List<Viewer> likes;

    public Movie(String title,
                 String titleOnScreen,
                 FilmRating filmRating,
                 int releaseYear,
                 Genre genre,
                 int duration,
                 String overview,
                 Director director){
        this.title = title;
        this.titleOnScreen = titleOnScreen;
        this.filmRating = filmRating;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.duration = duration;
        this.overview = overview;
        this.director = director;
    }
}
