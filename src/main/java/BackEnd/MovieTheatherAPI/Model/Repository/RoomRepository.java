package BackEnd.MovieTheatherAPI.Model.Repository;

import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}