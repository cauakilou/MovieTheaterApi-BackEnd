package BackEnd.MovieTheatherAPI.Model.Repository;

import BackEnd.MovieTheatherAPI.Model.Entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionEntity, Long>{


}
