/*
 *    @author: Emanuel Avilés
 *    @fecha de presentación: 24-10-24
 *    @Nota: 100 pts
 *    @version: 1.0
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Enumeration;

import java.net.*;
import java.io.*;

public class DrawArcs3b extends JPanel  implements ActionListener
{
    public double currentTheta;

    public JPanel speedPanel;
    public JPanel fanPanel;
    public JPanel boxPanel;

    public  JRadioButton lowButton;
    public  JRadioButton midButton;
    public  JRadioButton highButton;

    private JButton jbStop;
    private JButton jbReverse;

    private static JButton startAll;
    private static JButton stopAll;
    private static JButton suspendAll;
    private static JButton resumeAll;

    private String stopString ="Stop";
    private String reverseString ="Reverse";

    private String onString = "On";
    private String lowString = "Low";
    private String midString = "Mid";
    private String highString = "High";

    private static ServerSocket serverSocket;

    Timer timer;

    public int reverse = 1;
    public ButtonGroup group;

    public int count = 0;

    public DrawArcs3b()
    {
        //timer = new Timer(speed, new TimerListener());

        // setTitle("Box Fan");
        setLayout(new BorderLayout());

        //getContentPane().add(new ArcsPanel(), BorderLayout.CENTER);
        add(new ArcsPanel(), BorderLayout.CENTER);
        choicePanel();
    }

    private class TimerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            repaint();
        }
    }

    public static void main (String [] args)
    {
        DrawArcs3b jpanel = new DrawArcs3b();
        DrawArcs3b jpanel1 = new DrawArcs3b();
        DrawArcs3b jpanel2 = new DrawArcs3b();
        DrawArcs3b jpanel3 = new DrawArcs3b();

        JFrame jframe = new JFrame();

        /*jframe.add(jpanel);
        jframe.add(jpanel1);
        jframe.add(jpanel2);
        jframe.add(jpanel3);*/

        JPanel fanPanel = new JPanel();
        fanPanel.setLayout(new GridLayout(2, 1, 20, 10));
        fanPanel.add(jpanel);
        fanPanel.add(jpanel1);
        fanPanel.add(jpanel2);
        fanPanel.add(jpanel3);

        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(new BorderLayout(2,2));
        screenPanel.add(fanPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonBar(jpanel, jpanel1, jpanel2, jpanel3);
        screenPanel.add(buttonPanel, BorderLayout.SOUTH);

        startAll.setEnabled(true);
        stopAll.setEnabled(false);
        suspendAll.setEnabled(false);
        resumeAll.setEnabled(false);

        jframe.add(screenPanel);

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(1000, 600);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.setResizable(false);
        new Thread(() -> startServer(jpanel, jpanel1, jpanel2, jpanel3)).start(); //configuración del servidor
    }

