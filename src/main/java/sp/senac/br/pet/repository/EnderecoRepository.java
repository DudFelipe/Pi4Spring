package sp.senac.br.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sp.senac.br.pet.model.Endereco;
import sp.senac.br.pet.model.Usuario;

import java.util.List;
import java.util.Set;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    @Query("SELECT e, u FROM Endereco e JOIN e.usuario u WHERE e.usuario = :u")
    Set<Endereco> buscaEnderecos(@Param("u") Usuario u);

}
