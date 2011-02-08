package aplicacion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
	private double xOrigen;		// coordenada x del primer nodo seleccionado
	private double yOrigen;		// coordenada y del primer nodo seleccionado
	private boolean capturado;	// se utiliza para la selecci�n de m�s de un nodo
	
	public PanelDibujo(Aplicacion app) {
		
		super();
		this.app = app;
		this.setBackground(Color.WHITE);
		capturado = false;
		
		// agrega el mouse listener que permite la selecci�n
		// de elementos de la red en el panel de dibujo.
		setMouseListener(1);
	} // fin del constructor de clase
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 * 
	 * Este m�todo dibuja las avenidas y todos los puntos
	 * de la red, incluidos los de acceso y salida.
	 */
	public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.WHITE);
        
        super.paintComponent(g2);
        
        Ellipse2D acceso = new Ellipse2D.Float(170, 10, 20, 20);
        Ellipse2D salida = new Ellipse2D.Float(170, 390, 20, 20);
        
        // Dibuja todas las avenidas
        Avenida[] avenidas = app.getAvenidas();
        
        if(avenidas!=null) {
        	
            Line2D linea;
            
            double ang=0.0, angSep=0.0;	// �ngulo entre puntos y �ngulo de separaci�n.
            double tx,ty;	// variaci�n de �x� y de �y� entre puntos de origen y destino
            int dist = 10;	// tama�o de la punta de flecha
            Punto o;	// punto de origen de la recta
            Punto d;	// punto de destino de la recta
            double pdX, pdY;
            double p1X, p1Y,p2X, p2Y;	// puntos auxiliares para dibujar punta de flecha
            
        	for(int i=0; i<avenidas.length; i++) {
        		
        		o = avenidas[i].getOrigen();
        		d = avenidas[i].getDestino();
        		
        		 /* (la coordenadas de la ventana es al revez)
                calculo de la variacion de "x" y "y" para hallar el angulo **/

        		ty = -(o.getY() - d.getY())*1.0;
        		tx = (o.getX() - d.getX())*1.0;
        		
        		// �ngulo entre puntos
        	    ang = Math.atan (ty/tx);
        	    
        	    if(tx<0) {// si tx es negativo aumentar 180 grados
        	    	
        	        ang += Math.PI;
        	    }
        	    
        	    // �ngulo de separaci�n
        	    angSep = 25.0;
        	    
        	    pdX = d.getX() + 10*Math.cos(ang);
        	    pdY = d.getY() - 10*Math.sin(ang);
        	    
        	    p1X = (pdX + dist*Math.cos (ang-Math.toRadians(angSep)));
        	    p1Y = (pdY - dist*Math.sin (ang-Math.toRadians(angSep)));
        	    p2X = (pdX + dist*Math.cos (ang+Math.toRadians(angSep)));
        	    p2Y = (pdY - dist*Math.sin (ang+Math.toRadians(angSep)));
        	    
        		linea = new Line2D.Double(o.getX(), o.getY(), d.getX(), d.getY());
        		
        		if(avenidas[i].estaHabilitada()) {
        			g2.setColor(Color.black);
        		}
        		else {
        			
        			g2.setColor(Color.orange);
        		}
        		
                g2.setStroke(new BasicStroke(4));
        	    // dibuja la linea
                g2.draw(linea);
                //dibuja la punta
                g2.setStroke(new BasicStroke(3));
                g2.draw(new Line2D.Double(p1X, p1Y, pdX, pdY));
                g2.draw(new Line2D.Double(p2X, p2Y, pdX, pdY));
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
    			
    			punto = new Ellipse2D.Double(puntos[i].getX()-10, puntos[i].getY()-10, 20, 20);
    			
    			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	        g2.setStroke(new BasicStroke(2));
    	        g2.setPaint(Color.yellow);
    	        g2.fill(punto);
    	        g2.setPaint(Color.black);
    	        
    	        g2.draw(punto);
    		}
        }
    } // fin del m�todo paint
	
	
	/**
	 * Este m�todo agrega un listener para capturar los elementos
	 * de la red de trasporte que el usuario seleciona con un click
	 * en el panel de dibujo.
	 */
	private void mouseListenerMostrarElementos() {

		// Capturador de evento Mouse Click.
		// Obtiene la posici�n del panel de dibujo donde se hizo click.
		this.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e){

				double x = e.getX();
		        double y = e.getY();
		        
		        // solicita a la aplicaci�n que muestre el elemento
		        // de la posici�n (x, y) si este existe.
		        app.mostrarGraficoCoordenadas(x, y);
		      }
		});
	} // fin del m�todo mouseListenerMostrarElementos
	
	/**
	 * Este m�todo agrega un listener para capturar un punto en el
	 * panel de dibujo donde colocar un nuevo nodo.
	 */
	private void mouseListenerNuevoPunto() {
		
		// Capturador de evento Mouse Click.
		// Obtiene la posici�n del panel de dibujo donde se hizo click.
		this.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e){

				double x = e.getX();
		        double y = e.getY();
		        
		        if(app.existePuntoCoordenadas(x,y)) {
		        	
		        	app.mostrarMensajeAlerta("Ya existe un Nodo en esa posici�n.");
		        }
		        else {
		        	
		        	if(app.avenidaPasaPorPunto(x,y)) {
		        		
		        		int n = JOptionPane.showConfirmDialog(
		        			    app,
		        			    "Una avenida pasa por esa posici�n, deseas crear el punto de todas maneras?",
		        			    "An Inane Question",
		        			    JOptionPane.YES_NO_OPTION);
		        		
		        		// reestablece el mouse listener para capturar
				        // elementos de la red seleccionados.
				        setMouseListener(1);
				        
		        		if(n==0) {
					        
					        // muestra un JDialog con los campos a completar
							// como datos del nuevo punto.
					        app.mostrarIngresarPunto(x, y);
		        		}
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
		      }
		});
	} // fin del m�todo mouseListenerNuevoPunto
	
	
	/**
	 * Este m�todo agrega un listener que espera la selecci�n de un punto
	 * y luego invoca al m�todo para eliminarlo. Si no se selecciona un punto
	 * v�lido, se emite un mensaje advirtiendo que ning�n punto fue eliminado.
	 */
	private void mouseListenerEliminarPunto() {
		
		// Capturador de evento Mouse Click.
		// Obtiene la posici�n del panel de dibujo donde se hizo click.
		this.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e){

				double x = e.getX();
		        double y = e.getY();
		        
		        if(app.existePuntoCoordenadas(x,y)) {
		        	
		        	app.eliminarPunto(x, y);
		        }
		        else {
		        	app.mostrarMensajeInfo("No se ha eliminado ning�n punto.");
		        }
		        
		        // Reestablece el mouse listener
		        setMouseListener(1);
		      }
		});
	} // fin del m�todo mouseListenerEliminarPunto
	
	/**
	 * Este m�todo agrega un listener que espera la selecci�n de una avenida
	 * y luego invoca al m�todo para eliminarlo. Si no se selecciona una avenida
	 * v�lida, se emite un mensaje advirtiendo que no fue eliminada ninguna avenida.
	 */
	private void mouseListenerEliminarAvenida() {
		
		// Capturador de evento Mouse Click.
		// Obtiene la posici�n del panel de dibujo donde se hizo click.
		this.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e){

				double x = e.getX();
		        double y = e.getY();
		        
		        if(app.existeAvenidaCoordenadas(x,y)) {
		        	
		        	app.eliminarAvenida(x, y);
		        }
		        else {
		        	app.mostrarMensajeInfo("No se ha eliminado ninguna avenida.");
		        }

		        // Reestablece el mouse listener
		        setMouseListener(1);
		      }
		});
	} // fin del m�todo mouseListenerEliminarAvenida
	
	
	/**
	 * Este manejador prepara el panel para que obtenga los nodos seleccionados
	 * y efect�e la consulta con ellos.
	 */
	private void mouseListenerCaminoMasCorto() {
		
		// Capturador de evento Mouse Click.
		// Obtiene la posici�n del panel de dibujo donde se hizo click.
		this.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e){

				double x = e.getX();
		        double y = e.getY();
		        
		        if(app.existePuntoCoordenadas(x,y)) {
		        	
		        	if(!capturado) {	// ya se captur� el primer nodo
		        		
		        		capturado = true;
		        		xOrigen = x;
		        		yOrigen = y;
		        		
		        		app.mostrarMensajeInfo("Haz click en el panel para seleccionar el nodo destino.");
		        	}
		        	else {	// ya hay dos nodos capturados
		        		
		        		double dist = Math.sqrt(Math.pow(xOrigen-x, 2) + Math.pow(yOrigen-y, 2));
		        		
		        		if(dist > 10) {	// no se seleccion� el mismo punto
		        		
		        			app.obtenerCaminoMasCorto(xOrigen, yOrigen, x, y);
		        			// Reestablece el mouse listener
					        setMouseListener(1);
					        capturado = false;
		        		}
		        		else {
		        			
		        			app.mostrarMensajeInfo("Debes seleccionar dos puntos distintos.");
		        		}
		        	}
		        }
		        else {
		        	
		        	app.mostrarMensajeInfo("No se han seleccionado pares de puntos v�lidos.");
		        	
		        	// Reestablece el mouse listener
			        setMouseListener(1);
			        capturado = false;
		        }
			}
		});

	} // fin del m�todo mouseListenerCaminoMasCorto
	
	/**
	 * Este manejador prepara el panel para que obtenga los nodos seleccionados
	 * y efect�e la consulta con ellos.
	 */
	private void mouseListenerCaminoMenosPeajes() {
		
		// Capturador de evento Mouse Click.
		// Obtiene la posici�n del panel de dibujo donde se hizo click.
		this.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e){

				double x = e.getX();
		        double y = e.getY();
		        
		        if(app.existePuntoCoordenadas(x,y)) {
		        	
		        	if(!capturado) {	// ya se captur� el primer nodo
		        		
		        		capturado = true;
		        		xOrigen = x;
		        		yOrigen = y;
		        		
		        		app.mostrarMensajeInfo("Haz click en el panel para seleccionar el nodo destino.");
		        	}
		        	else {	// ya hay dos nodos capturados
		        		
		        		double dist = Math.sqrt(Math.pow(xOrigen-x, 2) + Math.pow(yOrigen-y, 2));
		        		
		        		if(dist > 10) {	// no se seleccion� el mismo punto
		        		
		        			app.obtenerCaminoMenosPeajes(xOrigen, yOrigen, x, y);
		        			// Reestablece el mouse listener
					        setMouseListener(1);
					        capturado = false;
		        		}
		        		else {
		        			
		        			app.mostrarMensajeInfo("Debes seleccionar dos puntos distintos.");
		        		}
		        	}
		        }
		        else {
		        	
		        	app.mostrarMensajeInfo("No se han seleccionado pares de puntos v�lidos.");
		        	
		        	// Reestablece el mouse listener
			        setMouseListener(1);
			        capturado = false;
		        }
			}
		});

	} // fin del m�todo mouseListenerCaminoMenosPeajes
	
	/**
	 * Este manejador prepara el panel para que obtenga el nodo seleccionado
	 * y efect�e la consulta
	 */
	private void mouseListenerPuntosAlcanzables() {
		
		// Capturador de evento Mouse Click.
		// Obtiene la posici�n del panel de dibujo donde se hizo click.
		this.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e){

				double x = e.getX();
		        double y = e.getY();
		        
		        if(app.existePuntoCoordenadas(x,y)) {
		        	
		        	app.obtenerPuntosAlcanzables(x, y);
		        	
		        	// Reestablece el mouse listener
			        setMouseListener(1);
		        }
		        else {
		        	
		        	app.mostrarMensajeInfo("No se ha seleccionado un punto v�lido.");
		        	
		        	// Reestablece el mouse listener
			        setMouseListener(1);
		        }
			}
		});

	} // fin del m�todo mouseListenerPuntosAlcanzables
	
	/**
	 * Este manejador prepara el panel para que obtenga el nodo seleccionado
	 * y efect�e la consulta
	 */
	private void mouseListenerPuntosMenosNPeajes() {
		
		// Capturador de evento Mouse Click.
		// Obtiene la posici�n del panel de dibujo donde se hizo click.
		this.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e){

				double x = e.getX();
		        double y = e.getY();
		        
		        if(app.existePuntoCoordenadas(x,y)) {
		        	
		        	app.obtenerPuntosMenosNPeajes(x, y);
		        }
		        else {
		        	
		        	app.mostrarMensajeInfo("No se ha seleccionado un punto v�lido.");
		        	
		        	// Reestablece el mouse listener
			        setMouseListener(1);
		        }
			}
		});

	} // fin del m�todo mouseListenerPuntosMenosNPeajes
	
	/**
	 * @param tipo
	 * 
	 * Este m�todo se utiliza para establecer el listener del panel 
	 * de dibujo.El valor que se recibe como par�metro determina 
	 * el mouse listener que estar� activo. 
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
			
			// Mouse listener para fijar la posici�n de un nuevo punto.
			case 2: mouseListenerNuevoPunto();
					break;
					
			// Mouse listener para crear una nueva avenida.
			//case 3: mouseListenerNuevaAvenida();
					
			// Mouse listener para eliminar un punto
			case 4: mouseListenerEliminarPunto();
					break;
					
			// Mouse listener para eliminar una avenida
			case 5: mouseListenerEliminarAvenida();
					break;
			
			// Mouse listener para consulta de camino m�s corto
			case 6: mouseListenerCaminoMasCorto();
					break;
				
			// Mouse listener para consulta de camino con menos peajes
			case 7: mouseListenerCaminoMenosPeajes();
					break;
			
			// Mouse listener para consulta de puntos alcanzables
			case 8: mouseListenerPuntosAlcanzables();
					break;
					
			// Mouse listener para consulta puntos alcanzables sin pasar por mas de N peajes
			case 9: mouseListenerPuntosMenosNPeajes();
					break;
		}
	} // fin del m�todo setMouseListener
}