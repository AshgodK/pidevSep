/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;



import java.io.IOException;
import java.net.URL;

import Entity.Evennement;
import Entity.SessionManager;
import Entity.passe;
import Service.EvennementServ;
import Service.passeServ;
import com.itextpdf.text.BaseColor;
import java.util.Optional;

import java.util.ResourceBundle;
import java.util.UUID;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;

import java.io.FileOutputStream;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.fonts.FontsResourceAnchor;
//import com.sun.scenario.effect.ImageData;
import java.io.File;
//import com.itextpdf.layout.element.Image;
import java.util.Properties;
import javaapp.JavaApp;
import javafx.scene.control.TextField;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;





/**
 * FXML Controller class
 *
 * @author pc
 */
public class detailEvent implements Initializable {

    private String username = "mailjavam@gmail.com";
private String password = "jaua dkab qcke halm";
    @FXML
    private AnchorPane anchorPane;
 @FXML
    private TextField mailPasse;
    @FXML
    private Button goToEvents;

    @FXML
    private Label LabelTitreE;

    @FXML
    private Label LabelDDE;

    @FXML
    private Label LabelDFE;

    @FXML
    private Label LabelCatE;

    @FXML
    private Label LabelAdrE;

    @FXML
    private Label LabeldescE;

    @FXML
    private Label LabelPriE;

    @FXML
    private Label LabelNbrpE;

