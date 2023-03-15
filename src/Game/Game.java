//Author: Lam Yan Yan Cindy, Tse Wai To

package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class Game extends JPanel {

    static int score = 0;
    int Limit = 0;
    static int time = 100;
    public final String TITLE = "Gems Crush";
    GameStart gameStart;
    JFrame frame = new JFrame(TITLE);
    static boolean ready = true;
    public static Gem[] difGem = new Gem[64];
    static boolean end = false;
    Random ran = new Random();
    static long saveT = 0;
    public STATE State = STATE.MEMU;
    static int set = 1;
    public static previewImage PI = new previewImage();
    public static int s1 = PI.preview(set);
    public static int s2 = PI.preview(set);
    static double fps = 0;
    static boolean canTouch = false;

    Sound bg;
    Sound fall;
    Sound match;
    Sound select;
    Sound change;

    public enum STATE {
        MEMU,
        GAME
    };

    public Game() {
        if (State == State.MEMU) {
            new Menu(this);
        }
    }

    public void startGame(Gem[] difGem_, int time_, int score_, int s1_, int s2_, int set_) {
        System.out.println("Load");
        time = time_;
        score = score_;
        s1 = s1_;
        s2 = s2_;
        difGem = difGem_;
        set = set_;

        frame.add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        bg = new Sound("src/assets/Moomin.wav");
        bg.loop();
        fall = new Sound("src/assets/fall.wav");
        match = new Sound("src/assets/match.wav");
        select = new Sound("src/assets/select.wav");
        change = new Sound("src/assets/change.wav");
        gameStart = new GameStart();
        gameStart.setFocusable(true);
        frame.setContentPane(gameStart);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        while (!end) {
            gameStart.loop();
        }
        gameStart.gameOver();
    }

    public STATE getState() {
        return State;
    }

    public void setState(STATE State) {
        this.State = State;
    }

    public static void main(String[] args) {
        Game main = new Game();
    }

    public void startGame() {

        System.out.println("New Game");
        frame.add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        bg = new Sound("src/assets/Moomin.wav");
        bg.loop();
        fall = new Sound("src/assets/fall.wav");
        match = new Sound("src/assets/match.wav");
        select = new Sound("src/assets/select.wav");
        change = new Sound("src/assets/change.wav");
        int count = 0;
        String gemName = "";
        int i = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                i = ran.nextInt(7);
                if (i == 0) {
                    gemName = "/assets/01.png";
                } else if (i == 1) {
                    gemName = "/assets/02.png";
                } else if (i == 2) {
                    gemName = "/assets/03.png";
                } else if (i == 3) {
                    gemName = "/assets/04.png";
                } else if (i == 4) {
                    gemName = "/assets/05.png";
                } else if (i == 5) {
                    gemName = "/assets/06.png";
                } else if (i == 6) {
                    gemName = "/assets/07.png";
                }
                difGem[count] = new Gem(gemName, x, y);
                count++;
            }
        }
        gameStart = new GameStart();
        gameStart.setFocusable(true);
        frame.setContentPane(gameStart);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        while (!end) {
            gameStart.loop();
        }
        gameStart.gameOver();

    }

    private class GameStart extends JPanel {

        boolean needCheck = false;
        private long ts = System.currentTimeMillis();
        int WAIT = 1000;
        int save1 = -1;
        int save2 = -1;
        int countGem = 0;
        int start = 1;
        int st = 1;
        boolean find = false;
        double saveCoordinateX = 0.0;
        int preSaveCoordinateX = 0;
        int preSaveCoordinateY = 0;
        double saveCoordinateY = 0.0;
        boolean a = true;
        private boolean delay = false;

        public GameStart() {
            setPreferredSize(new Dimension(800, 600));
            this.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    final SaveLoad SL = new SaveLoad();
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_S: {
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String user = JOptionPane.showInputDialog(null, "Enter your name: ");
                                    SL.Save(user + ".txt", difGem, time, score, s1, s2, set);
                                    System.out.println("Saved");
                                }
                            });
                            t.start();
                            break;
                        }
                        case KeyEvent.VK_L: {
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String user = JOptionPane.showInputDialog(null, "Enter your name: ");
                                    if (user != null && user != "") {
                                        SL.Load(user);
                                        int t = SL.getTime();
                                        if (t != 0) {
                                            time = t;
                                            score = SL.getScore();
                                            s1 = SL.getS1();
                                            s2 = SL.getS2();
                                            difGem = SL.getDifGem();
                                            set = SL.getSet();
                                        }
                                        System.out.println("Load");
                                    }
                                }

                            });
                            t.start();
                            break;
                        }
                        case KeyEvent.VK_2: {
                            String file = "";
                            if (set == 1) {
                                set = 2;
                                for (int x = 0; x < 64; x++) {
                                    switch (difGem[x].getfileName()) {
                                        case "/assets/01.png":
                                            file = "/assets/gemBlue.png";
                                            break;
                                        case "/assets/02.png":
                                            file = "/assets/gemGreen.png";
                                            break;
                                        case "/assets/03.png":
                                            file = "/assets/gemOrange.png";
                                            break;
                                        case "/assets/04.png":
                                            file = "/assets/gemPurple.png";
                                            break;
                                        case "/assets/05.png":
                                            file = "/assets/gemRed.png";
                                            break;
                                        case "/assets/06.png":
                                            file = "/assets/gemWhite.png";
                                            break;
                                        case "/assets/07.png":
                                            file = "/assets/gemYellow.png";
                                            break;
                                    }
                                    difGem[x].setfileName(file);
                                    difGem[x].setP(file);
                                }
                            }
                            break;
                        }
                        case KeyEvent.VK_1: {
                            String file = "";
                            if (set != 1) {
                                set = 1;
                                for (int x = 0; x < 64; x++) {
                                    switch (difGem[x].getfileName()) {
                                        case "/assets/gemBlue.png":
                                            file = "/assets/01.png";
                                            break;
                                        case "/assets/gemGreen.png":
                                            file = "/assets/02.png";
                                            break;
                                        case "/assets/gemOrange.png":
                                            file = "/assets/03.png";
                                            break;
                                        case "/assets/gemPurple.png":
                                            file = "/assets/04.png";
                                            break;
                                        case "/assets/gemRed.png":
                                            file = "/assets/05.png";
                                            break;
                                        case "/assets/gemWhite.png":
                                            file = "/assets/06.png";
                                            break;
                                        case "/assets/gemYellow.png":
                                            file = "/assets/07.png";
                                            break;
                                    }
                                    difGem[x].setfileName(file);
                                    difGem[x].setP(file);
                                }
                            }
                            break;
                        }
                    }
                }
            });

            MouseInputAdapter mia = new MouseInputAdapter() {

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (canTouch == false) {
                        return;
                    }
                    if (ready == true) {
                        Limit = 0;
                        mouseReleased(e);
                    }

                    ready = false;

                    if (!end && canTouch) {
                        Point pt = e.getPoint();

                        if ((pt.getX() > 240 && pt.getX() < 760) && (pt.getY() > 40 && pt.getY() < 560)) {
                            int c = 0;
                            saveCoordinateX = (int) ((pt.getX() - 240) / 65);
                            saveCoordinateY = (int) ((pt.getY() - 40) / 65);
                            if (a) {
                                preSaveCoordinateX = (int) saveCoordinateX;
                                preSaveCoordinateY = (int) saveCoordinateY;
                                a = false;

                            }

                            for (int q = 0; q < 8; q++) {
                                for (int w = 0; w < 8; w++) {
                                    if (pt != null) {
                                        //System.out.println("point  " + e.getX());
                                        if (difGem[c].isAt(pt)) {

                                            System.out.println("Mouse Entered: " + c);

                                            if (((int) (pt.getX() - 240) / 65 > (preSaveCoordinateX)) && ((int) (pt.getY() - 40) / 65 > (preSaveCoordinateY))) {
                                                change = new Sound("src/assets/change.wav");
                                                change.play();
                                                swap(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY], difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 9]);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].setPosX_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].getPosX_() + 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 9].setPosX_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 9].getPosX_() - 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].setPosY_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].getPosY_() + 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 9].setPosY_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 9].getPosY_() - 65);
                                                if ((preSaveCoordinateX * 8 + preSaveCoordinateY + 9) < 64) {
                                                    preSaveCoordinateX = (int) ((difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 9].getPosX()));
                                                    preSaveCoordinateY = (int) ((difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 9].getPosY()));
                                                }

                                                repaint();
                                            } else if (((int) (pt.getX() - 240) / 65 < (preSaveCoordinateX)) && ((int) (pt.getY() - 40) / 65 < (preSaveCoordinateY))) {
                                                change = new Sound("src/assets/change.wav");
                                                change.play();
                                                swap(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY], difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 9]);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].setPosX_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].getPosX_() - 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 9].setPosX_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 9].getPosX_() + 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].setPosY_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].getPosY_() - 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 9].setPosY_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 9].getPosY_() + 65);
                                                if ((preSaveCoordinateX * 8 + preSaveCoordinateY - 9) < 64) {
                                                    preSaveCoordinateX = (int) ((difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 9].getPosX()));
                                                    preSaveCoordinateY = (int) ((difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 9].getPosY()));
                                                }

                                                repaint();
                                            } else if (((int) (pt.getX() - 240) / 65 < (preSaveCoordinateX)) && ((int) (pt.getY() - 40) / 65 > (preSaveCoordinateY))) {
                                                change = new Sound("src/assets/change.wav");
                                                change.play();
                                                swap(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY], difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 7]);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].setPosX_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].getPosX_() - 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 7].setPosX_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 7].getPosX_() + 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].setPosY_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].getPosY_() + 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 7].setPosY_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 7].getPosY_() - 65);
                                                if ((preSaveCoordinateX * 8 + preSaveCoordinateY - 7) < 64) {
                                                    preSaveCoordinateX = (int) ((difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 7].getPosX()));
                                                    preSaveCoordinateY = (int) ((difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 7].getPosY()));
                                                }

                                                repaint();
                                            } else if (((int) (pt.getX() - 240) / 65 > (preSaveCoordinateX)) && ((int) (pt.getY() - 40) / 65 < (preSaveCoordinateY))) {
                                                change = new Sound("src/assets/change.wav");
                                                change.play();
                                                swap(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY], difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 7]);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].setPosX_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].getPosX_() + 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 7].setPosX_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 7].getPosX_() - 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].setPosY_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].getPosY_() - 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 7].setPosY_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 7].getPosY_() + 65);
                                                if ((preSaveCoordinateX * 8 + preSaveCoordinateY + 7) < 64) {
                                                    preSaveCoordinateX = (int) ((difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 7].getPosX()));
                                                    preSaveCoordinateY = (int) ((difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 7].getPosY()));
                                                }
                                                repaint();
                                            } else if ((int) (pt.getY() - 40) / 65 > (preSaveCoordinateY)) {
                                                change = new Sound("src/assets/change.wav");
                                                change.play();
                                                swap(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY], difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 1]);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].setPosY_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].getPosY_() + 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 1].setPosY_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 1].getPosY_() - 65);
                                                preSaveCoordinateY = (int) ((difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 1].getPosY()));
                                                repaint();
                                            } else if ((int) (pt.getY() - 40) / 65 < (preSaveCoordinateY)) {
                                                change = new Sound("src/assets/change.wav");
                                                change.play();
                                                swap(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY], difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 1]);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].setPosY_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].getPosY_() - 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 1].setPosY_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 1].getPosY_() + 65);
                                                preSaveCoordinateY = (int) ((difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 1].getPosY()));
                                                repaint();
                                            } else if ((int) (pt.getX() - 240) / 65 > (preSaveCoordinateX)) {
                                                change = new Sound("src/assets/change.wav");
                                                change.play();
                                                swap(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY], difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 8]);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].setPosX_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].getPosX_() + 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 8].setPosX_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 8].getPosX_() - 65);
                                                preSaveCoordinateX = (int) ((difGem[preSaveCoordinateX * 8 + preSaveCoordinateY + 8].getPosX()));

                                                repaint();

                                            } else if ((int) (pt.getX() - 240) / 65 < (preSaveCoordinateX)) {
                                                change = new Sound("src/assets/change.wav");
                                                change.play();
                                                swap(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY], difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 8]);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].setPosX_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY].getPosX_() - 65);
                                                difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 8].setPosX_(difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 8].getPosX_() + 65);
                                                preSaveCoordinateX = (int) ((difGem[preSaveCoordinateX * 8 + preSaveCoordinateY - 8].getPosX()));

                                                repaint();
                                            }
                                        }
                                        c++;
                                    }

                                }

                            }
                        } else {
                            saveCoordinateX = 0.0;
                            preSaveCoordinateX = 0;
                            preSaveCoordinateY = 0;
                            saveCoordinateY = 0.0;
                            a = true;
                            canTouch = false;
                            ready = true;
                            mouseClicked(e);
                        }
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!end) {
                        System.out.println("Start");
                        int c = 0;
                        Point point = e.getPoint();
                        for (int q = 0; q < 8; q++) {
                            for (int w = 0; w < 8; w++) {
                                if (point != null) {
                                    if (difGem[c].isAt(point)) {
                                        difGem[c].toggleFocus();
                                        repaint();
                                        select.play();
                                        if (save1 == -1) {
                                            save1 = c;
                                        } else if (c == save1 + 8 || c == save1 + 7 || c == save1 + 9 || c == save1 + 1 || c == save1 - 1 || c == save1 - 7 || c == save1 - 8 || c == save1 - 9) {
                                            if ((difGem[save1].getPosX() > difGem[c].getPosX()) && (difGem[save1].getPosY() > difGem[c].getPosY())) {
                                                swap(difGem[save1], difGem[c]);
                                                difGem[save1].setPosX_(difGem[save1].getPosX_() - 65);
                                                difGem[c].setPosX_(difGem[c].getPosX_() + 65);
                                                difGem[save1].setPosY_(difGem[save1].getPosY_() - 65);
                                                difGem[c].setPosY_(difGem[c].getPosY_() + 65);

                                            } else if ((difGem[save1].getPosX() > difGem[c].getPosX()) && (difGem[save1].getPosY() < difGem[c].getPosY())) {
                                                swap(difGem[save1], difGem[c]);
                                                difGem[save1].setPosX_(difGem[save1].getPosX_() - 65);
                                                difGem[c].setPosX_(difGem[c].getPosX_() + 65);
                                                difGem[save1].setPosY_(difGem[save1].getPosY_() + 65);
                                                difGem[c].setPosY_(difGem[c].getPosY_() - 65);

                                            } else if ((difGem[save1].getPosX() < difGem[c].getPosX()) && (difGem[save1].getPosY() < difGem[c].getPosY())) {
                                                swap(difGem[save1], difGem[c]);
                                                difGem[save1].setPosX_(difGem[save1].getPosX_() + 65);
                                                difGem[c].setPosX_(difGem[c].getPosX_() - 65);
                                                difGem[save1].setPosY_(difGem[save1].getPosY_() + 65);
                                                difGem[c].setPosY_(difGem[c].getPosY_() - 65);

                                            } else if ((difGem[save1].getPosX() < difGem[c].getPosX()) && (difGem[save1].getPosY() > difGem[c].getPosY())) {
                                                swap(difGem[save1], difGem[c]);
                                                difGem[save1].setPosX_(difGem[save1].getPosX_() + 65);
                                                difGem[c].setPosX_(difGem[c].getPosX_() - 65);
                                                difGem[save1].setPosY_(difGem[save1].getPosY_() - 65);
                                                difGem[c].setPosY_(difGem[c].getPosY_() + 65);

                                            } else if (difGem[save1].getPosX() > difGem[c].getPosX()) {
                                                swap(difGem[save1], difGem[c]);
                                                difGem[save1].setPosX_(difGem[save1].getPosX_() - 65);
                                                difGem[c].setPosX_(difGem[c].getPosX_() + 65);

                                            } else if (difGem[save1].getPosX() < difGem[c].getPosX()) {
                                                swap(difGem[save1], difGem[c]);
                                                difGem[save1].setPosX_(difGem[save1].getPosX_() + 65);
                                                difGem[c].setPosX_(difGem[c].getPosX_() - 65);

                                            } else if (difGem[save1].getPosY() > difGem[c].getPosY()) {
                                                swap(difGem[save1], difGem[c]);
                                                difGem[save1].setPosY_(difGem[save1].getPosY_() - 65);
                                                difGem[c].setPosY_(difGem[c].getPosY_() + 65);
                                            } else if (difGem[save1].getPosY() < difGem[c].getPosY()) {
                                                swap(difGem[save1], difGem[c]);
                                                difGem[save1].setPosY_(difGem[save1].getPosY_() + 65);
                                                difGem[c].setPosY_(difGem[c].getPosY_() - 65);
                                            }

                                            repaint();
                                            save1 = -1;

                                        } else {
                                            difGem[save1].toggleFocus();
                                            difGem[c].toggleFocus();
                                            repaint();
                                            save1 = -1;
                                        }
                                    }
                                    c++;

                                }
                            }
                        }
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    saveCoordinateX = 0.0;
                    preSaveCoordinateX = 0;
                    preSaveCoordinateY = 0;
                    saveCoordinateY = 0.0;
                    a = true;
                    ready = true;
                    return;
                }

            };

            this.addMouseMotionListener(mia);
            this.addMouseListener(mia);

            repaint();
        }

        public void swap(Gem a, Gem b) {
            double x = a.getPosX();
            double y = a.getPosY();
            a.setPosX(b.getPosX());
            a.setPosY(b.getPosY());
            b.setPosX(x);
            b.setPosY(y);
            a.setSelected(false);
            b.setSelected(false);
            difGem[(int) (a.getPosX() * 8 + a.getPosY())] = a;
            difGem[(int) (b.getPosX() * 8 + b.getPosY())] = b;
        }

        public void findMatch() {
            int count = 1;
            int colorball_x = 0;
            int colorball_y = 0;
            boolean check = false;
            for (int a = 0; a < 64; a++) {
                if (difGem[a].getfileName() == "/assets/10.png") {
                    idle(500);
                    difGem[a].match(false);
                    check = true;

                    int posx = (int) difGem[a].getPosX();
                    int posy = (int) difGem[a].getPosY();
                    for (int b = (posx * 8 + posy) + 8; b < 64; b = b + 8) {
                        difGem[b].match(false);
                    }
                    for (int c = (posx * 8 + posy) - 8; c >= 0; c = c - 8) {
                        difGem[c].match(false);
                    }
                    for (int d = (posx * 8 + posy) + 1; d < posx * 8 + 8; d++) {
                        difGem[d].match(false);
                    }
                    for (int e = (posx * 8 + posy) - 1; e >= posx * 8; e--) {
                        difGem[e].match(false);
                    }
                    break;
                }
            }
            if (!check) {
                for (int y = 0; y < 8; y++) {
                    count = 1;
                    for (int k = y + 8; k < 64; k += 8) {
                        if (difGem[k - 8].getPic() == difGem[k].getPic()) {
                            count++;
                            if (count == 3) {
                                colorball_x = 3;
                                difGem[k - 16].match(difGem[k - 16].isMatch());
                                difGem[k - 8].match(difGem[k - 8].isMatch());
                                difGem[k].match(difGem[k].isMatch());

                                match.play();
                            } else if (count > 3) {
                                colorball_x++;
                                difGem[k].match(difGem[k].isMatch());
                                match.play();
                            }
                        } else {
                            count = 1;
                        }
                    }
                }
                for (int g = 0; g < 8; g++) {
                    count = 1;
                    for (int k = g * 8 + 1; k < ((g + 1) * 8); k++) {
                        if (difGem[k].getPic() == difGem[k - 1].getPic()) {
                            count++;
                            if (count == 3) {
                                colorball_y = 3;
                                for (int x = k; x > k - 3; x--) {
                                    if (difGem[x].isMatch()) {
                                        difGem[x].match(difGem[x].isMatch());
                                    }
                                }
                                match.play();
                                difGem[k].match(difGem[k].isMatch());
                                difGem[k - 1].match(difGem[k - 1].isMatch());
                                difGem[k - 2].match(difGem[k - 2].isMatch());
                            } else if (count > 3) {
                                colorball_y++;
                                if (!difGem[k].isMatch()) {
                                    difGem[k].match(difGem[k].isMatch());
                                }
                                match.play();
                            }
                        } else {
                            count = 1;
                        }
                    }
                }
                if (colorball_x + colorball_y > 7) {
                    s1 = 10;
                }
            }
        }

        public void fulfillGem() {

            int countNull = 0;
            int save = 0;
            for (int x = 0; x < 8; x++) {
                countNull = 0;
                for (int i = x * 8 + 7; i >= x * 8; i--) {
                    countNull = 0;
                    if (difGem[i].getPic() == null) {
                        while (i - 1 >= x * 8) {
                            swap(difGem[i], difGem[i - 1]);
                            i--;
                        }
                        break;
                    }
                }
            }
            for (int k = 0; k < 8; k++) {
                if (difGem[k * 8].getPic() == (null)) {

                    difGem[k * 8].respam1(s1, set);
                    difGem[k * 8].setfileName(s1, set);
                    s1 = s2;
                    s2 = PI.preview(set);
                    fall.play();
                    repaint();
                    System.out.println(s1 + " " + s2);
                }

            }
        }

        public void loop() {
            fps = Math.round(1000.00 / (System.currentTimeMillis() - saveT) * 100.0) / 100.0;

            saveT = System.currentTimeMillis();
            if (System.currentTimeMillis() - ts >= WAIT) {
                time--;
                Limit++;
                if (Limit == 10) {
                    ready = true;
                }
                ts = System.currentTimeMillis();
            }

            canTouch = false;
            boolean containnull = false;
            for (int t = 0; t < 64; t++) {
                if (difGem[t].getPic() == null) {
                    containnull = true;
                }
            }
            if (ready == true) {
                repaint();
                if (!containnull) {
                    findMatch();
                }

                fulfillGem();

                countGem = 0;
                for (int j = 0; j < 64; j++) {
                    if (difGem[j].isMatch() == true) {
                        difGem[j].setPic(null);
                        difGem[j].toggleFocus();
                        countGem++;
                        find = true;
                        repaint();
                        idle(20);
                        difGem[j].toggleFocus();
                    }
                }

                score += countGem * 10;
                for (int j = 0; j < 64; j++) {
                    if (difGem[j].isMatch() == true) {
                        difGem[j].match(difGem[j].isMatch());
                    }
                }
            }
            repaint();
            int countNotNUll = 0;
            for (int w = 0; w < 64; w++) {
                if (difGem[w].getPic() != null) {
                    countNotNUll++;

                }
            }
            if (countNotNUll == 64) {
                canTouch = true;
            }

            if (time <= 0) {

                end = true;

            }
            idle(40);
        }

        public void gameOver() {
            System.out.println(end);
            repaint();
        }

        public void idle(final int ms) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(new ImageIcon(this.getClass().getResource("/assets/board.png")).getImage(), 0, 0, this);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            g.drawString("FPS: " + fps, 10, 20);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.drawString("Time: ", 50, 150);
            g.drawString(Integer.toString(time), 120, 150);
            g.drawString("Score: ", 50, 190);
            g.drawString(Integer.toString(score), 120, 190);
            g.drawString("Preview", 50, 230);
            g.drawImage(PI.image(s1, set), 50, 250, this);
            g.drawImage(PI.image(s2, set), 50, 320, this);
            g.drawString("Press \"S\" Save Game", 10, 415);
            g.drawString("Press \"L\" Load Game", 10, 460);
            g.drawString("Press \"1\" Set 1 Gems", 10, 505);
            g.drawString("Press \"2\" Set 2 Gems", 10, 550);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (difGem[i * 8 + j] != null) {
                        g.drawImage(difGem[i * 8 + j].getPic(), (int) difGem[i * 8 + j].getPosX_(), (int) difGem[i * 8 + j].getPosY_(), this);
                        if (difGem[i * 8 + j].isSelected()) {
                            g.drawImage(new ImageIcon(this.getClass().getResource("/assets/focus.png")).getImage(), (int) difGem[i * 8 + j].getPosX_(), (int) difGem[i * 8 + j].getPosY_(), this);
                        }
                    }
                }
            }
            if (end == true) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 800, 600);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
                g.setColor(Color.WHITE);
                g.drawString("Time out", 291, 250);
                g.drawString("Score: ", 290, 290);
                g.drawString(Integer.toString(score), 425, 290);
            }

        }
    }

}
