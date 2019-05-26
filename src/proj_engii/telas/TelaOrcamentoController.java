/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii.telas;

import Controladora.CtrlProduto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author hiroshi
 */
public class TelaOrcamentoController implements Initializable {

    @FXML
    private ScrollPane sub_tela;
    private CtrlProduto ctrl_prod = new CtrlProduto();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btBuscar(ActionEvent event) {
    }

    @FXML
    private void btnNovo(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/proj_engii/telas/TelaBuscaOrcamento.fxml"));
            sub_tela.setContent(root);
            
            this.center(sub_tela.getViewportBounds(), root);
            sub_tela.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {this.center(newValue, root);
            });
        } catch (Exception e) {
            System.out.println("Erro" + e);
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro! " + e, ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void btnGravar(ActionEvent event) {
    }

    @FXML
    private void btnAlterar(ActionEvent event) {
    }

    @FXML
    private void btnSair(ActionEvent event) {
    }

    private void center(Bounds viewPortBounds, Parent centeredNode) {
        double width = viewPortBounds.getWidth();
        double height = viewPortBounds.getHeight();
        double pwidth = ((Region) centeredNode).getPrefWidth();
        double pheight = ((Region) centeredNode).getPrefHeight();

        if (width > pwidth) {
            centeredNode.setTranslateX((width - pwidth) / 2);
        } else {
            centeredNode.setTranslateX(0);
        }
        if (height > pheight) {
            centeredNode.setTranslateY((height - pheight) / 2);
        } else {
            centeredNode.setTranslateY(0);
        }
    }
    
}
