/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladora;

import java.util.ArrayList;
import proj_engii.entidade.Produto;

/**
 *
 * @author Hiroshi
 */
public class CtrlProduto {
    private Produto prod = new Produto();
    
    public ArrayList<Produto> buscar(String descricao){
        return prod.buscar(descricao);
    }
    
    public Boolean alterar(Produto p){
        return prod.alterar(p);
    }
}