    @FXML
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //EvennementFrontController evc=new EvennementFrontController();
        Evennement e=new Evennement();
        //int eve=EvennementFrontController.Ove;
        e=getEvent(EvennementFrontController.Ove); 
        System.out.println("ove: "+EvennementFrontController.Ove);
        setEvent(e);
    
        
    goToEvents.setOnAction(event -> {
        try {
                
                JavaApp.setRoot("EvennementFront");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    


        
    }  
    
    public void setEvent(Evennement e) {
       // EvennementFrontController efc=new EvennementFrontController();
        //Evennement e=efc.getEvent();
        LabelTitreE.setText(e.getTitre());
       
        LabelDDE.setText(e.getDate_deb());
        LabelDFE.setText(e.getDate_fin());
        LabeldescE.setText(e.getDescription());
        LabelAdrE.setText(e.getAdresse());
        LabelCatE.setText(e.getCat_title());
        LabelPriE.setText(String.valueOf(e.getPrix())); 
        LabelNbrpE.setText(String.valueOf(e.getNbrP()));
        //imagePreview.setImage(new Image(new File(p.getImage().replace('/', '\\')).toURI().toString()));
    }
    
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
    
    
   public void buyP() throws IOException
       {
           // int id_Evnt = Integer.parseInt(IdCell);
          // float priceE= Float.parseFloat(PriceCell);
           //String to=mailPasse.getText();
           passeServ pass=new passeServ();
           String to=SessionManager.getInstance().getCurrentUser().getEmail();
            Alert A = new Alert(Alert.AlertType.CONFIRMATION);
            A.setAlertType(Alert.AlertType.CONFIRMATION);

            A.setContentText("Voulez vous acheter un ticker a evennement  : " + LabelTitreE.getText() + " ?");
            Optional<ButtonType> result = A.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            EvennementServ ev = new EvennementServ();
            if (ev.GetnbrP(EvennementFrontController.Ove)==0) {
                A.setAlertType(Alert.AlertType.WARNING);
            A.setContentText("event full ");
            A.show();
            }
            else if (to.isEmpty()||!to.endsWith("@gmail.com")) {
           Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez Entrez votre mail.");
            alert.showAndWait();
             }
              
            else{
               UUID uuid = UUID.randomUUID();
               String codeP=uuid.toString();
             
            //EvennementServ ev = new EvennementServ();
             //passeServ pass=new passeServ();
             
             passe newP = new passe();
             
            
             newP.setNomEvent(LabelTitreE.getText());
             newP.setCode(codeP);
             newP.setPriEvent(Float.parseFloat(LabelPriE.getText()));
             pass.AjouterP(newP, EvennementFrontController.Ove,SessionManager.getInstance().getCurrentUser().getId());
             ev.updatenbrP(EvennementFrontController.Ove);
             generatePDF(codeP);
             System.out.println(to);
                envoyer(to,codeP);
                 Alert alert = new Alert(Alert.AlertType.WARNING, "Votre pass est  envoyee par mail.");
            alert.showAndWait();
            }
            
   
            }
        
       }
   
   
   
   public void generatePDF(String code)
   {
        try {
            Rectangle pageSize = new Rectangle(400, 220);
            pageSize.setBackgroundColor(new BaseColor(		0xF0, 0xF8, 0xFF));
            
            Document document = new Document(pageSize);
           
             FontsResourceAnchor te = new FontsResourceAnchor();
            PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream("pdf/"+code+".pdf"));
           // writer.setBackgroundColor(new BaseColor(135, 206, 250));
            document.open();
            //Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);

           
            Paragraph paragraph = new Paragraph("PASS "+LabelTitreE.getText());
            paragraph.setAlignment(Element.ALIGN_CENTER);
             Font redFont = new Font();
       redFont.setColor(BaseColor.RED);
            Paragraph paragraph1 = new Paragraph( "Date debut: " +LabelDDE.getText());
             Font blueFont = new Font();
    blueFont.setColor(BaseColor.BLUE);
            Paragraph paragraph2 = new Paragraph( "Date fin: " +LabelDFE.getText());
            Paragraph paragraph3 = new Paragraph("Description: " +LabeldescE.getText());
            
            Paragraph paragraph4 = new Paragraph("Adresse: " +LabelAdrE.getText());
            paragraph4.setFont(redFont);
            Paragraph paragraph5 = new Paragraph("Categorie: " +LabelCatE.getText());
            Paragraph paragraph6 = new Paragraph("Prix: " +LabelPriE.getText());
            Paragraph paragraph7 = new Paragraph("Code: " +code);
             //Paragraph paragraph7 = new Paragraph();
             int[] transparency = new int[]{25, 25, 25};
        Image image = Image.getInstance("src/images/logo.png");
        
        //image.scaleToFit(10, 10);
        image.setTransparency(transparency);
        image.scaleAbsolute(60, 60);
        image.setAbsolutePosition(320, 155);
        document.add(image);
    //document.add(paragraph7);
         paragraph.setPaddingTop(0);
            document.add(paragraph);
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(paragraph5);
            document.add(paragraph6);
            document.add(paragraph7);
            document.close();
            System.out.println("PDF document generated.");
           
        } catch (Exception e) {
            System.err.println("Error generating PDF document: " + e.getMessage());
        }
   }
   public void envoyer(String to,String pdf) throws IOException {
// Etape 1 : Création de la session

       
       
       Properties props = new Properties();
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable","true");
props.put("mail.smtp.host","smtp.gmail.com");
props.put("mail.smtp.port","587");
Session session = Session.getInstance(props,
new javax.mail.Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {
return new PasswordAuthentication(username, password);
}
});
try {
// Etape 2 : Création de l'objet Message
Message message = new MimeMessage(session);
message.setFrom(new InternetAddress(username));
message.setRecipients(Message.RecipientType.TO,
InternetAddress.parse(to));
message.setSubject("PASSE");
//message.setText("Bonjour, ce message est un test ...");
// Etape 3 : Envoyer le message

Multipart multipart = new MimeMultipart();

// create the text part
MimeBodyPart textPart = new MimeBodyPart();
textPart.setText("your pass has been created");

// create the pdf part
MimeBodyPart pdfPart = new MimeBodyPart();
pdfPart.attachFile(new File("D:/UNI/gitProject/New folder/PDF/"+pdf+".pdf")); // specify the file path of the pdf
System.out.println("D:/NetBeans/JavaApp/PDF"+pdf+".pdf");
// add the parts to the multipart message
multipart.addBodyPart(textPart);
multipart.addBodyPart(pdfPart);

// add the multipart message to the email message
message.setContent(multipart);
Transport.send(message);
System.out.println("Message_envoye");
} catch (MessagingException e) {
throw new RuntimeException(e);
} 
       }
   
   

 
}
