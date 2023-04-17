package ServiciosBD;

import org.apache.poi.ss.usermodel.Sheet;

import Conexion.Conection;
import Conexion.ConnectionManage;

import java.sql.*;
import java.util.ArrayList;

import Desarrollo.Entidad;
import Desarrollo.Errores;
import Desarrollo.Extras;
import Desarrollo.Transporte;
import Validator.Validar_General;

public class EntidadServicesBD {
	public void insertar_entidad(Entidad e) throws ClassNotFoundException, SQLException,Exception {
		try (Connection con = new Conection().conexion()){
		    String consulta = "Select insertar_entidad(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		    PreparedStatement prepa = con.prepareStatement(consulta);
			prepa.setString(1, e.getNombre());
			prepa.setString(2, e.getEntidad());
			prepa.setString(3, e.getMunicipio());
			prepa.setString(4, e.getProvincia());
			prepa.setString(5, e.getDireccion());
			prepa.setString(6, e.getCalle());
			prepa.setString(7, e.getEntrecalle1());
			prepa.setString(8, e.getEntrecalle2());
			prepa.setString(9, e.getNumero());
			prepa.setString(10, e.getLocalidad());
			prepa.setString(11, e.getHorario_actual_entrada());
			prepa.setString(12, e.getHorario_actual_salida());
			prepa.setString(13, e.getHorario_propuesto_entrada());
			prepa.setString(14, e.getHorario_propuesto_salida());
			prepa.setString(15, e.getDatos());
			prepa.execute();
			prepa.close();
		}
	}
	
	public void eliminar_entidades() throws ClassNotFoundException, SQLException, Exception {
		try (Connection con = new Conection().conexion()){
			String consulta = "{call eliminar_entidades()}";
			CallableStatement prepa = con.prepareCall(consulta);
			prepa.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("No se pudo eliminar las entidades");
		}
	}
	
	//Probar luego
	public ArrayList<String> listado_entidades_nombre() throws SQLException, ClassNotFoundException,Exception{
		ArrayList<String>listado_entidades_nombre = new ArrayList<String>();
		try (Connection con = new Conection().conexion()){
			String consulta = "{?=call entidad_listado_nombre()}";
			con.setAutoCommit(false);
			CallableStatement prepa = con.prepareCall(consulta);
			prepa.registerOutParameter(1,java.sql.Types.REF_CURSOR);
			prepa.execute();
			ResultSet resultado = (ResultSet) prepa.getObject(1);
			while(resultado.next()) {
				listado_entidades_nombre.add(resultado.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("No se pudo cargar el listado de nombre de la entidades");
		}
		return listado_entidades_nombre;
	}
	
	public ArrayList<String> listado_municipios_entidades(ArrayList<String>listado) throws ClassNotFoundException, SQLException{
		ArrayList<String>listado_municipios_entidades = new ArrayList<String>();
		try (Connection con = new Conection().conexion()){
			for(int contador = 0; contador< listado.size();contador++) {
				String consulta = "{call entidad_municipio_buscar(?)}";			
				CallableStatement prepa = con.prepareCall(consulta);
				prepa.setString(1, Extras.getInstance().getListado_filtroArrayList().get(contador));
				ResultSet resultado = prepa.executeQuery();
				while(resultado.next()) {
					boolean verdad = false;
					String valor = resultado.getString(1);
					for(int contador2 = 0; contador2<listado_municipios_entidades.size() && verdad == false;contador2++) {
						if(valor.equals(listado_municipios_entidades.get(contador2)) == true) {
							verdad = true;
						}
					}
					if(verdad == false) {
						listado_municipios_entidades.add(valor);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listado_municipios_entidades;
	}
	
	public long calculo(String origen,String destino, String entidad) throws ClassNotFoundException, SQLException {
		long valor = 0;
		try (Connection con = new Conection().conexion()){
			String consulta = "{call entidad_calculo_origen_destino(?,?,?)}";
			CallableStatement prepa = con.prepareCall(consulta);
			prepa.setString(1, entidad);
			prepa.setString(2, origen);
			prepa.setString(3, destino);
			ResultSet resultado = prepa.executeQuery();
			while(resultado.next()) {
				valor = valor+resultado.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valor;
	}
}
