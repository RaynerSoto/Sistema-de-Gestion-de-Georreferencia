import java.sql.SQLException;

import javax.swing.JOptionPane;

import Interfaz.Cargar_archivo;

public class Codigos_prueba {
	/* Codigo de extraer los datos del Excel poco optimizado
	if(hojas.get(0).getSheetName().toLowerCase().trim().equalsIgnoreCase("entidad") || hojas.get(0).getSheetName().toLowerCase().trim().equalsIgnoreCase("entidades") || hojas.get(0).getSheetName().toLowerCase().trim().equalsIgnoreCase("trabajos") || hojas.get(0).getSheetName().toLowerCase().trim().equalsIgnoreCase("Centros de trabajos") || hojas.get(0).getSheetName().toLowerCase().trim().equalsIgnoreCase("centrodetrabajo") || hojas.get(0).getSheetName().toLowerCase().trim().equalsIgnoreCase("unidades") || hojas.get(0).getSheetName().toLowerCase().trim().equalsIgnoreCase("centro") || hojas.get(0).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugardetrabajo") || hojas.get(0).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugaresdetrabajo") || hojas.get(0).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugaresdetrabajos")){
		int cantidad_filas = hojas.get(0).getLastRowNum()+1;
		int cantida_columnas;
		try {
			cantida_columnas = hojas.get(0).getRow(0).getLastCellNum();
		} catch (Exception e2) {
			cantida_columnas = 0;
		}
		if(cantida_columnas == 0 || cantidad_filas == 0) {
			JOptionPane.showMessageDialog(Cargar_archivo.this,"La hoja: " +hojas.get(0).getSheetName()+ " está vacia revisar para cargar. Sino se arregla no se puede conseguir","Error Crítico",JOptionPane.ERROR_MESSAGE);
		}
		else {
			ingresar_entidades(hojas.get(0),0, cantidad_filas, cantida_columnas);
			try {
				llenar_entidad();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			cantidad_filas = hojas.get(1).getLastRowNum()+1;
			try {
				cantida_columnas = hojas.get(1).getRow(0).getLastCellNum();
			} catch (Exception e2) {
				cantida_columnas = 0;
			}
			if(cantida_columnas == 0 || cantidad_filas == 0) {
				JOptionPane.showMessageDialog(Cargar_archivo.this,"La hoja: " +hojas.get(1).getSheetName()+ " está vacia revisar para cargar.Cargados solamente los centros laborales","Error",JOptionPane.ERROR_MESSAGE);
			}
			else {
				ingresar_personas(hojas.get(1),1,cantidad_filas,cantida_columnas);
				JOptionPane.showMessageDialog(Cargar_archivo.this,"Archivos cargados","Correcto",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	else if(hojas.get(1).getSheetName().toLowerCase().trim().equalsIgnoreCase("entidad") || hojas.get(1).getSheetName().toLowerCase().trim().equalsIgnoreCase("entidades") || hojas.get(1).getSheetName().toLowerCase().trim().equalsIgnoreCase("trabajos") || hojas.get(1).getSheetName().toLowerCase().trim().equalsIgnoreCase("Centros de trabajos") || hojas.get(1).getSheetName().toLowerCase().trim().equalsIgnoreCase("centrodetrabajo") || hojas.get(1).getSheetName().toLowerCase().trim().equalsIgnoreCase("unidades") || hojas.get(1).getSheetName().toLowerCase().trim().equalsIgnoreCase("centro") || hojas.get(1).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugardetrabajo") || hojas.get(1).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugaresdetrabajo") || hojas.get(1).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugaresdetrabajos")){
		int cantidad_filas = hojas.get(1).getLastRowNum();
		int cantida_columnas;
		try {
			cantida_columnas = hojas.get(1).getRow(0).getLastCellNum();
		} catch (Exception e2) {
			cantida_columnas = 0;
		}
		if(cantida_columnas == 0 || cantidad_filas == 0) {
			JOptionPane.showMessageDialog(Cargar_archivo.this,"La hoja: " +hojas.get(1).getSheetName()+ " está vacia revisar para cargar","Error Crítico",JOptionPane.ERROR_MESSAGE);
		}
		else {
			ingresar_entidades(hojas.get(1),1, cantidad_filas, cantida_columnas);
			try {
				llenar_entidad();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cantidad_filas = hojas.get(0).getLastRowNum();
			try {
				cantida_columnas = hojas.get(0).getRow(0).getLastCellNum();
			} catch (Exception e2) {
				cantida_columnas = 0;
			}
			if(cantida_columnas == 0 || cantidad_filas == 0) {
				JOptionPane.showMessageDialog(Cargar_archivo.this,"La hoja: " +hojas.get(0).getSheetName()+ " está vacia revisar para cargar.Cargados solamente los centros laborales","Error",JOptionPane.ERROR_MESSAGE);
			}
			else {
				ingresar_personas(hojas.get(0),0,cantidad_filas,cantida_columnas);
			}
		}
	}
	
	
	Codigo de llenar la BD
	public void llenar_entidad() throws ClassNotFoundException, SQLException {
	int cantidad = Transporte.getInstance().getListado_entidades().size();
	for(int contador = 0; contador<cantidad;contador++) {
		try {
			Entidad_BD enti = new Entidad_BD();
			enti.insertar_entidad(Transporte.getInstance().getListado_entidades().get(contador));
		} catch (Exception e2) {
			
		}
	}
}*/
}
