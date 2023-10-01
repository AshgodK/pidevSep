/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.spinner.Picker;
import com.mycompany.entities.Categorie;
import com.mycompany.entities.Evenement;
import com.mycompany.services.EvenementService;
import com.mycompany.services.ServiceCategorie;
import java.util.Date;

/**
 *
 * @author pc
 */
public class EditcatForm extends Form{
    
    
    
    private Categorie c;

    public EditcatForm(Categorie c) {
        super("Edit Evenement");
         Command backCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                // Handle the back action here, for example, show the previous form
                new EvenementListForm(0).showBack();
            }
        };
        this.getToolbar().addCommandToLeftBar(backCommand);

        this.c = c;

        // Create text fields and a combo box for the evenement information
        TextField idField = new TextField(Integer.toString(c.getId()), "ID");
        TextField titreField = new TextField(c.getNom(), "Titre");
        TextField descriptionField = new TextField(c.getDesc(), "Description");

        // Create a button to save the changes
        Button saveBtn = new Button("Edit");
        saveBtn.addActionListener(e -> {
            // Validate the entered values
            try {
             
                String titre = titreField.getText().trim();
                String description = descriptionField.getText().trim();
             

                // Check that the entered values are valid
                if ( titre.isEmpty() || description.isEmpty() ) {
                    Dialog.show("Error", "Please enter valid values for all fields", "OK", null);
                    return;
                }

                // Update the evenement with the entered information
    
                c.setNom(titre);
                c.setDesc(description);

        

                // Update the evenement on the server
                ServiceCategorie.getInstance().editc(c);

                // Close the form
                // this.close();
                new CatListForm().show();
            } catch (NumberFormatException ex) {
                // Handle the case where the entered values are not valid integers or floats
                Dialog.show("Error", "Please enter valid values for ID, Prix, Nombre de Passe, and Nombre de Participants", "OK", null);
            }
        });

        // Add the input fields and combo box to the form
        add(titreField);
        add(descriptionField);
        add(saveBtn);
    }
    
}
