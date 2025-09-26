package BackEnd.MovieTheatherAPI.Model.Service;

import BackEnd.MovieTheatherAPI.Model.Dto.User.UserLoginDto;
import BackEnd.MovieTheatherAPI.Model.Jwt.JwtToken;
import BackEnd.MovieTheatherAPI.Model.Jwt.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    public JwtToken autenticar(UserLoginDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        authenticationManager.authenticate(authenticationToken);
        return detailsService.getTokenAuthenticated(dto.getEmail());
    }
}