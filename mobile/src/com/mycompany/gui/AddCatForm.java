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
public class AddCatForm extends Form{
    
    
       public AddCatForm() {
      super("Add Category");
        Command backCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                // Handle the back action here, for example, show the previous form
                new CatListForm().showBack();
            }
        };
        this.getToolbar().addCommandToLeftBar(backCommand);
        
        // Create text fields and a combo box for the evenement information
      
        TextField titreField = new TextField("", "Titre");
        TextField descriptionField = new TextField("", "Description");
       
        // Create a button to add the evenement
        Button addBtn = new Button("Add");
        addBtn.addActionListener(e -> {
            // Validate the entered values
       
               
                String titre = titreField.getText().trim();
                String description = descriptionField.getText().trim();
               

                // Check that the entered values are valid
                if (titre.isEmpty() || description.isEmpty()) {
                    Dialog.show("Error", "Please enter valid values for all fields", "OK", null);
                    return;
                }

                // Create a new evenement with the entered information
                Categorie c = new Categorie();
             
                c.setNom(titre);
                c.setDesc(description);
                

                // Set dateDebut and dateFin with parsed Date objects
               
                System.out.println(c);

                // Add the evenement to the server
                ServiceCategorie.getInstance().addCat(c);
                new CatListForm().show();
        });

        // Add the input fields and combo box to the form
     
        add(titreField);
        add(descriptionField);
        add(addBtn);
    }
    
}
