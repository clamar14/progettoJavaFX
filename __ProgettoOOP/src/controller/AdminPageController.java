package controller;

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
import model.Database;
import view.TestApp;

/**
 * Classe che permette di controllare l'interfaccia amministratore
 *
 */
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
    private ScrollPane searchScrollPane;

    @FXML
    private AnchorPane searchAnchorPane;
    
    @FXML
    private Label lista;
    
    @FXML
    private Label messaggio;

    /**
     * Metodo che viene invocato ogni volta che l'amministratore vuole inserire un alimento
     * e apre un pannello per consentirne l'inserimento
     * @param event
     */
    @FXML
    void addFood(ActionEvent event) {
    	visibleActionPane();
    	azioneSelezionata.setText("Aggiungi alimento");
    	food();
    	field2.setDisable(false);
    }

    /**
     * Metodo che viene invocato ogni volta che l'amministratore vuole inserire uno sport
     * e apre un pannello per consentirne l'inserimento
     * @param event
     */
    @FXML
    void addSport(ActionEvent event) {
    	visibleActionPane();
    	azioneSelezionata.setText("Aggiungi sport");
    	sport();
    	field2.setDisable(false);
    }

    /**
     * Metodo che permette di tornare dall'interfaccia amministratore all'interfaccia di login
     * @param event
     */
    @FXML
    void indietro(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(TestApp.class.getResource("Login.fxml"));
			ScrollPane login = (ScrollPane) loader.load();
			Scene scene = new Scene(login);
			TestApp.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    /**
     * Metodo che viene invocato ogni volta che l'amministratore vuole eliminare un alimento
     * e apre un pannello per consentirne la rimozione
     * @param event
     */
    @FXML
    void deleteFood(ActionEvent event) {
    	visibleActionPane();
    	azioneSelezionata.setText("Rimuovi alimento");
    	food();
    	field2.setDisable(true);
    }

    /**
     * Metodo che viene invocato ogni volta che l'amministratore vuole eliminare uno sport
     * e apre un pannello per consentirne la rimozione
     * @param event
     */
    @FXML
    void deleteSport(ActionEvent event) {
    	visibleActionPane();
    	azioneSelezionata.setText("Rimuovi sport");
    	sport();
    	field2.setDisable(true);
    }

    /**
     * Metodo che viene invocato quando l'amministratore vuole accedere all'area riservata
     *  e serve per verificare la password
     * @param event
     */
    @FXML
    void inserisci(ActionEvent event) {
    	if(codice.getText().equals(code))
    		adminPane.setVisible(true);
    }

    /**
     * Metodo che viene invocato ogni volta che l'amministratore vuole ricercare un alimento all'interno del database
     * @param event
     */
    @FXML
    void searchFood(ActionEvent event) {
    	visibleActionPane();
    	azioneSelezionata.setText("Cerca alimento");
    	food();
    	field2.setDisable(false);
    }

    /**
     * Metodo che viene invocato ogni volta che l'amministratore vuole ricercare uno sport all'interno del database
     * @param event
     */
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
    
    /**
     * Metodo che permette di interagire con il database a seconda dell'azione
     * che l'amministratore ha precedentemente settato
     * @param event
     */
    @FXML
    void esegui(ActionEvent event) {
    	searchScrollPane.setVisible(false);
    	messaggio.setText("");
    	if(azioneSelezionata.getText().equals("Aggiungi alimento")){
			if(!field1.getText().isEmpty() && !field2.getText().isEmpty())
				Database.update("INSERT INTO alimenti (nome, kcal_100g) VALUES('"+ field1.getText()+"','"+field2.getText()+"')");
			else{
				messaggio.setText("Inserire un alimento con le rispettive calorie");
			}
		}
		if(azioneSelezionata.getText().equals("Aggiungi sport")){
			if(!field1.getText().isEmpty() && !field2.getText().isEmpty())
				Database.update("INSERT INTO sport (nome, kcal_ora) VALUES('"+field1.getText()+"','"+field2.getText()+"')");
			else
				messaggio.setText("Inserire uno sport con le rispettive calorie");
		}
		if(azioneSelezionata.getText().equals("Rimuovi alimento")){
			ResultSet rs= Database.query("SELECT * FROM alimenti WHERE nome='"+field1.getText()+"'");
			try {
				if(rs.next())
					Database.update("DELETE FROM alimenti where nome='"+field1.getText()+"'");
				else
					messaggio.setText("Impossibile rimuovere un alimento non presente nel database");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if(azioneSelezionata.getText().equals("Rimuovi sport")){
			ResultSet rs= Database.query("SELECT * FROM sport WHERE nome='"+field1.getText()+"'");
			try {
				if(rs.next())
					Database.update("DELETE FROM sport where nome='"+field2.getText()+"'");
				else
					messaggio.setText("Impossibile rimuovere uno sport non presente nel database");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if(azioneSelezionata.getText().equals("Cerca alimento")){
			ResultSet rs = null;
			if(!field1.getText().isEmpty() && !field2.getText().isEmpty())
				rs=Database.query("SELECT * FROM alimenti WHERE nome='"+field1.getText()+"' AND kcal_100g='"+field2.getText()+"'");
			if(field1.getText().isEmpty())
				rs=Database.query("SELECT * FROM alimenti WHERE kcal_100g='"+field2.getText()+"'");
			if(field2.getText().isEmpty())
					rs=Database.query("SELECT * FROM alimenti WHERE nome='"+field1.getText()+"'");
		
			try {
				if(!rs.next())
					messaggio.setText("Non sono presenti alimenti corrispondenti");
				else {
					searchScrollPane.setVisible(true);
					String s=rs.getString("nome")+", "+rs.getString("kcal_100g")+" kcal per 100 grammi\n";
					while(rs.next()){
						s=s+rs.getString("nome")+", "+rs.getString("kcal_100g")+" kcal per 100 grammi\n";
					}
					lista.setText(s);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		if(azioneSelezionata.getText().equals("Cerca sport")){
			ResultSet rs = null;
			if(!field1.getText().isEmpty() && !field2.getText().isEmpty())
				rs=Database.query("SELECT * FROM sport WHERE nome='"+field1.getText()+"' AND kcal_ora='"+field2.getText()+"'");
			if(field1.getText().isEmpty())
				rs=Database.query("SELECT * FROM sport WHERE kcal_ora='"+field2.getText()+"'");
			if(field2.getText().isEmpty())
				rs=Database.query("SELECT * FROM sport WHERE nome='"+field1.getText()+"'");
				
			try {
				if(!rs.next())
					messaggio.setText("Non sono presenti sport corrispondenti ai parametri di ricerca");
				else{
					searchScrollPane.setVisible(true);
					String s=rs.getString("nome")+", "+rs.getString("kcal_ora")+" kcal consumate in un'ora\n";
					while(rs.next()){
						s=s+rs.getString("nome")+", "+rs.getString("kcal_ora")+" kcal consumate in un'ora\n";
					}
					System.out.println(s);
					lista.setText(s);
				}	
					
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
    }
    
   
}
