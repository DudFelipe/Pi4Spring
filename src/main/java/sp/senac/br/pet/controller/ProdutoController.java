package sp.senac.br.pet.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sp.senac.br.pet.model.Categoria;
import sp.senac.br.pet.model.Produto;
import sp.senac.br.pet.repository.CategoriaRepository;
import sp.senac.br.pet.repository.ProdutoRepository;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import sp.senac.br.pet.model.Carrinho;
import sp.senac.br.pet.model.Endereco;
import sp.senac.br.pet.model.Pedido;
import sp.senac.br.pet.model.Usuario;
import sp.senac.br.pet.repository.EnderecoRepository;
import sp.senac.br.pet.repository.PedidoRepository;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private EnderecoRepository enderecoRepository;


    @GetMapping()
    public ModelAndView listarProdutos(){
        ModelAndView mv = new ModelAndView("todos-produtos");

        List<Produto> produtos = produtoRepository.buscaProdutosAtivos();

        List<Categoria> categorias = categoriaRepository.findAll();

        mv.addObject("produtos", produtos);
        mv.addObject("categorias", categorias);

        return mv;
    }
    
    @GetMapping("/checkout")
    public ModelAndView checkout(Authentication authentication){
        if(authentication != null){
            Usuario u = (Usuario)authentication.getPrincipal();

            Set<Endereco> enderecos = enderecoRepository.buscaEnderecos(u);
            ModelAndView mv = new ModelAndView("checkout").addObject("endereco", new Endereco()).addObject("enderecos", enderecos);

            return mv;
        }
        
        
        return new ModelAndView("redirect:/login");
    }
    
    @PostMapping("/checkout")
    public String finalizaCompra(@RequestBody Carrinho carrinhoJSON){
        List<Produto> produtos = new ArrayList<>();
        
        for(int i = 0; i < carrinhoJSON.getProdutos().size() ; i++)
        {
            Produto p = produtoRepository.getOne(carrinhoJSON.getProdutos().get(i).getId());
            for(int k = 0; k < carrinhoJSON.getProdutos().get(i).getQuantidade(); k++)
            {
                produtos.add(p);
            }
            
        }
        
        Pedido novo_pedido = new Pedido();
        
        
        novo_pedido.setIdEndereco(carrinhoJSON.getIdEndereco());
        novo_pedido.setIdCliente(carrinhoJSON.getCliente());
        novo_pedido.setData(LocalDateTime.now());
        novo_pedido.setIdTipoPagamento(carrinhoJSON.getTipoPagamento());
        novo_pedido.setPrecoVenda(BigDecimal.valueOf(carrinhoJSON.getValor()));
        novo_pedido.setProdutos(produtos);
        
        pedidoRepository.save(novo_pedido);
        
        return "ok";
        
    }

    @GetMapping("/detalhe/{id}")
    public ModelAndView detalhe(@PathVariable int id){
        ModelAndView mv = new ModelAndView("detalheProduto");

        Produto p = produtoRepository.getOne(id);

        mv.addObject("produto", p);

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

            return new ModelAndView("redirect:/checkout");
        }

        return new ModelAndView("redirect:/login");
    }

}
