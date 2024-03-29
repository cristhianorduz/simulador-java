package red;

import java.io.Serializable;

/**
 * @author User
 * 
 * Esta clase representa las avenidas que unen dos puntos de la red, ambos
 * representados por los atributos origen y destino.
 *
 */
public class Avenida implements Serializable {
	
	private Punto origen;
	private Punto destino;
	private long traficoMaximo;
	private double distancia;
	private boolean habilitada;
	
	/**
	 * @param origen
	 * @param destino
	 * 
	 * Constructor de clase.
	 */
	public Avenida(Punto origen, Punto destino, long traficoMaximo, double distancia) {
		
		this.origen = origen;
		this.destino = destino;
		this.traficoMaximo = traficoMaximo;
		this.distancia = distancia;
		this.habilitada = true;	// por defecto la avenida est� habilitada
		
	} // fin de constructor de clase
	
	/**
	 * @return Punto
	 * 
	 * Devuelve el punto de origen de la avenida.
	 */
	public Punto getOrigen() {
		
		return origen;
	} // fin del m�todo getOrigen
	
	/**
	 * @return Punto
	 * 
	 * Devuelve el punto de destino de la avenida.
	 */
	public Punto getDestino() {
		
		return destino;
	} // fin del m�todo getDestino
	
	/**
	 * @return long
	 * 
	 * Devuelve el tr�fico m�ximo de la avenida.
	 */
	public long getTraficoMax() {
		
		return traficoMaximo;
	} // fin del m�toto getTraficoMax
	
	/**
	 * @return double
	 * 
	 * Devuelve la distancia de la avenida.
	 */
	public double getDistancia() {
		
		return distancia;
	} // fin del m�todo getDistancia
	
	/**
	 * @return boolean
	 * 
	 * Devuelve true si la avenida est� habilitada. 
	 * En caso contrario devuelve false.
	 */
	public boolean estaHabilitada() {
		
		return habilitada;
	} // fin del m�todo estaHabilitada
	
	/**
	 * @param estado
	 * 
	 * Establece el estado de la avenida.
	 */
	public void setEstado(boolean estado) {
		
		habilitada = estado;
	} // fin del m�todo setEstado
	
	/**
	 * @param t
	 * 
	 * Establece el trafico m�ximo.
	 */
	public void setTraficoMax(long t) {
		
		traficoMaximo = t;
	} // fin del m�todo setTraficoMax
	
	/**
	 * @param d
	 * 
	 * Establece la distancia.
	 */
	public void setDistancia(double d) {
		
		distancia = d;
	} // fin del m�todo setDistancia
}
