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
    private String orc_func, orc_cliente, orc_criado, orc_validade;
    private double orc_total;
    private Boolean aprovado;

    public Orcamento() {

    }

    public Orcamento(String orc_func, double orc_total, String orc_cliente, Boolean aprovado, String orc_criado, String orc_validade) {
        this.orc_func = orc_func;
        this.orc_total = orc_total;
        this.orc_cliente = orc_cliente;
        this.aprovado = aprovado;
        this.orc_criado = orc_criado;
        this.orc_validade = orc_validade;
    }

    public Orcamento(int orc_cod, String orc_func, double orc_total, String orc_cliente, Boolean aprovado, String orc_criado, String orc_validade) {
        this.orc_cod = orc_cod;
        this.orc_func = orc_func;
        this.orc_total = orc_total;
        this.orc_cliente = orc_cliente;
        this.aprovado = aprovado;
        this.orc_criado = orc_criado;
        this.orc_validade = orc_validade;
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

    public Boolean getAprovado() {
        return aprovado;
    }

    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }

    public String getOrc_criado() {
        return orc_criado;
    }

    public void setOrc_criado(String orc_criado) {
        this.orc_criado = orc_criado;
    }

    public String getOrc_validade() {
        return orc_validade;
    }

    public void setOrc_validade(String orc_validade) {
        this.orc_validade = orc_validade;
    }

    public Boolean salvar() {
        String sql = "";

        try {
            sql = "insert into orcamento(orc_cod,orc_func,orc_total,orc_cliente,orc_aprovacao,orc_criado,orc_validade) values(nextval('orc_sequence'),'$1',$2,'$3',$4,'$5','$6')";

            sql = sql.replace("$1", orc_func);
            sql = sql.replace("$2", orc_total + "");
            sql = sql.replace("$3", orc_cliente);
            sql = sql.replace("$4", aprovado + "");
            sql = sql.replace("$5", orc_criado);
            sql = sql.replace("$6", orc_validade);
            System.out.println("" + sql);
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
        ResultSet rs;
        ArrayList<Orcamento> list = new ArrayList<>();

        try {
            rs = Banco.con.consultar(sql);
            while (rs.next()) {
                list.add(new Orcamento(rs.getInt("orc_cod"), rs.getString("orc_func"), rs.getDouble("orc_total"),
                        rs.getString("orc_cliente"), rs.getBoolean("orc_aprovacao"), rs.getString("orc_criado"), rs.getString("orc_validade")));
            }

            return list;
        } catch (Exception er) {
        }

        return null;
    }

    public Boolean delete(int cod) {
        String sql = "";
        sql = "delete from orcamento where orc_cod = " + cod;
        return Banco.con.manipular(sql);
    }

    public Boolean aprovar(int cod) {
        String sql = "";
        sql = "update orcamento set orc_aprovacao = true where orc_cod = " + cod;
        return Banco.con.manipular(sql);
    }

    public Boolean atualizar_total(double total, int cod) {
        String sql = "";
        sql = "update orcamento set orc_total = $1 where orc_cod = " + cod;
        sql = sql.replace("$1", total+"");
        return Banco.con.manipular(sql);
    }
}
