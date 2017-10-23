package application;

import java.sql.*;
import java.util.*;

public class Utente {
	
	private String username;
	private String sesso;
	private int et‡;
	private int altezza;
	private int peso;
	private String attivit‡;
	double laf;
	double mb;
	

	public Utente(String username){
		this.username=username;
		ResultSet rs = Database.query("SELECT * from Utente where username = '" +this.username+ "'");
		try {
			this.sesso=rs.getString("sesso");
			this.et‡=(Calendar.getInstance().get(Calendar.YEAR)-rs.getInt("annoDiNascita"));
			this.altezza=rs.getInt("altezza");
			this.peso=rs.getInt("peso");
			this.attivit‡=rs.getString("attivit‡");
			metabolismoBasale();
			livelloAttivit‡Fisica();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getUserName(){
		return username;
	}
	
	public String getSesso(){
		return sesso;
	}
	
	public int getEt‡(){
		return et‡;
	}
	
	public int getAltezza(){
		return altezza;
	}
	
	public int getPeso(){
		return peso;
	}
	
	public String getAttivit‡(){
		return attivit‡;
	}
	
	public double metabolismoBasale(){
		mb = 0;
		
		if(et‡<18){
			if(sesso.equals("donna")){
				mb=((peso*8.36)+(altezza*162)+370);
			}
			else
				mb=((peso*16.2)+(altezza*131)+416);
		}
		if(et‡>=18 && et‡<30){
			if(sesso.equals("donna")){
				mb=((peso*14.7)+496);
			}
			else
				mb=((peso*15.3)+679);
		}
		if(et‡>=30 && et‡<60){
			if(sesso.equals("donna")){
				mb=((peso*8.7)+829);
			}
			else
				mb=((peso*11.6)+879);
		}
		if(et‡>=60 && et‡<75){
			if(sesso.equals("donna")){
				mb=((peso*9.2)+688);
			}
			else
				mb=((peso*11.9)+700);
		}
		if(et‡>=75){
			if(sesso.equals("donna")){
				mb=((peso*9.8)+624);
			}
			else
				mb=((peso*8.4)+819);
		}
		return mb;
	}
	
	public double livelloAttivit‡Fisica(){
		laf = 0;
		if(et‡>=75){
			if(sesso.equals("donna")){
				laf = 1.37;
			}
			else
				laf = 1.33;
		}
		if(et‡>=60 && et‡<75){
			if(sesso.equals("donna")){
				laf = 1.44;
			}
			else
				laf = 1.40;
		}
		if(et‡<60){
			if(attivit‡.equals("Leggera") && sesso.equals("donna")){
				laf=1.42;
			}
			if(attivit‡.equals("Leggera") && sesso.equals("uomo")){
				laf=1.41;
			}
			if(attivit‡.equals("Moderata") && sesso.equals("donna")){
				laf=1.56;
			}
			if(attivit‡.equals("Moderata") && sesso.equals("uomo")){
				laf=1.70;
			}
			if(attivit‡.equals("Pesante") && sesso.equals("donna")){
				laf=1.73;
			}
			if(attivit‡.equals("Pesante") && sesso.equals("uomo")){
				laf=2.01;
			}
		}
		return laf;
	}
	
	public int getFabbisogno(){
		return (int)(mb*laf);
	}
	
}

