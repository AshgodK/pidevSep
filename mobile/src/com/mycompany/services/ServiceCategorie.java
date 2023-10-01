/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Categorie;
import com.mycompany.entities.Evenement;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author pc
 */
public class ServiceCategorie {
      
    private ConnectionRequest req;
    private boolean resultOK;
    private static ServiceCategorie instance = null;
    private ArrayList<Categorie> cats;
    
      private ServiceCategorie() {
        req = new ConnectionRequest();
    }

    
       public static ServiceCategorie getInstance() {
        if (instance == null) {
            instance = new ServiceCategorie();
        }
        return instance;
    }

    
    
    public boolean addCat(Categorie c )
    {
        
         
    String url = Statics.BASE_URL + "/json/newCat?titre=" + c.getNom()+ "&description=" + c.getDesc();
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
    
     public ArrayList<Categorie> parseCats(String jsonText) {
        try {
            cats = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> catlistjson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) catlistjson.get("root");
            for (Map<String, Object> obj : list) {
                Categorie e = new Categorie();
                float id = Float.parseFloat(obj.get("id").toString());
                e.setId((int) id);
                e.setNom(obj.get("titre").toString());
                e.setDesc(obj.get("description").toString());
                cats.add(e);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return cats;
    }

    public ArrayList<Categorie> getAllCats() {
        String url = Statics.BASE_URL + "/json/getAllcats";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cats = parseCats(new String(req.getResponseData()));
                System.out.println(cats);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return cats;
    }
    
    public boolean editc(Categorie c) {
     
        String url = Statics.BASE_URL + "/json/editc?id=" + c.getId() + "&titre=" + c.getNom()+
                "&description=" + c.getDesc();
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
    
    
    public void deletec(int id) {
        Dialog d = new Dialog();
        if (d.show("Delete Category", "Do you really want to remove this Category?", "Yes", "No")) {
            req.setUrl(Statics.BASE_URL + "/json/deletec?id=" + id);
            NetworkManager.getInstance().addToQueueAndWait(req);
            d.dispose();
        }
    }
    
    
}
