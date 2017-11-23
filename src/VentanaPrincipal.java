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
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


public class VentanaPrincipal {

	/**
	 * IMPORTANTE: CADA HERRAMIENTA TENDRÃƒÆ’Ã¯Â¿Â½ UN CÃƒÆ’Ã¢â‚¬Å“DIGO ASOCIADO
	 */
	final static int BOLIGRAFO = 0;
	final static int GOMA = 1;

	//TODO: AÃƒÆ’Ã‚Â±adir la herramienta	

	

	// AÃ‘ADE AQUÃ� TU HERRAMIENTA;

	final static int CLONAR = 2;
	final static int DESHACER = 3;
	final static int REHACER = 4;
	final static int GUARDAR = 5;
	final static int CARGAR = 6;


	int herramientaActual = -1; // No hay nada por defecto.

	// La ventana principal, en este caso, guarda todos los componentes:

	JFrame ventana;

	// Paneles:
	JPanel panelSuperior;
	JPanel panelInferior;

	// Variables para dibujo
	JLabel lienzo;
	BufferedImage canvas;

	// Selector de colores;
	SelectorColor selector1;
	SelectorColor selector2;

	// Botones:
	JButton botonNuevo;
	JButton botonBoligrafo;
	JButton botonGoma;
	JButton botonClonar;
	JButton botonDeshacer;
	JButton botonRehacer;
	JButton botonSave;
	JButton botonCargarImagen;
	
	//VARIABLES PROPIAS DE CADA GRUPO:
	//Grupo JesÃºs:
	int xAnt=-1;
	int yAnt=-1;
	final int strokeGOMA = 10;
	
	//Grupo 2
	//Variables herramienta clonar
	
	int xRef =-1;
	int yRef =-1;
	int offsetX =0;
	int offsetY =0;
	int lastX = 0;
	int lastY = 0;
	int tamanioClonar =100;
	boolean empiezaClonar = false;
	

	BufferedImage canvasAnterior;
	BufferedImage canvasPosterior;

