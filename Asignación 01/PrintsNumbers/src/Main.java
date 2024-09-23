import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Main {
    private JFrame frame = new JFrame("Hilos");
    private JTextArea textArea;
    private JTextField textField;
    private PrintsNumbers printsNumbers;
    private Thread thread;
    private int sleepTime = 1000;

    public Main() {
        this.frame.setDefaultCloseOperation(3);
        this.frame.setLayout(new BorderLayout());
        JPanel var1 = new JPanel();
        var1.setLayout(new FlowLayout());
        this.textField = new JTextField(10);
        var1.add(new JLabel("Ingrese el tiempo de espera (ms):"));
        var1.add(this.textField);
        JButton var2 = new JButton("Iniciar");
        var2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Main.this.startThread();
            }
        });
        var1.add(var2);
        JButton var3 = new JButton("Limpiar");
        var3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Main.this.textArea.setText("");
            }
        });
        var1.add(var3);
        this.frame.add(var1, "North");
        this.textArea = new JTextArea(20, 40);
        this.textArea.setEditable(false);
        this.frame.add(new JScrollPane(this.textArea), "Center");
        this.frame.pack();
        this.frame.setVisible(true);
    }

    private void startThread() {
        if (this.thread != null && this.thread.isAlive()) {
            this.printsNumbers.stopPrinting();

            try {
                this.thread.join();
            } catch (InterruptedException var3) {
                Thread.currentThread().interrupt();
            }
        }

        try {
            this.sleepTime = Integer.parseInt(this.textField.getText());
            System.out.println("hola," + this.sleepTime);
        } catch (NumberFormatException var2) {
            JOptionPane.showMessageDialog(this.frame, "Ingrese un valor numérico válido");
            return;
        }

        this.printsNumbers = new PrintsNumbers(this.sleepTime);
        this.thread = new Thread(this.printsNumbers);
        this.thread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
