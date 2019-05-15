package sp.senac.br.pet.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sp.senac.br.pet.model.Usuario;
import sp.senac.br.pet.repository.UsuarioRepository;

import javax.validation.Valid;

import javax.validation.Valid;
import sp.senac.br.pet.model.Pedido;
import sp.senac.br.pet.repository.PedidoRepository;

@RestController
@RequestMapping("/login")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public ModelAndView login(Authentication authentication){
        if(authentication != null){
            return new ModelAndView("redirect:/login/minhaconta");
        }

        return new ModelAndView("login");
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastrar(){ //Mostrar o formulário de cadastro
        ModelAndView mv = new ModelAndView("cadastro");
        mv.addObject("usuario", new Usuario());
        return mv;
    }
    
    @GetMapping("/minhaconta")
    public ModelAndView minhaconta(){ //Mostrar o formulário de cadastro
        List<Pedido> pedidos = pedidoRepository.findAll();
        
        ModelAndView mv = new ModelAndView("minhaconta").addObject("pedidos", pedidos);
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
