/**
 * @(#)BufferTest.java
 *
 *
 *
 * @author
 * @version 1.00 2010/10/5
 *@author Modificado por MSc. Álvaro Pino Niño
 *   @version 2.0 2024/09/19
 *
 */

//Programa que implementa un buffer de mensajes:

/******************************************************************************
C:\Documents and Settings\Administrador\Mis documentos\Java 2010\Thread\
Sitios Concurrencia\N 8\Buffer de Mensajes
Clase BufferTest

Creamos un hilo productor y otro consumidor que comparten

el recurso compartido de tipo buffer de mensajes Buffer.

******************************************************************************/


import javax.swing.*;

public class BufferTest extends JFrame {

     private Thread[] thread = new Thread[2];


     public BufferTest()
     {
     	ThreadGroup g1 = new ThreadGroup("group");
         boolean done = false;

         Buffer buf = new Buffer();

              //   Productor p1 = new Productor(buf, 1);
                 thread[0] = new Thread(g1, new Productor(buf,1), "t");
                 thread[0].start();
              //   Consumidor c1 = new Consumidor(buf, 1);
                 thread[1] = new Thread(g1, new Consumidor(buf,1), "t");
                 thread[1].start();
     	while(!done)
             if (g1.activeCount() == 0)
                      done = true;
     }

      public void mensaje()
      {
      	System.out.println("\n\nTodos los hilos han terminado, fin del main() ");
      }
         public BufferTest() {

           //Adding our Frame
           JFrame f= new JFrame("Asignación05");
           //Creating objects for our Labels
           JLabel label1,label2;
           //Creating object for Sign In button
           JButton Button1;
           JButton Button2;
           JButton Button2;
           JButton Button4;
           //Creating object for our text boxes
           JTextField TextBox1,TextBox2;
           //Creating our button
           Button1=new JButton("Iniciar");
           Button2=new JButton("Parar");
           Button3=new JButton("Suspender");
           Button4=new JButton("Renaudar");
           //Creating our first Label
           label1=new JLabel("User Name:");
           //Creating our second label
           label2=new JLabel("Password:");
           //Creating our first text field
           TextBox1 = new JTextField(20);
           //Creating our second text field
           TextBox2 = new JTextField(20);
           //Setting bound for our Label1
           label1.setBounds(50,50, 100,30);
           //Setting bound for our Label2
           label2.setBounds(50,100, 100,30);
           //Setting bound for our TextBox1
           TextBox1.setBounds(180,50, 150,20);
           //Setting bound for our TextBox2
           TextBox2.setBounds(180,100, 150,20);
           //Setting bound for our Button1
           Button1.setBounds(110,150,95,30);
           //Adding our Label1,Label2,TextBox1,TextBox2,Button1 to our frame
           f.add(label1);
           f.add(label2);
           f.add(Button1);
           f.add(TextBox1);
           f.add(TextBox2);
           f.setSize(300,300);
           f.setLayout(null);
           f.setVisible(true);
}
         public static void main(String[] args) {

              new BufferTest();

         //      BufferTest bt = new BufferTest();

                 bt.mensaje();
         }
}








