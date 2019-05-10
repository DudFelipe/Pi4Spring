package sp.senac.br.pet.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sp.senac.br.pet.model.Usuario;

@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public ModelAndView index(Authentication authentication){
        Usuario u = null;

        if(authentication != null){
            u = (Usuario)authentication.getPrincipal();
        }

        ModelAndView mv = new ModelAndView("index").addObject("usuario", u);

        return mv;
    }
}
