package lti.mind.tree.movies.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Character extends Person{
    @EmbeddedId
    ActorMovieKey id;
    @ManyToOne
    private Actor actor;
    @ManyToOne
    private Movie movie;
}
