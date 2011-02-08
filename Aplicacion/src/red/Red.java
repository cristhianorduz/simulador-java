package red;

import java.io.Serializable;
import java.util.Vector;
import java.lang.Math;

/**
 * @author Goti
 * 
 * Esta clase representa la red de transporte.
 * Esta clase tiene una estructura similar a la de un grafo implementado
 * con listas de adyacencia, donde cada v�rtice corresponde a un punto de
 * intersecci�n entre avenidas y cada arco corresponde a una avenida.
 */
public class Red implements Serializable {
	
	private Punto acceso;	// punto de acceso de la red
	private Punto salida;	// punto de salida de la red
	private Vector<PuntoInterno> puntosInternos;	// vector de puntos internos de la red
	private Vector<Avenida> avenidas;	// vector de avenidas de la red
	private final int RADIO = 10;	// radio de las circunferencias
	private static final double MAX = 9999;
	
	/**
	 * Constructor de clase.
	 */
	public Red(double xAcceso, double yAcceso, double xSalida, double ySalida) {
		
		/* Crea los puntos externos de acceso y salida de la ciudad */
		acceso = new Punto("Acceso", xAcceso, yAcceso);
		salida = new Punto("Salida", xSalida, ySalida);
		
		/* Crea un arreglo vac�o de puntos internos */
		puntosInternos = new Vector<PuntoInterno>();
		
		/* Crea un arreglo vac�o de avenidas */
		avenidas = new Vector<Avenida>();
	} // fin del constructor de clase
	
	
	/**
	 * @param p
	 * 
	 * Recibe un punto como par�metro y lo agrega al vector de
	 * puntos internos de la red.
	 */
	public void agregarPuntoInterno(PuntoInterno p) {
		
		puntosInternos.add(p);
	} // fin del m�todo agregarPuntoInterno
	
	/**
	 * @param av
	 * 
	 * Recibe una avenida como par�metro y la agrega al vector
	 * de avenidas de la red.
	 */
	public void agregarAvenida(Avenida av) {
		
		avenidas.add(av);
	} // fin del m�todo agregarAvenida
	
	/**
	 * @param p
	 * 
	 * Elimina el nodo centrado en el punto "p"
	 */
	public void eliminarPunto(Punto p) {

		puntosInternos.remove(p);
		eliminarAvenidasContienen(p);
	} // fin del m�todo eliminarPunto
	
