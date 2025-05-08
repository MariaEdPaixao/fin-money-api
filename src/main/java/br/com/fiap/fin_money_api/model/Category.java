package br.com.fiap.fin_money_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // get, set, toString, equals
@Builder //cria um construtor com parametros
@NoArgsConstructor //cria um construtor sem parametros
@AllArgsConstructor //incluindo ambos os construtores, tanto com parametros quanto sem
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Não pode estar em branco")
    @Size(min = 3)
    private String name;

    @NotBlank(message = "Não pode estar em branco")
    @Pattern(regexp = "^[A-Z].*", message = "Deve começar com letra maiúscula")
    private String icon;

    // um user pode ter varias categorias
    @ManyToOne
    // vai ignorar as infos de usuário quando gerar algum json
    @JsonIgnore 
    private User user;

    // @Min(0)
    // @Max(10)
    // @Positive - ser maior que 0 - forma semântica
    // @Past - data no passado
    // @Future - data no futuro
    // @PastOrPresent - hoje ou para trás
}