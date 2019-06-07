/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladora;

import java.util.ArrayList;
import proj_engii.entidade.Despesa;

/**
 *
 * @author Hiroshi
 */
public class CtrlDespesa {

    private Despesa desp = new Despesa();

    public CtrlDespesa() {
    }

    public Boolean salvar(Despesa desp) {
        return desp.salvar(desp);
    }

    public ArrayList<Despesa> buscar(String nome, String tipo) {
        try {
            return desp.buscar(nome, tipo);
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return null;
    }
    
    public Boolean excluir(int codigo)
    {
        return desp.excluir(codigo);
    }
    
    public Boolean alterar(Despesa desp){
        return desp.alterar(desp);
    }
    
    public ArrayList<Despesa> between_dates(String date1, String date2){
        return desp.getBetweenDates(date1, date2);
    }
}
