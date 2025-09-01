package BackEnd.MovieTheatherAPI.Model.Repository;

import BackEnd.MovieTheatherAPI.Model.Entity.ClientEntity;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.ClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    @Query("SELECT c FROM ClientEntity c ORDER BY c.name ASC")
    Page<ClientProjection> findAllPageable(Pageable pageable);

    ClientEntity findByUserId(Long id);
}