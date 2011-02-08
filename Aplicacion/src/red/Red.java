package red;

import java.io.Serializable;
import java.util.Vector;
import java.lang.Math;

/**
 * @author Goti
 * 
 * Esta clase representa la red de transporte.
 * Esta clase tiene una estructura similar a la de un grafo implementado
 * con listas de adyacencia, donde cada vértice corresponde a un punto de
 * intersección entre avenidas y cada arco corresponde a una avenida.
 */
public class Red implements Serializable {
	
	private int numPuntos;	// número de puntos de la red
	private int maxPuntos;	// número máximo de puntos de la red
	private Punto acceso;	// punto de acceso de la red
	private Punto salida;	// punto de salida de la red
	private Vector<PuntoInterno> puntosInternos;	// vector de puntos internos de la red
	private Vector<Avenida> avenidas;	// vector de avenidas de la red
	private final int RADIO = 10;	// radio de las circunferencias
	
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
	 * 
	 * Elimina el nodo centrado en el punto "p"
	 */
	public void eliminarPunto(Punto p) {

		puntosInternos.remove(p);
		eliminarAvenidasContienen(p);
	} // fin del método eliminarPunto
	
	/**
	 * @param a
	 * 
	 * Elimina la avenida "a"
	 */
	public void eliminarAvenida(Avenida a) {
		
		avenidas.remove(a);
	} // fin del método eliminarAvenida
	
	/**
	 * @param p
	 * 
	 * Elimina las avenidas que tienen el punto p ya sea como
	 * origen o como destino.
	 */
	private void eliminarAvenidasContienen(Punto p) {
		
		int i = 0;
		Avenida a;
		
		while(i<avenidas.size()) {
			
			a = avenidas.get(i);
			if(a.getOrigen().equals(p) || a.getDestino().equals(p)) {
				
				eliminarAvenida(a);
			}
			
			i++;
		}
		
	} // fin del método eliminarAvenidasContienen
	
	/**
	 * @param x
	 * @param y
	 * @return boolean
	 * 
	 * Determina si existe un punto con las coordenadas que se
	 * pasan como parámetro.
	 */
	public Punto getPuntoCoordenadas(double x, double y) {
		
		double distancia;
		double difX;
		double difY;
		
		// Calcula la distancia del punto de coordenadas (x, y) al punto de acceso
		difX = acceso.getX() - x;
		difY = acceso.getY() - y;
		distancia = Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
		
		if(distancia <= RADIO) {	// el punto esta dentro de la circunferencia
			
			return acceso; // devuelve el punto de acceso
		}
		else {	// intenta con el punto de salida
			
			difX = salida.getX() - x;
			difY = salida.getY() - y;
			distancia = Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
				
			if(distancia <= RADIO) {
					
				return salida; // devuelve el punto de salida
			}
		}
		
		// busca en el vector de puntos internos
		int i = 0;
		int tam = puntosInternos.size();
		
		while(i<tam) {
			
			difX = puntosInternos.get(i).getX() - x;
			difY = puntosInternos.get(i).getY() - y;
			distancia = Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
			
			if(distancia <= RADIO) {
				
				return puntosInternos.get(i);
			}
			i++;
		}
		// no hubo coincidencias ¬¬
		return null;
	} // fin del método getPuntoCoordenadas
	
	/**
	 * @return Punto
	 */
	public Punto getAcceso() {
		
		return acceso;
	} // fin del método getAcceso
	
	/**
	 * @return Punto
	 */
	public Punto getSalida() {
		
		return salida;
	} // fin del método getSalida
	
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
	 * @param p
	 * @return boolean
	 * 
	 * Devuelve true si existe una avenida que tenga el punto
	 * p, ya sea como origen o como destino.
	 */
	public boolean existeAvenidaContiene(Punto p) {
		
		int i = 0;
		boolean encontrado = false;
		Avenida a;
		
		while(i<avenidas.size() && !encontrado) {
			
			a = avenidas.get(i);
			
			if(a.getOrigen().equals(p) || a.getDestino().equals(p)) {
				
				encontrado = true;
			}

			i++;
		}
		
		return encontrado;
		
	} // fin del método existeAvenidaContiene
	
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
		
