/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Utilisateur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.MyConnection;
//import at.favre.lib.crypto.bcrypt.BCrypt;
//import org.mindrot.jbcrypt.BCrypt ; 
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Firas
 */
public class ServiceUser {

    Connection cnx = MyConnection.getInstance().getCnx();

    public Utilisateur getOneById(int id) {
        Utilisateur p = null;
        try {
            String req = "Select * from utilisateur where id = '" + id + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                p = new Utilisateur(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("role"));
                break;

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return p;
    }

    public int authentification(String email, String password) {
        int id = -1;

        String userpass = "";

        try {
            String req = "SELECT * from utilisateur WHERE utilisateur.`email` ='" + email + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                id = rs.getInt("id");
                userpass = rs.getString("password");

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(id);

        if (id == -1) {
            System.out.println("user not found");
            return -1;
        } else if (!userpass.equals("")) {
            System.out.println("user found");
            String trimmedHashedPassword = userpass.trim();
            System.out.println(trimmedHashedPassword);
            // String testhash = BCrypt.hashpw(password, BCrypt.gensalt());
            /*  if (BCrypt.checkpw(password, testhash)){
           //if (BCrypt.verifyer(BCrypt.Version.VERSION_2Y).verify(password.toCharArray(), trimmedHashedPassword).verified) {
                System.out.println("It matches");
            } else {
                System.out.println("It does not match");
                return -2;
            }
             */
            if (BCrypt.checkpw(password, trimmedHashedPassword)) {
                System.out.println("mot de passe OK");
            } else {
                System.out.println("Mauvais mdp");
            }
        }

        return id;

    }

    public int ChercherMail(String email) {

        try {
            String req = "SELECT * from `utilisateur` WHERE `utilisateur`.`email` ='" + email + "'  ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getString("email").equals(email)) {
                    System.out.println("mail trouvé ! ");
                    return 1;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }
}
