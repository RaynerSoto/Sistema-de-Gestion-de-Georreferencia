package Desarrollo;

import java.util.ArrayList;

public class Extras {
	
private static Extras ext;
	
	public static Extras getInstance() {
		if(ext == null) {
			ext = new Extras();
		}
		return ext;
	}
	public Extras() {}
	
	public ArrayList<String> getListado_filtroArrayList() {
		return listado_filtroArrayList;
	}
	public void setListado_filtroArrayList(ArrayList<String> listado_filtroArrayList) {
		this.listado_filtroArrayList = listado_filtroArrayList;
	}

	private ArrayList<String>listado_filtroArrayList = new ArrayList<String>();
	
	public boolean buscar_filtro_insertado(String valor) {
		boolean verdad = false;
		for(int contador = 0; contador < listado_filtroArrayList.size() && verdad == false; contador++) {
			if(listado_filtroArrayList.get(contador).equals(valor) == true) {
				verdad = true;
			}
		}
		return verdad;
		
	}
}
