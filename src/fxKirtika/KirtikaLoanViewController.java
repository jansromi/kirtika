package fxKirtika;

import java.io.IOException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kirtika.Kirtika;
import kirtika.LainattuKirja;

public class KirtikaLoanViewController {
	
	@FXML
	private MenuBar myMenuBar;
	
	// Taulukon sarakkeet
	@FXML private TableView<LainattuKirja> tableView;
	@FXML private TableColumn<LainattuKirja, String> kirjanNimiColumn;
	@FXML private TableColumn<LainattuKirja, String> lainaajanNimiColumn;
	@FXML private TableColumn<LainattuKirja, LocalDate> lainaPvmColumn;
	@FXML private TableColumn<LainattuKirja, LocalDate> palautusPvmColumn;


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
	
	/**
	 * Alustetaan taulukko käyttämään LainattuKirja-olioita.
	 */
	public void initTable() {
		kirjanNimiColumn.setCellValueFactory(new PropertyValueFactory<LainattuKirja, String>("kirjanNimi"));
		lainaajanNimiColumn.setCellValueFactory(new PropertyValueFactory<LainattuKirja, String>("lainaajanNimi"));
		lainaPvmColumn.setCellValueFactory(new PropertyValueFactory<LainattuKirja, LocalDate>("lainaPvm"));
		palautusPvmColumn.setCellValueFactory(new PropertyValueFactory<LainattuKirja, LocalDate>("palautusPvm"));
	}
	
	/**
	 * @return Lainat joita löydetään kirtikasta
	 */
	public ObservableList<LainattuKirja> getLainat(){
		ObservableList<LainattuKirja> lainat = FXCollections.observableArrayList();
			for (int i = 0; i < kirtika.getLainatutLkm(); i++) {
				LainattuKirja lainattu = kirtika.annaLainattuKirja(i);
				lainat.add(lainattu);
			}
		return lainat;
	}
	
	
}