package sp.senac.br.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sp.senac.br.pet.model.Usuario;
import sp.senac.br.pet.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/cadastro")
    public ModelAndView cadastrar(){ //Mostrar o formulário de cadastro
        ModelAndView mv = new ModelAndView("cadastroUsuario");
        mv.addObject("usuario", new Usuario());
        return mv;
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastrar(Usuario u){ //Concluir o cadastro
        ModelAndView mv = new ModelAndView("cadastroUsuario");
        u.setTipoAcesso(1);
        usuarioRepository.save(u);
        return mv;
    }
}
