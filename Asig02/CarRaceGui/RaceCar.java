import java.util.Random;

class RaceCar extends Thread {
    private int finish;
    private String name;
    private Random random = new Random();

    public RaceCar(String name, int finish) {
        this.name = name;
        this.finish = finish;
    }

    @Override
    public void run() {
        for (int i = 1; i <= finish; i++) {
            System.out.println(name + ": " + i);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(name + " interrumpido.");
            }
        }
        System.out.println(name + " finalizado");
    }
}
