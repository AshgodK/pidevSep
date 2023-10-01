/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;


import Entity.Evennement;
import Entity.EventCategory;
import Entity.SessionManager;
import Entity.passe;
import Service.EvennementServ;
import Service.EventCategServ;
import Service.mail;
import Service.passeServ;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class EvennementFrontController implements Initializable {

    public static int Ove;
    
    @FXML
    private TableView<Evennement> tableViewEv;
    
    @FXML
    private TextField sID;
    
    @FXML
    private ChoiceBox<String> categorie;
     @FXML
    private Button goToDetail;
    @FXML
    private TableColumn<?, ?> id_event;
    @FXML
    private TableColumn<?, ?> titre_event;
    @FXML
    private TableColumn<?, ?> dateD_event;
    private TableColumn<?, ?> dateF_event;
    @FXML
    private TableColumn<?, ?> cat_event;
    private TableColumn<?, ?> adr_event;
    private TableColumn<?, ?> dec_event;
    private TableColumn<?, ?> prix_event;
    private TableColumn<?, ?> nbrP_event;
    @FXML
    private Button GoToProfile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        EventCategServ ev = new EventCategServ();
    categorie.getItems().addAll(ev.GetCategoriesTitle());
       
categorie.setOnAction(event -> {
    AfficherEventByCat();
});
     GoToProfile.setOnAction(event -> {
        try {
                SessionManager.getInstance().setCurrentUser(null);
                JavaApp.setRoot("connection");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


    }  
    
    
  
    
    @FXML
    public void AfficherEvent() {
    tableViewEv.getItems().clear();
    EvennementServ ev = new EvennementServ();
    List<Evennement> lE = ev.GetEvennements();
    // System.out.println(ConnectionController.idU);
   
    ObservableList<Evennement> EVList = FXCollections.observableArrayList(lE);
    id_event.setVisible(false);
    id_event.setCellValueFactory(new PropertyValueFactory<>("id"));
    titre_event.setCellValueFactory(new PropertyValueFactory<>("titre"));
//    dec_event.setCellValueFactory(new PropertyValueFactory<>("description"));
    dateD_event.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
  //  dateF_event.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
    cat_event.setCellValueFactory(new PropertyValueFactory<>("cat_title"));
    //adr_event.setCellValueFactory(new PropertyValueFactory<>("adresse"));
    //prix_event.setCellValueFactory(new PropertyValueFactory<>("prix"));
    //nbrP_event.setCellValueFactory(new PropertyValueFactory<>("nbrP"));
    tableViewEv.setItems(EVList);
}
    
    
    
    
    
    
     
     
     
    @FXML
     public void AfficherEventByTitle() {
    tableViewEv.getItems().clear();
    EvennementServ ev = new EvennementServ();
    String t=sID.getText();
    List<Evennement> lE = ev.GetEvennementsByTitle(t);
    ObservableList<Evennement> EVList = FXCollections.observableArrayList(lE);
   id_event.setVisible(false);
    id_event.setCellValueFactory(new PropertyValueFactory<>("id"));
    titre_event.setCellValueFactory(new PropertyValueFactory<>("titre"));
//    dec_event.setCellValueFactory(new PropertyValueFactory<>("description"));
    dateD_event.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
  //  dateF_event.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
    cat_event.setCellValueFactory(new PropertyValueFactory<>("cat_title"));
    //adr_event.setCellValueFactory(new PropertyValueFactory<>("adresse"));
    //prix_event.setCellValueFactory(new PropertyValueFactory<>("prix"));
    //nbrP_event.setCellValueFactory(new PropertyValueFactory<>("nbrP"));
    tableViewEv.setItems(EVList);
}
     
      public void AfficherEventByCat() {
    tableViewEv.getItems().clear();
    EvennementServ ev = new EvennementServ();
   //String t=sID.getText();
    String t = categorie.getValue();
    EventCategServ ecs= new EventCategServ();
             
             int cat = ecs.GetCategorieID(t);
             if (cat == -1) {
    // Category not found, show error message and return
    Alert alert = new Alert(Alert.AlertType.ERROR, "Selected category not found.");
    alert.showAndWait();
    return;
}
    List<Evennement> lE = ev.GetEvennementsBycat(cat);
    ObservableList<Evennement> EVList = FXCollections.observableArrayList(lE);
    id_event.setVisible(false);
    id_event.setCellValueFactory(new PropertyValueFactory<>("id"));
    titre_event.setCellValueFactory(new PropertyValueFactory<>("titre"));
//    dec_event.setCellValueFactory(new PropertyValueFactory<>("description"));
    dateD_event.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
  //  dateF_event.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
    cat_event.setCellValueFactory(new PropertyValueFactory<>("cat_title"));
    //adr_event.setCellValueFactory(new PropertyValueFactory<>("adresse"));
    //prix_event.setCellValueFactory(new PropertyValueFactory<>("prix"));
    //nbrP_event.setCellValueFactory(new PropertyValueFactory<>("nbrP"));
    tableViewEv.setItems(EVList);
}
      
    @FXML
       public void AfficherEventTriDD() {
    tableViewEv.getItems().clear();
    EvennementServ ev = new EvennementServ();
    List<Evennement> lE = ev.GetEvennementsTrierDD();
    
   
    ObservableList<Evennement> EVList = FXCollections.observableArrayList(lE);

   id_event.setVisible(false);
    id_event.setCellValueFactory(new PropertyValueFactory<>("id"));
    titre_event.setCellValueFactory(new PropertyValueFactory<>("titre"));
//    dec_event.setCellValueFactory(new PropertyValueFactory<>("description"));
    dateD_event.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
  //  dateF_event.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
    cat_event.setCellValueFactory(new PropertyValueFactory<>("cat_title"));
    //adr_event.setCellValueFactory(new PropertyValueFactory<>("adresse"));
    //prix_event.setCellValueFactory(new PropertyValueFactory<>("prix"));
    //nbrP_event.setCellValueFactory(new PropertyValueFactory<>("nbrP"));
    tableViewEv.setItems(EVList);
}
       
    @FXML
       public void buyP()
       {
           int SelectedRowIndex = tableViewEv.getSelectionModel().getSelectedIndex();
        
        int ColumnID = tableViewEv.getColumns().indexOf(id_event);
        int ColumnTitle = tableViewEv.getColumns().indexOf(titre_event);
        int ColumnPrice = tableViewEv.getColumns().indexOf(prix_event);
        
        
        Alert A = new Alert(Alert.AlertType.CONFIRMATION);
        if (SelectedRowIndex == -1) {
            A.setAlertType(Alert.AlertType.WARNING);
            A.setContentText("Selectionnez une colonne ! ");
            A.show();
        } else {
            String IdCell = tableViewEv.getColumns().get(ColumnID).getCellData(SelectedRowIndex).toString();
            String TitleCell = tableViewEv.getColumns().get(ColumnTitle).getCellData(SelectedRowIndex).toString();
           String PriceCell = tableViewEv.getColumns().get(ColumnPrice).getCellData(SelectedRowIndex).toString();
            int id_Evnt = Integer.parseInt(IdCell);
           float priceE= Float.parseFloat(PriceCell);
            A.setAlertType(Alert.AlertType.CONFIRMATION);

            A.setContentText("Voulez vous acheter un ticker a evennement  : " + TitleCell + " ?");
            Optional<ButtonType> result = A.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            EvennementServ ev = new EvennementServ();
            if (ev.GetnbrP(id_Evnt)==0) {
                A.setAlertType(Alert.AlertType.WARNING);
            A.setContentText("event full ");
            A.show();
            }
              
            else{
               UUID uuid = UUID.randomUUID();
               String codeP=uuid.toString();
             
            //EvennementServ ev = new EvennementServ();
             passeServ pass=new passeServ();
             
             passe newP = new passe();
             
            
             newP.setNomEvent(TitleCell);
             newP.setCode(codeP);
             newP.setPriEvent(priceE);
             pass.AjouterP(newP, id_Evnt,SessionManager.getInstance().getCurrentUser().getId());
             ev.updatenbrP(id_Evnt);
            }
            
   
            }
        }
       }
       
       public int getID()
       {
       int SelectedRowIndex = tableViewEv.getSelectionModel().getSelectedIndex();       
        int ColumnID = -1;
        int num=-1;
        
            
          //ColumnID = tableViewEv.getColumns().indexOf(id_event);
        Alert A = new Alert(Alert.AlertType.CONFIRMATION);
        if (SelectedRowIndex == -1) {
            A.setAlertType(Alert.AlertType.WARNING);
            A.setContentText("Selectionnez une colonne ! ");
            A.show();
            
        }
        else{ColumnID = tableViewEv.getColumns().indexOf(id_event);
           String IdCell = tableViewEv.getColumns().get(ColumnID).getCellData(SelectedRowIndex).toString();
          num =Integer.parseInt(IdCell);
        } 
            return num;
       }
    @FXML
       public int loadDetail()
       {
           int t=getID();
           if (t!=-1) {
               Ove=t;
           }
       goToDetail.setOnAction(event -> {
        try {
                
                JavaApp.setRoot("detailEvent");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
       return t;}
       
       public Evennement getEvent(int t)
       {
           EvennementServ e=new EvennementServ();
          // Evennement ev=new Evennement();
         //  int t=getID();
           System.out.println(t);
           String str = Integer.toString(t);
           Evennement ev=e.GetEvennementByID(str);           
           return ev;
       
       }
       
       
       
       
       
       

    
}
