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
 * 
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
     * TODO: Errorhandlers
     * @param event
     */
    @FXML
    void addBook(ActionEvent event) {
    	addBook();
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    void handleCancel(ActionEvent event) {
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    
    // 
	//=======================================================================================
    //
    
    private Kirtika kirtika;
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
    
    private void setFinnaFields() {
    	for (int i = 0; i < infoFields.size(); i++) {
			infoFields.get(i).setText(finnaData.get(i));
		}
    }

    /**
     * Adding a new book to the registry
     */
    private void addBook() {
        Book book = new Book();

        if (fieldBookName.getText().isEmpty()) {
            Dialogs.showMessageDialog("Sinun on asetettava kirjalle nimi");
            return;
        }

        if (fieldWriters.getText().isEmpty()) {
            Dialogs.showMessageDialog("Sinun on asetettava kirjalle kirjoittaja");
            return;
        }

        ArrayList<String> fields = getGuiFieldValues();
        Genre genre = getSelectedGenre();
        String customGenre = getGenreName();

        // If genre is null, add a custom genre. Otherwise add the genre id.
        fields.add(genre == null ? customGenre : genre.getGenreId());

        book.setGuiValues(fields);

        try {
            kirtika.addBook(book);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia kirjan lisäämisessä " + e.getMessage());
        }
    }

    /**
     * @return An ArrayList of values set in AddBook-view.
     *         Doesn't contain genre-information.
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
     * @return The selected genre from the choice box, or null if none is selected.
     */
    private Genre getSelectedGenre() {
        return choiceYklClassification.getSelectionModel().getSelectedItem();
    }

    /**
     * @return Custom genre. If blank string, "null"
     */
    private String getGenreName() {
        var customGenre = fieldCustomClassification.getText();
        return customGenre.isBlank() ? "null" : customGenre;
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		infoFields.add(fieldBookName);
		infoFields.add(fieldWriters);
		infoFields.add(fieldLanguage);
		infoFields.add(fieldPublisher);
		infoFields.add(fieldReleaseYear);
		infoFields.add(fieldIsbn);
	}

}
