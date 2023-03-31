package Interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.collections4.map.HashedMap;

import Desarrollo.Persona;
import Servicios.ConnectionManage;
import Servicios.EntidadServices;
import Servicios.PersonaServices;

import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.sf.jasperreports.*;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.*;
import java.io.*;
import net.sf.jasperreports.*;
import java.awt.Toolkit;

public class Interfaz_principal extends JFrame {

	private JPanel contentPane;
	private JMenu lista_acciones;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try (Connection con = ConnectionManage.getIntancia().getconection()){
					Interfaz_principal frame = new Interfaz_principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error Crítico",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interfaz_principal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Jordan\\Desktop\\Poty\\pROYECTO\\Tranporte Cujae v1.1\\Imagenes\\Isotipo.png"));
		setResizable(false);
		setTitle("Transporte Cujae");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 913, 714);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Archivo");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Importar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cargar_archivo cargar = new Cargar_archivo();
				cargar.setVisible(true);
				}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Eliminar todos los datos");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EntidadServices enti = new EntidadServices();
				PersonaServices perso = new PersonaServices();
				try {
					perso.eliminar_persona();
					enti.eliminar_entidades();
					JOptionPane.showMessageDialog(Interfaz_principal.this,"Datos borrados con éxtio","Conseguido",JOptionPane.INFORMATION_MESSAGE);
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(Interfaz_principal.this, e,"Error",JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(Interfaz_principal.this,"Error en conexion a la Base de datos","Error",JOptionPane.ERROR_MESSAGE);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_5);
		
		lista_acciones = new JMenu("Acciones");
		menuBar.add(lista_acciones);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Listado de centros laborales");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JasperPrint print = JasperFillManager.fillReport("ireports/listado_centros_trabajo.jasper", new HashedMap(),ConnectionManage.getIntancia().getconection());
					JasperViewer.viewReport(print,false);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(Interfaz_principal.this, e2,"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		lista_acciones.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Listado de personas ");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JasperPrint print = JasperFillManager.fillReport("ireports/listado_personas.jasper", new HashedMap(),ConnectionManage.getIntancia().getconection());
					JasperViewer.viewReport(print,false);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(Interfaz_principal.this, e2,"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		lista_acciones.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Listado de centros trabajos con su personal");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JasperPrint print = JasperFillManager.fillReport("ireports/listado_centros_trabajoXtrabajadorCI.jasper", new HashedMap(),ConnectionManage.getIntancia().getconection());
					JasperViewer.viewReport(print,false);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(Interfaz_principal.this, e2,"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		lista_acciones.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Matriz destino-llegada");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Seleccion_filtrado selec = new Seleccion_filtrado();
					selec.setVisible(true);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(Interfaz_principal.this,"Error en la Base de datos","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		lista_acciones.add(mntmNewMenuItem_4);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Jordan\\Desktop\\Poty\\pROYECTO\\Tranporte Cujae v1.1\\Imagenes\\close-up-de-una-chincheta-roja-sobre-un-mapa-de-la-ciudad-de-la-habana-cuba-ex305f.jpg"));
		lblNewLabel.setBounds(10, 11, 877, 632);
		contentPane.add(lblNewLabel);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	public void actualizar_menu() {
		EntidadServices enti = new EntidadServices();
	}
}
