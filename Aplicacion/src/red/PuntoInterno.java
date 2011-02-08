package red;

import java.io.Serializable;

/**
 * @author Goti
 * 
 * Esta clase es una especialización de la clase Punto.
 * Agrega un atributo adicional, que es el coste de peaje para
 * transitar por las avenidas que lo tienen como punto de origen.
 */
public class PuntoInterno extends Punto implements Serializable {
	
	private double costo;
	
	/**
	 * @param nombre
	 * @param costo
	 * 
	 * Constructor de clase.
	 */
	public PuntoInterno(double x, double y, String nombre, double costo) {
		
		super(nombre, x, y);
		
		this.costo = costo;
	} // fin del constructor
	
	/**
	 * @return double
	 * 
	 * Devuelve el costo del punto.
	 */
	public double getCosto() {
		
		return costo;
	} // fin del método getCosto
	
	/**
	 * @param c
	 * 
	 * Establece el valor del costo del punto.
	 */
	public void setCosto(double c) {
		
		costo = c;
	} // fin del método setCosto
	
} // fin de clase PuntoInterno