	//Ruta por defecto de guardado
	String rutaIncial = "\\home\\";


	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 50, 800, 600);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setTitle("Paint grupo 2");
	}

	/**
	 * MÃ©todo que inicializa todos los componentes de la ventana
	 */
	public void inicializarComponentes() {

		ventana.setLayout(new GridBagLayout());

		// ************************************************
		// PANEL SUPERIOR Y COMPONENTES DE PANEL SUPERIOR
		// ************************************************
		panelSuperior = new JPanel();
		panelSuperior.setLayout(new GridBagLayout());
		panelSuperior.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY),
				"Herramientas", TitledBorder.CENTER, TitledBorder.TOP));

		GridBagConstraints settings;
		settings = new GridBagConstraints();

		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.ipady = 10;
		settings.fill = GridBagConstraints.BOTH;

		ventana.add(panelSuperior,settings);
		
		// BotÃƒÂ³n nuevo


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
		 * VUESTRAS HERRAMIENTAS AQUÃ­
		 */
		
		//Herramienta de clonar
		botonClonar = new JButton(cargarIconoBoton("Imagenes/clonarIcon.png"));
		settings = new GridBagConstraints();
		settings.gridx = 5;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		panelSuperior.add(botonClonar, settings);
		


		// Herramienta de deshacer
		botonDeshacer = new JButton(cargarIconoBoton("Imagenes/deshacer.png")); // Icon made by
																				// [https://www.flaticon.es/autores/simpleicon]
																				// from www.flaticon.com
		settings = new GridBagConstraints();
		settings.gridx = 6;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		panelSuperior.add(botonDeshacer, settings);

		// Herramienta de rehacer
		botonRehacer = new JButton(cargarIconoBoton("Imagenes/rehacer.png")); // Icon made by
																				// [https://www.flaticon.es/autores/simpleicon]
																				// from www.flaticon.com
		settings = new GridBagConstraints();
		settings.gridx = 7;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		panelSuperior.add(botonRehacer, settings);

		//BotÃ³n de guardar imagen
		botonSave = new JButton(cargarIconoBoton("imagenes/guardar.png"));
		settings = new GridBagConstraints();
		settings.gridx = 8;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		panelSuperior.add(botonSave, settings);

		//BotÃ³n cargar imagen
		botonCargarImagen = new JButton(cargarIconoBoton("imagenes/loadImageIcon.png"));
		settings = new GridBagConstraints();
		settings.gridx = 9;
		settings.gridy = 0;
		settings.insets = new Insets(0, 10, 0, 0);
		panelSuperior.add(botonCargarImagen, settings);
		
		// Un elemento que ocupe todo el espacio a la derecha:
		JPanel panelEspacioDerecha = new JPanel();
		settings = new GridBagConstraints();
		settings.gridx = 10; /*** OJO ***/
		settings.gridy = 0;
		settings.weightx = 1;
		panelSuperior.add(panelEspacioDerecha, settings);

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
	public void inicializarListeners() {

		// Listener de carga de VentanaPrincipal. Cuando se carga la pantalla es cuando

		// se puede inicializar el canvas.
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
		 * Cada nueva herramienta que aÃ±adas, tendrÃ¡ un nuevo lisstener:
		 */
		botonBoligrafo.addActionListener(anadirListenerHerramienta(BOLIGRAFO));
		botonGoma.addActionListener(anadirListenerHerramienta(GOMA));
		botonClonar.addActionListener(anadirListenerHerramienta(CLONAR));

		/**
		 * 
		 * LOS METODOS DEBERIAN REALIZAR LAS OPCIONES DE DESHACER Y REHACER PERO HE SIDO INCAPAZ DE SOLUCIONARLO SIN COLECCIONES EXTERNAS
		 * QUE NO HEMOS LLEGADO A DAR EN CLASE COMO ES UndoManager U OTRAS
		 * 
		 * https://alvinalexander.com/java/jwarehouse/jEdit/jEdit/org/gjt/sp/jedit/buffer/UndoManager.java.shtml
		 * 
		 * @author ALBERTO REY MORENO - GRUPO 2 
		 * 
		 */
		botonDeshacer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// canvas = canvasAnterior;
				canvasAnterior = new BufferedImage(panelInferior.getWidth(), panelInferior.getHeight(), BufferedImage.TYPE_INT_ARGB);
				lienzo.setIcon(new ImageIcon(canvasAnterior));
				//correccion rapida por joaquin
				//Ahora limpia el canvas
				canvas.flush();canvas=canvasAnterior;
				lienzo.repaint();
				
			}
		});
		
		botonRehacer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				canvas = canvasPosterior;
				canvasPosterior = new BufferedImage(panelInferior.getWidth(), panelInferior.getHeight(), BufferedImage.TYPE_INT_ARGB);
				lienzo.setIcon(new ImageIcon(canvasPosterior));
				lienzo.repaint();
			}
		});

		// Joaquin

		// TODO: AÃ±adir nuevos listeners para las herramientas:
		botonSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guardar();
			}

		});

		//Boton cargarImagen
		botonCargarImagen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cargarImagen();
			}
		});

		lienzo.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// Dependiendo de la herramienta...
				switch (herramientaActual) {
					case BOLIGRAFO:
						mouseDraggedBoligrafo(e);
						break;
	
					case GOMA:
						borraGoma(e);
						break;
						
					case CLONAR:
						clonar(e);
						break;
						
					default:
						break;
				}				
				lienzo.repaint();
			}
		});
		
		lienzo.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e) {
				{
					//Si la herramienta CLONAR estÃ¡ seleccionada se pulsa el botÃ³n derecho
					if (herramientaActual==CLONAR && SwingUtilities.isRightMouseButton(e))
					{
					//Actualizala referencia
						xRef = e.getX()-tamanioClonar/2;
						yRef = e.getY()-tamanioClonar/2;
						System.out.println("Referencia en x: " + xRef +" y:" + yRef);
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (herramientaActual==CLONAR)
				{
					empiezaClonar= false;
				}
			}
		});
	
		lienzo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xAnt=e.getX();
				yAnt=e.getY();
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
	 * NOTA: SerÃ¡ conveniente colocar una imagen con fondo transparente y que sea
	 * cuadrada, para no estropear la interfaz.
	 * 
	 * @param rutaImagen:
	 *            La ruta de la imagen.
	 * @return El ImageIcon que se utilizarÃ¡Â¡ en un botÃ³n.
	 */
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
	 * MÃ©todo que devuelve un actionListener que cambia la herramienta Actual a la
	 * que se pasa por parÃƒÂ¡metros
	 * 
	 * @param herramienta
	 * @return Un action listener que cambia la herramienta actual. Se puede
	 *         utilizar sobre los botones, por ejemplo.
	 */
	public ActionListener anadirListenerHerramienta(int herramienta) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				herramientaActual = herramienta;
				
			}
		};
	}

	/**
	 * MÃ©todo que realiza todas las llamadas necesarias para inicializar la ventana
	 * correctamente.
	 */
	public void inicializar() {
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
	 * Pinta la lÃ­nea del bolÃ­grafo al arrastrarlo
	 * 
	 * @param e
	 */
	private void mouseDraggedBoligrafo(MouseEvent e) {

		if (xAnt == -1) {
			xAnt = e.getX();
		}
		if (yAnt == -1) {
			yAnt = e.getY();
		}

		Graphics graficos = canvas.getGraphics();
		graficos.setColor(selector1.getColor());
		graficos.drawLine(xAnt, yAnt, e.getX(), e.getY());
		graficos.dispose();
		xAnt = e.getX();
		yAnt = e.getY();
	}

	/**
	 * Borra donde estÃ¡ el ratÃ³n.
	 * @param e
	 */
	private void borraGoma(MouseEvent e) {
		Graphics graficos = canvas.getGraphics();
		graficos.setColor(selector2.getColor());
		graficos.fillOval(e.getX() - (strokeGOMA / 2), e.getY() - (strokeGOMA / 2), strokeGOMA, strokeGOMA);
		graficos.dispose();
	}
	
	/**
	 *  MÃ©todo de clonado
	 * @param e - Evento del ratÃ³n
	 */
	private void clonar(MouseEvent e)
	{
		if (SwingUtilities.isLeftMouseButton(e))
		{
			//Si se ejecuta por primera vez
			if (!empiezaClonar)
			{
				lastX =e.getX();
				lastY=e.getY();
				offsetX =0;
				offsetY =0;
				empiezaClonar=true;
			}
			else
			{
				offsetX = lastX-e.getX();
				offsetY = lastY-e.getY();
			}
				System.out.println("clonar");
				Graphics graficos = canvas.getGraphics();
				graficos.copyArea(xRef-offsetX, yRef-offsetY, 100, 100, (e.getX()-(xRef-offsetX))-50, (e.getY()-(yRef-offsetY))-50);
				graficos.dispose();
//			}
		}
	}
	/*
	 * Este metodo permite guardar el contenido del canvas a un archivo Para ello
	 * abrirÃƒÂ¡ un JfileChooser en el que el usuario elegirÃƒÂ¡ donde desea guardar el
	 * archivo y su nombre Si la ruta no tiene la extensiÃƒÂ³n jpg png o gif se le
	 * aÃƒÂ±adirÃƒÂ¡ al final .png
	 * 
	 * @author Joaquin Alonso Perianez Grupo 2
	 */
	protected void guardar() {
		JFileChooser explorador = new JFileChooser(rutaIncial);// para que salga en documentos
		explorador.setSelectedFile(new File(rutaIncial));
		explorador.setDialogTitle("Guardar imagen...");
		explorador.setFileFilter(new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png", "gif"));
		int seleccion = explorador.showSaveDialog(ventana);// devuelve el boton pulsado
		try {
			if (seleccion == JFileChooser.APPROVE_OPTION) {// comprueba si ha presionado el boton de aceptar
				File Seleccionada = explorador.getSelectedFile();
				String ruta = Seleccionada.getAbsolutePath();// obtenemos el path del archivo a guardar
				// comprobamos si a la hora de guardar obtuvo la extension y si no se la
				// asignamos
				if (!(ruta.endsWith(".png")) && !(ruta.endsWith(".jpg")) && !(ruta.endsWith(".gif"))) {
					File temp = new File(ruta + ".png");
					Seleccionada = temp;// renombramos el archivo
				}
				canvas.getGraphics();
				// Escribimos la imagen en el archivo.
				ImageIO.write(canvas, "png", Seleccionada);
				JOptionPane.showMessageDialog(ventana, "Guardado", "Guardado exitoso!",
						JOptionPane.INFORMATION_MESSAGE);
				rutaIncial = Seleccionada.getAbsolutePath();

			}
		} catch (Exception ex) {// por alguna excepcion salta un mensaje de error
			JOptionPane.showMessageDialog(ventana, "Error al guardar el archivo!", "Oops! Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * 	MÃ©todo para cargar imagen desde un fichero. Rescala la imagen selecionada y la pinta sobre el canvas
	 */
	protected void cargarImagen()
	{
		JFileChooser explorador = new JFileChooser(rutaIncial);
		explorador.setSelectedFile(new File(rutaIncial));
		explorador.setDialogTitle("Cargar imagen...");
		explorador.setFileFilter(new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png", "gif"));
		int fileSelected= explorador.showOpenDialog(ventana);
		if (fileSelected == JFileChooser.APPROVE_OPTION)
		{
			try {
				File imageFile = explorador.getSelectedFile().getAbsoluteFile();
				BufferedImage image= ImageIO.read(imageFile);
				Image resize = image.getScaledInstance(lienzo.getWidth(), lienzo.getHeight(), Image.SCALE_SMOOTH);

				Graphics graficos = canvas.getGraphics();
				graficos.drawImage(resize, 0, 0, null);
				graficos.dispose();
				lienzo.repaint();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}