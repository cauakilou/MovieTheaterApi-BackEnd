package BackEnd.MovieTheatherAPI.Model.Dto.Mapper;

import BackEnd.MovieTheatherAPI.Model.Dto.User.UserCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.User.UserResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;

public class UserMapper {

    private UserMapper(){}

    public static UserEntity toUser(UserCreateDto createDTO){
        return new ModelMapper().map(createDTO,UserEntity.class);

    }


    public static UserResponseDto toUserDto(UserEntity usuario) {
        String role = usuario.getRole().name().substring("ROLE_".length());
        PropertyMap<UserEntity, UserResponseDto> props = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UserResponseDto.class);
    }

    public static List<UserResponseDto> toListDTO(List<UserEntity> usuarios){
        return usuarios.stream().map(UserMapper::toUserDto).toList();

    }
}

