package red;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author Goti
 * 
 * Esta clase representa la red de transporte.
 * Esta clase tiene una estructura similar a la de un grafo implementado
 * con listas de adyacencia, donde cada vértice corresponde a un punto de
 * intersección entre avenidas y cada arco corresponde a una avenida.
 */
public class Red implements Serializable {
	
	private int numPuntos;
	private int maxPuntos;
	private Punto acceso;
	private Punto salida;
	private Vector<PuntoInterno> puntosInternos;
	private Vector<Avenida> avenidas;
	
	/**
	 * Constructor de clase.
	 */
	public Red(double xAcceso, double yAcceso, double xSalida, double ySalida) {
		
		/* Inicialmente hay dos puntos en la red, acceso y salida respectivamente */
		numPuntos = 2;
		
		/* Establece un máximo inicial de 20 puntos, que se extenderá
		 * en caso de que el número de intersecciones entre avenidas supere
		 * dicho tope */
		maxPuntos = 10;
		
		/* Crea los puntos externos de acceso y salida de la ciudad */
		acceso = new Punto("Acceso", xAcceso, yAcceso);
		salida = new Punto("Salida", xSalida, ySalida);
		
		/* Crea un arreglo vacío de puntos internos */
		puntosInternos = new Vector<PuntoInterno>();
		
		/* Crea un arreglo vacío de avenidas */
		avenidas = new Vector<Avenida>();
	} // fin del constructor de clase
	
	
	/**
	 * @param p
	 * 
	 * Recibe un punto como parámetro y lo agrega al vector de
	 * puntos internos de la red.
	 */
	public void agregarPuntoInterno(PuntoInterno p) {
		
		puntosInternos.add(p);
	} // fin del método agregarPuntoInterno
	
	/**
	 * @param av
	 * 
	 * Recibe una avenida como parámetro y la agrega al vector
	 * de avenidas de la red.
	 */
	public void agregarAvenida(Avenida av) {
		
		avenidas.add(av);
	} // fin del método agregarAvenida
	
	/**
	 * @param x
	 * @param y
	 * @return boolean
	 * 
	 * Determina si existe un punto con las coordenadas que se
	 * pasan como parámetro.
	 */
	public Punto getPuntoCoordenadas(double x, double y) {
		
		double xInf, xSup, yInf, ySup;
		
		// Compara con las coordenadas de los puntos de acceso y salida
		xInf = acceso.getX() - 20;
		xSup = acceso.getX() + 20;
		yInf = acceso.getY() - 20;
		ySup = acceso.getY() + 20;
		
		if(x>=xInf && x<=xSup) {
			
			if(y>=yInf && y<=ySup) {
				
				return acceso; // devuelve el punto de acceso
			}
			else {
				
				yInf = salida.getY() - 20;
				ySup = salida.getY() + 20;
				
				if(y>=yInf && y<=ySup) {
					
					return salida; // devuelve el punto de salida
				}
			}
		}
		
		// busca en el vector de puntos internos
		int i = 0;
		int tam = puntosInternos.size();
		
		while(i<tam) {
			
			xInf = puntosInternos.get(i).getX() - 20;
			xSup = puntosInternos.get(i).getX() + 20;
			yInf = puntosInternos.get(i).getY() - 20;
			ySup = puntosInternos.get(i).getY() + 20;
			
			if(x>=xInf && x<=xSup) {
				
				if(y>=yInf && y<=ySup) {
					
					return puntosInternos.get(i);
				}
			}
			i++;
		}
		// no hubo coincidencias ¬¬
		return null;
	} // fin del método getPuntoCoordenadas
	
	/**
	 * @return PuntoInterno[]
	 * 
	 * Este método almacena y devuelve todos los puntos internos de la red
	 * en un arreglo de objetos de clase PuntoInterno.
	 */
	public PuntoInterno[] getPuntosInternos() {
		
		PuntoInterno[] puntos = new PuntoInterno[puntosInternos.size()];
		
		for(int i=0; i<puntosInternos.size(); i++) {
			
			puntos[i] = puntosInternos.get(i);
		}
		
		return puntos;
	} // fin del método getPuntosInternos
	
	/**
	 * @return Avenida[]
	 * 
	 * Este método almacena y devuelve todas las avenidas de
	 * la red en un arreglo de objetos de clase Avenida.
	 */
	public Avenida[] getAvenidas() {
		
		Avenida[] av = new Avenida[avenidas.size()];
		
		for(int i=0; i<avenidas.size(); i++) {
			
			av[i] = avenidas.get(i);
		}
		
		return av;
	} // fin del método getAvenidas
	
	/**
	 * @param nombre
	 * @return boolean
	 * 
	 * Devuelve true si existe un punto con el nombre dado en la red.
	 * En caso contrario devuelve false.
	 */
	public boolean existePuntoNombre(String nombre) {
		
		boolean encontrado = false;
		int numPuntos = puntosInternos.size();
		int i=0;
		
		if(nombre.equals("Acceso") || nombre.equals("Salida")) {
			
			encontrado = true;
		}
		while(!encontrado && i<numPuntos) {
			
			if(puntosInternos.get(i).getNombre().equals(nombre)) {
				encontrado = true;
			}
			else {
				i++;
			}
		}
		
		return encontrado;
	} // fin del método existePuntoNombre
	
