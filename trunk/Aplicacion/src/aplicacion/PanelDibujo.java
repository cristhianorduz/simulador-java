package aplicacion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import red.Avenida;
import red.Punto;
import red.PuntoInterno;

public class PanelDibujo extends JPanel {
	
	private Aplicacion app;
	
	public PanelDibujo(Aplicacion app) {
		
		super();
		this.app = app;
		this.setBackground(Color.WHITE);
		
		// agrega el mouse listener que permite la selección
		// de elementos de la red en el panel de dibujo.
		setMouseListener(1);
	} // fin del constructor de clase
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 * 
	 * Este método dibuja las avenidas y todos los puntos
	 * de la red, incluidos los de acceso y salida.
	 */
	public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.WHITE);
        
        super.paintComponent(g2);
        
        Ellipse2D acceso = new Ellipse2D.Float(180, 1, 20, 20);
        Ellipse2D salida = new Ellipse2D.Float(180, 340, 20, 20);
        
        // Dibuja todas las avenidas
        Avenida[] avenidas = app.getAvenidas();
        
        if(avenidas!=null) {
        	
            Line2D linea;
            Punto o;
            Punto d;
            
        	for(int i=0; i<avenidas.length; i++) {
        		
        		o = avenidas[i].getOrigen();
        		d = avenidas[i].getDestino();
        		
        		linea = new Line2D.Double(o.getX()+10, o.getY()+10, d.getX()+10, d.getY()+10);
        		
        		if(avenidas[i].estaHabilitada()) {
        			g2.setColor(Color.black);
        		}
        		else {
        			
        			g2.setColor(Color.orange);
        		}
        		
                g2.setStroke(new BasicStroke(4));
                g2.draw(linea);
        	}
        }
        
        // Dibuja el punto de acceso
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));
        g2.setPaint(Color.blue);
        g2.fill(acceso);
        g2.setPaint(Color.black);

        g2.draw(acceso);
        
        // Dibuja el punto de salida
        g2.setPaint(Color.red);
        g2.fill(salida);
        g2.setPaint(Color.black);

        g2.draw(salida);
        
        // Dibuja todos los puntos internos
        PuntoInterno[] puntos = app.getPuntosInternos();
		
        if(puntos!=null) {
        	
            Ellipse2D punto;
            
        	for(int i=0; i<puntos.length; i++) {
    			
    			punto = new Ellipse2D.Double(puntos[i].getX(), puntos[i].getY(), 20, 20);
    			
    			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	        g2.setStroke(new BasicStroke(2));
    	        g2.setPaint(Color.yellow);
    	        g2.fill(punto);
    	        g2.setPaint(Color.black);
    	        
    	        g2.draw(punto);
    		}
        }
    } // fin del método paint
	
	/**
	 * Este método agrega un listener para capturar los elementos
	 * de la red de trasporte que el usuario seleciona con un click
	 * en el panel de dibujo.
	 */
	private void mouseListenerMostrarElementos() {

		// Capturador de evento Mouse Click.
		// Obtiene la posición del panel de dibujo donde se hizo click.
		this.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e){

				double x = e.getX();
		        double y = e.getY();
		        
		        // solicita a la aplicación que muestre el elemento
		        // de la posición (x, y) si este existe.
		        app.mostrarGraficoCoordenadas(x, y);
		      }
		});
	} // fin del método mouseListenerMostrarElementos
	
	/**
	 * Este método agrega un listener para capturar un punto en el
	 * panel de dibujo donde colocar un nuevo nodo.
	 */
	private void mouseListenerNuevoPunto() {
		
		// Capturador de evento Mouse Click.
		// Obtiene la posición del panel de dibujo donde se hizo click.
		this.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e){

				double x = e.getX();
		        double y = e.getY();
		        
		        if(app.existePuntoCoordenadas(x,y)) {
		        	
		        	app.mostrarMensajeAlerta("Ya existe un Nodo en esa posición.");
		        }
		        else {
		        	// reestablece el mouse listener para capturar
			        // elementos de la red seleccionados.
			        setMouseListener(1);
			        
			        // muestra un JDialog con los campos a completar
					// como datos del nuevo punto.
			        app.mostrarIngresarPunto(x, y);
		        }
		      }
		});
	} // fin del método mouseListenerNuevoPunto
	
	
	/**
	 * @param tipo
	 * 
	 * Este método se utiliza para establecer el listener del panel 
	 * de dibujo.El valor que se recibe como parámetro determina 
	 * el mouse listener que estará activo. 
	 */
	public void setMouseListener(int tipo) {
		
		// quita el mouse listener actual.
		if(this.getMouseListeners().length != 0) {
			
			this.removeMouseListener(this.getMouseListeners()[0]);
		}

		switch(tipo){
		
			// Mouse listener para seleccionar los elementos 
			// de la red de transporte en el panel de dibujo.
			case 1: mouseListenerMostrarElementos();
					break;
			
			// Mouse listener para fijar la posición de un nuevo punto
			// en la red.
			case 2: mouseListenerNuevoPunto();
					break;
		}
	} // fin del método setMouseListener
}