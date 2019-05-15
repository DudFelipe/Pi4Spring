package sp.senac.br.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.senac.br.pet.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
