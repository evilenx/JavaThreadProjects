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

public class BufferTest {

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
         public static void main(String[] args) {

                 BufferTest bt = new BufferTest();
                 
                 bt.mensaje();
                
              
     
         }

}








