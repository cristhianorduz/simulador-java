package aplicacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.*;
import dao.DataAccessObject;

import red.Avenida;
import red.Punto;
import red.PuntoInterno;
import red.Red;

/**
 * @author Goti
 *
 * Esta clase contiene los elementos y funcionalidades correspondientes
 * a la GUI de la aplicación. Tambien cumple la función de clase de control,
 * en tanto se encarga de la lógica y coordinación de los componentes de la 
 * aplicación.
 */
public class Aplicacion extends JFrame{
	
	// Red de transporte
	private Red red;
	
	// indice de la avenida seleccionada
	private int indiceAvenida;
	
	// indice del punto seleccionado
	private int indicePunto;
	
	// Barra de menú
	private JMenuBar jMenuBar;
	
	// Menu Archivo
	private JMenu archivo;
	private JMenuItem nuevaRed;
	private JMenuItem recuperarConf;
	private JMenuItem guardarConf;
	private JMenuItem limpiarConf;
	private JMenuItem salir;
	
	// Menu de Consultas para operar la Red
	private JMenu consultas;
	private JMenuItem ingresarPunto;
	private JMenuItem ingresarAv;
	private JMenuItem eliminarNodo;
	private JMenuItem eliminarAv;
	private JMenuItem opcA;
	private JMenuItem opcB;
	private JMenuItem opcC;
	private JMenuItem opcD;
	private JMenuItem opcE;
	
	// Menu de Ayuda para el Usuario
	private JMenu ayuda;
	private JMenuItem acercaDe;
	private JMenuItem ayudaProg;
	
	// Layout
	private BorderLayout layout;
	
	// Panel contenedor principal
	private JPanel panelContenedor;
	
	// Paneles contenedores secundarios
	private JPanel panelSecundarioSup;
	private JPanel panelSecundarioIzq;
	private JPanel panelSecundarioDer;
	private JPanel panelSecundarioInf;
	
	// Panel de visualización del grafo
	private JPanel panelGrafo;
	private PanelDibujo panelDibujo;
	
	// Panel de visualización de avenida seleccionada
	private JPanel panelAvenida;
	private JLabel labelTrafico;
	private JTextField fieldTrafico;
	private JLabel labelDistancia;
	private JTextField fieldDistancia;
	private JLabel labelEstado;
	private JComboBox boxEstado;
	private JButton botonEditarAv;
	
	// Panel de visualización de punto seleccionado
	private JPanel panelPunto;
	private JLabel labelNombre;
	private JTextField fieldNombre;
	private JLabel labelCosto;
	private JTextField fieldCosto;
	private JButton botonEditarPunto;
	
	// Panel de visualización de resultados de consultas
	private JPanel panelResultado;
	private JTextArea areaResultado;
	
	public static void main(String[] args){
	
		Aplicacion ap = new Aplicacion();
		
		ap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ap.setSize( 800, 600 );
		ap.setLocationRelativeTo(null);
		ap.setResizable(false);
		ap.setVisible(true);
	} // fin del método main
	
	/**
	 * Constructor de clase.
	 * Se emplea excepciones para informar posibles errores de inicio.
	 */
	private Aplicacion() {
		
		try {
			inicializar();
		}
		catch (Exception e) {
			mostrarMensajeError("Se ha producido un error en la aplicación y deberá cerrarse");
			dispose();
		}
	} // fin del constructor de clase
	
