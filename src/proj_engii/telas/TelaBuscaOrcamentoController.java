/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.telas;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author hiroshi
 */
public class TelaBuscaOrcamentoController implements Initializable {

    @FXML
    private ComboBox<?> cb_func;
    @FXML
    private JFXTextField txt_produto;
    @FXML
    private TableView<?> tab_prod;
    @FXML
    private TableColumn<?, ?> col_nome;
    @FXML
    private TableColumn<?, ?> col_valor;
    @FXML
    private TableColumn<?, ?> col_qtd;
    @FXML
    private TableView<?> tab_orc;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAdd(ActionEvent event) {
    }

    @FXML
    private void btnExcluir(ActionEvent event) {
    }
    
}
