package fxKirtika;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
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
import webscraping.FinnaHaku.BookNotFoundException;


/**
 * 
 * @author Jansromi
 * @version 20.1.2023
 * Kirtika-kirjatietokannan käyttöliittymän kontrolleri
 *
 */
public class KirtikaGUIController implements Initializable {

	
	
	@FXML
	private TextArea areaBookNotes;
    @FXML
    private TextField fieldIsbn, fieldPublisher, fieldReleaseYear, 
    fieldLoaner, fieldLanguage, fieldWriters, fieldClassificationDesc, fieldClassificationId, searchBar;
    private ArrayList<TextField> infoFields = new ArrayList<>();
    @FXML
    private CheckBox checkEditMode, checkLoaned;
    @FXML
    private DatePicker fieldLoanStartDate, fieldLoanReturnDate;
    @FXML
    private MenuBar myMenuBar;
    @FXML
    private MenuItem showLoanHistory, handleSave, handleDeleteBook;
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
		} catch (FileNotFoundException e) {
			showDialog(AlertType.WARNING, "Varoitus", "Tallennus epäonnistui", e.getMessage());
			return;
		}
    	Dialogs.showMessageDialog("Tallennus onnistui!");
    }
    
    /**
     * Fetches all YKL-classifications from the internet.
     * Saves the fetched classifications to the file. Finally
     * it sorts all the genres in ascending order based on ykl-id.
     */
    @FXML
    void handleFetchYklGenres() {
    	try {
			kirtika.fetchFintoGenres();
		} catch (IOException ioe) {
			showDialog(AlertType.WARNING,"Varoitus", "IO-toiminnan häiriö", ioe.getMessage());
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			showDialog(AlertType.WARNING,"Varoitus", "Pyyntö keskeytyi", ie.getMessage());
		}
    	Dialogs.showMessageDialog("Genret haettu onnistuneesti!");
    	try {
			kirtika.saveGenres();
		} catch (FileNotFoundException e1) {
			showDialog(AlertType.WARNING,"Varoitus", "Genretiedostoa ei löytynyt", e1.getMessage());
		}
    	try {
			kirtika.sortGenres();
		} catch (IOException e) {
			showDialog(AlertType.WARNING,"Varoitus", "Tiedoston käsittely epäonnistui genrejä lajitellessa", e.getMessage());
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

    /**
     * Editing the isbn from gui
     */
    @FXML
    void handleSetBookIsbn() {
    	Book book = chooserBooks.getSelectedObject();
    	if (book.set(5, fieldIsbn.getText())) {
    		Dialogs.showMessageDialog("Muokkaus onnistui!");
    	}
    	displayBookInfo(book);
    }
    
    /**
     * Editing the language from gui
     */
    @FXML
    void handleSetBookLanguage() {
        Book book = chooserBooks.getSelectedObject();
        if (book.set(1, fieldLanguage.getText())) {
            Dialogs.showMessageDialog("Muokkaus onnistui!");
        }
        displayBookInfo(book);
    }

    /**
     * Editing the publisher from gui
     */
    @FXML
    void handleSetBookPublisher() {
        Book book = chooserBooks.getSelectedObject();
        if (book.set(2, fieldPublisher.getText())) {
            Dialogs.showMessageDialog("Muokkaus onnistui!");
        }
        displayBookInfo(book);
    }

    /**
     * Editing the books release year from gui
     */
    @FXML
    void handleSetBookReleaseYear() {
        Book book = chooserBooks.getSelectedObject();
        if (book.set(3, fieldReleaseYear.getText())) {
            Dialogs.showMessageDialog("Muokkaus onnistui!");
        }
        displayBookInfo(book);
    }

    /**
     * Editing the books writer from gui
     */
    @FXML
    void handleSetBookWriter() {
        Book book = chooserBooks.getSelectedObject();
        if (book.set(0, fieldWriters.getText())) {
            Dialogs.showMessageDialog("Muokkaus onnistui!");
        }
        displayBookInfo(book);
    }
    
	/**
     * Sets the classification id
     */
    @FXML
    void handleSetBookClassificationId() {
    	Book book = chooserBooks.getSelectedObject();
        if (book.set(4, fieldClassificationId.getText())) {
            Dialogs.showMessageDialog("Muokkaus onnistui!");
        }
        displayBookInfo(book);
    }
    
    @FXML
    void handleClickBookClassificationDesc() {
    	showDialog(AlertType.INFORMATION, "Ei muokattavissa", "Kirjan luokituskuvaus ei ole muokattavissa", "Jos haluat oman luokituskuvauksen, luo mukautettu luokitus genrenäkymästä");
    }
	
	/**
	 * Sets the loan status value for the book selected.
	 */
    @FXML
    void handleSetLoan() {
    	setLoan();
    }
    
	/**
     * Sets the date when loan was given
     */
    @FXML
    void handleSetLoanDate() {
    	setLoanDate();
    }

	/**
     * Sets the date of (desired) return
     */
    @FXML
    void handleSetReturnDate() {
    	setReturnDate();
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
            fieldClassificationId.setEditable(true);
            fieldIsbn.setEditable(true);
        } else {
            fieldWriters.setEditable(false);
            fieldLanguage.setEditable(false);
            fieldPublisher.setEditable(false);
            fieldReleaseYear.setEditable(false);
            fieldClassificationId.setEditable(false);
            fieldIsbn.setEditable(false);
        }
    }
    
    /**
     * Search books 
     * @param event
     */
    @FXML
    void searchBooks() {
    	String keyword = searchBar.getText();
    	if (keyword.isBlank()) {
    		updateChooserBooks();
    		return;
    	}
    	
    	chooserBooks.clear();
    	List<Book> matchedBooks = kirtika.bookMatches(keyword);
    	for (Book book : matchedBooks) {
			chooserBooks.add(book.getBookName(), book);
		}
    	
    }
	
	/**
	 * Deletion of a book
	 * @param event
	 */
    @FXML
    void deleteBook(ActionEvent event) {
    	if(Dialogs.showQuestionDialog("Poista kirja", "Haluatko varmasti poistaa kirjan?", "Kyllä", "Ei")) {
    		Book book = chooserBooks.getSelectedObject();
    		kirtika.deleteBookNotes(book);
    		kirtika.deleteBook(book);
    		updateChooserBooks();
    	}

    }
	
    /**
     * When this method is called, the scene is changed to the loan view.
     * @throws IOException if there is an error with the file.
     */
    @FXML private void handleShowLoanHistory(ActionEvent event) throws IOException {
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
     * When this method is called, the scene is changed to the loan view.
     * @throws IOException if there is an error with the file.
     */
    @FXML private void handleShowGenres(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("KirtikaGenreView.fxml"));
        Parent root = loader.load();
        KirtikaGenreViewController ctrl = loader.getController();
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
    private void handleShowAddBookForm() throws IOException {
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
        	if (s == null) return;
        	ArrayList<String> finnaData;
			try {
				finnaData = kirtika.fetchFinnaData(s);
				ctrl.initFinnaData(finnaData);
			} catch (BookNotFoundException e) {
				showDialog(AlertType.WARNING,"Virhe", "ISBN-haku epäonnistui", "Annetulla ISBN-koodilla ei löytynyt kirjoja Finnan rajapinnasta");
				return;
			}
        	
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
	private void handleShowAboutView() {
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
	private void showDialog(AlertType type, String title, String headerText, String contentText) {
		Alert alert = new Alert(type);
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
			showDialog(AlertType.WARNING,"VAROITUS", "Kirjan lisätietojen tallentaminen ei onnistunut", e.getMessage());
		}
	}
	
	/**
	 * Selects the first book to be active
	 */
	public void selectFirst() {
		chooserBooks.setSelectedIndex(0);
		Book book = chooserBooks.getSelectedObject();
		if (book == null) return;
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
	 * Mediator for displaying all information on a book.
	 * @param book The displayed book
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
	
	/**
	 * Displays the associated text file on textarea
	 * @param book Which text file is shown
	 */
	private void displayBookNotes(Book book) {
		try {
			areaBookNotes.setText(kirtika.getBookNotes(book));
		} catch (IOException e) {
			showDialog(AlertType.WARNING,"Huomio!", "Tekstitiedostoa ei löytynyt", e.getMessage());
		}
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
			// If start date has been given
			if (fieldLoanStartDate.getValue() != null) {
				kirtika.setLoanDate(book.getBookId(), fieldLoanStartDate.getValue());
				LocalDate ldRet = fieldLoanReturnDate.getValue();
				// If return date has been stated and its later than loan start date, set it
				if ( ldRet != null && ldRet.compareTo(fieldLoanStartDate.getValue()) > 0 ) {
					kirtika.setReturnDate(book.getBookId(), ldRet);
				}
				// if return date is given, we assume the loan was given today
			} else if (fieldLoanReturnDate.getValue() != null) {
				kirtika.setLoanDate(book.getBookId(), LocalDate.now());
				LocalDate ldRet = fieldLoanReturnDate.getValue();
				// If return date has been stated and its later than loan start date, set it
				if ( ldRet.compareTo(LocalDate.now()) > 0 ) {
					kirtika.setReturnDate(book.getBookId(), ldRet);
				}
				else {
					showDialog(AlertType.WARNING,"Virhe", "Virheellinen palautuspäivämäärä", "Palautuspäivämäärää ei voitu asettaa");
				}
			}
			
			displayBookInfo(book);
		}
		// Unchecking closes the loan
		if (!b) {
			closeLoan(book);
			displayBookInfo(book);
		}
		
	}
	
	/**
	 * Sets the loan date
	 */
	private void setLoanDate() {
    	Book book = chooserBooks.getSelectedObject();
    	
    	// If user has forgotten to press enter when setting the loaner
    	if (kirtika.getActiveLoan(book.getBookId()) == null && !fieldLoaner.getText().isBlank()) {
    		setLoan();
    	}
    	
    	if (kirtika.getActiveLoan(book.getBookId()) != null) {
    		
    		// Date check
    		if (fieldLoanReturnDate != null)
    			if (fieldLoanStartDate.getValue().compareTo(fieldLoanReturnDate.getValue()) > 0) {
    				showDialog(AlertType.WARNING,"Virhe", "Virheellinen lainauspäivämäärä", "Lainauspäivä ei voi olla ennen palautuspäivää!");
    				fieldLoanStartDate.cancelEdit();
    				displayBookInfo(book);
    				return;
    			}
    		kirtika.setLoanDate(book.getBookId(), fieldLoanStartDate.getValue());
        	Dialogs.showMessageDialog("Lainauspäiväksi asetettu " + fieldLoanStartDate.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
    	}
	}
	
	/**
	 * Sets the return date.
	 */
	private void setReturnDate() {
    	Book book = chooserBooks.getSelectedObject();
    	
    	// If user has forgotten to press enter when setting the loaner
    	if (kirtika.getActiveLoan(book.getBookId()) == null && !fieldLoaner.getText().isBlank()) {
    		setLoan();
    	}
    	
    	if (kirtika.getActiveLoan(book.getBookId()) != null) {
    		
    		// When incorrect/null date was given (pro
    		if (fieldLoanReturnDate.getValue() == null) return;
    		
    		// Date check
    		if (fieldLoanStartDate.getValue() != null && fieldLoanReturnDate.getValue().compareTo(fieldLoanStartDate.getValue()) < 0) {
    			showDialog(AlertType.WARNING,"Virhe", "Virheellinen palautuspäivämäärä", "Palautuspäivä ei voi olla ennen lainauspäivää!");
    			fieldLoanReturnDate.cancelEdit();
    			displayBookInfo(book);
    			return;
    		}
    		kirtika.setReturnDate(book.getBookId(), fieldLoanReturnDate.getValue());
    		Dialogs.showMessageDialog("Palautuspäiväksi asetettu " + fieldLoanReturnDate.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
    	}
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
		} catch (FileNotFoundException e) {
			showDialog(AlertType.WARNING,"Huomio!", "Tiedostojen tallentaminen ei onnistunut", e.getMessage());
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
		} catch (FileNotFoundException e) {
			showDialog(AlertType.WARNING,"Huomio!", "Kirjan palauttaminen ei onnistunut", e.getMessage());
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Kirjalla ei ollut aktiivista lainaa!");
		}
	}
}
