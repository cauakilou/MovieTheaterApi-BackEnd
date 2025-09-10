package BackEnd.MovieTheatherAPI.Model.Repository.Projection;

import BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie.MovieResponseDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface SessionProjection {
    RoomResponseDto getRoom();
    MovieResponseDto getMovie();
    LocalDate getData();
    LocalTime getHorario();
    LocalTime getTermino();
}
