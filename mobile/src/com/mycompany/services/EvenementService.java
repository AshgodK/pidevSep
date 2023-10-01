/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.mycompany.entities.Evenement;
import java.util.ArrayList;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.codename1.util.Base64;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pc
 */
public class EvenementService {

    private ConnectionRequest req;
    private boolean resultOK;
    private static EvenementService instance = null;
    private ArrayList<Evenement> evenements;

    private EvenementService() {
        req = new ConnectionRequest();
    }

    public static EvenementService getInstance() {
        if (instance == null) {
            instance = new EvenementService();
        }
        return instance;
    }

    public ArrayList<Evenement> parseEvenement(String jsonText) {
        try {
            evenements = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> evenementListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) evenementListJson.get("root");
            for (Map<String, Object> obj : list) {
                Evenement e = new Evenement();
                float id = Float.parseFloat(obj.get("id").toString());
                e.setId((int) id);
                e.setTitre(obj.get("titre").toString());
                e.setDescription(obj.get("description").toString());
              //  e.setDateDebut(new Date(((Double) obj.get("dateDebut")).longValue()));
                e.setDateDebut(new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("dateDebut").toString()));
                 e.setDateFin(new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("dateFin").toString()));
               // e.setDateFin(new Date(((Double) obj.get("dateFin")).longValue()));
         //       e.setDdeb(new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("ddeb").toString()));
          //      e.setDdeb(new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("ddeb").toString()));

                 //  e.setZone(obj.get("zone").toString());
              //  e.setEventimg(obj.get("eventimg").toString());
             //   e.setNbrPasse((Integer) obj.get("nbrPasse"));
                e.setPrix(((Double) obj.get("prix")).floatValue());
                e.setAdresse(obj.get("adresse").toString());
                float NBR = Float.parseFloat(obj.get("nbrP").toString());
                e.setNbrP((int) NBR);

                evenements.add(e);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
        }

        return evenements;
    }

    public ArrayList<Evenement> getAllEvenements() {
        ArrayList<Evenement> listEvenement = new ArrayList<>();
        String url = Statics.BASE_URL + "/json/getAll";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                evenements = parseEvenement(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return evenements;
    }
     public ArrayList<Evenement> getAllEvenementsSorted() {
        ArrayList<Evenement> listEvenement = new ArrayList<>();
        String url = Statics.BASE_URL + "/json/getAllSorted";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                evenements = parseEvenement(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return evenements;
    }
    

   public boolean addEvenement(Evenement e) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String url = Statics.BASE_URL + "/json/new?titre=" + e.getTitre() + "&description=" + e.getDescription() +
            "&dateDebut=" + format.format(e.getDateDebut()) + "&dateFin=" + format.format(e.getDateFin()) +
            "&eventimg=" + e.getEventimg() +
            "&prix=" + e.getPrix() + "&adresse=" + e.getAdresse() + "&nbrp=" + e.getNbrP() + "&idc="+e.getCateg();
    req.setUrl(url);

    // Add a response listener to handle the server response
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            if (req.getResponseCode() == 200) {
                // Successful response
                resultOK = true;
            } else {
                // Error response
                resultOK = false;
                String errorMessage = new String(req.getResponseData(), 0, req.getResponseData().length);
                // Display the error message
                System.out.println("Error Message: " + errorMessage);
            }
            req.removeResponseListener(this);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOK;
}


    public boolean editEvenement(Evenement e) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String url = Statics.BASE_URL + "/json/edit?id=" + e.getId() + "&titre=" + e.getTitre() +
                "&description=" + e.getDescription() + "&dateDebut=" + format.format(e.getDateDebut()) + "&dateFin=" + format.format(e.getDateFin())  +
                "&eventimg=" + e.getEventimg()  +
                "&prix=" + e.getPrix() + "&adresse=" + e.getAdresse() + "&nbrp=" + e.getNbrP();
        req.setUrl(url);
     //   NetworkManager.getInstance().addToQueueAndWait(req);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public void deleteEvenement(int id) {
        Dialog d = new Dialog();
        if (d.show("Delete Evenement", "Do you really want to remove this Evenement?", "Yes", "No")) {
            req.setUrl(Statics.BASE_URL + "/json/delete?id=" + id);
            NetworkManager.getInstance().addToQueueAndWait(req);
            d.dispose();
        }
    }
}