/*
 *   @author: Emanuel Avilés (Ec-25-15187)
 *   fecha: 29-10-24 (sustentación)
 *   Nota:: 100 pts
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Print extends JFrame {
    private JTextField sleepTimeField;
    private JTextArea outputArea;
    private PrintsNumbers printsNumbers;
    private Thread thread;

    static JButton startButton;
    static JButton clearButton;
    static JButton stopButton;
    static JButton pauseButton;
    static JButton resumeButton;

    public Print() {
      setTitle("Control de Hilos");
      setSize(400, 300);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLayout(new FlowLayout());
      JLabel label = new JLabel("Ingrese el tiempo de espera (ms): ");
      sleepTimeField = new JTextField(10);
      outputArea = new JTextArea(10, 30);
      outputArea.setEditable(false);
      JScrollPane scrollPane = new JScrollPane(outputArea);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        startButton = new JButton("Iniciar");
        clearButton = new JButton("Limpiar");
        stopButton = new JButton("Detener");
        pauseButton = new JButton("Pausar");
        resumeButton = new JButton("Reanudar");

				// Initially disable the buttons except for the start button
				clearButton.setEnabled(false);
				stopButton.setEnabled(false);
				pauseButton.setEnabled(false);
				resumeButton.setEnabled(false);




        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPrinting();
								// Initially enable the buttons except for the start button
								startButton.setEnabled(false);
								clearButton.setEnabled(false);
								stopButton.setEnabled(true);
								pauseButton.setEnabled(true);
								resumeButton.setEnabled(false);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("");
                sleepTimeField.setText("");
								clearButton.setEnabled(false);
								startButton.setEnabled(true);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (thread != null) {
                    printsNumbers.stopPrinting();
                    outputArea.setText("");
                    //disable the buttons
                    clearButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    pauseButton.setEnabled(false);
                    resumeButton.setEnabled(false);
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (printsNumbers != null) {
                    printsNumbers.pausePrinting();
                    //enable the buttons
                    pauseButton.setEnabled(false);
                    stopButton.setEnabled(false);
                    resumeButton.setEnabled(true);
                    clearButton.setEnabled(false);
                }
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (printsNumbers != null) {
                    printsNumbers.resumePrinting();

                    pauseButton.setEnabled(true);
                    stopButton.setEnabled(true);
                    resumeButton.setEnabled(false);
                    clearButton.setEnabled(false);
                }
            }
        });

        this.add(label);
        this.add(sleepTimeField);
        this.add(startButton);
        this.add(clearButton);
        this.add(stopButton);
        this.add(pauseButton);
        this.add(resumeButton);
        this.add(scrollPane);
        this.setVisible(true);
    }

    private void startPrinting() {
        try {
            int sleepTime = Integer.parseInt(sleepTimeField.getText());
            if (sleepTime < 0) {
              JOptionPane.showMessageDialog(this, "Por favor, ingresa un número no negativo.", "Error", JOptionPane.ERROR_MESSAGE);
              return;
            }
            printsNumbers = new PrintsNumbers(sleepTime, outputArea);
            thread = new Thread(printsNumbers);
            thread.start();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Print();
    }
}

