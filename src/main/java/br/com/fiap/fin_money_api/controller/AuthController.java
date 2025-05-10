package br.com.fiap.fin_money_api.controller;

import br.com.fiap.fin_money_api.model.Credentials;
import br.com.fiap.fin_money_api.model.Token;
import br.com.fiap.fin_money_api.model.User;
import br.com.fiap.fin_money_api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Token login(@RequestBody Credentials credentials){
        var auth = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password());
        var user = (User) authenticationManager.authenticate(auth).getPrincipal(); //traz o usuario logado, se as credenciais estiverem erradas traz um 401

        return tokenService.createToken(user);
    }
}
