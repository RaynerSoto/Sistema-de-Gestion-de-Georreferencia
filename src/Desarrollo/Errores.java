package Desarrollo;

import Abstractas.Generales;

public class Errores {
	private Generales entidad;
	private String causa;
	
	public String getCausa() {
		return causa;
	}
	public void setCausa(String causa) {
		this.causa = causa;
	}
	public Generales getEntidad() {
		return entidad;
	}
	public void setEntidad(Generales entidad) {
		this.entidad = entidad;
	}
	
	public Errores(Generales entidad, String causa) {
		super();
		this.entidad = entidad;
		this.causa = causa;
	}
	
	
}
