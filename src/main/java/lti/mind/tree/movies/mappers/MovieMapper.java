package lti.mind.tree.movies.mappers;

import lti.mind.tree.movies.dtos.MovieDTO;
import lti.mind.tree.movies.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);
    @Mapping(target = "likes", expression = "java(movie.getLikes()!= null ? movie.getLikes().size():0)")
    MovieDTO toDTO(Movie movie);
    @Mapping(target = "likes", ignore = true)
    Movie toEntity(MovieDTO movieDTO);
}
