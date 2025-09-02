package BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie;

import BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

import static BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDto {

    private Long id;
    private String nome;
    private String sinopse;
    private Classificacao classificacao;
    private Integer duracaoHoras;
    private Integer duracaoMinutos;
    private Genero genero;

    }
