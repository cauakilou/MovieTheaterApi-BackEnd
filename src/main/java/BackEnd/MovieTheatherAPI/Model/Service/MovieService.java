package BackEnd.MovieTheatherAPI.Model.Service;

import BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity.Status;
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
            throw new MovieAlreadyExist(String.format("Filme %s já cadastrado ",filme.getNome()));
        }
    }


    @Transactional
    public MovieEntity buscarFilmes(long id){
            return movieRepository.findById(id).orElseThrow(()
            -> new EntityNotFoundException("Id não encontrado"));
    }

    @Transactional
    public MovieEntity trocarStatus(long id, String status){
        MovieEntity movie = movieRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(String.format("Filme com id %s encontrado",id))
        );

        try {
            // 2. Converte a String para o enum Status. Se a string for inválida, lança uma exceção.
            MovieEntity.Status novoStatus = MovieEntity.Status.valueOf(status.toUpperCase());

            // 3. Atualiza o status do filme
            movie.setStatus(novoStatus);

            // 4. O Spring Data JPA salva a entidade automaticamente ao final do método @Transactional
            // Não é necessário chamar movieRepository.save(filme) explicitamente aqui.
            return movie;
        } catch (IllegalArgumentException e) {
            // 5. Lança uma exceção personalizada se o status for inválido
            throw new RuntimeException(String.format("Status '%s' é inválido. Valores aceitos: EM_CARTAZ, EM_BREVE, FORA_DE_CARTAZ", status));
        }

    }

    public void deletarFilme(long id) {
        movieRepository.delete(movieRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(String.format("Filme com id %s encontrado",id))
        ));
    }
}
