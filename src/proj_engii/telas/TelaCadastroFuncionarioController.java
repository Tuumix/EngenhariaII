/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.telas;

import Controladora.CtrlFuncionario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
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
import javafx.scene.layout.BorderPane;
import proj_engii.bancoc.Banco;
import proj_engii.bancoc.MaskFieldUtil;
import proj_engii.bancoc.ValidarCPF;
import proj_engii.entidade.Funcionario;

/**
 * FXML Controller class
 *
 * @author hiroshi
 */
public class TelaCadastroFuncionarioController implements Initializable {

    @FXML
    private BorderPane telaCad;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtCidade;
    @FXML
    private JFXTextField txtCEP;
    @FXML
    private JFXTextField txtEndereco;
    @FXML
    private JFXTextField txtTelefone;
    @FXML
    private JFXTextField txtCPF;
    private CtrlFuncionario controladora_func;
    @FXML
    private JFXTextField txtNumero;
    private Boolean gravou;
    @FXML
    private JFXComboBox<String> cbNivel;
    private Object[] ob = new Object[15];
    @FXML
    private JFXTextField txtLogin;
    @FXML
    private JFXPasswordField txtSenha;
    @FXML
    private JFXPasswordField txtConfirmSenha;
    @FXML
    private JFXRadioButton rd_masc;
    @FXML
    private JFXRadioButton rd_fem;
    private Funcionario func = new Funcionario();
    @FXML
    private JFXTextField txtCod;
    @FXML
    private Label lbEstado;
    @FXML
    private JFXButton botao_gravar;
    @FXML
    private JFXButton botao_buscar;
    @FXML
    private JFXButton botao_alterar;
    @FXML
    private JFXButton botao_novo;
    @FXML
    private JFXButton botao_limpar;
    @FXML
    private JFXDatePicker dt_admissao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Inicializa();
        setColors();

        //txtNome.setStyle("-fx-text-inner-color: white;");
    }
//-----------------------------------------------------------------------------------------

    private String busca_cep(KeyEvent event) {
        return "";
    }
//-----------------------------------------------------------------------------------------

    @FXML
    private void btnGravar(ActionEvent event) {
        try {
            if (validar()) {
                if (ValidarCPF.isValidCPForCNPJ(txtCPF.getText()) == 1) {
                    if (txtSenha.getText().equals(txtConfirmSenha.getText())) {
                        ob[0] = Integer.parseInt(txtNumero.getText());
                        ob[1] = txtNome.getText();
                        ob[2] = "";
                        ob[3] = txtCPF.getText();
                        ob[4] = txtEndereco.getText();
                        ob[5] = txtCidade.getText();
                        ob[6] = txtEmail.getText();
                        ob[8] = txtLogin.getText();
                        ob[9] = cbNivel.getSelectionModel().getSelectedItem();
                        ob[10] = txtSenha.getText();
                        ob[11] = txtTelefone.getText();
                        ob[12] = dt_admissao.getValue().toString();

                        if (rd_fem.isSelected()) {
                            ob[7] = "Feminino";//rd_fem.getText();
                        } else {
                            ob[7] = "Masculino";
                            rd_masc.getText();
                        }

                        gravou = controladora_func.salvar(ob);
                        limpar();
                        estado_inicial(true);
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Funcionario inserido com sucesso! ", ButtonType.OK);
                        a.showAndWait();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Senha confirmado diferente da Senha!! ", ButtonType.OK);
                        a.showAndWait();
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "CPF inválido!! ", ButtonType.OK);
                    a.showAndWait();
                }
            }
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }
//-----------------------------------------------------------------------------------------

    @FXML
    private void mascaraCEP(KeyEvent event) {
        MaskFieldUtil.cepField(txtCEP);
    }
//-----------------------------------------------------------------------------------------

    @FXML
    private void mascaraTelefone(KeyEvent event) {
        MaskFieldUtil.foneField(txtTelefone);
    }
//-----------------------------------------------------------------------------------------

    @FXML
    private void mascaraCPF(KeyEvent event) {
        MaskFieldUtil.cpfField(txtCPF);
    }
//-----------------------------------------------------------------------------------------

    @FXML
    private void mascaraNum(KeyEvent event) {
        MaskFieldUtil.numericField(txtNumero);
    }
//-----------------------------------------------------------------------------------------

    @FXML
    private void btnBuscar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/proj_engii/telas/TelaBuscaFuncionario.fxml"));
            telaCad.getChildren().clear();
            telaCad.getChildren().add(root);
        } catch (Exception e) {
            System.out.println("Erro" + e);
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro! " + e, ButtonType.OK);
            a.showAndWait();
        }
    }
