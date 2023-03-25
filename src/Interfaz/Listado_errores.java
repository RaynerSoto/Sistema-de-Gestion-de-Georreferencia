package Interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Abstractas.Generales;
import Controller.Listado_Errores_Controller;
import Desarrollo.Entidad;
import Desarrollo.Errores;
import Desarrollo.Transporte;
import Interfaces.Table_Interfaces;

import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Listado_errores extends JDialog{

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private Listado_Errores_Controller listado_Errores_Controller = new Listado_Errores_Controller();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Listado_errores dialog = new Listado_errores();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Listado_errores(){
		setModal(true);
		setTitle("Error");
		setBounds(100, 100, 917, 658);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Listado de errores");
			lblNewLabel.setBounds(334, 11, 150, 23);
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
			contentPanel.add(lblNewLabel);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 38, 891, 549);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setFont(new Font("Tahoma", Font.PLAIN, 10));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		actualizarTabla();
	}
	
	private void actualizarTabla() {
		table.setModel(listado_Errores_Controller.actualizar_tabla());
	}
}
