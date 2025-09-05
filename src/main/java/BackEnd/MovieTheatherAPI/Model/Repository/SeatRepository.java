package BackEnd.MovieTheatherAPI.Model.Repository;

import BackEnd.MovieTheatherAPI.Model.Entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
}