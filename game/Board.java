/*
** autor: Emanuel Avilés
*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;



public class Board extends JPanel implements ActionListener {

  private final int B_WIDTH = 300;
  private final int B_HEIGHT = 300;
  private final int DOT_SIZE = 10;
  private final int ALL_DOTS = 900;
  private final int RAND_POS = 29;
  private final int DELAY = 140;

  private final int x[] = new int[ALL_DOTS];
  private final int y[] = new int[ALL_DOTS];

  private int dots;
  private int apple_x;
  private int apple_y;

  private boolean leftDirection = false;
  private boolean rightDirection = true;
  private boolean upDirection = false;
  private boolean downDirection = false;
  private boolean inGame = true;

  private Timer timer;
  private Image ball;
  private Image apple;
  private Image head;


  //buttons
  private JButton retryButton;
  private JButton pauseButton;
  private JButton resumeButton;
  private boolean paused = false;


  private int score = 0;


   public JPanel buttonPanel;
   public JPanel scorePanel;
   public JLabel scoreLabel;


  private Thread inGameThread;

  public Board() {

    initBoard();
  }

  // Extending The Thread class
  //    private class GameThread extends Thread {
  //      @Override
  //        public void run() {
  //          while (inGame) {
  //            checkApple();
  //            checkCollision();
  //            move();
  //            repaint();
  //            try {
  //              Thread.sleep(DELAY); //
  //            } catch (InterruptedException e) {
  //              Thread.currentThread().interrupt();
  //            }
  //          }
  //        }
  //    }
  //
  private void initBoard() {

    addKeyListener(new TAdapter());
    setBackground(Color.black);
    setFocusable(true);

    setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
    loadImages();
    initGame();

    //GameThread gameThread = new GameThread();
    //gameThread.start();


    /*
    * Create a panel for buttons
    */
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());



    retryButton = new JButton("Retry");
    retryButton.setBackground(Color.WHITE);
    retryButton.setVisible(false);
    retryButton.addActionListener(new RetryButtonListener());
    buttonPanel.add(retryButton);

    pauseButton = new JButton("Pause");
    pauseButton.setBackground(Color.WHITE);
    pauseButton.addActionListener(new PauseButtonListener());
    buttonPanel.add(pauseButton);

    resumeButton = new JButton("Resume");
    resumeButton.setBackground(Color.WHITE);
    resumeButton.setVisible(false);
    resumeButton.addActionListener(new ResumeButtonListener());
    buttonPanel.add(resumeButton);


    // Create a panel for score
    scorePanel = new JPanel();
    scorePanel.setLayout(new FlowLayout());
    scoreLabel = new JLabel("hola bob");
    scorePanel.add(scoreLabel);

    //add panels to the main frame
    //setLayout(new BorderLayout());
    //add(this, BorderLayout.CENTER); //this is the game panel
    //add(buttonPanel, BorderLayout.SOUTH);
    //add(scorePanel, BorderLayout.NORTH);



    // Start the game Thread
    inGameThread = new Thread(new GameRunnable());
    inGameThread.start();
  }


  private class GameRunnable implements Runnable {
    @Override
      public void run() {
        while (inGame) {
          if (!paused){
          checkApple();
          checkCollision();
          move();
          repaint();
          }
          try {
            Thread.sleep(DELAY); // Use the existing DELAY variable
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
      }
  }


  private void loadImages() {

    ImageIcon iid = new ImageIcon("src/resources/dot.png");
    ball = iid.getImage();

    ImageIcon iia = new ImageIcon("src/resources/apple.png");
    apple = iia.getImage();

    ImageIcon iih = new ImageIcon("src/resources/head.png");
    head = iih.getImage();
  }

  private void initGame() {

    dots = 3;

    for (int z = 0; z < dots; z++) {
      x[z] = 50 - z * 10;
      y[z] = 50;
    }

    locateApple();

    timer = new Timer(DELAY, this);
    timer.start();
  }

  @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);

      doDrawing(g);
    }

  private void doDrawing(Graphics g) {

    if (inGame) {

      g.drawImage(apple, apple_x, apple_y, this);

      for (int z = 0; z < dots; z++) {
        if (z == 0) {
          g.drawImage(head, x[z], y[z], this);
        } else {
          g.drawImage(ball, x[z], y[z], this);
        }
      }

      //Update the score label
      scoreLabel.setText("Score: " + score);
      // Display the score
 //     g.setColor(Color.white);
  //    g.setFont(new Font("Helvetica", Font.BOLD, 14));
   //   g.drawString("Score: " + score, 10, 20);


      Toolkit.getDefaultToolkit().sync();

    } else {

      gameOver(g);
    }
  }

  private void gameOver(Graphics g) {

    String msg = "Game Over";
    Font small = new Font("Helvetica", Font.BOLD, 14);
    FontMetrics metr = getFontMetrics(small);

    g.setColor(Color.white);
    g.setFont(small);
    g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);

    g.drawString("Final Score: " + score, (B_WIDTH - metr.stringWidth("Final Score: " + score)) / 2, B_HEIGHT / 2 + 20);
    score = 0;

    retryButton.setVisible(true);
    pauseButton.setVisible(false);
    resumeButton.setVisible(false);
  }





  private void checkApple() {

    if ((x[0] == apple_x) && (y[0] == apple_y)) {

      dots++;
      score++;
      locateApple();
    }
  }

  private void move() {

    for (int z = dots; z > 0; z--) {
      x[z] = x[(z - 1)];
      y[z] = y[(z - 1)];
    }

    if (leftDirection) {
      x[0] -= DOT_SIZE;
    }

    if (rightDirection) {
      x[0] += DOT_SIZE;
    }

    if (upDirection) {
      y[0] -= DOT_SIZE;
    }

    if (downDirection) {
      y[0] += DOT_SIZE;
    }
  }

  private void checkCollision() {


    for (int z = dots; z > 0; z--) {

      if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
        inGame = false;
      }
    }

    if (y[0] > B_HEIGHT - 12) {
      inGame = false;
    }

    if (y[0] < 10) {
      inGame = false;
    }

    if (x[0] > B_WIDTH - 12) { //20
      inGame = false;
    }

    if (x[0] < 10) { //0
      inGame = false;
    }

    if (!inGame) {
      timer.stop();
    }
  }

  private void locateApple() {

    int r = (int) (Math.random() * RAND_POS);
    apple_x = ((r * DOT_SIZE));

    r = (int) (Math.random() * RAND_POS);
    apple_y = ((r * DOT_SIZE));
  }

  @Override
    public void actionPerformed(ActionEvent e) {

      if (inGame) {

        checkApple();
        checkCollision();
        move();
      }

      repaint();
    }

  private class TAdapter extends KeyAdapter {

    @Override
      public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_H || key == KeyEvent.VK_LEFT) && (!rightDirection)) { //LEFT
          leftDirection = true;
          upDirection = false;
          downDirection = false;
        }

        if ((key == KeyEvent.VK_L || key == KeyEvent.VK_RIGHT) && (!leftDirection)) { // RIGHT
          rightDirection = true;
          upDirection = false;
          downDirection = false;
        }

        if ((key == KeyEvent.VK_K || key == KeyEvent.VK_UP) && (!downDirection)) { //UP
          upDirection = true;
          rightDirection = false;
          leftDirection = false;
        }

        if ((key == KeyEvent.VK_J || key == KeyEvent.VK_DOWN) && (!upDirection)) { //DOWN
          downDirection = true;
          rightDirection = false;
          leftDirection = false;
        }
      }
  }

  private class PauseButtonListener implements ActionListener {
    @Override
      public void actionPerformed(ActionEvent e) {
        paused = true;
        pauseButton.setVisible(false);
        resumeButton.setVisible(true);
      }
  }

  private class ResumeButtonListener implements ActionListener {
    @Override
      public void actionPerformed(ActionEvent e) {
        paused = false;
        pauseButton.setVisible(true);
        resumeButton.setVisible(false);
      }
  }
  private class RetryButtonListener implements ActionListener {
    @Override
      public void actionPerformed(ActionEvent e) {
        retryGame();
      }
  }

  private void retryGame() {
    inGame = true;
    dots = 3;
    for (int z = 0; z < dots; z++) {
      x[z] = 50 - z * 10;
      y[z] = 50;
    }
    leftDirection = false;
    rightDirection = true;
    upDirection = false;
    downDirection = false;
    locateApple();
    retryButton.setVisible(false);
    paused = false;
    pauseButton.setVisible(true);
    resumeButton.setVisible(false);
    repaint();

    if (inGameThread != null && !inGameThread.isAlive()) {
      inGameThread = new Thread(new GameRunnable());
      inGameThread.start();
    }
  }
}
