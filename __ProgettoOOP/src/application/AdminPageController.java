package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AdminPageController {
	final static String code = "aw1t56Y%";
    
	@FXML
    private Button indietro;

    @FXML
    private PasswordField codice;

    @FXML
    private Button inserisci;

    @FXML
    private AnchorPane adminPane;

    @FXML
    private Button aggiungiAliimento;

    @FXML
    private Button rimuoviAlimento;

    @FXML
    private Button cercaAlimento;

    @FXML
    private Button aggiungiSport;

    @FXML
    private Button rimuoviSport;

    @FXML
    private Button cercaSport;

    @FXML
    private AnchorPane actionPane;

    @FXML
    private Label azioneSelezionata;

    @FXML
    private TextField field1;

    @FXML
    private TextField field2;

    @FXML
    private Button esegui;

    @FXML
    private Label descrizione1;

    @FXML
    private Label descrizione2;

    @FXML
    void addFood(ActionEvent event) {
    	visibleActionPane();
    	azioneSelezionata.setText("Aggiungi alimento");
    	food();
    	field2.setDisable(false);
    }

    @FXML
    void addSport(ActionEvent event) {
    	visibleActionPane();
    	azioneSelezionata.setText("Aggiungi sport");
    	sport();
    	field2.setDisable(false);
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

    @FXML
    void deleteFood(ActionEvent event) {
    	visibleActionPane();
    	azioneSelezionata.setText("Rimuovi alimento");
    	food();
    	field2.setDisable(true);
    }

    @FXML
    void deleteSport(ActionEvent event) {
    	visibleActionPane();
    	azioneSelezionata.setText("Rimuovi sport");
    	sport();
    	field2.setDisable(true);
    }

    @FXML
    void inserisci(ActionEvent event) {
    	if(codice.getText().equals(code))
    		adminPane.setVisible(true);
    }

    @FXML
    void searchFood(ActionEvent event) {
    	visibleActionPane();
    	azioneSelezionata.setText("Cerca alimento");
    	food();
    	field2.setDisable(false);
    }

    @FXML
    void searchSport(ActionEvent event) {
    	visibleActionPane();
    	azioneSelezionata.setText("Cerca sport");
    	sport();
    	field2.setDisable(false);
    }
    
    void visibleActionPane(){
    	if(!actionPane.isVisible())
    		actionPane.setVisible(true);
    }

    void food(){
    	descrizione1.setText("Alimento");
    	descrizione2.setText("Calorie per 100 grammi");
    }
    
    void sport(){
    	descrizione1.setText("Sport");
    	descrizione2.setText("Calorie per ora");
    }
    
    @FXML
    void esegui(ActionEvent event) {
    	if(azioneSelezionata.equals("Aggiungi alimento")){
			if(field1.getText()!=null && field2.getText()!=null)
				Database.update("INSERT INTO alimenti (nome, kcal_100g) VALUES('"+ field1.getText()+"','"+field2.getText()+"')");
			//else
				//new Errore("Inserire un alimento con le rispettive calorie");
		}
		if(azioneSelezionata.equals("Aggiungi sport")){
			if(field1.getText()!=null && field2.getText()!=null)
				Database.update("INSERT INTO sport (nome, kcal_ora) VALUES('"+field1.getText()+"','"+field2.getText()+"')");
			//else
				//new Errore("Inserire uno sport con le rispettive calorie");
		}
		if(azioneSelezionata.equals("Rimuovi alimento")){
			ResultSet rs= Database.query("SELECT * FROM alimenti WHERE nome='"+field1.getText()+"'");
			try {
				if(rs.next())
					Database.update("DELETE FROM alimenti where nome='"+field1.getText()+"'");
				//else
					//new Errore("Impossibile rimuovere un alimento non presente nel database");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if(azioneSelezionata.equals("Rimuovi sport")){
			ResultSet rs= Database.query("SELECT * FROM sport WHERE nome='"+field1.getText()+"'");
			try {
				if(rs.next())
					Database.update("DELETE FROM sport where nome='"+field2.getText()+"'");
				//else
					//new Errore("Impossibile rimuovere uno sport non presente nel database");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if(azioneSelezionata.equals("Cerca alimento")){
			ResultSet rs;
			if(field1.getText()!=null && field2.getText()!=null)
				rs=Database.query("SELECT * FROM alimenti WHERE nome='"+field1.getText()+"' AND kcal_100g='"+field2.getText()+"'");
			if(field1.getText()==null)
				rs=Database.query("SELECT * FROM alimenti WHERE kcal_100g='"+field2.getText()+"'");
			if(field2.getText()==null)
				rs=Database.query("SELECT * FROM alimenti WHERE nome='"+field1.getText()+"'");
				
			//CERCA
			
		}
		if(azioneSelezionata.equals("Cerca sport")){
			ResultSet rs;
			if(field1.getText()!=null && field2.getText()!=null)
				rs=Database.query("SELECT * FROM sport WHERE nome='"+field1.getText()+"' AND kcal_ora='"+field2.getText()+"'");
			if(field1.getText()==null)
				rs=Database.query("SELECT * FROM sport WHERE kcal_ora='"+field2.getText()+"'");
			if(field2.getText()==null)
				rs=Database.query("SELECT * FROM sport WHERE nome='"+field1.getText()+"'");
				
			//CERCA
			
		}
    }
}
