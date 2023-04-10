package fxKirtika;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import kirtika.Book;
import kirtika.Kirtika;
import kirtika.SailoException;

/**
 * 
 * @author Jansromi
 * @version 9.4.2023
 *
 */
public class KirtikaAddBookController implements Initializable{

    @FXML
    private Button addBookSave;

    @FXML
    private TextField fieldIsbn, fieldJulkaisija, fieldKieli, 
    fieldKirjailija, fieldKirjanNimi, fieldLuokitus;

    /**
     * Handle for adding a new book to Kirtika
     * TODO: Errorhandlers
     * @param event
     */
    @FXML
    void addBook(ActionEvent event) {
    	newBook();
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    
    // 
	//=======================================================================================
    //
    
    private Kirtika kirtika;
    
    /**
     * Sets the mediator-class
     * @param kirtika
     */
    public void initKirtika(Kirtika kirtika) {
    	this.kirtika = kirtika;
    }

	/**
     * Adding a new book to the registery
     */
    private void newBook() {
		Book book = new Book();
		book.setBookId();
		book.setOdysseia();
		
		try {
			kirtika.addBook(book);
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Ongelmia kirjan lisäämisessä " + e.getMessage());
		}
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
