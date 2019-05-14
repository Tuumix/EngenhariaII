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
public class Tipo_Despesas {

    private int codigo;
    private String descricao;

    public Tipo_Despesas() {
    }

    public Tipo_Despesas(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
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

    @Override
    public String toString() {
        return descricao;
    }

    public ArrayList<Tipo_Despesas> buscar(String descricao) {
        String sql = "";
        if (descricao.isEmpty()) {
            sql = "select * from tipo_despesa";
        }

        System.out.println("" + sql);
        ResultSet rs;
        ArrayList<Tipo_Despesas> list = new ArrayList<>();

        try {
            rs = Banco.con.consultar(sql);
            while (rs.next()) {
                list.add(new Tipo_Despesas(rs.getInt("desp_tipocod"), rs.getString("desp_tipodescri")));
            }
        } catch (Exception er) {
        }

        return list;
    }
}