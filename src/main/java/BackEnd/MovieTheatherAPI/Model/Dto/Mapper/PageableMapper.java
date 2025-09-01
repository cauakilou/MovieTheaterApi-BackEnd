package BackEnd.MovieTheatherAPI.Model.Dto.Mapper;

import BackEnd.MovieTheatherAPI.Model.Dto.PageableDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class PageableMapper {
    public static PageableDTO pageableToDTO(Page page){
        return new ModelMapper().map(page, PageableDTO.class);
    }
    private PageableMapper(){}
}