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
        Duration duracao = movie.getDuracao();
        long horas = duracao.toHours();
        long minutos = duracao.toMinutesPart();
        dto.setDuracaoFormatada(String.format("%d:%02d", horas, minutos));

        return dto;
    }
}
