import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ConnectException;

public class clientServer extends JFrame implements ActionListener {
    private JButton startAll, stopAll, suspendAll, resumeAll, retryConnection;
    private Socket socket;
    private ObjectOutputStream oos;
    private JLabel connectionStatus;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 10000;

    // State tracking
    private enum SystemState {
        INITIAL,      // Initial state, nothing running
        RUNNING,      // System is running
        STOPPED,      // System has been stopped
        SUSPENDED     // System is suspended
    }
    private SystemState currentState = SystemState.INITIAL;

    public clientServer() {
        setTitle("Cliente Controlador");
        setSize(300, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Use GridBagLayout for more flexible layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Connection status label
        connectionStatus = new JLabel("Connecting to server...");
        connectionStatus.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 0;
        add(connectionStatus, gbc);

        // Retry connection button
        retryConnection = new JButton("Retry Connection");
        retryConnection.addActionListener(e -> connectToServer());
        retryConnection.setVisible(false);
        gbc.gridy = 1;
        add(retryConnection, gbc);

        // Create buttons
        startAll = createButton("Start All", gbc, 2);
        stopAll = createButton("Stop All", gbc, 3);
        suspendAll = createButton("Suspend All", gbc, 4);
        resumeAll = createButton("Resume All", gbc, 5);

        // Initially disable buttons
        updateButtonStates();

        // Connect to server (non-blocking)
        connectToServer();

        // Calculate and set the location
        centerFrame();
    }

    private void updateButtonStates() {
        switch (currentState) {
            case INITIAL:
                startAll.setEnabled(true);
                stopAll.setEnabled(false);
                suspendAll.setEnabled(false);
                resumeAll.setEnabled(false);
                break;
            case RUNNING:
                startAll.setEnabled(false);
                stopAll.setEnabled(true);
                suspendAll.setEnabled(true);
                resumeAll.setEnabled(false);
                break;
            case STOPPED:
                startAll.setEnabled(true);
                stopAll.setEnabled(false);
                suspendAll.setEnabled(false);
                resumeAll.setEnabled(true);
                break;
            case SUSPENDED:
                startAll.setEnabled(false);
                stopAll.setEnabled(true);
                suspendAll.setEnabled(false);
                resumeAll.setEnabled(true);
                break;
        }
    }

    private JButton createButton(String text, GridBagConstraints gbc, int yPos) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        gbc.gridy = yPos;
        add(button, gbc);
        return button;
    }

    private void connectToServer() {
        // Reset UI state
        connectionStatus.setText("Connecting to server...");
        connectionStatus.setForeground(Color.BLACK);
        retryConnection.setVisible(false);
        currentState = SystemState.INITIAL;
        updateButtonStates();

        // Use SwingWorker to prevent UI freezing during connection
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    socket = new Socket(SERVER_HOST, SERVER_PORT);
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    return null;
                } catch (ConnectException e) {
                    throw new Exception("Connection refused. Server may not be running.");
                } catch (Exception e) {
                    throw new Exception("Error connecting to server: " + e.getMessage());
                }
            }

            @Override
            protected void done() {
                try {
                    get(); // This will throw an exception if doInBackground failed
                    connectionStatus.setText("Connected to server");
                    connectionStatus.setForeground(Color.GREEN);
                    
                    // Enable start button
                    updateButtonStates();
                } catch (Exception e) {
                    connectionStatus.setText(e.getMessage());
                    connectionStatus.setForeground(Color.RED);
                    retryConnection.setVisible(true);
                }
            }
        };
        worker.execute();
    }

    private void centerFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = this.getSize().width;
        int frameHeight = this.getSize().height;

        // Position at the center horizontally and near the top vertically
        int x = (screenWidth - frameWidth) / 2;
        int y = 50;

        this.setLocation(x, y);
    }

    private void closeConnection() {
        try {
            if (oos != null) {
                oos.writeObject("Exit");
                oos.flush();
                oos.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            // Silently handle connection closure errors
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
            // Send command to server
            if (oos != null) {
                oos.writeObject(command);
                oos.flush();
            }

            // Update state based on command
            switch (command) {
                case "Start All":
                    currentState = SystemState.RUNNING;
                    break;
                case "Stop All":
                    currentState = SystemState.STOPPED;
                    break;
                case "Suspend All":
                    currentState = SystemState.SUSPENDED;
                    break;
                case "Resume All":
                    currentState = SystemState.RUNNING;
                    break;
            }

            // Update button states
            updateButtonStates();
        } catch (Exception ex) {
            // Update connection status if sending fails
            connectionStatus.setText("Error sending command: " + ex.getMessage());
            connectionStatus.setForeground(Color.RED);
            retryConnection.setVisible(true);
        }
    }

    @Override
    public void dispose() {
        closeConnection();
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            clientServer client = new clientServer();
            client.setVisible(true);
        });
    }
}
