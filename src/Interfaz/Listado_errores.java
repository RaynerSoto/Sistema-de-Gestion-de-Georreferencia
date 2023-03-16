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
import Desarrollo.Entidad;
import Desarrollo.Errores;
import Desarrollo.Transporte;

import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Listado_errores extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;

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
	public Listado_errores() {
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
		ArrayList<Errores>listado = Transporte.getInstance().getListado_errores();
		String[] encabezado = {"Nombre","Hoja","Fila","Denominación","Causa"};		
		Object [] [] tabla = new Object[listado.size()][encabezado.length];		

		for(int i=0; i<listado.size(); i++){
			int p = -1;
			tabla [i] [++p] = listado.get(i).getEntidad().getNombre();
			tabla [i] [++p] = listado.get(i).getEntidad().getHoja()+1;
			tabla [i] [++p] = listado.get(i).getEntidad().getFila()+1;
			if(listado.get(i).getEntidad() instanceof Entidad) {
				tabla [i][++p] = "Centro de Trabajo";
			}
			else {
				tabla [i][++p] = "Personal";
			}
			tabla [i] [++p] = listado.get(i).getCausa();
			Transporte.getInstance().getListado_errores().remove(i);
		}		
		
		DefaultTableModel defaultTableModel = new DefaultTableModel(tabla, encabezado){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
		table.setModel(defaultTableModel);
	}
}
