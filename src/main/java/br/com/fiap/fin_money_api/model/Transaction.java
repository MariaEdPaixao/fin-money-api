package br.com.fiap.fin_money_api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //mapear a classe para uma tabela no bd
@Data //gerar get/set
@Builder //construtor c parametros
@NoArgsConstructor  
@AllArgsConstructor
public class Transaction {
    
    //pk 
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo obrigatório!")
    @Size(min = 5, max = 255, message = "Deve ter pelo menos 5 caracteres")
    private String description;

    @Positive(message = "Deve ser um valor positivo")
    private BigDecimal amount;

    @PastOrPresent(message = "Não pode ser uma data no futuro")
    private LocalDate date;

    @NotNull(message = "Campo obrigatório!")
    @Enumerated(EnumType.STRING) //cadastrar no banco de dados como a string e não como um int (1 - INCOME, px) 
    private TransactionType type;
    
    @NotNull(message = "Campo obrigatório!")
    @ManyToOne
    private Category category;
}
