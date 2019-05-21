
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.telas;

import Controladora.CtrlDespesa;
import Controladora.CtrlTipoDespesa;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.layout.BorderPane;
import proj_engii.bancoc.MaskFieldUtil;
import proj_engii.entidade.Despesa;
import proj_engii.entidade.Tipo_Despesas;

/**
 * FXML Controller class
 *
 * @author Hiroshi
 */
public class TelaLançarDespesasController implements Initializable {

    @FXML
    private JFXComboBox<Tipo_Despesas> cbDespesa;
    @FXML
    private JFXTextField txtDescricao;
    @FXML
    private JFXTextField txtValor;
    @FXML
    private JFXDatePicker dtEmissao;
    @FXML
    private JFXDatePicker dtVencimento;
    @FXML
    private TableColumn<?, ?> col_descricao;
    @FXML
    private TableColumn<?, ?> col_tipo;
    @FXML
    private TableColumn<?, ?> col_valor;
    @FXML
    private TableColumn<?, ?> col_emissao;
    @FXML
    private TableColumn<?, ?> col_vencimento;
    @FXML
    private TableView<Despesa> tabela_desp;
    @FXML
    private JFXButton botao_add;
    @FXML
    private JFXButton botao_ret;
    @FXML
    private JFXButton botao_limpar;
    @FXML
    private JFXButton botao_novo;
    @FXML
    private JFXButton botao_gravar;
    @FXML
    private JFXButton botao_buscar;
    @FXML
    private JFXButton botao_sair;
    @FXML
    private BorderPane tela_despesa;
    private Despesa despesa;
    @FXML
    private JFXButton botao_alterar;
    private int cod;
    private Object[] ob = new Object[6];
    private CtrlDespesa ctrl_despesa = new CtrlDespesa();
    private ArrayList<Despesa> list_despesa = new ArrayList<>();
    private ArrayList<Tipo_Despesas> list_tipodespesas = new ArrayList<>();
    private CtrlTipoDespesa ctrl_tipodespesa = new CtrlTipoDespesa();
    @FXML
    private TableColumn<?, ?> col_dtcod;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCells();
        despesa = TelaBuscaDespesasController.getDespesa();
        list_tipodespesas = ctrl_tipodespesa.buscar("");
        cbDespesa.getItems().addAll(list_tipodespesas);

