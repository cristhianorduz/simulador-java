package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Goti
 * 
 * Esta clase tendr� el papel de intermediario entre la aplicaci�n
 * y los archivos donde se har�n persistir las configuraciones
 * de la red de transporte.
 * Provee tanto un m�todo de acceso a los archivos como un m�todo
 * para dar persistencia.
 */
public abstract class DataAccessObject {
	
	// Constante de clase que indica la ruta donde se almacenar�n
	// los archivos. Se supone inicialmente el directorio actual de la aplicaci�n.
	private static String directorio = "/";
	
	/**
	 * @param objeto
	 * @param nombreArchivo
	 * 
	 * Este m�todo se utiliza para hacer persistir un objeto en un archivo.
	 * Todas los objetos para los que tenga referencias tambi�n se har�n
	 * persistir en el mismo archivo, as� como las variables de tipos primitivos.
	 */
	public static void persistir(Serializable objeto, String nombreArchivo) throws Exception{
		
		FileOutputStream fos = null;	// archivo de salida para el stream
		ObjectOutputStream out = null;	// objeto stream de salida
		
		fos = new FileOutputStream(directorio + "/" + nombreArchivo);
		out = new ObjectOutputStream(fos);
		out.writeObject(objeto);	// escribe el objeto
		out.close();	// cierra el archivo y libera todas las referencias al mismo en memoria principal
		
	} // fin de m�todo persistir
	
	/**
	 * @param nombreArchivo
	 * @return Object
	 * 
	 * Lee un archivo de nombre nombreArchivo, recupera el objeto
	 * serializado y lo devuelve.
	 */
	public static Object cargar(String nombreArchivo) throws Exception{
		
		Object instancia = null;	// almacena la referencia al objeto recuperado
		FileInputStream fis = null;	// archivo de entrada para el stream
		ObjectInputStream in = null; // objeto stream de entrada
		
		fis = new FileInputStream(directorio + "/" + nombreArchivo);
		in = new ObjectInputStream(fis);
		instancia = in.readObject();
		in.close();
		
		return instancia ;
	} // fin de m�todo cargar
	
	/**
	 * @param nombreDir
	 * 
	 * Establece el nombre del directorio
	 */
	public static void setDirectorio(String nombreDir) {
		
		directorio = nombreDir;
	} // fin del m�todo setDirectorio
	
} // fin de clase DataAccessObject
