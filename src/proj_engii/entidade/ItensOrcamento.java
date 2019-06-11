/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.entidade;

import java.sql.ResultSet;
import java.util.ArrayList;
import proj_engii.bancoc.Banco;
import proj_engii.entidade.Orcamento;

/**
 *
 * @author hiroshi
 */
public class ItensOrcamento {

    private int itensorc_cod, orc_cod, prod_cod, prod_qtde;
    String prod_desc;

    public ItensOrcamento() {
    }

    public ItensOrcamento(int itensorc_cod, int orc_cod, int prod_cod, int prod_qtde, String prod_desc) {
        this.itensorc_cod = itensorc_cod;
        this.orc_cod = orc_cod;
        this.prod_cod = prod_cod;
        this.prod_qtde = prod_qtde;
        this.prod_desc = prod_desc;
    }

    public ItensOrcamento(int orc_cod, int prod_cod, int prod_qtde, String prod_desc) {
        this.orc_cod = orc_cod;
        this.prod_cod = prod_cod;
        this.prod_qtde = prod_qtde;
        this.prod_desc = prod_desc;
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

    public String getProd_desc() {
        return prod_desc;
    }

    public void setProd_desc(String prod_desc) {
        this.prod_desc = prod_desc;
    }

    public Boolean salvar() {
        String sql = "";
        try {
            sql = "insert into itens_orc(itensorc_cod, orc_cod_fk, prod_cod_fk, prod_qtde,prod_desc) "
                    + "values(nextval('itensorc_sequence'),$1,$2,$3,'$4')";

            sql = sql.replace("$1", orc_cod + "");
            sql = sql.replace("$2", prod_cod + "");
            sql = sql.replace("$3", prod_qtde + "");
            sql = sql.replace("$4", prod_desc + "");

            System.out.println("" + sql);
            return Banco.con.manipular(sql);
        } catch (Exception e) {
            System.out.println("Erro : " + e);
        }

        return false;
    }

    public ArrayList<ItensOrcamento> busca_itens(int cod) {
        String sql = "";
        sql = "select * from itens_orc where orc_cod_fk = " + cod;

        ResultSet rs;
        ArrayList<ItensOrcamento> list = new ArrayList<>();

        try {
            rs = Banco.con.consultar(sql);
            while (rs.next()) {
                list.add(new ItensOrcamento(rs.getInt("itensorc_cod"), rs.getInt("orc_cod_fk"), rs.getInt("prod_cod_fk"), rs.getInt("prod_qtde"),
                        rs.getString("prod_desc")));
            }

            return list;
        } catch (Exception er) {
        }

        return null;
    }

    public Boolean excluir(int cod) {
        String sql = "";

        sql = "delete from itens_orc where orc_cod_fk = " + cod;

        return Banco.con.manipular(sql);
    }

    public Boolean alterar(int qtde,int cod) {
        String sql = "";
        try {
            sql = "update itens_orc set prod_qtde = $1 where itensorc_cod = " + cod;
            sql = sql.replace("$1", qtde+"");
            System.out.println("" + sql);
            return Banco.con.manipular(sql);
        } catch (Exception e) {
            System.out.println("Erro : " + e);
        }

        return false;
    }

    public Boolean excluir_itensorc(int cod) {
        String sql = "";

        sql = "delete from itens_orc where itensorc_cod = " + cod;
        System.out.println(""+sql);
        return Banco.con.manipular(sql);
    }
}
