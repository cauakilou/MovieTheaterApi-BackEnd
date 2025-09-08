package BackEnd.MovieTheatherAPI.Model.Repository;

import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.ClientProjection;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.RoomProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    @Query("SELECT c FROM RoomEntity c ORDER BY c.nome ASC")
    Page<RoomProjection> findAllPageable(Pageable pageable);

    RoomEntity findByNome(String roomName);
}