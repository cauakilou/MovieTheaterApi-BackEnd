package BackEnd.MovieTheatherAPI.Model.Service;


import BackEnd.MovieTheatherAPI.Model.Entity.UserEntity;
import BackEnd.MovieTheatherAPI.Model.Exception.EntityNotFoundException;
import BackEnd.MovieTheatherAPI.Model.Exception.UserNameUniqueViolationException;
import BackEnd.MovieTheatherAPI.Model.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;


    @Transactional
    public UserEntity save(UserEntity user){
        try {
            return userRepository.save(user);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UserNameUniqueViolationException(String.format("Email '%s' já cadastrado", user.getEmail()));
        }
    }

    @Transactional
    public UserEntity search(long id){
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Id invalido"));
    }


    public UserEntity buscarPorEmail(String email) {
        return  userRepository.findByEmail(email);
    }


    public UserEntity.Role buscarRolePorEmail(String email) {
        return  userRepository.findByEmail(email).getRole();
    }
}
