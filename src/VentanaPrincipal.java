import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.cert.CollectionCertStoreParameters;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SpringLayout.Constraints;
import javax.swing.border.TitledBorder;


public class VentanaPrincipal {

	
	
	/**
	 * IMPORTANTE: CADA HERRAMIENTA TENDRÃ� UN CÃ“DIGO ASOCIADO
	 */
	final static int BOLIGRAFO = 0;
	final static int GOMA = 1;
	final static int MALLA = 2;
	//AÃ‘ADE AQUÃ� TU HERRAMIENTA;
	//TODO: AÃ±adir la herramienta	
	
	
	
	
	int herramientaActual = -1; //No hay nada por defecto.	
	
	//La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	
	//Paneles:
	JPanel panelSuperior;
	JPanel panelInferior;
	JLayeredPane panelCapas;
	
	//Variables para dibujo
	JLabel lienzo;
	BufferedImage canvas;
	
	//Variables para la malla
	JLabel lienzomalla;
	BufferedImage imagenmalla;
	
	//Selector de colores;
	SelectorColor selector1;
	SelectorColor selector2;
	
	//Botones:
	JButton botonNuevo;
	JButton botonBoligrafo;
	JButton botonGoma;
	JButton botonMalla;
	
	
	//VARIABLES PROPIAS DE CADA GRUPO:
	//Grupo JesÃºs:
	int xAnt=-1;
	int yAnt=-1;
	final int strokeGOMA = 10;
	int ratonX;
	int ratonY;
	
	

