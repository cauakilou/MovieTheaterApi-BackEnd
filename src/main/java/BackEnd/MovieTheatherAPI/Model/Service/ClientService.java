package BackEnd.MovieTheatherAPI.Model.Service;


import BackEnd.MovieTheatherAPI.Model.Entity.ClientEntity;
import BackEnd.MovieTheatherAPI.Model.Exception.CpfUniqueViolationException;
import BackEnd.MovieTheatherAPI.Model.Exception.EntityNotFoundException;
import BackEnd.MovieTheatherAPI.Model.Repository.ClientRepository;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.ClientProjection;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clienteRepository;

    @Transactional
    public ClientEntity save(ClientEntity client){
        try {
            return clienteRepository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUniqueViolationException(
                    String.format("cpf %s Não pode ser cadastrado, já existe",
                            client.getCpf()
                    )
            );
        }

    }

    @Transactional
    public ClientEntity search(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(String.format("cliente com id=%s não encontrado",id))
        );
    }


    @Transactional(readOnly = true)
    public Page<ClientProjection> buscarTodos(Pageable pageable) {
        return clienteRepository.findAllPageable(pageable);

    }
}
