package Interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Desarrollo.Entidad;
import Desarrollo.Extras;
import ServiciosBD.EntidadServicesBD;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import java.awt.Toolkit;

public class Seleccion_filtrado extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JComboBox comboBox;
	private Button boton_eliminar;
	private Button boton_annadir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Seleccion_filtrado dialog = new Seleccion_filtrado();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Seleccion_filtrado() throws ClassNotFoundException, SQLException {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Jordan\\Desktop\\Poty\\pROYECTO\\Tranporte Cujae v1.1\\Imagenes\\Isotipo.png"));
		setModal(true);
		setTitle("Filtro\r\n");
		setBounds(100, 100, 479, 640);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Seleccione las entidades que desea ver:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 11, 392, 50);
		contentPanel.add(lblNewLabel);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBox.setBounds(10, 96, 353, 33);
		contentPanel.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		scrollPane.setBounds(10, 140, 440, 335);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				actualizar_boton_eliminar();
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		boton_annadir = new Button("A\u00F1adir");
		boton_annadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(comboBox.getSelectedItem().toString().equals("") != true) {
						if(Extras.getInstance().buscar_filtro_insertado(comboBox.getSelectedItem().toString()) == false) {
							Extras.getInstance().getListado_filtroArrayList().add(comboBox.getSelectedItem().toString());
							actualizarTabla();
							llenar_combo();
						}
						else {
							JOptionPane.showMessageDialog(Seleccion_filtrado.this,"Filtro ya seleccionado","Advertencia",JOptionPane.ERROR_MESSAGE);
						}
					}
				} catch (NullPointerException e2) {
					JOptionPane.showMessageDialog(Seleccion_filtrado.this,"Filtrado vacío","Error",JOptionPane.ERROR_MESSAGE);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		boton_annadir.setFont(new Font("Dialog", Font.PLAIN, 15));
		boton_annadir.setBounds(10, 488, 165, 71);
		contentPanel.add(boton_annadir);
		
		boton_eliminar = new Button("Eliminar");
		boton_eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int posicion = table.getSelectedColumn();
				Extras.getInstance().getListado_filtroArrayList().remove(posicion);
				actualizarTabla();
				actualizar_boton_eliminar();
				try {
					llenar_combo();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		boton_eliminar.setFont(new Font("Dialog", Font.PLAIN, 15));
		boton_eliminar.setBounds(285, 488, 165, 71);
		contentPanel.add(boton_eliminar);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Acceder");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(Extras.getInstance().getListado_filtroArrayList().size() == 0) {
							JOptionPane.showMessageDialog(Seleccion_filtrado.this,"No se ha seleccionado un filtro","Error",JOptionPane.ERROR_MESSAGE);
						}
						else {
							dispose();
							Matriz_destino_origen matriz_destino_orige;
							try {
								matriz_destino_orige = new Matriz_destino_origen();
								matriz_destino_orige.setVisible(true);
							} catch (ClassNotFoundException | SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Extras.getInstance().getListado_filtroArrayList().removeAll(Extras.getInstance().getListado_filtroArrayList());
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		llenar_combo();
		actualizarTabla();
		actualizar_boton_eliminar();
	}
	public void llenar_combo() throws ClassNotFoundException, SQLException {
		ArrayList<String> listado = new ArrayList<String>();
		EntidadServicesBD ent = new EntidadServicesBD();
		listado = ent.listado_entidades_nombre();
		for(int contador = 0; contador<listado.size();contador++) {
			for(int contador2 = 0;contador2 < Extras.getInstance().getListado_filtroArrayList().size();contador2++) {
				if(Extras.getInstance().getListado_filtroArrayList().get(contador2).equals(listado.get(contador)) == true) {
					listado.remove(contador);
				}
			}
		}
		DefaultComboBoxModel<String> comb = new DefaultComboBoxModel<String>();
		comb.addAll(listado);
		comboBox.setModel(comb);
	}
	private void actualizarTabla() {
	String[] encabezado = {"Nombre"};		
	Object [] [] tabla = new Object[Extras.getInstance().getListado_filtroArrayList().size()][encabezado.length];		

	for(int i=0; i<Extras.getInstance().getListado_filtroArrayList().size(); i++){
		tabla [i] [0] = Extras.getInstance().getListado_filtroArrayList().get(i);
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
	public void actualizar_boton_eliminar() {
		if(table.getSelectedRowCount() == 1) {
			boton_eliminar.setEnabled(true);
		}
		else {
			boton_eliminar.setEnabled(false);
		}
	}
}
