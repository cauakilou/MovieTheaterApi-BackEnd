package BackEnd.MovieTheatherAPI.Model.Repository.Projection;

import org.springframework.beans.factory.annotation.Value;

public interface RoomProjection {
    long getId();
    String getNome();
    String getTipo();

    // Usa SpEL para calcular o tamanho da lista de assentos da entidade
    @Value("#{target.seats.size()}")
    int getCapacidade();
}