	/**
	 * @param a
	 * 
	 * Elimina la avenida "a"
	 */
	public void eliminarAvenida(Avenida a) {
		
		avenidas.remove(a);
	} // fin del m�todo eliminarAvenida
	
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
			else {
				i++;
			}
		}
		
	} // fin del m�todo eliminarAvenidasContienen
	
	/**
	 * @param x
	 * @param y
	 * @return boolean
	 * 
	 * Determina si existe un punto con las coordenadas que se
	 * pasan como par�metro.
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
		// no hubo coincidencias ��
		return null;
	} // fin del m�todo getPuntoCoordenadas
	
	/**
	 * @return Punto
	 */
	public Punto getAcceso() {
		
		return acceso;
	} // fin del m�todo getAcceso
	
	/**
	 * @return Punto
	 */
	public Punto getSalida() {
		
		return salida;
	} // fin del m�todo getSalida
	
	/**
	 * @return PuntoInterno[]
	 * 
	 * Este m�todo almacena y devuelve todos los puntos internos de la red
	 * en un arreglo de objetos de clase PuntoInterno.
	 */
	public PuntoInterno[] getPuntosInternos() {
		
		PuntoInterno[] puntos = new PuntoInterno[puntosInternos.size()];
		
		for(int i=0; i<puntosInternos.size(); i++) {
			
			puntos[i] = puntosInternos.get(i);
		}
		
		return puntos;
	} // fin del m�todo getPuntosInternos
	
	/**
	 * @return Avenida[]
	 * 
	 * Este m�todo almacena y devuelve todas las avenidas de
	 * la red en un arreglo de objetos de clase Avenida.
	 */
	public Avenida[] getAvenidas() {
		
		Avenida[] av = new Avenida[avenidas.size()];
		
		for(int i=0; i<avenidas.size(); i++) {
			
			av[i] = avenidas.get(i);
		}
		
		return av;
	} // fin del m�todo getAvenidas
	
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
	} // fin del m�todo existePuntoNombre
	
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
		
	} // fin del m�todo existeAvenidaPuntos
	
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
		
	} // fin del m�todo existeAvenidaContiene
	
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
	} // fin del m�todo getAvenidaCoordenadas
	
	
	/**
	 * @param origen
	 * @param destino
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
			
			if(y>=(yCalculado-10) && y<=(yCalculado+10)) {
				return true;
			}
			else {
				return false;
			}
		}
	} // fin del m�todo pertenece
	
	/**
	 * @param a
	 * @return int
	 * 
	 * Devuelve el indice de la avenida en el vector de avenidas.
	 */
	public int getIndiceAvenida(Avenida a) {
		
		return avenidas.indexOf(a);
	} // fin del m�todo getIndiceAvenida
	
	/**
	 * @param p
	 * @return int
	 * 
	 * Devuelve el indice del punto en el vector de puntos.
	 */
	public int getIndicePunto(Punto p) {
		
		return puntosInternos.indexOf(p);
	}// fin del m�todo getIndicePunto
	
	/**
	 * @param indice
	 * @return Avenida
	 * 
	 * Devuelve la avenida de la posicion "indice" del vector avenidas.
	 */
	public Avenida getAvenidaIndice(int indice) {
		
		return avenidas.get(indice);
	} // fom del m�todo getAvenidaIndice
	
	/**
	 * @param indice
	 * @return Punto
	 * 
	 * Devuelve el punto de la posicion "indice" del vector puntosInternos.
	 */
	public Punto getPuntoIndice(int indice) {
		
		return puntosInternos.get(indice);
	} // fin del m�todo getPuntoIndice
	
	
	/**
	 * @param x
	 * @param y
	 * @return boolean
	 * 
	 * Este m�todo determina si existe una avenida que atraviesa
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

				double dist;			
					
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
						
				// abscisa del punto de intersecci�n entre rectas
				xInt = (ordenada - b) / (a - m);
				// ordenada del punto de intersecci�n entre rectas
				yInt = m*xInt + ordenada;
						
				// la raiz cuadrada de la diferencia de los cuadrados de las coordenadas
				dist = Math.sqrt(Math.pow(xInt-x, 2) + Math.pow(yInt-y, 2));
						
				if(dist < 12) {		// la recta atraviesa al punto
					encontrado = true;
				}
			}
		}
		return encontrado;
	} // fin del m�todo avenidaPasaPorPunto
	
	/**
	 * @param origen
	 * @param destino
	 * @return boolean
	 * 
	 * Este m�todo determina si la avenida que pasa por los puntos de nombre origen
	 * y destino atraviesa alg�n otro punto de la red. Utiliza la f�rmula de la distancia
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
			double dist;
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
					
					// abscisa del punto de intersecci�n entre rectas
					xInt = (ordenada - b) / (a - m);
					// ordenada del punto de intersecci�n entre rectas
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
		
	} // fin del m�todo avenidaAtraviesaPuntos
	
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
	} // fin del m�todo getAvenida
	
	/**
	 * @param puntos
	 * @return Avenida[]
	 * 
	 * Devuelve la secuencia de avenidas seg�n la secuencia de puntos
	 * que se pasa como par�metro.
	 */
	public Avenida[] obtenerSecuenciaAvenidas(Punto[] puntos) {
		
		Vector<Avenida> avenidas = new Vector<Avenida>();
		Avenida a;
		
		for(int i=0; i<puntos.length-1; i++) {
			
			for(int j=0; j<puntos.length; j++) {
				
				a = getAvenida2(puntos[i], puntos[j]);
				
				if(a!=null) {
					
					avenidas.add(a);
				}
			}
		}
		
		// Pasa las avenidas a un arreglo
		int i=0;
		Avenida[] av = new Avenida[avenidas.size()];
		while(i<avenidas.size()) {
			
			av[i] = avenidas.get(i);
			i++;
		}
		
		return av;
		
	} // fin del m�todo obtenerSecuenciaAvenidas
	
	/***************************************************************************
	 ********************* M�todos de funcionalidades **************************
	 ***************************************************************************/
	
	/**
	* 
	* @return
	* 
	* Agrega el punto de Acceso y el punto de Salida al vector 
	* de Puntos Internos para facilitar los c�lculos
	*/
	public Vector<PuntoInterno> puntosInternosAux(){
		int n = puntosInternos.size();
		Vector<PuntoInterno> puntosAux = new Vector<PuntoInterno>();
		puntosAux.add(new PuntoInterno(0, 0, "Acceso", 0));
		for(int i=0; i<n; i++){
			puntosAux.add(puntosInternos.get(i));
		}
		puntosAux.add(new PuntoInterno(0,0,"Salida",0));
		return puntosAux;
	}
	/**
	* @param o
	* @param p
	* @return Avenida
	* 
	* Dados dos puntos origen y destino, obtiene la avenida entre ellos.
	*/
	public Avenida getAvenida2(Punto o, Punto p) {
		
		int i = 0;
		boolean encontrado = false;
		Avenida a = null;
		
		while(i<avenidas.size() && !encontrado) {
			
			a = avenidas.get(i);
			if(a.getOrigen().getNombre().equals(o.getNombre())&& a.getDestino().getNombre().equals(p.getNombre())) {
				
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
	} // fin del m�todo getAvenida2
		
	/**
	* @param origen
	* @param destino
	* @return Punto[]
	* 
	* Obtiene el camino m�s corto entre el par de puntos dados.
	* Devuelve un arreglo con la lista de puntos que conforman
	* el camino mas corto, si es que existe uno, incluyendo los puntos
	* de los extremos. Si los nodos no son alzcanzables devuelve "null".
	*/
		
	public Punto[] obtenerCaminoMasCorto(Punto origen, Punto destino) {
			int n = puntosInternosAux().size();
			Punto[] vector = new Punto[n];
			int inicio = getIndex(puntosInternosAux(),origen.getNombre());
			int fin = getIndex(puntosInternosAux(),destino.getNombre());
			vector= new Punto[n];
					
			int[] ultimo = new int[n];
			boolean[] marcados = new boolean[n];
			double[] distanciaMenor = new double[n];
			
			for(int i=0; i<n; i++){
				ultimo[i]=1;
				marcados[i]=false;
				distanciaMenor[i]=MAX;
			}
		distanciaMenor[inicio]=0;
		
		for(int t=1; t<n; t++){
			double max = MAX;
			int v = 1;
			for(int j=0; j<n; j++){
				if(!marcados[j] && (distanciaMenor[j]<=max)){
					max=distanciaMenor[j];
					v=j;
					}
			}
			marcados[v]=true;
			for(int k=1; k<n; k++){
				if(!marcados[k]&&existeAvenidaPuntos(puntosInternosAux().get(v).getNombre(),puntosInternosAux().get(k).getNombre())){
					if(distanciaMenor[k]>distanciaMenor[v]+getAvenida2(puntosInternosAux().get(v),puntosInternosAux().get(k)).getDistancia()){
							distanciaMenor[k]=distanciaMenor[v]+getAvenida2(puntosInternosAux().get(v),puntosInternosAux().get(k)).getDistancia();
							ultimo[k]=v;
						};
					};
				}
		}
		int y=0;
		while(fin!=inicio)
		{
			vector[y]= (Punto) puntosInternosAux().get(fin);
			fin=ultimo[fin];
			y++;
		}
		Punto[] salida = new Punto[y];
		for(int qw=0; qw<y; qw++){
			salida[qw]=vector[qw];}

		return salida;
	} // fin del m�todo obtenerCaminoMasCorto
	
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
		int n = puntosInternosAux().size();
		Punto[] vector = new Punto[n];
		int inicio = getIndex(puntosInternosAux(),origen.getNombre());
		int fin = getIndex(puntosInternosAux(),destino.getNombre());
		vector= new Punto[n];
					
		int[] ultimo = new int[n];
		boolean[] marcados = new boolean[n];
		double[] peajeMenor = new double[n];
			
		for(int i=0; i<n; i++){
			ultimo[i]=1;
			marcados[i]=false;
			peajeMenor[i]=MAX;
		}
		peajeMenor[inicio]=0;
			
		for(int t=1; t<n; t++){
				double max = MAX;
				int v = 1;
				for(int j=0; j<n; j++){
					if(!marcados[j] && (peajeMenor[j]<=max)){
						max=peajeMenor[j];
						v=j;
						}
				}			
				marcados[v]=true;
				for(int k=1; k<n; k++){
					if(!marcados[k]&&existeAvenidaPuntos(puntosInternosAux().get(v).getNombre(),puntosInternosAux().get(k).getNombre())){
						if(peajeMenor[k]>peajeMenor[v]+puntosInternosAux().get(k).getCosto()){
							peajeMenor[k]=peajeMenor[v]+puntosInternosAux().get(k).getCosto();
							ultimo[k]=v;
						};
					};
				}
		}
			
		int y=0;
		while(fin!=inicio){
			vector[y]= (Punto) puntosInternosAux().get(fin);
			fin=ultimo[fin];
			y++;}
		Punto[] salida = new Punto[y];
		for(int qw=0; qw<y; qw++){
			salida[qw]=vector[qw];}

		return salida;
			
	} // fin del m�todo obtenerCaminoMenosPeajes
	
	/**
	 * @param p
	 * @return Punto[]
	 * 
	 * Obtiene los puntos alcanzables desde el punto dado. Si no hay
	 * puntos alcanzables, el nodo ser� aislado, y se devuelve un
	 * arreglo de puntos vac�o.
	 */
	public Punto[] obtenerPuntosAlcanzables(Punto p) {
		
		Vector<Punto> alcanzables = new Vector<Punto>();
		int n = puntosInternosAux().size();
		Punto u;
		boolean[] marcados = new boolean[n];
		Punto[] salida = new Punto[n];
		int inicio = getIndex(puntosInternosAux(),p.getNombre());
		marcados[inicio]=true;
		alcanzables.add(p);
			
		while (alcanzables.size()>0){
			u=alcanzables.lastElement();
			for(int i=0; i<n; i++){
				if((existeAvenidaPuntos(u.getNombre(),puntosInternosAux().get(i).getNombre())&&!marcados[i])){
					marcados[i]=true;
					alcanzables.add(puntosInternosAux().get(i));
				};
			}
			alcanzables.remove(u);			
		}

		Vector<Punto> vector = new Vector<Punto>();
		int h=0;
		for (int cont=0;cont<n;cont++)
			if(marcados[cont]){
				vector.add((Punto) puntosInternosAux().get(cont));
				h++;
				}
		salida= new Punto[h];
		for(int y=0; y<h; y++){
			salida[y]=vector.get(y);
		}
		return salida;
	} // fin del m�todo obtenerPuntosAlcanzables
	
	/**
	 * @return long
	 * 
	 * Este m�todo obtiene el m�ximo tr�fico que puede ser registrado
	 * en la red desde el punto origen al punto destino. Est� basado
	 * en el algoritmo de Ford Fulkerson.
	 */
	public long obtenerMaximoTraficoEnRed() {
		
		long traficoMax = 0;
		Vector<long[]> clasificaciones;	// vector de clasificaciones
		long capResiduales[][];	//	matriz de capacidades residuales
		Vector<Vector<Integer>> conjuntoSi;	// conjunto de nodos a los que se puede acceder desde el nodo i
		Vector<Long> fps;	// flujos m�nimos encontrados en todas las rutas
		
		// crea el vector de clasificaciones y asigna [Long.MAXVALUE, -1] al punto de acceso
		clasificaciones = new Vector<long[]>();
		long clAcceso[] = {0, Long.MAX_VALUE, -1};	// clasificaci�n para el punto de acceso
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
		
		boolean cond = false;	// condici�n de cierre: S0 es vac�o
		
		do {
			
			if(conjuntoSi.get(i).size()!=0) {	// existen arcos que conectan el nodo i con otros nodos

				// obtiene el nodo destino del arco con mayor capacidad que salga
				// de i hacia un nodo de Si
				int k = getNodoK(conjuntoSi.get(i), capResiduales, i);

				long ak = capResiduales[i][k];
				// crea una clasificaci�n para el nodo k
				long clask[] = {k, ak, i};
				clasificaciones.add(clask);

				if(k == puntosInternos.size()+1) {	// hemos llegado al punto de salida

					long min = obtenerMinimo(clasificaciones);
					
					fps.add(new Long(min));

					int p = (int)clasificaciones.lastElement()[0];
					int s = (int)clasificaciones.lastElement()[2];
					
					// actualiza la matriz de capacidades
					while(clasificaciones.size()!=1) {
						
						capResiduales[p][s] += min;
						capResiduales[s][p] -= min;
						
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
							
							conjuntoSi.get(i).add(new Integer(j));
						}
					}
				}
			}
			else {	// el conjunto Si est� vac�o
				
				if(i!=0) {	// estamos en un nodo distinto del origen
					
					long clasif[] = obtenerClasificacion(clasificaciones, i);			
					int padre = (int)clasif[2];
					int hijo = (int)clasif[0];
					conjuntoSi.get(padre).remove(new Integer(hijo));

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
		
	} // fin del m�todo obtenerMaximoTraficoEnRed
	
	
	/**
	 * @param vector
	 * @return Long
	 * 
	 * Obtiene el valor de flujo m�nimo del conjunto de clasificaciones registradas.
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
		
	} // fin del m�todo obtenerMinimo
	
	/**
	 * @param vector
	 * @param k
	 * @return long[]
	 * 
	 * Devuelve la clasificaci�n creada para el elemento de indice k.
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
	} // fin del m�todo obtenerCalificacion
	
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
		
	} // fin del m�todo removerElemento
	
	/**
	 * @return Vector<Punto>
	 * 
	 * Este m�todo crea un vector de puntos en base a los puntos
	 * registrados en la red. El primer punto es el de acceso.
	 * El punto final es el de salida.
	 */
	private Vector<Punto> crearVectorPuntos() {
		
		Vector<Punto> vector = new Vector<Punto>();
		
		// agrega el punto de acceso
		vector.add(acceso);
		// agrega los dem�s puntos internos
		for(int i=0; i<puntosInternos.size(); i++) {
			
			vector.add(puntosInternos.get(i));
		}
		// agrega el punto de salid
		vector.add(salida);
		
		return vector;
	} // fin del m�todo crearVectorPuntos
	
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
		
	} // fin del m�todo inicializarMatrizCapacidadesResiduales
	
	
	/**
	 * @param vector
	 * @param matriz
	 * @return int
	 * 
	 * Determina cual es el nodo destino del arco con mayor tr�fico m�ximo
	 * que lo une al nodo i.
	 */
	private int getNodoK(Vector<Integer> vector, long matriz[][], int i) {
		
		int indice = 0;
		int k = 0 , j;
		long maximo = 0;
		// crea una matriz de de capacidad original para poder
		// determinar si dos nodos cualesquiera se encuentran
		// conectados en una cierta direcci�n
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
		
	} // fin del m�todo getNodoK
	
	/**
	 * @param p
	 * @param numPeajes
	 * @return Punto[]
	 * 
	 * Obtiene los puntos alcanzables desde el punto "p" sin
	 * pasar por m�s de "n" peajes. Devuelve un arreglo
	 * vac�o en caso de que no haya puntos alcanzables.
	 */
	public Punto[] obtenerPuntosMenosNPeajes(Punto p, int numPeajes) {
		
		int n = puntosInternosAux().size();
		Punto[] vector = new Punto[n];
		int inicio = getIndex(puntosInternosAux(),p.getNombre());
		vector= new Punto[n];
				
		int[] ultimo = new int[n];
		boolean[] marcados = new boolean[n];
		double[] cantPeajeMenor = new double[n];
		
		for(int i=0; i<n; i++){
			ultimo[i]=numPeajes+1;
			marcados[i]=false;
			cantPeajeMenor[i]=MAX;
		}
		cantPeajeMenor[inicio]=0;
		ultimo[inicio]=0;
		
		for(int t=1; t<n; t++){
				double max = MAX;
				int v = 1;
				for(int j=0; j<n; j++){
					if(!marcados[j] && (cantPeajeMenor[j]<=max)){
						max=cantPeajeMenor[j];
						v=j;
						}
				}			
				marcados[v]=true;
				for(int k=1; k<n; k++){
					if(!marcados[k]&&existeAvenidaPuntos(puntosInternosAux().get(v).getNombre(),puntosInternosAux().get(k).getNombre())){
						if(cantPeajeMenor[k]>cantPeajeMenor[v]+1){
							cantPeajeMenor[k]=cantPeajeMenor[v]+1;
							ultimo[k]=v;
						};
					};
				}
		}
		int y=0;
		for(int x=0; x<n; x++){
			if(cantPeajeMenor[x]<=numPeajes){
				
				vector[y]= (Punto) puntosInternosAux().get(x);
				if(puntosInternosAux().get(x).getNombre().equals(acceso.getNombre())){
					vector[y]= (Punto) acceso;
				}
				if(puntosInternosAux().get(x).getNombre().equals(salida.getNombre())){
					vector[y]= (Punto) salida;
				}
				y++;}
		}
		Punto[] salida = new Punto[y];
		for(int qw=0; qw<y; qw++){
			salida[qw]=vector[qw];}

		return salida;
	} // fin del m�todo obtenerPuntosMenosNPeajes

	
	/**
	 * @param p
	 * @param nombre
	 * @return int
	 * 
	 * Devuelve la posici�n en el vector de puntos internos del punto 
	 * cuyo nombre se pasa como par�metro.
	 */
	private int getIndex(Vector<PuntoInterno> p, String nombre ) {
		
		for (int i = 0; i < p.size(); i++) {
			PuntoInterno puntoInterno = (PuntoInterno) p.get(i);
			if(puntoInterno.getNombre().equals(nombre))
				return i;
		}
		return -1;
	} // fin del m�todo getIndex
	
} // fin de la clase Red
