package sp.senac.br.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sp.senac.br.pet.model.Usuario;
import sp.senac.br.pet.repository.UsuarioRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastrar(){ //Mostrar o formulário de cadastro
        ModelAndView mv = new ModelAndView("cadastroUsuario");
        mv.addObject("usuario", new Usuario());
        return mv;
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastrar(
            @ModelAttribute("usuario")  @Valid Usuario u,
            BindingResult bindingResult){ //Concluir o cadastro

        if(bindingResult.hasErrors()){
            return new ModelAndView("cadastroUsuario");
        }
        else {
            ModelAndView mv = new ModelAndView("redirect:/usuario/cadastro");
            u.setTipoAcesso(1);
            usuarioRepository.save(u);

            return mv;
        }
    }
}
