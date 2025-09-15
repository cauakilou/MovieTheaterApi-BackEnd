package BackEnd.MovieTheatherAPI.Model.Dto.Mapper;

import BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie.MovieResponseDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomResponseDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.SessionCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.SessionResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.SessionEntity;
import BackEnd.MovieTheatherAPI.Model.Utils.TimeConverter;

import java.time.LocalDate;
import java.time.LocalTime;

public class SessionMapper {

    public static SessionEntity toEntity(SessionCreateDto dto, RoomEntity room, MovieEntity movie){
        SessionEntity session = new SessionEntity();
        session.setData(LocalDate.parse(dto.getData()));
        session.setHorario(LocalTime.parse(dto.getInicio()));
        session.setRoom(room);
        session.setMovie(movie);
        session.setTermino(TimeConverter.termino(session.getMovie().getDuracao(),session.getHorario()));
        return session;
    }

    public static SessionResponseDto toDto(SessionEntity session){
        return new SessionResponseDto(
                session.getId(),
                RoomMapper.toDto(session.getRoom()),
                MovieMapper.toDto(session.getMovie()),
                String.valueOf(session.getData()),
                String.valueOf(session.getHorario()),
                String.valueOf(session.getTermino()));
    }
}
