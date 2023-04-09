package Pruebas;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.junit.Test;

import Servicios.FileServices;

public class Contruccion_libro_prueba {

	@Test
	public void prueba_null_creacion_libro() throws EncryptedDocumentException, IOException {
		try {
			assertNull(new FileServices().construccion_libro("C:\\Users\\rayne\\Desktop\\entidades y personas.xlsx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void prueba_not_null_creacion_libro() {
		try {
			assertNotNull(new FileServices().construccion_libro("C:\\Users\\rayne\\Desktop\\entidades y personas.xlsx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
