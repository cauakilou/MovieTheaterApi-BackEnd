package BackEnd.MovieTheatherAPI.Model.Dto.Mapper;

import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class PageableMapper {
    public static PageableDto pageableToDTO(Page page){
        return new ModelMapper().map(page, PageableDto.class);
    }
    private PageableMapper(){}
}