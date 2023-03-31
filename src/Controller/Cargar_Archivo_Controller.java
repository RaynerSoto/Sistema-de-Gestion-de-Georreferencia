package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Desarrollo.Errores;
import Desarrollo.Transporte;
import Interfaz.Cargar_archivo;
import Interfaz.Listado_errores;
import Servicios.ConnectionManage;
import Servicios.FileServices;
import Servicios.PersonaServices;

public class Cargar_Archivo_Controller {
	
	//Cargar el Excel
	public void proceso_cargar_guardar_Excel() {
		FileServices fileServices = new FileServices();
			try {
				Workbook libro = fileServices.creacion_libro(direccion.getText());
				ArrayList<Sheet>hojas = fileServices.listado_hojas(libro);
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
								ingresar_entidades(hojas.get(contador),contador, cantidad_filas, cantida_columnas);
							}
							else if(hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("personas") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("personal") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("trabajadores") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("trabajador") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("empleados") ||hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("persona")) {
								ingresar_personas(hojas.get(contador),contador, cantidad_filas, cantida_columnas);
							}
						}
					}
					if(Transporte.getInstance().getListado_entidades().size() != 0) {
						llenar_entidad();
						if(Transporte.getInstance().getListado_personas().size() != 0) {
							llenar_personas();
							if(Transporte.getInstance().getListado_errores().size() == 0) {
								JOptionPane.showMessageDialog(Cargar_archivo.this,"Datos cargados con éxito","Correcto",JOptionPane.INFORMATION_MESSAGE);
								dispose();
							}
							else {
								JOptionPane.showMessageDialog(Cargar_archivo.this,"Datos cargados con errores","Correcto",JOptionPane.INFORMATION_MESSAGE);
								Listado_errores erro = new Listado_errores();
								dispose();
								erro.setVisible(true);
							}
						}
						else {
							JOptionPane.showMessageDialog(Cargar_archivo.this,"La hoja del personal se encuentra vacía. No se pudo cargar","Error",JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(Cargar_archivo.this,"La hoja de los centros de trabajo está vacía, no se puede proceder. Revise","Error crítico",JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(Cargar_archivo.this,"Cantidad de hojas no aplicable","Error",JOptionPane.ERROR_MESSAGE);
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(Cargar_archivo.this,"Archivo no admitido","Error",JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	
	
	
	//Esta función procesa los datos que ya obtuvimos del Excel y enviarlos a la base de datos,obteniendo los errores ocurridos
	public ArrayList<Errores> cargar_datos_excel() throws Exception {
		ArrayList<Errores>listado_errores = new ArrayList<>();
		boolean verdad = false;
		for(int contador = 0;verdad == false;) {
			PersonaServices perso = new PersonaServices();
			try {
				if(Transporte.getInstance().getListado_personas().get(contador).getEntidad().equals("") == true || Transporte.getInstance().getListado_personas().get(contador).getNombre().equals("") == true || Transporte.getInstance().getListado_personas().get(contador).getCI().equals("") == true || Transporte.getInstance().getListado_personas().get(contador).getDireccion().equals("") == true || Transporte.getInstance().getListado_personas().get(contador).getCI().length() != 11) {
					String causa = "Valores necesarios no ingresados o erroneos";
					Errores erro = new Errores(Transporte.getInstance().getListado_personas().get(contador), causa);
					listado_errores.add(erro);
				}
				else {
					perso.insertar_persona(Transporte.getInstance().getListado_personas().get(contador));
				}
				Transporte.getInstance().eliminar_persona(Transporte.getInstance().getListado_personas().get(contador));
			} catch (org.postgresql.util.PSQLException e2) {
				String causa = "Los datos ya se han introducido previamente";
				try {
					ConnectionManage.getIntancia().getconection();
				} catch (Exception e3) {
					causa = "Servidor no encontrado";
				}
				Errores erro = new Errores(Transporte.getInstance().getListado_personas().get(contador), causa);
				Transporte.getInstance().getListado_errores().add(erro);
				Transporte.getInstance().eliminar_persona(Transporte.getInstance().getListado_personas().get(contador));
			} catch (java.lang.IndexOutOfBoundsException | NullPointerException e) {
				verdad = true;
			}
		}
		return listado_errores;
	}
	
	
	

}
