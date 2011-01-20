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
        labelPuntoOrigen.setText("Punto de origen: ");
        
        fieldPuntoOrigen = new JTextField();
        fieldPuntoOrigen.setText("");
        
        // Campo Punto de destino
        labelPuntoDestino = new JLabel();
        labelPuntoDestino.setText("Punto de destino: ");
        
        fieldPuntoDestino = new JTextField();
        fieldPuntoDestino.setText("");
        
        // Campo Trafico maximo
        labelTraficoMax = new JLabel();
        labelTraficoMax.setText("Tráfico máximo: ");

        fieldTraficoMax = new JTextField();
        fieldTraficoMax.setText("");
        
    	// Campo Distancia
        labelDistancia = new JLabel();
        labelDistancia.setText("Distancia: ");

        fieldDistancia = new JTextField();
        fieldDistancia.setText("");

        // Botón Aceptar
		botonAceptar = new JButton();
		botonAceptar.setText("Aceptar");
		botonAceptar.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				if(validarCampos()) {
					
					String origen = fieldPuntoOrigen.getText();
					String destino = fieldPuntoDestino.getText();
					
					// Solo se puede tener una avenida entre pares origen,destino.
					// Si no existe una avenida para ese par de puntos,
					// solicita a la aplicacion que cree una.
					if(!aplicacion.existeAvenidaPuntos(origen, destino)) {
						
						long trafico = Long.parseLong(fieldTraficoMax.getText());

						double dist = Double.parseDouble(fieldDistancia.getText());
						
						aplicacion.agregarAvenidaEntrePuntos(origen, destino, trafico, dist);
						dispose();
					}
					else {		
						mostrarMensajeInfo("Ya existe una avenida para ese par de puntos!");
					}
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
        	ventanaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(ventanaLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(ventanaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, ventanaLayout.createSequentialGroup()
                        .addComponent(botonAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(botonCancelar)
                        .addContainerGap())
                    .addGroup(GroupLayout.Alignment.TRAILING, ventanaLayout.createSequentialGroup()
                        .addComponent(labelTitulo)
                        .addGap(28, 28, 28))
                    .addGroup(GroupLayout.Alignment.TRAILING, ventanaLayout.createSequentialGroup()
                        .addGroup(ventanaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(ventanaLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(labelDistancia)
                                .addComponent(labelTraficoMax))
                            .addComponent(labelPuntoDestino)
                            .addComponent(labelPuntoOrigen))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ventanaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(fieldPuntoOrigen, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(fieldPuntoDestino, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(fieldDistancia, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(fieldTraficoMax, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                        .addGap(45, 45, 45))))
        );
        ventanaLayout.setVerticalGroup(
        	ventanaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, ventanaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitulo)
                .addGap(28, 28, 28)
                .addGroup(ventanaLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldPuntoOrigen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPuntoOrigen))
                .addGap(18, 18, 18)
                .addGroup(ventanaLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldPuntoDestino, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPuntoDestino))
                .addGap(18, 18, 18)
                .addGroup(ventanaLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTraficoMax)
                    .addComponent(fieldTraficoMax, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ventanaLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDistancia)
                    .addComponent(fieldDistancia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(ventanaLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonCancelar))
                .addContainerGap())
        );
	} // fin del método setLayout
	
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

} // fin de clase VentanaNuevaAvenida
