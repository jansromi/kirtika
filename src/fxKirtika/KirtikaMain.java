package fxKirtika;
	
import javafx.application.Application;
import javafx.stage.Stage;
import kirtika.Kirtika;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * 
 * @author Roba
 *
 */
public class KirtikaMain extends Application {
	
	/**
	 * Ohjelman käynnistyessä näytetään päänäkymä
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KirtikaMainView.fxml"));
			final Pane root = (Pane)ldr.load();
			final KirtikaGUIController kirtikaCtrl = (KirtikaGUIController)ldr.getController();
			
			Scene sceneMain = new Scene(root, 1200,709);
			
			sceneMain.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Kirtika - Kirjatietokanta");
			primaryStage.setScene(sceneMain);
			
			Kirtika kirtika = new Kirtika();
			kirtikaCtrl.setKirtika(kirtika);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	  
	public static void main(String[] args) {
		launch(args);
	}
	
}
