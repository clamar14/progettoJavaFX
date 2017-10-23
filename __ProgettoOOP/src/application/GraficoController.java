package application;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

public class GraficoController implements Initializable {
		private String username;
		
	    @FXML
	    private Button indietro;

	    @FXML
	    private ComboBox<String> data;

	    @FXML
	    private Button grafico;
	    
	    @FXML
	    private PieChart pieChart;
	    
	    public GraficoController(){
	    	username = LoginController.getUser().getUserName(); 
	    }

	    @FXML
	    void grafico(ActionEvent event) {
	    	ObservableList<PieChart.Data> dataset = creaDataSet();
	    	pieChart.setData(dataset);
	    }

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

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			ResultSet rs = Database.query("SELECT data FROM Diario WHERE username='"+username+"'");
			try {
				while(rs.next()){
						data.getItems().add(rs.getString("data"));
				}
			} catch (SQLException e) {
					e.printStackTrace();
				}		
			
		}

		private ObservableList<PieChart.Data> creaDataSet(){
			ObservableList<PieChart.Data> dataset = FXCollections.observableArrayList();
			ResultSet rs = Database.query("SELECT * FROM Diario WHERE username='"+username+"' AND data='"+data.getValue()+"'");
			try {
				if(rs.next()){
					//	if(rs.getInt("kcal_colazione")!=0)
							dataset.add(new PieChart.Data("Prima Colazione", new Double(rs.getInt("kcal_colazione"))));
					//	if(rs.getInt("kcal_pranzo")!=0)
							dataset.add(new PieChart.Data("Pranzo", new Double(rs.getInt("kcal_pranzo"))));
					//	if(rs.getInt("kcal_cena")!=0)
							dataset.add(new PieChart.Data("Cena", new Double(rs.getInt("kcal_cena"))));
					//	if(rs.getInt("kcal_snack")!=0)
							dataset.add(new PieChart.Data("Snack", new Double(rs.getInt("kcal_snack"))));
				}
			} catch (SQLException e) {
					e.printStackTrace();
				}		
			return dataset;
		}

}