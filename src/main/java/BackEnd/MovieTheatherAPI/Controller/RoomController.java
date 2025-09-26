package BackEnd.MovieTheatherAPI.Controller;

import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.PageableMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.RoomMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomResponseDto;
import BackEnd.MovieTheatherAPI.Model.Dto.User.UserResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Exception.ErrorMessage;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.MovieProjection;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.RoomProjection;
import BackEnd.MovieTheatherAPI.Model.Service.RoomService;
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

@Tag(name = "Salas", description = "Faz todas as operações relacioanas a criação de salas e suas cadeiras")
@RequestMapping("/api/v1/salas")
@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @Operation(summary = "Cria uma nova sala",description = "Cria um novo Sala e cadeiras para o preenchimento",
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
    public ResponseEntity<RoomResponseDto> adicionarRoom(@RequestBody @Valid RoomCreateDto dto) {
        RoomEntity room = RoomMapper.ToRoomEntity(dto);
        roomService.criarSala(room);
        return ResponseEntity.status(201).body(RoomMapper.toDto(room));
    }

    @Operation(summary = "recupera uma sala",description = "Recupera uma sala com base no seu ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "recurso criado com sucesso",
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
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> buscarRoom(@PathVariable Long id){
        return ResponseEntity.ok().body(RoomMapper.toDto(roomService.buscarPorId(id)));
    }

    @Operation(summary = "Recupera uma lista de salas",description = "recupera uma lista de salas",
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
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PageableDto> buscarRooms(@Parameter(hidden = true)@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable){
        Page<RoomProjection> listaDeFilmes = roomService.buscarTodos(pageable);
        return ResponseEntity.ok((PageableMapper.pageableToDTO(listaDeFilmes)));
    }

    @Operation(summary = "Deleta um filme",description = "Deleta um filme no DB pelo seu ID",
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
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRooms(@PathVariable Long id){
        roomService.deletar(id);
        return ResponseEntity.noContent().build();
    }




}
