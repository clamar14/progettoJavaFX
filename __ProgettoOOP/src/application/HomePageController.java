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

/**
 * Classe che permette di controllare l'interfaccia dell'homepage
 *
 */
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
    private Button trend;

    @FXML
    private Button indietro;

    @FXML
    private Button modifica;
    
    private Utente user;
    
    public HomePageController(Utente user){
    	this.user=user;
    }

    /**
     * Metodo con cui si apre il diario
     * @param event
     */
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

    /**
     * Metodo con cui si effetua il logout e si ritorna alla pagina di login
     * @param event
     */
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

    /**
     * Metodo che apre la pagina in cui l'utente può visualizzare le statistiche
     * @param event
     */
    @FXML
    void visualizzaTendenza(ActionEvent event) {
    	try {
    		TrendController controller = new TrendController(user);
    		FXMLLoader loader = new FXMLLoader(Main.class.getResource("Trend.fxml"));
			loader.setController(controller);
			ScrollPane modifica = (ScrollPane) loader.load();
			Scene scene = new Scene(modifica);
			Main.getStage().setScene(scene);
    	} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    /**
     * Metodo che apre una pagina ed essa modifica il profilo
     * @param event
     */
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

    /**
     * Metodo che apre la pagine in cui è presente il grafico a torta
     * @param event
     */
    @FXML
    void visualizzaDiario(ActionEvent event) {
    	try {
    		GraficoController controller = new GraficoController(user);
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Grafico.fxml"));
			loader.setController(controller);
			ScrollPane diario = (ScrollPane) loader.load();
			Scene scene = new Scene(diario);
			Main.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    /**
     * Metodo che viene richiamato a ogni apertura dell'interfaccia che permette di settare le informazioni dell'utente 
     */
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

