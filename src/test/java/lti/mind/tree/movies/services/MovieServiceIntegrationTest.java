package lti.mind.tree.movies.services;

import lti.mind.tree.movies.dtos.MovieDTO;
import lti.mind.tree.movies.entities.Movie;
import lti.mind.tree.movies.entities.enums.FilmRating;
import lti.mind.tree.movies.entities.enums.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MovieServiceIntegrationTest {

    @Autowired
    private MovieService movieService;

    @Test
    public void givenMovieService_whenSaveAndRetreiveMovie_thenOK() {
        Movie movie = movieService.createMovie(new MovieDTO(null, "Dune",
                        "Dune: Part One",
                        FilmRating.PG13,
                        2021,
                        Genre.SCIENCE_FICTION,
                        155,
                        "Paul Atreides arrives on Arrakis after his father accepts the stewardship of the dangerous planet. However, chaos ensues after a betrayal as forces clash to control melange, a precious resource.",
                        0));
        Optional<Movie> foundEntity = movieService.findMovieById(movie.getId());

        assertNotNull(foundEntity);
        assertEquals(movie.getReleaseYear(), foundEntity.get().getReleaseYear());
    }

    @Test
    @Sql(scripts = "/import_movies.sql")
    public void givenMovieService_whenGetMoviesByYear_thenOK() {
        Page<Map.Entry<Integer, List<MovieDTO>>> moviesByYear = movieService.getMoviesGroupedByYear(PageRequest.of(0,10));
        assertTrue(moviesByYear.getContent().size()>1);
    }

    @Test
    public void givenMovieService_whenGetMoviesByYear_thenFail() {
        Page<Map.Entry<Integer, List<MovieDTO>>> moviesByYear = movieService.getMoviesGroupedByYear(PageRequest.of(0,10));
        assertFalse(moviesByYear.getContent().size()>0);
    }

    @Test
    @Sql(scripts = "/import_movies.sql")
    public void givenMovieService_whenfindMoviesByYear_thenOk() {
        Page<Movie> moviesByYear = movieService.findMoviesByYear(2021,PageRequest.of(0,10));
        assertTrue(moviesByYear.getContent().size()==2);
    }

    @Test
    @Sql(scripts = "/import_movies.sql")
    public void givenMovieService_whenfindMoviesByYear_thenFail() {
        Page<Movie> moviesByYear = movieService.findMoviesByYear(2007,PageRequest.of(0,10));
        assertTrue(moviesByYear.getContent().size()==0);
    }

    @Test
    @Sql(scripts = "/import_movies.sql")
    public void givenMovieService_whenfindByCriteria_thenOk() {
        Page<Movie> moviesByCriteria = movieService.findMoviesByCriteria(null,2021,null,null,PageRequest.of(0,10));
        assertTrue(moviesByCriteria.getContent().size()==2);
    }
}
