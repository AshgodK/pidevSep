/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entity.SessionManager;
import Entity.Utilisateur;
import Service.ServiceUser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javaapp.JavaApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ConnectionController implements Initializable {

    @FXML
    private Button connectionButton;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordField;

    public ConnectionController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @FXML
    public void connectionClicked() throws SQLException, IOException, URISyntaxException, ClassNotFoundException {
        String page = "";
        String email = lastNameField.getText();
        String password = passwordField.getText();
        int id = -1;
        ServiceUser sa = new ServiceUser();
        Alert alert = new Alert(Alert.AlertType.NONE);
        SessionManager sessionManager = SessionManager.getInstance();
        id = sa.authentification(email, password);
        String role = "";
        if (id == -1) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText(" erroné ! ");
            alert.show();
        } else {
            sessionManager.setCurrentUser(sa.getOneById(id));
            role = sa.getOneById(id).getRole();
            switch (role) {
                case "admin":
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Admin connecté");
                    alert.show();
                    JavaApp.setRoot("Evennement");

                    break;
                case "user":
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("user connecté");
                    alert.show();
                    JavaApp.setRoot("EvennementFront");

                    break;

            }

        }
    }

}
