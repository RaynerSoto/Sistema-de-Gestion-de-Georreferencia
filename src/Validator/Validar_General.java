package Validator;

import Abstractas.Generales;

public class Validar_General {
	
	public void validar_general(Generales object) throws Exception {
		if(object.getNombre().equals(""))
			throw new Exception("El dato del nombre est� vac�o");
		if(object.getMunicipio().equals(""))
			throw new Exception("El municipio est� vac�o");
		if(object.getProvincia().equals(""))
			throw new Exception("La provincia est� vac�a");
		if(object.getDireccion().equals("") && (object.getCalle().equals("") || object.getEntrecalle1().equals("") || object.getEntrecalle2().equals("")))
			throw new Exception("No se encuentra la direcci�n");
		if(object.getDireccion().equals("")) {
			if(object.getCalle().equals("") && (object.getEntrecalle1().equals("") || object.getEntrecalle2().equals("")))
				throw new Exception("Direcci�n no valida");
		}
	}
}
