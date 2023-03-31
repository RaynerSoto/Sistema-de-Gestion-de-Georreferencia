package Servicios;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class FileServices {
	
	//Crear el libro de Excel a partir de la dirección
	public Workbook creacion_libro(String direccion) throws EncryptedDocumentException, IOException {
		return WorkbookFactory.create(new File(direccion));
	}
	
	//Crear libro a partir del fichero
	public Workbook creacion_libro(File file) throws EncryptedDocumentException, IOException {
		return WorkbookFactory.create(file);
	}
	
	
	//Listado hojas de un libro
	public ArrayList<Sheet> listado_hojas(Workbook libro){
		ArrayList<Sheet>hojas = new ArrayList<Sheet>();
		for(int contador = 0; contador<libro.getNumberOfSheets();contador++) {
			hojas.add((Sheet) libro.getSheetAt(contador));
		}
		return hojas;
	}
}
