package fxKirtika;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import kirtika.Kirja;
import kirtika.Kirtika;
import kirtika.SailoException;

/**
 * 
 * @author Robert Mikael Jansson
 * @version 20.1.2023
 * Kirjanlisäyslomakkeen kontrolleri
 *
 */
public class KirtikaAddBookController implements Initializable{

    @FXML
    private Button addBookSave;

    @FXML
    private TextField fieldIsbn, fieldJulkaisija, fieldKieli, 
    fieldKirjailija, fieldKirjanNimi, fieldLuokitus;

    /**
     * Uuden kirjan lisäys
     * TODO: Errorhanderit
     * @param event
     */
    @FXML
    void tallennaKirja(ActionEvent event) {
    	uusiKirja();
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    
    // FXML-koodit yllä
	//=======================================================================================
    //
    
    private Kirtika kirtika;
    private ListChooser<Kirja> chooserKirjat;
    
    /**
     * Alustustetaan AddBook-lomake käyttämään kirtikaa ja 
     * 
     * @param kirtika
     * @param chooserKirjat Päänäkymän kirjalistaus
     */
    public void initData(Kirtika kirtika, ListChooser<Kirja> chooserKirjat) {
    	this.kirtika = kirtika;
    	this.chooserKirjat = chooserKirjat;
    }

	/**
     * Uuden kirjan lisäys rekisteriin
     */
    private void uusiKirja() {
		Kirja uusi = new Kirja();
		uusi.setKirjaId();
		uusi.setOdysseia();
		
		try {
			kirtika.lisaa(uusi);
			hae(uusi.getKirjaId());
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Ongelmia kirjan lisäämisessä " + e.getMessage());
		}
		
	}

    /**
     * Päivitetään PÄÄNÄKYMÄN kirjalistaus, kun kirja on lisätty onnistuneesti
     * @param kid kirja-id
     */
    private void hae(int kid) {
    	chooserKirjat.clear();
    	
    	int index = 0;
		for (int i = 0; i < kirtika.getKirjat(); i++) {
			Kirja kirja = kirtika.annaKirja(i);
			if (kirja.getKirjaId() == kid) index = i;
			chooserKirjat.add(kirja.getKirjanNimi(), kirja);
		}
		chooserKirjat.setSelectedIndex(index);
		
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
