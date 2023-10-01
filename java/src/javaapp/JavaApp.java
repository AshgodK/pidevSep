/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapp;

import Entity.Utilisateur;
import gui.ConnectionController;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author pc
 */
public class JavaApp extends Application{

    /**
     * @param args the command line arguments
     */
    private static Scene scene;
    private static ConnectionController connection ;

    @Override
    public void start(Stage stage) throws IOException {
        
        scene = new Scene(loadFXML("connection"));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaApp.class.getResource("/gui/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public  static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, URISyntaxException {
       
        launch();  
    }
    
}
