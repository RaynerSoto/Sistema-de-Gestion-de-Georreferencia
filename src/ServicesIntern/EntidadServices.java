package ServicesIntern;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Conexion.Conection;
import Conexion.ConnectionManage;
import Desarrollo.Entidad;
import Desarrollo.Errores;
import Desarrollo.Transporte;
import ServiciosBD.EntidadServicesBD;
import Validator.Validar_General;

public class EntidadServices {
	
	//Encontrar la hoja de las entidades
	public Sheet hoja_entidad(Workbook libro) {
		Sheet hoja = null;
		for(int contador = 0; contador < libro.getNumberOfSheets() && hoja == null;contador ++) {
			if(libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("entidad") ||libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("entidades") || libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("trabajos") || libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("Centros de trabajos") || libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("centrodetrabajo") || libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("unidades") || libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("centro") || libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugardetrabajo") || libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugaresdetrabajo") || libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugaresdetrabajos")){
				hoja = libro.getSheetAt(contador);
			}
		}
		return hoja;
	}
	
	//Cargar entidades en una lista
	public ArrayList<Entidad> extraer_entidades(Sheet hoja,int posicion_hoja) {
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
		for(int contador_fila = 1; contador_fila<hoja.getLastRowNum()+1;contador_fila++) {
			for(int contador_columna=0;contador_columna<hoja.getRow(0).getLastCellNum();contador_columna++) {
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
	
	//Guardar entidades en la BD
	public ArrayList<Errores> almacenar_entidad(ArrayList<Entidad>listaEntidads) throws Exception{
		ArrayList<Errores> listado_errores = new ArrayList<>();
		boolean verdad = false;
		for(int contador = 0;verdad == false;) {
			try {
				validar_E_insertar_Entidad(listaEntidads.get(contador));
				listaEntidads.remove(contador);
			} catch (org.postgresql.util.PSQLException e2) {
				String causa = "Los datos ya se han introducido previamente";
				try (Connection con = new Conection().conexion()){} catch (Exception e3) {
					causa = "Servidor no encontrado";
				}
				Errores erro = new Errores(Transporte.getInstance().getListado_entidades().get(contador), causa); 
				listaEntidads.remove(contador);
				listado_errores.add(erro);
			} catch (java.lang.IndexOutOfBoundsException | NullPointerException e) {
				verdad = true;
			} catch (Exception e) {
				listado_errores.add(new Errores(listaEntidads.get(contador),e.getMessage()));
				listaEntidads.remove(contador);
			}
		}
		return listado_errores;
	}
	
	//Validar e insertar en la BD una Entidad
	public void validar_E_insertar_Entidad(Entidad e) throws ClassNotFoundException, SQLException, Exception {
		new Validar_General().validar_general(e);
		new EntidadServicesBD().insertar_entidad(e);
	}
}
