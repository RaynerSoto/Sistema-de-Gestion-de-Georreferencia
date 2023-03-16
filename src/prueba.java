import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Desarrollo.Extras;
import Servicios_BD.ConnectionManage;
import Servicios_BD.Persona_BD;

public class prueba {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ArrayList<String>listado_municipios_entidades = new ArrayList<String>();
		String consulta = "SELECT municipio FROM public.entidad Where entidad.centro_trabajo Like ?";
		PreparedStatement prepa = ConnectionManage.getIntancia().getconection().prepareStatement(consulta);
		prepa.setString(1,"CUJAE");
		ResultSet resultado = prepa.executeQuery();
		while(resultado.next()) {
			listado_municipios_entidades.add(resultado.getString(1));
		}
		JOptionPane.showMessageDialog(null, listado_municipios_entidades.get(0), consulta, JOptionPane.ERROR_MESSAGE);
	}
	
	public static String validar_carnet(String ci_convertir) {
		String valor = "";
		if(ci_convertir.length() == 11) {
			return ci_convertir;
		}
		else if(ci_convertir.length() < 11) {
			int cantidad = 11-ci_convertir.length();
			if(cantidad == 1) {
				ci_convertir = "0"+ci_convertir;
			}
			else if(cantidad == 2) {
				ci_convertir = "0"+"0" + ci_convertir;
			}
			else if(cantidad >=3) {
				ci_convertir = "0"+"0"+"0"+ci_convertir;
			}
		}
		else if(ci_convertir.length() >11) {
			for(int contador = 0; contador<ci_convertir.length();contador++){
				Character caracter = ci_convertir.charAt(contador);
				if(caracter.isDigit(caracter) == true) {
					if(valor == "") {
						valor = caracter.toString();
					}
					else {
						valor = valor + caracter.toString();
					}
				}
			return valor;
		}
		return ci_convertir;
	}
		return valor;
	}
}
