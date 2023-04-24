package fxKirtika;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kirtika.Genre;
import kirtika.Kirtika;
import utils.StringUtils;

public class KirtikaGenreViewController {

    @FXML
    private TableColumn<Genre, String> genreIdColumn;
    
    @FXML
    private TableColumn<Genre, String> genreDescColumn;


    @FXML
    private TableView<Genre> tableView;
    
    @FXML
    private MenuBar myMenuBar;

    

    @FXML
    void handleAddGenre() {
    	addGenre();
		tableView.setItems(getGenres());
    }

    @FXML
    void handleDeleteGenre(ActionEvent event) {
    	deleteGenre();
    	tableView.setItems(getGenres());
    }


	@FXML
    void handleShowMainView(ActionEvent event) throws IOException {
		FXMLLoader ldr = new FXMLLoader();
		ldr.setLocation(getClass().getResource("KirtikaMainView.fxml"));
		Parent root = ldr.load();
		KirtikaGUIController ctrl = ldr.getController();
		
		Scene mainViewScene = new Scene(root);
		Stage window = (Stage) myMenuBar.getScene().getWindow();
		window.setScene(mainViewScene);
		ctrl.setKirtika(kirtika);
		ctrl.updateChooserBooks();
		window.show();
    }
    
	//
	//===========================================================================================================
	//
	
	private Kirtika kirtika;
	
	/**
	* Initializes the controller to use Kirtika
	*/
	public void initialize(Kirtika kirtika) {
		this.kirtika = kirtika;
		initTable();
		tableView.setItems(getGenres());
	}
	
	/**
	 * Shows a warning dialog.
	 * @param title The title of the dialog
	 * @param headerText The header text of the dialog
	 * @param contentText The content text of the dialog
	 */
	public void showWarningDialog(String title, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
	
	/**
	 * Adds a user defined genre to Kirtika
	 */
	public void addGenre() {
		String msg = "Esim. 82.4 Suomalainen kaunokirjallisuus" + System.lineSeparator() +
					  "tai  82.4.2 Suomalainen kaunokirjallisuus, Waltarin kirjat";
		
		String input = "";
		
		// Input loop
		while (!StringUtils.matchesGenreFormat(input)) {
			if (input == null) break;
			input = showInputDialog("Lisää genre", "Lisää oma mukautettu genre muodossa \"luokitustunnus luokituskuvaus\"", msg);
			if (input != null && StringUtils.matchesGenreFormat(input)) {
				StringBuilder sb = new StringBuilder(input);
				String yklId = Mjonot.erota(sb, ' ');
				if (kirtika.genresContain(yklId)) {
					Dialogs.showMessageDialog("Tunnus " + yklId + " löytyy jo Kirtikasta!");
					continue;
				}
				kirtika.addGenre(input);
				
				try {
					kirtika.saveAll();
					kirtika.sortGenres();
				} catch (FileNotFoundException e) {
					showWarningDialog("VAROITUS", "Poisto epäonnistui, sillä tiedostoa ei löytynyt", e.getMessage());
				} catch (IOException e) {
					// from sortGenres
				}
				
				Dialogs.showMessageDialog("Genre lisätty onnistuneesti!");
			} else {
				Dialogs.showMessageDialog("Genreä ei voitu lisätä");
			}
		}
		

	}
	
	/**
	 * Deletes a genre from kirtika.
	 * Saves the state afterwards
	 * 
	 */
    private void deleteGenre() {
    	Genre genre = tableView.getSelectionModel().getSelectedItem();
		if (genre == null) return;
		if (Dialogs.showQuestionDialog("Huomio!", "Haluatko varmasti poistaa valitun genren?", "Kyllä", "Ei")) {
			kirtika.deleteGenre(genre);
			try {
				kirtika.deleteGenre(genre);
				kirtika.saveAll();
				Dialogs.showMessageDialog("Genre poistettu onnistuneesti");
			} catch (FileNotFoundException e) {
				showWarningDialog("VAROITUS", "Poisto epäonnistui, sillä tiedostoa ei löytynyt", e.getMessage());
			}
		}
	}
	
	/**
	 * 
	 * @param title
	 * @param headerText
	 * @param contentText
	 * @return
	 */
	private String showInputDialog(String title, String headerText, String contentText) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(contentText);
		Optional<String> answer = dialog.showAndWait();
		if (answer.isPresent()) return answer.get();
		else return null;
	}
	
	
	
	public void initTable() {
		genreIdColumn.setCellValueFactory(new PropertyValueFactory<Genre, String>("genreId"));
		genreDescColumn.setCellValueFactory(new PropertyValueFactory<Genre, String>("genreDesc"));
	}
    
    public ObservableList<Genre> getGenres() {
    	ObservableList<Genre> genres = FXCollections.observableArrayList();
    	ArrayList<Genre> genresList = kirtika.getGenres();
    	for (Genre genre : genresList) {
			genres.add(genre);
		}
    	return genres;
    }

}
