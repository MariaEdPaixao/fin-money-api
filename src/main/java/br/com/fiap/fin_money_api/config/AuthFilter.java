package br.com.fiap.fin_money_api.config;

import br.com.fiap.fin_money_api.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Override
                                    //oq o usuario ta pedindo   //se mandou o token no cabecalho
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //validar o header
        var header = request.getHeader("Authorization");
        if(header == null){
            filterChain.doFilter(request, response);
            return;
        }

        //validar Bearer
        if(!header.startsWith("Bearer ")){
            response.setStatus(401);
            //escrevendo algo na resposta
            response.getWriter().write("""
                {"message": "Authorization deve iniciar com Bearer"}        
            """);
            return;
        }

        //verificar se JWT está correto
        // bem formado? já expirou? assinatura correta?
        var jwt = header.replace("Bearer ", "");
        var user = tokenService.getUserFromToken(jwt);

        System.out.println(user);

        // se passar tudo, autenticar o usuário

        //autenticar o usuario

        //seta o usuario
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //segue em frente
        filterChain.doFilter(request, response);
    }
}
