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
		this.habilitada = true;	// por defecto la avenida está habilitada
		
	} // fin de constructor de clase
	
	/**
	 * @return Punto
	 * 
	 * Devuelve el punto de origen de la avenida.
	 */
	public Punto getOrigen() {
		
		return origen;
	} // fin del método getOrigen
	
	/**
	 * @return Punto
	 * 
	 * Devuelve el punto de destino de la avenida.
	 */
	public Punto getDestino() {
		
		return destino;
	} // fin del método getDestino
	
	/**
	 * @return long
	 * 
	 * Devuelve el tráfico máximo de la avenida.
	 */
	public long getTraficoMax() {
		
		return traficoMaximo;
	} // fin del métoto getTraficoMax
	
	/**
	 * @return double
	 * 
	 * Devuelve la distancia de la avenida.
	 */
	public double getDistancia() {
		
		return distancia;
	} // fin del método getDistancia
	
	/**
	 * @return boolean
	 * 
	 * Devuelve true si la avenida está habilitada. 
	 * En caso contrario devuelve false.
	 */
	public boolean estaHabilitada() {
		
		return habilitada;
	} // fin del método estaHabilitada
	
	/**
	 * @param estado
	 * 
	 * Establece el estado de la avenida.
	 */
	public void setEstado(boolean estado) {
		
		habilitada = estado;
	} // fin del método setEstado
	
	/**
	 * @param t
	 * 
	 * Establece el trafico máximo.
	 */
	public void setTraficoMax(long t) {
		
		traficoMaximo = t;
	} // fin del método setTraficoMax
	
	/**
	 * @param d
	 * 
	 * Establece la distancia.
	 */
	public void setDistancia(double d) {
		
		distancia = d;
	} // fin del método setDistancia
}