        if (despesa != null) {
            cod = despesa.getDesp_cod();
            txtDescricao.setText(despesa.getDesp_descricao());
            txtValor.setText(despesa.getDesp_valor() + "");
            inicializa_campos(false);
            inicializa_botoes(true, true, false, false, false);
        } else {
            inicializa_campos(true);
            inicializa_botoes(false, true, false, false, true);
        }
    }

    @FXML
    private void btnAdd(ActionEvent event) {
        try {
            if (!validar()) {
                ob[0] = txtDescricao.getText();
                ob[1] = Double.parseDouble(txtValor.getText().replace(",", "."));
                ob[2] = cbDespesa.getSelectionModel().getSelectedItem().getDescricao();
                ob[3] = dtEmissao.getValue().toString();
                ob[4] = dtVencimento.getValue().toString();
                ob[5] = cbDespesa.getSelectionModel().getSelectedItem().getCodigo();
                list_despesa.add(new Despesa(0, (String) ob[0], (Double) ob[1], (String) ob[2], (String) ob[3], (String) ob[4], (Integer) ob[5]));
                tabela_desp.setItems(FXCollections.observableArrayList(list_despesa));
            }
            //System.out.println(""+tabela_desp.getSelectionModel().getSelectedItem().getDesp_descricao());
        } catch (Exception e) {
            System.out.println("Erro : " + e);
        }

    }

    @FXML
    private void btnRemove(ActionEvent event) {
        if (tabela_desp.getSelectionModel().getSelectedIndex() == -1) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Escolha primeiro para excluir!", ButtonType.OK);
            a.showAndWait();
        } else {
            list_despesa.remove(tabela_desp.getSelectionModel().getSelectedItem());
            tabela_desp.setItems(FXCollections.observableArrayList(list_despesa));
        }
    }

    @FXML
    private void btnLimpar(ActionEvent event) {
        limpar_campos();
    }

    @FXML
    private void btnGravar(ActionEvent event) {
        if (tabela_desp.getItems().size() == 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Insira as despesas na tabela para gravar!", ButtonType.OK);
            a.showAndWait();
        } else {
            for (int i = 0; i < tabela_desp.getItems().size(); i++) {
                ctrl_despesa.salvar(tabela_desp.getItems().get(i));
            }
            limpar_campos();
            tabela_desp.getItems().clear();
            inicializa_campos(true);
            inicializa_botoes(false, true, false, false, true);
        }

    }

    @FXML
    private void btnNovo(ActionEvent event) {
        inicializa_campos(false);
        inicializa_botoes(true, false, true, false, true);
        cbDespesa.getSelectionModel().select(0);
    }

    @FXML
    private void btnBuscar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/proj_engii/telas/TelaBuscarDespesas.fxml"));
            tela_despesa.getChildren().clear();
            tela_despesa.getChildren().add(root);
        } catch (Exception e) {
            System.out.println("Erro" + e);
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro! " + e, ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void btnSair(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/proj_engii/telas/FXMLDocument.fxml"));
            tela_despesa.getChildren().clear();
            tela_despesa.getChildren().add(root);
        } catch (Exception e) {
            System.out.println("Erro" + e);
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro! " + e, ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void mascara_valor(KeyEvent event) {
        MaskFieldUtil.monetaryField(txtValor);
    }

    public void inicializa_campos(Boolean b1) {
        txtDescricao.setDisable(b1);
        cbDespesa.setDisable(b1);
        txtValor.setDisable(b1);
        dtEmissao.setDisable(b1);
        dtVencimento.setDisable(b1);
        botao_limpar.setDisable(b1);
        botao_add.setDisable(b1);
        botao_ret.setDisable(b1);
    }

    public void inicializa_botoes(Boolean b1, Boolean b2, Boolean b3, Boolean b4, Boolean b5) {
        botao_novo.setDisable(b1);
        botao_gravar.setDisable(b2);
        botao_buscar.setDisable(b3);
        botao_sair.setDisable(b4);
        botao_alterar.setDisable(b5);
    }

    private void setCells() {
        col_descricao.setCellValueFactory(new PropertyValueFactory<>("desp_descricao"));
        col_emissao.setCellValueFactory(new PropertyValueFactory<>("desp_dtEmissao"));
        col_tipo.setCellValueFactory(new PropertyValueFactory<>("desp_tipo"));
        col_valor.setCellValueFactory(new PropertyValueFactory<>("desp_valor"));
        col_vencimento.setCellValueFactory(new PropertyValueFactory<>("desp_dtVencimento"));
        col_dtcod.setCellValueFactory(new PropertyValueFactory<>("desp_tipocod"));
    }

    private Boolean validar() {
        Boolean erro = false;
        if (txtDescricao.getText().isEmpty()) {
            erro = true;
            Alert a = new Alert(Alert.AlertType.ERROR, "Preencha o campo Descrição!", ButtonType.OK);
            a.showAndWait();
        }
        if (txtValor.getText().isEmpty()) {
            erro = true;
            Alert a = new Alert(Alert.AlertType.ERROR, "Preencha o campo Valor!", ButtonType.OK);
            a.showAndWait();
        }
        if (cbDespesa.getSelectionModel().getSelectedIndex() == -1) {
            erro = true;
            Alert a = new Alert(Alert.AlertType.ERROR, "Escolha o tipo!", ButtonType.OK);
            a.showAndWait();
        }
        if (dtEmissao.getValue() == null) {
            erro = true;
            Alert a = new Alert(Alert.AlertType.ERROR, "Insira a data de emissão!", ButtonType.OK);
            a.showAndWait();
        }
        if (dtVencimento.getValue() == null) {
            erro = true;
            Alert a = new Alert(Alert.AlertType.ERROR, "Insira a data de Vencimento!", ButtonType.OK);
            a.showAndWait();
        }
        return erro;
    }

    private void limpar_campos() {
        txtDescricao.setText("");
        txtValor.setText("");
        dtEmissao.setValue(LocalDate.now());
        dtVencimento.setValue(LocalDate.now());
    }

    @FXML
    private void btnAlterar(ActionEvent event) {
        try {
            if (!validar()) {
                //System.out.println("" + cbDespesa.getSelectionModel().getSelectedItem().getCodigo());
                ob[0] = txtDescricao.getText();
                ob[1] = Double.parseDouble(txtValor.getText().replace(",", "."));
                ob[2] = cbDespesa.getSelectionModel().getSelectedItem().getDescricao();
                ob[3] = dtEmissao.getValue().toString();
                ob[4] = dtVencimento.getValue().toString();
                ob[5] = cbDespesa.getSelectionModel().getSelectedItem().getCodigo();
                if (ctrl_despesa.alterar(new Despesa(cod, (String) ob[0], (Double) ob[1], (String) ob[2], (String) ob[3], (String) ob[4], (Integer) ob[5]))) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Alterado com Sucesso!", ButtonType.OK);
                    a.showAndWait();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao alterar!", ButtonType.OK);
                    a.showAndWait();
                }
            }

        } catch (Exception e) {
            System.out.println(""+e);
        }
    }
}
