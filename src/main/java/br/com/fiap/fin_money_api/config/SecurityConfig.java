package br.com.fiap.fin_money_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    //pra acessar /categoriws tem que ter a role admin
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/categories/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
        )
                .httpBasic(Customizer.withDefaults())
                .build();
        //mantém a autenticação básica, só mudando algumas customizações
    }

//    serviço de localização de detalhes do usuário
    @Bean
    UserDetailsService userDetailsService(){
        //build retorna um user details
        var users = List.of(
                User
                        .withUsername("maria")
                        .password("$2a$12$xGfQu2trsL147wwpww1a6.SoEkK6e.Mx0TUbapTpc2ryFfBRAFTIe")
                        .roles("ADMIN")
                        .build(),
                User
                        .withUsername("joao")
                        .password("$2a$12$tFeb7kAzX.AGSn264fAnoO30IPrVxmducxPcHxqUMS2XHhOB0vLs6")
                        .roles("USER")
                        .build()

                // bff - backend or frontend
        );
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
