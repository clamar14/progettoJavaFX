package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class HomePageController implements Initializable {
	@FXML
	private Label benvenuto;
	
    @FXML
    private Label sesso;

    @FXML
    private Label età;

    @FXML
    private Label peso;

    @FXML
    private Label altezza;

    @FXML
    private Label fabbisogno;
    
    @FXML
    private Button visualizza;

    @FXML
    private Button aggiorna;

    @FXML
    private Button alimentiMangiati;

    @FXML
    private Button indietro;

    @FXML
    private Button modifica;

    @FXML
    void aggiornaDiario(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Login.fxml"));
			ScrollPane registrazione = (ScrollPane) loader.load();
			Scene scene = new Scene(registrazione);
			Main.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    @FXML
    void listaAlimenti(ActionEvent event) {

    }

    @FXML
    void modificaProfilo(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("ModificaAccount.fxml"));
			ScrollPane modifica = (ScrollPane) loader.load();
			Scene scene = new Scene(modifica);
			Main.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    @FXML
    void visualizzaDiario(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Grafico.fxml"));
			ScrollPane diario = (ScrollPane) loader.load();
			Scene scene = new Scene(diario);
			Main.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Utente u = LoginController.getUser();
		benvenuto.setText(benvenuto.getText()+" "+u.getUserName());
		sesso.setText(u.getSesso());
		peso.setText(String.valueOf(u.getPeso())+" kg");
		altezza.setText(String.valueOf(u.getAltezza())+" cm");
		età.setText(String.valueOf(u.getEtà())+" anni");
		fabbisogno.setText(fabbisogno.getText()+":\n"+String.valueOf(u.getFabbisogno())+" kcal");
	}

}

