package ServicesIntern;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Conexion.Conection;
import Conexion.ConnectionManage;
import Desarrollo.Errores;
import Desarrollo.Persona;
import Desarrollo.Transporte;
import ServiciosBD.PersonaServicesBD;
import Validator.Validar_Persona;

public class PersonaServices {
	
	//Encontrar la hoja de las personas
	public Sheet hoja_persona(Workbook libro) {
		Sheet hoja = null;
		for(int contador = 0; contador < libro.getNumberOfSheets() && hoja == null;contador ++) {
			if(libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("personas") || libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("personal") || libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("trabajadores") || libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("trabajador") || libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("empleados") || libro.getSheetAt(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("persona")) {
				hoja = libro.getSheetAt(contador);
			}
		}
		return hoja;
	}
	
	//Cargar persona en una lista
	public ArrayList<Persona> extraer_personas(Sheet hoja,int posicion_hoja) {
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
		for(int contador_fila = 1; contador_fila<hoja.getLastRowNum()+1;contador_fila++) {
			for(int contador_columna=0;contador_columna<hoja.getRow(0).getLastCellNum();contador_columna++) {
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
	
	//Guardar entidades en la BD		
	public ArrayList<Errores> almacenar_personas(ArrayList<Persona>personas) throws Exception {
		ArrayList<Errores>errores = new ArrayList<>();
		boolean verdad = false;
		for(int contador = 0;verdad == false;) {
			try {
				validar_E_insertar_Persona(personas.get(contador));
				personas.remove(contador);
			} catch (org.postgresql.util.PSQLException e2) {
				String causa = "Los datos ya se han introducido previamente";
				try (Connection con = new Conection().conexion()){} catch (Exception e3) {
					causa = "Servidor no encontrado";
				}
				Errores erro = new Errores(Transporte.getInstance().getListado_entidades().get(contador), causa); 
				personas.remove(contador);
				errores.add(erro);
			} catch (java.lang.IndexOutOfBoundsException | NullPointerException e) {
				verdad = true;
			} catch (Exception e) {
				errores.add(new Errores(personas.get(contador),e.getMessage()));
				personas.remove(contador);
			}
		}
		return errores;
	}
	
	//Validar e insertar en la BD una persona
	public void validar_E_insertar_Persona(Persona p) throws ClassNotFoundException, SQLException, Exception{
		new Validar_Persona().validar_persona(p);
		new PersonaServicesBD().insertar_persona(p);
	}
}
