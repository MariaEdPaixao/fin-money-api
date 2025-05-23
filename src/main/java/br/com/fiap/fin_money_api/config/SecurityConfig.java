package br.com.fiap.fin_money_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

    @Autowired
    private  AuthFilter authFilter;
    @Bean
    //pra acessar /categoriws tem que ter a role admin
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                auth -> auth
                        //.requestMatchers("/categories/**").hasRole("ADMIN")
                        .requestMatchers("/login/**").permitAll()
                        .anyRequest().authenticated()
        )
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .build();
        //mantém a autenticação básica, só mudando algumas customizações
    }

//    serviço de localização de detalhes do usuário
    // @Bean
    // UserDetailsService userDetailsService(){
    //     //build retorna um user details
    //     var users = List.of(
    //             User
    //                     .withUsername("maria")
    //                     .password("$2a$12$xGfQu2trsL147wwpww1a6.SoEkK6e.Mx0TUbapTpc2ryFfBRAFTIe")
    //                     .roles("ADMIN")
    //                     .build(),
    //             User
    //                     .withUsername("joao")
    //                     .password("$2a$12$tFeb7kAzX.AGSn264fAnoO30IPrVxmducxPcHxqUMS2XHhOB0vLs6")
    //                     .roles("USER")
    //                     .build()

                 // bff - backend or frontend
    //     );
    //     return new InMemoryUserDetailsManager(users);
    // }

    // sempre que eu pedir "passwordEncoder" usará BCrypt
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
