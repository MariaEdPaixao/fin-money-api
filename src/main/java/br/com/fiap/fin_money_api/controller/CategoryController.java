package br.com.fiap.fin_money_api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.server.ResponseStatusException;
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

import br.com.fiap.fin_money_api.model.Category;
import br.com.fiap.fin_money_api.model.User;
import br.com.fiap.fin_money_api.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

// a barra (/) é opcional
@RestController // notação -> indica que essa classe seja um componente do spring
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j // substitui o log -> private Logger log = LoggerFactory.getLogger(getClass());
       // // objeto para log no terminal
public class CategoryController {

    @Autowired // injeção de dependência
    private CategoryRepository repository;

    // path: atributo padrão -> toda notação tem um atributo padrão, se fosse passar
    // 2 atributos precisaria do nome
    // @RequestMapping(produces = "application/json", path = "/categories", method =
    // {RequestMethod.GET}) //notação -> mapeia requisições
    @GetMapping() // notação -> mapeia requisições do tipo get
    @Operation(summary = "Listar categorias", description = "Retorna um array com todas as categorias")
    @Cacheable("categories")
    // fala pro spring que eu quero o usuario logado pra fazer uma busca
    public List<Category> index(@AuthenticationPrincipal User user) {
        return repository.findByUser(user);
    }

    @PostMapping()
    @CacheEvict(value = "categories", allEntries = true) // envalidar tudo que tinha no cache antes
    @Operation(responses = @ApiResponse(responseCode = "400", description = "Validação falhou"), deprecated = true)
    @ResponseStatus(code = HttpStatus.CREATED)
    // notação que diz que o json virá do body e atribui com o objeto do Model
    public Category create(@RequestBody @Valid Category category, @AuthenticationPrincipal User user) {
        log.info("Cadastrando categoria " + category.getName());
        category.setUser(user);
        return repository.save(category);
    }

    @GetMapping("{id}")
    // o retorno será uma resposta com um status no corpo
    public ResponseEntity<Category> get(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.info("Buscando categoria " + id);
        return ResponseEntity.ok(getCategory(id, user));
    }

    // apagar
    @DeleteMapping("{id}")
    @CacheEvict(value = "categories", allEntries = true) // envalidar tudo que tinha no cache antes
    public ResponseEntity<Object> destroy(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.info("Apagando categoria: " + id);

        repository.delete(getCategory(id, user));
        return ResponseEntity.noContent().build(); // delete: 204 - sucesso, porém sem corpo para retornar
    }

    // editar
    @PutMapping("{id}")
    @CacheEvict(value = "categories", allEntries = true) // envalidar tudo que tinha no cache antes
    public ResponseEntity<Object> update(@PathVariable @Valid Long id, @RequestBody Category category,
            @AuthenticationPrincipal User user) {
        log.info("Atualizando categoria + " + id + " com " + category);

        var oldCategory = getCategory(id, user);
        // category.setId(id); // evitando que o id seja atualizado
        // category.setUser(user); //garantindo que mantenha o relacionamento com o usuário ligado
                                //obj original //onde coloco os nvs dados   //mantém o restante que indiquei
        BeanUtils.copyProperties(category, oldCategory, "id", "user"); //altera somente a categoria mesmo
        repository.save(oldCategory); // adicionando os novos dados
        return ResponseEntity.ok(oldCategory);
    }

    // pega o primeiro, ou senão lança uma exceção
    private Category getCategory(Long id, User user) {
        var categoryFind = repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
        // pega o primeiro ou, senão, lança uma exceção
        if (!categoryFind.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return categoryFind;
    }
}
