package lti.mind.tree.movies.entities.enums;

public enum FilmRating {
    G(1, "G – General Audiences"),
    PG(2, "PG – Parental Guidance Suggested"),
    PG13(3, "PG-13 – Parents Strongly Cautioned"),
    R(4, "R – Restricted"),
    NC17(5, "NC-17 – Adults Only");

    private final int id;

    private final String name;
    FilmRating(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
