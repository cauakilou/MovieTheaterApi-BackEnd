package BackEnd.MovieTheatherAPI.Controller;

import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.PageableMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.SessionMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.SessionCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.SessionResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.SessionEntity;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.RoomProjection;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.SessionProjection;
import BackEnd.MovieTheatherAPI.Model.Service.SessionService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/sessoes")
@RestController
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<SessionResponseDto> adicionarSession(@RequestBody @Valid SessionCreateDto dto) {
        return ResponseEntity.status(201).body(SessionMapper.toDto(
                sessionService.criarSessao(SessionMapper.toEntity(dto,
                        sessionService.recuperarSala(dto.getIdSala()),
                        sessionService.recuperarFilme(dto.getIdFilme()
                        )
                )
        )
        )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDto> buscarSession(@PathVariable Long id){
        return ResponseEntity.ok(SessionMapper.toDto(sessionService.buscarSessao(id)));
    }

    @GetMapping
    public ResponseEntity<PageableDto> buscarTodasSessoes(@Parameter(hidden = true)@PageableDefault(size = 5, sort = {"horario"}) Pageable pageable){
        Page<SessionProjection> listaDeFilmes = sessionService.buscarTodos(pageable);
        return ResponseEntity.ok((PageableMapper.pageableToDTO(listaDeFilmes)));
    }

}
