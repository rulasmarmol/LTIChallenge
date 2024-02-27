package lti.mind.tree.movies.services;

import lti.mind.tree.movies.dtos.ViewerDTO;
import lti.mind.tree.movies.entities.Movie;
import lti.mind.tree.movies.entities.Viewer;
import lti.mind.tree.movies.mappers.ViewerMapper;
import lti.mind.tree.movies.repositories.MovieRepository;
import lti.mind.tree.movies.repositories.ViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ViewerService {
    @Autowired
    private ViewerRepository viewerRepository;

    @Autowired
    private MovieRepository movieRepository;

    public Viewer createViewer(Viewer viewer) {
        return viewerRepository.save(viewer);
    }

    // Method to add a movie to a viewer's likedMovies
    public Optional<Viewer> likeMovie(UUID viewerId, Movie movie) {
        Optional<Viewer> viewerOptional = viewerRepository.findById(viewerId);

        if (viewerOptional.isPresent()) {
            Viewer viewer = viewerOptional.get();
            boolean alreadyLiked = viewer.getLikedMovies().stream()
                    .anyMatch(likedMovie -> likedMovie.getId().equals(movie.getId()));

            if (!alreadyLiked) {
                viewer.getLikedMovies().add(movie);
                viewerRepository.save(viewer);
                return Optional.of(viewer);
            }
        }
        return Optional.empty();
    }

    public List<ViewerDTO> findAllViewers() {
        List<Viewer> viewers = viewerRepository.findAll();
        List<ViewerDTO> viewerDTOs = viewers.stream()
                .map(viewer -> ViewerMapper.INSTANCE.toDTO(viewer))
                .collect(Collectors.toList());
        return viewerDTOs;
    }

    public boolean removeLike(UUID viewerId, UUID movieId) {
        Optional<Viewer> viewerOptional = viewerRepository.findById(viewerId);
        if (viewerOptional.isPresent()) {
            Viewer viewer = viewerOptional.get();
            boolean removed = viewer.getLikedMovies().removeIf(movie -> movie.getId().equals(movieId));
            if (removed) {
                viewerRepository.save(viewer);
                return true;
            }
        }
        return false;
    }
}
