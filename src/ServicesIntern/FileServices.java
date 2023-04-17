package ServicesIntern;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class FileServices {
	//Crear libro sin importar la situación
	public Workbook construccion_libro(Object object) throws EncryptedDocumentException, IOException {
		return object instanceof String ? creacion_libro((String)object):creacion_libro((File)object);
	}
	
	
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
}