	//Constructor, marca el tamaÃ±o y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 50, 800, 600);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * MÃ©todo que inicializa todos los componentes de la ventana
	 */
	public void inicializarComponentes(){
		
		ventana.setLayout(new GridBagLayout());
		
		//************************************************
		//PANEL SUPERIOR Y COMPONENTES DE PANEL SUPERIOR
		//************************************************
		panelSuperior = new JPanel();
		panelSuperior.setLayout(new GridBagLayout());
		panelSuperior.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.DARK_GRAY), 
				"Herramientas", 
				TitledBorder.CENTER, 
				TitledBorder.TOP));
		
		GridBagConstraints settings;
		settings = new GridBagConstraints();
		
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.ipady = 10;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelSuperior,settings);
		
		//BotÃ³n nuevo
		botonNuevo = new JButton(cargarIconoBoton("Imagenes/nuevo.png"));
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		panelSuperior.add(botonNuevo, settings);
		
		//Selector de color1
		selector1 = new SelectorColor(Color.ORANGE);
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		settings.fill = GridBagConstraints.BOTH;
		panelSuperior.add(selector1, settings);
		
		
		//Selector de color2
		selector2 = new SelectorColor(Color.WHITE);
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		settings.fill = GridBagConstraints.BOTH;
		panelSuperior.add(selector2, settings);
		
		
		//Herramienta de bolÃ­grafo
		botonBoligrafo = new JButton(cargarIconoBoton("Imagenes/boligrafo.png"));
		settings = new GridBagConstraints();
		settings.gridx = 3;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		panelSuperior.add(botonBoligrafo, settings);
		
		//Herramienta de borrar
		botonGoma = new JButton(cargarIconoBoton("Imagenes/borrar.png"));
		settings = new GridBagConstraints();
		settings.gridx = 4;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		panelSuperior.add(botonGoma, settings);
		
		/**
		 * VUESTRAS HERRAMIENTAS AQUÃ�
		 */
		//TODO: Insertar un botÃ³n e implementar mi herramienta.
		
		//Botón para herramienta malla.
		botonMalla = new JButton(cargarIconoBoton("Imagenes/mesh.png"));
		settings = new GridBagConstraints();
		settings.gridx = 5;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		panelSuperior.add(botonMalla, settings);
		
		//Un elemento que ocupe todo el espacio a la derecha:
		JPanel panelEspacioDerecha = new JPanel();
		settings = new GridBagConstraints();
		settings.gridx = 6; /*** OJO ***/
		settings.gridy = 0;
		settings.weightx = 1;
		panelSuperior.add(panelEspacioDerecha, settings);
		
		
		//***************************
		//EL LIENZO DONDE PINTAMOS. 
		//***************************	
		//panelInferior = new JPanel();
		//panelInferior.setBorder(BorderFactory.createLineBorder(Color.RED));
		//panelInferior.setLayout(new GridBagLayout());
		
		
		//Añadir un panel de capas que admita varios paneles de imágenes.
		panelCapas = new JLayeredPane(); //Panelcapas será un nuevo JLayeredPane
		panelCapas.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		panelCapas.setLayout(new GridBagLayout());
		
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelCapas, settings); //Aádimos el panel a la ventana, en la parte inferior.
		
		//Inicializamos el lienzo y lo asignamos al panel de capas.
		lienzo = new JLabel();
		
		lienzo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lienzo.setHorizontalAlignment(SwingConstants.CENTER);
		
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		panelCapas.add(lienzo,settings,new Integer(1)); //Añadimos el lienzo al panel como nueva capa, asignando profundidad.
		panelCapas.moveToFront(lienzo); //Movemos esta capa al frente
		ventana.repaint();
		
		
		
	}
	


	/**
	 * MÃ©todo que inicializa todos los listeners del programa.
	 */
	public void inicializarListeners(){
		
		//LÃ­stener de carga de VentanaPrincipal. Cuando se carga la pantalla es cuando se puede inicializar el canvas.
		ventana.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				actualizarCanvasVacio();
			}
		});
		
		botonNuevo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarCanvasVacio();
			}
		});
		
		
		
		/**
		 * Cada nueva herramienta que aÃ±adas, tendrÃ¡ un nuevo lÃ­stener:
		 */
		botonBoligrafo.addActionListener(anadirListenerHerramienta(BOLIGRAFO));
		botonGoma.addActionListener(anadirListenerHerramienta(GOMA));
		//TODO: AÃ±adir nuevos listeners para las herramientas:
		
		
		
		
		
		lienzo.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				//Dependiendo de la herramienta...
				switch (herramientaActual) {
					case BOLIGRAFO:
						mouseDraggedBoligrafo(e);
						break;
	
					case GOMA:
						borraGoma(e);
						break;
						
					default:
						break;
				}				
				lienzo.repaint();
			}			
		});
		
		//coge las posiciones de x e y del raton para usarlas al pintar la linea
		lienzo.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				ratonX = e.getX();
				ratonY = e.getY();
			}
		});
		
		botonMalla.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Se crea un panel auxiliar que se añadirá como otra capa.
				JPanel panelAux = new JPanel();
				panelAux.setLayout(new GridBagLayout());
				lienzomalla = new JLabel();
				
			    imagenmalla = new BufferedImage(lienzo.getWidth(), lienzo.getHeight(), BufferedImage.TYPE_INT_ARGB);
				lienzomalla.setIcon(new ImageIcon(imagenmalla));
				
				//Se asigna el label que contiene la imagen de la malla a la nueva capa.
				panelAux.add(lienzomalla);
				
				//Creamos las restricciones para el panel
				GridBagConstraints	settings = new GridBagConstraints();
				settings.gridx = 0;
				settings.gridy = 0;
				settings.weightx = 1;
				settings.weighty = 1;
				settings.fill = GridBagConstraints.BOTH;
				
				//Se añade el panel auxiliar como nueva capa al panel de capas.
				panelCapas.add(panelAux,settings,new Integer(100));
				
				//Movemos la capa del panel de la malla al frente
				panelCapas.moveToFront(panelAux);
				//Seleccionamos la capa que queremos mostrar y le damos la profundidad
				panelCapas.setLayer(panelAux, 100);
				
				lienzomalla.repaint();
				
				
			}
		});
			
		
	}
	
	
	/**
	 * MÃ©todo que Borra el canvas para pintarlo completamente en Blanco.
	 * El nuevo canvas se adapta al tamanio del lienzo.
	 */
	public void actualizarCanvasVacio(){
		canvas = new BufferedImage(lienzo.getWidth(), lienzo.getHeight(), BufferedImage.TYPE_INT_ARGB);
		lienzo.setIcon(new ImageIcon(canvas));
		Graphics graficos = canvas.getGraphics();
		graficos.setColor(selector2.getColor());
		graficos.fillRect(0, 0, panelCapas.getWidth(), panelCapas.getHeight());
		graficos.dispose();
		
		lienzo.repaint();
	}
	

	
	/**
	 * MÃ©todo que nos devuelve un icono para la barra de herramientas superior.
	 * NOTA: SerÃ­a conveniente colocar una imagen con fondo transparente y que sea cuadrada, para no estropear la interfaz.
	 * @param rutaImagen: La ruta de la imagen.
	 * @return El ImageIcon que se utilizarÃ¡ en un botÃ³n.
	 */
	public ImageIcon cargarIconoBoton(String rutaImagen){
		BufferedImage bufferAuxiliar = null;
		try {
			bufferAuxiliar = ImageIO.read(new File(rutaImagen));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(bufferAuxiliar.getScaledInstance(40, 40, BufferedImage.SCALE_SMOOTH));
	}
	
	/**
	 * Método que carga la imagen de la malla escalada al tamaño del lienzo
	 * @param rutaImagen
	 * @return
	 */
	public ImageIcon cargarImagenMalla(String rutaImagen){
		BufferedImage bufferAuxiliar = null;
		try {
			bufferAuxiliar = ImageIO.read(new File(rutaImagen));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(bufferAuxiliar.getScaledInstance(lienzo.getHeight(),lienzo.getWidth(), BufferedImage.SCALE_SMOOTH));
	}
	
	
	/**
	 * MÃ©todo que devuelve un actionListener que cambia la herramienta Actual a la que se pasa por parÃ¡metros
	 * @param herramienta
	 * @return Un action listener que cambia la herramienta actual. Se puede utilizar sobre los botones, por ejemplo.
	 */
	public ActionListener anadirListenerHerramienta(int herramienta){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				herramientaActual = herramienta;
			}
		};
	}
	
	
	/**
	 * MÃ©todo que realiza todas las llamadas necesarias para inicializar la ventana correctamente.
	 */
	public void inicializar(){
		ventana.setVisible(true);
		inicializarComponentes();	
		inicializarListeners();		
	}
	
	
	
	/*****************************************
	 *****************************************
	 * AQUÃ� VAN LOS MÃ‰TODOS DE LOS LISTENERS:
	 *****************************************
	 *****************************************/
	
	

	
	/**
	 * Pinta la lÃ­nea del bolÃ­grafo al arrastrar.
	 * @param e
	 */
	private void mouseDraggedBoligrafo(MouseEvent e){
		if(xAnt==-1){
			xAnt=e.getX();
		}
		if(yAnt==-1){
			yAnt=e.getY();
		}
		
		Graphics graficos = canvas.getGraphics();
		graficos.setColor(selector1.getColor());
		graficos.drawLine(ratonX, ratonY, e.getX(), e.getY());
		graficos.dispose();

		xAnt = e.getX();
		yAnt = e.getY();

		ratonX = e.getX();
		ratonY = e.getY();

		ratonX = e.getX();
		ratonY = e.getY();

	}
	
	
	/**
	 * Borra donde estÃ© el ratÃ³n.
	 * @param e
	 */
	private void borraGoma(MouseEvent e){
		Graphics graficos = canvas.getGraphics();
		graficos.setColor(selector2.getColor());
		graficos.fillOval(e.getX()-(strokeGOMA/2), 
				e.getY()-(strokeGOMA/2), 
				strokeGOMA, 
				strokeGOMA);
		graficos.dispose();
	}
	
	
}