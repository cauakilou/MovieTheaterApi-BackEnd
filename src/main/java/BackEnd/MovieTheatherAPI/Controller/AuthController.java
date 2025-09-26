package BackEnd.MovieTheatherAPI.Controller;

import BackEnd.MovieTheatherAPI.Model.Dto.User.UserLoginDto;
import BackEnd.MovieTheatherAPI.Model.Dto.User.UserResponseDto;
import BackEnd.MovieTheatherAPI.Model.Exception.ErrorMessage;
import BackEnd.MovieTheatherAPI.Model.Jwt.JwtToken;
import BackEnd.MovieTheatherAPI.Model.Service.AuthService; // Importar o novo serviço
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Tag(name="Autenticação", description = "Recurso para proceder com a autenticação na API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Autentica um usuario",description = "Autentica um usuario",
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
                    @ApiResponse(responseCode = "404", description = "Id inexistente",
                            content = @Content(mediaType = "Application/Json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(
            @RequestBody @Valid UserLoginDto dto, HttpServletRequest request
    ){
        log.info("Processo de autenticação pelo login {}", dto.getEmail());
        try {
            JwtToken token = authService.autenticar(dto);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            log.warn("Credenciais erradas para {}", dto.getEmail());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "credenciais invalidas"));
        }
    }
}