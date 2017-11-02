package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import model.Database;
import model.Utente;
import view.TestApp;

/**
 * Classe che permette di controllare l'interfaccia del grafico in cui si confrontano i diversi giorni della settimana
 * in base alle calorie assunte e consumate
 *
 */
public class TrendController implements Initializable{
	private Utente user;
	
	@FXML
    private Button indietro;

    @FXML
    private LineChart<String, Integer> lineChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    public TrendController(Utente user){
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
			FXMLLoader loader = new FXMLLoader(TestApp.class.getResource("HomePage.fxml"));
			loader.setController(controller);
			ScrollPane homepage = (ScrollPane) loader.load();
			Scene scene = new Scene(homepage);
			TestApp.getStage().setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    /**
     * Metodo con cui si può visualizzare il grafico che confronta i vari giorni della settimana
     *  in base alle calorie assunte e quelle consumate
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		x.setLabel("Data");
		y.setLabel("Calorie");
		
    	XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();
    	series1.setName("Calorie assunte");
    	XYChart.Series<String, Integer> series2 = new XYChart.Series<String, Integer>();
    	series2.setName("Calorie consumate");
    	XYChart.Series<String, Integer> series3 = new XYChart.Series<String, Integer>();
    	series3.setName("Fabbisogno");
    	
    	
    	ResultSet rs = Database.query("SELECT * FROM Diario WHERE username='"+user.getUserName()+"' ORDER BY data");
    	try {
			while (rs.next()){
				int sum = rs.getInt("kcal_colazione")+rs.getInt("kcal_pranzo")+rs.getInt("kcal_cena")+rs.getInt("kcal_snack");
				int consumato = rs.getInt("kcal_sport");
				String date = rs.getString("data");
			    String data = date.substring(8, 10)+"/"+date.substring(5, 7)+"/"+date.substring(0, 4);
				series1.getData().add(new XYChart.Data<String, Integer>(data, sum));
				series2.getData().add(new XYChart.Data<String, Integer>(data, consumato));
				series3.getData().add(new XYChart.Data<String, Integer>(data, rs.getInt("fabbisogno")));				
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		
    		
    	lineChart.getData().add(series1);
    	lineChart.getData().add(series2);
    	lineChart.getData().add(series3);
	}
}