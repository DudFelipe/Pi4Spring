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
        ModelAndView mv = new ModelAndView("cadastro");
        mv.addObject("usuario", new Usuario());
        return mv;
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastrar(
            @ModelAttribute("usuario")  @Valid Usuario u,
            BindingResult bindingResult){ //Concluir o cadastro

        if(bindingResult.hasErrors()){
            return new ModelAndView("cadastro");
        }
        else {
            ModelAndView mv = new ModelAndView("redirect:/index");
            u.setTipoAcesso(1);
            u.setAtivo(1);
            u.setNome(u.getNome() + " " + u.getSobrenome());
            u.setSenha(u.getHashSenha());
            usuarioRepository.save(u);

            return mv;
        }
    }
}
