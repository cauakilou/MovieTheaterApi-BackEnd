package BackEnd.MovieTheatherAPI.Model.Repository.Projection;

import BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity;
import java.time.Duration;

public interface MovieProjection {

    Long getId();
    String getNome();
    String GetSinopse();
    String GetPoster();
    MovieEntity.Classificacao getClassificacao();

    // Altere para retornar o Integer de minutos, ou mantenha Duration se preferir
    // Integer getDuracaoEmMinutos();
    Duration getDuracao();

    MovieEntity.Genero getGenero();
    MovieEntity.Status getStatus();

}