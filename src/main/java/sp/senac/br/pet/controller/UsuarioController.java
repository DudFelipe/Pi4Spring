package sp.senac.br.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sp.senac.br.pet.model.Endereco;
import sp.senac.br.pet.model.Usuario;
import sp.senac.br.pet.repository.EnderecoRepository;
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
    private EnderecoRepository enderecoRepository;

    @GetMapping
    public ModelAndView login(Authentication authentication){
        if(authentication != null){
            Usuario u = (Usuario)authentication.getPrincipal();

            u.setEnderecos(enderecoRepository.buscaEnderecos(u));

            ModelAndView mv = new ModelAndView("minhaconta");
            mv.addObject("usuario", u);

            return mv;
        }

        return new ModelAndView("login");
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

    @GetMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable int id){
        ModelAndView mv = new ModelAndView("cadastro");

        Usuario u = usuarioRepository.getOne(id);

        int espaco = u.getNome().indexOf(" ");

        u.setSobrenome(u.getNome().substring(espaco+1));
        u.setNome(u.getNome().substring(0, espaco));

        mv.addObject("usuario", u);
        return mv;
    }

    @PostMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable int id,
                                @ModelAttribute("usuario")  @Valid Usuario u,
                                BindingResult bindingResult, Authentication authentication){
        if(bindingResult.hasErrors()){
            return new ModelAndView("cadastro");
        }
        else{
            ModelAndView mv = new ModelAndView("redirect:/login");

            Usuario user = usuarioRepository.getOne(id);
            user.setNome(u.getNome() + " " + u.getSobrenome());
            user.setSobrenome(u.getSobrenome());
            user.setSenha(u.getHashSenha());
            user.setCpf(u.getCpf());
            user.setEmail(u.getEmail());
            user.setNascimento(u.getNascimento());
            user.setSexo(u.getSexo());
            user.setTelefone(u.getTelefone());

            usuarioRepository.save(user);

            authentication.setAuthenticated(false);

            return mv;
        }
    }

    @GetMapping("/minhaconta")
    public ModelAndView minhaconta() { //Mostrar o formulário de cadastro
        List<Pedido> pedidos = pedidoRepository.findAll();

        ModelAndView mv = new ModelAndView("redirect:/login/minhaconta").addObject("pedidos", pedidos);
        return mv;
    }

    @GetMapping("/endereco")
    public ModelAndView endereco(){
        ModelAndView mv = new ModelAndView("alterarEndereco");
        mv.addObject("endereco", new Endereco());
        return mv;
    }

    @PostMapping("/endereco")
    public ModelAndView endereco(
            @ModelAttribute("endereco") @Valid Endereco e,
            BindingResult bindingResult, Authentication authentication){

        if(bindingResult.hasErrors()){
            return new ModelAndView("alterarEndereco");
        }
        else if(authentication != null){
            Usuario u = (Usuario) authentication.getPrincipal();
            e.setUsuario(u);

            enderecoRepository.save(e);

            return new ModelAndView("redirect:/login");
        }

        return new ModelAndView("redirect:/login");
    }
}
