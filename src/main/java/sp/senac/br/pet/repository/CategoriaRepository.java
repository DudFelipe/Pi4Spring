package sp.senac.br.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.senac.br.pet.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
