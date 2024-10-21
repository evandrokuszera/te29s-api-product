package br.edu.utfpr.aluno.api_produto.controller;

import br.edu.utfpr.aluno.api_produto.model.Produto;
import br.edu.utfpr.aluno.api_produto.repositories.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/produtos")
public class ProdutoController {

    private ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository){
        this.repository = repository;
    }

    // Lista de endpoints

    @GetMapping
    public ResponseEntity<List<Produto>> getAll(){
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Produto> getOne(@PathVariable(name = "id") Long idProduto){
        Produto produtoEncontrado = this.repository.findById(idProduto).orElse(null);
        if (produtoEncontrado == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(produtoEncontrado);
    }

    @PostMapping
    public ResponseEntity<String> addOne(@RequestBody Produto produto) {
        if (produto.getDescription() == null || produto.getPrice() < 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Descrição ou Preço inválidos");
        } else {
            this.repository.save(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
        }
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<String> update(@PathVariable(name="id") Long idProduto, @RequestBody Produto produto) {
        Produto produtoDB = this.repository.findById(idProduto).orElse(null);
        if (produtoDB != null){
            produtoDB.setDescription(produto.getDescription());
            produtoDB.setQuantity(produto.getQuantity());
            produtoDB.setPrice(produto.getPrice());
            produtoDB.setCategory(produto.getCategory());
            this.repository.save(produtoDB);
            return ResponseEntity.ok("Produto atualizado com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable(name="id") Long idProduto){
        Produto produtoRemover = this.repository.findById(idProduto).orElse(null);
        if (produtoRemover != null){
            this.repository.delete(produtoRemover);
            return ResponseEntity.status(HttpStatus.OK).body("Produto removido com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
    }

    @GetMapping(path = "/categoria/{category}")
    public ResponseEntity<List<Produto>> getByCategory(@PathVariable(name = "category") String category){
        List<Produto> produtos = this.repository.findByCategory(category);
        if (produtos.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

}