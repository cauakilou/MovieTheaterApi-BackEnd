package BackEnd.MovieTheatherAPI.Model.Service;

import BackEnd.MovieTheatherAPI.Model.Entity.UserEntity;
import BackEnd.MovieTheatherAPI.Model.Exception.EntityNotFoundException;
import BackEnd.MovieTheatherAPI.Model.Exception.PasswordInvalidException;
import BackEnd.MovieTheatherAPI.Model.Exception.UserNameUniqueViolationException;
import BackEnd.MovieTheatherAPI.Model.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserEntity save(UserEntity user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Transactional
    public UserEntity editPassword(Long id, String senhaAtual, String novaSenha, String confirmarSenha) {
        if (!novaSenha.equals(confirmarSenha)){
            throw new PasswordInvalidException("nova senha não confere com confirmação de senha");
        }else {
            UserEntity user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("id invalido"));
            if(!passwordEncoder.matches(senhaAtual,user.getPassword())){
                throw new PasswordInvalidException("As senhas não coincidem");
            } else {
                user.setPassword(passwordEncoder.encode(novaSenha));
                return user;
            }

        }


    }
    @Transactional
    public List<UserEntity> buscarTodos() {
    return userRepository.findAll();
    }

}
