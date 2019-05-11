/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.telas;

import Controladora.CtrlFuncionario;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import proj_engii.entidade.Funcionario;

/**
 * FXML Controller class
 *
 * @author hiroshi
 */
public class TelaLoginController implements Initializable {

    @FXML
    private AnchorPane tela;
    @FXML
    private JFXTextField txtLogin;
    @FXML
    private JFXPasswordField txtSenha;
    private CtrlFuncionario ctrl_func = new CtrlFuncionario();
    private ArrayList<Funcionario> list = new ArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnVoltar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/proj_engii/telas/FXMLDocument.fxml"));
            tela.getChildren().clear();
            tela.getChildren().add(root);
        } catch (Exception e) {
            System.out.println("Erro" + e);
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro! " + e, ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void btnLogar(ActionEvent event) {
        //list = null;
        list = ctrl_func.logar(txtLogin.getText(), txtSenha.getText());
        System.out.println("" + list.size());
        if (list.size() == 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao logar! ", ButtonType.OK);
            a.showAndWait();

        } else {
            if (list.get(0).getNivel().equals("Patrão")) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Logado como Patrão!! ", ButtonType.OK);
                a.showAndWait();
            }
            else{
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Logado como Funcionario! ", ButtonType.OK);
                a.showAndWait();
            }

        }
    }

}
