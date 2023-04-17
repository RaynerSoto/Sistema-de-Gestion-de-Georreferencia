package ServiciosBD;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import Conexion.Conection;
import Conexion.ConnectionManage;
import Desarrollo.Persona;

public class PersonaServicesBD {
	public void insertar_persona(Persona p) throws ClassNotFoundException, SQLException,Exception{
		try (Connection con = new Conection().conexion()){
			String consulta = "SELECT insertar_persona(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement prepa = con.prepareStatement(consulta);
			prepa.setString(1, p.getEntidad());
			prepa.setString(2, p.getNombre());
			prepa.setString(3, p.getCI());
			prepa.setString(4, p.getMunicipio());
			prepa.setString(5, p.getProvincia());
			prepa.setString(6, p.getDireccion());
			prepa.setString(7, p.getCalle());
			prepa.setString(8, p.getEntrecalle1());
			prepa.setString(9, p.getEntrecalle2());
			prepa.setString(10, p.getNumero());
			prepa.setString(11, p.getLocalidad());
			prepa.setString(12, p.getDatos());
			prepa.execute();
			prepa.close();
		}
	}
	
	public void eliminar_persona() throws ClassNotFoundException, SQLException, Exception{
		try (Connection con =new Conection().conexion()){
			String consulta = "{call eliminar_personas()}";
			CallableStatement prepa = con.prepareCall(consulta);
			prepa.execute();
			prepa.close();
		} 
	}
	
	public ArrayList<String> listado_personas_municipioXentidad(String entidad) throws ClassNotFoundException, SQLException{
		ArrayList<String>persona_municipio = new ArrayList<String>();
		try (Connection con = new Conection().conexion()){
			String consulta = "{?=call entidad_persona_municipioXEntidad(?)}";
			con.setAutoCommit(false);
			CallableStatement prepa = con.prepareCall(consulta);
			prepa.registerOutParameter(1,java.sql.Types.OTHER);
			prepa.setString(2, entidad);
			prepa.execute();
			ResultSet e = (ResultSet) prepa.getObject(1);
			while(e.next()) {
				persona_municipio.add(e.getString(1));
			}
			e.close();
			prepa.close();
		}
		return persona_municipio;
	}
}
