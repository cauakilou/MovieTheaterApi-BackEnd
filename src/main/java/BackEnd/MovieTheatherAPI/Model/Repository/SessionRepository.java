package BackEnd.MovieTheatherAPI.Model.Repository;

import BackEnd.MovieTheatherAPI.Model.Entity.SessionEntity;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.SessionProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    @Query("SELECT s FROM SessionEntity s JOIN FETCH s.movie JOIN FETCH s.room ORDER BY s.data ASC, s.horario ASC")
    Page<SessionProjection> findAllPageable(Pageable pageable);
}