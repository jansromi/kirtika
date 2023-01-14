package fxKirtika;

import java.time.LocalDate;
import java.time.Month;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class KirtikaLoanViewController {
	//configure the table
	@FXML private TableView<LainattuKirja> tableView;
	@FXML private TableColumn<LainattuKirja, String> kirjanNimiColumn;
	@FXML private TableColumn<LainattuKirja, String> lainaajanNimiColumn;
	@FXML private TableColumn<LainattuKirja, LocalDate> lainaPvmColumn;
	@FXML private TableColumn<LainattuKirja, LocalDate> palautusPvmColumn;

	/**
	* Initializes the controller class
	*/
	public void initialize() {
		// set up columns
		kirjanNimiColumn.setCellValueFactory(new PropertyValueFactory<LainattuKirja, String>("kirjanNimi"));
		lainaajanNimiColumn.setCellValueFactory(new PropertyValueFactory<LainattuKirja, String>("lainaajanNimi"));
		lainaPvmColumn.setCellValueFactory(new PropertyValueFactory<LainattuKirja, LocalDate>("lainaPvm"));
		palautusPvmColumn.setCellValueFactory(new PropertyValueFactory<LainattuKirja, LocalDate>("palautusPvm"));
		
		// load dummy data
		tableView.setItems(getPeople());
	}
	
	/*
	 * returns an ObservableList of LainattuKirja objects
	 */
	public ObservableList<LainattuKirja> getPeople(){
		ObservableList<LainattuKirja> people = FXCollections.observableArrayList();
		people.add(new LainattuKirja("Odysseia", "Matti Meikäläinen", LocalDate.of(2023, Month.JANUARY, 13), null));
		people.add(new LainattuKirja("Surun ja ilon kaupunki", "Maija Meikäläinen", LocalDate.of(2022, Month.FEBRUARY, 2), LocalDate.of(2022, 03, 02)));
		people.add(new LainattuKirja("Odysseia", "Matti Meikäläinen", LocalDate.of(2022, Month.JANUARY, 1), LocalDate.of(2022, 12, 12)));
		return people;
	}
	
}