package BackEnd.MovieTheatherAPI.Model.Repository.Projection;

import BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity;

public interface MovieProjection {

    Long id();
    String nome();
    String sinopse();
    MovieEntity.Classificacao classificacao();
    Integer duracaoHoras();
    Integer duracaoMinutos();
    MovieEntity.Genero genero();
}
