/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author pc
 */
public class Categorie {
    
    private int id ; 
    private String nom ; 
    private String desc; 

    public Categorie() {
    }
    
    

    public Categorie(int id, String nom, String desc) {
        this.id = id;
        this.nom = nom;
        this.desc = desc;
    }

    public Categorie(String nom, String desc) {
        this.nom = nom;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return nom;
    }

   

   
    
    
    
}
