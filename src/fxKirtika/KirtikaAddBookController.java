package fxKirtika;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import kirtika.Book;
import kirtika.Genre;
import kirtika.Kirtika;
import kirtika.SailoException;

/**
 * Controller for book adding dialog
 * @author Jansromi
 * @version 9.4.2023
 *
 */
public class KirtikaAddBookController implements Initializable{


    @FXML
    private ChoiceBox<Genre> choiceYklClassification;
    
    @FXML
    private TextField fieldBookName, fieldCustomClassification, fieldIsbn,
    fieldLanguage, fieldPublisher, fieldReleaseYear, fieldWriters;
    
    private ArrayList<TextField> infoFields = new ArrayList<>();
    
    /**
     * Handle for adding a new book to Kirtika
     * @param event
     */
    @FXML
    void addBook(ActionEvent event) {
    	boolean b = addBook();
    	if (b) ((Node)(event.getSource())).getScene().getWindow().hide();
    	else addBook();
    }
    
    
    /**
     * Handle for cancelling the dialog
     * @param event
     */
    @FXML
    void handleCancel(ActionEvent event) {
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    
    // 
	//=======================================================================================
    //
    
    private Kirtika kirtika;
    
    /*
     * [0] == books title
	 * [1] == books main writer
	 * [2] == books language
	 * [3] == books publisher
	 * [4] == books release year
	 * [5] == books isbn
	 * [6] == YKL classification id
     */
    private ArrayList<String> finnaData = new ArrayList<String>();
    
    /**
     * Sets the mediator-class
     * @param kirtika
     */
    public void initKirtika(Kirtika kirtika) {
    	this.kirtika = kirtika;
		ArrayList<Genre> ar = kirtika.getGenres();
		for (Genre genre : ar) {
			choiceYklClassification.getItems().add(genre);
		}
    }
    
    /**
     * Sets the data fetched from Finna
     */
    public void initFinnaData(ArrayList<String> list) {
    	finnaData = list;
    	setFinnaFields();
    }
    


    /**
     * Adds a new book to the registry
     */
    private boolean addBook() {
        Book book = new Book();
        if (fieldBookName.getText().isEmpty()) {
            Dialogs.showMessageDialog("Sinun on asetettava kirjalle nimi");
            return false;
        }

        if (fieldWriters.getText().isEmpty()) {
            Dialogs.showMessageDialog("Sinun on asetettava kirjalle kirjoittaja");
            return false;
        }

        ArrayList<String> fields = getGuiFieldValues();
        Genre genre = getSelectedGenre();

        // If genre is not null (a genre is selected on choichebox), replace the custom genre.
        // Otherwise add the genre custom genre.
        if (genre != null ) {
        	fields.remove(6);
        	fields.add(genre.getGenreId());
        }

        book.setGuiValues(fields);

        try {
            kirtika.addBook(book);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia kirjan lisäämisessä " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * @return An ArrayList of values set in AddBook-view.
     */
    private ArrayList<String> getGuiFieldValues() {
        var fields = new ArrayList<String>();
        for (TextField f : infoFields) {
            var value = f.getText().isBlank() ? "null" : f.getText();
            fields.add(value);
        }
        return fields;
    }
    
    /**
     * Sets the text for dialog text fields. 
     */
    private void setFinnaFields() {
    	for (int i = 0; i < infoFields.size(); i++) {
			infoFields.get(i).setText(finnaData.get(i));
		}
    }

    /**
     * @return The selected genre from the choice box, or null if none is selected.
     */
    private Genre getSelectedGenre() {
        return choiceYklClassification.getSelectionModel().getSelectedItem();
    }
    
    /**
     * Initializes the text fields in the dialog.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		infoFields.add(fieldBookName);
		infoFields.add(fieldWriters);
		infoFields.add(fieldLanguage);
		infoFields.add(fieldPublisher);
		infoFields.add(fieldReleaseYear);
		infoFields.add(fieldIsbn);
		infoFields.add(fieldCustomClassification);
	}

}
