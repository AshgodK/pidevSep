/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entity.EventCategory;
import util.MyConnection;
import Service.EventCategServ;
import Service.EventCateg;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javaapp.JavaApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author pc
 */
public class EventCategController implements Initializable {
   // public static int idU=1;

    @FXML
    private TextField titre;
    @FXML
    private TextArea description;
    @FXML
    private TableView<EventCategory> tableViewEvCtg;
    @FXML
    private TableColumn<?, ?> id_cat_event;
    @FXML
    private TableColumn<?, ?> titre_cat_event;
    @FXML
    private TableColumn<?, ?> description_cat_event;
    @FXML
    private Button goToEvs;
    
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AfficherCateg();
        
        goToEvs.setOnAction(event -> {
        try {
                
                JavaApp.setRoot("evennement");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }    
    
    
    MyConnection cnxfv = null;
     Statement st = null;
     //EventCategServ ev = new EventCategServ() {};

    @FXML
    private void ajouter(javafx.event.ActionEvent event) 
    {
         String ttr=titre.getText();
        String dct=description.getText();
        
        EventCategServ ev1 = new EventCategServ();
        List<String> categories = ev1.GetCategoriesTitle();
        String selectedCategory = ttr;
        
        
        if (ttr.isEmpty()|| dct.isEmpty()) 
         {
            // Alerte si un champ est vide
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            alert.showAndWait();
           
         } 
       else if (categories.contains(selectedCategory)) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "categorie existe");
            alert.showAndWait();
}
         else 
         { 
             EventCategServ ev = new EventCategServ() {};
             EventCategory e=new EventCategory();
         
           e.setTitre(ttr);
                e.setDescription(dct);
         
                ev.AjouterEventCateg(e);
             
             
             
         }        
    }
    
    @FXML
    public void AfficherCateg()
    {tableViewEvCtg.getItems().clear();
        EventCategServ ev=new EventCategServ();
        List<EventCategory> lC = ev.GetCategories();
        ObservableList<EventCategory> CatList = FXCollections.observableArrayList(lC);
        
        
        id_cat_event.setCellValueFactory(new PropertyValueFactory<>("id"));
        titre_cat_event.setCellValueFactory(new PropertyValueFactory<>("titre"));
        description_cat_event.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableViewEvCtg.setItems(CatList);
    }
    
    @FXML
    public void del()
    {
        int SelectedRowIndex = tableViewEvCtg.getSelectionModel().getSelectedIndex();
        
        int ColumnIndex = tableViewEvCtg.getColumns().indexOf(id_cat_event);
        
        
        Alert A = new Alert(Alert.AlertType.CONFIRMATION);
        if (SelectedRowIndex == -1) {
            A.setAlertType(Alert.AlertType.WARNING);
            A.setContentText("Selectionnez une colonne ! ");
            A.show();
        } else {
            String IdCell = tableViewEvCtg.getColumns().get(ColumnIndex).getCellData(SelectedRowIndex).toString();
            int id_supp = Integer.parseInt(IdCell);
            EventCategServ ec = new EventCategServ();
            A.setAlertType(Alert.AlertType.CONFIRMATION);

            A.setContentText("Voulez vous Supprimer categorie  dont l'ID : " + IdCell + " ?");
            Optional<ButtonType> result = A.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                ec.supprimerEventcat(id_supp);
                A.setAlertType(Alert.AlertType.INFORMATION);
                A.setContentText("categorie Supprimé ! ");
                A.show();
                
            }

        }
    }
    
    @FXML
    public void update()
    {
        int SelectedRowIndex = tableViewEvCtg.getSelectionModel().getSelectedIndex();
        
        int ColumnIndex = tableViewEvCtg.getColumns().indexOf(id_cat_event);
        
        
        Alert A = new Alert(Alert.AlertType.CONFIRMATION);
        if (SelectedRowIndex == -1) {
            A.setAlertType(Alert.AlertType.WARNING);
            A.setContentText("Selectionnez une colonne ! ");
            A.show();
        } else {
            String IdCell = tableViewEvCtg.getColumns().get(ColumnIndex).getCellData(SelectedRowIndex).toString();
           int id_modif = Integer.parseInt(IdCell);
           //EventCategServ ec = new EventCategServ();
            A.setAlertType(Alert.AlertType.CONFIRMATION);

            A.setContentText("Voulez vous modifier la categorie dont l'ID : " + IdCell + " ?");
            Optional<ButtonType> result = A.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
               String ttr=titre.getText();
        
        String dct=description.getText();
        
        
         if (ttr.isEmpty()|| dct.isEmpty()) 
         {
            // Alerte si un champ est vide
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            alert.showAndWait();
           
         } 
         else 
         { 
             EventCategServ ev = new EventCategServ() {};
             EventCategory e=new EventCategory();
         
                e.setTitre(ttr);
                e.setDescription(dct);
         
                ev.ModifierEventCateg(e,id_modif);
             
             
             
         }  
                
            }
        }
    }
}
