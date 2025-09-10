package BackEnd.MovieTheatherAPI.Model.Repository;

import BackEnd.MovieTheatherAPI.Model.Entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
}