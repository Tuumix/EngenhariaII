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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import proj_engii.entidade.Funcionario;
import proj_engii.entidade.Produto;

/**
 * FXML Controller class
 *
 * @author hiroshi
 */
public class TelaBuscaOrcamentoController implements Initializable {

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
        tab_orc.getItems().add(new Produto(21,"asdsa",2.3,3));
    }

    @FXML
    private void btnAdd(ActionEvent event) {
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
    
    private void setColors()
    {
        cb_func.getStylesheets().add("/proj_engii/style.css");
        txt_produto.getStylesheets().add("/proj_engii/style.css");
        txt_quantidade.getStylesheets().add("/proj_engii/style.css");
    }

    @FXML
    private void btnRet(ActionEvent event) {
    }
}
