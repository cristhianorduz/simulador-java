package aplicacion;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * @author Goti
 * 
 * Esta clase se encarga de mostrar el diálogo que permite
 * cargar una configuración de red guardada con anterioridad.
 * Asimismo, tiene un conjunto de métodos de verificación.
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
	 * Clase empleada para establecer un filtro al diálogo
	 * utilizado para abrir archivos.
	 */
	class Filtro extends FileFilter {
		
	    public boolean accept(File file) {
	        String filename = file.getName();
	        return filename.endsWith(".red");
	    } // fin de método accept
	    
	    public String getDescription() {
	        return "*.red";
	    } // fin de método getDescription
	    
	} // fin de clase Filtro
	
	/**
	 * Prepara una ventana de diálogo para abrir un
	 * archivo con una configuración de red y lo muestra.
	 */
	public void mostrar() {
		
		int resp = this.showOpenDialog(app);
		
		if(resp == JFileChooser.APPROVE_OPTION) {
			
			File file = this.getSelectedFile();
			
			cargarObjetoArchivo(file);
		}
		
	} // fin de método mostrar
	
	/**
	 * @param archivo
	 * 
	 * Este método recupera el nombre y directorio padre
	 * del archivo a cargar e invoca al método para cargar
	 * el objeto correspondiente.
	 */
	public void cargarObjetoArchivo(File archivo) {
		
		String dir = archivo.getParent();	// directorio padre del archivo
		String nombre = archivo.getName();	// nombre del archivo
		
		app.cargarObjetoArchivo(dir, nombre);
	} // fin del método cargarObjetoArchivo
	

} // fin de clase VentanaAbrir
