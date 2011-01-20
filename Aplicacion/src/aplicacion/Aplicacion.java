package aplicacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

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
 * a la GUI de la aplicaci�n. Tambien cumple la funci�n de clase de control,
 * en tanto se encarga de la l�gica y coordinaci�n de los componentes de la 
 * aplicaci�n.
 */
public class Aplicacion extends JFrame{
	
	// Red de transporte
	private Red red;
	
	// indice de la avenida seleccionada
	private int indiceAvenida;
	
	// indice del punto seleccionado
	private int indicePunto;
	
	// Barra de men�
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
	private JMenuItem editarAv;
	private JMenuItem verNodo;
	private JMenuItem verAv;
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
	
	// Panele contenedores secundarios
	private JPanel panelSecundarioI;
	private JPanel panelSecundarioD;
	
	// Panel de visualizaci�n del grafo
	private JPanel panelGrafo;
	private PanelDibujo panelDibujo;
	
	// Panel de visualizaci�n de avenida seleccionada
	private JPanel panelAvenida;
	private JLabel labelTrafico;
	private JTextField fieldTrafico;
	private JLabel labelDistancia;
	private JTextField fieldDistancia;
	private JLabel labelEstado;
	private JComboBox boxEstado;
	
	// Panel de visualizaci�n de punto seleccionado
	private JPanel panelPunto;
	private JLabel labelNombre;
	private JTextField fieldNombre;
	private JLabel labelCosto;
	private JTextField fieldCosto;
	
	// Panel de visualizaci�n de resultados de consultas
	private JPanel panelResultado;
	private JTextArea areaResultado;
	
	public static void main(String[] args){
	
		Aplicacion ap = new Aplicacion();
		
		ap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ap.setSize( 800, 600 );
		ap.setLocationRelativeTo(null);
		ap.setResizable(true);
		ap.setVisible(true);
	} // fin del m�todo main
	
	/**
	 * Constructor de clase.
	 * Se emplea excepciones para informar posibles errores de inicio.
	 */
	private Aplicacion() {
		
		try {
			inicializar();
		}
		catch (Exception e) {
			mostrarMensajeError("Se ha producido un error en la aplicaci�n y deber� cerrarse");
			dispose();
		}
	} // fin del constructor de clase
	
