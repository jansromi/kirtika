package fxKirtika;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kirtika.Kirja;
import kirtika.Kirtika;


/**
 * 
 * @author Robert Mikael Jansson
 * @version 20.1.2023
 * Kirtika-kirjatietokannan käyttöliittymän kontrolleri
 *
 */
public class KirtikaGUIController implements Initializable {

	
    @FXML
    private TextField fieldIsbn, fieldJulkaisija, fieldJulkaisuvuosi, 
    fieldKenelleLainattu, fieldKieli, fieldKirjailija, fieldLuokitus;
    private ArrayList<TextField> tietokentat = new ArrayList<>();
    
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
    
    @FXML
    private MenuItem handleTallenna;
    
    @FXML
    private MenuItem handleDeleteBook;
    
    @FXML
    private ListChooser<Kirja> chooserKirjat;
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
		
	}
	
	/**
	 * Menu-valikon tallenna
	 * @param event
	 */
    @FXML
    void tallenna(ActionEvent event) {
    	kirtika.tallenna();
    }
	
	/**
	 * Kirjalistauksen virkistys. 
	 * Public jotta voidaan kutsua muista kontrollereista.
	 */
	@FXML
	public void updateChooserKirjat() {
		chooserKirjat.clear();
	
		for (int i = 0; i < kirtika.getKirjat(); i++) {
			Kirja kirja = kirtika.annaKirja(i);
			chooserKirjat.add(kirja.getKirjanNimi(), kirja);
		}
	}
	
    /**
     * Kun kirjalistauksesta painetaan kirja.
     */
    @FXML
    void listChooserCliked() {
    	if (chooserKirjat.getSelectedIndex() == -1) return;
    	setBookText(chooserKirjat.getSelectedIndex());
    }
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
    		Kirja kirja = chooserKirjat.getSelectedObject();
    		kirtika.poista(kirja);
    		updateChooserKirjat();
    	}

    }
	
	/**
	 * Kun tätä metodia kutsutaan, vaihdetaan scene lainausnäkymään
	 * @throws IOException jos tiedostossa jotain häikkää
	 */
	@FXML public void handleShowLoanHistory(ActionEvent event) throws IOException {
		FXMLLoader ldr = new FXMLLoader();
		System.out.println(chooserKirjat.getRivit());
		ldr.setLocation(getClass().getResource("KirtikaLoanView.fxml"));
		Parent root = ldr.load();
		KirtikaLoanViewController ctrl = ldr.getController();
		ctrl.initialize(kirtika);
		Scene loanViewScene = new Scene(root);
		
		Stage window = (Stage) myMenuBar.getScene().getWindow();
		window.setScene(loanViewScene);
		window.show();
	}
	
	/**
	 * Näyttää lomakkeen kirjojen lisäämistä varten
	 * @param event
	 * @throws IOException 
	 */
	@FXML
	public void handleShowAddBookForm(ActionEvent event) throws IOException {
		FXMLLoader ldr = new FXMLLoader();
		ldr.setLocation(getClass().getResource("KirtikaAddBook.fxml"));
		Parent root = ldr.load();
		KirtikaAddBookController ctrl = ldr.getController();
		ctrl.initData(kirtika);
		
		Stage popUpStage = new Stage();
		popUpStage.setTitle("Lisää kirja");
		popUpStage.initModality(Modality.APPLICATION_MODAL);
		
		popUpStage.setScene(new Scene(root,400,400));
		popUpStage.showAndWait();
		
		updateChooserKirjat();
		
		
		
    }
	
	/**
	 * Näyttää versiotietolomakkeen
	 * @param event
	 */
	@FXML
	public void handleShowAboutView(ActionEvent event) {
		Parent root;
        try {
        	root = FXMLLoader.load(getClass().getResource("KirtikaAboutView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Info");
            stage.setScene(new Scene(root, 494, 345));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}

	//===================================================================================
	//
	
	private Kirtika kirtika;
	/**
	 * Asetetaan viite Kirtika-luokkaan
	 * @param kirtika
	 */
	public void setKirtika(Kirtika kirtika) {
		this.kirtika = kirtika;
	}

	private void init() {
		tietokentat.add(fieldKirjailija);
		tietokentat.add(fieldKieli);
		tietokentat.add(fieldJulkaisija);
		tietokentat.add(fieldJulkaisuvuosi);
		tietokentat.add(fieldLuokitus);
		tietokentat.add(fieldIsbn);
	}

	/**
	 * Asetetaan kirjan tiedot näkyviksi tietokenttiin
	 * @param selectedId valitun kirjan id
	 */
	private void setBookText(int selectedId) {
		String[] s = kirtika.annaKirjanTiedot(selectedId);
		
		for (int i = 0; i < s.length; i++) {
			TextField tk = tietokentat.get(i);
			tk.setText(s[i]);
		}
	}

	
	
}
