package BackEnd.MovieTheatherAPI.Model.Dto.Mapper;

import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.SeatEntity;
import BackEnd.MovieTheatherAPI.Model.Service.RoomService;
import BackEnd.MovieTheatherAPI.Model.Utils.AlfabetoStream;
import BackEnd.MovieTheatherAPI.Model.Utils.SeatsCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomMapper {
    //mapper do nome
    public static RoomEntity ToRoomEntity(RoomCreateDto dto){
        RoomEntity room = new RoomEntity();
        room.setTipo(RoomEntity.TipoSala.valueOf(dto.getTipo()));
        room.setNome(dto.getNome());
        room.setSeats(SeatsCreate.criarAssentos(dto.getLinha(),dto.getColuna(),room));
        return room;
    }

    public static RoomResponseDto toDto(RoomEntity room){
        RoomResponseDto dto = new RoomResponseDto();
        dto.setNome(room.getNome());
        dto.setTipo(String.valueOf(room.getTipo()));
        dto.setSeats(room.getSeats());

        return dto;
    }

}
