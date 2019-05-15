package sp.senac.br.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sp.senac.br.pet.model.Produto;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    @Query("SELECT p FROM Produto p WHERE p.ativo = 1")
    List<Produto> buscaProdutosAtivos();

}
