package Servicios;

import java.sql.Connection;
import java.util.logging.*;
import java.util.logging.*;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectionManage {
	private static ConnectionManage singlenton = null;
	private static Connection connection;
	
	public static ConnectionManage getIntancia() throws ClassNotFoundException, SQLException {
		 if(singlenton == null) 
			 singlenton =  new ConnectionManage();
		 return singlenton;
	}
	
	public ConnectionManage() throws SQLException, ClassNotFoundException {
		 Class.forName("org.postgresql.Driver"); //Carga de driver
		 connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/transporte_obrero", "postgres", "0000"); //conexi�n a la basa de datos
	 }
	
	 //Acceso a la conexi�n
	 public static Connection getconection() throws SQLException{
		return connection;
	 }
}
