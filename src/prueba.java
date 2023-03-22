import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.compress.harmony.pack200.NewAttributeBands.Call;

import Desarrollo.Extras;
import Servicios_BD.ConnectionManage;

public class Prueba {

	public static void main(String[] args) throws Exception {
		ArrayList<String> listado = new ArrayList<>();
		listado.add("Rayner");
		ArrayList<String>listado_municipios_entidades = new ArrayList<String>();
		try (Connection con = ConnectionManage.getIntancia().getconection()){
			for(int contador = 0; contador< listado.size();contador++) {
				String consulta = "{?=call entidad_municipio_buscar(?)}";			
				CallableStatement prepa = con.prepareCall(consulta);
				prepa.registerOutParameter(1,java.sql.Types.VARCHAR);
				prepa.setString(2, listado.get(contador));
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
	}

}
