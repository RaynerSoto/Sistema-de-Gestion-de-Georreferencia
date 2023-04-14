package Servicios;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

import java.sql.*;

import Desarrollo.Entidad;
import Desarrollo.Errores;
import Desarrollo.Extras;
import Desarrollo.Transporte;
import Validator.Validar_General;

public class EntidadServices {
	public void insertar_entidad(Entidad e) throws ClassNotFoundException, SQLException,Exception {
		try (Connection con = ConnectionManage.getIntancia().getconection()){
			String consulta = "{call insertar_entidad(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement prepa = con.prepareCall(consulta);
			prepa.setString(1, e.getNombre());
			prepa.setString(2, e.getEntidad());
			prepa.setString(3, e.getMunicipio());
			prepa.setString(4, e.getProvincia());
			prepa.setString(5, e.getDireccion());
			prepa.setString(6, e.getCalle());
			prepa.setString(7, e.getEntrecalle1());
			prepa.setString(8, e.getEntrecalle2());
			prepa.setString(9, e.getNumero());
			prepa.setString(10, e.getLocalidad());
			prepa.setString(11, e.getHorario_actual_entrada());
			prepa.setString(12, e.getHorario_actual_salida());
			prepa.setString(13, e.getHorario_propuesto_entrada());
			prepa.setString(14, e.getHorario_propuesto_salida());
			prepa.setString(15, e.getDatos());
			prepa.execute();
		} catch (Exception e2) {
			e2.printStackTrace();
			throw new Exception("No se pudo insertar la entidad");
		}
	}
	
	public void eliminar_entidades() throws ClassNotFoundException, SQLException, Exception {
		try (Connection con = ConnectionManage.getIntancia().getconection()){
			String consulta = "{call eliminar_entidades()}";
			CallableStatement prepa = con.prepareCall(consulta);
			prepa.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("No se pudo eliminar las entidades");
		}
	}
	
