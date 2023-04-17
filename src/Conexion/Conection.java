package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conection {
	
	public Connection conexion() throws ClassNotFoundException, SQLException{
		Class.forName("org.postgresql.Driver"); //Carga de driver
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/transporte_obrero", "postgres", "0000"); //conexi�n a la basa de datos
		return connection;
	}
}
