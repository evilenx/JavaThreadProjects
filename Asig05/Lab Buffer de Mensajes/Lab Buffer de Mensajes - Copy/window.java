import java.swing*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class window extends Jframe {
  private JTextField producerField;
  private JTextField bufferfield;
  private JTextField consumerField;

  private JButton startButton;
  private JButton stopButton;
  private JButton suspendButton;
  private JButton resumeButton;


  private boolean isRunning = false;
  private boolean isSuspend = false;

  private window() {
    super("Producer-Consumer Example");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new FlowLayout());

    // Create labels and text fields
    JLabel producerLabel = new JLabel("Producer:");
    producerField = new JTextField(10);

    JLabel bufferLabel = new JLabel("Buffer:");
    bufferField = new JTextField(10);

    JLabel consumerLabel = new JLabel("Consumer:");
    consumerField = new JTextField(10);

    add(producerLabel);
    add(producerField);
    add(bufferLabel);
    add(bufferField);
    add(consumerLabel);
    add(consumerField);

    // Create buttons
    startButton = new JButton("Iniciar");
    stopButton = new JButton("Parar");
    suspendButton = new JButton("Suspender");
    resumeButton = new JButton("Reanudar");

		// Set up frame
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
  }

}


