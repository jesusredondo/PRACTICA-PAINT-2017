import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
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

import java.awt.event.WindowListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class VentanaPrincipal {

	/**
	 * IMPORTANTE: CADA HERRAMIENTA TENDRÃ� UN CÃ“DIGO ASOCIADO
	 */
	final static int BOLIGRAFO = 0;
	final static int GOMA = 1;
	// AÃ‘ADE AQUÃ� TU HERRAMIENTA;
	// TODO: AÃ±adir la herramienta

	int herramientaActual = -1; // No hay nada por defecto.

	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;

	// Paneles:
	JPanel panelSuperior;
	JPanel panelInferior;

	// Variables para dibujo
	JLabel lienzo;
	BufferedImage canvas;
	
	//Selector de colores;

	// Selector de colores;
	SelectorColor selector1;
	SelectorColor selector2;
	
	//Botones:

	// Botones:
	JButton botonNuevo;
	JButton botonBoligrafo;
	JButton botonGoma;
	
	//Tamaño de cursor

	// Tamaño de cursor
	int tamanhoCursor;
	// Imgen aux del lienzo
	Image imageAux;

	
	//VARIABLES PROPIAS DE CADA GRUPO:
	//Grupo 3
	JButton disminuirTamanhoCursor;
	JButton aumentarTamanhoCursor;
	JLabel lienzoTamanhoCursor;
	BufferedImage canvasTamanhoCursor;
	Graphics graficoTamanhoCursor;
	
	//Grupo JesÃºs:
	int xAnt=-1;
	int yAnt=-1;
	final int strokeGOMA = 10;
	
	//Constructor, marca el tamaÃ±o y el cierre del frame

	// Constructor, marca el tamaÃ±o y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 50, 800, 600);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * MÃ©todo que inicializa todos los componentes de la ventana
	 */
	public void inicializarComponentes(){
		
	public void inicializarComponentes() {

		ventana.setLayout(new GridBagLayout());
		//PANEL SUPERIOR Y COMPONENTES DE PANEL SUPERIOR
		//************************************************

		// ************************************************
		// PANEL SUPERIOR Y COMPONENTES DE PANEL SUPERIOR
		// ************************************************
		panelSuperior = new JPanel();
		panelSuperior.setLayout(new GridBagLayout());
		GridBagConstraints settings;
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.ipady = 10;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelSuperior, settings);

		// BotÃ³n nuevo
		botonNuevo = new JButton(cargarIconoBoton("Imagenes/nuevo.png"));
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		panelSuperior.add(botonNuevo, settings);

		// Selector de color1
		selector1 = new SelectorColor(Color.ORANGE);
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		settings.fill = GridBagConstraints.BOTH;
		panelSuperior.add(selector1, settings);

		// Selector de color2
		selector2 = new SelectorColor(Color.WHITE);
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		settings.fill = GridBagConstraints.BOTH;
		panelSuperior.add(selector2, settings);

		// Herramienta de bolÃ­grafo
		botonBoligrafo = new JButton(cargarIconoBoton("Imagenes/boligrafo.png"));
		settings = new GridBagConstraints();
		settings.gridx = 3;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		panelSuperior.add(botonBoligrafo, settings);

		// Herramienta de borrar
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
		
		
		
		
		
		
		
		//Un elemento que ocupe todo el espacio a la derecha:

		JPanel panelEspacioDerecha = new JPanel();
		settings = new GridBagConstraints();
		settings.gridx = 5; /*** OJO ***/
		settings.gridx = 8; /*** OJO ***/
		settings.gridy = 0;
		settings.weightx = 1;
		panelSuperior.add(panelEspacioDerecha, settings);
		
		//***************************
		//EL LIENZO DONDE PINTAMOS. 
		//***************************	

		// ***************************
		// EL LIENZO DONDE PINTAMOS.
		// ***************************
		panelInferior = new JPanel();
		panelInferior.setBorder(BorderFactory.createLineBorder(Color.RED));
		panelInferior.setLayout(new GridBagLayout());
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelInferior, settings);
		
		

		lienzo = new JLabel();
		lienzo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lienzo.setHorizontalAlignment(SwingConstants.CENTER);
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.fill = GridBagConstraints.BOTH;
		panelInferior.add(lienzo, settings);
		ventana.repaint();

	}

	/**
	 * MÃ©todo que inicializa todos los listeners del programa.
	 */
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
		// TODO: AÃ±adir nuevos listeners para las herramientas:

		// Pulsación del botón izquierdo del ratón
		lienzo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				posicionInicial(e);
			}
		});

		lienzo.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// Dependiendo de la herramienta...
				switch (herramientaActual) {
				case BOLIGRAFO:
					mouseDraggedBoligrafo(e);
					//Cambiar icono cursor
					lienzo.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
					break;

				case GOMA:
					borraGoma(e);
					//Cambiar icono cursor
					Image im = Toolkit.getDefaultToolkit().createImage("Imagenes\\GomaBorrar.png"); 
					Cursor cur = Toolkit.getDefaultToolkit().createCustomCursor(im, new Point(20,20),"WILL"); 
					lienzo.setCursor(cur); 
					break;
				}
				lienzo.repaint();
			}
		});

		
		aumentarTamanhoCursor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tamanhoCursor++;
				pintarTamanho();
			}
		});
		
		disminuirTamanhoCursor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tamanhoCursor--;
				pintarTamanho();
			}
		});

	}

	/**
	 * MÃ©todo que Borra el canvas para pintarlo completamente en Blanco. El nuevo
	 * canvas se adapta al tamanio del lienzo.
	 */
	public void actualizarCanvasVacio() {
		canvas = new BufferedImage(panelInferior.getWidth(), panelInferior.getHeight(), BufferedImage.TYPE_INT_ARGB);
		lienzo.setIcon(new ImageIcon(canvas));
		Graphics graficos = canvas.getGraphics();
		graficos.setColor(selector2.getColor());
		graficos.fillRect(0, 0, panelInferior.getWidth(), panelInferior.getHeight());
		graficos.dispose();
		lienzo.repaint();
	}

	/**
	 * MÃ©todo que nos devuelve un icono para la barra de herramientas superior.
	 * NOTA: SerÃ­a conveniente colocar una imagen con fondo transparente y que sea cuadrada, para no estropear la interfaz.
	 * @param rutaImagen: La ruta de la imagen.
	 * NOTA: SerÃ­a conveniente colocar una imagen con fondo transparente y que sea
	 * cuadrada, para no estropear la interfaz.
	 * 
	 * @param rutaImagen:
	 *            La ruta de la imagen.
	 * @return El ImageIcon que se utilizarÃ¡ en un botÃ³n.
	 */
	public ImageIcon cargarIconoBoton(String rutaImagen){
	public ImageIcon cargarIconoBoton(String rutaImagen) {
		BufferedImage bufferAuxiliar = null;
		try {
			bufferAuxiliar = ImageIO.read(new File(rutaImagen));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(bufferAuxiliar.getScaledInstance(40, 40, BufferedImage.SCALE_SMOOTH));
	}
	
	

	/**
	 * MÃ©todo que devuelve un actionListener que cambia la herramienta Actual a la que se pasa por parÃ¡metros
	 * MÃ©todo que devuelve un actionListener que cambia la herramienta Actual a la
	 * que se pasa por parÃ¡metros
	 * 
	 * @param herramienta
	 * @return Un action listener que cambia la herramienta actual. Se puede utilizar sobre los botones, por ejemplo.
	 * @return Un action listener que cambia la herramienta actual. Se puede
	 *         utilizar sobre los botones, por ejemplo.
	 */
	public ActionListener anadirListenerHerramienta(int herramienta){
	public ActionListener anadirListenerHerramienta(int herramienta) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				herramientaActual = herramienta;
			}
		};
	}
	
	

	/**
	 * MÃ©todo que realiza todas las llamadas necesarias para inicializar la ventana correctamente.
	 * MÃ©todo que realiza todas las llamadas necesarias para inicializar la ventana
	 * correctamente.
	 */
	public void inicializar(){
	public void inicializar() {
		ventana.setVisible(true);
		inicializarComponentes();	
		inicializarListeners();		
		inicializarComponentes();
		inicializarListeners();
	}

	/*****************************************
	 *****************************************
	 * AQUÃ� VAN LOS MÃ‰TODOS DE LOS LISTENERS:
	 *****************************************
	 *****************************************/


	// Actualizar el lienzo tras redimensionar
	public void redimensionarLienzo() {

		if (panelInferior.getWidth() > 0 && panelInferior.getHeight() > 0) {

			imageAux = canvas.getScaledInstance(panelInferior.getWidth(), panelInferior.getHeight(),
					Image.SCALE_SMOOTH);

			canvas = new BufferedImage(panelInferior.getWidth(), panelInferior.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = canvas.getGraphics();

			graphics.drawImage(imageAux, 0, 0, null);
			graphics.dispose();

			lienzo.setIcon(new ImageIcon(imageAux));

			lienzo.repaint();
		}
	}

	// Captura la posición inicial en la que se comienza a pintar en el lienzo.
	private void posicionInicial(MouseEvent e) {
		xAnt = e.getX();
		yAnt = e.getY();
	}


	/**
	 * 
	 */
	public void pintarTamanho() {
		refrescarCanvasTamanhoCursor();
		graficoTamanhoCursor=canvasTamanhoCursor.getGraphics();
		graficoTamanhoCursor.setColor(selector1.getColor());
		graficoTamanhoCursor.fillOval((canvasTamanhoCursor.getWidth()/2)-tamanhoCursor/2, (canvasTamanhoCursor.getHeight()/2)-tamanhoCursor/2, tamanhoCursor, tamanhoCursor);
		graficoTamanhoCursor.dispose();
		lienzoTamanhoCursor.repaint();
		
	}
	
	public void refrescarCanvasTamanhoCursor() {
		canvasTamanhoCursor= new BufferedImage(70, 50, Image.SCALE_SMOOTH);
		graficoTamanhoCursor= canvasTamanhoCursor.getGraphics();
		graficoTamanhoCursor.setColor(selector2.getColor());
		graficoTamanhoCursor.fillRect(0, 0, canvasTamanhoCursor.getWidth(), canvasTamanhoCursor.getHeight());
		graficoTamanhoCursor.dispose();
		lienzoTamanhoCursor.setIcon(new ImageIcon(canvasTamanhoCursor));
	}
	
	

	/**
	 * Pinta la lÃ­nea del bolÃ­grafo al arrastrar.
	 * 
	 * @param e
	 */
	private void mouseDraggedBoligrafo(MouseEvent e){
		if(xAnt==-1){
			xAnt=e.getX();
		}
		if(yAnt==-1){
			yAnt=e.getY();
		}

	private void mouseDraggedBoligrafo(MouseEvent e) {
		posicionInicial(e);
		Graphics grafico;
				grafico=canvas.getGraphics();
				Graphics2D lineaGorda=(Graphics2D) grafico;
				lineaGorda.setColor(selector1.getColor());
				lineaGorda.setStroke(new BasicStroke((tamanhoCursor)));
				lineaGorda.draw(new Line2D.Float(xAnt ,yAnt, e.getX(), e.getY()));
				grafico.dispose();
		
		Graphics graficos = canvas.getGraphics();
		graficos.setColor(selector1.getColor());
		graficos.drawLine(xAnt, yAnt, e.getX(), e.getY());
		graficos.dispose();
		xAnt = e.getX();
		yAnt = e.getY();
	}

	/**
	 * Borra donde estÃ© el ratÃ³n.
	 * 
	 * @param e
	 */


	private void borraGoma(MouseEvent e){
		
		posicionInicial(e);
		Graphics grafico;
				grafico=canvas.getGraphics();
				Graphics2D lineaGorda=(Graphics2D) grafico;
				lineaGorda.setColor(selector2.getColor());
				lineaGorda.setStroke(new BasicStroke((tamanhoCursor)));
				lineaGorda.draw(new Line2D.Float(xAnt ,yAnt, e.getX(), e.getY()));
				grafico.dispose();

		
	}
	
	
}