package aplicacion;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import aplicacion.VentanaAbrir.Filtro;

/**
 * @author Goti
 * 
 * Esta clase se encarga de mostrar el diálogo que permite
 * guardar una configuración de la red, así como también
 * tiene un conjunto de métodos para verificar valores almacenados.
 *
 */
public class VentanaGuardar extends JFileChooser {

	private Aplicacion app;
	
	/**
	 * @param aplicacion
	 * 
	 * Constructor de clase.
	 */
	public VentanaGuardar(Aplicacion aplicacion) {
		
		super();
		app = aplicacion;
		this.addChoosableFileFilter(new Filtro());	// agrega un filtro
	} // fin de constructor de clase
	
	/**
	 * @author Goti
	 * 
	 * Clase empleada para establecer un filtro al diálogo
	 * utilizado para guardar configuraciones de red.
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
	 * Prepara una ventana de diálogo para guardar un
	 * archivo con la configuración de la red y lo muestra.
	 * Posteriormente llama a una rutina para guardar el archivo.
	 */
	public void mostrar() {
		
		int resp = this.showSaveDialog(app);
		
		if(resp == JFileChooser.APPROVE_OPTION) {
			
			File file = this.getSelectedFile();
			
			guardarObjetoArchivo(file);
		}
		
	} // fin de método mostrar
	
	/**
	 * @param archivo
	 * 
	 * Este método recupera el nombre y directorio donde se almacenará
	 * el archivo de configuración de la red, e invoca a un método para
	 * lograr la persistencia del objeto.
	 */
	public void guardarObjetoArchivo(File archivo) {
		
		String dir = archivo.getParent();	// directorio donde se almacenará el archivo
		String nombre = archivo.getName();	// nombre del archivo
		
		if(!nombre.endsWith(".red")) {
			
			nombre += ".red";
		}
		
		app.guardarObjetoArchivo(dir, nombre);
	} // fin del método guardarObjetoArchivo
	
} // fin de clase VentanaGuardar
