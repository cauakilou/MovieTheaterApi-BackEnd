package BackEnd.MovieTheatherAPI.Model.Jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService detailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader(JwtUtils.JWT_AUTHORIZATION);

        if(header == null || !header.startsWith(JwtUtils.JWT_BEARER)){
            log.info("JWT token esta nulo, vazio ou não iniciado com Bearer");
            filterChain.doFilter(request,response);
            return;
        }

        final String token = header.substring(JwtUtils.JWT_BEARER.length());

        if(!JwtUtils.isTokenValido(token)){
            log.warn("Token invalido ou expirado");
            filterChain.doFilter(request,response);
            return;
        }
        String email = JwtUtils.getUsernameFromToken(token);
        toAuthentication(request,email);
        filterChain.doFilter(request,response);
    }

    private void toAuthentication(HttpServletRequest request, String email) {
        UserDetails userDetails = detailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null,userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }


}