package sp.senac.br.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sp.senac.br.pet.model.Endereco;
import sp.senac.br.pet.model.Pedido;
import sp.senac.br.pet.model.Usuario;

import java.util.List;
import java.util.Set;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p FROM Pedido p WHERE p.cliente = :u")
    Set<Pedido> buscaPedidosUsuario(@Param("u") Usuario u);

}
