package BackEnd.MovieTheatherAPI.Controller;

import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.UserMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.User.UserCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.User.UserPasswordDto;
import BackEnd.MovieTheatherAPI.Model.Dto.User.UserResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.UserEntity;
import BackEnd.MovieTheatherAPI.Model.Exception.ErrorMessage;
import BackEnd.MovieTheatherAPI.Model.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Tag(name = "Usuarios", description = "Contém todos os usuarios do sistema, sejam admin ou client, e as operações como leitura registro, alteração e a recuperação")
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "criar um novo usuario",description = "cria um usuario",
            responses = {
                    @ApiResponse(responseCode = "201",description = "recurso criado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "usuario e-mail já Cadastrado no sistema",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "dados de entrada invalidos",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))

            })
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserCreateDto dto){
        UserEntity user = userService.save(UserMapper.toUser(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserDto(user));
    }
    @Operation(summary = "Recuperar um usuario",description = "Recupera um usuario existente",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "recurso recuperado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "usuario não logado no sistema",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuario sem autorização",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Id inexistente",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))

            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR ( hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<UserResponseDto> search(@PathVariable Long id){
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(UserMapper.toUserDto(userService.search(id)));
    }
    @Operation(summary = "Atualizar a senha", description = "Recupera um usuario existente",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "recurso Atualizado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "401", description = "usuario não logado no sistema",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuario sem autorização",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Id inexistente",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "As senhas não coincidem",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))

            })
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT') AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id
            , @Valid @RequestBody UserPasswordDto dto) {
        userService.editPassword(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmarSenha());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Recuperar um usuario",description = "Recupera um usuario existente",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "recurso recuperado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "usuario não logado no sistema",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuario sem autorização",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserEntity> listaDeUsers = userService.buscarTodos();
        return ResponseEntity.ok(UserMapper.toListDTO(listaDeUsers) );
    }
}
