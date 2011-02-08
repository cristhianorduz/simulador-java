package aplicacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author Goti
 * 
 * Esta clase representa una ventana para mostrar
 * informacion acerca de la aplicación.
 */
public class VentanaAcercaDe extends JDialog {

	private Aplicacion app;
	
	private JTextArea infoAplicacion;
	private JButton botonOk;
	
	/**
	 * @param app
	 * 
	 * Constructor de clase.
	 */
	public VentanaAcercaDe(Aplicacion app) {
		
		super();
		
		// establece la aplicacion
		this.app = app;
		
		inicializarInterfaz();
	} // fin del constructor de clase
	
	
	/**
	 * Crea los componentes de la ventana y los agrega.
	 */
	private void inicializarInterfaz() {
		
		// Area de texto con información de la aplicación
		infoAplicacion = new JTextArea();
		infoAplicacion.setFont(new Font("Monospaced", Font.BOLD, 12));
		infoAplicacion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		infoAplicacion.setPreferredSize(new Dimension(300, 200));
		infoAplicacion.setEditable(false);
		infoAplicacion.setText(" Simulador de Tráfico\n" + 
								" Release 1.0.0\n" +
								" Fecha: 08/02/11\n\n" +
								" Autores:\n" +
								" Fausd, Juan Alejandro\n" +
								" Rubin, Daniel\n\n\n" +
								" http://code.google.com/p/simulador-java/");
		
		// Boton Ok
		botonOk = new JButton("Ok");
		botonOk.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				dispose();
			}
		});
		
		botonOk.addKeyListener(new KeyListener());
		
		// Layout
		setLayout();
	} // fin de método inicializarInterfaz
	
	/**
	 * Establece el layout del JDialog y agrega los componentes.
	 */
	private void setLayout() {
		
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		// Panel en ubicacion NORTH
		JPanel p1 = new JPanel();
		p1.setPreferredSize(new Dimension(400, 10));
		this.add(p1, BorderLayout.NORTH);
		
		// Panel en ubicacion WEST
		JPanel p2 = new JPanel();
		p2.setPreferredSize(new Dimension(20, 260));
		this.add(p2, BorderLayout.WEST);
		
		// Panel en ubicacion CENTER
		JPanel p3 = new JPanel();
		p3.setPreferredSize(new Dimension(350, 260));
		p3.add(infoAplicacion);
		this.add(p3, BorderLayout.CENTER);
		
		// Panel en ubicacion EAST
		JPanel p4 = new JPanel();
		p4.setPreferredSize(new Dimension(20, 260));
		this.add(p4, BorderLayout.EAST);
		
		// Panel en ubicacion SOUTH
		JPanel p5 = new JPanel();
		p5.setPreferredSize(new Dimension(400, 40));
		p5.add(botonOk);
		this.add(p5, BorderLayout.SOUTH);
		
	} // fin de método setLayout
	
	/**
	 * @author Goti
	 * 
	 * Esta clase se utiliza de manera auxiliar para implementar
	 * un key listener para la interfaz acerca de.
	 */
	private class KeyListener extends KeyAdapter {
		
        public void keyPressed(KeyEvent e) {
            
            String key = e.getKeyText(e.getKeyCode());
            key = key.toUpperCase();
            
            // maneja la pulsación de la tecla ENTER
            if (key.equals("INTRODUZCA")) {

            	dispose();
            }
        }
    } // fin de clase KeyListener
}

