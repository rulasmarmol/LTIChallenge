package lti.mind.tree.movies.entities.enums;

public enum Genre {

    ACTION (1, "Action"),
    COMEDY(2, "Comedy"),
    DOCUMENTARY(3, "Documentary"),
    DRAMA(4, "Drama"),
    FANTASY(5, "Fantasy"),
    HORROR(6, "Horror"),
    MUSICAL(7, "Musical"),
    MYSTERY(8, "Mystery"),
    ROMANCE(9, "Romance"),
    SCIENCE_FICTION(10, "Science Fiction"),
    THRILLER(11, "Thriller"),
    WESTERN(12, "Western"),
    DARK_COMEDY(13, "Dark Comedy");

    private final int id;

    private final String name;

    Genre(int id, String name){
        this.id = id;
        this.name = name;
    }
}
