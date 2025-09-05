package BackEnd.MovieTheatherAPI.Controller;


import BackEnd.MovieTheatherAPI.Model.Exception.ErrorMessage;
import BackEnd.MovieTheatherAPI.Model.Jwt.JwtToken;
import BackEnd.MovieTheatherAPI.Model.Jwt.JwtUserDetailsService;
import BackEnd.MovieTheatherAPI.Model.Dto.User.UserLoginDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Tag(name="Autenticação", description = "Recurso para proceder com a autenticação na API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class AuthController {
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;



    @PostMapping("/auth")
    public ResponseEntity<Object> autenticar(
            @RequestBody @Valid UserLoginDto dto, HttpServletRequest request
    ){
        log.info("Processo de autenticação pelo login {}",dto.getEmail());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getEmail(),dto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = detailsService.getTokenAuthenticated(dto.getEmail());
            return ResponseEntity.ok(token);
        }catch (AuthenticationException e){
            log.warn("Credenciais erradas para {}", dto.getEmail());
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
                "credenciais invalidas"));
    }


}

