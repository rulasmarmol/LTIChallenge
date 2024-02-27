package lti.mind.tree.movies.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lti.mind.tree.movies.dtos.MovieDTO;
import lti.mind.tree.movies.entities.Movie;
import lti.mind.tree.movies.services.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getMoviesByYearTest() throws Exception {
        int year = 2021;
        Page<Movie> page = new PageImpl<>(Collections.emptyList());
        when(movieService.findMoviesByYear(eq(year), any())).thenReturn(page);

        mockMvc.perform(get("/movies/year/" + year)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));

        verify(movieService).findMoviesByYear(eq(year), any());
    }

    @Test
    void getMoviesGroupByYearTest() throws Exception {
        Page<Map.Entry<Integer, List<MovieDTO>>> page = new PageImpl<>(Collections.emptyList());
        when(movieService.getMoviesGroupedByYear(any())).thenReturn(page);

        mockMvc.perform(get("/movies/grouped/year")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));

        verify(movieService).getMoviesGroupedByYear(any());
    }

    @Test
    void whenPostMovie_thenCreateMovie() throws Exception {
        MovieDTO movieDTO = new MovieDTO();
        Movie createdMovie = new Movie();

        when(movieService.createMovie(any(MovieDTO.class))).thenReturn(createdMovie);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDTO)))
                .andExpect(status().isCreated());

        verify(movieService).createMovie(any(MovieDTO.class));
    }

    @Test
    void getMoviesByCriteriaTest() throws Exception {
        Movie movie = new Movie();
        Page<Movie> moviePage = new PageImpl<>(Collections.singletonList(movie));

        when(movieService.findMoviesByCriteria(anyString(), anyInt(), any(), any(), any(PageRequest.class)))
                .thenReturn(moviePage);

        mockMvc.perform(get("/movies/search")
                        .param("title", "Test Movie")
                        .param("releaseYear", "2021")
                        .param("genre", "ACTION")
                        .param("filmRating", "PG")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void getMovieByIdTest() throws Exception {
        UUID movieId = UUID.randomUUID();
        Movie movie = new Movie();
        movie.setId(movieId);

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movieId);

        when(movieService.findMovieById(movieId)).thenReturn(Optional.of(movie));

        mockMvc.perform(get("/movies/{movieId}", movieId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(movieId.toString()));

        UUID notFoundMovieId = UUID.randomUUID();
        when(movieService.findMovieById(notFoundMovieId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/movies/{movieId}", notFoundMovieId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

