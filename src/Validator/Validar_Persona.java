package Validator;

import Desarrollo.Persona;

public class Validar_Persona extends Validar_General{
	public void validar_persona(Persona persona) throws Exception {
		validar_general(persona);
		validar_carnet(persona.getCI());
	}

	private void validar_carnet(String ci) throws Exception {
		if(ci.length() == 11) {
			
		}
		else {
			throw new Exception("Carnet con cantidad de caracteres no válidos");
		}
}
}
