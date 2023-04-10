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
import kirtika.Book;
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
    private TextField fieldIsbn, fieldPublisher, fieldRelaseYear, 
    fieldLoaner, fieldLanguage, fieldWriters, fieldClassification;
    private ArrayList<TextField> infoFields = new ArrayList<>();
    
    @FXML
    private CheckBox checkEditMode;
    @FXML
    private CheckBox checkLoaned;
    
    @FXML
    private DatePicker fieldLoanStartDate, fieldLoanReturnDate;
	
    @FXML
    private MenuBar myMenuBar;

    @FXML
    private TextField searchBar;

    @FXML
    private MenuItem showLoanHistory;
    
    @FXML
    private MenuItem handleSave;
    
    @FXML
    private MenuItem handleDeleteBook;
    
    @FXML
    private ListChooser<Book> chooserBooks;
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
	}
	
	/**
	 * Save function in the menu.
	 * @param event
	 */
    @FXML
    void save(ActionEvent event) {
    	try {
			kirtika.save();
		} catch (SailoException e) {
			
		}
    }
	
    /**
     * Sets the date, when loan was given
     */
    @FXML
    void handleSetLoanDate() {
    	Book book = chooserBooks.getSelectedObject();
    	if (kirtika.getActiveLoan(book.getBookId()) != null) {
    		kirtika.setLoanDate(book.getBookId(), fieldLoanStartDate.getValue());
        	Dialogs.showMessageDialog("Lainauspäiväksi asetettu " + fieldLoanStartDate.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
    	}
    }
    


	/**
     * Sets the date of (desired) return
     * 
     * TODO: If return date is set to be earlier than loan date.
     */
    @FXML
    void handleSetReturnDate() {
    	Book book = chooserBooks.getSelectedObject();
    	if (kirtika.getActiveLoan(book.getBookId()) != null) {
    		kirtika.setReturnDate(book.getBookId(), fieldLoanReturnDate.getValue());
    		Dialogs.showMessageDialog("Palautuspäiväksi asetettu " + fieldLoanReturnDate.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
    	}
    }
    
    


	/**
	 * Refreshes the book listing. 
	 * Public so that it can be called from other controllers.
	 */
	@FXML
	public void updateChooserBooks() {
		chooserBooks.clear();
		for (int i = 0; i < kirtika.getBooks(); i++) {
			Book book = kirtika.getBook(i);
			chooserBooks.add(book.getBookName(), book);
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
    	if (chooserBooks.getSelectedIndex() == -1) return;
    	setBookText(chooserBooks.getSelectedObject());
    }
    
    /**
     * When the edit mode checkbox is selected, make the metadata fields editable.
     * When the checkbox is deselected, make the fields static again.
     *
     */
    @FXML
    void editMetaInfo() {
        if (checkEditMode.isSelected()) {
            fieldWriters.setEditable(true);
            fieldLanguage.setEditable(true);
            fieldPublisher.setEditable(true);
            fieldRelaseYear.setEditable(true);
            fieldClassification.setEditable(true);
            fieldIsbn.setEditable(true);
        } else {
            fieldWriters.setEditable(false);
            fieldLanguage.setEditable(false);
            fieldPublisher.setEditable(false);
            fieldRelaseYear.setEditable(false);
            fieldClassification.setEditable(false);
            fieldIsbn.setEditable(false);
        }
    }
    
    /**
     * Search books 
     * @param event
     */
    @FXML
    void searchBooks(ActionEvent event) {
    	Dialogs.showMessageDialog("Ei osata vielä hakea");
    }
	

    /**
     * Tallennetaan lisätietokenttään tekstiä
     */
	@FXML private void handleTallennaLisatiedot() {
		Dialogs.showMessageDialog("Ei osata tallentaa");
	}
	
	/**
	 * Deletion of a book
	 * @param event
	 */
    @FXML
    void deleteBook(ActionEvent event) {
    	if(Dialogs.showQuestionDialog("Poista kirja", "Haluatko varmasti poistaa kirjan?", "Kyllä", "Ei")) {
    		Book book = chooserBooks.getSelectedObject();
    		kirtika.deleteBook(book);
    		updateChooserBooks();
    	}

    }
	
    /**
     * When this method is called, the scene is changed to the loan view.
     * @throws IOException if there is an error with the file.
     */
    @FXML public void handleShowLoanHistory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        System.out.println(chooserBooks.getRivit());
        loader.setLocation(getClass().getResource("KirtikaLoanView.fxml"));
        Parent root = loader.load();
        KirtikaLoanViewController ctrl = loader.getController();
        ctrl.initialize(kirtika);
        Scene loanViewScene = new Scene(root);
            
        Stage window = (Stage) myMenuBar.getScene().getWindow();
        window.setScene(loanViewScene);
        window.show();
    }
	
    /**
     * Shows the form for adding books
     * @throws IOException
     */
    @FXML
    public void handleShowAddBookForm() throws IOException {
        FXMLLoader ldr = new FXMLLoader();
        ldr.setLocation(getClass().getResource("KirtikaAddBook.fxml"));
        Parent root = ldr.load();
        KirtikaAddBookController ctrl = ldr.getController();
        ctrl.initKirtika(kirtika);
        
        Stage popUpStage = new Stage();
        popUpStage.setTitle("Lisää kirja");
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        
        popUpStage.setScene(new Scene(root, 400, 400));
        popUpStage.showAndWait();
        
        updateChooserBooks();
    }
	
	/**
	 * Shows the About-view
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
	 * Sets the reference to Kirtika-class
	 * @param kirtika
	 */
	public void setKirtika(Kirtika kirtika) {
		this.kirtika = kirtika;
	}
	
	
	/**
	 * Selects the first book to be active
	 */
	public void selectFirst() {
		if (chooserBooks.getItems() == null) return;
		chooserBooks.setSelectedIndex(0);
		Book book = chooserBooks.getSelectedObject();
		setBookText(book);
	}
	
	private void init() {
		infoFields.add(fieldWriters);
		infoFields.add(fieldLanguage);
		infoFields.add(fieldPublisher);
		infoFields.add(fieldRelaseYear);
		infoFields.add(fieldClassification);
		infoFields.add(fieldIsbn);
	}

	/**
	 * Displays the books informations on main view
	 * @param book The displayed book
	 * 
	 * loan[0] = boolean if book is loaned or not
	 * loan[1] = name of loaner
	 */
	private void setBookText(Book book) {
		int bId = book.getBookId();
		String[] s = kirtika.getBookInfo(bId);
		
		for (int i = 0; i < s.length; i++) {
			TextField tk = infoFields.get(i);
			tk.setText(s[i]);
		}
		Object[] loan = kirtika.getBookLoanInfo(bId);
		checkLoaned.setSelected((boolean)loan[0]);
		fieldLoaner.setText((String)loan[1]);
		
		
		// Otherwise setting a value triggers the onAction-event 
		// handleSetDate/handleSetReturnDate, which results in excess dialogs 
		// every time book is changed.
		EventHandler<ActionEvent> eventHandler = fieldLoanStartDate.getOnAction();
		fieldLoanStartDate.setOnAction(null);
		fieldLoanStartDate.setValue((LocalDate)loan[2]);
		fieldLoanStartDate.setOnAction(eventHandler);
		
		eventHandler = fieldLoanReturnDate.getOnAction();
		fieldLoanReturnDate.setOnAction(null);
		fieldLoanReturnDate.setValue((LocalDate)loan[3]);
		fieldLoanReturnDate.setOnAction(eventHandler);
	}
	
	/**
	 * Sets the loan status value for the book selected.
	 * 
	 * If checkbox is activated, it initializes a new loan.
	 * If checkbox is deactivated, it closes a previous loan with current date.
	 * 
	 */
	private void setLoan(){
		Book book = chooserBooks.getSelectedObject();
		boolean b = checkLoaned.isSelected();
		
		if (b && fieldLoaner.getText() != null && !fieldLoaner.getText().isEmpty()) {
			addNewLoan(book);
		}
		if (!b) closeLoan(book);
		
		
	}

	/**
	 * When checkbox is activated, a new loan is added.
	 * 
	 * @param book Book to set as loaned
	 */
	private void addNewLoan(Book book) {
		kirtika.addBookLoan(book, fieldLoaner.getText());
		book.setBookLoaned(true);
		try {
			kirtika.save();
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
			alert.setHeaderText("Loans kirjat");
			alert.setContentText("Lainan tallentaminen tiedostoon ei onnistunut");
			alert.showAndWait();
		}
		Dialogs.showMessageDialog("Lainan lisääminen onnistui!");
	}
	
	/**
	 * When checkbox is deactivated, loan is set as closed.
	 * 
	 * If return date is not given, it will be set as current date.
	 * @param book
	 */
	private void closeLoan(Book book) {
		LocalDate returnDate = fieldLoanReturnDate.getValue();
		if (returnDate == null) returnDate = LocalDate.now();
		try {
			kirtika.closeLoan(book.getBookId(), returnDate);
			Dialogs.showMessageDialog(book.getBookName() + " palautettiin onnistuneesti" + System.lineSeparator() + "päivämäärällä " + returnDate.toString());
		} catch (SailoException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("VAROITUS");
			alert.setHeaderText("Loans kirjat");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		
		try {
			kirtika.saveAll();
		} catch (SailoException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("VAROITUS");
			alert.setHeaderText("Loans kirjat");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		
	}


	
	
}
