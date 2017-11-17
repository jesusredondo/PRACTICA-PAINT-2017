import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;

/**
 * Clase que se utiliza para definir un selector de color.
 * El color se selecciona mediante un JColorChooser.
 * El componente base es un botón. El color seleccionado se muestra con el fondo del botón.
 * Tiene métodos públicos para obtener el color actual.
 * @author jesusredondogarcia
 *
 */
public class SelectorColor extends JButton implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	
	Color color;
	
	/**
	 * Sólo es válido el constructor sin ningún parámetro.
	 * @param colorInicial : El color inicial que tomará.
	 */
	public SelectorColor(Color colorInicial) {
		super();
		setColor(colorInicial);
		
		/** OJO **/
		addActionListener(this);
		
		//Hacemos que el tamaño preferido sea de 50x50 y que se acepte por el mínimo.
        setMinimumSize(new Dimension(50, 50));
        setPreferredSize(new Dimension(50, 50));
	}
	
	/**
	 * Método para obtener el color desde otra clase. Es útil para seleccionar un color y luego usarlo en otra herramienta.
	 * @return
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Método para definir el color del selector desde fuera.
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
		//repaint();
	}
	
	
	
	@Override
	/**
	 * Implementamos actionListener, luego debe existir este método, el que define qué ocurre cuando se pulsa un "SelectorColor"
	 */
	public void actionPerformed(ActionEvent e) {
		Color colorSeleccionado = JColorChooser.showDialog(this, "Selecciona Color", color);
		if (colorSeleccionado != null){
			color = colorSeleccionado;
			//repaint();
		}
	}
	
	
	
	@Override
	/**
	 * Por ahora no tenemos nada que comentar sobre este método. Hace la magia de pintar los selectores.
	 */
	public void paintComponent(Graphics g) {
		int borde = 1;
		g.setColor(Color.BLACK);
		g.fillOval(0, 0, this.getWidth(), this.getHeight());
		g.setColor(color);
		g.fillOval(borde, borde, this.getWidth()-borde*2, this.getHeight()-borde*2);
	}
	
}
