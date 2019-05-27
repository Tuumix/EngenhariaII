/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.entidade;

import proj_engii.bancoc.Banco;

/**
 *
 * @author hiroshi
 */
public class Orcamento {

    private int orc_cod;
    private String orc_func;
    private double orc_total;

    public Orcamento() {

    }

    public Orcamento(String orc_func, double orc_total) {
        this.orc_func = orc_func;
        this.orc_total = orc_total;
    }

    public Orcamento(int orc_cod, String orc_func, double orc_total) {
        this.orc_cod = orc_cod;
        this.orc_func = orc_func;
        this.orc_total = orc_total;
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

    public Boolean salvar() {
        String sql = "";

        try {
            sql = "insert into orcamento(orc_cod,orc_func,orc_total) values(nextval('orc_sequence'),'$1',$2)";

            sql = sql.replace("$1", orc_func);
            sql = sql.replace("$2", orc_total + "");
            
            return Banco.con.manipular(sql);

        } catch (Exception e) {
            System.out.println("" + e);
        }

        return false;
    }
    
    public Boolean delete(){
        return false;
    }
}
