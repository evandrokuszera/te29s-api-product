package br.edu.utfpr.aluno.api_produto.controller;

import br.edu.utfpr.aluno.api_produto.model.Produto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/produtos")
public class ProdutoController {
    // Lista de produtos, para simular um banco de dados
    private List<Produto> produtos;

    // Construtor para popular a lista de produtos
    public ProdutoController(){
      this.produtos = new ArrayList<>();
      this.produtos.add(new Produto(1L, "IPHONE 15", 10, 5000.0, "Smartphone"));
      this.produtos.add(new Produto(2L, "Airpods Pro", 20, 2500.0, "Acessórios"));
      this.produtos.add(new Produto(3L, "Notebook i7", 30, 4800.0, "Computadores"));
      this.produtos.add(new Produto(4L, "Cadeira Gamer", 50, 1500.0, "Móveis"));
    }

    // Lista de endpoints

    @GetMapping
    public List<Produto> getAll(){
        return this.produtos;
    }

    @GetMapping(path = "/{id}")
    public Produto getOne(@PathVariable(name = "id") Long idProduto){
        Produto produtoEncontrado = this.produtos.stream()
                .filter(produto -> produto.getId().equals(idProduto))
                .findFirst()
                .orElse(null);
        return produtoEncontrado;
    }

    @PostMapping
    public String addOne(@RequestBody Produto produto) {
        if (produto.getDescription() == null || produto.getPrice() < 0){
            return "Descrição ou Preço inválidos";
        } else {
            this.produtos.add(produto);
            return "Produto cadastrado com sucesso";
        }
    }

    @PutMapping(path="/{id}")
    public void update(@PathVariable(name="id") Long idProduto, @RequestBody Produto produto) {
        for (Produto p : this.produtos){
            if (p.getId().equals(idProduto)){
                p.setDescription(produto.getDescription());
                p.setQuantity(produto.getQuantity());
                p.setPrice(produto.getPrice());
                p.setCategory(produto.getCategory());
                break;
            }
        }
    }

    @DeleteMapping(path = "/{id}")
    public String delete(@PathVariable(name="id") Long idProduto){
        Produto produtoRemover = this.produtos.stream()
                .filter(p->p.getId().equals(idProduto))
                .findFirst()
                .orElse(null);
        if (produtoRemover != null){
            this.produtos.remove(produtoRemover);
            return "Produto removido com sucesso.";
        }
        return "Produto não encontrado";
    }
}
