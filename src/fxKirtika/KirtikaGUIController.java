package fxKirtika;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class KirtikaGUIController{
	
	@FXML
	MenuItem switchScene;
	MenuBar myMenuBar;
	Button btn;
	
	@FXML private void handleTallennaLisatiedot() {
		Dialogs.showMessageDialog("Ei osata tallentaa");
	}
	
	@FXML
	public void initialize() {
		
	}
	
	@FXML
	private void handleLainahistoria() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("KirtikaLoanView.fxml"));
		Stage window = (Stage)myMenuBar.getScene().getWindow();
		window.setScene(new Scene(root,1200, 709));
	}
	
	@FXML private void handleBtn() throws Exception{
		Dialogs.showMessageDialog("heelo");
		
	}
	
	/**
	 * Kun tätä metodia kutsutaan, vaihdetaan scene
	 * @throws IOException jos tiedostossa jotain häikkää
	 */
	@FXML public void changeScreenMenuItemPushed(ActionEvent event) throws IOException {
		Parent loanViewParent = FXMLLoader.load(getClass().getResource("KirtikaLoanView.fxml"));
		Scene loanViewScene = new Scene(loanViewParent);
		
		//Stage window = (Stage) myMenuBar.getScene().getWindow();
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(loanViewScene);
		window.show();
	}
	
}
