package fxKirtika;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kirtika.Kirtika;
import kirtika.Laina;
import kirtika.SailoException;

public class KirtikaLoanViewController {
	
	@FXML
	private MenuBar myMenuBar;
	
	// Taulukon sarakkeet
	@FXML private TableView<Laina> tableView;
	@FXML private TableColumn<Laina, String> kirjanNimiColumn;
	@FXML private TableColumn<Laina, String> lainaajanNimiColumn;
	@FXML private TableColumn<Laina, LocalDate> lainaPvmColumn;
	@FXML private TableColumn<Laina, LocalDate> palautusPvmColumn;
	
    

	@FXML public void handleShowMainView(ActionEvent event) throws IOException {
		FXMLLoader ldr = new FXMLLoader();
		ldr.setLocation(getClass().getResource("KirtikaMainView.fxml"));
		Parent root = ldr.load();
		KirtikaGUIController ctrl = ldr.getController();
		
		Scene mainViewScene = new Scene(root);
		Stage window = (Stage) myMenuBar.getScene().getWindow();
		window.setScene(mainViewScene);
		ctrl.setKirtika(kirtika);
		ctrl.updateChooserKirjat();
		window.show();
	}
	
    @FXML
    void handleDeleteLoan(ActionEvent event) {
    	deleteLoan();
    	tableView.setItems(getLainat());
    }
	
	//
	//===========================================================================================================
	//
	
	private Kirtika kirtika;
	
	/**
	* Alustetaan kontrolleri käyttämään kirtikaa.
	*/
	public void initialize(Kirtika kirtika) {
		this.kirtika = kirtika;
		initTable();
		tableView.setItems(getLainat());
	}
	
	public void deleteLoan() {
		Laina laina = tableView.getSelectionModel().getSelectedItem();
		if (laina == null) return;
		if (Dialogs.showQuestionDialog("Huomio!", "Haluatko varmasti poistaa valitun lainan?", "Kyllä", "Ei")) {
			kirtika.deleteLoan(laina.getLainaId());
			try {
				kirtika.setLainattu(laina.getLainattuKirjaId(), false);
				kirtika.saveBookLoans();
				kirtika.tallenna();
			} catch (SailoException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("VAROITUS");
				alert.setHeaderText("Lainatut kirjat");
				alert.setContentText("Lainan käsittely ei onnistunut, sillä tiedostoa ei löytynyt");
				alert.showAndWait();
			}
		}
		
	}
	
	/**
	 * Alustetaan taulukko käyttämään LainattuKirja-olioita.
	 */
	public void initTable() {
		kirjanNimiColumn.setCellValueFactory(new PropertyValueFactory<Laina, String>("kirjanNimi"));
		lainaajanNimiColumn.setCellValueFactory(new PropertyValueFactory<Laina, String>("lainaajanNimi"));
		lainaPvmColumn.setCellValueFactory(new PropertyValueFactory<Laina, LocalDate>("lainaPvm"));
		palautusPvmColumn.setCellValueFactory(new PropertyValueFactory<Laina, LocalDate>("palautusPvm"));
		
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	    lainaPvmColumn.setCellFactory(column -> new TableCell<Laina, LocalDate>() {
	        @Override
	        protected void updateItem(LocalDate item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || item == null) {
	                setText(null);
	            } else {
	                setText(formatter.format(item));
	            }
	        }
	    });
	    palautusPvmColumn.setCellFactory(column -> new TableCell<Laina, LocalDate>() {
	        @Override
	        protected void updateItem(LocalDate item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || item == null) {
	                setText(null);
	            } else {
	                setText(formatter.format(item));
	            }
	        }
	    });
	}
	
	/**
	 * @return Lainat joita löydetään kirtikasta
	 */
	public ObservableList<Laina> getLainat(){
		ObservableList<Laina> lainat = FXCollections.observableArrayList();
			for (int i = 0; i < kirtika.getLainatutLkm(); i++) {
				Laina lainattu = kirtika.annaLainattuKirja(i);
				lainat.add(lainattu);
			}
		return lainat;
	}
	
	
}