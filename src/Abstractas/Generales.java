package Abstractas;

import Desarrollo.Entidad;

public abstract class Generales {
	private String nombre;
	private String direccion;
	private String provincia;
	private String municipio;
	private String calle;
	private String entrecalle1;
	private String entrecalle2;
	private String numero;
	private String localidad;
	private String datos;
	private String entidad;
	private int hoja;
	private int fila;
	
	public Generales(String nombre, String direccion, String provincia, String municipio, String calle,
			String entrecalle1, String entrecalle2, String numero, String localidad, String datos, String entidad,int hoja,int fila) {
		super();
		this.nombre = nombre;
		this.direccion = direccion;
		this.provincia = provincia;
		this.municipio = municipio;
		this.calle = calle;
		this.entrecalle1 = entrecalle1;
		this.entrecalle2 = entrecalle2;
		this.numero = numero;
		this.localidad = localidad;
		this.datos = datos;
		this.entidad = entidad;
		this.hoja = hoja;
		this.fila = fila;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getEntrecalle1() {
		return entrecalle1;
	}
	public void setEntrecalle1(String entrecalle1) {
		this.entrecalle1 = entrecalle1;
	}
	public String getEntrecalle2() {
		return entrecalle2;
	}
	public void setEntrecalle2(String entrecalle2) {
		this.entrecalle2 = entrecalle2;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getDatos() {
		return datos;
	}
	public void setDatos(String datos) {
		this.datos = datos;
	}
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public int getHoja() {
		return hoja;
	}
	public void setHoja(int hoja) {
		this.hoja = hoja;
	} 
	
}
