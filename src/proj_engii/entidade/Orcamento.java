/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.entidade;

import java.sql.ResultSet;
import java.util.ArrayList;
import proj_engii.bancoc.Banco;

/**
 *
 * @author hiroshi
 */
public class Orcamento {

    private int orc_cod;
    private String orc_func, orc_cliente;
    private double orc_total;

    public Orcamento() {

    }

    public Orcamento(String orc_func, double orc_total, String orc_cliente) {
        this.orc_func = orc_func;
        this.orc_total = orc_total;
        this.orc_cliente = orc_cliente;
    }

    public Orcamento(int orc_cod, String orc_func, double orc_total, String orc_cliente) {
        this.orc_cod = orc_cod;
        this.orc_func = orc_func;
        this.orc_total = orc_total;
        this.orc_cliente = orc_cliente;

    }

    public int getOrc_cod() {
        return orc_cod;
    }

    public void setOrc_cod(int orc_cod) {
        this.orc_cod = orc_cod;
    }

    public String getOrc_func() {
        return orc_func;
    }

    public void setOrc_func(String orc_func) {
        this.orc_func = orc_func;
    }

    public double getOrc_total() {
        return orc_total;
    }

    public void setOrc_total(double orc_total) {
        this.orc_total = orc_total;
    }

    public String getOrc_cliente() {
        return orc_cliente;
    }

    public void setOrc_cliente(String orc_cliente) {
        this.orc_cliente = orc_cliente;
    }

    public Boolean salvar() {
        String sql = "";

        try {
            sql = "insert into orcamento(orc_cod,orc_func,orc_total,orc_cliente) values(nextval('orc_sequence'),'$1',$2,'$3')";

            sql = sql.replace("$1", orc_func);
            sql = sql.replace("$2", orc_total + "");
            sql = sql.replace("$3", orc_cliente);

            return Banco.con.manipular(sql);

        } catch (Exception e) {
            System.out.println("" + e);
        }

        return false;
    }

    public ArrayList<Orcamento> busca(String nome) {
        String sql = "";
        if (nome.isEmpty()) {
            sql = "select * from orcamento";
        } else {
            sql = "select * from orcamento where orc_cliente like" + "'%$1%'";
            sql = sql.replace("$1", nome);

        }
        System.out.println(""+sql);
        ResultSet rs;
        ArrayList<Orcamento> list = new ArrayList<>();

        try {
            rs = Banco.con.consultar(sql);
            while (rs.next()) {
                list.add(new Orcamento(rs.getInt("orc_cod"), rs.getString("orc_func"), rs.getDouble("orc_total"),
                        rs.getString("orc_cliente")));
            }

            return list;
        } catch (Exception er) {
        }

        return null;
    }

    public Boolean delete(int cod) {
        String sql = "";
        sql = "delete from orcamento where orc_cod = " + cod;
        System.out.println(""+sql);
        return Banco.con.manipular(sql);
    }
}