	/**
	 * Este m�todo crea la interfaz basica de la aplicaci�n.
	 */
	private void inicializar() {
		
		// Se setea el titulo
		this.setTitle("Simulador de Tr�fico");
		
		// Layout
		layout = new BorderLayout();
		this.getContentPane().setLayout(layout);
		
		// Se crea una barra de men�
		jMenuBar = new JMenuBar();
		this.setJMenuBar(jMenuBar);
		
		// agrega el men� Archivo a la barra de men�
		agregarJMenuArchivo();
	
		// agrega el men� Consulta a la barra de men�
		agregarJMenuConsulta();
		
		// agrega el men� Ayuda a la barra de men�
		agregarJMenuAyuda();
		
		// Se crea el panel principal de la aplicacion
		agregarPanelPrincipal();
		
		// Se instancia la clase Red
		red = new Red(180, 1, 180, 340);
		
		// Se inicializan los indices de avenida y punto seleccionados
		indiceAvenida = -1;
		indicePunto = -1;
		
	} // fin del m�todo inicializar
	
	
	/**
	 * Este m�todo crea un men� Archivo y le agrega los items:
	 * - Nueva Red
	 * - Recuperar Configuraci�n
	 * - Guardar Configuraci�n
	 * - Limpiar Configuraci�n
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
		recuperarConf.setText("Recuperar Configuraci�n   Alt+A+R");
		recuperarConf.setMnemonic('R');
		recuperarConf.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarRecuperarConf();
			}
		});
	
		// Opcion Guardar Configuracion
		guardarConf = new JMenuItem();
		guardarConf.setText("Guardar Configuraci�n   Alt+A+G");
		guardarConf.setMnemonic('G');
		guardarConf.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarGuardarConf();
			}
		});
		
		// Opcion Limpiar Configuracion
		limpiarConf = new JMenuItem();
		limpiarConf.setText("Limpiar Configuraci�n   Alt+A+L");
		limpiarConf.setMnemonic('L');
		limpiarConf.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

			}
		});
	
		// Opcion Salir
		salir = new JMenuItem();
		salir.setText("Salir   Alt+A+S");
		salir.setMnemonic('S');
		salir.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

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
	} // fin del m�todo agregarJMenuArchivo
	
	
	/**
	 * Este m�todo agrega el men� Consulta y le agrega los items:
	 * - Ingresar Punto
	 * - Ingresar Avenida
	 * - Editar Avenida
	 * - Ver Nodo
	 * - Ver Avenida
	 * - Camino m�s corto
	 * - Camino con menos peajes
	 * - Puntos alcanzables
	 * - M�ximo tr�fico en red
	 * - Camino con peajes m�ximos
	 */
	private void agregarJMenuConsulta() {
		
		// Elemento "Consulta"
		consultas = new JMenu();
		consultas.setText("Consulta");
		consultas.setMnemonic('C');
		
		// Opcion Ingresar Punto
		ingresarPunto = new JMenuItem();
		ingresarPunto.setText("Ingresar Punto   Alt+A+P");
		ingresarPunto.setMnemonic('P');
		ingresarPunto.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarMensajeInfo("Haz click en un punto del panel para ubicar el nuevo Nodo.");
				panelDibujo.setMouseListener(2);
			}
		});
		
		// Opcion Ingresar Avenida
		ingresarAv = new JMenuItem();
		ingresarAv.setText("Ingresar Avenida   Alt+A+A");
		ingresarAv.setMnemonic('A');
		ingresarAv.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				mostrarIngresarAvenida();
			}
		});
		
		// Opcion Editar Avenida
		editarAv = new JMenuItem();
		editarAv.setText("Editar Avenida   Alt+A+E");
		editarAv.setMnemonic('E');
		editarAv.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

			}
		});
		
		// Opcion Ver Nodo
		verNodo = new JMenuItem();
		verNodo.setText("Ver Nodo   Alt+A+V");
		verNodo.setMnemonic('V');
		verNodo.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

			}
		});
		
		// Opcion Ver Avenida
		verAv = new JMenuItem();
		verAv.setText("Ver Avenida   Alt+A+B");
		verAv.setMnemonic('B');
		verAv.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

			}
		});
		
		// Opcion Camino m�s Corto
		opcA = new JMenuItem();
		opcA.setText("Camino m�s Corto   Alt+A+G");
		opcA.setMnemonic('G');
		opcA.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

			}
		});
		
		// Opcion Camino con menos peajes
		opcB = new JMenuItem();
		opcB.setText("Camino con menos peajes   Alt+A+H");
		opcB.setMnemonic('H');
		opcB.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

			}
		});
		
		// Opcion Puntos alcanzables
		opcC = new JMenuItem();
		opcC.setText("Puntos alcanzables   Alt+A+J");
		opcC.setMnemonic('J');
		opcC.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

			}
		});
		
		// Opcion M�ximo tr�fico en red
		opcD = new JMenuItem();
		opcD.setText("M�ximo tr�fico en red   Alt+A+K");
		opcD.setMnemonic('K');
		opcD.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

			}
		});
		
		// Opcion Camino con peajes m�ximos
		opcE = new JMenuItem();
		opcE.setText("Camino con peajes m�ximos   Alt+A+L");
		opcE.setMnemonic('L');
		opcE.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

			}
		});
		
		consultas.add(ingresarPunto);
		consultas.add(ingresarAv);
		consultas.addSeparator();
		consultas.add(editarAv);
		consultas.add(verNodo);
		consultas.add(verAv);
		consultas.addSeparator();
		consultas.add(opcA);
		consultas.add(opcB);
		consultas.add(opcC);
		consultas.add(opcD);
		consultas.add(opcE);
		
		jMenuBar.add(consultas);
	} // fin del m�todo agregarJMenuConsulta
	
	
	/**
	 * Este m�todo crea el men� Ayuda y le agrega los items:
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

			}
		});
		
		// Opcion Ayuda del Programa
		ayudaProg = new JMenuItem();
		ayudaProg.setText("Ayuda del Programa   Alt+A+H");
		ayudaProg.setMnemonic('H');
		ayudaProg.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

			}
		});
		
		ayuda.add(acercaDe);
		ayuda.add(ayudaProg);
		
		jMenuBar.add(ayuda);
	} // fin del m�todo agregarJMenuAyuda
	
	/**
	 * Este m�todo crea el panel principal de la aplicaci�n.
	 * Este panel contiene los elementos:
	 * - panel secundario izquierdo
	 * - panel secundario derecho
	 */
	private void agregarPanelPrincipal() {
		
		panelContenedor = new JPanel();
		
		// Layout
		GridLayout panelContLayout = new GridLayout(1, 2);
		panelContenedor.setLayout(panelContLayout);
		
		agregarPanelSecundarioI();
		
		agregarPanelSecundarioD();
		
		this.getContentPane().add(panelContenedor);
	} // fin del m�todo agregarPanelPrincipal
	
	/**
	 * Este m�todo crea el panel secundario izquierdo y lo agrega al contenedor principal.
	 * * Este panel contiene los elementos:
	 * - panelAvenida
	 * - panelPunto
	 * - panelResultado
	 */
	private void agregarPanelSecundarioI() {
		
		panelSecundarioI = new JPanel();
		
		// Se agrega el panel de avenida
		crearPanelAvenida();
		
		// Se agrega el panel de punto
		crearPanelPunto();
		
		// Se agrega el panel de resultado
		crearPanelResultado();
		
		panelSecundarioI.add(panelAvenida);
		panelSecundarioI.add(panelPunto);
		panelSecundarioI.add(panelResultado);
		
		panelContenedor.add(panelSecundarioI);
	} // fin del m�todo agregarPanelSecundarioI
	
	/**
	 * Este m�todo crea el panel secundario derecho y lo agrega al contenedor principal.
	 * Este panel contiene el elemento:
	 * - panelGrafo
	 */
	private void agregarPanelSecundarioD() {
		
		panelSecundarioD = new JPanel();
		
		// Se agrega el panel de grafo
		crearPanelGrafo();
		
		panelSecundarioD.add(panelGrafo);
		
		panelContenedor.add(panelSecundarioD);
	} // fin del m�todo agregarPanelSecundarioD
	
	/**
	 * Este m�todo crea el panel secundario y se inserta en el
	 * el panel que se utiliza para visualizar el grafo de la Red.
	 */
	private void crearPanelGrafo() {
		
		panelGrafo = new JPanel();
		panelGrafo.setBackground(Color.WHITE);
		panelGrafo.setBorder(BorderFactory.createTitledBorder("Red de transporte"));
		panelGrafo.setName("Panel de grafo");
		panelGrafo.setPreferredSize(new Dimension(365, 405));
		
		panelDibujo = new PanelDibujo(this);
		panelDibujo.setPreferredSize(new Dimension(360, 400));
		
		panelGrafo.add(panelDibujo);
	} // fin del m�todo crearPanelGrafo
	
	/**
	 * Este m�todo crea el panel para mostrar los atributos de una avenida seleccionada.
	 */
	private void crearPanelAvenida() {
		
		panelAvenida = new JPanel();
		panelAvenida.setBorder(BorderFactory.createTitledBorder("Avenida"));
        panelAvenida.setName("Panel de avenida");
		
		// Layout
		GroupLayout panelAvenidaLayout = new GroupLayout(panelAvenida);
        
        // Campo tr�fico m�ximo
		labelTrafico = new JLabel();
        labelTrafico.setText("Tr�fico M�ximo: ");
        labelTrafico.setName("label trafico");
        fieldTrafico = new JTextField();
        fieldTrafico.setText("");
        fieldTrafico.setName("field trafico");
        fieldTrafico.setBackground(Color.WHITE);
        fieldTrafico.setEditable(false);

        // Campo distancia
        labelDistancia = new JLabel();
        labelDistancia.setText("Distancia: ");
        labelDistancia.setName("label distancia");
        fieldDistancia = new JTextField();
        fieldDistancia.setText("");
        fieldDistancia.setName("field distancia");
        fieldDistancia.setBackground(Color.WHITE);
        fieldDistancia.setEditable(false);
        
        // Campo estado de avenida
        labelEstado = new JLabel();
        labelEstado.setText("");
        labelEstado.setName("label estado");
        boxEstado = new JComboBox();
        boxEstado.setModel(new DefaultComboBoxModel(new String[] { "Habilitada", "Deshabilitada" }));
        boxEstado.setName("box estado");
        boxEstado.setBackground(Color.WHITE);
        boxEstado.setEnabled(false);
        boxEstado.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {
				
				int opcion = boxEstado.getSelectedIndex();
				Avenida a = red.getAvenidaIndice(indiceAvenida);
				
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
			}
		});
  
        panelAvenida.setLayout(panelAvenidaLayout);
        
        panelAvenidaLayout.setHorizontalGroup(
        	panelAvenidaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelAvenidaLayout.createSequentialGroup()
                .addGroup(panelAvenidaLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(labelEstado)
                    .addComponent(labelTrafico)
                    .addComponent(labelDistancia))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAvenidaLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(boxEstado, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fieldDistancia)
                    .addComponent(fieldTrafico, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        panelAvenidaLayout.setVerticalGroup(
        		panelAvenidaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelAvenidaLayout.createSequentialGroup()
                .addGroup(panelAvenidaLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTrafico)
                    .addComponent(fieldTrafico, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAvenidaLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldDistancia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDistancia))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAvenidaLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(boxEstado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEstado))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
	} // fin del m�todo crearPanelAvenida
	
	/**
	 * Este m�todo crea el panel para mostrar los atributos de un punto seleccionado.
	 */
	private void crearPanelPunto() {
		
		panelPunto = new JPanel();
		panelPunto.setBorder(BorderFactory.createTitledBorder("Punto"));
        panelPunto.setName("Panel de punto");
		
		// Layout
		GroupLayout panelPuntoLayout = new GroupLayout(panelPunto);
		
		// Campo nombre
		labelNombre = new JLabel();
		labelNombre.setText("Nombre: ");
		labelNombre.setName("label nombre");
		fieldNombre = new JTextField();
		fieldNombre.setText("");
		fieldNombre.setName("field nombre");
		fieldNombre.setBackground(Color.WHITE);
		fieldNombre.setEditable(false);

		// Campo costo
		labelCosto = new JLabel();
        labelCosto.setText("Costo: ");
        labelCosto.setName("label costo");
        fieldCosto = new JTextField();
        fieldCosto.setText("");
        fieldCosto.setName("field costo");
        fieldCosto.setBackground(Color.WHITE);
        fieldCosto.setEditable(false);

        panelPunto.setLayout(panelPuntoLayout);
        panelPuntoLayout.setHorizontalGroup(
        	panelPuntoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelPuntoLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(panelPuntoLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(labelCosto)
                    .addComponent(labelNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPuntoLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(fieldCosto)
                    .addComponent(fieldNombre, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        panelPuntoLayout.setVerticalGroup(
        	panelPuntoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelPuntoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPuntoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNombre))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPuntoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCosto)
                    .addComponent(fieldCosto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
	} // fin del m�todo crearPanelPunto
	
	/**
	 * Este m�todo crea el panel para mostrar los resultados de las consultas.
	 */
	private void crearPanelResultado() {
		
		panelResultado = new JPanel();
		panelResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));
		panelResultado.setName("Panel de resultado");
		
		areaResultado = new JTextArea();
		areaResultado.setColumns(20);
		areaResultado.setRows(5);
		areaResultado.setName("area de texto resultado");
		areaResultado.setEditable(false);
		areaResultado.setBackground(Color.WHITE);
		panelResultado.add(areaResultado);
	} // fin del m�todo crearPanelResultado
	
	/**
	 * Este m�todo crea un elemento JDialog con los campos necesarios
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
	} // fin del m�todo mostrarIngresarPunto
	
	/**
	 * Este m�todo crea un elemento JDialog con los campos necesarios
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
	} // fin del m�todo mostrarIngresarAvenida
	
	/**
	 * @param x
	 * @param y
	 * 
	 * Agrega un nuevo punto en la lista de nodos de la red
	 * y solicita la actualizaci�n del panel de dibujo para que
	 * incorpore el nuevo punto.
	 */
	public void agregarPuntoPosicion(double x, double y, String nombre, String costo) {
		
		PuntoInterno nuevo = new PuntoInterno(x, y, nombre, Double.parseDouble(costo));
		
		red.agregarPuntoInterno(nuevo);
		
		// solicita al panel de dibujo que se actualice
		panelDibujo.repaint();
		
	} // fin del m�todo agregarPuntoPosici�n
	

	/**
	 * @param origen
	 * @param destino
	 * @param trafico
	 * @param dist
	 * 
	 * Agrega una nueva avenida en la lista de avenidas de la red
	 * y solicita la actualizaci�n del panel de dibujo para que
	 * incorpore el cambio.
	 */
	public void agregarAvenidaEntrePuntos(String origen, String destino, long trafico, double dist) {
		
		Punto o = red.getPuntoNombre(origen);
		Punto d = red.getPuntoNombre(destino);
		
		Avenida nueva = new Avenida(o, d, trafico, dist);
		
		red.agregarAvenida(nueva);
		
		// solicita al panel de dibujo que se actualice
		panelDibujo.repaint();
	} // fin del m�todo agregarAvenidaEntrePuntos
	
	/**
	 * @param x
	 * @param y
	 * 
	 * Determina si existe un punto o una avenida con las coordenadas
	 * que se pasan como par�metro y la muestra en pantalla.
	 */
	public void mostrarGraficoCoordenadas(double x, double y) {
		
		Punto p = red.getPuntoCoordenadas(x, y);
		
		if(p != null) {
			
			indicePunto = red.getIndicePunto(p);
			
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
	} // fin del m�todo mostrarGraficoCoordenadas
	
	/**
	 * @return PuntoInterno[]
	 * 
	 * Devuelve un arreglo con todos los puntos internos de la red de transporte.
	 */
	public PuntoInterno[] getPuntosInternos() {
		
		return red.getPuntosInternos();
	} // fin del m�todo getPuntosInternos
	
	/**
	 * @return Avenida[]
	 * 
	 * Devuelve un arreglo con todas las avenidas de la red de transporte.
	 */
	public Avenida[] getAvenidas() {
		
		return red.getAvenidas();
	} // fin del m�todo getAvenidas
	
	/**
	 * @param nombre
	 * @return boolean
	 * 
	 * Invoca al metodo existePuntoNombre del objeto de clase Red.
	 */
	public boolean existePuntoNombre(String nombre) {
		
		return red.existePuntoNombre(nombre);
	} // fin del m�todo existePuntoNombre
	
	public boolean existeAvenidaPuntos(String origen, String destino) {
		
		return red.existeAvenidaPuntos(origen, destino);
	} // fin del m�todo existeAvenidaPuntos
	
	/**
	 * @param x
	 * @param y
	 * @return boolean
	 * 
	 * Determina si existe o no un punto registrado con las coordenadas
	 * que se especifican como par�metro.
	 */
	public boolean existePuntoCoordenadas(double x, double y) {
		
		Punto p = red.getPuntoCoordenadas(x, y);
		
		if(p==null) {
			return false;
		}
		return true;
	} // fin del m�todo existePuntoCoordenadas
	
	/**
	 * Muestra un di�logo para abrir una configuraci�n.
	 */
	private void mostrarRecuperarConf() {
		
		VentanaAbrir v = new VentanaAbrir(this);
		
		v.mostrar();
	}
	
	/**
	 * @param nombreDir
	 * @param nombreArchivo
	 * 
	 * Este m�todo invoca al DAO para cargar el objeto contenido
	 * en el archivo seleccionado por el usuario.
	 */
	public void cargarObjetoArchivo(String nombreDir, String nombreArchivo) {
		
		// Establece el directorio padre del archivo
		DataAccessObject.setDirectorio(nombreDir);
		
		// Recupera la instancia de red del archivo
		red = (Red) DataAccessObject.cargar(nombreArchivo);
		
		// Actualiza el panel de dibujo
		panelDibujo.repaint();
	}
	
	/**
	 * Muestra un di�logo para guardar una configuraci�n.
	 */
	public void mostrarGuardarConf() {
		
		VentanaGuardar v = new VentanaGuardar(this);
		
		v.mostrar();
	} // fin del m�todo mostrarGuardarConf
	
	public void guardarObjetoArchivo(String nombreDir, String nombreArchivo) {
		
		// Establece el directorio padre del archivo
		DataAccessObject.setDirectorio(nombreDir);
		
		DataAccessObject.persistir(red, nombreArchivo);
	}
	
	/**
	 * @param mensaje
	 * 
	 * Muestra un mensaje informativo con la cadena que 
	 * recibe como par�metro mediante un dialog predefinido 
	 * de JOptionPane.
	 */
	public void mostrarMensajeInfo(String mensaje) {
		
		JOptionPane.showMessageDialog(this, mensaje);
	} // fin del m�todo mostrarMensajeInfo
	
	/**
	 * @param mensaje
	 * 
	 * Muestra un mensaje de alerta con la cadena que 
	 * recibe como par�metro mediante un dialog predefinido 
	 * de JOptionPane.
	 */
	public void mostrarMensajeAlerta(String mensaje) {
		
		JOptionPane.showMessageDialog(this, mensaje, "Inane warning", JOptionPane.WARNING_MESSAGE);
	} // fin del m�todo mostrarMensajeAlerta
	
	/**
	 * @param mensaje
	 * 
	 * Muestra un mensaje de error con la cadena que 
	 * recibe como par�metro mediante un dialog predefinido 
	 * de JOptionPane.
	 */
	public void mostrarMensajeError(String mensaje) {
		
		JOptionPane.showMessageDialog(this, mensaje, "Inane error", JOptionPane.ERROR_MESSAGE);
	} // fin del m�todo mostrarMensajeError
	
	/**
	 * Muestra un mensaje preguntando si el usuario desea guardar la red creada
	 * o desea continuar sin guardarla.
	 */
	public void mostrarPreguntaGuardar() {
		
		int n = JOptionPane.showConfirmDialog(
		    this,
		    "La red creada se perder�, deseas guardarla?",
		    "An Inane Question",
		    JOptionPane.YES_NO_OPTION);
		
		if(n==0) {
			
			mostrarGuardarConf();
		}
		else {
			
			// Se instancia la clase Red
			red = new Red(180, 1, 180, 340);
			
			// Se inicializan los indices de avenida y punto seleccionados
			indiceAvenida = -1;
			indicePunto = -1;
			
			fieldNombre.setText("");
			fieldCosto.setText("");
			
			fieldTrafico.setText("");
	        fieldDistancia.setText("");
	        boxEstado.setSelectedIndex(0);
	        boxEstado.setEnabled(false);
		}
		
		panelDibujo.repaint();
	} // fin del m�todo mostrarPreguntaGuardar
	
} // fin de definici�n de clase Aplicacion