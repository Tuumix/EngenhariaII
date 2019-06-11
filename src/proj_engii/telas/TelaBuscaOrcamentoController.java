/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.telas;

import Controladora.CtrlOrcamento;
import static com.sun.javafx.css.SizeUnits.S;
import proj_engii.entidade.ItensOrcamento;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.paint.Color;
import javafx.util.converter.IntegerStringConverter;
import javax.swing.JOptionPane;

import proj_engii.entidade.Orcamento;
import proj_engii.entidade.Produto;

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
    private TableColumn<ItensOrcamento, String> col_prodI;
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
    private ArrayList<Produto> lista_produto = new ArrayList<>();
    private Produto p = new Produto();
    private ArrayList<Integer> lista_excluido = new ArrayList<>();
    @FXML
    private TextField txt_tipo;
    @FXML
    private Label lbStatus;
    @FXML
    private TableColumn<?, ?> col_dtcriacao;
    @FXML
    private TableColumn<?, ?> col_dtvencimento;
    @FXML
    private TableColumn<?, ?> cod_itens;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCells();
        lista_produto = p.buscar("");
        cbPesquisa.getItems().add("Cliente");
        cbPesquisa.getItems().add("Funcionario");
        tabela_orcamento.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        aux = ctrl_orc.buscar("");
        reverte_data();
        tabela_orcamento.setItems(FXCollections.observableArrayList(aux));
        // TODO
    }

    /*    private int itensorc_cod, orc_cod, prod_cod, prod_qtde;
    String prod_desc;*/
    public void setCells() {
        tabela_orcamento.setEditable(true);

        col_cod.setCellValueFactory(new PropertyValueFactory<>("orc_cod"));
        col_cli.setCellValueFactory(new PropertyValueFactory<>("orc_cliente"));
        col_func.setCellValueFactory(new PropertyValueFactory<>("orc_func"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("orc_total"));
        col_dtcriacao.setCellValueFactory(new PropertyValueFactory<>("orc_criado"));
        col_dtvencimento.setCellValueFactory(new PropertyValueFactory<>("orc_validade"));

        cod_itens.setCellValueFactory(new PropertyValueFactory<>("itensorc_cod"));
        col_codI.setCellValueFactory(new PropertyValueFactory<>("prod_cod"));
        col_prodI.setCellValueFactory(new PropertyValueFactory<>("prod_desc"));
        col_codO.setCellValueFactory(new PropertyValueFactory<>("orc_cod"));
        col_qtdeI.setCellValueFactory(new PropertyValueFactory<>("prod_qtde"));
        col_qtdeI.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

    }

    @FXML
    private void selected(MouseEvent event) {
        int cod;
        Orcamento orc;
        orc = tabela_orcamento.getSelectionModel().getSelectedItem();
        if (orc.getAprovado()) {
            lbStatus.setText("Aprovado");
            lbStatus.setTextFill(Color.web("#44bd32"));
        } else {
            lbStatus.setText("Não Aprovado");
            lbStatus.setTextFill(Color.web("#e84118"));
        }
        tabela_itens.setItems(FXCollections.observableArrayList(ctrl_orc.buscar_itens(orc.getOrc_cod())));
    }

    @FXML
    private void btnExcluir(ActionEvent event) {
        int cod;
        int res = JOptionPane.showConfirmDialog(null, "Deseja Realmente excluir?", "Exclusão", JOptionPane.YES_NO_OPTION);

        if (res == JOptionPane.YES_OPTION) {
            if (!validar()) {
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
        }
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
                    list.add(new Orcamento(p.getOrc_cod(), p.getOrc_func(), p.getOrc_total(), p.getOrc_cliente(), p.getAprovado(), p.getOrc_criado(), p.getOrc_validade()));
                }
            }
        } else {
            for (int i = 0; i < aux.size(); i++) {
                p = aux.get(i);
                if (p.getOrc_func().contains(txt_tipo.getText())) {
                    list.add(new Orcamento(p.getOrc_cod(), p.getOrc_func(), p.getOrc_total(), p.getOrc_cliente(), p.getAprovado(), p.getOrc_criado(), p.getOrc_validade()));
                }
            }
        }
        tabela_orcamento.getItems().clear();
        tabela_orcamento.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    private void btnConfirmar(ActionEvent event) {
        double soma = 0.0;
        try {
            for (int i = 0; i < tabela_itens.getItems().size(); i++) {
                soma += get_mult(tabela_itens.getItems().get(i).getProd_cod(), tabela_itens.getItems().get(i).getProd_qtde());
                if (ctrl_orc.altera_itensorc(tabela_itens.getItems().get(i).getProd_qtde(), tabela_itens.getItems().get(i).getItensorc_cod())) {
                    System.out.println("ok");
                }
            }
            for (int j = 0; j < lista_excluido.size(); j++) {
                if (!ctrl_orc.excluir_itensorc(lista_excluido.get(j))) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao excluir item!! ", ButtonType.OK);
                    a.showAndWait();
                }
            }
            ctrl_orc.atualizar_total(soma, tabela_orcamento.getSelectionModel().getSelectedItem().getOrc_cod());
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Orçamento alterado com sucesso!! ", ButtonType.OK);
            a.showAndWait();
        } catch (Exception e) {

        }
    }

    @FXML
    private void btnAprovar(ActionEvent event) {
        if (ctrl_orc.aprovar_orc(tabela_orcamento.getSelectionModel().getSelectedItem().getOrc_cod())) {
            aux = ctrl_orc.buscar("");
            tabela_orcamento.setItems(FXCollections.observableArrayList(aux));
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Orçamento aprovado com sucesso!! ", ButtonType.OK);
            a.showAndWait();
        }
    }

    private Boolean validar() {
        Boolean erro = false;

        if (tabela_orcamento.getSelectionModel().getSelectedItem().getAprovado() == true) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Não é possível excluir um orçamento aprovado!! ", ButtonType.OK);
            a.showAndWait();
            erro = true;
        }

        return erro;
    }

    private void reverte_data() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (int i = 0; i < aux.size(); i++) {
            aux.get(i).setOrc_criado(LocalDate.parse(aux.get(i).getOrc_criado(), formatter).format(formatter2).toString());
            aux.get(i).setOrc_validade(LocalDate.parse(aux.get(i).getOrc_validade(), formatter).format(formatter2).toString());
        }
    }

    private double get_mult(int cod, int qtde) {
        int i = 0;
        while (lista_produto.get(i).getCodigo() != cod) {
            i++;
        }
        return qtde * lista_produto.get(i).getValor();
    }

    @FXML
    private void btnRetirar(ActionEvent event) {
        lista_excluido.add(tabela_itens.getSelectionModel().getSelectedItem().getItensorc_cod());
        tabela_itens.getItems().remove(tabela_itens.getSelectionModel().getSelectedIndex());
        tabela_itens.refresh();
    }


    @FXML
    private void edit_table(TableColumn.CellEditEvent<ItensOrcamento, Integer> event) {
        ItensOrcamento itens_orc = tabela_itens.getSelectionModel().getSelectedItem();
        if (event.getNewValue() < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Atribua um valor valido!! ", ButtonType.OK);
            a.showAndWait();
        }else{
            itens_orc.setProd_qtde(event.getNewValue());
        }
    }
}
