package Desarrollo;

import Abstractas.Generales;

public class Entidad extends Generales {
	private String horario_actual_entrada;
	private String horario_actual_salida;
	private String horario_propuesto_entrada;
	private String horario_propuesto_salida;
	
	
	public Entidad(String nombre, String direccion, String provincia, String municipio, String calle,
			String entrecalle1, String entrecalle2, String numero, String localidad, String datos, String entidad,
			int hoja, int fila, String horario_actual_entrada, String horario_actual_salida,
			String horario_propuesto_entrada, String horario_propuesto_salida) {
		super(nombre, direccion, provincia, municipio, calle, entrecalle1, entrecalle2, numero, localidad, datos,
				entidad, hoja, fila);
		this.horario_actual_entrada = horario_actual_entrada;
		this.horario_actual_salida = horario_actual_salida;
		this.horario_propuesto_entrada = horario_propuesto_entrada;
		this.horario_propuesto_salida = horario_propuesto_salida;
	}
	public String getHorario_actual_entrada() {
		return horario_actual_entrada;
	}
	public void setHorario_actual_entrada(String horario_actual_entrada) {
		this.horario_actual_entrada = horario_actual_entrada;
	}
	public String getHorario_actual_salida() {
		return horario_actual_salida;
	}
	public void setHorario_actual_salida(String horario_actual_salida) {
		this.horario_actual_salida = horario_actual_salida;
	}
	public String getHorario_propuesto_entrada() {
		return horario_propuesto_entrada;
	}
	public void setHorario_propuesto_entrada(String horario_propuesto_entrada) {
		this.horario_propuesto_entrada = horario_propuesto_entrada;
	}
	public String getHorario_propuesto_salida() {
		return horario_propuesto_salida;
	}
	public void setHorario_propuesto_salida(String horario_propuesto_salida) {
		this.horario_propuesto_salida = horario_propuesto_salida;
	}
}
