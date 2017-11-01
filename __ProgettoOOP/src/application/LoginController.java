package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
/**
 * Classe che permette di controllare l'iterfaccia di login
 *
 */
public class LoginController {
	@FXML
    private PasswordField password;

    @FXML
    private TextField userName;

    @FXML
    private Button login;

    @FXML
    private Button registrazione;
    
    @FXML
    private Button admin;
    
    @FXML
    private Label messaggio;

    /**
     * Metodo che viene invocato ogni volta che viene eseguito un login da un utente 
     * @param e
     */
    @FXML
    void login(ActionEvent e) {
		try {
			ResultSet rs = Database.query("SELECT * from Utente where username = '" +userName.getText()+ "' AND password = '" +String.valueOf(password.getText())+ "'");
			if (rs.next()){
				try {
					HomePageController controller = new HomePageController(new Utente(rs.getString("username")));
					FXMLLoader loader = new FXMLLoader(Main.class.getResource("HomePage.fxml"));
					loader.setController(controller);
					ScrollPane registrazione = (ScrollPane) loader.load();
					Scene scene = new Scene(registrazione);
					Main.getStage().setScene(scene);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else{
				messaggio.setVisible(true);
			}	
		}
		catch (SQLException ex) {
			System.out.println("Errore nell' interrogazione al DB");
		}
	}
    

    /**
     * Metodo che viene invocato ogni volta che un utente deve eseguire la sua registrazione 
     * @param event
     */
    @FXML
    void registrazione(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Registrazione.fxml"));
			ScrollPane registrazione = (ScrollPane) loader.load();
			Scene scene = new Scene(registrazione);
			Main.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
    
    /**
     * Metodo che viene invocato
     * @param event
     */
    @FXML
    void admin(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("AdminPage.fxml"));
			ScrollPane amministratore = (ScrollPane) loader.load();
			Scene scene = new Scene(amministratore);
			Main.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
}
