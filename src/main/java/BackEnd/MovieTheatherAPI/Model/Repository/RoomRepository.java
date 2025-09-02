package BackEnd.MovieTheatherAPI.Model.Repository;

import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}