/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.telas;

import Controladora.CtrlFuncionario;
import Controladora.CtrlProduto;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import proj_engii.entidade.Funcionario;
import proj_engii.entidade.Produto;

/**
 * FXML Controller class
 *
 * @author hiroshi
 */
public class TelaCadastrarOrcamentoController implements Initializable {

    @FXML
    private ComboBox<Funcionario> cb_func;
    @FXML
    private JFXTextField txt_produto;
    @FXML
    private TableView<Produto> tab_prod;
    @FXML
    private TableColumn<?, ?> col_nome;
    @FXML
    private TableColumn<?, ?> col_valor;
    @FXML
    private TableColumn<?, ?> col_qtd;
    @FXML
    private TableView<Produto> tab_orc;
    @FXML
    private TableColumn<?, ?> col_orcnome;
    @FXML
    private TableColumn<?, ?> col_orcval;
    @FXML
    private TableColumn<?, ?> col_orcqtd;
    @FXML
    private TextField txt_quantidade;
    @FXML
    private TableColumn<?, ?> col_codigo;
    @FXML
    private TableColumn<?, ?> col_orccod;
    private CtrlFuncionario ctrl_func = new CtrlFuncionario();
    private CtrlProduto ctrl_prod = new CtrlProduto();
    private ArrayList<Funcionario> list_func = new ArrayList<>();
    private ArrayList<Produto> list_prod = new ArrayList<>();
    private ArrayList<Produto> aux = new ArrayList<>();
    private ArrayList<Produto> prod_orc = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setColors();
        setCells();
        list_func = ctrl_func.buscar("", "");
        cb_func.getItems().addAll(list_func);

        list_prod = ctrl_prod.buscar("");
        tab_prod.setItems(FXCollections.observableArrayList(list_prod));
        //tab_orc.getItems().add(new Produto(21, "asdsa", 2.3, 3));
    }

    @FXML
    private void btnAdd(ActionEvent event) {
        Produto p = new Produto();
        Boolean achou = false;
        if (tab_prod.getSelectionModel().getSelectedIndex() != -1) {
            p = tab_prod.getSelectionModel().getSelectedItem();
            if (Integer.parseInt(txt_quantidade.getText()) <= p.getQtde() && Integer.parseInt(txt_quantidade.getText()) > 0 && !txt_quantidade.getText().isEmpty()) {
                if (prod_orc.isEmpty()) {
                    prod_orc.add(new Produto(p.getCodigo(), p.getDescricao(), p.getValor(), Integer.parseInt(txt_quantidade.getText())));
                    list_prod.get(tab_prod.getSelectionModel().getSelectedIndex()).setQtde(p.getQtde() - Integer.parseInt(txt_quantidade.getText()));
                } else {
                    for (int i = 0; i < prod_orc.size(); i++) {
                        if (p.getCodigo() == prod_orc.get(i).getCodigo()) {
                            achou = true;
                            prod_orc.get(i).setQtde(Integer.parseInt(txt_quantidade.getText()) + prod_orc.get(i).getQtde());
                            list_prod.get(tab_prod.getSelectionModel().getSelectedIndex()).setQtde(p.getQtde() - Integer.parseInt(txt_quantidade.getText()));
                        }
                    }
                    if (!achou) {
                        prod_orc.add(new Produto(p.getCodigo(), p.getDescricao(), p.getValor(), Integer.parseInt(txt_quantidade.getText())));
                        list_prod.get(tab_prod.getSelectionModel().getSelectedIndex()).setQtde(p.getQtde() - Integer.parseInt(txt_quantidade.getText()));
                    }
                }
                tab_orc.getItems().clear();
                tab_orc.setItems(FXCollections.observableArrayList(prod_orc));
                tab_prod.getItems().clear();
                tab_prod.setItems(FXCollections.observableArrayList(list_prod));
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Quantidade inválida!", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Selecione um produto pelo menos!", ButtonType.OK);
            a.showAndWait();
        }
    }

    private void setCells() {
        //------------------------------------------------------------------------- Produto
        col_codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        col_nome.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        col_qtd.setCellValueFactory(new PropertyValueFactory<>("qtde"));
        col_valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        //------------------------------------------------------------------------- Orçamento
        col_orccod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        col_orcnome.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        col_orcqtd.setCellValueFactory(new PropertyValueFactory<>("qtde"));
        col_orcval.setCellValueFactory(new PropertyValueFactory<>("valor"));

        tab_prod.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tab_orc.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setColors() {
        cb_func.getStylesheets().add("/proj_engii/style.css");
        txt_produto.getStylesheets().add("/proj_engii/style.css");
        txt_quantidade.getStylesheets().add("/proj_engii/style.css");
        tab_orc.getStylesheets().add("/proj_engii/style.css");
        tab_prod.getStylesheets().add("/proj_engii/style.css");
    }

    @FXML
    private void btnRet(ActionEvent event) {

    }

    @FXML
    private void busca_prod(KeyEvent event) {
        Produto p;

        if (aux != null) {
            aux.clear();
        }
        for (int i = 0; i < list_prod.size(); i++) {
            p = list_prod.get(i);
            if (list_prod.get(i).getDescricao().contains(txt_produto.getText())) {
                aux.add(new Produto(p.getCodigo(), p.getDescricao(), p.getValor(), p.getQtde()));
            }
        }
        tab_prod.getItems().clear();
        tab_prod.setItems(FXCollections.observableArrayList(aux));
    }

    @FXML
    private void btnGravar(ActionEvent event) {
        try {
            for (int i = 0; i < list_prod.size(); i++) {
                if (ctrl_prod.alterar(list_prod.get(i))) {
                    System.out.println("ok");
                }
            }
        } catch (Exception e) {

        }
    }
}
