package BackEnd.MovieTheatherAPI.Controller;


import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.ClientMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.Client.ClientCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Client.ClientResponseDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.PageableMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import BackEnd.MovieTheatherAPI.Model.Dto.User.UserResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.ClientEntity;
import BackEnd.MovieTheatherAPI.Model.Exception.ErrorMessage;
import BackEnd.MovieTheatherAPI.Model.Jwt.JwtUserDetails;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.ClientProjection;
import BackEnd.MovieTheatherAPI.Model.Service.ClientService;
import BackEnd.MovieTheatherAPI.Model.Service.UserService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Clientes", description = "Faz todas as operações relacioanas a criação de perfis de cliente")
@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @Operation(summary = "Cria um novo cliente",description = "Cria um novo cliente no DB",
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
    @PreAuthorize("hasRole ('CLIENT')")
    @PostMapping
    public ResponseEntity<ClientResponseDto> adicionarClient(@RequestBody @Valid ClientCreateDto dto,
                                                               @AuthenticationPrincipal JwtUserDetails userDetails){
        ClientEntity cliente = ClientMapper.toClient(dto);
        cliente.setUser(userService.search(userDetails.getId()));
        clientService.save(cliente);
        return ResponseEntity.status(201).body(ClientMapper.toDto(cliente));
    }


    @Operation(summary = "Recupera um cliente",description = "Recupera um cliente utilizando o ID",
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
                    @ApiResponse(responseCode = "404", description = "ID Invalido",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDto> getById(@PathVariable Long id){
        ClientEntity cliente = clientService.search(id);
        return ResponseEntity.ok(ClientMapper.toDto(cliente));
    }

    @Operation(summary = "Recupera uma lista de clientes",description = "Recupera uma lista de clientes no DB e as clolca",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",description = "recurso Recuperado com sucesso",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "usuario não logado no sistema",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "usuario sem autorização",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAll(@Parameter(hidden = true)@PageableDefault(size = 5, sort = {"name"}) Pageable pageable){
        Page<ClientProjection> listaDeClientes = clientService.buscarTodos(pageable);
        return ResponseEntity.ok((PageableMapper.pageableToDTO(listaDeClientes)));
    }

    @Operation(summary = "Recebe detalhes de um cliente",description = "Recupera os detalhes sobre um cliente",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Detalhes obtidos",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Usuario não Logado",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuario sem autorização",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Credenciais Invalidas",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping(path = "/detalhes")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> getDetalhess(@AuthenticationPrincipal JwtUserDetails userDetails){
        ClientEntity cliente = clientService.buscarPorUsuarioId(userDetails.getId());
        return ResponseEntity.ok((ClientMapper.toDto(cliente)));
    }

}
