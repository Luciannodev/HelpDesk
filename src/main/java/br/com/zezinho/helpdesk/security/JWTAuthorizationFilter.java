package br.com.zezinho.helpdesk.security;

import br.com.zezinho.helpdesk.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDetailsServiceImpl userDetailsService;
    private final JWTUtil jwt;

    public JWTAuthorizationFilter(UserDetailsServiceImpl userDetailsService, @Lazy AuthenticationManager authenticationManager, JWTUtil jwt) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.jwt = jwt;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken authToken = null;
            try {
                authToken = getAuthentication(header.substring(7));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (authToken != null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) throws Exception {
        if (jwt.tokenValido(token)) {
            String username = jwt.getUsername(token);
            UserDetails details = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
        }
        throw new Exception("Usuario não permitido");
    }

}












