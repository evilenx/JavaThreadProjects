/**
 * @(#)Buffer.java
 *
 *
 * @author 
 * @version 1.00 2010/10/5
 */

/******************************************************************************
C:\Documents and Settings\Administrador\Mis documentos\Java 2010\Thread\
Sitios Concurrencia\N 8\Buffer de Mensajes
Clase Buffer

Recurso compartido por los procesos. Sección Crítica.

******************************************************************************/

class Buffer {

         private int capacidad = 10;

         private int pila[] = new int[capacidad];

         private int puntero = -1;

         private boolean estaLleno = false;

         private boolean estaVacio = true;


         public synchronized int lee() {

                 System.out.println("LEE - ptro: "+puntero+" - vacio: "+estaVacio );

                 while(estaVacio) {

                          try {

                                    wait();

                          } catch (InterruptedException e) {

                                    System.out.println("Interrupción del hilo..." );

                          }

                 }

                 int num = pila[puntero];

                 pila[puntero]=0;

                 puntero--;

                 if (puntero < 0) estaVacio = true;

                 estaLleno = false;

                 System.out.print("\n-- Pila:");

      for (int i=0;i<capacidad;i++) System.out.print(" "+pila[i]);

                 notify();

                 return num;

         }












         public synchronized void escribe(int num) {

                 System.out.println("ESCRIBE - ptro: "+puntero+" - lleno: "+estaLleno );

                 while(estaLleno) {

                          try {

                                    wait();

                          } catch (InterruptedException e) {

                                    System.out.println("Interrupción del hilo..." );

                          }

                 }

                 puntero++;

                 pila[puntero]=num;

                 if (puntero == capacidad-1) estaLleno = true;

                 estaVacio = false;

                 System.out.print("\n-- Pila:");

                 for (int i=0;i<capacidad;i++) System.out.print(" "+pila[i]);

                 notify();

         }

}
