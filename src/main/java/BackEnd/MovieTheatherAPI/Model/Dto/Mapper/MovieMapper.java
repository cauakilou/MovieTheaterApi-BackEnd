package BackEnd.MovieTheatherAPI.Model.Dto.Mapper;


import BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie.MovieCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie.MovieResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity;
import BackEnd.MovieTheatherAPI.Model.Utils.DurationUtils;
import org.modelmapper.ModelMapper;

import java.time.Duration;

public class MovieMapper {
    
    public static MovieEntity toMovie(MovieCreateDto dto){
        MovieEntity movie = new ModelMapper().map(dto, MovieEntity.class);

        movie.setDuracao((DurationUtils.fromMinutes(dto.getDuracao())));
        return movie;
    }

    public static MovieResponseDto toDto(MovieEntity movie){
        MovieResponseDto dto = new ModelMapper().map(movie, MovieResponseDto.class);
        Duration duracaoTotal = movie.getDuracao();
        dto.setDuracaoHoras((int) duracaoTotal.toHours());
        dto.setDuracaoMinutos((int) duracaoTotal.toMinutesPart());
        return dto;
    }
}
