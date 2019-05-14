/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladora;

import java.util.ArrayList;
import proj_engii.entidade.Tipo_Despesas;

/**
 *
 * @author hiroshi
 */
public class CtrlTipoDespesa {
    private Tipo_Despesas tipo = new Tipo_Despesas();
    public ArrayList<Tipo_Despesas> buscar(String descricao){
        return tipo.buscar(descricao);
    }
}
