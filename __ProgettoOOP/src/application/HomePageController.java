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
    
    private Utente user;
    
    public HomePageController(Utente user){
    	this.user=user;
    }

    @FXML
    void aggiornaDiario(ActionEvent event) {
    	try {
    		DiarioController controller = new DiarioController(user);
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Diario.fxml"));
			loader.setController(controller);
			ScrollPane diario = (ScrollPane) loader.load();
			Scene scene = new Scene(diario);
			Main.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    @FXML
    void logout(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Login.fxml"));
			ScrollPane login = (ScrollPane) loader.load();
			Scene scene = new Scene(login);
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
    		ModificaAccountController controller = new ModificaAccountController(user);
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("ModificaAccount.fxml"));
			loader.setController(controller);
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
    		GraficoController controller = new GraficoController(user.getUserName());
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Grafico.fxml"));
			loader.setController(controller);
			ScrollPane diario = (ScrollPane) loader.load();
			Scene scene = new Scene(diario);
			Main.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		benvenuto.setText(benvenuto.getText()+" "+user.getUserName());
		sesso.setText(user.getSesso());
		peso.setText(String.valueOf(user.getPeso())+" kg");
		altezza.setText(String.valueOf(user.getAltezza())+" cm");
		età.setText(String.valueOf(user.getEtà())+" anni");
		fabbisogno.setText(fabbisogno.getText()+":\n"+String.valueOf(user.getFabbisogno())+" kcal");
	}

}

