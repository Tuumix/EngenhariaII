/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.telas;

import Controladora.CtrlFuncionario;
import Controladora.CtrlOrcamento;
import Controladora.CtrlProduto;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import proj_engii.bancoc.Banco;
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
    private CtrlFuncionario ctrl_func = new CtrlFuncionario();
    private CtrlProduto ctrl_prod = new CtrlProduto();
    private CtrlOrcamento ctrl_orc = new CtrlOrcamento();
    private ArrayList<Funcionario> list_func = new ArrayList<>();
    private ArrayList<Produto> list_prod = new ArrayList<>();
    private ArrayList<Produto> aux = new ArrayList<>();
    private ArrayList<Produto> prod_orc = new ArrayList<>();
    private Object[] ob = new Object[10];
    @FXML
    private Label txt_total;
    private Double total = 0.0;
    private Produto p;
    @FXML
    private AnchorPane tela;

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
    }

    @FXML
    private void btnAdd(ActionEvent event) {
        Produto p = new Produto();
        Boolean achou = false;
        total = 0.0;

        if (tab_prod.getSelectionModel().getSelectedIndex() != -1) {
            p = tab_prod.getSelectionModel().getSelectedItem();
            if (!txt_quantidade.getText().isEmpty() && p.getQtde() >= Integer.parseInt(txt_quantidade.getText()) && Integer.parseInt(txt_quantidade.getText()) != 0) {
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
        total = getTotal();
        txt_total.setText(total + "");
    }

    private void setCells() {
        //------------------------------------------------------------------------- Produto
        col_codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        col_nome.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        col_qtd.setCellValueFactory(new PropertyValueFactory<>("qtde"));
        col_valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        //------------------------------------------------------------------------- Orçamento
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
        int cod, qtde, i = 0;
        Produto p;

        while (tab_orc.getSelectionModel().getSelectedItem().getCodigo() != prod_orc.get(i).getCodigo()) {
            i++;
        }
        if (i < prod_orc.size()) {
            cod = tab_orc.getSelectionModel().getSelectedItem().getCodigo();
            qtde = tab_orc.getSelectionModel().getSelectedItem().getQtde();
            atualiza_produtos(cod, qtde);
            prod_orc.remove(i);
        }
        total = getTotal();
        txt_total.setText(total+"");
        tab_orc.getItems().clear();
        tab_orc.getItems().addAll(prod_orc);
    }

    private void atualiza_produtos(int cod, int qtde) {
        int i = 0;
        while (i < list_prod.size() && list_prod.get(i).getCodigo() != cod) {
            i++;
        }
        if (i < list_prod.size()) {
            list_prod.get(i).setQtde(list_prod.get(i).getQtde() + qtde);
        }

        tab_prod.getItems().clear();
        tab_prod.getItems().addAll(list_prod);
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
        Boolean certo = true;
        int ult_orc;
        try {
            if (validacao()) {
                ob[0] = cb_func.getSelectionModel().getSelectedItem().toString();
                ob[1] = total;
                ob[2] = "Juvenildo";
                if (ctrl_orc.salvar(ob)) {
                    ult_orc = Banco.con.getMaxPK("orcamento", "orc_cod");

                    for (int j = 0; j < prod_orc.size(); j++) {
                        p = prod_orc.get(j);
                        if (ctrl_orc.salvar_itens(ult_orc, p.getCodigo(), p.getQtde(), p.getDescricao())) {
                            //certo = false;
                        } else {
                            certo = false;
                        }
                    }
                    if (certo) {
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Orcamento cadastrado!", ButtonType.OK);
                        a.showAndWait();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar orçamento!", ButtonType.OK);
                        a.showAndWait();
                    }
                }
            }

        } catch (Exception e) {

        }
    }

    public Boolean validacao() {
        Boolean certo = true;

        if (cb_func.getSelectionModel().getSelectedIndex() == -1) {
            certo = false;
            Alert a = new Alert(Alert.AlertType.ERROR, "Selecione o funcionário!", ButtonType.OK);
            a.showAndWait();
        }

        if (tab_orc.getItems().size() == 0) {
            certo = false;
            Alert a = new Alert(Alert.AlertType.ERROR, "Insira os produtos do orçamento!", ButtonType.OK);
            a.showAndWait();
        }
        return certo;
    }

    @FXML
    private void btnBuscar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/proj_engii/telas/TelaBuscaOrcamento.fxml"));
            tela.getChildren().clear();
            tela.getChildren().add(root);
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
            tela.getChildren().clear();
            tela.getChildren().add(root);
        } catch (Exception e) {
            System.out.println("Erro" + e);
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro! " + e, ButtonType.OK);
            a.showAndWait();
        }
    }

    private Boolean valida_add() {
        Boolean certo = true;
        if (cb_func.getSelectionModel().getSelectedIndex() == -1) {
            certo = false;
            Alert a = new Alert(Alert.AlertType.ERROR, "Selecione um funcionário! ", ButtonType.OK);
            a.showAndWait();
        }
        return certo;
    }

    private double getTotal() {
        total = 0.0;
        for (int i = 0; i < prod_orc.size(); i++) {
            total += prod_orc.get(i).getValor() * prod_orc.get(i).getQtde();
        }
        return total;
    }
}
