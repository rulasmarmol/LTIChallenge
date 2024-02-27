package lti.mind.tree.movies.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ActorMovieKey implements Serializable {

    @Column(name = "actor_id")
    private UUID actorId;

    @Column(name = "movie_id")
    private UUID movieId;
}
