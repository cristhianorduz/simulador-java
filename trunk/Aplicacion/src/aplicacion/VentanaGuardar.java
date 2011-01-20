package aplicacion;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import aplicacion.VentanaAbrir.Filtro;

/**
 * @author Goti
 * 
 * Esta clase se encarga de mostrar el di�logo que permite
 * guardar una configuraci�n de la red, as� como tambi�n
 * tiene un conjunto de m�todos para verificar valores almacenados.
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
	 * Clase empleada para establecer un filtro al di�logo
	 * utilizado para guardar configuraciones de red.
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
	 * Prepara una ventana de di�logo para guardar un
	 * archivo con la configuraci�n de la red y lo muestra.
	 * Posteriormente llama a una rutina para guardar el archivo.
	 */
	public void mostrar() {
		
		int resp = this.showSaveDialog(app);
		
		if(resp == JFileChooser.APPROVE_OPTION) {
			
			File file = this.getSelectedFile();
			
			guardarObjetoArchivo(file);
		}
		
	} // fin de m�todo mostrar
	
	/**
	 * @param archivo
	 * 
	 * Este m�todo recupera el nombre y directorio donde se almacenar�
	 * el archivo de configuraci�n de la red, e invoca a un m�todo para
	 * lograr la persistencia del objeto.
	 */
	public void guardarObjetoArchivo(File archivo) {
		
		String dir = archivo.getParent();	// directorio donde se almacenar� el archivo
		String nombre = archivo.getName();	// nombre del archivo
		
		if(!nombre.endsWith(".red")) {
			
			nombre += ".red";
		}
		
		app.guardarObjetoArchivo(dir, nombre);
	} // fin del m�todo guardarObjetoArchivo
	
} // fin de clase VentanaGuardar