//-----------------------------------------------------------------------------------------

    @FXML
    private void btnAlterar(ActionEvent event) {
        try {
            if (validar()) {
                if (txtSenha.getText().equals(txtConfirmSenha.getText())) {
                    if (txtNumero.getText().isEmpty()) {
                        ob[0] = "0";
                    } else {
                        ob[0] = txtNumero.getText();
                    }
                    ob[1] = txtNome.getText();
                    ob[2] = txtCEP.getText();
                    ob[3] = txtCPF.getText();
                    ob[4] = txtEndereco.getText();
                    ob[5] = txtCidade.getText();
                    ob[6] = txtEmail.getText();
                    ob[8] = txtLogin.getText();
                    ob[9] = cbNivel.getSelectionModel().getSelectedItem();
                    ob[10] = txtSenha.getText();
                    ob[11] = txtTelefone.getText();
                    ob[12] = dt_admissao.getValue().toString();

                    if (rd_fem.isSelected()) {
                        ob[7] = "Feminino";
                    } else {
                        ob[7] = "Masculino";
                    }

                    gravou = controladora_func.alterar(ob, Integer.parseInt(txtCod.getText()));
                    lbEstado.setText("Nulo");
                    limpar();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Funcionario alterado com sucesso!!", ButtonType.OK);
                    a.showAndWait();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Senha confirmado diferente da Senha!! ", ButtonType.OK);
                    a.showAndWait();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro tela" + e);
        }
    }
//-----------------------------------------------------------------------------------------

    @FXML
    private void btnLimpar(ActionEvent event) {
        limpar();
    }
//-----------------------------------------------------------------------------------------

    @FXML
    private void btnSair(ActionEvent event) {
        System.exit(0);
    }
//-----------------------------------------------------------------------------------------

    @FXML
    private void btnNovo(ActionEvent event) {
        habilita_campo();
        estado_botoes(true, false, true, true, false); //alt,gravar,buscar,novo
        rd_fem.setDisable(false);
        rd_masc.setDisable(false);
        lbEstado.setText("Cadastrando");
    }
//-----------------------------------------------------------------------------------------

    public void estado_botoes(Boolean b1, Boolean b2, Boolean b3, Boolean b4, Boolean b5) {
        botao_alterar.setDisable(b1);
        botao_gravar.setDisable(b2);
        botao_buscar.setDisable(b3);
        botao_novo.setDisable(b4);
        botao_limpar.setDisable(b5);
    }
//-----------------------------------------------------------------------------------------

    public void estado_inicial(Boolean estado) {
        txtCod.setDisable(estado);
        estado_botoes(true, true, false, false, true); //alt,gravar,buscar,novo
        txtCEP.setDisable(estado);
        txtNome.setDisable(estado);
        txtCPF.setDisable(estado);
        txtCidade.setDisable(estado);
        txtConfirmSenha.setDisable(estado);
        txtEmail.setDisable(estado);
        txtLogin.setDisable(estado);
        txtNome.setDisable(estado);
        txtNumero.setDisable(estado);
        txtSenha.setDisable(estado);
        txtTelefone.setDisable(estado);
        txtEndereco.setDisable(estado);
        cbNivel.setDisable(estado);
        rd_fem.setDisable(estado);
        rd_masc.setDisable(estado);
        dt_admissao.setDisable(estado);
    }
//-----------------------------------------------------------------------------------------

    public void habilita_campo() {
        txtCEP.setDisable(false);
        txtNome.setDisable(false);
        txtCPF.setDisable(false);
        txtCidade.setDisable(false);
        txtConfirmSenha.setDisable(false);
        txtEmail.setDisable(false);
        txtLogin.setDisable(false);
        txtNome.setDisable(false);
        txtNumero.setDisable(false);
        txtSenha.setDisable(false);
        txtTelefone.setDisable(false);
        txtEndereco.setDisable(false);
        cbNivel.setDisable(false);
        dt_admissao.setDisable(false);
    }
//-----------------------------------------------------------------------------------------

    public void Inicializa() {
        int cod = Banco.con.getMaxPK("funcionario", "func_cod");
        controladora_func = new CtrlFuncionario();
        func = TelaBuscaFuncionarioController.getFuncionario();
        txtCod.setText(cod + "");
        cbNivel.getItems().add("Funcionário");
        cbNivel.getItems().add("Patrão");
        estado_inicial(true);

        if (func != null) {
            estado_inicial(false);
            estado_botoes(false, true, false, false, true); //alt,gravar,buscar,novo
            func = TelaBuscaFuncionarioController.getFuncionario();
            txtNome.setText(func.getNome());
            txtCEP.setText(func.getCep());
            txtCPF.setText(func.getCpf());
            txtEmail.setText(func.getEmail());
            txtCidade.setText(func.getCidade());
            txtEndereco.setText(func.getEndereco());
            txtNumero.setText(func.getNumero() + "");
            txtLogin.setText(func.getLogin());
            txtTelefone.setText(func.getTelefone());
            txtCod.setText(func.getCodigo() + "");
            lbEstado.setText("Alterando");
        } else {
            lbEstado.setText("Nulo");
        }
    }
//-----------------------------------------------------------------------------------------

    public void limpar() {
        txtNome.clear();
        txtCEP.clear();
        txtCPF.clear();
        txtEmail.clear();
        txtCidade.clear();
        txtEndereco.clear();
        txtNumero.clear();
        txtLogin.clear();
        txtTelefone.clear();
    }
//-----------------------------------------------------------------------------------------

    private void setColors() {
        txtCEP.getStylesheets().add("/proj_engii/style.css");
        txtCod.getStylesheets().add("/proj_engii/style.css");
        txtNome.getStylesheets().add("/proj_engii/style.css");
        txtCPF.getStylesheets().add("/proj_engii/style.css");
        txtCidade.getStylesheets().add("/proj_engii/style.css");
        txtConfirmSenha.getStylesheets().add("/proj_engii/style.css");
        txtEmail.getStylesheets().add("/proj_engii/style.css");
        txtLogin.getStylesheets().add("/proj_engii/style.css");
        txtNumero.getStylesheets().add("/proj_engii/style.css");
        txtSenha.getStylesheets().add("/proj_engii/style.css");
        txtTelefone.getStylesheets().add("/proj_engii/style.css");
        txtEndereco.getStylesheets().add("/proj_engii/style.css");
        cbNivel.getStylesheets().add("/proj_engii/style.css");
    }
//-----------------------------------------------------------------------------------------

    private Boolean validar() {
        Boolean validado = true;
        if (txtNome.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Campo Nome obrigatório!", ButtonType.OK);
            a.showAndWait();
            validado = false;
        }

        if (txtLogin.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Campo Login obrigatório!", ButtonType.OK);
            a.showAndWait();
            validado = false;

        }

        if (txtSenha.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Campo Senha obrigatório!", ButtonType.OK);
            a.showAndWait();
            validado = false;

        }

        if (txtConfirmSenha.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Campo Confirmar Senha obrigatório!", ButtonType.OK);
            a.showAndWait();
            validado = false;
        }

        if (cbNivel.getValue() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Insira uma data!", ButtonType.OK);
            a.showAndWait();
            validado = false;
        }
        return validado;
    }
    //-----------------------------------------------------------------------------------------
}
