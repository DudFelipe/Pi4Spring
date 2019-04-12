package sp.senac.br.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.senac.br.pet.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
