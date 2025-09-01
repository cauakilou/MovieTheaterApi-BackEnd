package BackEnd.MovieTheatherAPI.Model.Dto.Mapper;

import BackEnd.MovieTheatherAPI.Model.Dto.Client.ClientCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Client.ClientResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.ClientEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {


    public static ClientEntity toClient(ClientCreateDto dto){
        return new ModelMapper().map(dto, ClientEntity.class);
    }

    public static ClientResponseDto toDto(ClientEntity client){
        return new ModelMapper().map(client, ClientResponseDto.class);
    }


}


