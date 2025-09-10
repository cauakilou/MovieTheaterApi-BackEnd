package BackEnd.MovieTheatherAPI.Model.Dto.Session;

import BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie.MovieResponseDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SessionResponseDto {
    private RoomResponseDto Sala;
    private MovieResponseDto Filme;
    private String inicio;
    private String data;
    private String termino;
}
