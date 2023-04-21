package Controller;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import Desarrollo.Entidad;
import Desarrollo.Errores;
import Desarrollo.Transporte;
import Interfaces.Table_Interfaces;

public class Listado_Errores_Controller implements Table_Interfaces {

	@Override
	public DefaultTableModel actualizar_tabla() {
		ArrayList<Errores>listado = Transporte.getInstance().getListado_errores();
		String[] encabezado = {"Nombre","Hoja","Fila","Denominación","Causa"};		
		Object [] [] tabla = new Object[listado.size()][encabezado.length];		

		for(int i=0; i<listado.size(); i++){
			int p = -1;
			tabla [i] [++p] = listado.get(i).getEntidad().getNombre();
			tabla [i] [++p] = listado.get(i).getEntidad().getHoja()+1;
			tabla [i] [++p] = listado.get(i).getEntidad().getFila();
			if(listado.get(i).getEntidad() instanceof Entidad) {
				tabla [i][++p] = "Centro de Trabajo";
			}
			else {
				tabla [i][++p] = "Personal";
			}
			tabla [i] [++p] = listado.get(i).getCausa();
		}		
		
		DefaultTableModel defaultTableModel = new DefaultTableModel(tabla, encabezado){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
		return defaultTableModel;
	}

}
