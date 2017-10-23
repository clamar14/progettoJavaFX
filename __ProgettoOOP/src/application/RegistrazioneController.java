package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
    private TextField password;

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
