package fxKirtika;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
			Parent root = FXMLLoader.load(getClass().getResource("KirtikaMainView.fxml"));
			Scene sceneMain = new Scene(root,1200,709);
			sceneMain.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Kirtika - Kirjatietokanta");
			primaryStage.setScene(sceneMain);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	  
	public static void main(String[] args) {
		launch(args);
	}
	
}