		double xOrigen = origen.getX();
		double yOrigen = origen.getY();
		double xDestino = destino.getX();
		double yDestino = destino.getY();
		
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
	} // fom del método getAvenidaIndice
	
	/**
	 * @param indice
	 * @return Punto
	 * 
	 * Devuelve el punto de la posicion "indice" del vector puntosInternos.
	 */
	public Punto getPuntoIndice(int indice) {
		
		return puntosInternos.get(indice);
	} // fin del método getPuntoIndice
	
	
	/**
	 * @param x
	 * @param y
	 * @return boolean
	 * 
	 * Este método determina si existe una avenida que atraviesa
	 * al nodo centrado en el punto (x, y).
	 */
	public boolean avenidaPasaPorPunto(double x, double y) {
		
		boolean encontrado = false;
		
		for(int i=0; i<avenidas.size() && !encontrado; i++) {
			
			Punto o = avenidas.get(i).getOrigen();
			Punto d = avenidas.get(i).getDestino();
			
			// Coordenadas del origen
			double xOrigen = o.getX();
			double yOrigen = o.getY();
			// Coordenadas del destino
			double xDestino = d.getX();
			double yDestino = d.getY();
			
			// Si la pendiente de la recta es infinita
			if(xDestino-xOrigen == 0) {

				double ordenada, xInt, yInt, dist;			
					
				// la diferencia entre coordenadas
				dist = xOrigen - x;
					
				// hace positiva la distancia
				dist = dist<0 ? -dist : dist;
					
				if(dist < 12) {		// la recta atraviesa al punto
					encontrado = true;
				}
			}
			else {
				
				// Pendiente de la recta
				double a = (yDestino-yOrigen)/(xDestino-xOrigen);
				
				// Ordenada al origen de la recta
				double b = yOrigen - a * xOrigen;
				
				// Pendiente de la recta perpendicular
				double m = -(1/a);
				
				double ordenada, xInt, yInt, dist;
				
				// ordenada de la recta de pendiente m que pasa por el punto dado
				ordenada = y - m*x;
						
				// abscisa del punto de intersección entre rectas
				xInt = (ordenada - b) / (a - m);
				// ordenada del punto de intersección entre rectas
				yInt = m*xInt + ordenada;
						
				// la raiz cuadrada de la diferencia de los cuadrados de las coordenadas
				dist = Math.sqrt(Math.pow(xInt-x, 2) + Math.pow(yInt-y, 2));
						
				if(dist < 12) {		// la recta atraviesa al punto
					encontrado = true;
				}
			}
		}
		return encontrado;
	} // fin del método avenidaPasaPorPunto
	
	/**
	 * @param origen
	 * @param destino
	 * @return boolean
	 * 
	 * Este método determina si la avenida que pasa por los puntos de nombre origen
	 * y destino atraviesa algún otro punto de la red. Utiliza la fórmula de la distancia
	 * de un punto a una recta. Si dicha distancia es menor a 12 (radio + grosor de la recta), 
	 * la recta no atraviesa el punto.
	 */
	public boolean avenidaAtraviesaPuntos(String origen, String destino) {
		
		Punto o = getPuntoNombre(origen);
		Punto d = getPuntoNombre(destino);
		
		// Coordenadas del origen
		double xOrigen = o.getX();
		double yOrigen = o.getY();
		// Coordenadas del destino
		double xDestino = d.getX();
		double yDestino = d.getY();
		
		// Si la pendiente de la recta es infinita
		if(xDestino-xOrigen == 0) {
			
			int i = 0;
			boolean encontrado = false;
			double ordenada, xInt, yInt, dist;
			Punto p;
			
			while(i<puntosInternos.size() && !encontrado) {
				
				p = puntosInternos.get(i);
				
				// la diferencia entre coordenadas
				dist = xOrigen - p.getX();
				
				// hace positiva la distancia
				dist = dist<0 ? -dist : dist;
				
				if(dist < 12) {		// la recta atraviesa al punto
					encontrado = true;
				}
				else {	// la recta no lo atraviesa
					i++;
				}
			}
			
			return encontrado;
		}
		else {
			
			// Pendiente de la recta
			double a = (yDestino-yOrigen)/(xDestino-xOrigen);
			
			// Ordenada al origen de la recta
			double b = yOrigen - a * xOrigen;
			
			// Pendiente de la recta perpendicular
			double m = -(1/a);
			
			int i = 0;
			boolean encontrado = false;
			double ordenada, xInt, yInt, dist;
			Punto p;
			
			while(i<puntosInternos.size() && !encontrado) {
				
				p = puntosInternos.get(i);
				
				if(!p.getNombre().equals(origen) && !p.getNombre().equals(destino)) {
					
					// ordenada de la recta de pendiente m que pasa por el punto "p"
					ordenada = p.getY() - m*p.getX();
					
					// abscisa del punto de intersección entre rectas
					xInt = (ordenada - b) / (a - m);
					// ordenada del punto de intersección entre rectas
					yInt = m*xInt + ordenada;
					
					// la raiz cuadrada de la diferencia de los cuadrados de las coordenadas
					dist = Math.sqrt(Math.pow(xInt-p.getX(), 2) + Math.pow(yInt-p.getY(), 2));
					
					if(dist < 12) {		// la recta atraviesa al punto
						encontrado = true;
					}
				}
				
				i++;
			}
			
			return encontrado;
		}
		
	} // fin del método avenidaAtraviesaPuntos
	
	/**
	 * @param o
	 * @param p
	 * @return Avenida
	 * 
	 * Dados dos puntos origen y destino, obtiene la avenida entre ellos.
	 * Si no encuentra una avenida entre los puntos devuelve null.
	 */
	public Avenida getAvenida(Punto o, Punto p) {
		
		int i = 0;
		boolean encontrado = false;
		Avenida a = null;
		
		while(i<avenidas.size() && !encontrado) {
			
			a = avenidas.get(i);
			if(a.getOrigen().equals(o) && a.getDestino().equals(p)) {
				
				encontrado = true;
			}
			else {
				i++;
			}
		}
		
		if(encontrado) {
			
			return a;
		}
		else {
			return null;
		}
	} // fin del método getAvenida
	
	/**
	 * @param puntos
	 * @return Avenida[]
	 * 
	 * Devuelve la secuencia de avenidas según la secuencia de puntos
	 * que se pasa como parámetro.
	 */
	public Avenida[] obtenerSecuenciaAvenidas(Punto[] puntos) {
		
		Vector<Avenida> avenidas = new Vector<Avenida>();
		Avenida a;
		
		for(int i=0; i<puntos.length-1; i++) {
			
			a = getAvenida(puntos[i], puntos[i+1]);
			
			avenidas.add(a);
		}
		
		// Pasa las avenidas a un arreglo
		int i=0;
		Avenida[] av = new Avenida[avenidas.size()];
		while(i<avenidas.size()) {
			
			av[i] = avenidas.get(i);
		}
		
		return av;
		
	} // fin del método obtenerSecuenciaAvenidas
	
	/***************************************************************************
	 *********************** Métodos a implementar *****************************
	 ***************************************************************************/
	
	/**
	 * @param origen
	 * @param destino
	 * @return Punto[]
	 * 
	 * Obtiene el camino más corto entre el par de puntos dados.
	 * Devuelve un arreglo con la lista de puntos que conforman
	 * el camino mas corto, si es que existe uno, incluyendo los puntos
	 * de los extremos. Si los nodos no son alzcanzables devuelve "null".
	 */
	public Punto[] obtenerCaminoMasCorto(Punto origen, Punto destino) {
		
		
	} // fin del método obtenerCaminoMasCorto
	
	/**
	 * @param origen
	 * @param destino
	 * @return Punto[]
	 * 
	 * Obtiene el camino con menos peajes entre el par de puntos dados.
	 * Devuelve un arreglo con la lista de puntos que conforman
	 * el camino, si existe uno, incluyendo los puntos de los extremos.
	 * Si los nodos no son alcanzables devuelve "null".
	 * 
	 */
	public Punto[] obtenerCaminoMenosPeajes(Punto origen, Punto destino) {
		
		
	} // fin del método obtenerCaminoMenosPeajes
	
	/**
	 * @param p
	 * @return Punto[]
	 * 
	 * Obtiene los puntos alcanzables desde el punto dado. Si no hay
	 * puntos alcanzables, el nodo será aislado, y se devuelve un
	 * arreglo de puntos vacío.
	 */
	public Punto[] obtenerPuntosAlcanzables(Punto p) {
		
		
	} // fin del método obtenerPuntosAlcanzables
	
	/**
	 * @return long
	 * 
	 * Este método obtiene el máximo tráfico que puede ser registrado
	 * en la red desde el punto origen al punto destino. Está basado
	 * en el algoritmo de Ford Fulkerson.
	 */
	public long obtenerMaximoTraficoEnRed() {
		
		long traficoMax = 0;
		Vector<long[]> clasificaciones;	// vector de clasificaciones
		long capResiduales[][];	//	matriz de capacidades residuales
		Vector<Vector<Integer>> conjuntoSi;	// conjunto de nodos a los que se puede acceder desde el nodo i
		Vector<Long> fps;	// flujos mínimos encontrados en todas las rutas
		
		// crea el vector de clasificaciones y asigna [Long.MAXVALUE, -1] al punto de acceso
		clasificaciones = new Vector<long[]>();
		long clAcceso[] = {0, Long.MAX_VALUE, -1};	// clasificación para el punto de acceso
		clasificaciones.add(clAcceso);
		
		// crea la matriz de capacidades residuales
		capResiduales = new long[puntosInternos.size()+2][];
		for(int i=0; i<puntosInternos.size()+2; i++) {
			capResiduales[i] = new long[puntosInternos.size()+2];
		}
		
		// crea el vector de fps
		fps = new Vector<Long>();
		
		// inicializa las capacidades residuales a las capacidades iniciales
		inicializarMatrizCapResiduales(capResiduales);
		
		// inicializa i = 0, suponiendo que se empieza del punto de acceso
		int i = 0;
		
		conjuntoSi = new Vector<Vector<Integer>>();
		for(int j=0; j<puntosInternos.size()+2; j++) {
			
			conjuntoSi.add(new Vector<Integer>());
		}
		
		// crea el conjunto Si inicial
		for(int j=0; j<puntosInternos.size()+2; j++) {
			
			if(capResiduales[i][j] != 0){
				conjuntoSi.get(i).add(new Integer(j));
			}
		}
		
		boolean cond = false;	// condición de cierre: S0 es vacío
		
		do {
			
			if(conjuntoSi.get(i).size()!=0) {	// existen arcos que conectan el nodo i con otros nodos

				// obtiene el nodo destino del arco con mayor capacidad que salga
				// de i hacia un nodo de Si
				int k = getNodoK(conjuntoSi.get(i), capResiduales, i);

				long ak = capResiduales[i][k];
				System.out.println("k: " + k);
				// crea una clasificación para el nodo k
				long clask[] = {k, ak, i};
				clasificaciones.add(clask);

				if(k == puntosInternos.size()+1) {	// hemos llegado al punto de salida
					System.out.println("Entro " + k + " a " + i);
					long min = obtenerMinimo(clasificaciones);
					
					fps.add(new Long(min));

					int p = (int)clasificaciones.lastElement()[0];
					int s = (int)clasificaciones.lastElement()[2];
					
					// actualiza la matriz de capacidades
					while(clasificaciones.size()!=1) {
						
						capResiduales[p][s] += min;
						capResiduales[s][p] -= min;
						
						System.out.println("de: " + p +" a: " + s + " valores: " +capResiduales[p][s]);
						System.out.println("de: " + s +" a: " + p + " valores: " +capResiduales[s][p]);
						
						clasificaciones.remove(clasificaciones.lastElement());
						
						if(clasificaciones.size()!=1) {
							
							p = (int)clasificaciones.lastElement()[0];
							s = (int)clasificaciones.lastElement()[2];
						}
					}
					
					int l = 0;
					while(l<conjuntoSi.size()) {
						
						conjuntoSi.get(l).removeAllElements();
						l++;
					}
					
					i = 0;
					
					for(int j=0; j<puntosInternos.size()+2; j++) {
						if(capResiduales[i][j]>0 && j>i){
							conjuntoSi.get(i).add(new Integer(j));
						}
					}
				}
				else {	// continuamos con el camino
					i = k;
					
					for(int j=0; j<puntosInternos.size()+2; j++) {
						if(capResiduales[i][j] > 0 && j>i){
							System.out.println("i: " + i + " j: " + j + " valores: " + capResiduales[i][j]);
							conjuntoSi.get(i).add(new Integer(j));
						}
					}
				}
			}
			else {	// el conjunto Si está vacío
				
				if(i!=0) {	// estamos en un nodo distinto del origen
					
					long clasif[] = obtenerClasificacion(clasificaciones, i);			
					int padre = (int)clasif[2];
					int hijo = (int)clasif[0];
					conjuntoSi.get(padre).remove(new Integer(hijo));
					System.out.println(conjuntoSi.get(padre).size());
					i=padre;
					clasificaciones.remove(clasif);
				}
				else {	// estamos en el nodo origen
					
					for(int m=0; m<fps.size(); m++) {
						
						traficoMax += fps.get(m);
					}
					
					cond = true;
				}
			}
			
		} while(!cond);
		
		return traficoMax;
		
	} // fin del método obtenerMaximoTraficoEnRed
	
	
	/**
	 * @param vector
	 * @return Long
	 * 
	 * Obtiene el valor de flujo mínimo del conjunto de clasificaciones registradas.
	 */
	private long obtenerMinimo(Vector<long[]> vector) {
		
		int i = 1;
		long min = vector.get(0)[1];
		
		while(i<vector.size()) {
			
			if(min > vector.get(i)[1]) {
				
				min = vector.get(i)[1];
			}
			i++;
		}
		
		return min;
		
	} // fin del método obtenerMinimo
	
	/**
	 * @param vector
	 * @param k
	 * @return long[]
	 * 
	 * Devuelve la clasificación creada para el elemento de indice k.
	 */
	private long[] obtenerClasificacion(Vector<long[]> vector, int k) {
		
		int i=0;
		boolean encontrado = false;
		
		while(!encontrado && i<vector.size()) {
			
			if(vector.get(i)[0] == k) {
				
				encontrado = true;
			}
			else {
				i++;
			}
		}
		
		if(encontrado) {
			return vector.get(i);
		}
		else {
			return null;
		}
	} // fin del método obtenerCalificacion
	
	/**
	 * @param vector
	 * @param k
	 * 
	 * Elimina del vector el elemento que contiene el valor k.
	 */
	private void removerElemento(Vector<Integer> vector, int k){
		
		int i = 0;
		boolean encontrado = false;
		
		while(!encontrado && i<vector.size()) {
			
			if(vector.get(i).intValue() == k ) {
				
				encontrado = true;
			}
			else {
				i++;
			}
		}
		
		if(encontrado) {
			
			vector.remove(i);
		}
		
	} // fin del método removerElemento
	
	/**
	 * @return Vector<Punto>
	 * 
	 * Este método crea un vector de puntos en base a los puntos
	 * registrados en la red. El primer punto es el de acceso.
	 * El punto final es el de salida.
	 */
	private Vector<Punto> crearVectorPuntos() {
		
		Vector<Punto> vector = new Vector<Punto>();
		
		// agrega el punto de acceso
		vector.add(acceso);
		// agrega los demás puntos internos
		for(int i=0; i<puntosInternos.size(); i++) {
			
			vector.add(puntosInternos.get(i));
		}
		// agrega el punto de salid
		vector.add(salida);
		
		return vector;
	} // fin del método crearVectorPuntos
	
	/**
	 * @param matriz
	 * 
	 * Inicializa la matriz de capacidades residuales en base al grafo
	 * que forma la red de transporte (nodos y avenidas). Se emplea
	 * en el algoritmo de Ford Fulkerson.
	 */
	private void inicializarMatrizCapResiduales(long[][] matriz) {
		
		Vector<Punto> puntos = crearVectorPuntos();
		Punto p;
		Punto o;
		Avenida a;
		
		// agrega las capacidades iniciales
		for(int i=0; i<puntos.size(); i++) {
			
			p = puntos.get(i);
			
			for(int j=i+1; j<puntos.size(); j++) {
				
				o = puntos.get(j);
				
				a = getAvenida(p, o);
				
				if(a != null) { // exite avenida entre p y o
					matriz[i][j] = a.getTraficoMax();
				}
				else {
					matriz[i][j] = 0;
				}
				
				a = getAvenida(o, p);
				
				if(a != null) {	// existe avenida entre o y p
					matriz[j][i] = a.getTraficoMax();
				}
				else {
					matriz[j][i] = 0;
				}
			}
		}
		
	} // fin del método inicializarMatrizCapacidadesResiduales
	
	
	/**
	 * @param vector
	 * @param matriz
	 * @return int
	 * 
	 * Determina cual es el nodo destino del arco con mayor tráfico máximo
	 * que lo une al nodo i.
	 */
	private int getNodoK(Vector<Integer> vector, long matriz[][], int i) {
		
		int indice = 0;
		int k = 0 , j;
		long maximo = 0;
		// crea una matriz de de capacidad original para poder
		// determinar si dos nodos cualesquiera se encuentran
		// conectados en una cierta dirección
		long matrizOriginal[][] = new long[puntosInternos.size()+2][];
		for(int l=0; l<puntosInternos.size()+2; l++) {
			matrizOriginal[l] = new long[puntosInternos.size()+2];
		}
		inicializarMatrizCapResiduales(matrizOriginal);

		while(indice < vector.size()) {
			
			j = vector.get(indice).intValue();
			
			if(maximo < matriz[i][j] && matrizOriginal[i][j]>0) {	
				
				maximo = matriz[i][vector.get(indice).intValue()];
				k = vector.get(indice).intValue();
			}
			
			indice++;
		}
		
		return k;
		
	} // fin del método getNodoK
	
	/**
	 * @param p
	 * @param numPeajes
	 * @return Punto[]
	 * 
	 * Obtiene los puntos alcanzables desde el punto "p" sin
	 * pasar por más de "n" peajes. Devuelve un arreglo
	 * vacío en caso de que no haya puntos alcanzables.
	 */
	public Punto[] obtenerPuntosMenosNPeajes(Punto p, int numPeajes) {
		
		
	} // fin del método obtenerPuntosMenosNPeajes
	
} // fin de la clase Red
