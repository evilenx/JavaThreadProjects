import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* @author Emanuel Avilés
**/

public class Print extends JFrame {
	private JTextField sleepTimeField;
	private JTextArea outputArea;
	private PrintsNumbers printsNumbers;
	private Thread thread;

   /**
	* Constructor de la clase Print
	* El constructor no lleva int, float, void...  Antes del nombre
   * Es lo primero que se llama cuando creas una copia de la clase
	**/
	Print() {
		setTitle("Control de Hilos");
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		JLabel label = new JLabel("Ingrese el tiempo de espera (ms): ");
		sleepTimeField = new JTextField(10);
		outputArea = new JTextArea(10, 30);
		outputArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(outputArea);

		/**
		* JFrame centrado
		* Simplemente es crear una copia de la clase Toolkit
		* Usar esa copia para conseguir el height y width de tu pantalla
		* Ese valor lo divides entre 2 y le restas la mitad de la ventana
		**/
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		JButton startButton = new JButton("Iniciar");
		JButton clearButton = new JButton("Limpiar");
		JButton stopButton  = new JButton("Detener");

		startButton.addActionListener(new ActionListener() {
		   @Override
			public void actionPerformed(ActionEvent e) {
			   startPrinting();
			}
		});

		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{//Limpiar el área de texto
				outputArea.setText("");
			}
		});

		/**
		* Detener el hilo
		* Solo interrumpes el hilo que esta corriendo
		* Limpias la pantalla
		**/
		stopButton.addActionListener(new ActionListener() {
		   @Override
			public void actionPerformed(ActionEvent e)
			{//Detiene el hilo y limpia el campo de texto
				thread.interrupt();
				outputArea.setText("");
			}
		});

		this.add(label);
		this.add(sleepTimeField);
		this.add(startButton);
		this.add(clearButton);
		this.add(stopButton);
		this.add(scrollPane);
		this.setVisible(true);
	}

   /**
	* Tomas el valor que el usuario ingresa en el TextField
	* Tratas de convertirlo a entero
	* Creas una instancia de PrintNumbers y llamas a start()
	* El sleep debe hacerlo el propio hilo
	**/
	private void startPrinting() {
		try {
			int sleepTime = Integer.parseInt(sleepTimeField.getText());
			printsNumbers = new PrintsNumbers(sleepTime, outputArea);
			thread = new Thread(printsNumbers);
			thread.start();

			//outputArea.append("main() is ending...\n");
		}
		catch (NumberFormatException e)
		{// restaurar estado de interrupción
			JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
			Thread.currentThread().interrupt();
		}
	}

	public static void main(String[] args) {
		new Print();
	}
}
