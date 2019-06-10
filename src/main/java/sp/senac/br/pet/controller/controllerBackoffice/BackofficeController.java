package sp.senac.br.pet.controller.controllerBackoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sp.senac.br.pet.model.Categoria;
import sp.senac.br.pet.model.Pedido;
import sp.senac.br.pet.model.Produto;
import sp.senac.br.pet.model.Usuario;
import sp.senac.br.pet.repository.CategoriaRepository;
import sp.senac.br.pet.repository.PedidoRepository;
import sp.senac.br.pet.repository.ProdutoRepository;
import sp.senac.br.pet.repository.UsuarioRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class BackofficeController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("indexBackoffice");
        return mv;
    }

    @GetMapping("/usuariosBackoffice/lista")
    public ModelAndView usuariosBackoffice(){
        ModelAndView mv = new ModelAndView("usuariosBackoffice");

        List<Usuario> usuarios = usuarioRepository.buscaUsuariosAtivos();

        mv.addObject("usuarios", usuarios);
        return mv;
    }

    @GetMapping("/usuariosBackoffice")
    public ModelAndView cadastraUsuarios(){
        ModelAndView mv = new ModelAndView("usuariosBackofficeCadastro");

        mv.addObject("usuario", new Usuario());
        return mv;
    }

    @PostMapping("/usuariosBackoffice")
    public ModelAndView cadastraUsuarios(
            @ModelAttribute("usuario")  @Valid Usuario u,
            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ModelAndView("usuariosBackofficeCadastro");
        }
        else{
            ModelAndView mv = new ModelAndView("redirect:/admin");

            u.setNome(u.getNome() + " " + u.getSobrenome());
            u.setSenha(u.getHashSenha());
            u.setAtivo(1);

            usuarioRepository.save(u);
            return mv;
        }

    }

    @GetMapping("/excluirUsuario/{id}")
    public ModelAndView excluirUsuario(@PathVariable int id){
        ModelAndView mv = new ModelAndView("redirect:/admin");

        Usuario u = usuarioRepository.getOne(id);

        u.setAtivo(0);

        int espaco = u.getNome().indexOf(" ");

        u.setSobrenome(u.getNome().substring(espaco+1));
        u.setNome(u.getNome().substring(0, espaco));

        usuarioRepository.save(u);

        return mv;
    }

    @GetMapping("/alterarUsuario/{id}")
    public ModelAndView alterarUsuario(@PathVariable int id){
        ModelAndView mv = new ModelAndView("usuariosBackofficeCadastro");

        List<Usuario> usuarios = usuarioRepository.buscaUsuariosAtivos();

        Usuario u = usuarioRepository.getOne(id);

        int espaco = u.getNome().indexOf(" ");

        u.setSobrenome(u.getNome().substring(espaco+1));
        u.setNome(u.getNome().substring(0, espaco));

        mv.addObject("usuarios", usuarios);
        mv.addObject("usuario", u);
        return mv;
    }

    @PostMapping("/alterarUsuario/{id}")
    public ModelAndView alterarUsuario(@PathVariable int id, @ModelAttribute("usuario")  @Valid Usuario u,
                                       BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            List<Usuario> usuarios = usuarioRepository.buscaUsuariosAtivos();
            return new ModelAndView("usuariosBackofficeCadastro").addObject("usuarios", usuarios);
        }
        else{
            ModelAndView mv = new ModelAndView("redirect:/admin");

            Usuario user = usuarioRepository.getOne(id);
            user.setNome(u.getNome());
            user.setSobrenome(u.getSobrenome());
            user.setSenha(u.getHashSenha());
            user.setCpf(u.getCpf());
            user.setEmail(u.getEmail());
            user.setNascimento(u.getNascimento());
            user.setSexo(u.getSexo());
            user.setTelefone(u.getTelefone());
            user.setTipoAcesso(u.getTipoAcesso());

            user.setNome(u.getNome() + " " + u.getSobrenome());

            usuarioRepository.save(user);

            return mv;
        }
    }


    ///Produtos e Categorias

    @GetMapping("/adicionar")
    public ModelAndView adicionar(){
        ModelAndView mv = new ModelAndView("cadastroProduto");

        List<Categoria> categorias = categoriaRepository.findAll();

        for(Categoria c : categorias){
            System.out.println(c.getIdCategoria());
            System.out.println(c.getNome());
        }

        mv.addObject("produto", new Produto());
        mv.addObject("categorias", categorias);


        return mv;
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(Produto p){
        ModelAndView mv = new ModelAndView("redirect:/admin");

        produtoRepository.save(p);

        List<Produto> produtos = produtoRepository.findAll();

        mv.addObject("produtos", produtos);

        mv.addObject("insert", true);

        return mv;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable int id){
        ModelAndView mv = new ModelAndView("cadastroProduto");

        List<Categoria> categorias = categoriaRepository.findAll();
        Produto p = produtoRepository.getOne(id);

        mv.addObject("produto", p);
        mv.addObject("categorias", categorias);

        return mv;
    }

    @PostMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable int id, Produto p){
        ModelAndView mv = new ModelAndView("redirect:/admin/listagemProdutosBackOffice");

        Produto prod = produtoRepository.getOne(id);

        prod.setDescricao(p.getDescricao());
        prod.setAtivo(p.getAtivo());
        prod.setNome(p.getNome());
        prod.setPreco(p.getPreco());
        prod.setEstoque(p.getEstoque());
        prod.setIdCategoria((p.getIdCategoria()));
        prod.setModelo(p.getModelo());
        prod.setCodigodebarras(p.getCodigodebarras());

        produtoRepository.save(prod);

        return mv;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable int id){

        ModelAndView mv = new ModelAndView("redirect:/admin/listagemProdutosBackOffice");

        Produto p = produtoRepository.getOne(id);
        produtoRepository.delete(p);

        mv.addObject("sucesso", true);

        return mv;

    }

    @GetMapping("/listagemProdutosBackOffice")
    public ModelAndView listagemProdutosBackOffice(){
        ModelAndView mv = new ModelAndView("listagemProdutosBackOffice");
        List<Produto> produtos = produtoRepository.findAll();

        mv.addObject("produtos", produtos);


        return mv;
    }

    @GetMapping("/listagemPedidoBackOffice")
    public ModelAndView listagemPedidoBackOffice(){
        ModelAndView mv = new ModelAndView("listagemPedidoBackoffice");

        List<Pedido> pedidos = pedidoRepository.findAll();
        mv.addObject("pedidos", pedidos);


        return mv;
    }

    @GetMapping("/listagemPedidosBackOffice/validar/{id}")
    public ModelAndView validaPedido(@PathVariable int id){
        ModelAndView mv = new ModelAndView("redirect:/admin/listagemPedidoBackOffice");

        Pedido p = pedidoRepository.getOne(id);

        p.setStatus(1);

        pedidoRepository.save(p);

        return mv;
    }

}
