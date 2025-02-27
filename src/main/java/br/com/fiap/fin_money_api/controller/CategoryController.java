package br.com.fiap.fin_money_api.controller;

import br.com.fiap.fin_money_api.model.Category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController //notação -> indica que essa classe seja um componente do spring
public class CategoryController {


    private List<Category> repository = new ArrayList<>();

    //path: atributo padrão -> toda notação tem um atributo padrão, se fosse passar 2 atributos precisaria do nome 
    // @RequestMapping(produces = "application/json", path = "/categories", method = {RequestMethod.GET}) //notação -> mapeia requisições
    @GetMapping("/categories") //notação -> mapeia requisições do tipo get
    public List<Category> index(){
        return repository;
    }

    @PostMapping("/categories")
    @ResponseStatus(code = HttpStatus.CREATED)
    //notação que diz que o json virá do body e atribui com o objeto do Model
    public Category create(@RequestBody Category category){
        System.out.println("Cadastrando categoria " + category.getName());
        repository.add(category);
        return category;
    }

    @GetMapping("/categories/{id}")
    //o retorno será uma resposta com um status no corpo
    public ResponseEntity<Category> get(@PathVariable Long id){
        System.out.println("Buscando categoria " + id);
        // stream fluxo de dados, faz o fluxo e retorna ele, que pode ser consumido por outra função
        // arrow function () -> |  //devolverá uma stream 
        var category = repository.stream().filter(c -> c.getId().equals(id)).findFirst();

        if(category.isEmpty()){
            return ResponseEntity.status(404).build(); //como não tem corpo, chamo o metódo build para terminar a construção da resposta
        }
        return ResponseEntity.ok(category.get());
    }
}
