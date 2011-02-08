package aplicacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


/**
 * @author Goti
 * 
 * Esta clase se utiliza para mostrar un panel de ayuda al usuario.
 */
public class VentanaAyuda extends JDialog {
	
	private Aplicacion app;
	
	private JTextArea infoAyuda;
	private JTree indice;
	private JButton botonOk;
	private String actual;
	
	/**
	 * @param ap
	 * 
	 * Constructor de clase.
	 */
	public VentanaAyuda(Aplicacion ap) {
		
		super();
		
		// establece la aplicacion
		this.app = app;
		
		inicializarInterfaz();
	} // fin del constructor de clase
	
	/**
	 * Crea los componentes de la ventana y los agrega.
	 */
	private void inicializarInterfaz() {
		
		// Indice de secciones del manual de ayuda
		// Modelo de datos
		DefaultMutableTreeNode contenidos = new DefaultMutableTreeNode("Contenidos");
		DefaultTreeModel modelo = new DefaultTreeModel(contenidos);
		// Vista del �rbol
		indice = new JTree(modelo);
		indice.addTreeSelectionListener(new TreeSelectionListener() {
			
			public void valueChanged(TreeSelectionEvent e) {
				
				DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)indice.getLastSelectedPathComponent();
				
				// si no hay nada seleccionado 
		        if (nodo == null) return;
		        
		        setTextoAyuda(nodo.toString());
			}
		});

		// Modelos de datos de los componentes de ayuda
		DefaultMutableTreeNode m1 = new DefaultMutableTreeNode("Sobre la interfaz");
		DefaultMutableTreeNode m2 = new DefaultMutableTreeNode("Configuraciones de red");
		DefaultMutableTreeNode m3 = new DefaultMutableTreeNode("Funcionalidades b�sicas");
		DefaultMutableTreeNode m4 = new DefaultMutableTreeNode("Funcionalidades especiales");
		// Inserta los componentes dentro del modelo
		modelo.insertNodeInto(m1, contenidos, 0);
		modelo.insertNodeInto(m2, contenidos, 1);
		modelo.insertNodeInto(m3, contenidos, 2);
		modelo.insertNodeInto(m4, contenidos, 3);
		
		// Area de texto con informaci�n de ayuda
		infoAyuda = new JTextArea();
		infoAyuda.setFont(new Font("Monospaced", Font.BOLD, 12));
		infoAyuda.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		infoAyuda.setPreferredSize(new Dimension(420, 390));
		infoAyuda.setEditable(false);
		// Secci�n de incio del manual de ayuda
		actual = "Contenidos";
		setTextoContenidos();
		
