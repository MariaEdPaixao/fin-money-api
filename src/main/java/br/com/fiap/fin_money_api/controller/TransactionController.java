package br.com.fiap.fin_money_api.controller;

import java.time.LocalDate;
import java.util.List;

import br.com.fiap.fin_money_api.specification.TransactionSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.fin_money_api.model.Transaction;
import br.com.fiap.fin_money_api.repository.TransactionRepository;

@RestController
@RequestMapping("transactions")
@Slf4j
public class TransactionController {

    //todos os campos que serão possibilidades de filtro da transação estarão aqui
    public record TransactionFilters(String description, LocalDate startDate, LocalDate endDate){}

    @Autowired
    private TransactionRepository repository;

    @GetMapping
    public Page<Transaction> index(
            TransactionFilters filters,
            @PageableDefault(size = 10, sort = "date", direction = Sort.Direction.ASC) Pageable pageable){

        var specification = TransactionSpecification.withFilters(filters);
        return repository.findAll(specification,pageable);
//        if(description != null && date != null)
//            return  repository.findByDescriptionContainingIgnoringCaseAndDate(description, date, pageable);
//
//        if (description != null)
//            return  repository.findByDescriptionContainingIgnoringCase(description, pageable);
//
//        if (date != null)
//            return repository.findByDate(date, pageable);

//        var probe = Transaction.builder()
//                .description(description)
//                .date(date)
//                .build();
//        var matcher = ExampleMatcher.matchingAll()
//                .withIgnoreCase()
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
//        //.of -> criar um example a partir de um probe (exemplo de transactions, nesse caso)
//        var example = Example.of(probe, matcher);

    }
}
