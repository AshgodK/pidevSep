/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import Entity.EventCategory;
import util.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;


/**
 *
 * @author pc
 */
public class EventCategServ implements EventCateg{
    
    Connection cnx;
    //Connection cnx = DataSource.getInstance().getCnx();
    // cnx=MyConnection.getInstance().getCnx();

    public void eventCategServ() {
                cnx=MyConnection.getInstance().getCnx();
    }
    
    
   // @Override
    public void AjouterEventCateg(EventCategory e)
    {
     try {
         cnx=MyConnection.getInstance().getCnx();
                        

                String req = "INSERT INTO event_category (titre, description) "
                   + "VALUES ('"+e.getTitre()+"','"+e.getDescription() +"')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("category ajoutée avec succès");
            } catch (SQLException ex) {
                
                System.out.println(ex.getMessage());        
            }   
    
    
    }
    
    
    public List<EventCategory> GetCategories() 
    {
       List<EventCategory> list=new ArrayList<>();
        try {
            cnx=MyConnection.getInstance().getCnx();

            String req = "Select  *  FROM event_category";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                    EventCategory rec = new EventCategory();
                   rec.setId(rs.getLong("id"));
                   
                   rec.setDescription(rs.getString("description"));
                   rec.setTitre(rs.getString("titre"));
                  
                   
              
                list.add(rec);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return list;
    }
    
    
    
    public void supprimerEventcat(int id) {
     try {
          cnx=MyConnection.getInstance().getCnx();
            String req = "DELETE FROM event_category WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("category deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    public void ModifierEventCateg(EventCategory e,int i) {
     try {
         cnx=MyConnection.getInstance().getCnx();
            String req = " UPDATE event_category SET " + " titre=?, description = ?  where id = ? ";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
          
            
          
               pst.setString(1,e.getTitre());
               pst.setString(2, e.getDescription());
               pst.setLong(3, i);
                
             
            pst.executeUpdate();
            System.out.println("categorie   modifié !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }   
    }
    
    
    
    public List<String> GetCategoriesTitle() 
    {
       List<String> list=new ArrayList<>();
        try {
            cnx=MyConnection.getInstance().getCnx();

            String req = "Select  titre  FROM event_category";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
        
                list.add(rs.getString("titre"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return list;
    }
    
    public int GetCategorieID(String cat) 
    {
       int id = -1;
    try {
        cnx = MyConnection.getInstance().getCnx();
        String req = "SELECT ID FROM event_category WHERE titre = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, cat);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            id = rs.getInt("ID");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return id;
    }
    
    public String GetCategorieTitre(int id) {
    String titre = null;
    try {
        cnx = MyConnection.getInstance().getCnx();
        String req = "SELECT titre FROM event_category WHERE ID = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            titre = rs.getString("titre");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return titre;
}
    
}
