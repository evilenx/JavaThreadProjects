/**
* asig02
* @author Emanuel Avilés
* date: 24-09-24
*//
public class Race {
    public static void main(String[] args) {
        /**
        * comprobar si se proporciona el número correcto de argumentos
        **/
        if (args.length < 6) {
            System.out.println("Please provide five names and one finish value as command line arguments.");
            return;
        }

        /**
        * extraer el valor final
        * del último argumento de la línea de comando
        **/
        int finish;
        try {
            finish = Integer.parseInt(args[args.length - 1]);
        } catch (NumberFormatException e) {
            System.out.println("The finish value must be an integer.");
            return;
        }

        // array para contener los hilos
        Thread[] cars = new Thread[100];


        for (int i = 0; i < 5; i++) {
            cars[i] = new RaceCar(args[i], finish); //llenar el array con objetos de carreras
        }

        // iniciar cada hilo
        for (Thread car : cars) {
            car.start();
        }
    }
}
