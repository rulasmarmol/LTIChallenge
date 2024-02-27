package lti.mind.tree.movies.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lti.mind.tree.movies.dtos.ViewerDTO;
import lti.mind.tree.movies.entities.Movie;
import lti.mind.tree.movies.entities.Viewer;
import lti.mind.tree.movies.services.MovieService;
import lti.mind.tree.movies.services.ViewerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(ViewerController.class)
class ViewerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ViewerService viewerService;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    private Viewer viewer;
    private Movie movie;
    private ViewerDTO viewerDTO;

    @BeforeEach
    void setUp() {
        viewer = new Viewer();
        viewer.setId(UUID.randomUUID());

        movie = new Movie();
        movie.setId(UUID.randomUUID());

        viewerDTO = new ViewerDTO();
    }

    @Test
    void createViewerTest() throws Exception {
        when(viewerService.createViewer(any(Viewer.class))).thenReturn(viewer);

        mockMvc.perform(post("/viewers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(viewer)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(viewer)));
    }

    @Test
    void likeMovieTest() throws Exception {
        when(movieService.findMovieById(any(UUID.class))).thenReturn(Optional.of(movie));
        when(viewerService.likeMovie(any(UUID.class), any(Movie.class))).thenReturn(Optional.of(viewer));

        mockMvc.perform(post("/viewers/{viewerId}/likes/{movieId}", viewer.getId(), movie.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void getAllViewersTest() throws Exception {
        when(viewerService.findAllViewers()).thenReturn(Collections.singletonList(viewerDTO));

        mockMvc.perform(get("/viewers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(viewerDTO))));
    }

    @Test
    void removeLikeTest() throws Exception {
        when(viewerService.removeLike(any(UUID.class), any(UUID.class))).thenReturn(true);

        mockMvc.perform(delete("/viewers/{viewerId}/likes/{movieId}", viewer.getId(), movie.getId()))
                .andExpect(status().isOk());
    }
}
