
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
import javafx.scene.control.Label;
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
    private Object[] ob = new Object[10];
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
    @FXML
    private Label lbEstado;
    private LocalDate local;
    @FXML
    private JFXTextField txtDespesa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int i;
        list_tipodespesas = ctrl_tipodespesa.buscar("");
        cbDespesa.getItems().addAll(list_tipodespesas);
        setColors();
        if (TelaBuscaDespesasController.getDespesa() != null) {
            despesa = TelaBuscaDespesasController.getDespesa();
        }
        if (despesa != null) {
            i = 0;
            cod = despesa.getDesp_cod();
            txtDescricao.setText(despesa.getDesp_descricao());
            txtValor.setText(despesa.getDesp_valor() + "");
            dtEmissao.setValue(local.parse(despesa.getDesp_dtEmissao()));
            dtVencimento.setValue(local.parse(despesa.getDesp_dtEmissao()));
            dtPagamento.setValue(null);
            while (!cbDespesa.getItems().get(i).getDescricao().equals(despesa.getDesp_tipo())) {
                i++;
            }
            cbDespesa.getSelectionModel().select(i);
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
        cbDespesa.setEditable(false);
        //System.out.println("" + cbDespesa.get);
        //System.out.println("" + cbDespesa.getItems().size());
        try {
            if (!validar()) {
                ob[0] = txtDescricao.getText();
                ob[1] = Double.parseDouble(txtValor.getText().replace(",", "."));
                ob[2] = cbDespesa.getItems().get(cbDespesa.getSelectionModel().getSelectedIndex()).toString();
                ob[3] = dtEmissao.getValue().toString();
                ob[4] = dtVencimento.getValue().toString();
                ob[5] = cbDespesa.getSelectionModel().getSelectedItem().getCodigo();
                if (dtPagamento.getValue() == null) {
                    ob[6] = "NULL";
                } else {
                    ob[6] = dtPagamento.getValue().toString();
                }
                if (ctrl_despesa.salvar(new Despesa(0, (String) ob[0], (Double) ob[1], (String) ob[2], (String) ob[3], (String) ob[4], (Integer) ob[5], (String) ob[6]))) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Inserido com sucesso!", ButtonType.OK);
                    a.showAndWait();
                }

                inicializa_campos(true);
                limpar_campos();
                inicializa_botoes(false, true, false, false, true);
            }
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
        dtPagamento.setDisable(b1);
        txtDespesa.setDisable(b1);
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
        dtEmissao.setValue(null);
        dtVencimento.setValue(null);
    }

    @FXML
    private void btnAlterar(ActionEvent event) {
        try {
            if (!validar()) {
                ob[0] = txtDescricao.getText();
                ob[1] = Double.parseDouble(txtValor.getText().replace(",", "."));
                ob[2] = cbDespesa.getSelectionModel().getSelectedItem().getDescricao();
                ob[3] = dtEmissao.getValue().toString();
                ob[4] = dtVencimento.getValue().toString();
                ob[5] = cbDespesa.getSelectionModel().getSelectedItem().getCodigo();

                if (dtPagamento.getValue() == null) {
                    ob[6] = "NULL";
                } else {
                    ob[6] = dtPagamento.getValue().toString();
                }
                if (ctrl_despesa.alterar(new Despesa(cod, (String) ob[0], (Double) ob[1], (String) ob[2], (String) ob[3], (String) ob[4], (Integer) ob[5], (String) ob[6]))) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Alterado com Sucesso!", ButtonType.OK);
                    a.showAndWait();
                    inicializa_campos(true);
                    limpar_campos();
                    inicializa_botoes(false, true, false, false, true);
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
        txtDespesa.getStylesheets().add("/proj_engii/style.css");
    }

    @FXML
    private void btn_buscadesp(KeyEvent event) {
    }

    @FXML
    private void busca_desp_txt(KeyEvent event) {
        ArrayList<Tipo_Despesas> list_tipo = new ArrayList<>();

        if (list_tipo != null) {
            list_tipo.clear();
        }

        for (int i = 0; i < list_tipodespesas.size(); i++) {
            if (list_tipodespesas.get(i).getDescricao().contains(txtDespesa.getText())) {
                list_tipo.add(new Tipo_Despesas(list_tipodespesas.get(i).getCodigo(), list_tipodespesas.get(i).getDescricao()));
            }
        }
        cbDespesa.getItems().clear();
        cbDespesa.getItems().addAll(list_tipo);
    }

    @FXML
    private void val_mask(KeyEvent event) {
        MaskFieldUtil.monetaryField(txtValor);
    }
}
