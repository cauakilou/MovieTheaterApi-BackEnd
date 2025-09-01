package BackEnd.MovieTheatherAPI.Controller;


import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.ClientMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.Client.ClientCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Client.ClientResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.ClientEntity;
import BackEnd.MovieTheatherAPI.Model.Service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PreAuthorize("hasRole('ADMIN') OR hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<ClientResponseDto> create(@RequestBody @Valid ClientCreateDto dto){
        ClientEntity client = clientService.save(ClientMapper.toClient(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ClientMapper.toDto(client));
    }

}