	/**
	 * Este método crea la interfaz basica de la aplicación.
	 */
	private void inicializar() {
		
		// Se setea el titulo
		this.setTitle("Simulador de Tráfico");
		
		// Layout
		layout = new BorderLayout();
		this.getContentPane().setLayout(layout);
		
		// Se crea una barra de menú
		jMenuBar = new JMenuBar();
		this.setJMenuBar(jMenuBar);
		
		// agrega el menú Archivo a la barra de menú
		agregarJMenuArchivo();
	
		// agrega el menú Consulta a la barra de menú
		agregarJMenuConsulta();
		
		// agrega el menú Ayuda a la barra de menú
		agregarJMenuAyuda();
		
		// Se crea el panel principal de la aplicacion
		agregarPanelPrincipal();
		
		// Se instancia la clase Red
		red = new Red(180, 20, 180, 400);
		
		// Se inicializan los indices de avenida y punto seleccionados
		indiceAvenida = -1;
		indicePunto = -1;

	} // fin del método inicializar
	
	
	/**
	 * Este método crea un menú Archivo y le agrega los items:
	 * - Nueva Red
	 * - Recuperar Configuración
	 * - Guardar Configuración
	 * - Limpiar Configuración
	 * - Salir
	 */
	private void agregarJMenuArchivo() {
		
		// Elemento "Archivo"
		archivo = new JMenu();
		archivo.setText("Archivo");
		archivo.setMnemonic('A');
		
		// Opcion Nueva Red
		nuevaRed = new JMenuItem();
		nuevaRed.setText("Nueva Red   Alt+A+N");
		nuevaRed.setMnemonic('N');
		nuevaRed.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
					mostrarPreguntaGuardar();
			}
		});
		
		// Opcion Recuperar Configuracion
		recuperarConf = new JMenuItem();
		recuperarConf.setText("Recuperar Configuración   Alt+A+R");
		recuperarConf.setMnemonic('R');
		recuperarConf.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarRecuperarConf();
			}
		});
	
		// Opcion Guardar Configuracion
		guardarConf = new JMenuItem();
		guardarConf.setText("Guardar Configuración   Alt+A+G");
		guardarConf.setMnemonic('G');
		guardarConf.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarGuardarConf();
			}
		});
		
		// Opcion Limpiar Configuracion
		limpiarConf = new JMenuItem();
		limpiarConf.setText("Limpiar Configuración   Alt+A+L");
		limpiarConf.setMnemonic('L');
		limpiarConf.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarPreguntaGuardar();
			}
		});
	
		// Opcion Salir
		salir = new JMenuItem();
		salir.setText("Salir   Alt+A+S");
		salir.setMnemonic('S');
		salir.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				dispose();
			}
		});
		
		archivo.add(nuevaRed);
		archivo.addSeparator();
		archivo.add(recuperarConf);
		archivo.add(guardarConf);
		archivo.add(limpiarConf);
		archivo.addSeparator();
		archivo.add(salir);
		
		jMenuBar.add(archivo);
	} // fin del método agregarJMenuArchivo
	
	
	/**
	 * Este método agrega el menú Consulta y le agrega los items:
	 * - Ingresar Punto
	 * - Ingresar Avenida
	 * - Editar Avenida
	 * - Ver Nodo
	 * - Ver Avenida
	 * - Camino más corto
	 * - Camino con menos peajes
	 * - Puntos alcanzables
	 * - Máximo tráfico en red
	 * - Camino con peajes máximos
	 */
	private void agregarJMenuConsulta() {
		
		// Elemento "Consulta"
		consultas = new JMenu();
		consultas.setText("Consulta");
		consultas.setMnemonic('C');
		
		// Opcion Ingresar Punto
		ingresarPunto = new JMenuItem();
		ingresarPunto.setText("Ingresar Punto   Alt+C+P");
		ingresarPunto.setMnemonic('P');
		ingresarPunto.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarMensajeInfo("Haz click en un punto del panel para ubicar el nuevo Nodo.");
				panelDibujo.setMouseListener(2);
			}
		});
		
		// Opcion Ingresar Avenida
		ingresarAv = new JMenuItem();
		ingresarAv.setText("Ingresar Avenida   Alt+C+A");
		ingresarAv.setMnemonic('A');
		ingresarAv.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarIngresarAvenida();
			}
		});
		
		// Opcion Eliminar Nodo
		eliminarNodo = new JMenuItem();
		eliminarNodo.setText("Eliminar Nodo   Alt+C+D");
		eliminarNodo.setMnemonic('D');
		eliminarNodo.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarMensajeInfo("Haz click en el panel para seleccionar el punto a eliminar.");
				panelDibujo.setMouseListener(4);
			}
		});
		
		// Opcion Eliminar Avenida
		eliminarAv = new JMenuItem();
		eliminarAv.setText("Eliminar Avenida   Alt+C+B");
		eliminarAv.setMnemonic('B');
		eliminarAv.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarMensajeInfo("Haz click en el panel para seleccionar la avenida a eliminar.");
				panelDibujo.setMouseListener(5);
			}
		});
		
		// Opcion Camino más Corto
		opcA = new JMenuItem();
		opcA.setText("Camino más Corto   Alt+C+G");
		opcA.setMnemonic('G');
		opcA.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarMensajeInfo("Haz click en el panel para seleccionar el nodo origen.");
				panelDibujo.setMouseListener(6);
			}
		});
		
		// Opcion Camino con menos peajes
		opcB = new JMenuItem();
		opcB.setText("Camino con menos peajes   Alt+C+H");
		opcB.setMnemonic('H');
		opcB.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarMensajeInfo("Haz click en el panel para seleccionar el nodo origen.");
				panelDibujo.setMouseListener(7);
			}
		});
		
		// Opcion Puntos alcanzables
		opcC = new JMenuItem();
		opcC.setText("Puntos alcanzables   Alt+C+J");
		opcC.setMnemonic('J');
		opcC.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarMensajeInfo("Haz click en el panel para seleccionar el nodo origen.");
				panelDibujo.setMouseListener(8);
			}
		});
		
		// Opcion Máximo tráfico en red
		opcD = new JMenuItem();
		opcD.setText("Máximo tráfico en red   Alt+C+K");
		opcD.setMnemonic('K');
		opcD.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				obtenerMaximoTraficoEnRed();
			}
		});
		
		// Opcion Camino con peajes máximos
		opcE = new JMenuItem();
		opcE.setText("Camino con menos de N peajes   Alt+C+L");
		opcE.setMnemonic('L');
		opcE.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarMensajeInfo("Haz click en el panel para seleccionar el nodo origen.");
				panelDibujo.setMouseListener(9);
			}
		});
		
		consultas.add(ingresarPunto);
		consultas.add(ingresarAv);
		consultas.addSeparator();
		consultas.add(eliminarNodo);
		consultas.add(eliminarAv);
		consultas.addSeparator();
		consultas.add(opcA);
		consultas.add(opcB);
		consultas.add(opcC);
		consultas.add(opcD);
		consultas.add(opcE);
		
		jMenuBar.add(consultas);
	} // fin del método agregarJMenuConsulta
	
	
	/**
	 * Este método crea el menú Ayuda y le agrega los items:
	 * - Acerca de
	 * - Ayuda del Programa
	 */
	private void agregarJMenuAyuda() {
		
		// Elemento "Ayuda"
		ayuda = new JMenu();
		ayuda.setText("Ayuda");
		ayuda.setMnemonic('H');
		
		// Opcion Acerca de
		acercaDe = new JMenuItem();
		acercaDe.setText("Acerca de...   Alt+A+A");
		acercaDe.setMnemonic('A');
		acercaDe.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarAcercaDe();
			}
		});
		
		// Opcion Ayuda del Programa
		ayudaProg = new JMenuItem();
		ayudaProg.setText("Ayuda del Programa   Alt+A+H");
		ayudaProg.setMnemonic('H');
		ayudaProg.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarManualAyuda();
			}
		});
		
		ayuda.add(acercaDe);
		ayuda.add(ayudaProg);
		
		jMenuBar.add(ayuda);
	} // fin del método agregarJMenuAyuda
	
	/**
	 * Este método crea el panel principal de la aplicación.
	 * Este panel contiene los elementos:
	 * - panel secundario izquierdo
	 * - panel secundario derecho
	 */
	private void agregarPanelPrincipal() {
		
		panelContenedor = new JPanel();
		
		// Layout
		BorderLayout panelContLayout = new BorderLayout();
		panelContenedor.setLayout(panelContLayout);
		
		// Agrega los paneles contenedores
		agregarPanelSecundarioSup();
		
		agregarPanelSecundarioIzq();
		
		agregarPanelSecundarioDer();
		
		agregarPanelSecundarioInf();
		
		this.getContentPane().add(panelContenedor);
	} // fin del método agregarPanelPrincipal
	
	/**
	 * Este método crea el panel secundario superior y lo agrega al contenedor principal.
	 */
	private void agregarPanelSecundarioSup() {
		
		panelSecundarioSup = new JPanel();
		
		panelSecundarioSup.setPreferredSize(new Dimension(800, 20));
		
		panelContenedor.add(panelSecundarioSup, BorderLayout.NORTH);
	} // fin del método agregarPanelSecundarioSup
	
	/**
	 * Este método crea el panel secundario izquierdo y lo agrega al contenedor principal.
	 * * Este panel contiene los elementos:
	 * - panelAvenida
	 * - panelPunto
	 * - panelResultado
	 */
	private void agregarPanelSecundarioIzq() {
		
		panelSecundarioIzq = new JPanel();
		
		// Se agrega el panel de avenida
		crearPanelAvenida();
		
		// Se agrega el panel de punto
		crearPanelPunto();
		
		// Se agrega el panel de resultado
		crearPanelResultado();
		
		panelSecundarioIzq.add(panelAvenida);
		panelSecundarioIzq.add(panelPunto);
		panelSecundarioIzq.add(panelResultado);
		
		panelContenedor.add(panelSecundarioIzq, BorderLayout.CENTER);
	} // fin del método agregarPanelSecundarioIzq
	
	/**
	 * Este método crea el panel secundario derecho y lo agrega al contenedor principal.
	 * Este panel contiene el elemento:
	 * - panelGrafo
	 */
	private void agregarPanelSecundarioDer() {
		
		panelSecundarioDer = new JPanel();
		
		// Se agrega el panel de grafo
		crearPanelGrafo();
		
		panelSecundarioDer.add(panelGrafo);
		
		panelContenedor.add(panelSecundarioDer, BorderLayout.EAST);
	} // fin del método agregarPanelSecundarioDer
	
	/**
	 * Este método crea el panel secundario inferior y lo agrega al contenedor principal.
	 */
	private void agregarPanelSecundarioInf() {
		
		panelSecundarioInf = new JPanel();

		panelSecundarioInf.setPreferredSize(new Dimension(800, 20));
		
		panelContenedor.add(panelSecundarioInf, BorderLayout.SOUTH);
	} // fin del método agregarPanelSecundarioInf
	
	/**
	 * Este método crea el panel secundario y se inserta en el
	 * el panel que se utiliza para visualizar el grafo de la Red.
	 */
	private void crearPanelGrafo() {
		
		panelGrafo = new JPanel();
		panelGrafo.setBackground(Color.WHITE);
		panelGrafo.setBorder(BorderFactory.createTitledBorder("Red de transporte"));
		panelGrafo.setName("Panel de grafo");
		panelGrafo.setPreferredSize(new Dimension(370, 470));
		
		panelDibujo = new PanelDibujo(this);
		panelDibujo.setPreferredSize(new Dimension(360, 440));
		
		panelGrafo.add(panelDibujo);
	} // fin del método crearPanelGrafo
	
	/**
	 * Este método crea el panel para mostrar los atributos de una avenida seleccionada.
	 */
	private void crearPanelAvenida() {
		
		panelAvenida = new JPanel();
		panelAvenida.setBorder(BorderFactory.createTitledBorder("Avenida"));
        panelAvenida.setName("Panel de avenida");
        panelAvenida.setPreferredSize(new Dimension(320, 150));
		
		// Layout
		BorderLayout panelAvenidaLayout = new BorderLayout();
        
        // Campo tráfico máximo
		labelTrafico = new JLabel();
        labelTrafico.setText("Tráfico Máximo: ");
        labelTrafico.setName("label trafico");
        fieldTrafico = new JTextField();
        fieldTrafico.setText("");
        fieldTrafico.setName("field trafico");
        fieldTrafico.setPreferredSize(new Dimension(150, 20));
        fieldTrafico.setBackground(Color.WHITE);
        fieldTrafico.setEditable(false);
        
        // Campo distancia
        labelDistancia = new JLabel();
        labelDistancia.setText("            Distancia: ");
        labelDistancia.setName("label distancia");
        fieldDistancia = new JTextField();
        fieldDistancia.setText("");
        fieldDistancia.setName("field distancia");
        fieldDistancia.setPreferredSize(new Dimension(150, 20));
        fieldDistancia.setBackground(Color.WHITE);
        fieldDistancia.setEditable(false);
        
        // Campo estado de avenida
        labelEstado = new JLabel();
        labelEstado.setText("");
        labelEstado.setName("label estado");
        boxEstado = new JComboBox();
        boxEstado.setModel(new DefaultComboBoxModel(new String[] { "Habilitada", "En Reparación" }));
        boxEstado.setName("box estado");
        boxEstado.setBackground(Color.WHITE);
        boxEstado.setEnabled(false);
        
        // Boton editar avenida
        botonEditarAv = new JButton("Editar");
        botonEditarAv.setEnabled(false);
        botonEditarAv.setName("editarAv");
        botonEditarAv.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				editarAvenidaSeleccionada();
			}
		});
        botonEditarAv.addKeyListener(new KeyListener());
        
        panelAvenida.setLayout(panelAvenidaLayout);
    
        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setPreferredSize(new Dimension(300, 120));
        
        // Panel que contiene el campo tráfico
        JPanel panelTrafico = new JPanel();
        panelTrafico.setPreferredSize(new Dimension(300, 25));
        panelTrafico.add(labelTrafico);
        panelTrafico.add(fieldTrafico);
        
        // Panel que contiene el campo distancia
        JPanel panelDistancia = new JPanel();
        panelDistancia.setPreferredSize(new Dimension(300, 25));
        panelDistancia.add(labelDistancia);
        panelDistancia.add(fieldDistancia);
        
        // Panel que contiene el comboBox
        JPanel panelEstado = new JPanel();
        panelEstado.setPreferredSize(new Dimension(300, 36));
        panelEstado.setLayout(new BorderLayout());
        JPanel panelAux = new JPanel();
        panelAux.setPreferredSize(new Dimension(130, 10));
        panelEstado.add(panelAux, BorderLayout.WEST);
        panelAux = new JPanel();
        panelAux.setPreferredSize(new Dimension(30, 10));
        panelEstado.add(panelAux, BorderLayout.EAST);
        panelAux = new JPanel();
        panelAux.setPreferredSize(new Dimension(300, 11));
        panelEstado.add(panelAux, BorderLayout.SOUTH);
        panelEstado.add(boxEstado, BorderLayout.CENTER);
        
        // Agrega los componentes en el panel central
        panelCentral.add(panelTrafico, BorderLayout.NORTH);
        panelCentral.add(panelDistancia, BorderLayout.CENTER);
        panelCentral.add(panelEstado, BorderLayout.SOUTH);
        panelAvenida.add(panelCentral, BorderLayout.CENTER);
        
        // Panel que contiene el botón Editar
        JPanel panelInf = new JPanel();
        panelInf.setLayout(new BorderLayout());
        panelInf.setPreferredSize(new Dimension(300, 34));
        panelAux = new JPanel();
        panelAux.setPreferredSize(new Dimension(180, 28));
        panelInf.add(panelAux, BorderLayout.WEST);
        panelAux = new JPanel();
        panelAux.setPreferredSize(new Dimension(30, 28));
        panelInf.add(panelAux, BorderLayout.EAST);
        panelAux = new JPanel();
        panelAux.setPreferredSize(new Dimension(300, 5));
        panelInf.add(panelAux, BorderLayout.SOUTH);
        panelInf.add(botonEditarAv, BorderLayout.CENTER);
        panelAvenida.add(panelInf, BorderLayout.SOUTH);
        
	} // fin del método crearPanelAvenida
	
	/**
	 * Este método crea el panel para mostrar los atributos de un punto seleccionado.
	 */
	private void crearPanelPunto() {
		
		panelPunto = new JPanel();
		panelPunto.setBorder(BorderFactory.createTitledBorder("Punto"));
        panelPunto.setName("Panel de punto");
        panelPunto.setPreferredSize(new Dimension(320, 120));
		
		// Layout
        BorderLayout panelPuntoLayout = new BorderLayout();
		
		// Campo nombre
		labelNombre = new JLabel();
		labelNombre.setText("Nombre: ");
		labelNombre.setName("label nombre");
		fieldNombre = new JTextField();
		fieldNombre.setText("");
		fieldNombre.setName("field nombre");
		fieldNombre.setBackground(Color.WHITE);
        fieldNombre.setPreferredSize(new Dimension(150, 20));
		fieldNombre.setEditable(false);
		
		// Campo costo
		labelCosto = new JLabel();
        labelCosto.setText("    Costo: ");
        labelCosto.setName("label costo");
        fieldCosto = new JTextField();
        fieldCosto.setText("");
        fieldCosto.setName("field costo");
        fieldCosto.setBackground(Color.WHITE);
        fieldCosto.setPreferredSize(new Dimension(150, 20));
        fieldCosto.setEditable(false);

        // Boton editar punto
        botonEditarPunto = new JButton("Editar");
        botonEditarPunto.setName("editarPunto");
        botonEditarPunto.setEnabled(false);
        botonEditarPunto.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				editarPuntoSeleccionado();
			}
		});
        botonEditarPunto.addKeyListener(new KeyListener());
        
        panelPunto.setLayout(panelPuntoLayout);
    
        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setPreferredSize(new Dimension(300, 90));
        
        // Panel que contiene el campo tráfico
        JPanel panelNombre = new JPanel();
        panelNombre.setPreferredSize(new Dimension(300, 25));
        panelNombre.add(labelNombre);
        panelNombre.add(fieldNombre);
        panelCentral.add(panelNombre, BorderLayout.NORTH);
        
        // Panel que contiene el campo distancia
        JPanel panelCosto = new JPanel();
        panelCosto.setPreferredSize(new Dimension(300, 25));
        panelCosto.add(labelCosto);
        panelCosto.add(fieldCosto);
        panelCentral.add(panelCosto, BorderLayout.CENTER);
        
        panelPunto.add(panelCentral, BorderLayout.CENTER);
        
        // Panel que contiene el botón Editar
        JPanel panelInf = new JPanel();
        panelInf.setLayout(new BorderLayout());
        panelInf.setPreferredSize(new Dimension(300, 34));
        JPanel panelAux = new JPanel();
        panelAux.setPreferredSize(new Dimension(180, 28));
        panelInf.add(panelAux, BorderLayout.WEST);
        panelAux = new JPanel();
        panelAux.setPreferredSize(new Dimension(30, 28));
        panelInf.add(panelAux, BorderLayout.EAST);
        panelAux = new JPanel();
        panelAux.setPreferredSize(new Dimension(300, 5));
        panelInf.add(panelAux, BorderLayout.SOUTH);
        panelInf.add(botonEditarPunto, BorderLayout.CENTER);
        panelPunto.add(panelInf, BorderLayout.SOUTH);
        
	} // fin del método crearPanelPunto
	
	/**
	 * Este método crea el panel para mostrar los resultados de las consultas.
	 */
	private void crearPanelResultado() {
		
		panelResultado = new JPanel();
		panelResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));
		panelResultado.setName("Panel de resultado");
		panelResultado.setBackground(Color.WHITE);
		panelResultado.setPreferredSize(new Dimension(320, 150));
		
		areaResultado = new JTextArea();
		areaResultado.setPreferredSize(new Dimension(310, 120));
		areaResultado.setName("area de texto resultado");
		areaResultado.setEditable(false);
		areaResultado.setBackground(Color.WHITE);
		panelResultado.add(areaResultado);
	} // fin del método crearPanelResultado
	
	/**
	 * Este método crea un elemento JDialog con los campos necesarios
	 * a llenar para cargar un nuevo punto.
	 */
	public void mostrarIngresarPunto(double x, double y) {
		
		VentanaNuevoPunto ventanaNuevoPunto = new VentanaNuevoPunto(x, y, this);
		
		// JDialog
		ventanaNuevoPunto = new VentanaNuevoPunto(x, y, this);
        ventanaNuevoPunto.setTitle("Ingresar Punto");
        ventanaNuevoPunto.setResizable(false);
        ventanaNuevoPunto.setSize(260, 220);
        ventanaNuevoPunto.setLocationRelativeTo(null);
        ventanaNuevoPunto.setVisible(true);
	} // fin del método mostrarIngresarPunto
	
	/**
	 * Este método crea un elemento JDialog con los campos necesarios
	 * a llenar para cargar una nueva avenida.
	 */
	private void mostrarIngresarAvenida() {
		
		VentanaNuevaAvenida ventanaNuevaAvenida = new VentanaNuevaAvenida(this);
		
		// JDialog
		ventanaNuevaAvenida = new VentanaNuevaAvenida(this);
		ventanaNuevaAvenida.setTitle("Ingresar Avenida");
		ventanaNuevaAvenida.setSize(280, 300);
		ventanaNuevaAvenida.setLocationRelativeTo(null);
        ventanaNuevaAvenida.setVisible(true);
        
	} // fin del método mostrarIngresarAvenida
	
	/**
	 * Este método crea un elemento JDialog con información
	 * sobre la aplicación.
	 */
	private void mostrarAcercaDe() {
		
		VentanaAcercaDe ventanaAcercaDe;
		
		// JDialog
		ventanaAcercaDe = new VentanaAcercaDe(this);
		ventanaAcercaDe.setTitle("Acerca de");
		ventanaAcercaDe.setSize(400, 300);
		ventanaAcercaDe.setLocationRelativeTo(null);
		ventanaAcercaDe.setVisible(true);
		
	} // fin del método mostrarAcercaDe
	
	
	/**
	 * Este método crea un elemento JDialog con
	 * información de ayuda.
	 */
	private void mostrarManualAyuda() {
		
		VentanaAyuda ventanaAyuda;
		
		// JDialog
		ventanaAyuda = new VentanaAyuda(this);
		ventanaAyuda.setTitle("Manual de ayuda");
		ventanaAyuda.setSize(680, 500);
		ventanaAyuda.setLocationRelativeTo(null);
		ventanaAyuda.setVisible(true);

	} // fin del método mostrarManualAyuda
	
	/**
	 * @param x
	 * @param y
	 * 
	 * Agrega un nuevo punto en la lista de nodos de la red
	 * y solicita la actualización del panel de dibujo para que
	 * incorpore el nuevo punto.
	 */
	public void agregarPuntoPosicion(double x, double y, String nombre, String costo) {
		
		PuntoInterno nuevo = new PuntoInterno(x, y, nombre, Double.parseDouble(costo));
		
		red.agregarPuntoInterno(nuevo);
		
		// solicita al panel de dibujo que se actualice
		panelDibujo.repaint();
		
	} // fin del método agregarPuntoPosición
	

	/**
	 * @param origen
	 * @param destino
	 * @param trafico
	 * @param dist
	 * 
	 * Agrega una nueva avenida en la lista de avenidas de la red
	 * y solicita la actualización del panel de dibujo para que
	 * incorpore el cambio.
	 */
	public void agregarAvenidaEntrePuntos(String origen, String destino, long trafico, double dist) {
		
		Punto o = red.getPuntoNombre(origen);
		Punto d = red.getPuntoNombre(destino);
		
		Avenida nueva = new Avenida(o, d, trafico, dist);
		
		red.agregarAvenida(nueva);
		
		// solicita al panel de dibujo que se actualice
		panelDibujo.repaint();
	} // fin del método agregarAvenidaEntrePuntos
	
	
	/**
	 * @param x
	 * @param y
	 * 
	 * Elimina el nodo que contiene las coordenadas (x, y).
	 * Actualiza el panel de dibujo y el panel de punto.
	 */
	public void eliminarPunto(double x, double y) {
		
		Punto p = red.getPuntoCoordenadas(x, y);
		
		if(!p.equals(red.getAcceso()) && !p.equals(red.getSalida())) {
			
			if(red.existeAvenidaContiene(p)) {
				
				int n = JOptionPane.showConfirmDialog(
					    this,
					    "Una o mas avenidas se eliminarán. Desea eliminar el punto de todos modos?",
					    "An Inane Question",
					    JOptionPane.YES_NO_OPTION);
					
					if(n==0) {
						
						red.eliminarPunto(p);
						panelDibujo.repaint();
						
						indicePunto = -1;
						fieldCosto.setText("");
						fieldCosto.setEditable(false);
						botonEditarPunto.setEnabled(false);
						
						// Puede que se elimine la avenida cuyos datos se muestran
						indiceAvenida = -1;
						fieldDistancia.setText("");
						fieldDistancia.setEditable(false);
						fieldTrafico.setText("");
						fieldTrafico.setEditable(false);
						botonEditarAv.setEnabled(false);
					}
			}
			else {
				
				red.eliminarPunto(p);
				panelDibujo.repaint();
			}
			
		}
		else {
			mostrarMensajeInfo("Los puntos de acceso y salida no puden ser eliminados.");
		}
	} // fin del método eliminarPunto
	
	/**
	 * @param x
	 * @param y
	 * 
	 * Elimina la avenida que contiene el punto de coordenadas (x,y).
	 * Actualiza el panel de dibujo y el panel de avenida con los datos
	 * en blanco.
	 */
	public void eliminarAvenida(double x, double y) {
		
		Avenida a = red.getAvenidaCoordenadas(x, y);
		
		red.eliminarAvenida(a);
		panelDibujo.repaint();
		
		indiceAvenida = -1;
		fieldDistancia.setText("");
		fieldDistancia.setEditable(false);
		fieldTrafico.setText("");
		fieldTrafico.setEditable(false);
		botonEditarAv.setEnabled(false);
		
	} // fin del método eliminarAvenida
	
	/**
	 * Se activa al hacerse click o presionar Enter con el
	 * foco en el botón editar del panel visor de avenida. 
	 * Almacena los valores registrados, realizando previamente una verificación.
	 */
	public void editarAvenidaSeleccionada() {
			
		Avenida a = red.getAvenidaIndice(indiceAvenida);
		
		// Verifica los valores de distancia y tráfico
		try {
			
			double d = Double.parseDouble(fieldDistancia.getText());
			
			if(d>0) {
				
				try {
					
					long t = Long.parseLong(fieldTrafico.getText());
					
					if(t>0) {
						
						a.setDistancia(d);
						a.setTraficoMax(t);
					}
				}
				catch(Exception e) {
					
					mostrarMensajeInfo("El valor ingresado como tráfico no es válido.");
				}
			}
			else {
				mostrarMensajeInfo("La distancia debe ser positiva!");
			}
		}
		catch(Exception e) {
			
			mostrarMensajeInfo("El valor ingresado como distancia no es válido.");
		}
		
		int opcion = boxEstado.getSelectedIndex();
		
		// Verifica el valor del comboBox
		if(opcion == 0) {
				
			// verifica si el estado de la avenida debe cambiar
			if(!a.estaHabilitada()) {
				// la avenida estaba deshabilitada. Debe habilitarse
				red.getAvenidaIndice(indiceAvenida).setEstado(true);
				panelDibujo.repaint();
			}
		}
		else {
			// verifica si el estado de la avenida debe cambiar
			if(a.estaHabilitada()) {
				// la avenida estaba habilitada. Debe deshabilitarse
				red.getAvenidaIndice(indiceAvenida).setEstado(false);
				panelDibujo.repaint();
			}
		}
	} // fin del método editarAvenidaSeleccionada
	
	/**
	 * Se activa al hacerse click o presionar Enter con el foco sobre el
	 * botón editar del panel de punto. Verifica los valores ingresados
	 * y, en caso de que sean válidos, actualiza el atributo.
	 */
	public void editarPuntoSeleccionado() {
		
		Punto p = red.getPuntoIndice(indicePunto);
		
		
		// Verifica los valores ingresados
		try {
				
			double c = Double.parseDouble(fieldCosto.getText());
				
			if(c>=0) {
					
				((PuntoInterno)p).setCosto(c);
			}
			else {
				
				mostrarMensajeInfo("El costo no puede ser negativo!");
			}
		}
		catch(Exception e){
			
			mostrarMensajeInfo("El valor ingresado para el costo no es válido.");
		}	
	} // fin del método editarPuntoSeleccionado
	
	/**
	 * @param x
	 * @param y
	 * 
	 * Determina si existe un punto o una avenida con las coordenadas
	 * que se pasan como parámetro y la muestra en pantalla. Si es el primer
	 * punto o la primer avenida seleccionada desde que se cargó la red,
	 * se habilitan los campos de edición.
	 */
	public void mostrarGraficoCoordenadas(double x, double y) {
		
		Punto p = red.getPuntoCoordenadas(x, y);
		
		if(p != null) {
			
			if(!p.getNombre().equals("Acceso") && !p.getNombre().equals("Salida")) {
				
				if(indicePunto == -1) {	// es el primer nodo seleccionado
					
					// habilita los campos de edición
					fieldCosto.setEditable(true);
					botonEditarPunto.setEnabled(true);
				}
				
				indicePunto = red.getIndicePunto(p);
			}
			else {
				
				if(indicePunto != -1) {	// si se seleccionó un nodo Acceso o red
										// y antes se seleccionó un nodo interno
					
					// habilita los campos de edición
					fieldCosto.setEditable(false);
					botonEditarPunto.setEnabled(false);
					
					indicePunto = -1;
				}
				
			}
			
			
			// Mostrar punto en panelPunto
			fieldNombre.setText(p.getNombre());
			
			if(!p.getNombre().equals("Acceso") && !p.getNombre().equals("Salida")) {
				
				fieldCosto.setText(Double.toString(((PuntoInterno)p).getCosto()));
			}
			else {
				
				fieldCosto.setText("");
			}
		}
		else {
			
			Avenida a = red.getAvenidaCoordenadas(x, y);
			
			if(a != null) {
				
				if(indiceAvenida == -1) {	// es el primer nodo seleccionado
					
					// habilita los campos de edición
					fieldDistancia.setEditable(true);
					fieldTrafico.setEditable(true);
					botonEditarAv.setEnabled(true);
				}
				
				// Almacena el indice la avenida seleccionada
				indiceAvenida = red.getIndiceAvenida(a);
				
				fieldTrafico.setText("" + a.getTraficoMax());
				fieldDistancia.setText("" + a.getDistancia());
				
				boxEstado.setEnabled(true);
				
				if(a.estaHabilitada()) {
					
					boxEstado.setSelectedIndex(0);
				}
				else {
					
					boxEstado.setSelectedIndex(1);
				}
			}
		}
			
			// Mostrar avenida en panelAvenida
		//}
	} // fin del método mostrarGraficoCoordenadas
	
	/**
	 * @return PuntoInterno[]
	 * 
	 * Devuelve un arreglo con todos los puntos internos de la red de transporte.
	 */
	public PuntoInterno[] getPuntosInternos() {
		
		return red.getPuntosInternos();
	} // fin del método getPuntosInternos
	
	/**
	 * @return Avenida[]
	 * 
	 * Devuelve un arreglo con todas las avenidas de la red de transporte.
	 */
	public Avenida[] getAvenidas() {
		
		return red.getAvenidas();
	} // fin del método getAvenidas
	
	/**
	 * @param nombre
	 * @return boolean
	 * 
	 * Invoca al metodo existePuntoNombre del objeto de clase Red.
	 */
	public boolean existePuntoNombre(String nombre) {
		
		return red.existePuntoNombre(nombre);
	} // fin del método existePuntoNombre
	
	public boolean existeAvenidaPuntos(String origen, String destino) {
		
		return red.existeAvenidaPuntos(origen, destino);
	} // fin del método existeAvenidaPuntos
	
	/**
	 * @param x
	 * @param y
	 * @return boolean
	 * 
	 * Determina si existe o no un punto registrado con las coordenadas
	 * que se especifican como parámetro.
	 */
	public boolean existePuntoCoordenadas(double x, double y) {
		
		Punto p = red.getPuntoCoordenadas(x, y);
		
		return !(p==null);
	} // fin del método existePuntoCoordenadas
	
	public boolean existeAvenidaCoordenadas(double x, double y) {
		
		Avenida a = red.getAvenidaCoordenadas(x, y);
		
		return !(a==null);
	} // fin del método existeAvenidaCoordenadas
	
	/**
	 * @param origen
	 * @param destino
	 * @return boolean
	 * 
	 * Devuelve true si la avenida que pasa por los puntos origen y destino
	 * atraviesa algún punto distinto de estos. Devuelve false en caso contrario.
	 */
	public boolean avenidaAtraviesaPuntos(String origen, String destino) {
		
		return red.avenidaAtraviesaPuntos(origen, destino);
	} // fin del método avenidaAtraviesaPuntos
	
	/**
	 * @param x
	 * @param y
	 * @return boolean
	 * 
	 * Devuelve true si existe una avenida que pase por el punto de
	 * coordenadas (x,y).
	 */
	public boolean avenidaPasaPorPunto(double x, double y) {
		
		return red.avenidaPasaPorPunto(x, y);
	} // fin del método avenidaPasaPorPunto
	
	/**
	 * Muestra un diálogo para abrir una configuración.
	 */
	private void mostrarRecuperarConf() {
		
		VentanaAbrir v = new VentanaAbrir(this);
		
		v.mostrar();
	} // fin del método mostrarRecuperarConf 
	
	/**
	 * @param nombreDir
	 * @param nombreArchivo
	 * 
	 * Este método invoca al DAO para cargar el objeto contenido
	 * en el archivo seleccionado por el usuario.
	 */
	public void cargarObjetoArchivo(String nombreDir, String nombreArchivo) {
		
		// Establece el directorio padre del archivo
		DataAccessObject.setDirectorio(nombreDir);
		
		try {
			// Recupera la instancia de red del archivo
			red = (Red)DataAccessObject.cargar(nombreArchivo);
		}
		catch(IOException ex){
			
			mostrarMensajeError("El archivo seleccionado no puede ser abierto.");
		}
		catch(ClassNotFoundException ex){

			mostrarMensajeError("El archivo seleccionado no puede ser abierto.");
		}
		catch(Exception e) {
			
			mostrarMensajeError("Ha ocurrido un error en la aplicación, por favor inténtelo nuevamente.");
		}
		
		// Actualiza el panel de dibujo
		panelDibujo.repaint();
	} // fin del método cargarObjetoArchivo
	
	/**
	 * Muestra un diálogo para guardar una configuración.
	 */
	public void mostrarGuardarConf() {
		
		VentanaGuardar v = new VentanaGuardar(this);
		
		v.mostrar();
	} // fin del método mostrarGuardarConf
	
	/**
	 * @param nombreDir
	 * @param nombreArchivo
	 * 
	 * Este método invoca al DAO para guardar los objetos serializables
	 * instanciados en la aplicación.
	 */
	public void guardarObjetoArchivo(String nombreDir, String nombreArchivo) {
		
		// Establece el directorio padre del archivo
		DataAccessObject.setDirectorio(nombreDir);
		
		try {
			// Guarda la configuracion actual de la red en un archivo.
			DataAccessObject.persistir(red, nombreArchivo);
		}
		catch(IOException ex){

			mostrarMensajeError("La configuración de la red no pudo ser guardada..");
		}
		catch(Exception e) {
			
			mostrarMensajeError("Ha ocurrido un error en la aplicación, por favor inténtelo nuevamente.");
		}
	} // fin del método guardarObjetoArchivo
	
	
	/**
	 * @param xOrigen
	 * @param yOrigen
	 * @param xDestino
	 * @param yDestino
	 * 
	 * Obtiene el camino más corto entre el par de puntos dados.
	 */
	public void obtenerCaminoMasCorto(double xOrigen, double yOrigen, double xDestino, double yDestino) {
		
		Punto origen = red.getPuntoCoordenadas(xOrigen, yOrigen);
		Punto destino = red.getPuntoCoordenadas(xDestino, yDestino);
		
		Punto[] camino = red.obtenerCaminoMasCorto(origen, destino);
		
		camino = invertirArreglo(camino);
		
		if(camino != null ) {	// existe al menos un camino
			
			// Muestra el resultado en el panel
			areaResultado.setText("El camino más corto es la secuencia:\n");
			
			int i = 0;
			
			while(i<camino.length) {
				
				// Escribe el nodo
				areaResultado.append("- " + camino[i].getNombre() + "\n");
				i++;
			}
		}
		else {
			
			// Muestra el resultado en el panel
			areaResultado.setText("Los nodos no son alcanzables.");
		}
		
	} // fin del método obtenerCaminoMasCorto
	
	/**
	 * @param xOrigen
	 * @param yOrigen
	 * @param xDestino
	 * @param yDestino
	 * 
	 * Obtiene el camino con menos peajes del nodo origen al destino.
	 */
	public void obtenerCaminoMenosPeajes(double xOrigen, double yOrigen, double xDestino, double yDestino) {
		
		Punto origen = red.getPuntoCoordenadas(xOrigen, yOrigen);
		Punto destino = red.getPuntoCoordenadas(xDestino, yDestino);
		
		Punto[] camino = red.obtenerCaminoMenosPeajes(origen, destino);
		
		camino = invertirArreglo(camino);
		
		if(camino != null ) {	// existe al menos un camino
			
			// Muestra el resultado en el panel
			areaResultado.setText("El camino con menos peajes es la secuencia:\n");
			
			int i = 0;
			
			while(i<camino.length) {
				
				// Escribe el nodo
				areaResultado.append("- " + camino[i].getNombre() + "\n");
				i++;
			}
		}
		else {
			
			// Muestra el resultado en el panel
			areaResultado.setText("Los nodos no son alcanzables.");
		}
		
	} // fin del método obtenerCaminoMenosPeajes
	
	/**
	 * @param x
	 * @param y
	 * 
	 * Obtiene los puntos alcanzables desde un punto dado.
	 */
	public void obtenerPuntosAlcanzables(double x, double y) {
		
		Punto p = red.getPuntoCoordenadas(x, y);
		
		Punto[] puntos = red.obtenerPuntosAlcanzables(p);
		
		if(puntos.length != 0) {	// existe al menos un nodo
			
			// Muestra el resultado en el panel
			areaResultado.setText("Los puntos alcanzables desde el nodo " + p.getNombre()+ " son:\n");
			
			int i = 0;
			
			while(i<puntos.length) {
				
				// Escribe el nodo
				areaResultado.append("- " + puntos[i].getNombre() + "\n");
				i++;
			}
		}
		else {
			
			// Muestra el resultado en el panel
			areaResultado.setText("El nodo está aislado.");
		}
		
	} // fin del método obtenerPuntosAlcanzables
	
	/**
	 * @param x
	 * @param y
	 * 
	 * Obtiene los puntos alcanzables desde el punto dado,
	 * tras pasar por menos de n peajes.
	 */
	public void obtenerPuntosMenosNPeajes(double x, double y) {
		
		String n = JOptionPane.showInputDialog("Por favor, ingrese el número de peajes:");
		
		int numPeajes=0;
		
		try {
			
			numPeajes = Integer.parseInt(n);
		}
		catch(Exception e) {
			
			mostrarMensajeInfo("El número de peajes ingresados no es válido. La operación fue cancelada.");
		}
		
		if(numPeajes>0) {
				
			Punto p = red.getPuntoCoordenadas(x, y);
				
			Punto[] puntos = red.obtenerPuntosMenosNPeajes(p, numPeajes);
				
			Avenida[] avenidas = red.obtenerSecuenciaAvenidas(puntos);
			
			JDialog ver = new JDialog();
			ver.setSize(420, 460);
			ver.setTitle("Resultado: Nodos alcanzables con menos de " + n + " peajes");
			ver.setResizable(false);
			ver.setLocationRelativeTo(null);
				
			VentanaDibujo resultado = new VentanaDibujo(avenidas, puntos);
			resultado.setPreferredSize(new Dimension(420, 460));
			resultado.repaint();
			resultado.setVisible(true);

			ver.add(resultado);
			ver.setVisible(true);
		}
		
		
	}	// fin del método obtenerPuntosMenosNPeajes
	
	/**
	 * Obtiene el máximo tráfico posible en la red.
	 */
	public void obtenerMaximoTraficoEnRed() {
		
		long maxTraf = red.obtenerMaximoTraficoEnRed();
		
		// Muestra el resultado en el panel
		areaResultado.setText("El máximo tráfico en la red es de: " + maxTraf);
		
	} // fin del método obtenerMaximoTraficoEnRed
	
	/**
	 * @param mensaje
	 * 
	 * Muestra un mensaje informativo con la cadena que 
	 * recibe como parámetro mediante un dialog predefinido 
	 * de JOptionPane.
	 */
	public void mostrarMensajeInfo(String mensaje) {
		
		JOptionPane.showMessageDialog(this, mensaje);
	} // fin del método mostrarMensajeInfo
	
	/**
	 * @param mensaje
	 * 
	 * Muestra un mensaje de alerta con la cadena que 
	 * recibe como parámetro mediante un dialog predefinido 
	 * de JOptionPane.
	 */
	public void mostrarMensajeAlerta(String mensaje) {
		
		JOptionPane.showMessageDialog(this, mensaje, "Inane warning", JOptionPane.WARNING_MESSAGE);
	} // fin del método mostrarMensajeAlerta
	
	/**
	 * @param mensaje
	 * 
	 * Muestra un mensaje de error con la cadena que 
	 * recibe como parámetro mediante un dialog predefinido 
	 * de JOptionPane.
	 */
	public void mostrarMensajeError(String mensaje) {
		
		JOptionPane.showMessageDialog(this, mensaje, "Inane error", JOptionPane.ERROR_MESSAGE);
	} // fin del método mostrarMensajeError
	
	/**
	 * Muestra un mensaje preguntando si el usuario desea guardar la red creada
	 * o desea continuar sin guardarla.
	 */
	public void mostrarPreguntaGuardar() {
		
		int n = JOptionPane.showConfirmDialog(
		    this,
		    "La red creada se perderá, deseas guardarla?",
		    "An Inane Question",
		    JOptionPane.YES_NO_OPTION);
		
		if(n==0) {
			
			mostrarGuardarConf();
		}
		else {
			
			// Se instancia la clase Red
			red = new Red(180, 20, 180, 400);
			
			// Se inicializan los indices de avenida y punto seleccionados
			indiceAvenida = -1;
			indicePunto = -1;
			
			fieldNombre.setText("");
			fieldCosto.setText("");
			fieldCosto.setEditable(false);
			
			fieldTrafico.setText("");
			fieldTrafico.setEditable(false);
	        fieldDistancia.setText("");
	        fieldDistancia.setEditable(false);
	        boxEstado.setSelectedIndex(0);
	        boxEstado.setEnabled(false);
		}
		
		panelDibujo.repaint();
	} // fin del método mostrarPreguntaGuardar
	
	/**
	 * @param arreglo
	 * @return Punto[]
	 * 
	 * Invierte los elementos del arreglo que recibe como parámetro.
	 */
	private Punto[] invertirArreglo(Punto[] arreglo) {
		
		Punto[] temp = new Punto[arreglo.length];
		int j=0;
		
		for(int i=arreglo.length-1; i>=0; i--) {
			
			temp[j] = arreglo[i];
			
			j++;
		}
		
		return temp;
	} // fin del método revertirArreglo
	
	/**
	 * @author Goti
	 * 
	 * Esta clase se utiliza de manera auxiliar para implementar
	 * un key listener para los diferentes botones.
	 */
	private class KeyListener extends KeyAdapter {
		
        public void keyPressed(KeyEvent e) {
            
            String key = e.getKeyText(e.getKeyCode());
            key = key.toUpperCase();
            
            // el componente que despertó el evento
            String comp = e.getComponent().getName();
            
            // maneja la pulsación de la tecla ENTER
            if (key.equals("INTRODUZCA")) {
            	
            	if(comp.equals("editarAv")) {
            		
            		editarAvenidaSeleccionada();
            	}
            	else {
            		
            		editarPuntoSeleccionado();
            	}
            }
        }
    } // fin de clase KeyListener
	
} // fin de definición de clase Aplicacion