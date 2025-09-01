package BackEnd.MovieTheatherAPI.Model.Service;


import BackEnd.MovieTheatherAPI.Model.Entity.ClientEntity;
import BackEnd.MovieTheatherAPI.Model.Exception.CpfUniqueViolationException;
import BackEnd.MovieTheatherAPI.Model.Repository.ClientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        } catch (DataIntegrityViolationException e){
            throw new CpfUniqueViolationException(
                    String.format("CPF %s não pode ser cadastrado novamente, verifique outra conta",client.getCpf())
            );
        }
    }




}
