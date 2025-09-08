package BackEnd.MovieTheatherAPI.Model.Service;

import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Exception.EntityNotFoundException;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.RoomProjection;
import BackEnd.MovieTheatherAPI.Model.Repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public RoomEntity criarSala(RoomEntity room){
        return roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public RoomEntity buscarPorId(Long id) {
        return roomRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Sala com id='" + id + "' não encontrada.")
        );
    }

    @Transactional
    public Page<RoomProjection> buscarTodos(Pageable pageable) {
       return roomRepository.findAllPageable(pageable);
    }
    @Transactional
    public void deletar(long id) {
        roomRepository.deleteById(id);
    }
}
