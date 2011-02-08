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
		// Vista del árbol
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
		DefaultMutableTreeNode m3 = new DefaultMutableTreeNode("Funcionalidades básicas");
		DefaultMutableTreeNode m4 = new DefaultMutableTreeNode("Funcionalidades especiales");
		// Inserta los componentes dentro del modelo
		modelo.insertNodeInto(m1, contenidos, 0);
		modelo.insertNodeInto(m2, contenidos, 1);
		modelo.insertNodeInto(m3, contenidos, 2);
		modelo.insertNodeInto(m4, contenidos, 3);
		
		// Area de texto con información de ayuda
		infoAyuda = new JTextArea();
		infoAyuda.setFont(new Font("Monospaced", Font.BOLD, 12));
		infoAyuda.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		infoAyuda.setPreferredSize(new Dimension(420, 390));
		infoAyuda.setEditable(false);
		// Sección de incio del manual de ayuda
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
		
	} // fin del método inicializarInterfaz
	
	
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
		
	} // fin del método setLayout
	
	
	/**
	 * @param String nombreNodo
	 * 
	 * Recibe como parámetro una cadena que indica la hoja con ayuda
	 * a mostrar. Se tienen 4 secciones:
	 * - Portada o sección de inicio
	 * - Crear, cargar y guardar configuraciones de red
	 * - Funcionalidades básicas
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
			else if(nombreNodo.equals("Funcionalidades básicas")) {
				
				setTextoFuncBasicas();
				actual = "Funcionalidades básicas";
			}
			else if(nombreNodo.equals("Funcionalidades especiales")) {
				
				setTextoFuncEsp();
				actual = "Funcionalidades especiales";
			}
		}
		
	} // fin del método setTextoAyuda
	
	
	/**
	 * Muestra el texto de la sección contenidos.
	 */
	private void setTextoContenidos() {
		
		String texto = " Manual de ayuda\n" + 
					   " ---------------\n\n" +
					   "    Este manual le proporcionará ayuda sobre las distintas\n" +
					   " funcionalidades que ofrece la aplicación.\n" +
					   "    En el panel de la izquierda podrá visualizar las\n" +
					   " diferentes secciones del manual, cada una de las cuales\n" +
					   " podrá ser seleccionada haciendo click.\n\n" +
					   "    La primera sección se titula 'Sobre la interfaz' y\n" +
					   " contiene algunas consideraciones sobre el objetivo de cada\n" +
					   " panel, asi como también de las acciones que permite\n" +
					   " realizar en cuanto a modificación de la red.\n\n" +
					   "    La segunda sección se titula 'Configuraciones de red',\n" +
					   " y proporciona ayuda para crear nuevas configuraciones de\n" +
					   " red, guardarlas en un archivo y recuperarlas cuando así\n" +
					   " se lo desee.\n\n" +
					   "    La tercera y cuarta secciones proveen información sobre,\n" +
					   " las funcionalidades que ofrece la aplicación.\n\n";
		
		infoAyuda.setText(texto);
		
	} // fin del método setTextoContenidos
	
	/**
	 * Muestra el texto de la sección sobre la interfaz.
	 */
	private void setTextoInterfaz() {
		
		String texto = " Sobre la interfaz\n" + 
					   " -----------------\n\n" +
					   "    En la parte superior de la aplicación podrá visualizar\n" +
					   " un menú con los siguientes items:\n" +
					   " - Archivo, para crear nuevas redes, cargar una existente o\n" +
					   "   guardar la configuración de red actual.\n" +
					   " - Consulta, que permite manipular nodos y avenidas, asi\n" +
					   "   como aplicar operaciones sobre ellos.\n" +
					   " - Ayuda, que proporciona información sobre la aplicación.\n\n" +
					   "    También podrá visualizar un panel de 'Avenida', que \n" +
					   " mostrará los parámetros de una avenida seleccionada y le\n" +
					   " permitirá editarlos.\n" +
					   "    De manera similar, hay un panel de 'Punto', que permite\n" +
					   " aplicar las mismas operaciones pero sobre puntos.\n" +
					   "    El panel de 'Resultado' le mostrará los resultados de\n" +
					   " las consultas, correspondientes a funciones especiales.\n" +
					   "    Finalmente, podrá ver un panel de dibujo, que es un \n" +
					   " panel interactivo que le permitirá graficar elementos \n" +
					   " de la red así como seleccionarlos para ver y editar sus\n" +
					   " propiedades.\n";
		
		infoAyuda.setText(texto);
		
	} // fin del método setTextoInterfaz
	
	/**
	 * Muestra el texto de la sección configuraciones de red.
	 */
	private void setTextoConfiguraciones() {
		
		String texto = " Configuraciones de red\n" + 
					   " ----------------------\n\n" +
					   "    Las distintas opciones para tatar configuraciones de \n" +
					   " red las podrá encontrar en el item Archivo del menú \n" +
					   " superior. Estas opciones son las siguientes:\n\n" +
					   " - Nueva Red, que permite la creación de una nueva red.\n\n" +
					   " - Recuperar Configuración, que permite cargar un\n" +
					   "   archivo con una configuración de red guardada.\n" +
					   "   Estos archivos tienen extensión '.red' y los \n" +
					   "   genera la aplicación.\n\n" +
					   " - Guardar Configuración, que permite dar persistencia\n" +
					   "   a la configuración de red actual. Se genera así un\n" +
					   "   archivo de extensión '.red'.\n\n" +
					   " - Limpiar Configuración, que permite borrar los arcos\n" +
					   "   y nodos cargados, y comenzar una nueva red vacía.\n\n" +
					   " - Salir, que permite cerrar la aplicación.\n\n";
		
		infoAyuda.setText(texto);
		
	} // fin del método setTextoConfiguraciones
	
	/**
	 * Muestra el texto de la sección funcionalidades básicas.
	 */
	private void setTextoFuncBasicas() {
		
		String texto = " Funcionalidades básicas\n" + 
					   " -----------------------\n\n" +
					   "    Las funcionalidades que se nombrarán en esta sección \n" +
					   " se pueden encontrar en los dos primeros casilleros de\n" +
					   " opciones del item Consulta del menú superior.\n\n" +
					   " - Ingresar Punto, que permite ingresar un nuevo punto a\n" +
					   "   la red. Se le solicitará hacer un click en la posición\n" +
					   "   del panel de dibujo donde desea ubicar el punto.\n" +
					   "   Nota: Los nodos amarillos corresponden a puntos\n" +
					   "   ingresados por el usuario, el nodo azul representa\n" +
					   "   el  acceso a la red y el nodo rojo la salida.\n" +
					   " - Ingresar Avenida, que permite cargar una avenida entre\n" +
					   "   dos puntos cualesquiera. Las avenidas podrán tener dos\n" +
					   "   colores: negro (si está habilitada) o naranja (si está\n" +
					   "   deshabilitada).\n" +
					   " - Eliminar Nodo, que permite la eliminación de un punto.\n" +
					   "   Para eliminarlo se le pedirá que seleccione el nodo \n" +
					   "   mediante un click en el panel de dibujo.\n" +
					   " - Eliminar Avenida, similar a la opción anterior pero\n" +
					   "   aplicada a una avenida.\n\n";
		
		infoAyuda.setText(texto);
		
	} // fin del método setTextoFuncBasicas
	
	/**
	 * Muestra el texto de la sección funcionalidades especiales.
	 */
	private void setTextoFuncEsp() {
		
		String texto = " Funcionalidades especiales\n" + 
					   " --------------------------\n\n" +
					   "    Las funcionalidades que se nombrarán en esta sección \n" +
					   " se pueden encontrar en el tercer casillero de opciones \n" +
					   " del item Consulta del menú superior.\n\n" +
					   " - Camino más corto, que solicita la selección de dos \n" +
					   "   puntos y determina el camino más corto para llegar del \n" +
					   "   primero al segundo\n" +
					   " - Camino con menos peajes, que solicita la selección de \n" +
					   "   dos puntos y determina el camino con menos peajes para \n" +
					   "   llegar del primero al segundo\n" +
					   " - Puntos alcanzables, que solicita la selección de un \n" +
					   "   punto y determina los puntos alcanzables a partir del \n" +
					   "   mismo.\n" +
					   " - Máximo tráfico en red, que determina el máximo tráfico\n" +
					   "   posible desde el punto de acceso al punto de salida.\n" +
					   " - Camino con menos de N peajes, que muestra los puntos que\n" +
					   "   pueden alcanzarse a partir del punto ingresado, sin \n" +
					   "   pasar por más de N peajes (N también se solicita)\n";
		
		infoAyuda.setText(texto);
		
	} // fin del método setTextoFuncEsp
	
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
            
            // maneja la pulsación de la tecla ENTER
            if (key.equals("INTRODUZCA")) {

            	dispose();
            }
        }
    } // fin de clase KeyListeners

} // fin de clase VentanaAyuda
