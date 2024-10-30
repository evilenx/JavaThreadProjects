/**
 * * @author: Emanuel Avilés (Ec-25-15187)
 *   Date: 15-10-24 (sustentación)
 */
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
				Print.clearButton.setEnabled(true);
				Print.stopButton.setEnabled(false);
				Print.pauseButton.setEnabled(false);
				Print.resumeButton.setEnabled(false);
    }
}
