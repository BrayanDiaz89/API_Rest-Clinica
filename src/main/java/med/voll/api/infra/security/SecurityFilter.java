package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired //Es mejor a nivel de constructor que de Autowired (Mejorar eso)
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Obtener el token
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            var nombreUsuario = tokenService.getSubject(token);
            if (nombreUsuario != null) {
                //Token es válido.
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                //Convertir a usuario en autentication, ya que usuario es UserDetails
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities()); //(Forzar inicio sesión)
                SecurityContextHolder.getContext().setAuthentication(authentication); //Contextualizar inicio de sesión del usuario
            }
        }
        //Está es la única forma de hacer el filtro, si el primer filtro no hace la llamada
        //Al siguiente filtro, no retornará los datos.
        filterChain.doFilter(request, response);
    }
}
