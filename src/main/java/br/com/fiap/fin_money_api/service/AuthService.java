package br.com.fiap.fin_money_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fiap.fin_money_api.repository.UserRepository;

// authservice é entendido como um userdetailsservice, faz parte desse "clube"
// e precisa implementar metódos especificos

@Service //para o spring reconhecer nosso AuthService
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    //como carregar os detalhes de um usuário pelo user name
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // busca pelo email, caso contrario lança exceção
        return repository.findByEmail(username).orElseThrow(
            () -> new UsernameNotFoundException("usuário não encontrado")
        );

    }
    
}
