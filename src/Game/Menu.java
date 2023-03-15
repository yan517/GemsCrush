//Author: Lam Yan Yan Cindy, Tse Wai To

package Game;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author yan
 */
public class Menu extends JPanel {

    private static final long serialVersionUID = 1L;

    JButton playButton;
    JButton exitButton;
    JButton loadButton;
    JLabel background;
    static BufferedImage img;
    JFrame frame;

    Game main;

    boolean loadgame = false;
    int time = -1;

    public Menu(Game main) {
        this.main = main;
        frame = new JFrame("Gems Crush");

        playButton = new JButton("Play");
        loadButton = new JButton("Load");
        exitButton = new JButton("Exit");
        GameStart game = new GameStart();
        GridBagConstraints c = new GridBagConstraints();
        game.setLayout(new GridBagLayout());
        frame.add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 1;
        game.add(playButton);
        game.add(loadButton, c);
        c.gridx = 0;
        c.gridy = 2;
        game.add(exitButton, c);
        frame.setContentPane(game);

        ButtonListener listener = new ButtonListener();
        playButton.addActionListener(listener);
        exitButton.addActionListener(listener);
        loadButton.addActionListener(listener);

        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        MainMenu();
    }

    public void MainMenu() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (main.getState() == Game.STATE.GAME) {
                        frame.setVisible(false);
                        frame.dispose();
                        main.startGame();
                        break;
                    } else if (loadgame == true) {
                        frame.setVisible(false);
                        frame.dispose();
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        t.start();

    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String name = e.getActionCommand();
            if (name.equals("Play")) {
                System.out.println("Play");
                main.setState(Game.STATE.GAME);

            } else if (name.equals("Exit")) {
                System.exit(0);
            } else if (name.equals("Load")) {
                final SaveLoad SL = new SaveLoad();
                System.out.println("Load");

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String user = JOptionPane.showInputDialog(null, "Enter your name: ");
                        System.out.println(user);
                        if (user != "" && user != null) {
                            loadgame = SL.Load(user);
                            time = SL.getTime();
                            if (time != 0) {
                                main.startGame(SL.getDifGem(), SL.getTime(), SL.getScore(), SL.getS1(), SL.getS2(), SL.getSet());
                            }
                            System.out.println("hi");
                        } else {

                        }
                    }

                });
                t.start();

            }
        }
    }

    private class GameStart extends JPanel {

        public GameStart() {
            setPreferredSize(new Dimension(800, 600));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(new ImageIcon(this.getClass().getResource("/assets/moomin_valley_008.jpg")).getImage(), 0, 0, this);

        }
    }
}
