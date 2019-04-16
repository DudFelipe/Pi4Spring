package sp.senac.br.pet.controller.controllerBackoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sp.senac.br.pet.model.Usuario;
import sp.senac.br.pet.repository.UsuarioRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class BackofficeController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("indexBackoffice");
        return mv;
    }

    @GetMapping("/usuariosBackoffice")
    public ModelAndView usuariosBackoffice(){
        ModelAndView mv = new ModelAndView("usuariosBackoffice");

        List<Usuario> usuarios = usuarioRepository.findAll();

        mv.addObject("usuarios", usuarios);
        mv.addObject("usuario", new Usuario());
        return mv;
    }

    @PostMapping("/usuariosBackoffice")
    public ModelAndView cadastraUsuarios(
            @ModelAttribute("usuario")  @Valid Usuario u,
            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            List<Usuario> usuarios = usuarioRepository.findAll();
            return new ModelAndView("usuariosBackoffice").addObject("usuarios", usuarios);
        }
        else{
            ModelAndView mv = new ModelAndView("redirect:/admin");

            usuarioRepository.save(u);
            return mv;
        }

    }

    /*@GetMapping("/excluirUsuario/{id}")
    public ModelAndView excluirUsuario(@PathVariable int id){
        ModelAndView mv = new ModelAndView("redirect:/admin/indexBackoffice");

        usuarioRepository.deleteById(id);

        return mv;
    }*/

    @GetMapping("/alterarUsuario/{id}")
    public ModelAndView alterarUsuario(@PathVariable int id){
        ModelAndView mv = new ModelAndView("usuariosBackoffice");

        List<Usuario> usuarios = usuarioRepository.findAll();

        Usuario u = usuarioRepository.getOne(id);

        mv.addObject("usuarios", usuarios);
        mv.addObject("usuario", u);
        return mv;
    }

    @PostMapping("/alterarUsuario/{id}")
    public ModelAndView alterarUsuario(@PathVariable int id, @ModelAttribute("usuario")  @Valid Usuario u,
                                       BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            List<Usuario> usuarios = usuarioRepository.findAll();
            return new ModelAndView("usuariosBackoffice").addObject("usuarios", usuarios);
        }
        else{
            ModelAndView mv = new ModelAndView("redirect:/admin");

            Usuario user = usuarioRepository.getOne(id);
            user.setNome(u.getNome());
            user.setTipoAcesso(u.getTipoAcesso());
            user.setCpf(u.getCpf());
            user.setEmail(u.getEmail());
            user.setEndereco(u.getEndereco());
            user.setNascimento(u.getNascimento());
            user.setRg(u.getRg());
            user.setSenha(u.getSenha());
            user.setSexo(u.getSexo());
            user.setTelefone(u.getTelefone());

            usuarioRepository.save(user);

            return mv;
        }

    }
}
