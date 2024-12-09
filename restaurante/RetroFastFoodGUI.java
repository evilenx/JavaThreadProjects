import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RetroFastFoodGUI {
    // Instance variables to manage order state
    private static double total = 0.0;
    private static List<String> orderItems = new ArrayList<>();
    private static JLabel totalLabel;
    private static JTextArea orderDetailsArea; // Nuevo textarea para mostrar detalles
    private static JPanel menuPanel;

    public static void main(String[] args) {
        // Set up retro look and feel
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
        } catch (Exception e) {
            System.out.println("No se pudo cargar el tema retro.");
        }

        // Create main frame
        JFrame frame = new JFrame("MS Fast Food");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800); // Increased size to accommodate textarea
        frame.setLayout(new BorderLayout());
        
        // Try to set icon, handle potential file not found
        try {
            frame.setIconImage(new ImageIcon("icons/hamburger_retro.png").getImage());
        } catch (Exception e) {
            System.out.println("Ícono no encontrado.");
        }
        
        frame.setLocationRelativeTo(null);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Guardar Pedido");
        JMenuItem loadItem = new JMenuItem("Cargar Pedido");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Menu Item Actions
        saveItem.addActionListener(e -> saveOrder(frame));
        loadItem.addActionListener(e -> loadOrder(frame));
        exitItem.addActionListener(e -> System.exit(0));

        // About Menu
        JMenu aboutMenu = new JMenu("About");
        JMenuItem aboutItem = new JMenuItem("Acerca de");

        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, 
                "Versión: 1.0.1\nAutor: Emanuel Avilés", 
                "Acerca de MS-Food", 
                JOptionPane.INFORMATION_MESSAGE);
        });

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        aboutMenu.add(aboutItem);
        menuBar.add(aboutMenu);

        frame.setJMenuBar(menuBar);

        
        // Toolbar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton saveButton = createToolbarButton("icons/save_icon.png", "Guardar Pedido", e -> saveOrder(frame));
        JButton loadButton = createToolbarButton("icons/load_icon.png", "Cargar Pedido", e -> loadOrder(frame));
        JButton exitButton = createToolbarButton("icons/exit_icon.png", "Salir", e -> System.exit(0));

        toolBar.add(saveButton);
        toolBar.add(loadButton);
        toolBar.add(exitButton);
        frame.add(Box.createHorizontalGlue());

        // Title
        //JLabel title = new JLabel("¡Bienvenido a MS-Food!", SwingConstants.CENTER);
        //title.setFont(new Font("MS Sans Serif", Font.BOLD, 20));
        //title.setBackground(new Color(192, 192, 192));
        //title.setOpaque(true);
        //frame.add(title);
        // Título en la barra de herramientas
        JLabel title = new JLabel("¡Bienvenido a MS-Food!", SwingConstants.CENTER);
        title.setFont(new Font("MS Sans Serif", Font.BOLD, 20));
        title.setForeground(Color.BLACK);
        toolBar.add(title);


        // Añadir la barra de herramientas al marco
        frame.add(toolBar, BorderLayout.NORTH);

        // Menu Panel
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(12, 2, 5, 5));
        menuPanel.setBackground(new Color(192, 192, 192));

        // Menu Items
        String[] menuItems = {
            "Hamburguesa Clásica", "Hamburguesa con Queso", "Hamburguesa Doble",
            "Papas Fritas", "Refresco Pequeño", "Refresco Grande",
            "Burrito", "Taco", "Pizza", "Hot Dog",
            "Galleta ChocoChip", "Cupcake", "Dona", "Pastelito",
            "Malteada", "Sundae", "Jugo de Naranja", "Smoothie de Fresa",
            "Waffle", "Paleta Helada", "Helado en Barquillo", "Pollo"
        };

        double[] prices = {
            5.99, 6.99, 8.99, 
            2.99, 1.99, 2.49, 
            7.49, 4.99, 6.99, 5.49, 
            2.49, 3.99, 2.99, 3.49, 
            4.99, 5.99, 3.99, 4.49, 
            3.99, 2.99, 3.99, 6.99
        };

        /*
        ImageIcon[] icons = new ImageIcon[menuItems.length];
        for (int i = 0; i < menuItems.length; i++) {
            try {
                icons[i] = new ImageIcon("icons/" + getIconFileName(menuItems[i]));
            } catch (Exception e) {
                icons[i] = null; // Use null if icon not found
            }
        }
        */

        ImageIcon[] icons = {
            new ImageIcon("icons/hamburger_retro.png"), 
            new ImageIcon("icons/cheeseburger_retro.png"),
            new ImageIcon("icons/doubleburger_retro.png"), 
            new ImageIcon("icons/fries_retro.png"),
            new ImageIcon("icons/soda_small_retro.png"), 
            new ImageIcon("icons/soda_large_retro.png"),
            new ImageIcon("icons/burrito.png"), 
            new ImageIcon("icons/taco.png"),
            new ImageIcon("icons/pizza_01.png"), 
            new ImageIcon("icons/hot_dog_01.png"),
            new ImageIcon("icons/cookie_chocolate_chip.png"), 
            new ImageIcon("icons/cupcake.png"),
            new ImageIcon("icons/doughnut.png"), 
            new ImageIcon("icons/oaty_cake.png"),
            new ImageIcon("icons/shake.png"), 
            new ImageIcon("icons/ice_cream_sundae_02.png"),
            new ImageIcon("icons/orange_juice.png"), 
            new ImageIcon("icons/strawberry_smoothie.png"),
            new ImageIcon("icons/waffle.png"), 
            new ImageIcon("icons/popsicle.png"),
            new ImageIcon("icons/ice_cream_bar_02.png"),
            new ImageIcon("icons/chicken.png") 
        };


        // Total Label
        totalLabel = new JLabel("Total: $0.00", SwingConstants.CENTER);
        totalLabel.setFont(new Font("MS Sans Serif", Font.BOLD, 14));
        totalLabel.setBackground(new Color(192, 192, 192));
        totalLabel.setOpaque(true);

        // Order Details TextArea
        orderDetailsArea = new JTextArea(10, 40);
        orderDetailsArea.setEditable(false);
        orderDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(orderDetailsArea);

        // Add menu buttons
        for (int i = 0; i < menuItems.length; i++) {
            int index = i;
            JButton button = new JButton(menuItems[i] + " - $" + prices[i], icons[i]);
            button.setFont(new Font("MS Sans Serif", Font.PLAIN, 12));
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setFocusPainted(false);
            button.addActionListener(e -> {
                total += prices[index];
                orderItems.add(menuItems[index] + " - $" + prices[index]);
                updateOrderDetails();
                totalLabel.setText(String.format("Total: $%.2f", total));
            });
            menuPanel.add(button);
        }

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(new Color(192, 192, 192));

        JButton finishButton = new JButton("Finalizar Pedido");
        finishButton.setFont(new Font("MS Sans Serif", Font.BOLD, 12));
        finishButton.setFocusPainted(false);
        finishButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                String.format("Gracias por su compra.\nTotal final: $%.2f", total),
                "Pedido Finalizado", JOptionPane.INFORMATION_MESSAGE);
            // Reset order after finishing
            resetOrder();
        });

        // Add components to bottom panel
        bottomPanel.add(totalLabel, BorderLayout.NORTH);
        bottomPanel.add(finishButton, BorderLayout.CENTER);
        bottomPanel.add(scrollPane, BorderLayout.SOUTH); // Add scrollpane with textarea

        // Add components to frame
        frame.add(menuPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Make window visible
        frame.setVisible(true);
    }

    // Update order details in the TextArea
    private static void updateOrderDetails() {
        // Clear previous content
        orderDetailsArea.setText("");
        
        // Add each item to the TextArea
        for (String item : orderItems) {
            orderDetailsArea.append(item + "\n");
        }
        
        // Add total at the end
        orderDetailsArea.append("\n" + String.format("Total: $%.2f", total));
    }

    // Helper method to create toolbar buttons with error handling
    private static JButton createToolbarButton(String iconPath, String tooltip, ActionListener action) {
        JButton button;
        try {
            button = new JButton(new ImageIcon(iconPath));
        } catch (Exception e) {
            button = new JButton(tooltip); // Fallback to text if icon fails
        }
        button.setToolTipText(tooltip);
        button.addActionListener(action);
        return button;
    }

    // Helper method to map menu items to icon filenames
    private static String getIconFileName(String menuItem) {
        // Convert menu item to lowercase and replace spaces with underscores
        return menuItem.toLowerCase().replace(' ', '_') + "_retro.png";
    }

    // Save order to file
    private static void saveOrder(JFrame parent) {
        if (orderItems.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "No hay pedido para guardar.", 
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Pedido");
        int userSelection = fileChooser.showSaveDialog(parent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            // Ensure file has .txt extension
            if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(fileToSave))) {
                // Write order details
                for (String item : orderItems) {
                    writer.println(item);
                }
                // Write total
                writer.println(String.format("Total: $%.2f", total));

                JOptionPane.showMessageDialog(parent, "Pedido guardado exitosamente.", 
                    "Guardado", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error al guardar el pedido: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Load order from file
    private static void loadOrder(JFrame parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Cargar Pedido");
        int userSelection = fileChooser.showOpenDialog(parent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileToLoad))) {
                // Reset current order
                resetOrder();

                String line;
                while ((line = reader.readLine()) != null) {
                    // Skip empty lines
                    if (line.trim().isEmpty()) continue;

                    // Check if line is total
                    if (line.trim().toLowerCase().startsWith("total:")) {
                        // Parse total
                        try {
                            total = Double.parseDouble(line.split(":")[1].trim().replace("$", ""));
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            JOptionPane.showMessageDialog(parent, "Error al leer el total.", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    }

                    // Add item to order
                    orderItems.add(line.trim());
                }

                // Update order details and total label
                updateOrderDetails();
                totalLabel.setText(String.format("Total: $%.2f", total));

                JOptionPane.showMessageDialog(parent, "Pedido cargado exitosamente.", 
                    "Cargado", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error al cargar el pedido: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Reset order state
    private static void resetOrder() {
        total = 0.0;
        orderItems.clear();
        totalLabel.setText("Total: $0.00");
        orderDetailsArea.setText(""); // Clear textarea
    }
}
