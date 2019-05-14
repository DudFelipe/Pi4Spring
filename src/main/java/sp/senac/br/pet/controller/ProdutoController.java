package sp.senac.br.pet.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sp.senac.br.pet.model.Categoria;
import sp.senac.br.pet.model.Produto;
import sp.senac.br.pet.repository.CategoriaRepository;
import sp.senac.br.pet.repository.ProdutoRepository;

import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sp.senac.br.pet.model.Carrinho;
import sp.senac.br.pet.model.Pedido;
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
    public ModelAndView checkout(){
        ModelAndView mv = new ModelAndView("checkout");
        return mv;
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
        
        
        novo_pedido.setIdCliente(carrinhoJSON.getCliente());
        novo_pedido.setData(LocalDateTime.now());
        novo_pedido.setIdTipoPagamento(carrinhoJSON.getTipoPagamento());
        novo_pedido.setPrecoVenda(BigDecimal.valueOf(carrinhoJSON.getValor()));
        novo_pedido.setProdutos(produtos);
        
        pedidoRepository.save(novo_pedido);
        
        return "ok";
        
    }


}
