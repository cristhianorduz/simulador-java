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
	
	// Panel de visualización de punto seleccionado
	private JPanel panelPunto;
	private JLabel labelNombre;
	private JTextField fieldNombre;
	private JLabel labelCosto;
	private JTextField fieldCosto;
	
	// Panel de visualización de resultados de consultas
	private JPanel panelResultado;
	private JTextArea areaResultado;
	
	public static void main(String[] args){
	
		Aplicacion ap = new Aplicacion();
		
		ap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ap.setSize( 800, 600 );
		ap.setLocationRelativeTo(null);
		ap.setResizable(true);
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
		red = new Red(180, 1, 180, 340);
		
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
		
		// Opcion Camino más Corto
		opcA = new JMenuItem();
		opcA.setText("Camino más Corto   Alt+A+G");
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
		
		// Opcion Máximo tráfico en red
		opcD = new JMenuItem();
		opcD.setText("Máximo tráfico en red   Alt+A+K");
		opcD.setMnemonic('K');
		opcD.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evento) {

			}
		});
		
		// Opcion Camino con peajes máximos
		opcE = new JMenuItem();
		opcE.setText("Camino con peajes máximos   Alt+A+L");
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
		GridLayout panelContLayout = new GridLayout(1, 2);
		panelContenedor.setLayout(panelContLayout);
		
		agregarPanelSecundarioI();
		
		agregarPanelSecundarioD();
		
		this.getContentPane().add(panelContenedor);
	} // fin del método agregarPanelPrincipal
	
	/**
	 * Este método crea el panel secundario izquierdo y lo agrega al contenedor principal.
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
	} // fin del método agregarPanelSecundarioI
	
	/**
	 * Este método crea el panel secundario derecho y lo agrega al contenedor principal.
	 * Este panel contiene el elemento:
	 * - panelGrafo
	 */
	private void agregarPanelSecundarioD() {
		
		panelSecundarioD = new JPanel();
		
		// Se agrega el panel de grafo
		crearPanelGrafo();
		
		panelSecundarioD.add(panelGrafo);
		
		panelContenedor.add(panelSecundarioD);
	} // fin del método agregarPanelSecundarioD
	
	/**
	 * Este método crea el panel secundario y se inserta en el
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
	} // fin del método crearPanelGrafo
	
	/**
	 * Este método crea el panel para mostrar los atributos de una avenida seleccionada.
	 */
	private void crearPanelAvenida() {
		
		panelAvenida = new JPanel();
		panelAvenida.setBorder(BorderFactory.createTitledBorder("Avenida"));
        panelAvenida.setName("Panel de avenida");
		
		// Layout
		GroupLayout panelAvenidaLayout = new GroupLayout(panelAvenida);
        
        // Campo tráfico máximo
		labelTrafico = new JLabel();
        labelTrafico.setText("Tráfico Máximo: ");
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
	} // fin del método crearPanelAvenida
	
	/**
	 * Este método crea el panel para mostrar los atributos de un punto seleccionado.
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
	} // fin del método crearPanelPunto
	
	/**
	 * Este método crea el panel para mostrar los resultados de las consultas.
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
	 * Determina si existe un punto o una avenida con las coordenadas
	 * que se pasan como parámetro y la muestra en pantalla.
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
		
		if(p==null) {
			return false;
		}
		return true;
	} // fin del método existePuntoCoordenadas
	
	/**
	 * Muestra un diálogo para abrir una configuración.
	 */
	private void mostrarRecuperarConf() {
		
		VentanaAbrir v = new VentanaAbrir(this);
		
		v.mostrar();
	}
	
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
		
		// Recupera la instancia de red del archivo
		red = (Red) DataAccessObject.cargar(nombreArchivo);
		
		// Actualiza el panel de dibujo
		panelDibujo.repaint();
	}
	
	/**
	 * Muestra un diálogo para guardar una configuración.
	 */
	public void mostrarGuardarConf() {
		
		VentanaGuardar v = new VentanaGuardar(this);
		
		v.mostrar();
	} // fin del método mostrarGuardarConf
	
	public void guardarObjetoArchivo(String nombreDir, String nombreArchivo) {
		
		// Establece el directorio padre del archivo
		DataAccessObject.setDirectorio(nombreDir);
		
		DataAccessObject.persistir(red, nombreArchivo);
	}
	
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
	} // fin del método mostrarPreguntaGuardar
	
} // fin de definición de clase Aplicacion