	//Probar luego
	public ArrayList<String> listado_entidades_nombre() throws SQLException, ClassNotFoundException,Exception{
		ArrayList<String>listado_entidades_nombre = new ArrayList<String>();
		try (Connection con = ConnectionManage.getIntancia().getconection()){
			String consulta = "{?=call entidad_listado_nombre()}";
			con.setAutoCommit(false);
			CallableStatement prepa = con.prepareCall(consulta);
			prepa.registerOutParameter(1,java.sql.Types.REF_CURSOR);
			prepa.execute();
			ResultSet resultado = (ResultSet) prepa.getObject(1);
			while(resultado.next()) {
				listado_entidades_nombre.add(resultado.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("No se pudo cargar el listado de nombre de la entidades");
		}
		return listado_entidades_nombre;
	}
	
	public ArrayList<String> listado_municipios_entidades(ArrayList<String>listado) throws ClassNotFoundException, SQLException{
		ArrayList<String>listado_municipios_entidades = new ArrayList<String>();
		try (Connection con = ConnectionManage.getIntancia().getconection()){
			for(int contador = 0; contador< listado.size();contador++) {
				String consulta = "{call entidad_municipio_buscar(?)}";			
				CallableStatement prepa = con.prepareCall(consulta);
				prepa.setString(1, Extras.getInstance().getListado_filtroArrayList().get(contador));
				ResultSet resultado = prepa.executeQuery();
				while(resultado.next()) {
					boolean verdad = false;
					String valor = resultado.getString(1);
					for(int contador2 = 0; contador2<listado_municipios_entidades.size() && verdad == false;contador2++) {
						if(valor.equals(listado_municipios_entidades.get(contador2)) == true) {
							verdad = true;
						}
					}
					if(verdad == false) {
						listado_municipios_entidades.add(valor);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listado_municipios_entidades;
	}
	
	public long calculo(String origen,String destino, String entidad) throws ClassNotFoundException, SQLException {
		long valor = 0;
		try (Connection con = ConnectionManage.getIntancia().getconection()){
			String consulta = "{call entidad_calculo_origen_destino(?,?,?)}";
			CallableStatement prepa = con.prepareCall(consulta);
			prepa.setString(1, entidad);
			prepa.setString(2, origen);
			prepa.setString(3, destino);
			ResultSet resultado = prepa.executeQuery();
			while(resultado.next()) {
				valor = valor+resultado.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valor;
	}
	
	//Cargar entidades en una lista
	public ArrayList<Entidad> extraer_entidades(Sheet hoja,int posicion_hoja) {
		ArrayList<Entidad>listado_entidades = new ArrayList<>();
		String nombre = null;
		String provincia = null;
		String municipio = null;
		String entidad = null;
		String direccion = null;
		String calle = null;
		String entrecalle1 = null;
		String entrecalle2 = null;
		String numero = null;
		String localidad = null;
		String datos = null;
		String horario_actual_entrada = null;
		String horario_actual_salida = null;
		String horario_propuesto_entrada = null;
		String horario_propuesto_salida = null;
		for(int contador_fila = 1; contador_fila<hoja.getLastRowNum()+1;contador_fila++) {
			for(int contador_columna=0;contador_columna<hoja.getRow(0).getLastCellNum();contador_columna++) {
				if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("id.centro trabajo") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("centrotrabajo") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("centro")) {
					try {
						nombre = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						nombre = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombreentidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombredeentidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entidadsuperior") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("pertenecea") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("pertenecientea") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("pertenecientea:")) {
					try {
						entidad = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						entidad = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("municipio") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("municipios")) {
					try {
						municipio = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						municipio = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("provincia") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("provincias")) {
					try {
						provincia = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						provincia = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("direccion") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("dirección") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Direccion completa") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Dirección completa") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("direccionescompletas")) {
					try {
						direccion = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						direccion = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Calle") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("calles")) {
					try {
						calle = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						calle = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalle1") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalles1")) {
					try {
						entrecalle1 = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						entrecalle1 = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalle2") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalles2")) {
					try {
						entrecalle2 = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						entrecalle2 = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("numero") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("número")) {
					try {
						numero = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						numero = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("localidad")) {
					try {
						localidad = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						localidad = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("datos") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Datos adicionales") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Datosadicionales") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("datosadicionales") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("datos adicionales")) {
					try {
						datos = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						datos = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Horario actual de entrada")) {
					try {
						horario_actual_entrada = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						horario_actual_entrada = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Horario actual de salida")) {
					try {
						horario_actual_salida = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						horario_actual_salida = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Horario propuesto entrada")) {
					try {
						horario_propuesto_entrada = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						horario_propuesto_entrada = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Horario propuesto de salida")) {
					try {
						horario_propuesto_salida = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (Exception e2) {
						horario_propuesto_salida = "";
					}
				}
			}
			Entidad enti = new Entidad(nombre, direccion, provincia, municipio, calle, entrecalle1, entrecalle2, numero, localidad, datos, entidad,posicion_hoja,contador_fila,horario_actual_entrada, horario_actual_salida, horario_propuesto_entrada, horario_propuesto_salida);
			listado_entidades.add(enti); 
		}
		return listado_entidades;
	}
	
	//Guardar entidades en la BD
	public ArrayList<Errores> almacenar_entidad(ArrayList<Entidad>listaEntidads) throws Exception{
		ArrayList<Errores> listado_errores = new ArrayList<>();
		boolean verdad = false;
		for(int contador = 0;verdad == false;) {
			EntidadServices enti = new EntidadServices();
			try {
				/*
				if(Transporte.getInstance().getListado_entidades().get(contador).getNombre().equals("") == true || Transporte.getInstance().getListado_entidades().get(contador).getProvincia().equals("") == true || Transporte.getInstance().getListado_entidades().get(contador).getMunicipio().equals("") == true || Transporte.getInstance().getListado_entidades().get(contador).getDireccion().equals("") == true) {
					String causa = "Valores necesarios no ingresados";
					Errores erro = new Errores(Transporte.getInstance().getListado_entidades().get(contador), causa); 
					Transporte.getInstance().getListado_errores().add(erro);
				}
				else {
					enti.insertar_entidad(Transporte.getInstance().getListado_entidades().get(contador));
				}*/
				Validar_General val = new Validar_General();
				val.validar_general(listaEntidads.get(contador));
				enti.insertar_entidad(listaEntidads.get(contador));
				listaEntidads.remove(contador);
			} catch (org.postgresql.util.PSQLException e2) {
				String causa = "Los datos ya se han introducido previamente";
				try (Connection con = ConnectionManage.getIntancia().getconection()){} catch (Exception e3) {
					causa = "Servidor no encontrado";
				}
				Errores erro = new Errores(Transporte.getInstance().getListado_entidades().get(contador), causa); 
				listaEntidads.remove(contador);
				Transporte.getInstance().getListado_errores().add(erro);
			} catch (java.lang.IndexOutOfBoundsException | NullPointerException e) {
				verdad = true;
			} catch (Exception e) {
				Transporte.getInstance().getListado_errores().add(new Errores(listaEntidads.get(contador),e.getMessage()));
				listaEntidads.remove(contador);
			}
		}
		return listado_errores;
	}
}
