package BackEnd.MovieTheatherAPI.Controller;

import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.PageableMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.SessionMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.SessionCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.SessionResponseDto;
import BackEnd.MovieTheatherAPI.Model.Dto.User.UserResponseDto;
import BackEnd.MovieTheatherAPI.Model.Exception.ErrorMessage;
import BackEnd.MovieTheatherAPI.Model.Service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Sessões", description = "Controller destinado a todas as operações de ")
@RequestMapping("/api/v1/sessoes")
@RestController
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @Operation(summary = "Cria uma nova Sessão",description = "Cria um nova Sessão, ligando um filme a uma sala em um horario especifico",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",description = "recurso criado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "usuario não logado no sistema",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "usuario sem autorização",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Credenciais Invalidas",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
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

    @Operation(summary = "Busca uma sessão",description = "Busca uma sessão pelo seu ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "recurso recuperado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "usuario não logado no sistema",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "usuario sem autorização",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "ID invalido",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDto> buscarSession(@PathVariable Long id){
        return ResponseEntity.ok(SessionMapper.toDto(sessionService.buscarSessao(id)));
    }

    @Operation(summary = "busca todas as sessões",description = "busca todas as sessões ",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "representa a pagina retornada"
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                            description = "representa o total de elementos por pagina"
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "String", defaultValue = "id,asc")),
                            description = "representa a ordenação da pagina"
                    ),
            },
            responses = {
                    @ApiResponse(responseCode = "200",description = "recurso criado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "usuario não logado no sistema",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "usuario sem autorização",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    })
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

    @Operation(summary = "Deleta um filme",description = "Deleta um filme no DB",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "recurso deletado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "usuario não logado no sistema",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "usuario sem autorização",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "ID invalido",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        sessionService.exclude(id);
        return ResponseEntity.noContent().build();
    }
}

