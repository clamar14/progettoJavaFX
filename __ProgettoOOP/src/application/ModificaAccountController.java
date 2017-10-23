package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class ModificaAccountController implements Initializable {

    @FXML
    private TextField nome;

    @FXML
    private TextField cognome;

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
    private ComboBox<String> attivit�;

    @FXML
    private Button modifica;

    @FXML
    private Button indietro;

    @FXML
    void homepage(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("HomePage.fxml"));
			ScrollPane homepage = (ScrollPane) loader.load();
			Scene scene = new Scene(homepage);
			Main.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    @FXML
    void modifica(ActionEvent event) {

    }

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
  		
  		attivit�.getItems().addAll("Leggera", "Moderata", "Pesante");
  		
  		ResultSet rs = Database.query("SELECT * from Utente where username = '" +LoginController.getUser().getUserName()+ "'");
		try {
			nome.setText(rs.getString("nome"));
			cognome.setText(rs.getString("cognome"));
			sesso.setValue(rs.getString("sesso"));
			//=(Calendar.getInstance().get(Calendar.YEAR)-rs.getInt("annoDiNascita"));
			altezza.setValue(rs.getInt("altezza"));
			peso.setValue(rs.getInt("peso"));
			attivit�.setValue(rs.getString("attivit�"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

