package lti.mind.tree.movies.services;

import lti.mind.tree.movies.dtos.ViewerDTO;
import lti.mind.tree.movies.entities.Viewer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ViewerServiceIntegrationTest {

    @Autowired
    private ViewerService viewerService;

    @Autowired
    private MovieService movieService;

    @Test
    public void givenViewerService_whenSaveAndRetreiveMovie_thenOK() {
        Viewer viewer = viewerService.createViewer(new Viewer(
                "John",
                "Due",
                null
        ));
        List<ViewerDTO> viewers = viewerService.findAllViewers();
        assertTrue(viewers.size() ==1);
    }

    @Test
    @Sql(scripts = {"/import_movies.sql", "/import_viewers.sql"})
    public void givenMovieService_whenLikeMovie_thenOk() {
        Optional<Viewer> viewer =
                viewerService.likeMovie(
                        UUID.fromString("2efc2e8b-68f4-4144-82d5-a7f5c1cf5b89"),
                        movieService.findMovieById(UUID.fromString("b2271682-ba61-4cd1-8e1d-3c3c79c44cbe")).get());
        assertTrue(viewer.isPresent());
    }

    @Test
    @Sql(scripts = {"/import_movies.sql", "/import_viewers.sql"})
    public void givenMovieService_whenLikeMovie_thenFail() {
        Optional<Viewer> viewer = viewerService.likeMovie(UUID.randomUUID(),
                movieService.findMovieById(UUID.fromString("b2271682-ba61-4cd1-8e1d-3c3c79c44cbe")).get());
        assertFalse(viewer.isPresent());
    }

    @Test
    @Sql(scripts = {"/import_movies.sql", "/import_viewers.sql"})
    public void givenMovieService_whenRemoveLikeMovie_thenOk() {
        viewerService.likeMovie(
                        UUID.fromString("2efc2e8b-68f4-4144-82d5-a7f5c1cf5b89"),
                        movieService.findMovieById(UUID.fromString("b2271682-ba61-4cd1-8e1d-3c3c79c44cbe")).get());

        boolean removed =
                viewerService.removeLike(
                        UUID.fromString("2efc2e8b-68f4-4144-82d5-a7f5c1cf5b89"),
                        UUID.fromString("b2271682-ba61-4cd1-8e1d-3c3c79c44cbe"));
        assertTrue(removed);
    }

    @Test
    @Sql(scripts = {"/import_movies.sql", "/import_viewers.sql"})
    public void givenMovieService_whenRemoveLikeMovie_thenFail() {
        boolean removed =
                viewerService.removeLike(
                        UUID.randomUUID(),
                        UUID.randomUUID());
        assertFalse(removed);    }
}
