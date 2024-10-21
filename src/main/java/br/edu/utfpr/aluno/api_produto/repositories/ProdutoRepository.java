package br.edu.utfpr.aluno.api_produto.repositories;

import br.edu.utfpr.aluno.api_produto.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    List<Produto> findByCategory(String categoria);

}