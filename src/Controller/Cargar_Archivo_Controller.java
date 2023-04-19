package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.WindowConstants;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Conexion.ConnectionManage;
import Desarrollo.Entidad;
import Desarrollo.Errores;
import Desarrollo.Persona;
import Desarrollo.Transporte;
import Interfaz.Cargar_archivo;
import Interfaz.Listado_errores;
import ServicesIntern.EntidadServices;
import ServicesIntern.FileServices;
import ServicesIntern.PersonaServices;
import ServiciosBD.EntidadServicesBD;
import ServiciosBD.PersonaServicesBD;
import Validator.Validar_General;

public class Cargar_Archivo_Controller {
	
	
	//Cargar el Excel
	public void proceso_cargar_guardar_Excel(Object object) throws Exception {
			try (Workbook libro = new FileServices().construccion_libro(object);){
				ArrayList<Sheet>hojas = new FileServices().listado_hojas(libro);
				ArrayList<Entidad>entidads = new ArrayList<>();
				ArrayList<Persona>personas = new ArrayList<>();
				if(hojas.size() == 2) {
					for(int contador = 0; contador < hojas.size();contador++) {
						int cantidad_filas = hojas.get(contador).getLastRowNum()+1;
						int cantida_columnas;
						try {
							cantida_columnas = hojas.get(contador).getRow(0).getLastCellNum();
						} catch (Exception e2) {
							cantida_columnas = 0;
						}
						if(cantidad_filas != 0 && cantida_columnas!=0) {
							if(hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("entidad") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("entidades") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("trabajos") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("Centros de trabajos") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("centrodetrabajo") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("unidades") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("centro") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugardetrabajo") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugaresdetrabajo") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugaresdetrabajos")){
								entidads = new EntidadServices().extraer_entidades(hojas.get(contador),contador);
							}
							else if(hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("personas") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("personal") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("trabajadores") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("trabajador") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("empleados") ||hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("persona")) {
								personas = new PersonaServices().extraer_personas(hojas.get(contador),contador);
							}
						}
					}
					if(entidads.size() != 0) {
						Transporte.getInstance().getListado_errores().addAll(new EntidadServices().almacenar_entidad(entidads));
;						if(Transporte.getInstance().getListado_personas().size() != 0) {
							Transporte.getInstance().getListado_errores().addAll(new PersonaServices().almacenar_personas(personas));
							if(Transporte.getInstance().getListado_errores().size() == 0) {
								throw new Exception("Datos cargados con éxito");
							}
							else {
								throw new Exception("Datos cargados con errores");
							}
						}
						else {
							throw new Exception("La hoja del personal se encuentra vacía. No se pudo cargar");
						}
					}
					else {
						throw new Exception("La hoja de los centros de trabajo está vacía, no se puede proceder. Revise");
					}
				}
				else {
					throw new Exception("Cantidad de hojas no aplicable");
				}
			}
		}
}
