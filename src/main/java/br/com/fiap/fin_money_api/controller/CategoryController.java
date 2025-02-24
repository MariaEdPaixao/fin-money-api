package br.com.fiap.fin_money_api.controller;

import br.com.fiap.fin_money_api.model.Category;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController //notação -> indica que essa classe seja um componente do spring
public class CategoryController {

    @RequestMapping(produces = "application/json", path = "/categories", method = {RequestMethod.GET}) //notação -> mapeia requisições
    public Category index(){
        return new Category(1L, "Educação", "book");
    }
}
