package model;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * Questa classe rappresenta l'utente che ha effettuato il login e contiene tutte le sue informazioni 
 *
 */
public class Utente {
	
	private String username;
	private String sesso;
	private int et�;
	private int altezza;
	private int peso;
	private String attivit�;
    
	/**
	 * Costruttore che serve per inserire le informazione nell'utente
	 * @param username
	 */
	public Utente(String username){
		this.username=username;
		ResultSet rs = Database.query("SELECT * from Utente where username = '" +this.username+ "'");
		try {			
			this.sesso=rs.getString("sesso");
			this.et�=(Calendar.getInstance().get(Calendar.YEAR)-calcolaAnno(rs.getString("dataDiNascita")));
			this.altezza=rs.getInt("altezza");
			this.peso=rs.getInt("peso");
			this.attivit�=rs.getString("attivit�");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e){
			e.printStackTrace();
		}
		
	}
	
	public String getUserName(){
		return username;
	}
	
	public String getSesso(){
		return sesso;
	}
	
	public int getEt�(){
		return et�;
	}
	
	public int getAltezza(){
		return altezza;
	}
	
	public int getPeso(){
		return peso;
	}
	
	public String getAttivit�(){
		return attivit�;
	}
	
	
	public void setSesso(String sesso){
		this.sesso=sesso;
	}
	
	public void setEt�(LocalDate data){
		this.et�=(Calendar.getInstance().get(Calendar.YEAR)-data.getYear());
	}
	
	public void setAltezza(int altezza){
		this.altezza=altezza;
	}
	
	public void setPeso(int peso){
		this.peso=peso;
	}

	public void setAttivit�(String attivit�){
		this.attivit�=attivit�;
	}
	
	/**
	 * Questo metodo calcola il metabolismo basale di ogni utente 
	 * @return double
	 */
	public double metabolismoBasale(){
		double mb = 0;
		
		if(et�<18){
			if(sesso.equals("donna")){
				mb=((peso*8.36)+(altezza*162)+370);
			}
			else
				mb=((peso*16.2)+(altezza*131)+416);
		}
		if(et�>=18 && et�<30){
			if(sesso.equals("donna")){
				mb=((peso*14.7)+496);
			}
			else
				mb=((peso*15.3)+679);
		}
		if(et�>=30 && et�<60){
			if(sesso.equals("donna")){
				mb=((peso*8.7)+829);
			}
			else
				mb=((peso*11.6)+879);
		}
		if(et�>=60 && et�<75){
			if(sesso.equals("donna")){
				mb=((peso*9.2)+688);
			}
			else
				mb=((peso*11.9)+700);
		}
		if(et�>=75){
			if(sesso.equals("donna")){
				mb=((peso*9.8)+624);
			}
			else
				mb=((peso*8.4)+819);
		}
		return mb;
	}
	
	/**
	 * Questo metodo calcola il livello di attivit� fisica di ogni utente 
	 * @return double
	 */
	public double livelloAttivit�Fisica(){
		double laf = 0;
		if(et�>=75){
			if(sesso.equals("donna")){
				laf = 1.37;
			}
			else
				laf = 1.33;
		}
		if(et�>=60 && et�<75){
			if(sesso.equals("donna")){
				laf = 1.44;
			}
			else
				laf = 1.40;
		}
		if(et�<60){
			if(attivit�.equals("Leggera") && sesso.equals("donna")){
				laf=1.42;
			}
			if(attivit�.equals("Leggera") && sesso.equals("uomo")){
				laf=1.41;
			}
			if(attivit�.equals("Moderata") && sesso.equals("donna")){
				laf=1.56;
			}
			if(attivit�.equals("Moderata") && sesso.equals("uomo")){
				laf=1.70;
			}
			if(attivit�.equals("Pesante") && sesso.equals("donna")){
				laf=1.73;
			}
			if(attivit�.equals("Pesante") && sesso.equals("uomo")){
				laf=2.01;
			}
		}
		return laf;
	}
	
	/**
	 * Questo metodo calcola il fabbisogno energetico di una persona
	 * @return int
	 */
	public int getFabbisogno(){
		return (int)(metabolismoBasale()*livelloAttivit�Fisica());
	}
    
	/**
	 * Questo metodo calcola gli anni di ogni utente registrato
	 * @param data
	 * @return int
	 * @throws ParseException
	 */
	private int calcolaAnno(String data) throws ParseException{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = df.parse(data);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}
}

