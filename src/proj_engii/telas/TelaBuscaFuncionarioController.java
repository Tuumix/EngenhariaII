/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.telas;

import Controladora.CtrlFuncionario;
import com.jfoenix.controls.JFXComboBox;
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
import proj_engii.entidade.Funcionario;

/**
 * FXML Controller class
 *
 * @author hiroshi
 */
public class TelaBuscaFuncionarioController implements Initializable {

    @FXML
    private TableView<Funcionario> tabela;
    @FXML
    private TableColumn<?, ?> nome;
    @FXML
    private TableColumn<?, ?> cpf;
    @FXML
    private TableColumn<?, ?> cep;
    @FXML
    private TableColumn<?, ?> endereco;
    @FXML
    private TableColumn<?, ?> cidade;
    @FXML
    private TableColumn<?, ?> numero;
    @FXML
    private TableColumn<?, ?> email;
    @FXML
    private TableColumn<?, ?> login;
    @FXML
    private TableColumn<?, ?> senha;
    @FXML
    private JFXComboBox<String> cbTipo;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private TableColumn<?, ?> dtadmissao;
    private CtrlFuncionario ctr_func = new CtrlFuncionario();
    private ArrayList<Funcionario> list = new ArrayList<>();
    public static Funcionario f;
    @FXML
    private AnchorPane tela;
    @FXML
    private TableColumn<?, ?> codigo;
    @FXML
    private TableColumn<?, ?> nivel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCells();
        setSize();
        setColor();
        cbTipo.getItems().add("Nome");
        cbTipo.getItems().add("Login");
        cbTipo.getSelectionModel().selectFirst();
        list = ctr_func.buscar("", "");
        tabela.setItems(FXCollections.observableArrayList(list));
        f = null;
    }

    private void setColor() {
        txtNome.getStylesheets().add("/proj_engii/style.css");
        cbTipo.getStylesheets().add("/proj_engii/style.css");
        tabela.getStylesheets().add("/proj_engii/style.css");
    }

    private void setSize() {
        nome.setMinWidth(180);
        cep.setMinWidth(180);
        cpf.setMinWidth(180);
        endereco.setMinWidth(180);
        numero.setMinWidth(180);
        cidade.setMinWidth(180);
        login.setMinWidth(180);
        senha.setMinWidth(180);
        dtadmissao.setMinWidth(180);
        email.setMinWidth(180);
        codigo.setMinWidth(180);
        nivel.setMinWidth(180);
    }

    private void setCells() {
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        cidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        senha.setCellValueFactory(new PropertyValueFactory<>("senha"));
        dtadmissao.setCellValueFactory(new PropertyValueFactory<>("dtAdmissao"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        nivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
    }

    @FXML
    private void btnBuscar(ActionEvent event) {

    }

    @FXML
    private void btnExcluir(ActionEvent event) {
        int res;
        res = JOptionPane.showConfirmDialog(null, "Deseja Realmente excluir?", "Exclusão", JOptionPane.YES_NO_OPTION);
        try {
            if (res == JOptionPane.YES_OPTION) {
                if (validar_patrao() != -1) {
                    ctr_func.excluir(tabela.getSelectionModel().getSelectedItem().getCodigo());
                    list = ctr_func.buscar("", "");
                    tabela.setItems(FXCollections.observableArrayList(list));
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Funcionario Excluido com Sucesso! ", ButtonType.OK);
                    a.showAndWait();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Não é possível excluir o patrão!! ", ButtonType.OK);
                    a.showAndWait();
                }
            }

            //}
        } catch (Exception e) {
            System.out.println("" + e);
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao Excluir! ", ButtonType.OK);
            a.showAndWait();
        }
    }

    public static Funcionario getFuncionario() {
        return f;
    }

    @FXML
    private void btnSair(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/proj_engii/telas/TelaCadastroFuncionario.fxml"));
            tela.getChildren().clear();
            tela.getChildren().add(root);
        } catch (Exception e) {
            System.out.println("Erro" + e);
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro! " + e, ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void btnAlt(ActionEvent event) {
        int pos = tabela.getSelectionModel().getSelectedIndex();
        f = new Funcionario();
        f = tabela.getSelectionModel().getSelectedItem();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/proj_engii/telas/TelaCadastroFuncionario.fxml"));
            tela.getChildren().clear();
            tela.getChildren().add(root);
        } catch (Exception e) {
            System.out.println("Erro" + e);
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro! " + e, ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void busca_lista(KeyEvent event) {
        if (cbTipo.getSelectionModel().getSelectedIndex() == -1 && !txtNome.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Escolha um tipo a ser pesquisado!! ", ButtonType.OK);
            a.showAndWait();
        } else {
            list = ctr_func.buscar(txtNome.getText(), cbTipo.getSelectionModel().getSelectedItem().toLowerCase());
        }
        tabela.setItems(FXCollections.observableArrayList(list));
    }
    
    private int validar_patrao(){
        int cont = 0;
        for(int i = 0; i < list.size();i++){
            if(list.get(i).getNivel().equals("Patrão"))
                cont++;
        }
        if(cont == 1 && list.size() == 1)
            return -1;
        return cont;
    }
}
