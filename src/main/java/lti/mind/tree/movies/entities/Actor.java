package lti.mind.tree.movies.entities;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
public class Actor extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToMany
    Set<Movie> movies;
}
