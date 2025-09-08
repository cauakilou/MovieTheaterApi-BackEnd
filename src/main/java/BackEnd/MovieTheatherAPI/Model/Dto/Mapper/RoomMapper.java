package BackEnd.MovieTheatherAPI.Model.Dto.Mapper;

import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomResponseDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Seat.SeatResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.SeatEntity;
import BackEnd.MovieTheatherAPI.Model.Utils.SeatsCreate;

import java.util.List;
import java.util.stream.Collectors;

public class RoomMapper {

    public static RoomEntity ToRoomEntity(RoomCreateDto dto){
        RoomEntity room = new RoomEntity();
        room.setTipo(RoomEntity.TipoSala.valueOf(dto.getTipo()));
        room.setNome(dto.getNome());

        // ** A LÓGICA DE ASSOCIAÇÃO CORRIGIDA **
        List<SeatEntity> novosAssentos = SeatsCreate.criarAssentos(dto.getLinha(), dto.getColuna());
        novosAssentos.forEach(room::addSeat); // Usa o novo método auxiliar

        return room;
    }

    public static RoomResponseDto toDto(RoomEntity room){
        RoomResponseDto dto = new RoomResponseDto();
        dto.setNome(room.getNome());
        dto.setTipo(String.valueOf(room.getTipo()));

        if (room.getSeats() != null) {
            dto.setSeats(room.getSeats().stream().map(seatEntity -> {
                SeatResponseDto seatDto = new SeatResponseDto();
                seatDto.setId(seatEntity.getId());
                seatDto.setFileira(seatEntity.getFileira());
                seatDto.setNumero(seatEntity.getNumero());
                seatDto.setTipo(seatEntity.getTipo().name());
                return seatDto;
            }).collect(Collectors.toList()));
        }

        return dto;
    }
}