		// Boton Ok
		botonOk = new JButton("Ok");
		botonOk.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				dispose();
			}
		});
		
		botonOk.addKeyListener(new KeyListener());
		
		// Layout
		setLayout();
		
	} // fin del m�todo inicializarInterfaz
	
	
	/**
	 * Establece el layout del JDialog y agrega los componentes.
	 */
	private void setLayout() {
		
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		// Panel en ubicacion NORTH
		JPanel p1 = new JPanel();
		p1.setPreferredSize(new Dimension(600, 20));
		this.add(p1, BorderLayout.NORTH);
		
		// Panel en ubicacion WEST
		JPanel p2 = new JPanel();
		p2.setPreferredSize(new Dimension(20, 400));
		this.add(p2, BorderLayout.WEST);
		
		// Panel en ubicacion CENTER
		JPanel p3 = new JPanel();
		p3.setLayout(new BorderLayout());
		p3.setPreferredSize(new Dimension(220, 400));
		JPanel panelAux = new JPanel();
		panelAux.setPreferredSize(new Dimension(220, 5));
		p3.add(panelAux, BorderLayout.NORTH);
		panelAux = new JPanel();
		panelAux.setPreferredSize(new Dimension(220, 380));
		panelAux.setLayout(new BorderLayout());
		panelAux.add(indice, BorderLayout.CENTER);
		panelAux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p3.add(panelAux, BorderLayout.CENTER);
		panelAux = new JPanel();
		panelAux.setPreferredSize(new Dimension(220, 14));
		p3.add(panelAux, BorderLayout.SOUTH);
		this.add(p3, BorderLayout.CENTER);
		
		// Panel en ubicacion EAST
		JPanel p4 = new JPanel();
		p4.setPreferredSize(new Dimension(440, 400));
		p4.add(infoAyuda);
		this.add(p4, BorderLayout.EAST);
		
		// Panel en ubicacion SOUTH
		JPanel p5 = new JPanel();
		p5.setPreferredSize(new Dimension(600, 35));
		p5.add(botonOk);
		this.add(p5, BorderLayout.SOUTH);
		
	} // fin del m�todo setLayout
	
	
	/**
	 * @param String nombreNodo
	 * 
	 * Recibe como par�metro una cadena que indica la hoja con ayuda
	 * a mostrar. Se tienen 4 secciones:
	 * - Portada o secci�n de inicio
	 * - Crear, cargar y guardar configuraciones de red
	 * - Funcionalidades b�sicas
	 * - Funcionalidades especiales
	 * - Consideraciones sobre la interfaz
	 */
	private void setTextoAyuda(String nombreNodo) {
		
		if(!nombreNodo.equals(actual)) {
			
			if(nombreNodo.equals("Contenidos")) {
				
				setTextoContenidos();
				actual = "Contenidos";
			}
			else if(nombreNodo.equals("Sobre la interfaz")) {
				
				setTextoInterfaz();
				actual = "Sobre la interfaz";
			}
			else if(nombreNodo.equals("Configuraciones de red")) {
				
				setTextoConfiguraciones();
				actual = "Configuraciones de red";
			}
			else if(nombreNodo.equals("Funcionalidades b�sicas")) {
				
				setTextoFuncBasicas();
				actual = "Funcionalidades b�sicas";
			}
			else if(nombreNodo.equals("Funcionalidades especiales")) {
				
				setTextoFuncEsp();
				actual = "Funcionalidades especiales";
			}
		}
		
	} // fin del m�todo setTextoAyuda
	
	
	/**
	 * Muestra el texto de la secci�n contenidos.
	 */
	private void setTextoContenidos() {
		
		String texto = " Manual de ayuda\n" + 
					   " ---------------\n\n" +
					   "    Este manual le proporcionar� ayuda sobre las distintas\n" +
					   " funcionalidades que ofrece la aplicaci�n.\n" +
					   "    En el panel de la izquierda podr� visualizar las\n" +
					   " diferentes secciones del manual, cada una de las cuales\n" +
					   " podr� ser seleccionada haciendo click.\n\n" +
					   "    La primera secci�n se titula 'Sobre la interfaz' y\n" +
					   " contiene algunas consideraciones sobre el objetivo de cada\n" +
					   " panel, asi como tambi�n de las acciones que permite\n" +
					   " realizar en cuanto a modificaci�n de la red.\n\n" +
					   "    La segunda secci�n se titula 'Configuraciones de red',\n" +
					   " y proporciona ayuda para crear nuevas configuraciones de\n" +
					   " red, guardarlas en un archivo y recuperarlas cuando as�\n" +
					   " se lo desee.\n\n" +
					   "    La tercera y cuarta secciones proveen informaci�n sobre,\n" +
					   " las funcionalidades que ofrece la aplicaci�n.\n\n";
		
		infoAyuda.setText(texto);
		
	} // fin del m�todo setTextoContenidos
	
	/**
	 * Muestra el texto de la secci�n sobre la interfaz.
	 */
	private void setTextoInterfaz() {
		
		String texto = " Sobre la interfaz\n" + 
					   " -----------------\n\n" +
					   "    En la parte superior de la aplicaci�n podr� visualizar\n" +
					   " un men� con los siguientes items:\n" +
					   " - Archivo, para crear nuevas redes, cargar una existente o\n" +
					   "   guardar la configuraci�n de red actual.\n" +
					   " - Consulta, que permite manipular nodos y avenidas, asi\n" +
					   "   como aplicar operaciones sobre ellos.\n" +
					   " - Ayuda, que proporciona informaci�n sobre la aplicaci�n.\n\n" +
					   "    Tambi�n podr� visualizar un panel de 'Avenida', que \n" +
					   " mostrar� los par�metros de una avenida seleccionada y le\n" +
					   " permitir� editarlos.\n" +
					   "    De manera similar, hay un panel de 'Punto', que permite\n" +
					   " aplicar las mismas operaciones pero sobre puntos.\n" +
					   "    El panel de 'Resultado' le mostrar� los resultados de\n" +
					   " las consultas, correspondientes a funciones especiales.\n" +
					   "    Finalmente, podr� ver un panel de dibujo, que es un \n" +
					   " panel interactivo que le permitir� graficar elementos \n" +
					   " de la red as� como seleccionarlos para ver y editar sus\n" +
					   " propiedades.\n";
		
		infoAyuda.setText(texto);
		
	} // fin del m�todo setTextoInterfaz
	
	/**
	 * Muestra el texto de la secci�n configuraciones de red.
	 */
	private void setTextoConfiguraciones() {
		
		String texto = " Configuraciones de red\n" + 
					   " ----------------------\n\n" +
					   "    Las distintas opciones para tatar configuraciones de \n" +
					   " red las podr� encontrar en el item Archivo del men� \n" +
					   " superior. Estas opciones son las siguientes:\n\n" +
					   " - Nueva Red, que permite la creaci�n de una nueva red.\n\n" +
					   " - Recuperar Configuraci�n, que permite cargar un\n" +
					   "   archivo con una configuraci�n de red guardada.\n" +
					   "   Estos archivos tienen extensi�n '.red' y los \n" +
					   "   genera la aplicaci�n.\n\n" +
					   " - Guardar Configuraci�n, que permite dar persistencia\n" +
					   "   a la configuraci�n de red actual. Se genera as� un\n" +
					   "   archivo de extensi�n '.red'.\n\n" +
					   " - Limpiar Configuraci�n, que permite borrar los arcos\n" +
					   "   y nodos cargados, y comenzar una nueva red vac�a.\n\n" +
					   " - Salir, que permite cerrar la aplicaci�n.\n\n";
		
		infoAyuda.setText(texto);
		
	} // fin del m�todo setTextoConfiguraciones
	
	/**
	 * Muestra el texto de la secci�n funcionalidades b�sicas.
	 */
	private void setTextoFuncBasicas() {
		
		String texto = " Funcionalidades b�sicas\n" + 
					   " -----------------------\n\n" +
					   "    Las funcionalidades que se nombrar�n en esta secci�n \n" +
					   " se pueden encontrar en los dos primeros casilleros de\n" +
					   " opciones del item Consulta del men� superior.\n\n" +
					   " - Ingresar Punto, que permite ingresar un nuevo punto a\n" +
					   "   la red. Se le solicitar� hacer un click en la posici�n\n" +
					   "   del panel de dibujo donde desea ubicar el punto.\n" +
					   "   Nota: Los nodos amarillos corresponden a puntos\n" +
					   "   ingresados por el usuario, el nodo azul representa\n" +
					   "   el  acceso a la red y el nodo rojo la salida.\n" +
					   " - Ingresar Avenida, que permite cargar una avenida entre\n" +
					   "   dos puntos cualesquiera. Las avenidas podr�n tener dos\n" +
					   "   colores: negro (si est� habilitada) o naranja (si est�\n" +
					   "   deshabilitada).\n" +
					   " - Eliminar Nodo, que permite la eliminaci�n de un punto.\n" +
					   "   Para eliminarlo se le pedir� que seleccione el nodo \n" +
					   "   mediante un click en el panel de dibujo.\n" +
					   " - Eliminar Avenida, similar a la opci�n anterior pero\n" +
					   "   aplicada a una avenida.\n\n";
		
		infoAyuda.setText(texto);
		
	} // fin del m�todo setTextoFuncBasicas
	
	/**
	 * Muestra el texto de la secci�n funcionalidades especiales.
	 */
	private void setTextoFuncEsp() {
		
		String texto = " Funcionalidades especiales\n" + 
					   " --------------------------\n\n" +
					   "    Las funcionalidades que se nombrar�n en esta secci�n \n" +
					   " se pueden encontrar en el tercer casillero de opciones \n" +
					   " del item Consulta del men� superior.\n\n" +
					   " - Camino m�s corto, que solicita la selecci�n de dos \n" +
					   "   puntos y determina el camino m�s corto para llegar del \n" +
					   "   primero al segundo\n" +
					   " - Camino con menos peajes, que solicita la selecci�n de \n" +
					   "   dos puntos y determina el camino con menos peajes para \n" +
					   "   llegar del primero al segundo\n" +
					   " - Puntos alcanzables, que solicita la selecci�n de un \n" +
					   "   punto y determina los puntos alcanzables a partir del \n" +
					   "   mismo.\n" +
					   " - M�ximo tr�fico en red, que determina el m�ximo tr�fico\n" +
					   "   posible desde el punto de acceso al punto de salida.\n" +
					   " - Camino con menos de N peajes, que muestra los puntos que\n" +
					   "   pueden alcanzarse a partir del punto ingresado, sin \n" +
					   "   pasar por m�s de N peajes (N tambi�n se solicita)\n";
		
		infoAyuda.setText(texto);
		
	} // fin del m�todo setTextoFuncEsp
	
	/**
	 * @author Goti
	 * 
	 * Esta clase se utiliza de manera auxiliar para implementar
	 * un key listener para la interfaz acerca de.
	 */
	private class KeyListener extends KeyAdapter {
		
        public void keyPressed(KeyEvent e) {

            String key = e.getKeyText(e.getKeyCode());
            key = key.toUpperCase();
            
            // maneja la pulsaci�n de la tecla ENTER
            if (key.equals("INTRODUZCA")) {

            	dispose();
            }
        }
    } // fin de clase KeyListeners

} // fin de clase VentanaAyuda
