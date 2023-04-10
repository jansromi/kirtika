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
import javafx.scene.control.TextArea;
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
	private TextArea areaBookNotes;
    @FXML
    private TextField fieldIsbn, fieldPublisher, fieldReleaseYear, 
    fieldLoaner, fieldLanguage, fieldWriters, fieldClassificationDesc, fieldClassificationId;
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
     * Saves the book notes
     * @param event
     */
    @FXML
    void handleSaveBookNotes(ActionEvent event) {
    	saveBookNotes();
    }

    @FXML
    void handleSetBookIsbn() {
    	Book book = chooserBooks.getSelectedObject();
    	if (book.set(5, fieldIsbn.getText())) {
    		Dialogs.showMessageDialog("Muokkaus onnistui!");
    	}
    	displayBookInfo(book);
    }
    
    @FXML
    void handleSetBookLanguage() {
        Book book = chooserBooks.getSelectedObject();
        if (book.set(1, fieldLanguage.getText())) {
            Dialogs.showMessageDialog("Muokkaus onnistui!");
        }
        displayBookInfo(book);
    }

    @FXML
    void handleSetBookPublisher() {
        Book book = chooserBooks.getSelectedObject();
        if (book.set(2, fieldPublisher.getText())) {
            Dialogs.showMessageDialog("Muokkaus onnistui!");
        }
        displayBookInfo(book);
    }

    @FXML
    void handleSetBookReleaseYear() {
        Book book = chooserBooks.getSelectedObject();
        if (book.set(3, fieldReleaseYear.getText())) {
            Dialogs.showMessageDialog("Muokkaus onnistui!");
        }
        displayBookInfo(book);
    }

    @FXML
    void handleSetBookWriter() {
        Book book = chooserBooks.getSelectedObject();
        if (book.set(0, fieldWriters.getText())) {
            Dialogs.showMessageDialog("Muokkaus onnistui!");
        }
        displayBookInfo(book);
    }



    @FXML
    void handleSetBookYklDesc() {

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
    	displayBookInfo(chooserBooks.getSelectedObject());
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
            fieldReleaseYear.setEditable(true);
            fieldClassificationDesc.setEditable(true);
            fieldIsbn.setEditable(true);
        } else {
            fieldWriters.setEditable(false);
            fieldLanguage.setEditable(false);
            fieldPublisher.setEditable(false);
            fieldReleaseYear.setEditable(false);
            fieldClassificationDesc.setEditable(false);
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
    	showAddBookForm(false);
    }
    
    @FXML
    void handleShowAddBookFormISBN(ActionEvent event) throws IOException {
    	showAddBookForm(true);
    }
    
    private void showAddBookForm(boolean isISBNSelected) throws IOException {
        FXMLLoader ldr = new FXMLLoader();
        ldr.setLocation(getClass().getResource("KirtikaAddBook.fxml"));
        Parent root = ldr.load();
        KirtikaAddBookController ctrl = ldr.getController();
        
        ctrl.initKirtika(kirtika);
        
        if (isISBNSelected) {
        	String s = Dialogs.showInputDialog("Anna kirjan ISBN-numero", null);
        	ArrayList<String> finnaData = kirtika.fetchFinnaData(s);
        	ctrl.initFinnaData(finnaData);
        }
        
        Stage popUpStage = new Stage();
        popUpStage.setTitle("Lisää kirja");
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        
        popUpStage.setScene(new Scene(root, 400, 420));
        popUpStage.showAndWait();
        
        updateChooserBooks();
    }
	
	/**
	 * Shows the About-view
	 */
	@FXML
	public void handleShowAboutView() {
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
	
	/**
	 * Shows a warning dialog.
	 * @param title The title of the dialog
	 * @param headerText The header text of the dialog
	 * @param contentText The content text of the dialog
	 */
	private void showWarningDialog(String title, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
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
	
    private void saveBookNotes() {
		try {
			kirtika.saveBookNotes(chooserBooks.getSelectedObject(), areaBookNotes.getText());
			Dialogs.showMessageDialog("Tallennus onnistui!");
		} catch (IOException e) {
			showWarningDialog("VAROITUS", "Kirjan lisätietojen tallentaminen ei onnistunut", e.getMessage());
		}
	}
	
	/**
	 * Selects the first book to be active
	 */
	public void selectFirst() {
		if (chooserBooks.getItems() == null) return;
		chooserBooks.setSelectedIndex(0);
		Book book = chooserBooks.getSelectedObject();
		displayBookInfo(book);
	}
	
	private void init() {
		infoFields.add(fieldWriters);
		infoFields.add(fieldLanguage);
		infoFields.add(fieldPublisher);
		infoFields.add(fieldReleaseYear);
		infoFields.add(fieldClassificationDesc);
		infoFields.add(fieldClassificationId);
		infoFields.add(fieldIsbn);
	}

	/**
	 * Displays the books informations on main view
	 * @param book The displayed book
	 * 
	 * loan[0] = boolean if book is loaned or not
	 * loan[1] = name of loaner
	 */
	private void displayBookInfo(Book book) {
	    displayMainBookInfo(book);
	    displayBookLoanInfo(book);
	    displayBookNotes(book);
	}
	
	/**
	 * Displays the main info textfields.
	 * @param book Which books loan info to show
	 */
	private void displayMainBookInfo(Book book) {
	    int bId = book.getBookId();
	    String[] s = kirtika.getBookInfo(bId);
	    for (int i = 0; i < s.length; i++) {
	        TextField tf = infoFields.get(i);
	        tf.setText(s[i]);
	    }
	}
	
	/**
	 * Displays the books loan info
	 * @param book Which books loan info to show
	 * 
	 * obj[0] = value for checkbox
	 * obj[1] = lenders name
	 * obj[2] = date of loan
	 * obj[4] = return date
	 */
	private void displayBookLoanInfo(Book book) {
		int bId = book.getBookId();
	    Object[] loan = kirtika.getBookLoanInfo(bId);
	    checkLoaned.setSelected((boolean) loan[0]);
	    fieldLoaner.setText((String) loan[1]);

	    updateFieldWithoutActionEvent(fieldLoanStartDate, (LocalDate) loan[2]);
	    updateFieldWithoutActionEvent(fieldLoanReturnDate, (LocalDate) loan[3]);
	}
	
	private void displayBookNotes(Book book) {
		areaBookNotes.setText(kirtika.getBookNotes(book));
	}
	
	/**
	 * Setting a value for DatePicker-fields
	 * triggers the onAction-event without using this method
	 * @param field DatePicker where date is shown
	 * @param value LocalDate to be shown
	 */
	private void updateFieldWithoutActionEvent(DatePicker field, LocalDate value) {
		EventHandler<ActionEvent> eventHandler = field.getOnAction();
	    field.setOnAction(null);
	    field.setValue(value);
	    field.setOnAction(eventHandler);
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
		
		// Checkbox is selected, and field for Loaner has text.
		if (b && fieldLoaner.getText() != null && !fieldLoaner.getText().isEmpty()) {
			addNewLoan(book);
		}
		// Unchecking closes the loan
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
		
		// Save data to files
		try {
			kirtika.saveAll();
			Dialogs.showMessageDialog("Lainan lisääminen onnistui!");
		} catch (SailoException e) {
			showWarningDialog("VAROITUS", "Tiedostojen tallentaminen ei onnistunut", e.getMessage());
		}
	}

	/**
	 * When checkbox is deactivated, loan is set as closed.
	 * 
	 * If return date is not given, it will be set as current date.
	 * @param book
	 */
	private void closeLoan(Book book) {
		LocalDate returnDate = fieldLoanReturnDate.getValue();
		if (returnDate == null) {
			returnDate = LocalDate.now();
		}
		try {
			kirtika.closeLoan(book.getBookId(), returnDate);
			Dialogs.showMessageDialog(book.getBookName() + " palautettiin onnistuneesti" + System.lineSeparator() + "päivämäärällä " + returnDate.toString());
			kirtika.saveAll();
		} catch (SailoException e) {
			showWarningDialog("VAROITUS", "Kirjan palauttaminen ei onnistunut", e.getMessage());
		}
	}
}
