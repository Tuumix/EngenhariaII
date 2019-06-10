/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladora;

import java.util.ArrayList;
import proj_engii.entidade.Funcionario;

/**
 *
 * @author hiroshi
 */
public class CtrlFuncionario {

    private Funcionario func = new Funcionario();

    public CtrlFuncionario() {
    }

    public Boolean salvar(Object[] ob) {
        try {
            func = new Funcionario((Integer) ob[0], (String) ob[1], (String) ob[2],
                    (String) ob[3], (String) ob[4], (String) ob[5], (String) ob[6], (String) ob[7],
                    (String) ob[8], (String) ob[9], (String) ob[10], (String) ob[11], (String) ob[12]);
            return func.salvar(func);
        } catch (Exception e) {
            System.out.println("Erro" + e);
        }
        return false;
    }

    public ArrayList<Funcionario> buscar(String nome, String tipo) {
        try {
            return func.buscar(nome, tipo);
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return null;
    }

    public Boolean alterar(Object[] ob, int cod) {
        try {
            func = new Funcionario((Integer) ob[0], (String) ob[1], (String) ob[2],
                    (String) ob[3], (String) ob[4], (String) ob[5], (String) ob[6], (String) ob[7],
                    (String) ob[8], (String) ob[9], (String) ob[10], (String) ob[11], (String) ob[12]);
            return func.alterar(func, cod);
        } catch (Exception e) {
            System.out.println("Erro" + e);
        }
        return false;
    }

    public Boolean excluir(int codigo) {
        try {
            return func.excluir(codigo);
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return false;
    }

    public ArrayList<Funcionario> logar(String login, String senha) {
        try {
            return func.logar(login, senha);
        } catch (Exception e) {
            System.out.println("" + e);
        }

        return null;
    }

    public ArrayList<Funcionario> buscar_login(String login, int cod) {
        try {
            return func.busca_login(login,cod);
        } catch (Exception e) {
            System.out.println("" + e);
        }

        return null;
    }
}
