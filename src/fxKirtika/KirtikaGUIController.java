package fxKirtika;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kirtika.Kirja;
import kirtika.Kirtika;
import kirtika.SailoException;


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
    fieldLainaaja, fieldKieli, fieldKirjailija, fieldLuokitus;
    private ArrayList<TextField> tietokentat = new ArrayList<>();
    
    @FXML
    private CheckBox checkMuokkaustila;
    @FXML
    private CheckBox checkLainassa;
    
    @FXML
    private DatePicker fieldLainauspvm, fieldPalautuspvm;
	
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
	 * Save function in the menu.
	 * @param event
	 */
    @FXML
    void tallenna(ActionEvent event) {
    	try {
			kirtika.tallenna();
		} catch (SailoException e) {
			
		}
    }
	
    /**
     * Sets the date, when loan was given
     */
    @FXML
    void handleSetLoanDate() {
    	Kirja kirja = chooserKirjat.getSelectedObject();
    	if (kirtika.getActiveLoan(kirja.getKirjaId()) != null) {
    		kirtika.setLoanDate(kirja.getKirjaId(), fieldLainauspvm.getValue());
        	Dialogs.showMessageDialog("Lainauspäiväksi asetettu " + fieldLainauspvm.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
    	}
    }
    


	/**
     * Sets the date of (desired) return
     * 
     * TODO: If return date is set to be earlier than loan date.
     */
    @FXML
    void handleSetReturnDate() {
    	Kirja kirja = chooserKirjat.getSelectedObject();
    	if (kirtika.getActiveLoan(kirja.getKirjaId()) != null) {
    		kirtika.setReturnDate(kirja.getKirjaId(), fieldPalautuspvm.getValue());
    		Dialogs.showMessageDialog("Palautuspäiväksi asetettu " + fieldPalautuspvm.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
    	}
    }
    
    


	/**
	 * Refreshes the book listing. 
	 * Public so that it can be called from other controllers.
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
	 * Sets the loan status value for the book selected.
	 * @param event
	 */
    @FXML
    void handleSetLoan(ActionEvent event) {
    	setLoan();
    }
	


	/**
     * When a book is clicked from the book listing.
     */
    @FXML
    void listChooserCliked() {
    	if (chooserKirjat.getSelectedIndex() == -1) return;
    	setBookText(chooserKirjat.getSelectedObject());
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
	
	
	/**
	 * Selects the first book to be active
	 */
	public void selectFirst() {
		if (chooserKirjat.getItems() == null) return;
		chooserKirjat.setSelectedIndex(0);
		Kirja kirja = chooserKirjat.getSelectedObject();
		setBookText(kirja);
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
	 * 
	 * loan[0] = boolean if book is loaned or not
	 * loan[1] = name of loaner
	 */
	private void setBookText(Kirja kirja) {
		int bId = kirja.getKirjaId();
		String[] s = kirtika.annaKirjanTiedot(bId);
		
		for (int i = 0; i < s.length; i++) {
			TextField tk = tietokentat.get(i);
			tk.setText(s[i]);
		}
		Object[] loan = kirtika.getBookLoanInfo(bId);
		checkLainassa.setSelected((boolean)loan[0]);
		fieldLainaaja.setText((String)loan[1]);
		
		
		// Otherwise setting a value triggers the onAction-event 
		// handleSetDate/handleSetReturnDate, which results in excess dialogs 
		// every time book is changed.
		EventHandler<ActionEvent> eventHandler = fieldLainauspvm.getOnAction();
		fieldLainauspvm.setOnAction(null);
		fieldLainauspvm.setValue((LocalDate)loan[2]);
		fieldLainauspvm.setOnAction(eventHandler);
		
		eventHandler = fieldPalautuspvm.getOnAction();
		fieldPalautuspvm.setOnAction(null);
		fieldPalautuspvm.setValue((LocalDate)loan[3]);
		fieldPalautuspvm.setOnAction(eventHandler);
	}
	
	/**
	 * Sets the loan status value for the book selected.
	 * 
	 * If checkbox is activated, it initializes a new loan.
	 * If checkbox is deactivated, it closes a previous loan with current date.
	 * 
	 */
	private void setLoan(){
		Kirja kirja = chooserKirjat.getSelectedObject();
		boolean b = checkLainassa.isSelected();
		
		if (b && fieldLainaaja.getText() != null && !fieldLainaaja.getText().isEmpty()) {
			addNewLoan(kirja);
		}
		if (!b) closeLoan(kirja);
	}

	/**
	 * When checkbox is activated, a new loan is added.
	 * 
	 * @param kirja Book to set as loaned
	 */
	private void addNewLoan(Kirja kirja) {
		kirtika.addBookLoan(kirja, fieldLainaaja.getText());
		kirja.setLainassa(true);
		try {
			kirtika.tallenna();
		} catch (SailoException sE) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("VAROITUS");
			alert.setHeaderText("Kirjaluettelo");
			alert.setContentText("Kirjojen tallennus ei onnistunut, sillä tiedostoa ei löytynyt");
			alert.showAndWait();
		}
		try {
			kirtika.saveBookLoans();
		} catch (SailoException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("VAROITUS");
			alert.setHeaderText("Lainatut kirjat");
			alert.setContentText("Lainan tallentaminen tiedostoon ei onnistunut");
			alert.showAndWait();
		}
		Dialogs.showMessageDialog("Lainan lisääminen onnistui!");
	}
	
	/**
	 * When checkbox is deactivated, loan is set as closed.
	 * 
	 * If return date is not given, it will be set as current date.
	 * @param kirja
	 */
	private void closeLoan(Kirja kirja) {
		LocalDate returnDate = fieldPalautuspvm.getValue();
		if (returnDate == null) returnDate = LocalDate.now();
		try {
			kirtika.closeLoan(kirja.getKirjaId(), returnDate);
			Dialogs.showMessageDialog(kirja.getKirjanNimi() + " palautettiin onnistuneesti" + System.lineSeparator() + "päivämäärällä " + returnDate.toString());
		} catch (SailoException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("VAROITUS");
			alert.setHeaderText("Lainatut kirjat");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		
		try {
			kirtika.saveAll();
		} catch (SailoException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("VAROITUS");
			alert.setHeaderText("Lainatut kirjat");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		
	}


	
	
}
