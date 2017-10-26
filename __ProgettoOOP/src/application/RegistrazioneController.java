package application;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class RegistrazioneController implements Initializable  {
    @FXML
	private TextField nome;

    @FXML
    private TextField cognome;

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<String> sesso;

    @FXML
    private DatePicker data;

    @FXML
    private ComboBox<Integer> altezza;
    
    @FXML
    private ComboBox<Integer> peso;
    
    @FXML
    private ComboBox<String> attività;
    
    @FXML
    private Button indietro;

    @FXML
    private Label messaggio;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int i;
		
    	sesso.getItems().addAll("uomo", "donna");
		
		for(i=50; i<=300; ++i){
			altezza.getItems().add(i);
		}
		
		for(i=20; i<=500; ++i){
			peso.getItems().add(i);
		}
		
		attività.getItems().addAll("Leggera", "Moderata", "Pesante");
	}

    @FXML
    void registrazione(ActionEvent event) {
		boolean cont = true;
		if(userName.getText().isEmpty() || password.getText().isEmpty() || data.getValue()==null || altezza.getValue()==null || peso.getValue()==null || attività.getValue()==null){
			messaggio.setText("Riempire i campi obbligatori");
			System.out.println("Riempire i campi obbligatori");
			cont = false;
		}
		if(data.getValue()!=null && (data.getValue().getYear()<(Calendar.getInstance().get(Calendar.YEAR)-100) 
				|| data.getValue().getYear()>(Calendar.getInstance().get(Calendar.YEAR)-14))){
			messaggio.setText("Iscrizione consentita ad utenti di età compresa tra 14 e 100 anni");
			System.out.println("Inserire un anno di nascita valido");
			cont = false;
		}
		
		if(cont){
			try {
				ResultSet rs;
				rs = Database.query("SELECT * FROM Utente U where U.username = '" +userName.getText()+ "'");
				if (rs.next()){
					System.out.println("Username non valido");
					messaggio.setText("Username non valido");
				}
				else {
					Database.update("INSERT INTO Utente VALUES ('" +userName.getText()+ "','" + password.getText()+ "','"
						+nome.getText()+ "','" + cognome.getText() + "','" +sesso.getValue()+ "','"  +data.getValue()+ "','"
						+peso.getValue()+ "','" +altezza.getValue()+ "','" +attività.getValue()+ "')");
				}	
			}
			catch (SQLException ex) {
				System.out.println("Errore nell' interrogazione al DB");
			}
		}
		messaggio.setVisible(true);
    }
    
    @FXML
    void indietro(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Login.fxml"));
			ScrollPane login = (ScrollPane) loader.load();
			Scene scene = new Scene(login);
			Main.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
    
}
