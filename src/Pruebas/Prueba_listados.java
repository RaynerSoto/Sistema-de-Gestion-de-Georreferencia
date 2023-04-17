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
import ServicesIntern.FileServices;
import ServiciosBD.EntidadServicesBD;

public class Prueba_listados {

	@Test
	public void test() throws EncryptedDocumentException, IOException, Exception {
		//Cargar_Archivo_Controller cargar = new Cargar_Archivo_Controller();
		Workbook libro = new FileServices().construccion_libro("C:\\Users\\rayne\\Desktop\\entidades y personas.xlsx");
		Sheet hoja = new FileServices().hoja_entidad(libro);
		int posicion_hoja = libro.getSheetIndex(hoja);
		ArrayList<Entidad>entidads = new EntidadServicesBD().extraer_entidades(hoja, posicion_hoja);
		System.out.print(entidads.size());
		for(int contador = 0; contador<entidads.size();contador++) {
			try {
				new EntidadServicesBD().insertar_entidad(entidads.get(contador));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
