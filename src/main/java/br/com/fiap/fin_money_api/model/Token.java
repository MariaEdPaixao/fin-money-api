package br.com.fiap.fin_money_api.model;

// record - tipo de dado que representa um objeto imutavel
// não podem ser mudados
// tem um get e um construtor, sem que eu precise aplicar
public record Token (
    String token,
    String email
) {}
