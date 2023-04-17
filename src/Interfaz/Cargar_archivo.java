package Interfaz;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.ant.taskdefs.Java;

import Conexion.ConnectionManage;
import Desarrollo.Entidad;
import Desarrollo.Errores;
import Desarrollo.Persona;
import Desarrollo.Transporte;
import ServicesIntern.FileServices;
import ServiciosBD.EntidadServicesBD;
import ServiciosBD.PersonaServicesBD;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ActionEvent;

import javax.imageio.stream.FileImageInputStream;
import javax.swing.DropMode;
import java.awt.Font;
import java.awt.Color;
import java.awt.TextField;
import java.awt.Toolkit;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;

public class Cargar_archivo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField direccion;
	private JCheckBox seleccion;
	private JCheckBox seleccion1;
	/**
	 * @wbp.nonvisual location=342,129
	 */
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Cargar_archivo dialog = new Cargar_archivo();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Cargar_archivo() {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Jordan\\Desktop\\Poty\\pROYECTO\\Tranporte Cujae v1.1\\Imagenes\\Isotipo.png"));
		String excel_viejo = ".xls";
		String excel_nuevo = ".xlsx";
		setTitle("Cargar archivo");
		setBounds(100, 100, 472, 240);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		direccion = new JTextField();
		direccion.setForeground(Color.BLACK);
		direccion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		direccion.setEditable(false);
		direccion.setBounds(10, 30, 391, 31);
		contentPanel.add(direccion);
		direccion.setColumns(10);
		
		Button button = new Button("     ...\r\n");
		button.setActionCommand("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int archivo_seleccionado;
				JFileChooser seleccionar_archivo = new JFileChooser();
				FileNameExtensionFilter filtro = new FileNameExtensionFilter(excel_nuevo,"XLSX");
				FileNameExtensionFilter filtro2 = new FileNameExtensionFilter(excel_viejo, "XLS");
				seleccionar_archivo.setFileFilter(filtro);
				seleccionar_archivo.setFileFilter(filtro2);
				archivo_seleccionado = seleccionar_archivo.showOpenDialog(null);
				if(archivo_seleccionado == seleccionar_archivo.APPROVE_OPTION) {
					File Fichero = seleccionar_archivo.getSelectedFile();
					direccion.setText(Fichero.getAbsolutePath());
					if(seleccionar_archivo.getSelectedFile().getName() == excel_nuevo) {
						direccion.setText(Fichero.getAbsolutePath());
					}

				}
				else {
					direccion.setText("");
				}
			}
		});
		button.setBounds(407, 30, 39, 31);
		contentPanel.add(button);
		
		Button button_1 = new Button("Aceptar");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Transporte.getInstance().getListado_errores().removeAll(Transporte.getInstance().getListado_errores());
				if(direccion.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(Cargar_archivo.this,"No hay direccion del archivo","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					if(seleccion.isSelected() == true && seleccion1.isSelected() == true) {
						JOptionPane.showMessageDialog(Cargar_archivo.this,"Ambas opciones no pueden estar marcadas","Error",JOptionPane.ERROR_MESSAGE);
					}
					else {
						if(seleccion.isSelected() == true) {
							EntidadServicesBD enti = new EntidadServicesBD();
							PersonaServicesBD perso = new PersonaServicesBD();
							try {
								perso.eliminar_persona();
								enti.eliminar_entidades();
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							insertar_Excel_BD();
						}
						else if(seleccion1.isSelected() == true) {
							insertar_Excel_BD();
						}
						else {
							JOptionPane.showMessageDialog(Cargar_archivo.this,"Seleccionar la opción deseada","Advertencia",JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
		});
		button_1.setBounds(10, 155, 113, 31);
		contentPanel.add(button_1);
		
		Button button_2 = new Button("Cerrar");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_2.setBounds(340, 155, 106, 34);
		contentPanel.add(button_2);
		
		seleccion = new JCheckBox("Eliminar todos los datos actuales  e insertar los nuevos");
		seleccion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				actualizar_selecion_box_selection();
			}
		});
		seleccion.setBounds(10, 84, 391, 23);
		contentPanel.add(seleccion);
		
		seleccion1 = new JCheckBox("Mantener los datos actuales e insertar nuevos");
		seleccion1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				actualizar_selecion_box_selection1();
			}
		});
		seleccion1.setBounds(10, 112, 391, 23);
		contentPanel.add(seleccion1);
		buttonGroup.add(seleccion);
		buttonGroup.add(seleccion1);
	}
	
	
	public void ingresar_entidades(Sheet hoja,int posicion_hoja,int cantidad_filas,int cantida_columnas) {
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
		for(int contador_fila = 1; contador_fila<cantidad_filas;contador_fila++) {
			for(int contador_columna=0;contador_columna<cantida_columnas;contador_columna++) {
				String columna = hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim();
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
			Transporte.getInstance().getListado_entidades().add(enti); 
		}
	}
	
	public void ingresar_personas(Sheet hoja,int posicion_hoja,int cantidad_filas,int cantida_columnas) {
		String entidad = null;
		String nombre = null;
		String CI = null;
		String provincia = null;
		String municipio = null;
		String direccion = null;
		String calle = null;
		String entrecalle1 = null;
		String entrecalle2 = null;
		String numero = null;
		String localidad = null;
		String datos = null;
		for(int contador_fila = 1; contador_fila<cantidad_filas;contador_fila++) {
			for(int contador_columna=0;contador_columna<cantida_columnas;contador_columna++) {
				if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombre") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombreyapellidos") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombres") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Nombres y apellidos")) {
					try {
						nombre = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						nombre = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombreentidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("nombredeentidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entidadsuperior") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("pertenecea") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("pertenecientea") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("pertenecientea:") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Id.Sede")) {
					try {
						entidad = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						entidad = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("municipio") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("municipios")) {
					try {
						municipio = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						municipio = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("provincia") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("provincias")) {
					try {
						provincia = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						provincia = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("direccion") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("dirección") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Direccion completa") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Dirección completa") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("direccionescompletas")) {
					try {
						direccion = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						direccion = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Calle") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("calles")) {
					try {
						calle = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						calle = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalle1") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalles1")) {
					try {
						entrecalle1 = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						entrecalle1 = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalle2") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("entrecalles2")) {
					try {
						entrecalle2 = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
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
					} catch (NullPointerException e2) {
						localidad = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("datos") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("Datos adicionales") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("datosadicionales")) {
					try {
						datos = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
					} catch (NullPointerException e2) {
						datos = "";
					}
				}
				else if(hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("ci") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("carnet") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("carnet de identidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("carnetdeidentidad") || hoja.getRow(0).getCell(contador_columna).getStringCellValue().trim().equalsIgnoreCase("CI")) {
					try {
						CI = hoja.getRow(contador_fila).getCell(contador_columna).getStringCellValue();
						CI = validar_carnet(CI);
					} catch (NullPointerException e2) {
						CI = "";
					} 
				}
			}
			Persona per = new Persona(nombre, direccion, provincia, municipio, calle, entrecalle1, entrecalle2, numero, localidad, datos, entidad, posicion_hoja, contador_fila, CI);
			Transporte.getInstance().getListado_personas().add(per);
		}
	}
	
	public void llenar_entidad() throws ClassNotFoundException, SQLException{
		boolean verdad = false;
		for(int contador = 0;verdad == false;) {
			EntidadServicesBD enti = new EntidadServicesBD();
			try {
				if(Transporte.getInstance().getListado_entidades().get(contador).getNombre().equals("") == true || Transporte.getInstance().getListado_entidades().get(contador).getProvincia().equals("") == true || Transporte.getInstance().getListado_entidades().get(contador).getMunicipio().equals("") == true || Transporte.getInstance().getListado_entidades().get(contador).getDireccion().equals("") == true) {
					String causa = "Valores necesarios no ingresados";
					Errores erro = new Errores(Transporte.getInstance().getListado_entidades().get(contador), causa); 
					Transporte.getInstance().getListado_errores().add(erro);
				}
				else {
					enti.insertar_entidad(Transporte.getInstance().getListado_entidades().get(contador));
				}
				Transporte.getInstance().eliminar_entidad(Transporte.getInstance().getListado_entidades().get(contador));
			} catch (org.postgresql.util.PSQLException e2) {
				String causa = "Los datos ya se han introducido previamente";
				try (Connection con = ConnectionManage.getIntancia().getconection()){} catch (Exception e3) {
					causa = "Servidor no encontrado";
				}
				Errores erro = new Errores(Transporte.getInstance().getListado_entidades().get(contador), causa); 
				Transporte.getInstance().eliminar_entidad(Transporte.getInstance().getListado_entidades().get(contador));
				Transporte.getInstance().getListado_errores().add(erro);
			} catch (java.lang.IndexOutOfBoundsException | NullPointerException e) {
				verdad = true;
			}
		}
	}
	
	//Comentar este método para probar el método nuevo para extraer y almacenar la información
	public void llenar_personas() throws Exception {
		boolean verdad = false;
		for(int contador = 0;verdad == false;) {
			PersonaServicesBD perso = new PersonaServicesBD();
			try {
				if(Transporte.getInstance().getListado_personas().get(contador).getEntidad().equals("") == true || Transporte.getInstance().getListado_personas().get(contador).getNombre().equals("") == true || Transporte.getInstance().getListado_personas().get(contador).getCI().equals("") == true || Transporte.getInstance().getListado_personas().get(contador).getDireccion().equals("") == true || Transporte.getInstance().getListado_personas().get(contador).getCI().length() != 11) {
					String causa = "Valores necesarios no ingresados o erroneos";
					Errores erro = new Errores(Transporte.getInstance().getListado_personas().get(contador), causa);
					Transporte.getInstance().getListado_errores().add(erro);
				}
				else {
					perso.insertar_persona(Transporte.getInstance().getListado_personas().get(contador));
				}
				Transporte.getInstance().eliminar_persona(Transporte.getInstance().getListado_personas().get(contador));
			} catch (org.postgresql.util.PSQLException e2) {
				String causa = "Los datos ya se han introducido previamente";
				try {
					ConnectionManage.getIntancia().getconection();
				} catch (Exception e3) {
					causa = "Servidor no encontrado";
				}
				Errores erro = new Errores(Transporte.getInstance().getListado_personas().get(contador), causa);
				Transporte.getInstance().getListado_errores().add(erro);
				Transporte.getInstance().eliminar_persona(Transporte.getInstance().getListado_personas().get(contador));
			} catch (java.lang.IndexOutOfBoundsException | NullPointerException e) {
				verdad = true;
			}
		}
	}
	
	public ArrayList<Sheet> listado_hojas(Workbook libro){
		ArrayList<Sheet>hojas = new ArrayList<Sheet>();
		for(int contador = 0; contador<libro.getNumberOfSheets();contador++) {
			hojas.add((Sheet) libro.getSheetAt(contador));
		}
		return hojas;
	}
	
	public void insertar_Excel_BD() throws Exception {
	FileServices fileServices = new FileServices();
		try {
			Workbook libro = fileServices.creacion_libro(direccion.getText());
			ArrayList<Sheet>hojas = fileServices.listado_hojas(libro);
			if(hojas.size() == 2) {
				for(int contador = 0; contador < hojas.size();contador++) {
					int cantidad_filas = hojas.get(contador).getLastRowNum()+1;
					int cantida_columnas;
					try {
						cantida_columnas = hojas.get(contador).getRow(0).getLastCellNum();
					} catch (Exception e2) {
						cantida_columnas = 0;
					}
					if(cantidad_filas != 0 && cantida_columnas!=0) {
						if(hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("entidad") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("entidades") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("trabajos") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("Centros de trabajos") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("centrodetrabajo") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("unidades") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("centro") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugardetrabajo") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugaresdetrabajo") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("lugaresdetrabajos")){
							ingresar_entidades(hojas.get(contador),contador, cantidad_filas, cantida_columnas);
						}
						else if(hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("personas") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("personal") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("trabajadores") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("trabajador") || hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("empleados") ||hojas.get(contador).getSheetName().toLowerCase().trim().equalsIgnoreCase("persona")) {
							ingresar_personas(hojas.get(contador),contador, cantidad_filas, cantida_columnas);
						}
					}
				}
				if(Transporte.getInstance().getListado_entidades().size() != 0) {
					llenar_entidad();
					if(Transporte.getInstance().getListado_personas().size() != 0) {
						llenar_personas();
						if(Transporte.getInstance().getListado_errores().size() == 0) {
							JOptionPane.showMessageDialog(Cargar_archivo.this,"Datos cargados con éxito","Correcto",JOptionPane.INFORMATION_MESSAGE);
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(Cargar_archivo.this,"Datos cargados con errores","Correcto",JOptionPane.INFORMATION_MESSAGE);
							Listado_errores erro = new Listado_errores();
							dispose();
							erro.setVisible(true);
						}
					}
					else {
						JOptionPane.showMessageDialog(Cargar_archivo.this,"La hoja del personal se encuentra vacía. No se pudo cargar","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(Cargar_archivo.this,"La hoja de los centros de trabajo está vacía, no se puede proceder. Revise","Error crítico",JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(Cargar_archivo.this,"Cantidad de hojas no aplicable","Error",JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(Cargar_archivo.this,"Archivo no admitido","Error",JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void actualizar_selecion_box_selection() {
		if(seleccion.isSelected() == true) {
			seleccion1.setSelected(false);
		}
	}
	
	public void actualizar_selecion_box_selection1() {
		//seleccion.
		if(seleccion1.isSelected() == true) {
			seleccion.setSelected(false);
		}
	}
	
	public String validar_carnet(String ci_convertir) {
		String valor = "";
		if(ci_convertir.length() == 11) {
			return ci_convertir;
		}
		else if(ci_convertir.length() < 11) {
			int cantidad = 11-ci_convertir.length();
			if(cantidad == 1) {
				ci_convertir = "0"+ci_convertir;
			}
			else if(cantidad == 2) {
				ci_convertir = "0"+"0" + ci_convertir;
			}
			else if(cantidad >=3) {
				ci_convertir = "0"+"0"+"0"+ci_convertir;
			}
		}
		else if(ci_convertir.length() >11) {
			for(int contador = 0; contador<ci_convertir.length();contador++){
				Character caracter = ci_convertir.charAt(contador);
				if(caracter.isDigit(caracter) == true) {
					if(valor == "") {
						valor = caracter.toString();
					}
					else {
						valor = valor + caracter.toString();
					}
				}
		}
		return valor;
	}
		return valor;
}
}
