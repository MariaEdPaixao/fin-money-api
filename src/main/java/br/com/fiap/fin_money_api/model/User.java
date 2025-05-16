package br.com.fiap.fin_money_api.model;

import java.util.Collection;
import java.util.List;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users") //altero os parametros que o jpa vai usar pra criar a tabela
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id") // faça o equals do id, e nao de todos os campos
public class User implements UserDetails {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "email inválido")
    @NotBlank(message = "campo obrigatório")
    @Column(unique = true) //altero os parametros que o jpa vai usar pra criar o campo
    private String email;

    @Size(min = 5)
    private String password;

    //private String role;
    //return List.of(new SimpleGrantedAuthority(role));

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
       return email;
    }
}
