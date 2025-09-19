package BackEnd.MovieTheatherAPI.Model.Service;

import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.SeatEntity;
import BackEnd.MovieTheatherAPI.Model.Exception.EntityNotFoundException;
import BackEnd.MovieTheatherAPI.Model.Exception.RoomNameUniqueViolationException;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.RoomProjection;
import BackEnd.MovieTheatherAPI.Model.Repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return roomRepository.save(room);
        } catch (DataIntegrityViolationException e ) {
            throw new RoomNameUniqueViolationException(String.format("nome: %s invalido ",room.getNome()));
        }
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
        roomRepository.delete(roomRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(String.format("Sala com id %s encontrado",id))
        ));
    }

    public SeatEntity buscarAssentoPorId(Long id) {

        return roomRepository.findSeatById(id);
    }
}
