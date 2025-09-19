package BackEnd.MovieTheatherAPI.Model.Service;

import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.SessionMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.SessionResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.SeatEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.SessionEntity;
import BackEnd.MovieTheatherAPI.Model.Exception.EntityNotFoundException;
import BackEnd.MovieTheatherAPI.Model.Repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
            throw new DataIntegrityViolationException(String.format("sessão Invalida %s",sessao));
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
    public SeatEntity recuperarAssento(Long id){
        return roomService.buscarAssentoPorId(id);
    }


    @Transactional(readOnly = true)
    public Page<SessionResponseDto> buscarTodos(Pageable pageable, Long movieId, String genre, String date) {

        // THE FIX: Replace the deprecated 'where(null)' with the modern 'allOf()'
        Specification<SessionEntity> spec = Specification.allOf();

        if (movieId != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("movie").get("id"), movieId));
        }

        if (genre != null && !genre.isEmpty()) {
            MovieEntity.Genero genreEnum = MovieEntity.Genero.valueOf(genre.toUpperCase());
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("movie").get("genero"), genreEnum));
        }

        if (date != null && !date.isEmpty()) {
            LocalDate localDate = LocalDate.parse(date);
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("data"), localDate));
        }

        Page<SessionEntity> entityPage = sessionRepository.findAll(spec, pageable);
        return entityPage.map(SessionMapper::toDto);
    }

    public void exclude(Long id) {
        sessionRepository.delete(sessionRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(String.format("Elemento com ID %s não encontrado",id))
        ));
    }
}
