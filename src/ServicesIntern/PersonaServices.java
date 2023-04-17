package ServicesIntern;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Sheet;

import Desarrollo.Persona;
import Desarrollo.Transporte;

public class PersonaServices {
	//Cargar persona en una lista
	public ArrayList<Persona> ingresar_personas(Sheet hoja,int posicion_hoja,int cantidad_filas,int cantida_columnas) {
		ArrayList<Persona>listado_personas = new ArrayList<>();
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
