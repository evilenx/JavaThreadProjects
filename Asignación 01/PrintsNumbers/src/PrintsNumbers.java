public class PrintsNumbers implements Runnable{
    int sleepTime;
    boolean keepGoing;

    //constructor
    PrintsNumbers(int sleepTime) {
        this.sleepTime = sleepTime;
        this.textArea= textArea; //variable que acabo de insertar
        keepGoing = true;
    }

    //constructor
    public void stopPrinting() {
        keepGoing = false;
        System.out.println("main is ending");
    }

    /*
    //run que cosas que uso en el hilo
    @Override
    public void run() {
        int number = 1;
        while (keepGoing) {
            System.out.println(number);
            number++;
            try{
                //el hilo duerme por un segundo
               Thread.sleep(1000);
            }catch (InterruptedException e){
               Thread.currentThread().interrupt();
               System.out.println("Hilo interrumpido");
            }
        }
    }
    */



		@Override
			public void run() {
				int number = 1;
				while (keepGoing) {
					// Añade el número al JTextArea
					SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
							textArea.append(String.valueOf(number) + "\n");
							}
							});
					number++;
					try {
						// El hilo duerme por el tiempo especificado
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						System.out.println("Hilo interrumpido");
					}
				}
			}
}


