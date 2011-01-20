package red;

import java.io.Serializable;

/**
 * @author User
 * 
 * Esta clase representa los puntos de intersección entre avenidas.
 * Contiene un atributo de nombre que lo representa.
 */
public class Punto implements Serializable {
	
	private String nombre;
	private double posX;
	private double posY;
	
	/**
	 * @param nombre
	 * @param x
	 * @param y
	 * 
	 * Constructor de clase.
	 */
	public Punto(String nombre, double x, double y) {
		
		this.nombre = nombre;
		this.posX = x;
		this.posY = y;
	}
	
	/**
	 * @return double
	 * 
	 * Devuelve la coordenada X del punto.
	 */
	public double getX() {
		
		return posX;
	}
	
	/**
	 * @return double
	 * 
	 * Devuelve la coordenada Y del punto.
	 */
	public double getY() {
		
		return posY;
	}
	
	/**
	 * @return String
	 * 
	 * Devuelve el nombre del punto;
	 */
	public String getNombre() {
		
		return nombre;
	}
}
