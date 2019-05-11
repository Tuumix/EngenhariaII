/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj_engii;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import proj_engii.bancoc.Banco;

/**
 *
 * @author hiroshi
 */
public class Proj_EngII extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/proj_engii/telas/FXMLDocument.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(true);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Erro" + e);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(Banco.conectar())
            launch(args);
        else
            System.out.println("lascou!");
    }

}
