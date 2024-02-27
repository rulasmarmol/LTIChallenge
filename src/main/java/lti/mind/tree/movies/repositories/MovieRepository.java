package lti.mind.tree.movies.repositories;

import lti.mind.tree.movies.entities.Movie;
import lti.mind.tree.movies.entities.enums.FilmRating;
import lti.mind.tree.movies.entities.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
    Page<Movie> findAllByReleaseYear(int releaseYear, Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE " +
            "(:title IS NULL OR m.title = :title) AND " +
            "(:releaseYear IS NULL OR m.releaseYear = :releaseYear) AND " +
            "(:genre IS NULL OR m.genre = :genre) AND " +
            "(:filmRating IS NULL OR m.filmRating = :filmRating)")
    Page<Movie> findByCriteria(@Param("title") String title,
                               @Param("releaseYear") int releaseYear,
                               @Param("genre") Genre genre,
                               @Param("filmRating") FilmRating filmRating,
                               Pageable pageable);

}
