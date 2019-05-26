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
 * @author Hiroshi
 */
public class Despesa {

    private int desp_cod;
    private String desp_descricao;
    private Double desp_valor;
    private String desp_tipo;
    private String desp_dtEmissao;
    private String desp_dtVencimento;
    private String desp_dtPagamento;
    private int desp_tipocod;

    public Despesa() {
    }

    public Despesa(int desp_cod, String desp_descricao, Double desp_valor, String desp_tipo, String desp_dtEmissao, String desp_dtVencimento, int desp_tipocod, String desp_dtPagamento) {
        this.desp_cod = desp_cod;
        this.desp_descricao = desp_descricao;
        this.desp_valor = desp_valor;
        this.desp_tipo = desp_tipo;
        this.desp_dtEmissao = desp_dtEmissao;
        this.desp_dtVencimento = desp_dtVencimento;
        this.desp_tipocod = desp_tipocod;
        this.desp_dtPagamento = desp_dtPagamento;
    }

    public int getDesp_cod() {
        return desp_cod;
    }

    public void setDesp_cod(int desp_cod) {
        this.desp_cod = desp_cod;
    }

    public String getDesp_descricao() {
        return desp_descricao;
    }

    public void setDesp_descricao(String desp_descricao) {
        this.desp_descricao = desp_descricao;
    }

    public Double getDesp_valor() {
        return desp_valor;
    }

    public void setDesp_valor(Double desp_valor) {
        this.desp_valor = desp_valor;
    }

    public String getDesp_tipo() {
        return desp_tipo;
    }

    public void setDesp_tipo(String desp_tipo) {
        this.desp_tipo = desp_tipo;
    }

    public String getDesp_dtEmissao() {
        return desp_dtEmissao;
    }

    public void setDesp_dtEmissao(String desp_dtEmissao) {
        this.desp_dtEmissao = desp_dtEmissao;
    }

    public String getDesp_dtVencimento() {
        return desp_dtVencimento;
    }

    public void setDesp_dtVencimento(String desp_dtVencimento) {
        this.desp_dtVencimento = desp_dtVencimento;
    }

    public int getDesp_tipocod() {
        return desp_tipocod;
    }

    public void setDesp_tipocod(int desp_tipocod) {
        this.desp_tipocod = desp_tipocod;
    }

    public String getDesp_dtPagamento() {
        return desp_dtPagamento;
    }

    public void setDesp_dtPagamento(String desp_dtPagamento) {
        this.desp_dtPagamento = desp_dtPagamento;
    }

    public boolean salvar(Despesa desp) {
        try {
            String sql = "insert into despesa (desp_codigo,desp_tipo, desp_descricao, desp_valor, desp_dtEmissao, desp_dtVencimento, desp_tipocod, desp_dtpagamento)"
                    + " values (nextval('desp_sequence'),'$1' ,'$2', $3, '$4', '$5', $6, '$7')";
            //System.out.println(""+nome+numero+login+senha+nivel+dtAdmissao);
            System.out.println(""+desp.getDesp_dtPagamento());
            sql = sql.replace("$1", desp.getDesp_tipo());
            sql = sql.replace("$2", desp.getDesp_descricao());
            sql = sql.replace("$3", desp.getDesp_valor() + "");
            sql = sql.replace("$4", desp.getDesp_dtEmissao());
            sql = sql.replace("$5", desp.getDesp_dtVencimento());
            sql = sql.replace("$6", desp.getDesp_tipocod()+"");
            sql = sql.replace("$7", desp.getDesp_dtPagamento());

            System.out.println("" + sql);
            return Banco.con.manipular(sql);
        } catch (Exception e) {
            System.out.println("Erro banco " + e);
        }
        return false;
    }

    public ArrayList<Despesa> buscar(String nome, String tipo) {
        String sql = "";
        if (nome.isEmpty()) {
            sql = "select * from despesa";
        } else {
            if (tipo.equals("descricao")) {
                sql = "select * from despesa where desp_descricao like" + "'%$1%'";
            } else {
                sql = "select * from despesa where desp_tipo = " + "'$1'";
            }
        }
        sql = sql.replace("$1", nome);
        System.out.println("" + sql);
        ResultSet rs;
        ArrayList<Despesa> list = new ArrayList<>();

        try {
            rs = Banco.con.consultar(sql);
            while (rs.next()) {
                list.add(new Despesa(rs.getInt("desp_codigo"), rs.getString("desp_descricao"), rs.getDouble("desp_valor"),
                        rs.getString("desp_tipo"), rs.getString("desp_dtEmissao"), rs.getString("desp_dtVencimento"), rs.getInt("desp_tipocod"), rs.getString("desp_dtpagamento")));
            }
        } catch (Exception er) {
        }

        return list;
    }

    public Boolean excluir(int codigo) {
        String sql = "delete from despesa where desp_codigo = " + "'$1'";
        sql = sql.replace("$1", codigo + "");
        System.out.println("" + sql);
        return Banco.con.manipular(sql);
    }

    public Boolean alterar(Despesa desp) {
        String sql = "update despesa set desp_valor = '$1', desp_dtemissao = '$2',"
                + " desp_dtvencimento = '$3', desp_descricao = '$4', desp_tipo = '$5', desp_tipocod = $7"
                + "where desp_codigo = $6";
        sql = sql.replace("$1", desp.desp_valor + "");
        sql = sql.replace("$2", desp.desp_dtEmissao);
        sql = sql.replace("$3", desp.desp_dtVencimento);
        sql = sql.replace("$4", desp.desp_descricao);
        sql = sql.replace("$5", desp.desp_tipo + "");
        sql = sql.replace("$6", desp.desp_cod + "");
        sql = sql.replace("$7", desp.desp_tipocod+"");
        System.out.println(""+sql);
        return Banco.con.manipular(sql);
    }
}
