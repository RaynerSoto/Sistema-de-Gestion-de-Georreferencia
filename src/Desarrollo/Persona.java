package Desarrollo;

import Abstractas.Generales;

public class Persona extends Generales {
	private String CI;

	
	public Persona(String nombre, String direccion, String provincia, String municipio, String calle,
			String entrecalle1, String entrecalle2, String numero, String localidad, String datos, String entidad,
			int hoja, int fila, String cI) {
		super(nombre, direccion, provincia, municipio, calle, entrecalle1, entrecalle2, numero, localidad, datos,
				entidad, hoja, fila);
		CI = cI;
	}

	public String getCI() {
		return CI;
	}

	public void setCI(String cI) {
		CI = cI;
	}
}
