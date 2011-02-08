package aplicacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author User
 * 
 * Esta clase representa una ventana para ingresar
 * nuevos puntos a la red de transporte.
 */
public class VentanaNuevoPunto extends JDialog {

	private Aplicacion app;
	
	private JLabel labelTitulo;
	private JLabel labelNombre;
	private JTextField fieldNombre;
	private JLabel labelCosto;
	private JTextField fieldCosto;
	private JButton botonAceptar;
	private JButton botonCancelar;
	
	private double x;
	private double y;
	
	/**
	 * Constructor de clase.
	 */
	public VentanaNuevoPunto(double x, double y, Aplicacion app) {
		
		super();
		
		// establece la aplicacion
		this.app = app;
		// inicializa las coordenadas
		this.x = x;
		this.y = y;
		
		inicializarInterfaz();
	} // fin del constructor de clase
	
	/**
	 * Crea los componentes del JDialog y los agrega.
	 */
	private void inicializarInterfaz() {
		
		// Titulo
        labelTitulo = new JLabel();
        labelTitulo.setFont(new Font("sansserif", Font.BOLD, 14));
        labelTitulo.setText("Datos del nuevo punto:");

        // Campo Nombre
        labelNombre = new JLabel();
        labelNombre.setText("Nombre: ");
        
        fieldNombre = new JTextField();
        fieldNombre.setText("");
        fieldNombre.setPreferredSize(new Dimension(90, 20));
        
        // Campo Costo
        labelCosto = new JLabel();
        labelCosto.setText("    Costo: ");

        fieldCosto = new JTextField();
        fieldCosto.setText("");
        fieldCosto.setPreferredSize(new Dimension(90, 20));
        fieldCosto.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

				if(verificarCampos()) {
					
					app.agregarPuntoPosicion(x, y, fieldNombre.getText(), fieldCosto.getText());
					dispose();
				}
			}
		});

        // Botón Aceptar
		botonAceptar = new JButton();
		botonAceptar.setText("Aceptar");
		botonAceptar.setName("Aceptar");
		botonAceptar.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

				if(verificarCampos()) {
					
					app.agregarPuntoPosicion(x, y, fieldNombre.getText(), fieldCosto.getText());
					dispose();
				}
			}
		});
		botonAceptar.addKeyListener(new KeyListener());
		
		// Botón Cancelar
		botonCancelar = new JButton();
        botonCancelar.setText("Cancelar");
        botonCancelar.setName("Cancelar");
        botonCancelar.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

				dispose();
			}
		});
        botonCancelar.addKeyListener(new KeyListener());
        
        setLayout();

	} // fin del método inicializar
	
	/**
	 * Establece el Layout del JDialog y agrega los componentes.
	 */
	private void setLayout() {
		
		BorderLayout ventanaLayout = new BorderLayout();
        this.getContentPane().setLayout(ventanaLayout);
        
        // Titulo
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(labelTitulo);
        panelSuperior.setPreferredSize(new Dimension(280, 40));
        
        // Campos
        JPanel panelCentral = new JPanel();
        panelCentral.setPreferredSize(new Dimension(280, 200));
        
        JPanel panelNombre = new JPanel();
        panelNombre.add(labelNombre);
        panelNombre.add(fieldNombre);
        panelNombre.setPreferredSize(new Dimension(280, 40));
        
        JPanel panelCosto = new JPanel();
        panelCosto.add(labelCosto);
        panelCosto.add(fieldCosto);
        panelCosto.setPreferredSize(new Dimension(280, 40));
        
        panelCentral.add(panelNombre);
        panelCentral.add(panelCosto);
        
        // Botones
        JPanel panelInferior = new JPanel();
        panelInferior.add(botonAceptar);
        panelInferior.add(botonCancelar);
        panelInferior.setPreferredSize(new Dimension(280, 40));
        
        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(panelCentral, BorderLayout.CENTER);
        this.add(panelInferior, BorderLayout.SOUTH);
		
	} // fin del método setLayout
	
	/**
	 * @return
	 * 
	 * Este método determina si todos los valores ingresados en los
	 * campos son correctos. En caso de que lo sean, devuelve true.
	 * En caso contrario, emite un mensaje de alerta al usuario
	 * indicando el error correspondiente y devuelve false.
	 */
	private boolean verificarCampos() {
		
		String nombre = fieldNombre.getText();
		String costo = fieldCosto.getText();
		
		if(!nombre.equals("")) {
			
			if(!app.existePuntoNombre(nombre)) {
				
				try {
					
					double c = Double.parseDouble(costo);
					
					if(c>0) {
						return true;
					}
					else {
						mostrarMensajeAlerta("El costo ingresado debe ser positivo.");
					}
				}
				catch(Exception e) {
					mostrarMensajeAlerta("El costo ingresado no es un valor correcto.");
					return false;
				}
			}
			else {
				mostrarMensajeAlerta("El nombre ingresado ya existe.");
			}
		}
		else {
			mostrarMensajeAlerta("El campo nombre está vacío");
		}
		
		return false;
		
	} // fin del método verificarCampos
	
	/**
	 * @param mensaje
	 * 
	 * Muestra un mensaje informativo con la cadena que 
	 * recibe como parámetro mediante un dialog predefinido 
	 * de JOptionPane.
	 */
	public void mostrarMensajeInfo(String mensaje) {
		
		JOptionPane.showMessageDialog(this, mensaje);
	}
	
	/**
	 * @param mensaje
	 * 
	 * Muestra un mensaje de alerta con la cadena que 
	 * recibe como parámetro mediante un dialog predefinido 
	 * de JOptionPane.
	 */
	public void mostrarMensajeAlerta(String mensaje) {
		
		JOptionPane.showMessageDialog(this, mensaje, "Inane warning", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * @param mensaje
	 * 
	 * Muestra un mensaje de error con la cadena que 
	 * recibe como parámetro mediante un dialog predefinido 
	 * de JOptionPane.
	 */
	public void mostrarMensajeError(String mensaje) {
		
		JOptionPane.showMessageDialog(this, mensaje, "Inane error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * @author Goti
	 * 
	 * Esta clase se utiliza de manera auxiliar para implementar
	 * un key listener para la interfaz de nuevo punto.
	 */
	private class KeyListener extends KeyAdapter {
		
        public void keyPressed(KeyEvent e) {
        	
            String targ = "ENTER";
            
            String key = e.getKeyText(e.getKeyCode());
            key = key.toUpperCase();
            
            // el componente que despertó el evento
            String comp = e.getComponent().getName();
            
            // maneja la pulsación de la tecla ENTER
            if (key.equals("INTRODUZCA")) {
            	
            	if(comp.equals("Aceptar")) {
            		
            		if(verificarCampos()) {
    					
    					app.agregarPuntoPosicion(x, y, fieldNombre.getText(), fieldCosto.getText());
    					dispose();
    				}
            	}
            	else {
            		dispose();
            	}
            }
        }
    } // fin de clase KeyListener
	
} // fin de clase VentanaNuevoPunto