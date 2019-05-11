/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.entidade;

/**
 *
 * @author hiroshi
 */
public class Cidade {
    private int codigo_cidade;
    private String cidade;

    public Cidade(int codigo_cidade, String cidade) {
        this.codigo_cidade = codigo_cidade;
        this.cidade = cidade;
    }

    public int getCodigo_cidade() {
        return codigo_cidade;
    }

    public void setCodigo_cidade(int codigo_cidade) {
        this.codigo_cidade = codigo_cidade;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    
}
