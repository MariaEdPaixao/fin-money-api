package br.com.fiap.fin_money_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.fin_money_api.model.Transaction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
//JpaSpecificationExecutor - também vai ter o findAll por especificação
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    // SELECT  * FROM TRANSACTION WHERE DESCRIPTION = :DESCRIPTION
    //@Query("SELECT t FROM TRANSACTION as t WHERE t.description = :description") //JPQL
    //Page<Transaction> findByDescriptionContainingIgnoringCase(String description, Pageable pageable); //Query Methods

    //Page<Transaction> findByDescriptionContainingIgnoringCaseAndDate(String description, LocalDate date, Pageable pageable);

    //Page<Transaction> findByDate(LocalDate date, Pageable pageable);
}
