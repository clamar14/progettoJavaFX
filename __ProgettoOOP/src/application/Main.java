package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;

/**
 * Classe che permette all' utente di effettuare il login o se non è iscritto di effettuare la sua registrazione 
 *
 */
public class Main extends Application {
	static Stage stage;
	
	/**
	 *  Metodo con cui viene caricata la pagina dedicata all'amministratore
	 */
	@Override
	public void start(Stage s) {
		try {
			 stage=s;
			 FXMLLoader loader = new FXMLLoader(Main.class.getResource("Login.fxml"));
	         ScrollPane loginLayout = (ScrollPane) loader.load();
	    
	         Scene scene = new Scene(loginLayout);
	         stage.setScene(scene);
	         stage.setMaxHeight(510);
	         stage.setMaxWidth(700);
	         stage.show();
	         
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Stage getStage(){
		return stage;
	}
	
	/**
	 * Main del programma 
	 * @param args
	 */
	public static void main(String[] args) {
		Database db = new Database();
		db.testInsert();
		launch(args);
	}
	
}
