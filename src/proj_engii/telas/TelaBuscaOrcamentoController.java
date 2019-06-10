/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.telas;

import Controladora.CtrlOrcamento;
import proj_engii.entidade.ItensOrcamento;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;
import proj_engii.entidade.Orcamento;

/**
 * FXML Controller class
 *
 * @author hiroshi
 */
public class TelaBuscaOrcamentoController implements Initializable {

    @FXML
    private TableColumn<?, ?> col_cod;
    @FXML
    private TableColumn<?, ?> col_cli;
    @FXML
    private TableColumn<?, ?> col_func;
    @FXML
    private TableColumn<?, ?> col_total;
    @FXML
    private TableView<Orcamento> tabela_orcamento;
    private CtrlOrcamento ctrl_orc = new CtrlOrcamento();
    @FXML
    private TableColumn<?, ?> col_codI;
    @FXML
    private TableColumn<?, ?> col_prodI;
    @FXML
    private TableColumn<?, ?> col_codO;
    @FXML
    private TableColumn<ItensOrcamento, Integer> col_qtdeI;
    @FXML
    private TableView<ItensOrcamento> tabela_itens;
    @FXML
    private AnchorPane tela;
    @FXML
    private ComboBox<String> cbPesquisa;
    private ArrayList<Orcamento> list = new ArrayList<>();
    private ArrayList<Orcamento> aux = new ArrayList<>();
    @FXML
    private TextField txt_tipo;
    @FXML
    private Label lbStatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCells();
        cbPesquisa.getItems().add("Cliente");
        cbPesquisa.getItems().add("Funcionario");
        tabela_orcamento.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        aux = ctrl_orc.buscar("");
        tabela_orcamento.setItems(FXCollections.observableArrayList(aux));
        
        tabela_orcamento.setEditable(true);
        col_qtdeI.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // TODO
    }

    /*    private int itensorc_cod, orc_cod, prod_cod, prod_qtde;
    String prod_desc;*/
    public void setCells() {
        col_cod.setCellValueFactory(new PropertyValueFactory<>("orc_cod"));
        col_cli.setCellValueFactory(new PropertyValueFactory<>("orc_cliente"));
        col_func.setCellValueFactory(new PropertyValueFactory<>("orc_func"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("orc_total"));

        col_codI.setCellValueFactory(new PropertyValueFactory<>("prod_cod"));
        col_prodI.setCellValueFactory(new PropertyValueFactory<>("prod_desc"));
        col_codO.setCellValueFactory(new PropertyValueFactory<>("orc_cod"));
        col_qtdeI.setCellValueFactory(new PropertyValueFactory<>("prod_qtde"));
    }

    @FXML
    private void selected(MouseEvent event) {
        int cod;
        cod = tabela_orcamento.getSelectionModel().getSelectedItem().getOrc_cod();
        tabela_itens.setItems(FXCollections.observableArrayList(ctrl_orc.buscar_itens(cod)));
    }

    @FXML
    private void btnExcluir(ActionEvent event) {
        int cod;
        cod = tabela_orcamento.getSelectionModel().getSelectedItem().getOrc_cod();
        if (ctrl_orc.excluir(cod)) {
            if (ctrl_orc.excluir_orcamento(cod)) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Orcamento excluído com sucesso!", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Error!", ButtonType.OK);
            a.showAndWait();
        }
        tabela_itens.getItems().clear();
        tabela_orcamento.setItems(FXCollections.observableArrayList(ctrl_orc.buscar("")));
    }

    @FXML
    private void btnSair(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/proj_engii/telas/TelaCadastrarOrcamento.fxml"));
            tela.getChildren().clear();
            tela.getChildren().add(root);
        } catch (Exception e) {
            System.out.println("Erro" + e);
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro! " + e, ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void search(KeyEvent event) {
        Orcamento p = new Orcamento();
        if (list != null) {
            list.clear();
        }
        if (cbPesquisa.getSelectionModel().getSelectedItem().equals("Cliente")) {
            for (int i = 0; i < aux.size(); i++) {
                p = aux.get(i);
                if (p.getOrc_cliente().contains(txt_tipo.getText())) {
                    list.add(new Orcamento(p.getOrc_cod(), p.getOrc_func(), p.getOrc_total(), p.getOrc_cliente()));
                }
            }
        } else {
            for (int i = 0; i < aux.size(); i++) {
                p = aux.get(i);
                if (p.getOrc_func().contains(txt_tipo.getText())) {
                    list.add(new Orcamento(p.getOrc_cod(), p.getOrc_func(), p.getOrc_total(), p.getOrc_cliente()));
                }
            }
        }
        tabela_orcamento.getItems().clear();
        tabela_orcamento.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    private void btnConfirmar(ActionEvent event) {
        for(int i = 0; i < tabela_itens.getItems().size();i++){
            if(ctrl_orc.altera_itensorc(tabela_itens.getItems().get(i).getProd_qtde()))
                System.out.println("ok");
        }
    }

    @FXML
    private void onclk_qtde(TableColumn.CellEditEvent<ItensOrcamento, Integer> event) {
        ItensOrcamento itens = tabela_itens.getSelectionModel().getSelectedItem();
        itens.setProd_qtde(event.getNewValue());
    }

}
