/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

/**
 *
 * @author pc
 */
import com.codename1.components.Switch;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.mycompany.entities.Evenement;
import com.mycompany.services.EvenementService;
import java.util.ArrayList;

public class EvenementListForm extends Form {

    private ArrayList<Evenement> evenements;
    private Container evenementContainer;
    
    public Container displayEvents(ArrayList<Evenement> evenements)
    {
        
         Container evenementContainer = new Container();
         for (Evenement evenement : evenements) {
            // Create a container to hold the evenement's information and buttons
            Container evenementRow = new Container(new GridLayout(6, 1));

            // Create labels to display the evenement's information
            Label idLabel = new Label("ID: " + evenement.getId());
            Label titreLabel = new Label("Titre: " + evenement.getTitre());
            Label descriptionLabel = new Label("Description: " + evenement.getDescription());
            Label dateDebutLabel = new Label("Date Debut: " + evenement.getDateDebut().toString());
            Label dateFinLabel = new Label("Date Fin: " + evenement.getDateFin().toString());
            Label prixLabel = new Label("Prix: " + evenement.getPrix());
            Label partLabel = new Label("nbrP: " + evenement.getNbrP());

            // Create buttons to edit and delete the evenement
            Button editBtn = new Button("Edit");
            editBtn.addActionListener(e -> {
                new EditEvenementForm(evenement).show();
            });

            Button deleteBtn = new Button("Delete");
            deleteBtn.addActionListener(e -> {
                // Delete the evenement from the server
                EvenementService.getInstance().deleteEvenement(evenement.getId());

                // Remove the evenement from the container
                // evenementContainer.removeComponent(evenementRow);
                new EvenementListForm(0).show();

            });

            // Add the labels to the evenement row
            evenementRow.add(idLabel);
            evenementRow.add(titreLabel);
            evenementRow.add(descriptionLabel);
            evenementRow.add(dateDebutLabel);
            evenementRow.add(dateFinLabel);
            evenementRow.add(prixLabel);
            evenementRow.add(partLabel);

            // Add the buttons to the evenement row
            evenementRow.add(editBtn);
            evenementRow.add(deleteBtn);

            // Add the evenement row to the container
            evenementContainer.add(evenementRow);
            
            
        }
         
         return evenementContainer ; 
    }

    public EvenementListForm(int switchState) {
        super("Evenement List");

        // Add a button to add a new evenement
        Button addBtn = new Button("Add Evenement");
        addBtn.addActionListener(e -> {
            new AddEvenementForm().show();
        });
        add(addBtn);
        Button Gocat = new Button("View Catgories");
        Gocat.addActionListener(e -> {
            new CatListForm().show();
        });
        add(Gocat);

       Switch filterSwitch = new Switch();
       
           // evenements = EvenementService.getInstance().getAllEvenements();
       if (switchState == 0)
       {
             evenements = EvenementService.getInstance().getAllEvenements();
            evenementContainer = displayEvents(evenements);
       }
       else 
       {
           evenements = EvenementService.getInstance().getAllEvenementsSorted();
            evenementContainer = displayEvents(evenements);
            filterSwitch.setOn();
       }
        
       
        filterSwitch.addActionListener(e -> {
            if (filterSwitch.isOn()) {
                System.out.println("set on");
                   
              new EvenementListForm(1).show();
                 
                  
            } else {
                System.out.println("set off");
             
                  new EvenementListForm(0).show();

            }
        });

        add(filterSwitch);
        // Get all the evenements from the server

        // Create a container to hold the evenements
      //  evenementContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        // evenementContainer.setScrollableY(true);
        // Add each evenement to the container with buttons to edit and delete it
       

        // Add the container to the form
        add(evenementContainer);

    }
}
