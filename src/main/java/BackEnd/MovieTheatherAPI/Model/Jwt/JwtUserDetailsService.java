package BackEnd.MovieTheatherAPI.Model.Jwt;

import BackEnd.MovieTheatherAPI.Model.Entity.UserEntity;
import BackEnd.MovieTheatherAPI.Model.Repository.UserRepository; // Importar o repositório
import BackEnd.MovieTheatherAPI.Model.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // Mudar de UserService para UserRepository

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity usuario = userRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
        }
        return new JwtUserDetails(usuario);
    }

    public JwtToken getTokenAuthenticated(String email) {
        UserEntity usuario = userRepository.findByEmail(email);
        // Garantir que não retornamos null se o usuário não for encontrado
        if (usuario != null && usuario.getRole() != null) {
            String role = usuario.getRole().name().substring("ROLE_".length());
            return JwtUtils.createToken(email, role);
        }
        throw new UsernameNotFoundException("Não foi possível gerar token para o email: " + email);
    }
}