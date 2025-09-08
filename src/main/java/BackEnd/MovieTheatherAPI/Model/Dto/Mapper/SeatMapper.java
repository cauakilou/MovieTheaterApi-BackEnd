package BackEnd.MovieTheatherAPI.Model.Dto.Mapper;

import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomResponseDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Seat.SeatResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.SeatEntity;

public class SeatMapper {

    public static SeatResponseDto toDto(SeatEntity seat){
        SeatResponseDto dto = new SeatResponseDto();
        dto.setFileira((seat.getFileira()));
        dto.setTipo(String.valueOf(seat.getTipo()));
        dto.setNumero(seat.getNumero());

        return dto;
    }
}
