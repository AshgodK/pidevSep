/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Entity.passe;
import java.sql.Connection;
import java.sql.Statement;
import util.MyConnection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author pc
 */
public class passeServ extends passe {

    Connection cnx;

    public void passeServ() {
        cnx = MyConnection.getInstance().getCnx();
    }

    public void AjouterP(passe p, int id_ev, int u) {
        try {
            cnx = MyConnection.getInstance().getCnx();
            String req = "insert into passe (evennement_id, passe_owner_id, code, prix)"
                    + "values('" + id_ev + "','" + u + "','" + p.getCode() + "','" + p.getPriEvent() + "')";
            Statement st = cnx.createStatement();
            System.out.println(u);
            st.executeUpdate(req);
            System.out.println("ticket ajouter avec succ");
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }

    }

    public String getMail(int id) {
        String m = null;

        try {
            cnx = MyConnection.getInstance().getCnx();
            String req = "SELECT email FROM utilisateur WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                m = rs.getString("email");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return m;
    }

    public List<Integer> GetUsersID(int evennement_id) {
        List<Integer> list = new ArrayList<>();
        try {
            cnx = MyConnection.getInstance().getCnx();

            String req = "SELECT passe_owner_id FROM passe WHERE evennement_id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, evennement_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("passe_owner_id"));
                //System.out.println(rs.getInt("passe_owner_id"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

}
