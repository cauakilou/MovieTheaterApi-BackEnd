package BackEnd.MovieTheatherAPI.Controller;


import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.ClientMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.Client.ClientCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Client.ClientResponseDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.PageableMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.PageableDTO;
import BackEnd.MovieTheatherAPI.Model.Entity.ClientEntity;
import BackEnd.MovieTheatherAPI.Model.Jwt.JwtUserDetails;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.ClientProjection;
import BackEnd.MovieTheatherAPI.Model.Service.ClientService;
import BackEnd.MovieTheatherAPI.Model.Service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @PreAuthorize("hasRole ('CLIENT')")
    @PostMapping
    public ResponseEntity<ClientResponseDto> adicionarClient(@RequestBody @Valid ClientCreateDto dto,
                                                               @AuthenticationPrincipal JwtUserDetails userDetails){
        ClientEntity cliente = ClientMapper.toClient(dto);
        cliente.setUser(userService.search(userDetails.getId()));
        clientService.save(cliente);
        return ResponseEntity.status(201).body(ClientMapper.toDto(cliente));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDto> getById(@PathVariable Long id){
        ClientEntity cliente = clientService.search(id);
        return ResponseEntity.ok(ClientMapper.toDto(cliente));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDTO> getAll(@Parameter(hidden = true)@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable){
        Page<ClientProjection> listaDeClientes = clientService.buscarTodos(pageable);
        return ResponseEntity.ok((PageableMapper.pageableToDTO(listaDeClientes)));
    }

}
