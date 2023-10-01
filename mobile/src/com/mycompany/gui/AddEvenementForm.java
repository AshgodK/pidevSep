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
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
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
import java.util.ArrayList;
import java.util.Date;

public class AddEvenementForm extends Form {
    private Picker ddebPicker;
    private Picker dfinPicker;

    public AddEvenementForm() {
        super("Add Evenement");
        Command backCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                // Handle the back action here, for example, show the previous form
                new EvenementListForm(0).showBack();
            }
        };
        this.getToolbar().addCommandToLeftBar(backCommand);
        
        // Create text fields and a combo box for the evenement information
        TextField titreField = new TextField("", "Titre");
        TextField descriptionField = new TextField("", "Description");
        ddebPicker = new Picker();
        ddebPicker.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        dfinPicker = new Picker();
        dfinPicker.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        TextField prixField = new TextField("", "Prix");
        
        TextField eventimgField = new TextField("", "Event Image");
        TextField adresseField = new TextField("", "Adresse");
        TextField nbrPField = new TextField("", "Nombre de Participants");
        
         ComboBox<Categorie> categoryComboBox = new ComboBox<>();
          ArrayList<Categorie> categories = ServiceCategorie.getInstance().getAllCats();
        for (Categorie category : categories) {
            categoryComboBox.addItem(category);
        }

        // Create a button to add the evenement
        Button addBtn = new Button("Add");
        addBtn.addActionListener(e -> {
            // Validate the entered values
            try {
                String titre = titreField.getText().trim();
                String description = descriptionField.getText().trim();
                Date ddeb = (Date) ddebPicker.getValue();
                Date dfin = (Date) dfinPicker.getValue();
                float prix = Float.parseFloat(prixField.getText());
                String eventimg = eventimgField.getText().trim();
                String adresse = adresseField.getText().trim();
                int nbrP = Integer.parseInt(nbrPField.getText());

                // Check that the entered values are valid
                if ( titre.isEmpty() || description.isEmpty() || prix < 0 ||
                     eventimg.isEmpty() ||  adresse.isEmpty() || nbrP < 0) {
                    Dialog.show("Error", "Please enter valid values for all fields", "OK", null);
                    return;
                }
                
                 Categorie selectedCategory = categoryComboBox.getSelectedItem();
                int categoryId = selectedCategory.getId();

                // Create a new evenement with the entered information
                Evenement evenement = new Evenement();
                evenement.setTitre(titre);
                evenement.setDescription(description);
                

                // Set dateDebut and dateFin with parsed Date objects
                 evenement.setDateDebut(ddeb);
                 evenement.setDateFin(dfin);
                evenement.setPrix(prix);
                evenement.setEventimg(eventimg);
                evenement.setAdresse(adresse);
                evenement.setNbrP(nbrP);
                evenement.setCateg(categoryId);
                System.out.println(evenement);

                // Add the evenement to the server
                EvenementService.getInstance().addEvenement(evenement);
                new EvenementListForm(0).show();

                // Close the form
                // this.close();
            } catch (NumberFormatException ex) {
                // Handle the case where the entered values are not valid integers or floats
                Dialog.show("Error", "Please enter valid values for Prix, Nombre de Passe, and Nombre de Participants", "OK", null);
            }
        });

        // Add the input fields and combo box to the form
 
        add(titreField);
        add(descriptionField);
        add(ddebPicker);
        add(dfinPicker);
        add(prixField);
        add(eventimgField);
        add(adresseField);
        add(nbrPField);
        add(categoryComboBox);
        add(addBtn);
    }
}
