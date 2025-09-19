package BackEnd.MovieTheatherAPI.Controller;

import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.PageableMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.SessionMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.SessionCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.SessionResponseDto;
import BackEnd.MovieTheatherAPI.Model.Service.SessionService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/sessoes")
@RestController
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<PageableDto> buscarTodasSessoes(
            @Parameter(hidden = true) @PageableDefault(size = 5, sort = {"horario"}) Pageable pageable,
            // 2. Add the optional request parameters for filtering
            @RequestParam(required = false) Long movieId,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String date
    ) {
        // 3. Pass the parameters to the service method
        Page<SessionResponseDto> listaDeSessoes = sessionService.buscarTodos(pageable, movieId, genre, date);
        return ResponseEntity.ok(PageableMapper.pageableToDTO(listaDeSessoes));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        sessionService.exclude(id);
        return ResponseEntity.noContent().build();
    }
}

