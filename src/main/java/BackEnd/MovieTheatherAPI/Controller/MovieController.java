package BackEnd.MovieTheatherAPI.Controller;

import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.PageableMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie.MovieCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie.MovieResponseDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.MovieMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.User.UserResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity;
import BackEnd.MovieTheatherAPI.Model.Exception.ErrorMessage;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.MovieProjection;
import BackEnd.MovieTheatherAPI.Model.Service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "Filmes", description = "Faz todas as operações relacioanas a criação de Filmes, atualizações e controle")
@RestController
@RequestMapping("/api/v1/filmes")
@RequiredArgsConstructor
public class MovieController {
    
    private final MovieService movieService;

    @Operation(summary = "Cria um novo filme",description = "Cria um novo filme no DB",
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
    @PreAuthorize("hasRole ('ADMIN')")
    @PostMapping
    public ResponseEntity<MovieResponseDto> adicionarMovie(@RequestBody @Valid MovieCreateDto dto){
        MovieEntity movie = MovieMapper.toMovie(dto);
        movieService.criarFilme(movie);
        return ResponseEntity.status(201).body(MovieMapper.toDto(movie));
    }

    @Operation(summary = "Recuperar um filme pelo seu ID",description = "Cria um novo cliente no DB",
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
                    @ApiResponse(responseCode = "404", description = "ID não existe",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole ('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> buscarFilmePorId(@PathVariable Long id){
        return ResponseEntity.status(200).body(MovieMapper.toDto(movieService.buscarFilmes(id)));
    }

    @Operation(summary = "Recuperar a lista de filmes que existem",description = "Recupera toda a lista de filmes que se tem",
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
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole ('ADMIN')")
    @GetMapping
    public ResponseEntity<PageableDto> getAll(@Parameter(hidden = true)@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable){
        Page<MovieProjection> listaDeFilmes = movieService.buscarTodos(pageable);
        return ResponseEntity.ok((PageableMapper.pageableToDTO(listaDeFilmes)));
    }

    @Operation(summary = "Recupera um filme pelo status",description = "Recupera um filme pelo seu status e como o fazer ",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "recursos recuperado com sucessos",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "usuario não logado no sistema",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "usuario sem autorização",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole ('ADMIN')")
    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<MovieResponseDto> mudarStatus(
            @PathVariable Long id,
            @PathVariable String status){
        return ResponseEntity.ok().body(MovieMapper.toDto(movieService.trocarStatus(id,status)));
    }

    @Operation(summary = "Deleta um filme",description = "Deleta um filme do DB pelo ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "recurso Deletado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "usuario não logado no sistema",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "usuario sem autorização",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "ID Invalido",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarFilme(@PathVariable Long id){
        movieService.deletarFilme(id);
        return ResponseEntity.ok(null);
    }






}
