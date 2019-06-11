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
            orc = new Orcamento((String) ob[0], (Double) ob[1], (String) ob[2], (Boolean) ob[3], (String) ob[4], (String) ob[5]);
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

    public Boolean altera_itensorc(int qtde, int cod) {
        try {
            return itens.alterar(qtde, cod);
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return false;
    }

    public Boolean aprovar_orc(int cod) {
        return orc.aprovar(cod);
    }

    public Boolean atualizar_total(double total, int cod) {
        return orc.atualizar_total(total, cod);
    }

    public Boolean excluir_itensorc(int cod) {
        return itens.excluir_itensorc(cod);
    }
}
