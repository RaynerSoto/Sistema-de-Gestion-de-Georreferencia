package Validator;

import Desarrollo.Persona;

public class Validar_Persona extends Validar_General{
	public void validar_carnet(Persona persona) throws Exception {
		validar_general(persona);
	}
}
