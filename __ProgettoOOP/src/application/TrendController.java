package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;

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
    	
    	
    	ResultSet rs = Database.query("SELECT * FROM Diario WHERE username='"+user.getUserName()+"'");
    	try {
			while (rs.next()){
				int sum = rs.getInt("kcal_colazione")+rs.getInt("kcal_pranzo")+rs.getInt("kcal_cena")+rs.getInt("kcal_snack");
				int consumato = rs.getInt("kcal_sport");
				series1.getData().add(new XYChart.Data<String, Integer>(rs.getString("data"), sum));
				series2.getData().add(new XYChart.Data<String, Integer>(rs.getString("data"), consumato));
				series3.getData().add(new XYChart.Data<String, Integer>(rs.getString("data"), rs.getInt("fabbisogno")));
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		} 
    		
    	lineChart.getData().add(series1);
    	lineChart.getData().add(series2);
    	lineChart.getData().add(series3);
	}
}