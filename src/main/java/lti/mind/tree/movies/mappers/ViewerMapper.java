package lti.mind.tree.movies.mappers;

import lti.mind.tree.movies.dtos.ViewerDTO;
import lti.mind.tree.movies.entities.Viewer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = MovieMapper.class)
public interface ViewerMapper {
    ViewerMapper INSTANCE = Mappers.getMapper(ViewerMapper.class);
    ViewerDTO toDTO(Viewer viewer);
    Viewer toEntity(ViewerDTO viewerDTO);
}
