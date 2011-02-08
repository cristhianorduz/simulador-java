package aplicacion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;

import red.Avenida;
import red.Punto;

/**
 * @author User
 * 
 * Esta clase se utilza para mostrar graficos de redes de transporte.
 * Se utiliza principalmente para mostrar resultados de consultas.
 */
public class VentanaDibujo extends JPanel {
	
	private Punto[] puntos;
	private Avenida[] avenidas;
	private JPanel panelDibujo;
	private JPanel panelCont;
	
	/**
	 * @param avenidas
	 * @param puntos
	 * 
	 * Constructor de clase
	 */
	public VentanaDibujo(Avenida[] avenidas, Punto[] puntos) {
		
		super();
		
		this.puntos = puntos;
		this.avenidas = avenidas;
		this.setBackground(Color.WHITE);
		
		// Muestra el gráfico de la nueva avenida
		
		panelDibujo= new JPanel();
		panelCont = new JPanel();
		panelCont.setBackground(Color.WHITE);
		panelCont.setBorder(BorderFactory.createTitledBorder("Resultado"));
		panelCont.setName("Panel de grafo");
		panelCont.setPreferredSize(new Dimension(420, 460));
		panelDibujo.setPreferredSize(new Dimension(420, 460));		
		panelCont.add(panelDibujo);
		this.add(panelCont);
		
	} // fin del constructor
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 * 
	 * Sobrecarga del método paint definido anteriormente. Básicamente,
	 * realiza la misma tarea que el método paint anterior. Se utiliza 
	 * para graficar redes según los parámetros.
	 */
	public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.WHITE);
        
        super.paintComponent(g2);
        
        Ellipse2D acceso = new Ellipse2D.Float(210, 10, 20, 20);
        Ellipse2D salida = new Ellipse2D.Float(210, 390, 20, 20);
        
        // Dibuja todas las avenidas
        if(avenidas!=null) {
        	
            Line2D linea;
            
            double ang=0.0, angSep=0.0;	// ángulo entre puntos y ángulo de separación.
            double tx,ty;	// variación de ´x´ y de ´y´ entre puntos de origen y destino
            int dist = 10;	// tamaño de la punta de flecha
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
        		
        		// ángulo entre puntos
        	    ang = Math.atan (ty/tx);
        	    
        	    if(tx<0) {// si tx es negativo aumentar 180 grados
        	    	
        	        ang += Math.PI;
        	    }
        	    
        	    // ángulo de separación
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
    } // fin del método paint sobrecargado

}
