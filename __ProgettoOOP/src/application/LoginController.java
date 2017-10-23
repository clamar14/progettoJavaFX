package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

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
    
    public static Utente user;

    @FXML
    void login(ActionEvent e) {
		try {
			ResultSet rs = Database.query("SELECT * from Utente where username = '" +userName.getText()+ "' AND password = '" +String.valueOf(password.getText())+ "'");
			if (rs.next()){
				try {
					user = new Utente(rs.getString("username"));
					FXMLLoader loader = new FXMLLoader(Main.class.getResource("HomePage.fxml"));
					ScrollPane registrazione = (ScrollPane) loader.load();
					Scene scene = new Scene(registrazione);
					Main.getStage().setScene(scene);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else{
				//new Errore("Username o Password errati");
			}	
		}
		catch (SQLException ex) {
			System.out.println("Errore nell' interrogazione al DB");
		}
	}
    

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
    
    public static Utente getUser(){
    	return user;
    }
}
