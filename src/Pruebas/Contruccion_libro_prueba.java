package Pruebas;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.junit.Test;

import Servicios.FileServices;

public class Contruccion_libro_prueba {

	@Test
	public void test() throws EncryptedDocumentException, IOException {
		try {
			assertNotNull(new FileServices().construccion_libro("C:\\Users\\rayne\\Desktop\\entidades y personas.xlsx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
