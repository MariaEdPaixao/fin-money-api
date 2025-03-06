package br.com.fiap.fin_money_api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
// import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.fin_money_api.model.Category;
// a barra (/) é opcional
@RestController // notação -> indica que essa classe seja um componente do spring
@RequestMapping("/categories")
public class CategoryController {
    private Logger log = LoggerFactory.getLogger(getClass()); // objeto para log no terminal
    private List<Category> repository = new ArrayList<>();

    // path: atributo padrão -> toda notação tem um atributo padrão, se fosse passar
    // 2 atributos precisaria do nome
    // @RequestMapping(produces = "application/json", path = "/categories", method =
    // {RequestMethod.GET}) //notação -> mapeia requisições
    @GetMapping() // notação -> mapeia requisições do tipo get
    public List<Category> index() {
        return repository;
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    // notação que diz que o json virá do body e atribui com o objeto do Model
    public Category create(@RequestBody Category category) {
        log.info("Cadastrando categoria " + category.getName());
        repository.add(category);
        return category;
    }

    @GetMapping("{id}")
    // o retorno será uma resposta com um status no corpo
    public ResponseEntity<Category> get(@PathVariable Long id) {
        log.info("Buscando categoria " + id);
        return ResponseEntity.ok(getCategory(id));
    }

    // apagar
    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        log.info("Apagando categoria: " + id);

        repository.remove(getCategory(id));
        return ResponseEntity.noContent().build(); //delete: 204 - sucesso, porém sem corpo para retornar
    }

    //editar
    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Category category) {
        log.info("Atualizando categoria + " + id + " " + category);
 
        repository.remove(getCategory(id)); // tirando os dados antigos
        category.setId(id); // evitando que o id seja atualizado
        repository.add(category); // adicionando os novos dados
        return ResponseEntity.ok(category);
    }

    //pega o primeiro, ou senão lança uma exceção
    private Category getCategory(Long id) {
        // stream fluxo de dados, faz o fluxo e retorna ele, que pode ser consumido por
        // outra função
        // arrow function () -> | //devolverá uma stream
        return repository.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
        // pega o primeiro ou, senão, lança uma exceção
    }
}
