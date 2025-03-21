package br.com.fiap.fin_money_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
//@Getter //gera o get para todos os atributos
//@ToString // gera o toString de retornar json
@Data //gera tudo junto o lombok

public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //o campo id vai ser gerado pelo próprio bd
    private Long id;
    private String name;
    private String icon;
}
