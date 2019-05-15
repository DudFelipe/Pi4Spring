/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sp.senac.br.pet.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author diogo.carauta
 */
@Entity
@Table(name = "tipopagamento")
public class TipoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    
    String descricao;
}
