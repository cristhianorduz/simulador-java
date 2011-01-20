package aplicacion;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * @author Goti
 * 
 * Esta clase se encarga de mostrar el di�logo que permite
 * cargar una configuraci�n de red guardada con anterioridad.
 * Asimismo, tiene un conjunto de m�todos de verificaci�n.
 */
public class VentanaAbrir extends JFileChooser {
	
	private Aplicacion app;
	
	/**
	 * @param aplicacion
	 * 
	 * Constructor de clase.
	 */
	public VentanaAbrir(Aplicacion aplicacion) {
		
		super();
		app = aplicacion;	// referencia al JFrame padre
		this.addChoosableFileFilter(new Filtro());	// agrega un filtro
	} // fin de constructor de clase
	
	/**
	 * @author Goti
	 * 
	 * Clase empleada para establecer un filtro al di�logo
	 * utilizado para abrir archivos.
	 */
	class Filtro extends FileFilter {
		
	    public boolean accept(File file) {
	        String filename = file.getName();
	        return filename.endsWith(".red");
	    } // fin de m�todo accept
	    
	    public String getDescription() {
	        return "*.red";
	    } // fin de m�todo getDescription
	    
	} // fin de clase Filtro
	
	/**
	 * Prepara una ventana de di�logo para abrir un
	 * archivo con una configuraci�n de red y lo muestra.
	 */
	public void mostrar() {
		
		int resp = this.showOpenDialog(app);
		
		if(resp == JFileChooser.APPROVE_OPTION) {
			
			File file = this.getSelectedFile();
			
			cargarObjetoArchivo(file);
		}
		
	} // fin de m�todo mostrar
	
	/**
	 * @param archivo
	 * 
	 * Este m�todo recupera el nombre y directorio padre
	 * del archivo a cargar e invoca al m�todo para cargar
	 * el objeto correspondiente.
	 */
	public void cargarObjetoArchivo(File archivo) {
		
		String dir = archivo.getParent();	// directorio padre del archivo
		String nombre = archivo.getName();	// nombre del archivo
		
		app.cargarObjetoArchivo(dir, nombre);
	} // fin del m�todo cargarObjetoArchivo
	

} // fin de clase VentanaAbrir