	/**
	 * @param origen
	 * @param destino
	 * @return boolean
	 * 
	 * Determina si existe una avenida con que comience en el
	 * punto de nombre "origen" y termine en el punto de nombre "destino".
	 */
	public boolean existeAvenidaPuntos(String origen, String destino) {
		
		boolean encontrado = false;
		int i = 0;
		Avenida a;
		
		while(!encontrado && i<avenidas.size()) {
			
			a = avenidas.get(i);
			
			if(a.getOrigen().getNombre().equals(origen)) {
				
				if(a.getDestino().getNombre().equals(destino)) {
					
					encontrado = true;
				}
			}
			i++;
		}
		
		return encontrado;
		
	} // fin del método existeAvenidaPuntos
	
	/**
	 * @param nombre
	 * @return Punto
	 * 
	 * Busca el punto con el nombre indicado en la lista de nombres
	 * y lo devuelve a la rutina invocadora.
	 */
	public Punto getPuntoNombre(String nombre) {
		
		boolean encontrado = false;
		int numPuntos = puntosInternos.size();
		int i=0;
		
		if(nombre.equals("Acceso")) {
			
			return acceso;
		}
		else if(nombre.equals("Salida")) {
			
			return salida;
		}
		while(!encontrado && i<numPuntos) {
			
			if(puntosInternos.get(i).getNombre().equals(nombre)) {
				encontrado = true;
			}
			else {
				i++;
			}
		}
		if(encontrado) {
			return puntosInternos.get(i);
		}
		
		return null;
	}
	
	/**
	 * @param x
	 * @param y
	 * @return Avenida
	 * 
	 * Determina si existe una avenida que pase por el punto
	 * de coordenadas (x, y) y, en caso de que exista,
	 * devuelve una referencia a dicha avenida. En caso
	 * contrario devuelve null.
	 */
	public Avenida getAvenidaCoordenadas(double x, double y) {
		
		boolean encontrado = false;
		int i = 0;
		Avenida a = null;
		
		while(!encontrado && i<avenidas.size()) {
			
			a = avenidas.get(i);
			
			if(pertenece(a.getOrigen(), a.getDestino(), x, y)) {

				encontrado = true;
			}
			i++;
		}
		
		if(encontrado) {
			
			return a;
		}
		else {
			
			return null;
		}
	} // fin del método getAvenidaCoordenadas
	
	
	/**
	 * @param x
	 * @param y
	 * @param x
	 * @param y
	 * @return boolean
	 * 
	 * Devuelve true si el punto de coordenadas (x, y) pertenece
	 * a la recta formada por los puntos origen y destino.
	 */
	public boolean pertenece(Punto origen, Punto destino, double x, double y) {
		
		double xOrigen = origen.getX() + 10;
		double yOrigen = origen.getY() + 10;
		double xDestino = destino.getX() + 10;
		double yDestino = destino.getY() + 10;
		
		// Si la pendiente de la recta es infinita
		if(xDestino-xOrigen == 0) {
			
			if(yOrigen > yDestino) {
				
				if(y >= yDestino && y <= yOrigen) {
					
					return true;
				}
			}
			else {
				
				if(y <= yDestino && y >= yOrigen) {
					
					return true;
				}
			}
			return false;
		}
		else{
			
			// Pendiente de la recta
			double a = (yDestino-yOrigen)/(xDestino-xOrigen);
			
			// Ordenada al origen de la recta
			double b = yOrigen - a * xOrigen;
			
			// Valor calculado en Y para la recta
			double yCalculado = a * x + b;
			
			if(y>=(yCalculado-4) && y<=(yCalculado+4)) {
				return true;
			}
			else {
				return false;
			}
		}
	} // fin del método pertenece
	
	/**
	 * @param a
	 * @return int
	 * 
	 * Devuelve el indice de la avenida en el vector de avenidas.
	 */
	public int getIndiceAvenida(Avenida a) {
		
		return avenidas.indexOf(a);
	} // fin del método getIndiceAvenida
	
	/**
	 * @param p
	 * @return int
	 * 
	 * Devuelve el indice del punto en el vector de puntos.
	 */
	public int getIndicePunto(Punto p) {
		
		return puntosInternos.indexOf(p);
	}// fin del método getIndicePunto
	
	/**
	 * @param indice
	 * @return Avenida
	 * 
	 * Devuelve la avenida de la posicion "indice" del vector avenidas.
	 */
	public Avenida getAvenidaIndice(int indice) {
		
		return avenidas.get(indice);
	}
	
	/**
	 * @param indice
	 * @return Punto
	 * 
	 * Devuelve el punto de la posicion "indice" del vector puntosInternos.
	 */
	public Punto getPuntoIndice(int indice) {
		
		return puntosInternos.get(indice);
	}
	
} // fin de la clase Red
