package lti.mind.tree.movies.controllers;

import lti.mind.tree.movies.dtos.ViewerDTO;
import lti.mind.tree.movies.entities.Viewer;
import lti.mind.tree.movies.services.MovieService;
import lti.mind.tree.movies.services.ViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/viewers")
public class ViewerController {

    @Autowired
    private ViewerService viewerService;

    @Autowired
    private MovieService movieService; // Assume this service exists and has a method to find movies by ID

    @PostMapping
    public ResponseEntity<Viewer> createViewer(@RequestBody Viewer viewer) {
        Viewer createdViewer = viewerService.createViewer(viewer);
        return new ResponseEntity<>(createdViewer, HttpStatus.CREATED);
    }

    // Endpoint to add a like to a movie
    @PostMapping("/{viewerId}/likes/{movieId}")
    public ResponseEntity<?> likeMovie(@PathVariable UUID viewerId, @PathVariable UUID movieId) {
        return movieService.findMovieById(movieId)
                .map(movie -> {
                    viewerService.likeMovie(viewerId, movie);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ViewerDTO>> getAllViewers() {
        List<ViewerDTO> viewers = viewerService.findAllViewers();
        return ResponseEntity.ok(viewers);
    }

    @DeleteMapping("/{viewerId}/likes/{movieId}")
    public ResponseEntity<?> removeLike(@PathVariable UUID viewerId, @PathVariable UUID movieId) {
        boolean success = viewerService.removeLike(viewerId, movieId);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
