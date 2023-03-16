package Desarrollo;

import java.util.ArrayList;

import Abstractas.Generales;


public class Transporte {
	private ArrayList<Entidad>listado_entidades = new ArrayList<Entidad>();
	private ArrayList<Persona>listado_personas = new ArrayList<Persona>();
	private ArrayList<Errores>listado_errores = new ArrayList<Errores>();
	
	private static Transporte tranport;
	
	public static Transporte getInstance() {
		if(tranport == null) {
			tranport = new Transporte();
		}
		return tranport;
	}
	
	
	
	public Transporte() {}
	
	
	public ArrayList<Persona> getListado_personas() {
		return listado_personas;
	}
	public void setListado_personas(ArrayList<Persona> listado_personas) {
		this.listado_personas = listado_personas;
	}
	public ArrayList<Entidad> getListado_entidades() {
		return listado_entidades;
	}
	public void setListado_entidades(ArrayList<Entidad> listado_entidades) {
		this.listado_entidades = listado_entidades;
	}
	
	public void eliminar_entidad(Entidad e) {
		getListado_entidades().remove(e);
	}
	public void eliminar_persona(Persona p) {
		getListado_personas().remove(p);
	}



	public ArrayList<Errores> getListado_errores() {
		return listado_errores;
	}



	public void setListado_errores(ArrayList<Errores> listado_errores) {
		this.listado_errores = listado_errores;
	}
}
