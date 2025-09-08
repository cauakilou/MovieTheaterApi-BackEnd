package BackEnd.MovieTheatherAPI.Model.Dto.Session.Room;

import BackEnd.MovieTheatherAPI.Model.Dto.Session.Seat.SeatResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.SeatEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomResponseDto {


    private String nome;
    private String tipo;
    private List<SeatResponseDto> seats;


}
