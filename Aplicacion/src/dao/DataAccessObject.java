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
 * Esta clase tendrá el papel de intermediario entre la aplicación
 * y los archivos donde se harán persistir las configuraciones
 * de la red de transporte.
 * Provee tanto un método de acceso a los archivos como un método
 * para dar persistencia.
 */
public abstract class DataAccessObject {
	
	// Constante de clase que indica la ruta donde se almacenarán
	// los archivos. Se supone inicialmente el directorio actual de la aplicación.
	private static String directorio = "/";
	
	/**
	 * @param objeto
	 * @param nombreArchivo
	 * 
	 * Este método se utiliza para hacer persistir un objeto en un archivo.
	 * Todas los objetos para los que tenga referencias también se harán
	 * persistir en el mismo archivo, así como las variables de tipos primitivos.
	 */
	public static void persistir(Serializable objeto, String nombreArchivo) throws Exception{
		
		FileOutputStream fos = null;	// archivo de salida para el stream
		ObjectOutputStream out = null;	// objeto stream de salida
		
		fos = new FileOutputStream(directorio + "/" + nombreArchivo);
		out = new ObjectOutputStream(fos);
		out.writeObject(objeto);	// escribe el objeto
		out.close();	// cierra el archivo y libera todas las referencias al mismo en memoria principal
		
	} // fin de método persistir
	
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
	} // fin de método cargar
	
	/**
	 * @param nombreDir
	 * 
	 * Establece el nombre del directorio
	 */
	public static void setDirectorio(String nombreDir) {
		
		directorio = nombreDir;
	} // fin del método setDirectorio
	
} // fin de clase DataAccessObject
