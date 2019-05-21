
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
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
    private JFXDatePicker dtEmissao;
    @FXML
    private JFXDatePicker dtVencimento;
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
    private AnchorPane sub_tela;
    @FXML
    private JFXDatePicker dtPagamento;
    @FXML
    private JFXTextField txtDescricao;
    @FXML
    private JFXComboBox<Tipo_Despesas> cbDespesa;
    @FXML
    private JFXTextField txtValor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        despesa = TelaBuscaDespesasController.getDespesa();
        list_tipodespesas = ctrl_tipodespesa.buscar("");
        cbDespesa.getItems().addAll(list_tipodespesas);
        //sub_tela.setOpacity(0.2);
        setColors();
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
    private void btnLimpar(ActionEvent event) {
        limpar_campos();
    }

    @FXML
    private void btnGravar(ActionEvent event) {
        try {
            if (!validar()) {
                ob[0] = txtDescricao.getText();
                ob[1] = Double.parseDouble(txtValor.getText().replace(",", "."));
                ob[2] = cbDespesa.getSelectionModel().getSelectedItem().getDescricao();
                ob[3] = dtEmissao.getValue().toString();
                ob[4] = dtVencimento.getValue().toString();
                ob[5] = cbDespesa.getSelectionModel().getSelectedItem().getCodigo();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Inserido com sucesso!", ButtonType.OK);
                a.showAndWait();
                list_despesa.add(new Despesa(0, (String) ob[0], (Double) ob[1], (String) ob[2], (String) ob[3], (String) ob[4], (Integer) ob[5]));
            }
            //System.out.println(""+tabela_desp.getSelectionModel().getSelectedItem().getDesp_descricao());
        } catch (Exception e) {
            System.out.println("Erro : " + e);
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
    }

    public void inicializa_botoes(Boolean b1, Boolean b2, Boolean b3, Boolean b4, Boolean b5) {
        botao_novo.setDisable(b1);
        botao_gravar.setDisable(b2);
        botao_buscar.setDisable(b3);
        botao_sair.setDisable(b4);
        botao_alterar.setDisable(b5);
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
            System.out.println("" + e);
        }
    }

    private void setColors() {
        txtDescricao.getStylesheets().add("/proj_engii/style.css");
        txtValor.getStylesheets().add("/proj_engii/style.css");
        dtEmissao.getStylesheets().add("/proj_engii/style.css");
        dtVencimento.getStylesheets().add("/proj_engii/style.css");
        dtPagamento.getStylesheets().add("/proj_engii/style.css");
        cbDespesa.getStylesheets().add("/proj_engii/style.css");
    }
}
