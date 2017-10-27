package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	public static Connection con = null;
	public static Statement st = null;
	
	/*costruttore: carica il driver e apre una connessione al DB*/
	public Database (){
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:database.db");
		}
		catch (ClassNotFoundException e){
			System.out.println("Errore nel caricamento del driver");
		}
		catch (SQLException e){
			System.out.println("Errore di connessione ad DB");	
		}
		
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			System.out.println("Errore nella creazione di uno Statement");
		}
		
	}
	
	public void testInsert() {
        try{
        	st.executeUpdate("CREATE TABLE IF NOT EXISTS Utente (username varchar(25) PRIMARY KEY, password varchar(30), "
        			+ "nome varchar(15), cognome varchar(20), sesso varchar(5), dataDiNascita date, peso int, "
        			+ "altezza int, attività varchar(30))"); 
        	
        	st.executeUpdate("CREATE TABLE IF NOT EXISTS alimenti (nome VARCHAR(50) PRIMARY KEY ,kcal_100g INTEGER)");
    		st.executeUpdate("CREATE TABLE IF NOT EXISTS sport (nome VARCHAR(50) PRIMARY KEY , kcal_ora INTEGER)");
    		
    		st.executeUpdate("CREATE TABLE IF NOT EXISTS Diario (username varchar (25) references Utente, data varchar(10), "
    				+ "kcal_colazione int default 0, kcal_pranzo int default 0,"
    				+ " kcal_cena int default 0, kcal_snack int default 0, kcal_sport int default 0, fabbisogno int,"
    				+ " PRIMARY KEY(username, data))");
        }
		catch (SQLException e){ 
			e.printStackTrace();
		}
	}
	
	/*metodo per l'interagire con il DB ed eseguire delle query*/
	public static ResultSet query(String qry){
		ResultSet rs = null;
	
		try {
			rs = st.executeQuery(qry);
		}
		catch (SQLException e){
			System.out.println("Errore nell'interazione con il DB (select)");	
		}
		return rs;		
	}
	
	/*metodo per l'interagire con il DB ed eseguire delle operazioni di INSERT, UPDATE or DELETE*/
	public static void update(String qry){
		try {
			st.executeUpdate(qry);
		}
		catch (SQLException e){
			System.out.println("Errore nell'interazione con il DB (upate)");	
		}
			
	}
	
	public void closeConnection() {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("Errore nella chiusura della connessione ad DB");
			}
	}


}
