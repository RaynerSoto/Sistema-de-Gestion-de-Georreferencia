package Pruebas;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import Controller.Cargar_Archivo_Controller;
import Desarrollo.Entidad;
import Desarrollo.Errores;
import Desarrollo.Persona;
import ServicesIntern.EntidadServices;
import ServicesIntern.FileServices;
import ServicesIntern.PersonaServices;
import ServiciosBD.EntidadServicesBD;
import ServiciosBD.PersonaServicesBD;

public class Prueba_listados {

	@Test
	public void test() throws EncryptedDocumentException, IOException, Exception {
		Workbook libro = new FileServices().construccion_libro("C:\\Users\\rayne\\Desktop\\entidades y personas.xlsx");
		Sheet hoja = new EntidadServices().hoja_entidad(libro);
		int posicion_hoja = libro.getSheetIndex(hoja);
		ArrayList<Entidad>entidads = new EntidadServices().extraer_entidades(hoja, posicion_hoja);
		System.out.print(entidads.size());
		for(int contador = 0; contador<entidads.size();contador++) {
			try {
				new EntidadServicesBD().insertar_entidad(entidads.get(contador));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void test2() throws EncryptedDocumentException, IOException {
		Workbook libro = new FileServices().construccion_libro("C:\\Users\\rayne\\Desktop\\entidades y personas.xlsx");
		Sheet hoja = new PersonaServices().hoja_persona(libro);
		int posicion_hoja = libro.getSheetIndex(hoja);
		ArrayList<Persona>personas = new PersonaServices().extraer_personas(hoja, posicion_hoja);
		System.out.print(personas.size());
		for(int contador = 0; contador<personas.size();contador++) {
			try {
				new PersonaServicesBD().insertar_persona(personas.get(contador));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
