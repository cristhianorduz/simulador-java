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
import javax.swing.LayoutStyle;

/**
 * @author User
 * 
 * Esta clase representa una ventana para ingresar
 * nuevos puntos a la red de transporte.
 */
public class VentanaNuevaAvenida extends JDialog {
	
	private Aplicacion aplicacion;
	
	private JLabel labelTitulo;
	private JLabel labelPuntoOrigen;
	private JTextField fieldPuntoOrigen;
	private JLabel labelPuntoDestino;
	private JTextField fieldPuntoDestino;
	private JLabel labelTraficoMax;
	private JTextField fieldTraficoMax;
	private JLabel labelDistancia;
	private JTextField fieldDistancia;
	private JButton botonAceptar;
	private JButton botonCancelar;
	
	/**
	 * Constructor de clase.
	 */
	public VentanaNuevaAvenida(Aplicacion app) {
		
		super();
		
		aplicacion = app;
		
		inicializar();
	} // fin del constructor de clase
	
	/**
	 * Crea los componentes del JDialog y los agrega.
	 */
	private void inicializar() {
		
		// Titulo
        labelTitulo = new JLabel();
        labelTitulo.setFont(new Font("sansserif", Font.BOLD, 14));
        labelTitulo.setText("Datos de la nueva avenida:");

        // Campo Punto de origen
        labelPuntoOrigen = new JLabel();
        labelPuntoOrigen.setText("  Punto de origen: ");
        
        fieldPuntoOrigen = new JTextField();
        fieldPuntoOrigen.setText("");
        fieldPuntoOrigen.setPreferredSize(new Dimension(90, 20));
        
        // Campo Punto de destino
        labelPuntoDestino = new JLabel();
        labelPuntoDestino.setText("Punto de destino: ");
        
        fieldPuntoDestino = new JTextField();
        fieldPuntoDestino.setText("");
        fieldPuntoDestino.setPreferredSize(new Dimension(90, 20));
        
        // Campo Trafico maximo
        labelTraficoMax = new JLabel();
        labelTraficoMax.setText("   Tráfico máximo: ");

        fieldTraficoMax = new JTextField();
        fieldTraficoMax.setText("");
        fieldTraficoMax.setPreferredSize(new Dimension(90, 20));
        
    	// Campo Distancia
        labelDistancia = new JLabel();
        labelDistancia.setText("               Distancia: ");

        fieldDistancia = new JTextField();
        fieldDistancia.setText("");
        fieldDistancia.setPreferredSize(new Dimension(90, 20));
        fieldDistancia.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				manejarEvento();
			}
		});

        // Botón Aceptar
		botonAceptar = new JButton();
		botonAceptar.setText("Aceptar");
		botonAceptar.setName("Aceptar");
		botonAceptar.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				manejarEvento();
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
        
        JPanel panelOrigen = new JPanel();
        panelOrigen.add(labelPuntoOrigen);
        panelOrigen.add(fieldPuntoOrigen);
        panelOrigen.setPreferredSize(new Dimension(280, 40));
        
        JPanel panelDestino = new JPanel();
        panelDestino.add(labelPuntoDestino);
        panelDestino.add(fieldPuntoDestino);
        panelDestino.setPreferredSize(new Dimension(280, 40));
        
        JPanel panelTrafico = new JPanel();
        panelTrafico.add(labelTraficoMax);
        panelTrafico.add(fieldTraficoMax);
        panelTrafico.setPreferredSize(new Dimension(280, 40));
        
        JPanel panelDistancia = new JPanel();
        panelDistancia.add(labelDistancia);
        panelDistancia.add(fieldDistancia);
        panelDistancia.setPreferredSize(new Dimension(280, 40));
        
        panelCentral.add(panelOrigen);
        panelCentral.add(panelDestino);
        panelCentral.add(panelTrafico);
        panelCentral.add(panelDistancia);
        
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
	 * Ejecuta las acciones de obtención de valores y verificación
	 * luego de completarse el formulario para ingresar nueva avenida.
	 */
	private void manejarEvento() {
		
		if(validarCampos()) {
			
			String origen = fieldPuntoOrigen.getText();
			String destino = fieldPuntoDestino.getText();
			
			// Solo se puede tener una avenida entre pares origen,destino.
			// Si no existe una avenida para ese par de puntos,
			// solicita a la aplicacion que cree una.
			if(!aplicacion.existeAvenidaPuntos(origen, destino)) {
				
				if(aplicacion.avenidaAtraviesaPuntos(origen, destino)) {
					
					int n = JOptionPane.showConfirmDialog(
						    this,
						    "La avenida atraviesa uno o más puntos  de la red. Desea dibujarla de todos modos?",
						    "An Inane Question",
						    JOptionPane.YES_NO_OPTION);
					
					if(n == 0) {	// decidió dibujarla de todos modos
						
						long trafico = Long.parseLong(fieldTraficoMax.getText());

						double dist = Double.parseDouble(fieldDistancia.getText());
						
						aplicacion.agregarAvenidaEntrePuntos(origen, destino, trafico, dist);
						
						dispose();
					}
				}
				else {	// camino despejado!
					
					long trafico = Long.parseLong(fieldTraficoMax.getText());

					double dist = Double.parseDouble(fieldDistancia.getText());
					
					aplicacion.agregarAvenidaEntrePuntos(origen, destino, trafico, dist);
					
					dispose();
				}
			}
			else {
				
				mostrarMensajeInfo("Ya existe una avenida para ese par de puntos!");
			}
		}
		
	} // fin del método manejarEvento
	
	/**
	 * @return
	 * 
	 * Este método verifica los valores ingresados en los campos, es decir,
	 * si se han ingresado y si corresponden a:
	 * - Puntos de origen y destino que han sido cargados.
	 * - Valores de tráfico máximo válidos (debe ser un número natural)
	 * - Valores de distancia válidos (no pueden ser negativos)
	 */
	private boolean validarCampos() {
		
		String origen = fieldPuntoOrigen.getText();
		String destino = fieldPuntoDestino.getText();
		
		if(aplicacion.existePuntoNombre(origen)) {
			
			if(aplicacion.existePuntoNombre(destino)) {
				
				try {
					long trafico = Long.parseLong(fieldTraficoMax.getText());
					
					if(trafico > 0) {
						
						try {
							
							double dist = Double.parseDouble(fieldDistancia.getText());
							
							if(dist > 0) {
								return true;
							}
							else {
								JOptionPane.showMessageDialog(this, "El valor ingresado como distancia no es válido.");
								return false;
							}
						}
						catch (Exception e) {
							JOptionPane.showMessageDialog(this, "El valor ingresado como distancia no es válido.");
							return false;
						}
					}
					else {
						JOptionPane.showMessageDialog(this, "El valor ingresado como tráfico máximo no es válido.");
						return false;
					}
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(this, "El valor ingresado como tráfico máximo no es válido.");
					return false;
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "El punto ingresado como destino no existe!");
				return false;
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "El punto ingresado como origen no existe!");
			return false;
		}
	} // fin del método validarCampos
	
	/**
	 * @param mensaje
	 * 
	 * Muestra un mensaje informativo con la cadena que 
	 * recibe como parámetro mediante un dialog predefinido 
	 * de JOptionPane.
	 */
	public void mostrarMensajeInfo(String mensaje) {
		
		JOptionPane.showMessageDialog(this, mensaje);
	} // fin del método mostrarMensajeInfo
	
	/**
	 * @author Goti
	 * 
	 * Esta clase se utiliza de manera auxiliar para implementar
	 * un key listener para la interfaz de nueva avenida.
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
            		
            		manejarEvento();
            	}
            	else {
            		dispose();
            	}
            }
        }
    } // fin de clase KeyListener

} // fin de clase VentanaNuevaAvenida
