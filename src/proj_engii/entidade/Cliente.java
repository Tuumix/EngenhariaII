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
public class Cliente {
    private int cli_cod, cli_num;
    private String cli_cpf, cli_cep, cli_email, cli_nome, cli_telefone;

    public Cliente(){}
    
    public Cliente(int cli_cod, int cli_num, String cli_cpf, String cli_cep, String cli_email, String cli_nome, String cli_telefone) {
        this.cli_cod = cli_cod;
        this.cli_num = cli_num;
        this.cli_cpf = cli_cpf;
        this.cli_cep = cli_cep;
        this.cli_email = cli_email;
        this.cli_nome = cli_nome;
        this.cli_telefone = cli_telefone;
    }

    public int getCli_cod() {
        return cli_cod;
    }

    public void setCli_cod(int cli_cod) {
        this.cli_cod = cli_cod;
    }

    public int getCli_num() {
        return cli_num;
    }

    public void setCli_num(int cli_num) {
        this.cli_num = cli_num;
    }

    public String getCli_cpf() {
        return cli_cpf;
    }

    public void setCli_cpf(String cli_cpf) {
        this.cli_cpf = cli_cpf;
    }

    public String getCli_cep() {
        return cli_cep;
    }

    public void setCli_cep(String cli_cep) {
        this.cli_cep = cli_cep;
    }

    public String getCli_email() {
        return cli_email;
    }

    public void setCli_email(String cli_email) {
        this.cli_email = cli_email;
    }

    public String getCli_nome() {
        return cli_nome;
    }

    public void setCli_nome(String cli_nome) {
        this.cli_nome = cli_nome;
    }

    public String getCli_telefone() {
        return cli_telefone;
    }

    public void setCli_telefone(String cli_telefone) {
        this.cli_telefone = cli_telefone;
    }
    
    public ArrayList<Cliente> buscar() { //    public Cliente(int cli_cod, int cli_num, String cli_cpf, String cli_cep, String cli_email, String cli_nome, String cli_telefone) {

        String sql = "select * from cliente";
        System.out.println(""+sql);
        ResultSet rs;
        ArrayList<Cliente> list = new ArrayList<>();

        try {
            rs = Banco.con.consultar(sql);
            while (rs.next()) {
                list.add(new Cliente(rs.getInt("cli_cod"), rs.getInt("cli_num"), rs.getString("cli_cpf"),
                        rs.getString("cli_cep"), rs.getString("cli_email"), rs.getString("cli_nome"),
                        rs.getString("cli_telefone")));
            }
        } catch (Exception er) {
        }

        return list;
    }
    
    @Override
    public String toString() {
        return cli_nome;
    }
}
