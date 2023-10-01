/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;

/**
 *
 * @author pc
 */
public class Evenement {
    private Integer id;
    private String titre;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private String eventimg;
    private Float prix;
    private String adresse;
    private Integer nbrP;
    private int categ; 

    public Evenement() {
    }

    public Evenement(Integer id, String titre, String description, Date dateDebut, Date dateFin, String eventimg, Float prix, String adresse, Integer nbrP) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.eventimg = eventimg;
        this.prix = prix;
        this.adresse = adresse;
        this.nbrP = nbrP;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }




    public String getEventimg() {
        return eventimg;
    }

    public void setEventimg(String eventimg) {
        this.eventimg = eventimg;
    }


    
    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getNbrP() {
        return nbrP;
    }

    public void setNbrP(Integer nbrP) {
        this.nbrP = nbrP;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", eventimg=" + eventimg + ", prix=" + prix + ", adresse=" + adresse + ", nbrP=" + nbrP + '}';
    }

    public int getCateg() {
        return categ;
    }

    public void setCateg(int categ) {
        this.categ = categ;
    }
    
    

    
    
}
