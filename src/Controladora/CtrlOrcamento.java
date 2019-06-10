/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladora;

import proj_engii.entidade.ItensOrcamento;
import java.util.ArrayList;
import proj_engii.entidade.Orcamento;

/**
 *
 * @author hiroshi
 */
public class CtrlOrcamento {

    private Orcamento orc = new Orcamento();
    private ItensOrcamento itens = new ItensOrcamento();

    public CtrlOrcamento() {

    }

    public Boolean salvar(Object[] ob) {
        try {
            orc = new Orcamento((String) ob[0], (Double) ob[1], (String) ob[2]);
            return orc.salvar();
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return false;

    }

    public Boolean salvar_itens(int orc_cod, int prod_cod, int qtde, String desc) {
        try {
            itens = new ItensOrcamento(orc_cod, prod_cod, qtde, desc);
            return itens.salvar();
        } catch (Exception e) {

        }
        return false;
    }

    public ArrayList<Orcamento> buscar(String nome) {
        try {
            orc = new Orcamento();
            return orc.busca(nome);
        } catch (Exception e) {
            System.out.println("aqui" + e);
        }
        return null;
    }

    public ArrayList<ItensOrcamento> buscar_itens(int cod) {
        try {
            return itens.busca_itens(cod);
        } catch (Exception e) {
            System.out.println("aqui" + e);
        }
        return null;
    }

    public Boolean excluir(int cod) {
        try {
            return itens.excluir(cod);
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return false;
    }

    public Boolean excluir_orcamento(int cod) {
        try {
            return orc.delete(cod);
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return false;
    }

    public Boolean altera_itensorc(int qtde) {
        try {
            return itens.alterar(qtde);
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return false;
    }
}
