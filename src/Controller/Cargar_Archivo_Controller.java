package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Desarrollo.Entidad;
import Desarrollo.Errores;
import Desarrollo.Persona;
import Desarrollo.Transporte;
import Interfaz.Cargar_archivo;
import Interfaz.Listado_errores;
import Servicios.ConnectionManage;
import Servicios.FileServices;
import Servicios.PersonaServices;

public class Cargar_Archivo_Controller {
	
	//Cargar el Excel
	public void proceso_cargar_guardar_Excel(Object object) {
		FileServices fileServices = new FileServices();
			try {
				Workbook libro = fileServices.construccion_libro(object);
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
	public List<Errores> cargar_datos_personas() throws Exception {
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
	
	//Cargar entidades en una lista
	public List<Entidad> ingresar_entidades(Sheet hoja,int posicion_hoja,int cantidad_filas,int cantida_columnas) {
		ArrayList<Entidad>listado_entidades = new ArrayList<>();
		String nombre = null;
		String provincia = null;
		String municipio = null;
		String entidad = null;
		String direccion = null;
		String calle = null;
		String entrecalle1 = null;
		String entrecalle2 = null;
		String numero = null;
		String localidad = null;
		String datos = null;
		String horario_actual_entrada = null;
		String horario_actual_salida = null;
		String horario_propuesto_entrada = null;
		String horario_propuesto_salida = null;
		for(int contador_fila = 1; contador_fila<cantidad_filas;contador_fila++) {
			for(int contador_columna=0;contador_columna<cantida_columnas;contador_columna++) {
				String columna = hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim();
				if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("id.centro trabajo") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("centrotrabajo") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("centro")) {
					try {
						nombre = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						nombre = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombreentidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombredeentidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entidadsuperior") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("pertenecea") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("pertenecientea") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("pertenecientea:")) {
					try {
						entidad = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						entidad = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("municipio") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("municipios")) {
					try {
						municipio = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						municipio = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("provincia") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("provincias")) {
					try {
						provincia = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						provincia = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("direccion") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("dirección") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Direccion completa") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Dirección completa") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("direccionescompletas")) {
					try {
						direccion = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						direccion = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Calle") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("calles")) {
					try {
						calle = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						calle = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalle1") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalles1")) {
					try {
						entrecalle1 = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						entrecalle1 = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalle2") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalles2")) {
					try {
						entrecalle2 = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						entrecalle2 = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("numero") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("número")) {
					try {
						numero = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						numero = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("localidad")) {
					try {
						localidad = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						localidad = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("datos") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Datos adicionales") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Datosadicionales") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("datosadicionales") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("datos adicionales")) {
					try {
						datos = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						datos = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Horario actual de entrada")) {
					try {
						horario_actual_entrada = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						horario_actual_entrada = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Horario actual de salida")) {
					try {
						horario_actual_salida = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						horario_actual_salida = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Horario propuesto entrada")) {
					try {
						horario_propuesto_entrada = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						horario_propuesto_entrada = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Horario propuesto de salida")) {
					try {
						horario_propuesto_salida = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						horario_propuesto_salida = "";
					}
				}
			}
			Entidad enti = new Entidad(nombre, direccion, provincia, municipio, calle, entrecalle1, entrecalle2, numero, localidad, datos, entidad,posicion_hoja,contador_fila,horario_actual_entrada, horario_actual_salida, horario_propuesto_entrada, horario_propuesto_salida);
			listado_entidades.add(enti); 
		}
		return listado_entidades;
	}
	
	//Cargar personas en una lista
	public List<Persona> ingresar_personas(Sheet hoja,int posicion_hoja,int cantidad_filas,int cantida_columnas) {
		ArrayList<Persona> listado_personas = new ArrayList<>();
		String entidad = null;
		String nombre = null;
		String CI = null;
		String provincia = null;
		String municipio = null;
		String direccion = null;
		String calle = null;
		String entrecalle1 = null;
		String entrecalle2 = null;
		String numero = null;
		String localidad = null;
		String datos = null;
		for(int contador_fila = 1; contador_fila<cantidad_filas;contador_fila++) {
			for(int contador_columna=0;contador_columna<cantida_columnas;contador_columna++) {
				if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombre") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombreyapellidos") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombres") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Nombres y apellidos")) {
					try {
						nombre = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						nombre = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombreentidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombredeentidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entidadsuperior") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("pertenecea") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("pertenecientea") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("pertenecientea:") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Id.Sede")) {
					try {
						entidad = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						entidad = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("municipio") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("municipios")) {
					try {
						municipio = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						municipio = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("provincia") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("provincias")) {
					try {
						provincia = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						provincia = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("direccion") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("dirección") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Direccion completa") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Dirección completa") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("direccionescompletas")) {
					try {
						direccion = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						direccion = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Calle") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("calles")) {
					try {
						calle = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						calle = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalle1") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalles1")) {
					try {
						entrecalle1 = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						entrecalle1 = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalle2") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalles2")) {
					try {
						entrecalle2 = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						entrecalle2 = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("numero") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("número")) {
					try {
						numero = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						numero = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("localidad")) {
					try {
						localidad = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						localidad = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("datos") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Datos adicionales") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("datosadicionales")) {
					try {
						datos = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						datos = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("ci") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("carnet") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("carnet de identidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("carnetdeidentidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("CI")) {
					try {
						CI = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						CI = "";
					} 
				}
			}
			Persona per = new Persona(nombre, direccion, provincia, municipio, calle, entrecalle1, entrecalle2, numero, localidad, datos, entidad, posicion_hoja, contador_fila, CI);
			listado_personas.add(per);
		}
		return listado_personas;
	}
}
