/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.telas;

import Controladora.CtrlTipoDespesa;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import proj_engii.bancoc.Banco;
import proj_engii.entidade.Tipo_Despesas;

/**
 * FXML Controller class
 *
 * @author hiroshi
 */
public class TelaTipoDespesasController implements Initializable {

    @FXML
    private TableColumn<?, ?> col_codigo;
    @FXML
    private TableColumn<?, ?> col_descricao;
    @FXML
    private JFXTextField txt_descricao;
    @FXML
    private TableView<Tipo_Despesas> tabela;
    private ArrayList<Tipo_Despesas> list_tipodesp = new ArrayList<>();
    @FXML
    private JFXButton botao_novo;
    @FXML
    private JFXButton botao_gravar;
    @FXML
    private JFXButton botao_alterar;
    @FXML
    private JFXButton botao_buscar;
    @FXML
    private JFXButton botao_sair;
    @FXML
    private JFXButton botao_pesquisar;
    @FXML
    private JFXButton botao_confirmar;
    private CtrlTipoDespesa controladora_tipo = new CtrlTipoDespesa();
    private Tipo_Despesas tipo = new Tipo_Despesas();
    private Object[] ob = new Object[3];

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init_botoes(false, true, true, false, true); //novo, gravar, alterar, buscar, confirmar
        setCells();
        setCampos(true, true);
        list_tipodesp = controladora_tipo.buscar("");
        tabela.setItems(FXCollections.observableArrayList(list_tipodesp));
    }

    @FXML
    private void btnPesquisar(ActionEvent event) {
        list_tipodesp = controladora_tipo.buscar(txt_descricao.getText());
        tabela.setItems(FXCollections.observableArrayList(list_tipodesp));
    }

    @FXML
    private void btnNovo(ActionEvent event) {
        setCampos(false, true);
        botao_pesquisar.setText("Cadastrar");
        init_botoes(true, false, true, !false, true); //novo, gravar, alterar, buscar, confirmar
        
    }

    @FXML
    private void btnGravar(ActionEvent event) {
        try {
            int cod = Banco.con.getMaxPK("tipo_despesa", "desp_tipocod") + 1;
            ob[0] = cod;
            ob[1] = txt_descricao.getText();
            controladora_tipo.salvar(ob);
            list_tipodesp = controladora_tipo.buscar("");
            tabela.setItems(FXCollections.observableArrayList(list_tipodesp));
        } catch (Exception e) {
            System.out.println("" + e);
        }

    }

    @FXML
    private void btnAlterar(ActionEvent event) {
        try {
            ob[0] = tipo.getCodigo();
            ob[1] = txt_descricao.getText();
            if (controladora_tipo.alterar(ob)) {
                System.out.println("ok");
            }
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

    @FXML
    private void btnBuscar(ActionEvent event) {
        setCampos(false, false);
        init_botoes(true, true, true, true, false);
        list_tipodesp = controladora_tipo.buscar("");
        tabela.setItems(FXCollections.observableArrayList(list_tipodesp));
    }

    @FXML
    private void btnSair(ActionEvent event) {
    }

    private void init_botoes(Boolean b1, Boolean b2, Boolean b3, Boolean b4, Boolean b5) {
        botao_novo.setDisable(b1);
        botao_gravar.setDisable(b2);
        botao_alterar.setDisable(b3);
        botao_buscar.setDisable(b4);
        botao_confirmar.setDisable(b5);
    }

    @FXML
    private void btnConfirmar(ActionEvent event) {
    }

    @FXML
    private void onclick(MouseEvent event) {
        tipo = tabela.getSelectionModel().getSelectedItem();
        txt_descricao.setText(tipo.getDescricao());
    }

    private void setCells() {
        col_codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        col_descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
    }

    private void setCampos(Boolean b1, Boolean b2) {
        txt_descricao.setDisable(b1);
        tabela.setDisable(b2);
    }
}
