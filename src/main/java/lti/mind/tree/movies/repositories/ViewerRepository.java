package lti.mind.tree.movies.repositories;

import lti.mind.tree.movies.entities.Viewer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ViewerRepository extends JpaRepository<Viewer, UUID> {
}
