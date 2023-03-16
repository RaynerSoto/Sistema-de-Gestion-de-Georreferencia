package Servicios_BD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import Desarrollo.Persona;

public class Persona_BD {
	public void insertar_persona(Persona p) throws ClassNotFoundException, SQLException {
		String consulta = "SELECT insertar_persona(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement prepa = ConnectionManage.getIntancia().getconection().prepareStatement(consulta);
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
	}
	
	public void eliminar_persona() throws ClassNotFoundException, SQLException {
		String consulta = "Select eliminar_personas()";
		PreparedStatement prepa = ConnectionManage.getIntancia().getconection().prepareStatement(consulta);
		prepa.execute();
	}
	
	public ArrayList<String> listado_personas_municipioXentidad(String entidad) throws ClassNotFoundException, SQLException{
		ArrayList<String>persona_municipio = new ArrayList<String>();
		String consulta = "Select distinct persona.municipio From persona Inner Join entidad on entidad.centro_trabajo Like persona.idsede Where entidad.centro_trabajo Like ?";
		PreparedStatement prepa = ConnectionManage.getIntancia().getconection().prepareStatement(consulta);
		prepa.setString(1, entidad);
		ResultSet e = prepa.executeQuery();
		while(e.next()) {
			persona_municipio.add(e.getString(1));
		}
		return persona_municipio;
	}
}
