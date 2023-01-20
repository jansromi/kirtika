package fxKirtika;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * 
 * @author Robert Mikael Jansson
 * @version 20.1.2023
 * Kirjanlisäyslomakkeen kontrolleri
 *
 */
public class KirtikaAddBookController {

    @FXML
    private Button addBookSave;

    @FXML
    private TextField fieldIsbn;

    @FXML
    private TextField fieldJulkaisija;

    @FXML
    private TextField fieldKieli;

    @FXML
    private TextField fieldKirjailija;

    @FXML
    private TextField fieldLuokitus;

    
    /**
     * Uuden kirjan lisäys
     * @param event
     */
    @FXML
    void tallennaKirja(ActionEvent event) {
    	Dialogs.showMessageDialog("Ei osata vielä tallentaa");
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
