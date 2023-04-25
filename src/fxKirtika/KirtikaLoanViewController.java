package fxKirtika;

import java.io.FileNotFoundException;
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
import kirtika.Loan;

/**
 * Controller for the loan view
 * @author Jansromi
 */
public class KirtikaLoanViewController {
	
	@FXML
	private MenuBar myMenuBar;
	
	// Columns of the table
	@FXML private TableView<Loan> tableView;
	@FXML private TableColumn<Loan, String> bookNameColumn;
	@FXML private TableColumn<Loan, String> lenderNameColumn;
	@FXML private TableColumn<Loan, LocalDate> loanStartDateColumn;
	@FXML private TableColumn<Loan, LocalDate> loanEndDateColumn;

	/**
	 * Return to main view
	 * @param event ActionEvent
	 * @throws IOException if loading fxml fails
	 */
	@FXML public void handleShowMainView(ActionEvent event) throws IOException {
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
	
	/**
	 * Delete loan-menuitem
	 */
    @FXML
    void handleDeleteLoan() {
    	deleteLoan();
    	tableView.setItems(getLainat());
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
		tableView.setItems(getLainat());
	}
	
	/**
	 * Method for deleting a loan. 
	 */
	public void deleteLoan() {
		Loan loan = tableView.getSelectionModel().getSelectedItem();
		if (loan == null) return;
		if (Dialogs.showQuestionDialog("Huomio!", "Haluatko varmasti poistaa valitun lainan?", "Kyllä", "Ei")) {
			kirtika.deleteLoan(loan.getLoanId());
			try {
				kirtika.setBookAsLoaned(loan.getLoanedBookId(), false);
				kirtika.saveAll();
			} catch (FileNotFoundException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("VAROITUS");
				alert.setHeaderText("Lainat");
				alert.setContentText("Lainan käsittely ei onnistunut, sillä tiedostoa ei löytynyt");
				alert.showAndWait();
			}
		}
		
	}
	
	/**
	 * Initializes the table to display Loan objects.
	 * Sets up custom cell factories for the loanStartDateColumn and loanEndDateColumn
	 * to format the dates as DD-MM-YYYY.
	 */
	public void initTable() {
		bookNameColumn.setCellValueFactory(new PropertyValueFactory<Loan, String>("bookName"));
		lenderNameColumn.setCellValueFactory(new PropertyValueFactory<Loan, String>("loanerName"));
		loanStartDateColumn.setCellValueFactory(new PropertyValueFactory<Loan, LocalDate>("loanStartDate"));
		loanEndDateColumn.setCellValueFactory(new PropertyValueFactory<Loan, LocalDate>("loanEndDate"));
		
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    loanStartDateColumn.setCellFactory(column -> new TableCell<Loan, LocalDate>() {
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
	    loanEndDateColumn.setCellFactory(column -> new TableCell<Loan, LocalDate>() {
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
	 * @return ObservableList<Loan> all found loans from kirtika.
	 */
	public ObservableList<Loan> getLainat(){
		ObservableList<Loan> lainat = FXCollections.observableArrayList();
		for (int i = 0; i < kirtika.getLoansAmt(); i++) {
			Loan lainattu = kirtika.getLoanedBook(i);
			lainat.add(lainattu);
		}
		return lainat;
	}
	
	
}