package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

/**
 * Classe che permette di controllare l'interfaccia in cui si può effettuare
 *  la modifica delle informazioni di un utente
 *
 */
public class ModificaAccountController implements Initializable {
	private Utente user;
	
    @FXML
    private TextField nome;

    @FXML
    private TextField cognome;
    
    @FXML
    private Label messaggio;

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
    private Button modifica;

    @FXML
    private Button indietro;

    public ModificaAccountController(Utente user){
    	this.user=user;
    }
    
    /**
     * Metodo che permette di tornare all'homepage
     * @param event
     */
    @FXML
    void homepage(ActionEvent event) {
    	try {
    		HomePageController controller = new HomePageController(user);
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("HomePage.fxml"));
			loader.setController(controller);
			ScrollPane homepage = (ScrollPane) loader.load();
			Scene scene = new Scene(homepage);
			Main.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
    
    /**
     * Metodo che viene invocato ogni volta che un utente vuole modificare una o più delle sue informazioni 
     * @param event
     */
    @FXML
    void modifica(ActionEvent event) {  	
    	Database.update("UPDATE Utente "
				+"SET nome='"+nome.getText()
				+"', cognome='"+cognome.getText()
				+"', dataDiNascita='"+data.getValue()
				+"', sesso='"+sesso.getValue()
				+"', peso='"+peso.getValue()
				+"', altezza='"+altezza.getValue()
				+"', attività='"+attività.getValue()
				+"' WHERE username='"+user.getUserName()+"'");
    	user.setAltezza(altezza.getValue());
    	user.setAttività(attività.getValue());
    	user.setEtà(data.getValue());
    	user.setPeso(peso.getValue());
    	user.setSesso(sesso.getValue());
    	messaggio.setVisible(true);
    }
    /**
     * Metodo che viene invocato ogni volta che viene aperta la pagina per modificare l'account
     * e inizializza i vari campi con i dati immessi dall'utente in fase di registrazione
     */
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
  		
  		ResultSet rs = Database.query("SELECT * from Utente where username = '" +user.getUserName()+ "'");
		try {
			nome.setText(rs.getString("nome"));
			cognome.setText(rs.getString("cognome"));
			sesso.setValue(rs.getString("sesso"));
			
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		    String dateInString = rs.getString("dataDiNascita");
		    Date date = dt.parse(dateInString);
		    data.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		        
			altezza.setValue(rs.getInt("altezza"));
			peso.setValue(rs.getInt("peso"));
			attività.setValue(rs.getString("attività"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
            e.printStackTrace();
		}
	}

}


