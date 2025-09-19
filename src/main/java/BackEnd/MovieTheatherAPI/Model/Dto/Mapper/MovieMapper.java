package BackEnd.MovieTheatherAPI.Model.Dto.Mapper;

import BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie.MovieCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie.MovieResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity;
import BackEnd.MovieTheatherAPI.Model.Utils.DurationUtils;
import org.modelmapper.ModelMapper;

import java.time.Duration;

public class MovieMapper {

    // This method is fine, no changes needed here.
    public static MovieEntity toMovie(MovieCreateDto dto){
        MovieEntity movie = new ModelMapper().map(dto, MovieEntity.class);
        movie.setDuracao((DurationUtils.fromMinutes(dto.getDuracao())));
        return movie;
    }

    public static MovieResponseDto toDto(MovieEntity movie){
        if (movie == null) {
            return null;
        }

        // --- Start of the minimal change ---
        // We replace the ModelMapper line with this manual mapping.
        MovieResponseDto dto = new MovieResponseDto();
        dto.setId(movie.getId());
        dto.setNome(movie.getNome());
        dto.setSinopse(movie.getSinopse());
        dto.setStatus(movie.getStatus());
        dto.setClassificacao(movie.getClassificacao());
        dto.setGenero(movie.getGenero());
        dto.setDuracaoFormatada(movie.getDuracao().toString());

        // Add any other fields from MovieEntity that you want in the DTO
        // --- End of the minimal change ---

        // The rest of your original code stays exactly the same.
        Duration duracao = movie.getDuracao();
        if (duracao != null) {
            long horas = duracao.toHours();
            long minutos = duracao.toMinutesPart();
            dto.setDuracaoFormatada(String.format("%d:%02d", horas, minutos));
        }

        return dto;
    }
}