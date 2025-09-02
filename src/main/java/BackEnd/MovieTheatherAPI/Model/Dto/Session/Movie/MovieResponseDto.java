package BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String duracaoFormatada;
    private Genero genero;
    private Status status;

    }
