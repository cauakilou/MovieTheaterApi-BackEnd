package BackEnd.MovieTheatherAPI.Model.Service;

import BackEnd.MovieTheatherAPI.Model.Dto.Session.SessionResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.SessionEntity;
import BackEnd.MovieTheatherAPI.Model.Exception.EntityNotFoundException;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.SessionProjection;
import BackEnd.MovieTheatherAPI.Model.Repository.SessionRepository;
import BackEnd.MovieTheatherAPI.Model.Utils.TimeConverter;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final RoomService roomService;
    private final MovieService movieService;

    @Transactional
    public SessionEntity criarSessao(SessionEntity sessao){
          try {
            return sessionRepository.save(sessao);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(String.format("sessão Invalida"));
        }

    }
    @Transactional(readOnly = true)
    public RoomEntity recuperarSala(long id){
        return roomService.buscarPorId(id);
    }
    @Transactional(readOnly = true)
    public MovieEntity recuperarFilme(long id){
        return movieService.buscarFilmes(id);
    }
    @Transactional(readOnly = true)
    public SessionEntity buscarSessao(long id) {
        return sessionRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(String.format("sessão com id %s não encontrado",id))
        );
    }


    @Transactional(readOnly = true)
    public Page<SessionProjection> buscarTodos(Pageable pageable) {
        return sessionRepository.findAllPageable(pageable);
    }
}
