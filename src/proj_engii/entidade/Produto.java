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
public class Produto {

    int codigo;
    private String descricao;
    private double valor;
    private int qtde;

    public Produto() {

    }

    public Produto(int codigo, String descricao, double valor, int qtde) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.valor = valor;
        this.qtde = qtde;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public ArrayList<Produto> buscar(String prod) {
        try {
            String sql = "";
            if (prod.isEmpty()) {
                sql = "select * from produto";
            } else {
                sql = "select * from produto where prod_descricao like " + "'%$1%'";
            }
            sql = sql.replace("$1", prod);
            ResultSet rs;
            ArrayList<Produto> list = new ArrayList<>();
            
            try {
                rs = Banco.con.consultar(sql);
                while (rs.next()) {
                    list.add(new Produto(rs.getInt("prod_cod"), rs.getString("prod_descricao"), rs.getDouble("prod_valor"), rs.getInt("prod_qtde")));
                }
                return list;
            } catch (Exception er) {
            }
        } catch (Exception e) {
            System.out.println("Erro banco " + e);
        }
        return null;
    }

    public Boolean alterar(Produto p) {
        String sql = "";
        try {
            sql = "update produto set prod_qtde = $1 where prod_cod  = " + "$2";
            sql = sql.replace("$1", p.getQtde() + "");
            sql = sql.replace("$2", p.getCodigo() + "");

            System.out.println("" + sql);
            return Banco.con.manipular(sql);
        } catch (Exception e) {

        }
        return false;
    }

}
