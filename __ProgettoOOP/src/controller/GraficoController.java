package controller;

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
import model.Database;
import model.Utente;
import view.TestApp;

/**
 * Classe che permette di controllare l'interfaccia del grafico
 *
 */
public class GraficoController implements Initializable {
		private Utente user;
		
	    @FXML
	    private Button indietro;

	    @FXML
	    private ComboBox<String> data;

	    @FXML
	    private Button grafico;
	    
	    @FXML
	    private PieChart pieChart;
	    
	    @FXML
	    private Label kcalAssunte;

	    @FXML
	    private Label kcalConsumate;

	    @FXML
	    private Label fabbisogno;

	    
	    
	    public GraficoController(Utente user){
	    	this.user = user; 
	    }

	    /**
	     * Metodo che viene invocato per creare il grafico
	     * @param event
	     */
	    @FXML
	    void grafico(ActionEvent event) {
	    	ObservableList<PieChart.Data> dataset = creaDataSet();
	    	pieChart.setData(dataset);
	    	kcalAssunte.setVisible(true);
	    	kcalConsumate.setVisible(true);
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
				ScrollPane registrazione = (ScrollPane) loader.load();
				Scene scene = new Scene(registrazione);
				TestApp.getStage().setScene(scene);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    }

	    /**
	     * Metodo che viene richiamato ad ogni apertura per permettere all'utente di selezionare la data
	     */
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			fabbisogno.setText(fabbisogno.getText()+" "+user.getFabbisogno()+" kcal");
			ResultSet rs = Database.query("SELECT data FROM Diario WHERE username='"+user.getUserName()+"'");
			try {
				while(rs.next()){		
					String date = rs.getString("data");
					String d = date.substring(8, 10)+"/"+date.substring(5, 7)+"/"+date.substring(0, 4);
					data.getItems().add(d);
				}
			} catch (SQLException e) {
					e.printStackTrace();
				}		
			
		}

		/**
		 * Metodo che viene utilizzato per creare il Dataset
		 * @return
		 */
		private ObservableList<PieChart.Data> creaDataSet(){
			pieChart.setAnimated(true);
			ObservableList<PieChart.Data> dataset = FXCollections.observableArrayList();
			String date = data.getValue();
			String d = date.substring(6, 10)+"/"+date.substring(3, 5)+"/"+date.substring(0, 2);
			ResultSet rs = Database.query("SELECT * FROM Diario WHERE username='"+user.getUserName()+"' AND data='"+d+"'");
			try {
				if(rs.next()){
					if(rs.getInt("kcal_colazione")!=0)
						dataset.add(new PieChart.Data("Prima Colazione", new Double(rs.getInt("kcal_colazione"))));
					if(rs.getInt("kcal_pranzo")!=0)
						dataset.add(new PieChart.Data("Pranzo", new Double(rs.getInt("kcal_pranzo"))));
					if(rs.getInt("kcal_cena")!=0)
						dataset.add(new PieChart.Data("Cena", new Double(rs.getInt("kcal_cena"))));
					if(rs.getInt("kcal_snack")!=0)
						dataset.add(new PieChart.Data("Snack", new Double(rs.getInt("kcal_snack"))));
					kcalAssunte.setText("Hai assunto "+(rs.getInt("kcal_colazione")+rs.getInt("kcal_pranzo")
											+rs.getInt("kcal_cena")+rs.getInt("kcal_snack"))+" kcal");
					kcalConsumate.setText("Hai consumato "+rs.getInt("kcal_sport")+" kcal");
				}
			} catch (SQLException e) {
					e.printStackTrace();
				}		
			return dataset;
		}

}