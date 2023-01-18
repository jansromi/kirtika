package fxKirtika;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class KirtikaGUIController{

    @FXML
    private TextField fieldIsbn, fieldJulkaisija, fieldJulkaisuvuosi, 
    fieldKenelleLainattu, fieldKieli, fieldKirjailija, fieldLuokitus;

    @FXML
    private CheckBox checkMuokkaustila;
    
    @FXML
    private DatePicker fieldLainauspvm;
	
    @FXML
    private MenuBar myMenuBar;

    @FXML
    private TextField searchBar;

    @FXML
    private MenuItem showLoanHistory;

    /**
     * Kun muokkaustila täppä valitaan, tehdään
     * metatietokentistä muokattavia. Kun täppä
     * valitaan epäaktiiviseksi, niin kentät
     * palautetaan staattisiksi
     * @param event
     */
    @FXML
    void editMetaInfo(ActionEvent event) {
    	if (checkMuokkaustila.isSelected()) {
    	fieldKirjailija.setEditable(true);
    	fieldKieli.setEditable(true);
    	fieldJulkaisija.setEditable(true);
    	fieldJulkaisuvuosi.setEditable(true);
    	fieldLuokitus.setEditable(true);
    	fieldIsbn.setEditable(true);
    	}
    	else {
    		fieldKirjailija.setEditable(false);
        	fieldKieli.setEditable(false);
        	fieldJulkaisija.setEditable(false);
        	fieldJulkaisuvuosi.setEditable(false);
        	fieldLuokitus.setEditable(false);
        	fieldIsbn.setEditable(false);
    	}
    	
    	
    }
    
    /**
     * Kirjojen haku enterillä
     * @param event
     */
    @FXML
    void haeKirjoja(ActionEvent event) {
    	Dialogs.showMessageDialog("Ei osata vielä hakea");
    }
	
    
    /**
     * Tallennetaan lisätietokenttään tekstiä
     */
	@FXML private void handleTallennaLisatiedot() {
		Dialogs.showMessageDialog("Ei osata tallentaa");
	}
	
	/**
	 * Kirjan poisto
	 * @param event
	 */
    @FXML
    void deleteBook(ActionEvent event) {
    	if(Dialogs.showQuestionDialog("Poista kirja", "Haluatko varmasti poistaa kirjan?", "Kyllä", "Ei")) {
    		Dialogs.showMessageDialog("Ei osata poistaa tietoja");
    	}

    }
	
	
	@FXML
	public void initialize() {
		
	}
	
	/**
	 * Kun tätä metodia kutsutaan, vaihdetaan scene lainausnäkymään
	 * @throws IOException jos tiedostossa jotain häikkää
	 */
	@FXML public void handleShowLoanHistory(ActionEvent event) throws IOException {
		Parent loanViewParent = FXMLLoader.load(getClass().getResource("KirtikaLoanView.fxml"));
		Scene loanViewScene = new Scene(loanViewParent);
		
		Stage window = (Stage) myMenuBar.getScene().getWindow();
		window.setScene(loanViewScene);
		window.show();
	}
	
	
	/**
	 * Näyttää lomakkeen kirjojen lisäämistä varten
	 * @param event
	 */
	@FXML
	public void handleShowAddBookForm(ActionEvent event) {
        Parent root;
        try {
        	root = FXMLLoader.load(getClass().getResource("KirtikaAddBook.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lisää kirja");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	
}