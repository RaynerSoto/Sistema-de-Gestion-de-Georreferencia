package Validator;

import java.util.Calendar;
import java.util.Date;

import Desarrollo.Persona;

public class Validar_Persona extends Validar_General{
	
	public void validar_persona(Persona persona) throws Exception {
		validar_general(persona);
		validar_carnet(persona.getCI());
	}
	
	public void validar_carnet(String CI) throws Exception {
		boolean verdad = true;
        if (CI.length() != 11) {
        	throw new Exception("El carnet debe tener 11 digitos");
        } else {
            for (int contador = 0; contador < 11 && verdad == true; contador++) {
                Character caracter = new Character(CI.charAt(contador));
                if (caracter.isDigit(CI.charAt(contador)) == false) {
                    throw new Exception("El carnet lo compononen caracteres no numéricos");
                }
            }
            if (verdad == true) {
            	String ano = new Character(CI.charAt(0)).toString() + new Character(CI.charAt(1)).toString();
                String mes = new Character(CI.charAt(2)).toString() + new Character(CI.charAt(3)).toString();
                String dia = new Character(CI.charAt(4)).toString() + new Character(CI.charAt(5)).toString();
                int mes_r = new Integer(mes);
                if (mes_r < 1 || mes_r > 12) {
                	throw new Exception("El carnet tiene errores en el mes");
                } else {
                    int dia_r = new Integer(dia);
                    int valor = 1;
                    if (mes_r == valor || mes_r == valor + 2 || mes_r == valor + 4 || mes_r == valor + 6 || mes_r == valor + 7 || mes_r == valor + 9 || mes_r == valor + 11) {
                        if (dia_r < 1 || dia_r > 31) {
                        	throw new Exception("El carnet tiene errores en el día");
                        }
                    } else if (mes_r == valor + 1) {
                        if (new Integer(ano) % 4 == 0) {
                            if (dia_r < 1 || dia_r > 28) {
                            	throw new Exception("El carnet tiene errores en el día");
                            }
                        } else {
                            if (dia_r < 1 || dia_r > 27) {
                            	throw new Exception("El carnet tiene errores en el día");
                            }
                        }
                    } else {
                        if (dia_r < 1 || dia_r > 30) {
                        	throw new Exception("El carnet tiene errores en el día");
                        }
                    }
                }
                if (verdad != false) {
                    String posiccion = new Character(CI.charAt(6)).toString();
                    if (new Integer(posiccion) == 9) {
                        ano = "18" + ano;
                    } else if (new Integer(posiccion) >= 0 && new Integer(posiccion) <= 5) {
                        ano = "19" + ano;
                    } else {
                        ano = "20" + ano;
                    }
                    Calendar fecha_del_dia = Calendar.getInstance();//Sacar la fecha actual de la computadora
                    Date fecha_hoy = fecha_del_dia.getTime();
                    Date fecha_carnet = new Date(new Integer(ano), new Integer(mes), new Integer(dia));
                    if (fecha_hoy.before(fecha_carnet) == false) {
                    	throw new Exception("El carnet no tiene una fecha valida");
                    }
                }
            }
        }
	}
}
