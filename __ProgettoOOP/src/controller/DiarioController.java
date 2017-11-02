package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import model.Database;
import model.Utente;
import view.TestApp;

/**
 * Classe che permette di controllare l'interfaccia diario
 *
 */
public class DiarioController implements Initializable {
	private Utente user;
	
	private boolean diarioRegistrato;
	
	private String day;
	
	@FXML
    private Button homepage;

    @FXML
    private Button colazione;

    @FXML
    private Button pranzo;

    @FXML
    private Button cena;

    @FXML
    private Button snack;

    @FXML
    private TextField alimento;

    @FXML
    private TextField grammiAlimento;

    @FXML
    private TextField sport;

    @FXML
    private TextField oreSport;

    @FXML
    private Label kcalColazione;

    @FXML
    private Label kcalPranzo;

    @FXML
    private Label kcalCena;

    @FXML
    private Label kcalSnack;

    @FXML
    private Label kcalConsumate;

    @FXML
    private Label totale;

    @FXML
    private Label fabbisogno;

    @FXML
    private Button aggiungiSport;
    
    @FXML
    private Label kcalAlimentoCalcolate;

    @FXML
    private Label kcalSportCalcolate;

    @FXML
    private Label messaggio;
    
    @FXML
    private Label messaggio2;
    
    public DiarioController(Utente user){
    	this.user=user;
    }
    
    
    /**
     * Metodo con cui si aggiungono gli alimenti mangiati per colazione
     * @param event
     */
    @FXML
    void addBreakfast(ActionEvent event) {
    	if (calcola())
    	kcalColazione.setText(String.valueOf(Integer.parseInt(kcalColazione.getText())+Integer.parseInt(kcalAlimentoCalcolate.getText())));
    }

    /**
     * Metodo con cui si aggiungono gli alimenti mangiati per cena
     * @param event
     */
    @FXML
    void addDinner(ActionEvent event) {
    	if (calcola())
    		kcalCena.setText(String.valueOf(Integer.parseInt(kcalCena.getText())+Integer.parseInt(kcalAlimentoCalcolate.getText())));
    }

    /**
     * Metodo con cui si aggiungono gli alimenti mangiati per pranzo
     * @param event
     */
    @FXML
    void addLunch(ActionEvent event) {
    	if(calcola())
    		kcalPranzo.setText(String.valueOf(Integer.parseInt(kcalPranzo.getText())+Integer.parseInt(kcalAlimentoCalcolate.getText())));
    }

    /**
     * Metodo con cui si aggiungono gli alimenti mangiati per gli spuntini
     * @param event
     */
    @FXML
    void addSnack(ActionEvent event) {
    	if(calcola())
    		kcalSnack.setText(String.valueOf(Integer.parseInt(kcalAlimentoCalcolate.getText())));
    }

