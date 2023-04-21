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
import Controller.Cargar_Archivo_Controller;
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
				try {
					Transporte.getInstance().getListado_errores().removeAll(Transporte.getInstance().getListado_errores());
					if(direccion.getText().isEmpty() == true) {
						JOptionPane.showMessageDialog(Cargar_archivo.this,"No hay direccion del archivo","Error",JOptionPane.ERROR_MESSAGE);
					}
					else {
						if(seleccion.isSelected() == false && seleccion1.isSelected() == false) {
							JOptionPane.showMessageDialog(Cargar_archivo.this,"Ambas opciones no pueden estar marcadas","Error",JOptionPane.ERROR_MESSAGE);
						}
						else {
							if(seleccion.isSelected() == true) {
								new EntidadServicesBD().eliminar_entidades();
								new PersonaServicesBD().eliminar_persona();
							}
							new Cargar_Archivo_Controller().proceso_cargar_guardar_Excel(direccion.getText());
						}
					}
				} catch (Exception e2) {
					if(e2.getMessage().equals("Datos cargados con éxito") == true) {
						JOptionPane.showMessageDialog(Cargar_archivo.this,e2.getMessage(),"Conseguido",JOptionPane.YES_OPTION);
					}
					else if(e2.getMessage().equals("Datos cargados con errores") == true) {
						JOptionPane.showMessageDialog(Cargar_archivo.this,e2.getMessage(),"Advertencia",JOptionPane.WARNING_MESSAGE);
						Listado_errores eroErrores = new Listado_errores();
						eroErrores.setVisible(true);
					}
					else if(e2.getMessage().equals("La hoja del personal se encuentra vacía. No se pudo cargar") == true) {
						JOptionPane.showMessageDialog(Cargar_archivo.this,e2.getMessage(),"Advertencia",JOptionPane.WARNING_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(Cargar_archivo.this,e2.getMessage(),"Advertencia",JOptionPane.ERROR_MESSAGE);
					}
					dispose();
				}
		}});
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
}
