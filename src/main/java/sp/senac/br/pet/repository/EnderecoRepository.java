package sp.senac.br.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.senac.br.pet.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
