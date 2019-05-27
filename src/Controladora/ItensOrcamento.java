/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladora;

import proj_engii.bancoc.Banco;

/**
 *
 * @author hiroshi
 */
public class ItensOrcamento {

    private int itensorc_cod, orc_cod, prod_cod, prod_qtde;

    public ItensOrcamento() {
    }

    public ItensOrcamento(int itensorc_cod, int orc_cod, int prod_cod, int prod_qtde) {
        this.itensorc_cod = itensorc_cod;
        this.orc_cod = orc_cod;
        this.prod_cod = prod_cod;
        this.prod_qtde = prod_qtde;
    }

    public ItensOrcamento(int orc_cod, int prod_cod, int prod_qtde) {
        this.orc_cod = orc_cod;
        this.prod_cod = prod_cod;
        this.prod_qtde = prod_qtde;
    }

    public int getItensorc_cod() {
        return itensorc_cod;
    }

    public void setItensorc_cod(int itensorc_cod) {
        this.itensorc_cod = itensorc_cod;
    }

    public int getOrc_cod() {
        return orc_cod;
    }

    public void setOrc_cod(int orc_cod) {
        this.orc_cod = orc_cod;
    }

    public int getProd_cod() {
        return prod_cod;
    }

    public void setProd_cod(int prod_cod) {
        this.prod_cod = prod_cod;
    }

    public int getProd_qtde() {
        return prod_qtde;
    }

    public void setProd_qtde(int prod_qtde) {
        this.prod_qtde = prod_qtde;
    }

    public Boolean salvar() {
        String sql = "";
        try {
            sql = "insert into itens_orc(itensorc_cod, orc_cod_fk, prod_cod_fk, prod_qtde) "
                    + "values(nextval('itensorc_sequence'),$1,$2,$3)";

            sql = sql.replace("$1", orc_cod + "");
            sql = sql.replace("$2", prod_cod + "");
            sql = sql.replace("$3", prod_qtde + "");
            System.out.println(""+sql);
            return Banco.con.manipular(sql);
        } catch (Exception e) {
            System.out.println("Erro : " + e);
        }

        return false;
    }
}
