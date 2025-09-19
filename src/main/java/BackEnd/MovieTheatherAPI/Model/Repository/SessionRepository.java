package BackEnd.MovieTheatherAPI.Model.Repository;

import BackEnd.MovieTheatherAPI.Model.Entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SessionRepository extends JpaRepository<SessionEntity, Long>, JpaSpecificationExecutor<SessionEntity> {
    /*
    @Query(value = "SELECT DISTINCT s FROM SessionEntity s " +
            "LEFT JOIN FETCH s.room r " +
            "LEFT JOIN FETCH s.movie m " +
            "ORDER BY s.data ASC, s.horario ASC",
            countQuery = "SELECT COUNT(s) FROM SessionEntity s")
    Page<SessionEntity> findAllPageable(Pageable pageable);

     */
}