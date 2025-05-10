package br.com.fiap.fin_money_api.service;

import br.com.fiap.fin_money_api.model.Token;
import br.com.fiap.fin_money_api.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    //Instant: momento no tempo
    // fuso horario do brasil -> .plusMinutes(10).toInstant(ZoneOffset.ofHours(-3) 10min dps que
    // .plusDays(1) validade de 1 dia
    private Instant expiresAt = LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.ofHours(-3));

    private Algorithm algorithm = Algorithm.HMAC256("secret"); //assinatura
    public Token createToken(User user
    ){
        //.withclaim, tudo oq quero guardar no payload
        var jwt = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withExpiresAt(expiresAt)
                .sign(algorithm);
//                .withClaim("role", user.getRole())

        return new Token(jwt, user.getEmail());
    }

    public User getUserFromToken(String token){
        //metodo require -> valida o token de acordo com meu algoritmo (secret, assinatura)
        var verifiedToken = JWT.require(algorithm).build().verify(token);

        return User.builder()
        .id(Long.valueOf(verifiedToken.getSubject()))
                .email(verifiedToken.getClaim("email").toString()).build();
    }
}