    /**
     * Metodo con cui si aggiungono gli sport fatti durante la giornata e calcola le calorie di ogni sport introdotto
     * @param event
     */
    @FXML
    void addSport(ActionEvent event) {
    	ResultSet rs = Database.query("SELECT kcal_ora FROM sport WHERE nome='" +sport.getText()+"'");
		try {
			if(rs.next()){
				int kcalParziale = rs.getInt("kcal_ora");
				int quantità = Integer.parseInt(oreSport.getText());
				kcalSportCalcolate.setText(String.valueOf(kcalParziale*quantità));
				kcalConsumate.setText(String.valueOf(Integer.parseInt(kcalConsumate.getText())+Integer.parseInt(kcalSportCalcolate.getText())));
				controlla();
			}
			else{
				kcalSportCalcolate.setText("Sport non disponibile");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		kcalSportCalcolate.setVisible(true);
    }

    /**
     * Metodo che aggiorna il database e permette di tornare all'homepage
     * @param event
     */
    @FXML
    void homepage(ActionEvent event) {
    	if(!diarioRegistrato){
    		if(Integer.parseInt(totale.getText())!=0 || Integer.parseInt(kcalConsumate.getText())!=0){
    			Database.update("INSERT INTO Diario (username, data) VALUES ('"+user.getUserName()+"','"+day+"')");
    			diarioRegistrato=true;
    		}
    	}
    	if(diarioRegistrato)
    		Database.update("UPDATE Diario "
				+"SET kcal_colazione='"+kcalColazione.getText()
				+"', kcal_pranzo='"+kcalPranzo.getText()
				+"', kcal_cena='"+kcalCena.getText()
				+"', kcal_snack='"+kcalSnack.getText()
				+"', kcal_sport='"+kcalConsumate.getText()
				+"', fabbisogno='"+user.getFabbisogno()
				+"' WHERE username='"+user.getUserName()+"' AND data='"+day+"'");    	
    	
    	try {
       		HomePageController controller = new HomePageController(user);
			FXMLLoader loader = new FXMLLoader(TestApp.class.getResource("HomePage.fxml"));
			loader.setController(controller);
			ScrollPane registrazione = (ScrollPane) loader.load();
			Scene scene = new Scene(registrazione);
			TestApp.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    /**
     * Metodo che calcola le calorie per ogni alimento introdotto e aggiorna il totale
     * @return
     */
    private boolean calcola(){
    	boolean retVal = false;
    	ResultSet rs = Database.query("SELECT kcal_100g FROM alimenti WHERE nome='" +alimento.getText()+"'");
		try {
			if(rs.next()){
				int kcalParziale = rs.getInt("kcal_100g");
				int quantità = Integer.parseInt(grammiAlimento.getText());
				kcalAlimentoCalcolate.setText(String.valueOf(kcalParziale*quantità));
				totale.setText(String.valueOf(Integer.parseInt(totale.getText())+Integer.parseInt(kcalAlimentoCalcolate.getText())));
				controlla();
				retVal=true;
			}
			else{
				kcalAlimentoCalcolate.setText("Alimento non disponibile");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		kcalAlimentoCalcolate.setVisible(true);
		return retVal;
    }

    /**
     * Metodo che confronta le calorie introdotte durante il giorno e il fabbisogno energetico 
     */
    private void controlla(){
    	int n = Integer.parseInt(fabbisogno.getText())+Integer.parseInt(kcalConsumate.getText());
    	int tot = Integer.parseInt(totale.getText());
    	if(tot>n){
    		messaggio.setVisible(true);
    		if((tot-n)>=180)
    		try{
    			ResultSet rs=Database.query("SELECT * FROM sport ORDER BY RANDOM() LIMIT 1");
    			while(rs.next()){
    				if(rs.getInt("kcal_ora")<=(tot-n)){
    					int ore = (int) (tot-n)/rs.getInt("kcal_ora");
    					messaggio2.setText("Potresti fare\n"+String.valueOf(ore)+"h di "+rs.getString("nome"));
    					messaggio2.setVisible(true);
    					break;
    				}	
    			}
       		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    	else{
    		messaggio.setVisible(false);
    		messaggio2.setVisible(false);
    	}
    }
    
    /**
     * Metodo che viene richiamato a ogni apertura dell'interfaccia che permette di settare le calorie introdotte 
     * e consumate durante i giorno
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Date dt=new Date();
		DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
		day = dateFormat.format(dt);
		diarioRegistrato=false;
		
		fabbisogno.setText(String.valueOf(user.getFabbisogno()));
		ResultSet rs = Database.query("SELECT * FROM Diario WHERE username='"+user.getUserName()+"'AND data='"+day+"'");
		try {
			if(rs.next()){
					diarioRegistrato=true;
					kcalColazione.setText(String.valueOf(rs.getInt("kcal_colazione")));
					kcalPranzo.setText(String.valueOf(rs.getInt("kcal_pranzo")));
					kcalCena.setText(String.valueOf(rs.getInt("kcal_cena")));
					kcalSnack.setText(String.valueOf(rs.getInt("kcal_snack")));
					kcalConsumate.setText(String.valueOf(rs.getInt("kcal_sport")));
					int sum=Integer.parseInt(kcalColazione.getText())+Integer.parseInt(kcalPranzo.getText())+Integer.parseInt(kcalCena.getText())+Integer.parseInt(kcalSnack.getText());
					totale.setText(String.valueOf(sum));
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		controlla();
	}
}
