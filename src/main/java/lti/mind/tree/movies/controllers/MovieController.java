package lti.mind.tree.movies.controllers;

import lti.mind.tree.movies.dtos.MovieDTO;
import lti.mind.tree.movies.entities.Movie;
import lti.mind.tree.movies.entities.enums.FilmRating;
import lti.mind.tree.movies.entities.enums.Genre;
import lti.mind.tree.movies.mappers.MovieMapper;
import lti.mind.tree.movies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping(value="/grouped/year", produces="application/json")
    public ResponseEntity<Page<Map.Entry<Integer, List<MovieDTO>>>> getMoviesGroupedByYear(Pageable pageable) {
        Page<Map.Entry<Integer, List<MovieDTO>>> moviesGroupedByYear = movieService.getMoviesGroupedByYear(pageable);
        return ResponseEntity.ok(moviesGroupedByYear);
    }

    @GetMapping(value="/year/{year}", produces="application/json")
    @ResponseBody
    public ResponseEntity<Page<Movie>> getMoviesByYear(@PathVariable("year") int year, @PageableDefault(size = 20) Pageable pageable) {
        Page<Movie> movies = movieService.findMoviesByYear(year, pageable);
        return ResponseEntity.ok(movies);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Movie createMovie(@RequestBody MovieDTO movieDTO) {
        return movieService.createMovie(movieDTO);
    }

    @GetMapping(value="/search", produces="application/json")
    @ResponseBody
    public ResponseEntity<Page<Movie>> getMoviesByCriteria(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) int releaseYear,
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) FilmRating filmRating,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<Movie> movies = movieService.findMoviesByCriteria(title, releaseYear, genre, filmRating, pageable);
        return ResponseEntity.ok(movies);
    }

    @GetMapping(value="/{movieId}", produces="application/json")
    @ResponseBody
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable UUID movieId) {
        return movieService.findMovieById(movieId)
                .map(movie -> {
                    MovieDTO movieDTO = MovieMapper.INSTANCE.toDTO(movie);
                    return ResponseEntity.ok(movieDTO);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
