/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.telas;

import Controladora.CtrlDespesa;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import proj_engii.entidade.Despesa;

/**
 * FXML Controller class
 *
 * @author Hiroshi
 */
public class TelaBuscaDespesasController implements Initializable {

    @FXML
    private AnchorPane tela;
    @FXML
    private JFXComboBox<String> cbTipo;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private TableView<Despesa> tabela;
    @FXML
    private TableColumn<?, ?> col_codigo;
    @FXML
    private TableColumn<?, ?> col_descricao;
    @FXML
    private TableColumn<?, ?> col_tipo;
    @FXML
    private TableColumn<?, ?> col_valor;
    @FXML
    private TableColumn<?, ?> col_dtVencimento;
    @FXML
    private TableColumn<?, ?> col_dtemissao;
    private CtrlDespesa controladora_desp = new CtrlDespesa();
    private ArrayList<Despesa> list_despesa = new ArrayList<>();
    public static Despesa desp;
    @FXML
    private TableColumn<?, ?> col_pagamento;
    private ArrayList<Despesa> aux = new ArrayList<>();
    @FXML
    private JFXDatePicker data1;
    @FXML
    private JFXDatePicker data2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCells();
        setColors();
        cbTipo.getItems().add("Tipo");
        cbTipo.getItems().add("Descricao");
        cbTipo.getSelectionModel().select(0);
        list_despesa = controladora_desp.buscar(txtNome.getText(), "");
        tabela.setItems(FXCollections.observableArrayList(list_despesa)); 
        
        
    }

    private void setCells() {
        col_codigo.setCellValueFactory(new PropertyValueFactory<>("desp_cod"));
        col_descricao.setCellValueFactory(new PropertyValueFactory<>("desp_descricao"));
        col_dtemissao.setCellValueFactory(new PropertyValueFactory<>("desp_dtEmissao"));
        col_tipo.setCellValueFactory(new PropertyValueFactory<>("desp_tipo"));
        col_valor.setCellValueFactory(new PropertyValueFactory<>("desp_valor"));
        col_dtVencimento.setCellValueFactory(new PropertyValueFactory<>("desp_dtVencimento"));
        col_pagamento.setCellValueFactory(new PropertyValueFactory<>("desp_dtPagamento"));

    }

    @FXML
    private void btnBuscar(ActionEvent event) {
        aux = controladora_desp.between_dates(data1.getValue().toString(), data2.getValue().toString());
        tabela.setItems(FXCollections.observableArrayList(aux));
    }

    @FXML
    private void btnAlt(ActionEvent event) {
        if (tabela.getSelectionModel().getSelectedIndex() != -1) {
            if (tabela.getSelectionModel().getSelectedItem().getDesp_dtPagamento() == null) {
                try {
                    desp = new Despesa();
                    desp = tabela.getSelectionModel().getSelectedItem();
                    Parent root = FXMLLoader.load(getClass().getResource("/proj_engii/telas/TelaLançarDespesas.fxml"));
                    tela.getChildren().clear();
                    tela.getChildren().add(root);
                } catch (Exception e) {
                    System.out.println("Erro" + e);
                    Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro! " + e, ButtonType.OK);
                    a.showAndWait();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Não é possível alterar uma despesa já quitada!!! ", ButtonType.OK);
                a.showAndWait();
            }

        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Selecione um para exclusão!! ", ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void btnExcluir(ActionEvent event) {
        int res = JOptionPane.showConfirmDialog(null, "Deseja Realmente excluir?", "Exclusão", JOptionPane.YES_NO_OPTION);

        try {
            if (res == JOptionPane.YES_OPTION) {
                if (tabela.getSelectionModel().getSelectedIndex() != -1) {

                    if (tabela.getSelectionModel().getSelectedItem().getDesp_dtPagamento() == null) {
                        if (controladora_desp.excluir(tabela.getSelectionModel().getSelectedItem().getDesp_cod())) {
                            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Excluído com Sucesso!! ", ButtonType.OK);
                            a.showAndWait();
                            list_despesa = controladora_desp.buscar(txtNome.getText(), "");
                            tabela.setItems(FXCollections.observableArrayList(list_despesa));
                        }
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Não é possível excluir uma despesa já quitada!!! ", ButtonType.OK);
                        a.showAndWait();
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Selecione um para exclusão!! ", ButtonType.OK);
                    a.showAndWait();
                }
            }
            //}
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao Excluir!! ", ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void btnSair(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/proj_engii/telas/TelaLançarDespesas.fxml"));
            tela.getChildren().clear();
            tela.getChildren().add(root);
        } catch (Exception e) {
            System.out.println("Erro" + e);
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro! " + e, ButtonType.OK);
            a.showAndWait();
        }
    }

    public static Despesa getDespesa() {
        return desp;
    }

    private void setColors() {
        txtNome.getStylesheets().add("/proj_engii/style.css");
        cbTipo.getStylesheets().add("/proj_engii/style.css");
        tabela.getStylesheets().add("/proj_engii/style.css");
        data1.getStylesheets().add("/proj_engii/style.css");
        data2.getStylesheets().add("/proj_engii/style.css");
    }

    @FXML
    private void busca_filtro(KeyEvent event) {
        Despesa p;
        if (aux != null) {
            aux.clear();
        }
        if (cbTipo.getSelectionModel().getSelectedIndex() == -1 && !txtNome.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Escolha um tipo a ser pesquisado!! ", ButtonType.OK);
            a.showAndWait();
        } else {
            for (int i = 0; i < list_despesa.size(); i++) {
                p = list_despesa.get(i);
                if (p.getDesp_descricao().contains(txtNome.getText()) && cbTipo.getSelectionModel().getSelectedItem().equals("Descricao")) {
                    aux.add(new Despesa(p.getDesp_cod(), p.getDesp_descricao(), p.getDesp_valor(), p.getDesp_tipo(), p.getDesp_dtEmissao(), p.getDesp_dtVencimento(), p.getDesp_tipocod(), p.getDesp_dtPagamento()));
                }
                if (p.getDesp_tipo().contains(txtNome.getText()) && cbTipo.getSelectionModel().getSelectedItem().equals("Tipo")) {
                    aux.add(new Despesa(p.getDesp_cod(), p.getDesp_descricao(), p.getDesp_valor(), p.getDesp_tipo(), p.getDesp_dtEmissao(), p.getDesp_dtVencimento(), p.getDesp_tipocod(), p.getDesp_dtPagamento()));
                }
            }
        }
        tabela.setItems(FXCollections.observableArrayList(aux));
    }
}
