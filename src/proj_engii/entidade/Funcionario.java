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
public class Funcionario {

    private int codigo, numero;
    private String nome, cep, cpf, endereco, cidade, email, sexo, login, nivel, senha, telefone, dtAdmissao;

    public Funcionario() {

    }

    public Funcionario(int codigo, int numero, String nome, String cep, String cpf, String endereco, String telefone, String cidade, String email, String sexo, String login, String nivel, String senha, String dtAdmissao) {
        this.codigo = codigo;
        this.nome = nome;
        this.cep = cep;
        this.cpf = cpf;
        this.endereco = endereco;
        this.numero = numero;
        this.telefone = telefone;
        this.cidade = cidade;
        this.email = email;
        this.sexo = sexo;
        this.login = login;
        this.nivel = nivel;
        this.senha = senha;
        this.dtAdmissao = dtAdmissao;
    }

    public Funcionario(int numero, String nome, String cep, String cpf, String endereco, String cidade, String email, String sexo, String login, String nivel, String senha, String telefone, String dtAdmissao) {
        this.nome = nome;
        this.cep = cep;
        this.cpf = cpf;
        this.endereco = endereco;
        this.numero = numero;
        this.telefone = telefone;
        this.cidade = cidade;
        this.email = email;
        this.sexo = sexo;
        this.login = login;
        this.nivel = nivel;
        this.senha = senha;
        this.dtAdmissao = dtAdmissao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDtAdmissao() {
        return dtAdmissao;
    }

    public void setDtAdmissao(String dtAdmissao) {
        this.dtAdmissao = dtAdmissao;
    }

    public boolean salvar(Funcionario func) {
        try {
            String sql = "insert into funcionario (func_cod,func_nome, func_cpf, func_cep, func_endereco, func_cidade, "
                    + "func_sexo, func_telefone, func_email, func_numero, func_login, func_senha, func_nivel, func_dtAdmissao)"
                    + " values (nextval('func_sequence'),'$n' ,'$1', '$2', '$3', '$4', '$5', '$6', '$7', '$8', '$9', '$a', '$b', '$c')";
            //System.out.println(""+nome+numero+login+senha+nivel+dtAdmissao);
            sql = sql.replace("$1", cpf);
            sql = sql.replace("$2", cep);
            sql = sql.replace("$3", endereco);
            sql = sql.replace("$4", cidade);
            sql = sql.replace("$5", sexo);
            sql = sql.replace("$6", telefone);
            sql = sql.replace("$7", email);
            sql = sql.replace("$8", numero + "");
            sql = sql.replace("$9", login);
            sql = sql.replace("$a", senha);
            sql = sql.replace("$b", nivel);
            sql = sql.replace("$c", dtAdmissao);
            sql = sql.replace("$n", nome);

            System.out.println("" + sql);
            return Banco.con.manipular(sql);
        } catch (Exception e) {
            System.out.println("Erro banco " + e);
        }
        return false;
    }

    public ArrayList<Funcionario> buscar(String nome, String tipo) {
        String sql = "";
        if (nome.isEmpty()) {
            sql = "select * from funcionario";
        } else {
            if (tipo.equals("nome")) {
                sql = "select * from funcionario where func_nome like" + "'%$1%'";
            } else {
                sql = "select * from funcionario where func_login like " + "'%$1%'";
            }
            sql = sql.replace("$1", nome);
        }
        ResultSet rs;
        ArrayList<Funcionario> list = new ArrayList<>();

        try {
            rs = Banco.con.consultar(sql);
            while (rs.next()) {
                list.add(new Funcionario(rs.getInt("func_cod"), rs.getInt("func_numero"), rs.getString("func_nome"),
                        rs.getString("func_cep"), rs.getString("func_cpf"), rs.getString("func_endereco"),
                        rs.getString("func_telefone"), rs.getString("func_cidade"), rs.getString("func_email"), rs.getString("func_sexo"), rs.getString("func_login"),
                        rs.getString("func_nivel"), rs.getString("func_senha"), rs.getString("func_dtAdmissao")));
            }
        } catch (Exception er) {
        }

        return list;
    }

    public Boolean alterar(Funcionario func, int cod) {
        String sql = "";
        try {
            sql = "update funcionario"
                    + " set func_numero = $2, func_cpf = '$3', func_cep = '$4', func_endereco = '$5', func_cidade = '$6', "
                    + "func_sexo = '$7', func_telefone = '$8', func_email = '$9', func_login = '$a', func_nivel = '$b', func_dtadmissao = '$c', func_senha = '$d', func_nome = '$e'"
                    + "where func_cod = '$r'";

            sql = sql.replace("$2", func.getNumero() + "");
            sql = sql.replace("$3", func.getCpf());
            sql = sql.replace("$4", func.getCep());
            sql = sql.replace("$5", func.getEndereco());
            sql = sql.replace("$6", func.getCidade());
            sql = sql.replace("$7", func.getSexo());
            sql = sql.replace("$8", func.getTelefone());
            sql = sql.replace("$9", func.getEmail());
            sql = sql.replace("$a", func.getLogin());
            sql = sql.replace("$b", func.getNivel());
            sql = sql.replace("$c", func.getDtAdmissao());
            sql = sql.replace("$d", func.getSenha());
            sql = sql.replace("$e", func.getNome());
            sql = sql.replace("$r", cod + "");
            System.out.println("" + sql);
            return Banco.con.manipular(sql);

        } catch (Exception e) {
            System.out.println("" + e);
        }
        return false;
    }

    public Boolean excluir(int codigo) {
        String sql = "delete from funcionario where func_cod = " + "'$1'";
        sql = sql.replace("$1", codigo + "");
        System.out.println("" + sql);
        return Banco.con.manipular(sql);
    }

    public ArrayList<Funcionario> logar(String login, String senha) {
        String sql = "select * from funcionario where func_login = " + "'$1'" + " and func_senha = " + "'$2'";
        sql = sql.replace("$1", login);
        sql = sql.replace("$2", senha);

        ResultSet rs;
        ArrayList<Funcionario> list = new ArrayList<>();
        System.out.println("" + sql);
        try {
            rs = Banco.con.consultar(sql);
            while (rs.next()) {
                list.add(new Funcionario(rs.getInt("func_cod"), rs.getInt("func_numero"), rs.getString("func_nome"),
                        rs.getString("func_cep"), rs.getString("func_cpf"), rs.getString("func_endereco"),
                        rs.getString("func_telefone"), rs.getString("func_cidade"), rs.getString("func_email"), rs.getString("func_sexo"), rs.getString("func_login"),
                        rs.getString("func_nivel"), rs.getString("func_senha"), rs.getString("func_dtAdmissao")));
            }
            return list;
        } catch (Exception er) {
        }
        return null;
    }

    public ArrayList<Funcionario> busca_login(String login, int cod) {
        String sql = "";
        if (cod == -1) {
            sql = "select * from funcionario where func_login = " + "'$1'";
            sql = sql.replace("$1", login);
        } else {
            sql = "select * from funcionario where func_login = " + "'$1'" + "and func_cod = " +cod;
            sql = sql.replace("$1", login);
        }

        ResultSet rs;
        ArrayList<Funcionario> list = new ArrayList<>();
        try {
            rs = Banco.con.consultar(sql);
            while (rs.next()) {
                list.add(new Funcionario(rs.getInt("func_cod"), rs.getInt("func_numero"), rs.getString("func_nome"),
                        rs.getString("func_cep"), rs.getString("func_cpf"), rs.getString("func_endereco"),
                        rs.getString("func_telefone"), rs.getString("func_cidade"), rs.getString("func_email"), rs.getString("func_sexo"), rs.getString("func_login"),
                        rs.getString("func_nivel"), rs.getString("func_senha"), rs.getString("func_dtAdmissao")));
            }
            return list;
        } catch (Exception er) {
        }
        return null;
    }

    @Override
    public String toString() {
        return nome;
    }
}
