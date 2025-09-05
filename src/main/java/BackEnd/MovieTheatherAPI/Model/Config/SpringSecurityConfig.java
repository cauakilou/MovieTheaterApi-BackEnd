package BackEnd.MovieTheatherAPI.Model.Config;

import BackEnd.MovieTheatherAPI.Model.Jwt.JwtAuthenticationEntryPoint;
import BackEnd.MovieTheatherAPI.Model.Jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor; // Usando Lombok para um construtor limpo
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableMethodSecurity
@EnableWebMvc
@Configuration
@RequiredArgsConstructor // Adicionado para injeção via construtor
public class SpringSecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter; // Injetado
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // Injetado

    private static final String[] DOCUMENTATION_OPENAPI = {
            "/docs/index.html",
            "/docs-park.html", "/docs-park/**",
            "/v3/api-docs/**",
            "/swagger-ui-custom.html", "/swagger-ui.html", "/swagger-ui/**",
            "/**.html", "/webjars/**", "/configuration/**", "/swagger-resources/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
                        .requestMatchers(DOCUMENTATION_OPENAPI).permitAll()
                        .anyRequest().authenticated()
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        jwtAuthorizationFilter, // CORRIGIDO: Usando o bean injetado
                        UsernamePasswordAuthenticationFilter.class
                ).exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint) // CORRIGIDO: Usando o bean injetado
                ).build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}