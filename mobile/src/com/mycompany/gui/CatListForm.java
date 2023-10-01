/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.GridLayout;
import com.mycompany.entities.Categorie;
import com.mycompany.entities.Evenement;
import com.mycompany.services.EvenementService;
import com.mycompany.services.ServiceCategorie;
import java.util.ArrayList;

/**
 *
 * @author pc
 */
public class CatListForm extends Form{
    
    
    private ArrayList<Categorie> cats;

    public CatListForm() {
        super("Categories List");
          Command backCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                // Handle the back action here, for example, show the previous form
                new EvenementListForm(0).showBack();
            }
        };
        this.getToolbar().addCommandToLeftBar(backCommand);

          // Add a button to add a new evenement
        Button addBtn = new Button("Add Category");
        addBtn.addActionListener(e -> {
            new AddCatForm().show();
        });
       add(addBtn);
        
        // Get all the evenements from the server
        cats = ServiceCategorie.getInstance().getAllCats();

        // Create a container to hold the evenements
        Container evenementContainer = new Container();

        // Add each evenement to the container with buttons to edit and delete it
        for (Categorie c : cats) {
            // Create a container to hold the evenement's information and buttons
            Container evenementRow = new Container(new GridLayout(3, 1));

            // Create labels to display the evenement's information
            Label idLabel = new Label("ID: " + c.getId());
            Label titreLabel = new Label("Nom: " + c.getNom());
            Label descriptionLabel = new Label("Description: " + c.getDesc());

            // Create buttons to edit and delete the evenement
            Button editBtn = new Button("Edit");
            editBtn.addActionListener(e -> {
                new EditcatForm(c).show();
            });

           Button deleteBtn = new Button("Delete");
            deleteBtn.addActionListener(e -> {
                // Delete the evenement from the server
                ServiceCategorie.getInstance().deletec(c.getId());

                // Remove the evenement from the container
               // evenementContainer.removeComponent(evenementRow);
                new CatListForm().show();
                
            });

            // Add the labels to the evenement row
          
            evenementRow.add(titreLabel);
            evenementRow.add(descriptionLabel);

            // Add the buttons to the evenement row
              evenementRow.add(editBtn);
              evenementRow.add(deleteBtn);

            // Add the evenement row to the container
            evenementContainer.add(evenementRow);
        }

        // Add the container to the form
        add(evenementContainer);
       

      
    }
    
}
