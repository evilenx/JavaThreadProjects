/**
 * @(#)Productor.java
 *
 *
 * @author
 * @version 1.00 2010/10/5
 */

/******************************************************************************
C:\Documents and Settings\Administrador\Mis documentos\Java 2010\Thread\
Sitios Concurrencia\N 8\Buffer de Mensajes
Clase Productor

******************************************************************************/

class Productor extends Thread {

         private Buffer buffer;

         private int num;



         public Productor(Buffer b,int n) {

                 buffer = b;

                 this.num = n;

         }



         public void run() {

                 for (int i=1; i<=25; i++) {

                          buffer.escribe(i);

                          System.out.println(" - Productor " + this.num + " pone: " + i);

                          try {

                                    sleep((int)(Math.random()*1000));

                          } catch (InterruptedException e) {

                                    System.out.println("Interrupción del hilo..." );

                          }

                 }

         }

}




