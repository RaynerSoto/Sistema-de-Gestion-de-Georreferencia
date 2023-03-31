package Interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Desarrollo.Entidad;
import Desarrollo.Errores;
import Desarrollo.Extras;
import Desarrollo.Persona;
import Desarrollo.Transporte;
import Servicios.EntidadServices;
import Servicios.PersonaServices;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.awt.Choice;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;
import java.awt.Toolkit;

public class Matriz_destino_origen extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Matriz_destino_origen dialog = new Matriz_destino_origen();
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
	public Matriz_destino_origen() throws ClassNotFoundException, SQLException {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Jordan\\Desktop\\Poty\\pROYECTO\\Tranporte Cujae v1.1\\Imagenes\\Isotipo.png"));
		setModal(true);
		ArrayList<String>listado_municipios_entidades = new ArrayList<String>();
		setTitle("Matriz");
		setBounds(100, 100, 802, 629);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Matriz Destino-Origen");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 11, 336, 28);
		contentPanel.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 758, 509);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						Seleccion_filtrado fil;
						try {
							fil = new Seleccion_filtrado();
							fil.setVisible(true);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException  e2) {
							JOptionPane.showMessageDialog(Matriz_destino_origen.this,"Error al encontrar la BD","Error",JOptionPane.ERROR_MESSAGE);
							dispose();
							Interfaz_principal inter = new Interfaz_principal();
							inter.setVisible(true);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			
		}
		actualizarTabla();
	}
	
	private void actualizarTabla() throws ClassNotFoundException, SQLException {
		EntidadServices enti = new EntidadServices();
		PersonaServices perso = new PersonaServices();
		ArrayList<String>listado_municipios_entidades = enti.listado_municipios_entidades(Extras.getInstance().getListado_filtroArrayList());
		String[] encabezado = new String[listado_municipios_entidades.size()+2];
		encabezado[0] ="Matriz";
		for(int contador = 0; contador<listado_municipios_entidades.size();contador++) {
			String a =  listado_municipios_entidades.get(contador);
			encabezado[contador+1] = a;
		}
		encabezado[listado_municipios_entidades.size()+1] = "Total";
		ArrayList<String>listado_municipios_personas = new ArrayList<String>();
		for(int contador = 0; contador<Extras.getInstance().getListado_filtroArrayList().size();contador++){
			ArrayList<String>lista = perso.listado_personas_municipioXentidad(Extras.getInstance().getListado_filtroArrayList().get(contador));
			for(int contador2 = 0; contador2<lista.size();contador2++) {
				boolean validar = false;
				for(int contador3 = 0; contador3<listado_municipios_personas.size() && validar ==false;contador3++) {
					if(lista.get(contador2).equals(listado_municipios_personas.get(contador3)) == true) {
						validar = true;
					}
				}
				if(validar == false) {
					listado_municipios_personas.add(lista.get(contador2));
				}
			}
		}
		String [] encabezado_personas = new String[listado_municipios_personas.size()];
		for(int contador = 0; contador < listado_municipios_personas.size();contador++) {
			encabezado_personas[contador] = listado_municipios_personas.get(contador);
		}
		int encabezado_longitud = encabezado.length;
		int municipios_personas = encabezado_personas.length;
		Object [] [] tabla = new Object[municipios_personas][encabezado_longitud];
		for(int contador = 0; contador < municipios_personas;contador++) {
			if(listado_municipios_personas.get(contador).equals("") == true) {
				tabla[contador][0] = "MUNICIPIO DESCONOCIDO";
			}
			else {
				tabla[contador][0] = listado_municipios_personas.get(contador);
			}
		}
		for(int contador = 0 ;contador <listado_municipios_personas.size();contador++) {
			Long cantidad = (long) 0;
			int posicion_final = 0;
			for(int contador2 = 0; contador2<listado_municipios_entidades.size();contador2 ++) {
				String destino = listado_municipios_entidades.get(contador2);
				String origen = listado_municipios_personas.get(contador);
				Long valor = (long) 0;
				for(int contador3 = 0; contador3<Extras.getInstance().getListado_filtroArrayList().size();contador3++) {
					valor = valor + enti.calculo(origen, destino,Extras.getInstance().getListado_filtroArrayList().get(contador3));
					cantidad = cantidad + enti.calculo(origen, destino,Extras.getInstance().getListado_filtroArrayList().get(contador3));
				}
				tabla[contador][contador2+1] = valor;
				posicion_final = contador2;
			}
			tabla[contador][posicion_final+2] = cantidad;
		}
		DefaultTableModel defaultTableModel = new DefaultTableModel(tabla,encabezado){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
		table.setModel(defaultTableModel);
	}
}
