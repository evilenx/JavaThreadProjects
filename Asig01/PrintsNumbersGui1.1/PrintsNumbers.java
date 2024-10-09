import javax.swing.*;

public class PrintsNumbers implements Runnable {
    private boolean keepGoing;
    private boolean paused;
    private int sleepTime;
    private JTextArea outputArea;

    public PrintsNumbers(int sleepTime, JTextArea outputArea) {
        this.keepGoing = true;
        this.paused = false;
        this.sleepTime = sleepTime;
        this.outputArea = outputArea;
    }

    public void stopPrinting() {
        this.keepGoing = false;
    }

    public void pausePrinting() {
        this.paused = true;
    }

    public void resumePrinting() {
        this.paused = false;
        synchronized (this) {
            notify(); // Notify the thread to resume
        }
    }

    @Override
    public void run() {
        int number = 1;
        while (keepGoing) {
            if (number > 10) {
                stopPrinting();
                outputArea.append("Thread stopped.\n");
            } else {
                outputArea.append(number++ + "\n");
                try {
                    Thread.sleep(sleepTime);
                    while (paused) { // Pause the thread if paused flag is true
                        synchronized (this) {
                            wait(); // Wait until notified to resume
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interruption status
                }
            }
        }
    }
}






//import javax.swing.*;
//
//public class PrintsNumbers implements Runnable {
//  private boolean keepGoing;
//  private int sleepTime;
//  private JTextArea outputArea;
//
//  public PrintsNumbers(int sleepTime, JTextArea outputArea) {
//    this.keepGoing = true;
//    this.sleepTime = sleepTime;
//    this.outputArea = outputArea;
//  }
//
//  public void stopPrinting() {
//    this.keepGoing = false;
//  }
//
//  @Override
//    public void run() {
//      int number = 1;
//      while (keepGoing) {
//        if (number > 10)
//        {/*Si el numero ya se pasa de 10, debes detener el hilo*/
//          stopPrinting();
//          outputArea.append("Thread.interrupt();\n");
//        }
//        else {
//          /*
//           * Si el numero no es mayor de diez, sumarle uno a number y duermes el hilo
//           * Imprimir en el JTextArea
//           */
//          outputArea.append(number++ + "\n");
//          try
//          {/*Dormir por el tiempo especificado*/
//            Thread.sleep(sleepTime);
//          }
//          catch (InterruptedException e) {
//            Thread.currentThread().interrupt(); // restaurar estado de interrupción
//          }
//        }
//      }
//    }
//}
