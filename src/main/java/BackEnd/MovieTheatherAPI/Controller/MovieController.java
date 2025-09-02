package BackEnd.MovieTheatherAPI.Controller;

import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.PageableMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie.MovieCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie.MovieResponseDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.MovieMapper;
import BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.MovieProjection;
import BackEnd.MovieTheatherAPI.Model.Service.MovieService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/filmes")
@RequiredArgsConstructor
public class MovieController {
    
    private final MovieService movieService;


    @PreAuthorize("hasRole ('ADMIN')")
    @PostMapping
    public ResponseEntity<MovieResponseDto> adicionarMovie(@RequestBody @Valid MovieCreateDto dto){
        MovieEntity movie = MovieMapper.toMovie(dto);
        movieService.criarFilme(movie);
        return ResponseEntity.status(201).body(MovieMapper.toDto(movie));
    }

    @PreAuthorize("hasRole ('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> buscarFilmePorId(@PathVariable long id){
        return ResponseEntity.status(200).body(MovieMapper.toDto(movieService.buscarFilmes(id)));
    }

    @PreAuthorize("hasRole ('ADMIN')")
    @GetMapping
    public ResponseEntity<PageableDto> getAll(@Parameter(hidden = true)@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable){
        Page<MovieProjection> listaDeFilmes = movieService.buscarTodos(pageable);
        return ResponseEntity.ok((PageableMapper.pageableToDTO(listaDeFilmes)));
    }

    @PreAuthorize("hasRole ('ADMIN')")
    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<MovieResponseDto> MudarOStatus(
            @PathVariable long id,
            @PathVariable String status){;
        return ResponseEntity.ok().body(MovieMapper.toDto(movieService.trocarStatus(id,status)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarFilme(@PathVariable long id){
        movieService.deletarFilme(id);
        return ResponseEntity.ok(null);
    }






}