public static void startServer(DrawArcs3b... panels) {
    ServerSocket serverSocket = null;
    Socket socket = null;
    ObjectInputStream ois = null;

    try {
        serverSocket = new ServerSocket(10000);
        serverSocket.setReuseAddress(true); // Optional, but helpful for server restarts
        socket = serverSocket.accept();
        ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Servidor escuchando en el puerto 10000...");
        System.out.println("Cliente conectado...");

        while (true) {
            try {
                String command = (String) ois.readObject();
                System.out.println("Comando recibido: " + command);

                switch (command) {
                    case "Start All":
                        for (DrawArcs3b panel : panels) {
                            panel.time(65);
                            panel.timer.start();
                        }
                        break;

                    case "Stop All":
                        for (DrawArcs3b panel : panels) {
                            if (panel.timer != null && panel.timer.isRunning()) {
                                panel.timer.stop();
                            }
                        }
                        break;

                    case "Suspend All":
                        for (DrawArcs3b panel : panels) {
                            if (panel.timer != null) panel.timer.stop();
                        }
                        break;

                    case "Resume All":
                        for (DrawArcs3b panel : panels) {
                            panel.timer.start();
                        }
                        break;

                    default:
                        System.out.println("Comando no reconocido.");
                }
            } catch (EOFException e) {
                System.out.println("Client has disconnected.");
                break; // Exit the loop when client disconnects
            } catch (SocketException e) {
                System.out.println("Socket exception: " + e.getMessage());
                break; // Exit the loop when socket exception occurs
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // Close resources in the finally block
        if (ois != null) {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}






// public static void startServer(DrawArcs3b... panels) {
//        try {
//            serverSocket = new ServerSocket(10000);
//            Socket socket = serverSocket.accept();
//            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//            System.out.println("Servidor escuchando en el puerto 10000...");
//
//            System.out.println("Cliente conectado...");
//            while(true){
//                try {
//                    try{
//                      String command = (String) ois.readObject();
//                    }catch( EOFException e){ System.exit(1);}
//
//                    System.out.println("Comando recibido: " + command);
//
//                    switch (command) {
//                        case "Start All":
//                            for (DrawArcs3b panel : panels) {
//                                panel.time(65);
//                                panel.timer.start();
//                            }
//                            break;
//
//                        case "Stop All":
//                            for (DrawArcs3b panel : panels) {
//                                if (panel.timer != null && panel.timer.isRunning()){
//                                  panel.timer.stop();
//
//                                }
//                            }
//                            break;
//
//                        case "Suspend All":
//                            for (DrawArcs3b panel : panels) {
//                                if (panel.timer != null) panel.timer.stop();
//                            }
//                            break;
//
//                        case "Resume All":
//                            for (DrawArcs3b panel : panels) {
//                                panel.timer.start();
//                            }
//                            break;
//
//                        default:
//                            System.out.println("Comando no reconocido.");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//

 public int getCount()
 {
   return count;
 }

 public void time(int speed)
 {
   if(speed  >= 0)
        {
            timer = new Timer(speed, new TimerListener());
            timer.start();
        }
    }

    public void choicePanel()
    {
        // ButtonListener choiceBtn = new ButtonListener();

        lowButton = new JRadioButton(lowString,false);
        lowButton.setActionCommand(lowString);

        midButton = new JRadioButton(midString,false);
        midButton.setActionCommand(midString);

        highButton = new JRadioButton(highString,false);
        highButton.setActionCommand(highString);

        //Group the radio buttons.
        group = new ButtonGroup();

        group.add(lowButton);
        group.add(midButton);
        group.add(highButton);

        //Register a listener for the radio buttons.

        lowButton.addActionListener(this);
        midButton.addActionListener(this);
        highButton.addActionListener(this);

        speedPanel = new JPanel();

        speedPanel.add(lowButton);
        speedPanel.add(midButton);
        speedPanel.add(highButton);

        jbReverse = new JButton(reverseString);
        jbReverse.setActionCommand(reverseString);

        jbStop = new JButton(stopString);
        jbStop.setActionCommand(stopString);

        speedPanel.add(jbStop);
        speedPanel.add(jbReverse);

        jbStop.addActionListener(this);
        jbReverse.addActionListener(this);
        //
        add(speedPanel, BorderLayout.NORTH);
    }

    public static JRadioButton getSelection(ButtonGroup group)
    {
        for (Enumeration e = group.getElements(); e.hasMoreElements();)
        {
            JRadioButton b = (JRadioButton) e.nextElement();
            if (b.getModel() == group.getSelection())
            {
                return b;
            }
        }
        return null;
    }
      /*  public class ButtonListener implements ActionListener
        {
                int count=0;

       */

    //count++;
    public void actionPerformed(ActionEvent e)
    {
        AbstractButton aButton = (AbstractButton) e.getSource();

        System.out.println("Selected: " + aButton.getText());

                /*	ButtonModel aModel = aButton.getModel();
                                    boolean armed = aModel.isArmed();
                                    boolean pressed = aModel.isPressed();
                                    boolean selected = aModel.isSelected();
                     System.out.println("Changed: " + armed + "/" + pressed + "/" +selected);
                     */

        if(e.getActionCommand().equals("Low"))
        {
            count++;
            if(count == 1)
            {
                time(150);
            }
            else if (count > 1)
            {
                timer.start();
                timer.stop();
                time(150);
            }
        }
        else if (e.getActionCommand().equals("Mid"))
        {
            count++;

            if(count == 1)
            {
                time(65);
            }
            else
            {
                timer.start();
                timer.stop();
                time(65);
            }
        }
        else if (e.getActionCommand().equals("High"))
        {
            count++;

            if(count == 1)
            {
                time(20);
            }

            else
            {

                timer.start();
                timer.stop();
                time(20);
            }
        }
        else if (e.getActionCommand().equals("Stop"))
        {
            count++;
            if(count==1)
            {
                count--;
                time(-1);
            }
            else
            {
                JRadioButton seleccionado = DrawArcs3b.getSelection(group);
                seleccionado.setSelected(false);
                //System.out.println(seleccionado.toString());

/*
                                	ButtonModel aModel = aButton.getModel();
                                    boolean armed = aModel.isArmed();
                                    boolean pressed = aModel.isPressed();

                                    boolean selected = aModel.isSelected();
                                    */
                // group.setSelected(aModel,false);
                //lowButton.setSelected(false);
                //midButton.setSelected(false);
                //highButton.setSelected(false);
                // System.out.println("Changed: " + armed + "/" + pressed + "/" +selected);

                timer.start();
                timer.stop();
            }
        }
        else if (e.getActionCommand().equals("Reverse"))
        {
            count++;
            if(count==1)
            {
                count--;
                time(-1);
            }
            else
            {
                timer.start();
                timer.stop();

                if(lowButton.isSelected())
                    time(150);
                else if (midButton.isSelected())
                    time(65);
                else time(20);

                reverse = -reverse;
                //repaint();
            }
        }
        //}
    }

    class ArcsPanel extends JPanel
    {

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            currentTheta = currentTheta -1;

            int xCenter = 0;
            int yCenter = 0;

            int radius = (int) (Math.min(getWidth(), getHeight()) * 0.4) -30;
            int x = xCenter - radius;
            int y = yCenter - radius;
            int sLength = (int) (radius * 0.8);

            g2d.translate(this.getWidth() / 2, this.getHeight() / 2);

            //Frame
            g.setColor(Color.red);
            g.drawRect(-100,-100,200,200);

            //Horizontal Line
            g.setColor(Color.black);

            g.drawLine(-100,-25,100,-25);
            g.drawLine(-100,-50,100,-50);
            g.drawLine(-100,-75,100,-75);
            g.drawLine(-100,0, 100,0);
            g.drawLine(-100,25, 100,25);
            g.drawLine(-100,50, 100,50);
            g.drawLine(-100,75, 100,75);

            //Handle
            g.setColor(Color.blue);
            g.drawLine(-30,-100,-25,-120);
            g.drawLine(30,-100,25,-120);
            g.drawLine(-25,-120,25,-120);

            //Vertical Lines
            g.setColor(Color.black);
            g.drawLine(-75,100,-75,-100);
            g.drawLine(-50,100,-50,-100);
            g.drawLine(-25,100,-25,-100);
            g.drawLine(0,100,0,-100);
            g.drawLine(25,100,25,-100);
            g.drawLine(50,100,50,-100);
            g.drawLine(75,100,75,-100);

            //Fan blades orientation
            if( reverse  < 0)
                g2d.rotate(-currentTheta);
            else if (reverse > 0)
                g2d.rotate(currentTheta);

            g.setColor(Color.blue);
            g.fillArc(x, y,2 * radius, 2 * radius, 0, 45);
            g.fillArc(x, y, 2 * radius, 2 * radius, 90, 45);
            g.fillArc(x, y, 2 * radius, 2 * radius, 180, 45);
            g.fillArc(x, y, 2 * radius, 2 * radius, 270, 45);
        }
    }

    public static JPanel createButtonBar(DrawArcs3b jPanel, DrawArcs3b jPanel1, DrawArcs3b jPanel2, DrawArcs3b jPanel3)
    {
        JPanel buttonPanel = new JPanel();
        startAll = new JButton("Start all");
        startAll.addActionListener(e ->
        {
            System.out.println("MOVE NOW");

            if(jPanel.timer != null && jPanel1.timer != null && jPanel2.timer != null && jPanel3.timer != null)
            {
                jPanel.timer.start();
                jPanel.midButton.setSelected(true);
                jPanel1.timer.start();
                jPanel1.midButton.setSelected(true);
                jPanel2.timer.start();
                jPanel2.midButton.setSelected(true);
                jPanel3.timer.start();
                jPanel3.midButton.setSelected(true);
            }
            else
            {
                jPanel.time(65);
                jPanel.timer.start();
                jPanel.midButton.setSelected(true);
                jPanel.count = 1;

                jPanel1.time(65);
                jPanel1.timer.start();
                jPanel1.midButton.setSelected(true);
                jPanel1.count = 1;

                jPanel2.time(65);
                jPanel2.timer.start();
                jPanel2.midButton.setSelected(true);
                jPanel2.count = 1;

                jPanel3.time(65);
                jPanel3.timer.start();
                jPanel3.midButton.setSelected(true);
                jPanel3.count = 1;
            }
            startAll.setEnabled(false);
            stopAll.setEnabled(true);
            resumeAll.setEnabled(false);
            suspendAll.setEnabled(true);
        });

        stopAll = new JButton("Stop all");
        stopAll.addActionListener(e -> {
            System.out.println("STOP NOW");
            jPanel.timer.stop();
            jPanel1.timer.stop();
            jPanel2.timer.stop();
            jPanel3.timer.stop();

            jPanel.currentTheta = 0;
            jPanel1.currentTheta = 0;
            jPanel2.currentTheta = 0;
            jPanel3.currentTheta = 0;

            jPanel.reverse = 1;
            jPanel1.reverse = 1;
            jPanel2.reverse = 1;
            jPanel3.reverse = 1;

            startAll.setEnabled(true);
            stopAll.setEnabled(false);

            startAll.setEnabled(true);
            suspendAll.setEnabled(false);
            resumeAll.setEnabled(false);
            stopAll.setEnabled(false);
        });

        suspendAll = new JButton("Suspend all");
        suspendAll.addActionListener(e -> {
            System.out.println("Suspend NOW");
            jPanel.timer.stop();
            jPanel1.timer.stop();
            jPanel2.timer.stop();
            jPanel3.timer.stop();

            startAll.setEnabled(false);
            stopAll.setEnabled(true);
            suspendAll.setEnabled(false);
            resumeAll.setEnabled(true);
        });

        resumeAll = new JButton("Resume all");
        resumeAll.addActionListener(e -> {
            System.out.println("Resume NOW");
            jPanel.time(65);
            jPanel.timer.start();
            jPanel.midButton.setSelected(true);
            jPanel.count = 1;

            jPanel1.time(65);
            jPanel1.timer.start();
            jPanel1.midButton.setSelected(true);
            jPanel1.count = 1;

            jPanel2.time(65);
            jPanel2.timer.start();
            jPanel2.midButton.setSelected(true);
            jPanel2.count = 1;

            jPanel3.time(65);
            jPanel3.timer.start();
            jPanel3.midButton.setSelected(true);
            jPanel3.count = 1;
            startAll.setEnabled(false);
            stopAll.setEnabled(true);
            resumeAll.setEnabled(false);
            suspendAll.setEnabled(true);
        });

//        buttonPanel.setLayout(new FlowLayout());
//        buttonPanel.add(startAll);
//        buttonPanel.add(stopAll);
//        buttonPanel.add(suspendAll);
//        buttonPanel.add(resumeAll);
//
        return buttonPanel;
    }

}
