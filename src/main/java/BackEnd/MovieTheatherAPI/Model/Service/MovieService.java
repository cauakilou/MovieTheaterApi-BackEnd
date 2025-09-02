package BackEnd.MovieTheatherAPI.Model.Service;

import BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity;
import BackEnd.MovieTheatherAPI.Model.Exception.CpfUniqueViolationException;
import BackEnd.MovieTheatherAPI.Model.Exception.EntityNotFoundException;
import BackEnd.MovieTheatherAPI.Model.Exception.MovieAlreadyExist;
import BackEnd.MovieTheatherAPI.Model.Repository.MovieRepository;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.MovieProjection;
import BackEnd.MovieTheatherAPI.Model.Repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Page<MovieProjection> buscarTodos(Pageable pageable) {
        return movieRepository.findAllPageable(pageable);
    }

    @Transactional
    public MovieEntity criarFilme(MovieEntity filme){
        try {
            return movieRepository.save(filme);
        } catch (DataIntegrityViolationException e) {
            throw new MovieAlreadyExist("Filme já cadastrado");
        }
    }

    @Transactional
    public MovieEntity buscarFilmes(long id){
            return movieRepository.findById(id).orElseThrow(()
            -> new EntityNotFoundException("Id não encontrado"));
    }
}
