
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;

public class Snake extends JFrame {

  public Snake() {

    initUI();
  }

  private void initUI() {

    //add(new Board());
    Board board = new Board();
    add(board, BorderLayout.CENTER);

    // button and score panels to the frame
    add(board.buttonPanel, BorderLayout.SOUTH);
    add(board.scorePanel, BorderLayout.NORTH);

    setResizable(false);
    pack();

    setTitle("Snake");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {

    EventQueue.invokeLater(() -> {
        JFrame ex = new Snake();
        ex.setVisible(true);
    });
  }
}
