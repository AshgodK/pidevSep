/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.mycompany.entities.Evenement;

/**
 *
 * @author pc
 */
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.TextField;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.services.EvenementService;
import java.util.Date;

public class EditEvenementForm extends Form {

    private Picker ddebPicker;
    private Picker dfinPicker;
    private Evenement evenement;

    public EditEvenementForm(Evenement evenement) {
        super("Edit Evenement");
         Command backCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                // Handle the back action here, for example, show the previous form
                new EvenementListForm(0).showBack();
            }
        };
        this.getToolbar().addCommandToLeftBar(backCommand);

        this.evenement = evenement;

        // Create text fields and a combo box for the evenement information
        TextField idField = new TextField(Integer.toString(evenement.getId()), "ID");
        TextField titreField = new TextField(evenement.getTitre(), "Titre");
        TextField descriptionField = new TextField(evenement.getDescription(), "Description");
        ddebPicker = new Picker();
        ddebPicker.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        ddebPicker.setDate(evenement.getDateDebut());
        dfinPicker = new Picker();
        dfinPicker.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        dfinPicker.setDate(evenement.getDateFin());
        TextField prixField = new TextField(Float.toString(evenement.getPrix()), "Prix");


        TextField adresseField = new TextField(evenement.getAdresse(), "Adresse");
        TextField nbrPField = new TextField(Integer.toString(evenement.getNbrP()), "Nombre de Participants");

        // Create a button to save the changes
        Button saveBtn = new Button("Save");
        saveBtn.addActionListener(e -> {
            // Validate the entered values
            try {
                int id = Integer.parseInt(idField.getText());
                String titre = titreField.getText().trim();
                String description = descriptionField.getText().trim();
                Date ddeb = (Date) ddebPicker.getValue();
                Date dfin = (Date) dfinPicker.getValue();
                float prix = Float.parseFloat(prixField.getText());
                String adresse = adresseField.getText().trim();
                int nbrP = Integer.parseInt(nbrPField.getText());

                // Check that the entered values are valid
                if (id < 0 || titre.isEmpty() || description.isEmpty() || prix < 0 ||
                       adresse.isEmpty() || nbrP < 0) {
                    Dialog.show("Error", "Please enter valid values for all fields", "OK", null);
                    return;
                }

                // Update the evenement with the entered information
                evenement.setId(id);
                evenement.setTitre(titre);
                evenement.setDescription(description);

                // Set dateDebut and dateFin with parsed Date objects
                evenement.setDateDebut(ddeb);
                evenement.setDateFin(dfin);
                evenement.setPrix(prix);
                evenement.setAdresse(adresse);
                evenement.setNbrP(nbrP);

                // Update the evenement on the server
                EvenementService.getInstance().editEvenement(evenement);

                // Close the form
                // this.close();
                new EvenementListForm(0).show();
            } catch (NumberFormatException ex) {
                // Handle the case where the entered values are not valid integers or floats
                Dialog.show("Error", "Please enter valid values for ID, Prix, Nombre de Passe, and Nombre de Participants", "OK", null);
            }
        });

        // Add the input fields and combo box to the form
        add(idField);
        add(titreField);
        add(descriptionField);
        add(ddebPicker);
        add(dfinPicker);
        add(prixField);
        add(new Label("Zone"));
        add(adresseField);
        add(nbrPField);
        add(saveBtn);
    }
}
