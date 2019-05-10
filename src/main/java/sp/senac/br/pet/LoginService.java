package sp.senac.br.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sp.senac.br.pet.model.Usuario;
import sp.senac.br.pet.repository.UsuarioRepository;

import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    List<Usuario> usuarios;

    @Override
    public Usuario loadUserByUsername(String email) throws UsernameNotFoundException {
        usuarios = usuarioRepository.buscaUsuariosAtivos();

        for (Usuario u : usuarios) {
            if(u.getEmail().equals(email)){
                return u;
            }
        }
        throw new UsernameNotFoundException("Usuário não encontrado!");
    }
}
