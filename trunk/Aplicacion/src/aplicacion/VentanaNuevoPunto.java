package aplicacion;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
        
        // Campo Costo
        labelCosto = new JLabel();
        labelCosto.setText("Costo: ");

        fieldCosto = new JTextField();
        fieldCosto.setText("");

        // Botón Aceptar
		botonAceptar = new JButton();
		botonAceptar.setText("Aceptar");
		botonAceptar.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

				if(verificarCampos()) {
					
					app.agregarPuntoPosicion(x, y, fieldNombre.getText(), fieldCosto.getText());
					dispose();
				}
			}
		});
		
		// Botón Cancelar
		botonCancelar = new JButton();
        botonCancelar.setText("Cancelar");
        botonCancelar.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

				dispose();
			}
		});
        
        setLayout();

	} // fin del método inicializar
	
	/**
	 * Establece el Layout del JDialog.
	 */
	private void setLayout() {
		
        GroupLayout ventanaLayout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(ventanaLayout);
        
        ventanaLayout.setHorizontalGroup(
        		ventanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ventanaLayout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addGroup(ventanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ventanaLayout.createSequentialGroup()
                            .addComponent(botonAceptar)
                            .addGap(18, 18, 18)
                            .addComponent(botonCancelar)
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ventanaLayout.createSequentialGroup()
                            .addComponent(labelTitulo)
                            .addGap(28, 28, 28))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ventanaLayout.createSequentialGroup()
                            .addGroup(ventanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelCosto)
                                .addComponent(labelNombre))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(ventanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(fieldNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                .addComponent(fieldCosto, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                            .addGap(45, 45, 45))))
            );
        	ventanaLayout.setVerticalGroup(
        		ventanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ventanaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(labelTitulo)
                    .addGap(28, 28, 28)
                    .addGroup(ventanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelNombre))
                    .addGap(18, 18, 18)
                    .addGroup(ventanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelCosto))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                    .addGroup(ventanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(botonAceptar)
                        .addComponent(botonCancelar))
                    .addContainerGap())
            );
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
	
} // fin de clase VentanaNuevoPunto