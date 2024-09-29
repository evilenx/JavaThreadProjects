/*
Fecha: 17-09-24
Profesor: Lic. Álvaro Pino - Programación V
Asignación 01
Creando un hilo
Estudiante: Emanuel Avilés
Cédula: Ec-25-15187
*/
public class Print {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("introducir un argumento en milisegundos.");
            return;
        }

        int sleepTime;
        try {
            sleepTime = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("El argumento debe ser un número entero.");
            return;
        }

        PrintsNumbers printsNumbers = new PrintsNumbers();
        Thread thread = new Thread(printsNumbers);
        thread.start();

        try {
            Thread.sleep(sleepTime); // dormir el hilo main por el tiempo especificado
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restaurar estado de interrupción
        }

        printsNumbers.stopPrinting(); // detener la impresión
        System.out.println("main() is ending…");
    }
}
