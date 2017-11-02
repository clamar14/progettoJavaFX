package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Database;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;

/**
 * Classe che permette all' utente di effettuare il login o se non è iscritto di effettuare la sua registrazione 
 *
 */
public class TestApp extends Application {
	static Stage stage;
	
	/**
	 *  Metodo con cui viene caricata la pagina dedicata all'amministratore
	 */
	@Override
	public void start(Stage s) {
		try {
			 stage=s;
			 FXMLLoader loader = new FXMLLoader(TestApp.class.getResource("Login.fxml"));
	         ScrollPane loginLayout = (ScrollPane) loader.load();
	    
	         Scene scene = new Scene(loginLayout);
	         stage.setScene(scene);
	         stage.setMaxHeight(510);
	         stage.setMaxWidth(700);
	         stage.show();
	         
	         stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	             public void handle(WindowEvent we) {
	                 Database.closeConnection();
	                 System.out.println("Stage chiuso");
	             }
	         });
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Stage getStage(){
		return stage;
	}
	

	public static void main(String[] args) {
		Database db = new Database();
		db.testInsert();
		launch(args);
	}
	
}
