package lti.mind.tree.movies.services;

import lti.mind.tree.movies.dtos.MovieDTO;
import lti.mind.tree.movies.entities.Movie;
import lti.mind.tree.movies.entities.enums.FilmRating;
import lti.mind.tree.movies.entities.enums.Genre;
import lti.mind.tree.movies.mappers.MovieMapper;
import lti.mind.tree.movies.repositories.MovieRepository;
import lti.mind.tree.movies.repositories.ViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ViewerRepository viewerRepository;

    @Transactional
    public Page<Map.Entry<Integer, List<MovieDTO>>> getMoviesGroupedByYear(Pageable pageable) {
        List<Movie> movies = movieRepository.findAll();
        Map<Integer, List<MovieDTO>> groupedByYear = movies.stream().collect(Collectors.groupingBy(Movie::getReleaseYear,
                Collectors.mapping(movie -> MovieMapper.INSTANCE.toDTO(movie), // Include likes count
                        Collectors.toList())));

        List<Map.Entry<Integer, List<MovieDTO>>> entries = new ArrayList<>(groupedByYear.entrySet());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), entries.size());

        if (start >= entries.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, entries.size());
        }
        List<Map.Entry<Integer, List<MovieDTO>>> pageContent = entries.subList(start, end);
        return new PageImpl<>(pageContent, pageable, entries.size());
    }

    public Page<Movie> findMoviesByYear(int year, Pageable pageable) {
        return movieRepository.findAllByReleaseYear(year, pageable);
    }

    public Movie createMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setTitleOnScreen(movieDTO.getTitleOnScreen());
        movie.setFilmRating(movieDTO.getFilmRating());
        movie.setReleaseYear(movieDTO.getReleaseYear());
        movie.setGenre(movieDTO.getGenre());
        movie.setDuration(movieDTO.getDuration());
        movie.setOverview(movieDTO.getOverview());
        return movieRepository.save(movie);
    }

    public Optional<Movie> findMovieById(UUID movieId) {
        return movieRepository.findById(movieId);
    }

    public Page<Movie> findMoviesByCriteria(String title, int releaseYear, Genre genre, FilmRating filmRating, Pageable pageable) {
        return movieRepository.findByCriteria(title, releaseYear, genre, filmRating, pageable);
    }
}
