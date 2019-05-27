/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladora;

import proj_engii.entidade.Orcamento;

/**
 *
 * @author hiroshi
 */
public class CtrlOrcamento {

    private Orcamento orc;
    private ItensOrcamento itens;

    public CtrlOrcamento() {

    }

    public Boolean salvar(Object[] ob) {
        try {
            orc = new Orcamento((String) ob[0], (Double) ob[1]);
            return orc.salvar();
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return false;

    }

    public Boolean salvar_itens(int orc_cod, int prod_cod, int qtde){
        try{
            itens = new ItensOrcamento(orc_cod, prod_cod, qtde);
            return itens.salvar();
        }catch(Exception e){
            
        }
        return false;
    }
}
