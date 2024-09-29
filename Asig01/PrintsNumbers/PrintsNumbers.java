/*
Fecha: 17-09-24
Profesor: Lic. Álvaro Pino - Programación V
Asignación 01
Creando un hilo
Estudiante: Emanuel Avilés
Cédula: Ec-25-15187
*/

public class PrintsNumbers implements Runnable { //interface runable
    private boolean keepGoing;

    public PrintsNumbers() {
        this.keepGoing = true;
    }

    public void stopPrinting() {
        this.keepGoing = false;
    }

    @Override
    public void run() {
        int number = 1;
        while (keepGoing) {
            System.out.println(number++);
            try {
                Thread.sleep(1000); // dormir por un segundo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // restaurar estado de interrupción
            }
        }
    }
}
