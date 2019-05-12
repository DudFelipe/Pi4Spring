package sp.senac.br.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sp.senac.br.pet.model.Categoria;
import sp.senac.br.pet.model.Produto;
import sp.senac.br.pet.repository.CategoriaRepository;
import sp.senac.br.pet.repository.ProdutoRepository;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;


    @GetMapping()
    public ModelAndView listarProdutos(){
        ModelAndView mv = new ModelAndView("todos-produtos");

        List<Produto> produtos = produtoRepository.buscaProdutosAtivos();

        List<Categoria> categorias = categoriaRepository.findAll();

        mv.addObject("produtos", produtos);
        mv.addObject("categorias", categorias);

        return mv;
    }


}
