package control;

import data.Save;
import elements.Blinky;
import elements.BugMan;
import elements.Element;
import elements.Cherry;
import elements.Clyde;
import elements.Enemy;
import elements.Inky;
import elements.Pinky;
import elements.Strawberry;

import utils.Consts;
import utils.Drawing;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import scene.StageGameOver;
import scene.InitStage;

import scene.Stage;
import scene.Stage1;
import scene.Stage2;
import scene.Stage3;

public class GameScreen extends JFrame implements KeyListener, MouseListener {

    private BugMan bugMan;

    private Blinky blinky;
    private Clyde clyde;
    private Inky inky;
    private Pinky pinky;

    private Strawberry strawberry;
    private Cherry cherry;

    private ArrayList<Element> elemArray;
    private ArrayList<Enemy> enemys;

    private final GameController controller = new GameController();
    private final Random random = new Random();
    private final Executor executor_scene_1;

    private Stage scene;

    private Image imgLife;
    private Image imgScore;
    private Image imgNum0, imgNum1, imgNum2, imgNum3,
            imgNum4, imgNum5, imgNum6, imgNum7, imgNum8, imgNum9;

    private int controlScene;

    public GameScreen() {
        Drawing.setGameScreen(this);
        initComponents();

        this.addKeyListener(this);
        this.addMouseListener(this);

        this.setSize(Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().left + getInsets().right,
                Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().top + getInsets().bottom + 50);

        this.elemArray = new ArrayList<>();
        this.enemys = new ArrayList<>();

        // Bugman
        this.bugMan = new BugMan();
        this.addElement(bugMan);

        // Blinky
        this.blinky = new Blinky();
        this.elemArray.add(blinky);
        this.enemys.add(blinky);

        // Inky
        this.inky = new Inky();
        this.elemArray.add(inky);
        this.enemys.add(inky);

        // Pinky
        this.pinky = new Pinky();
        this.elemArray.add(pinky);
        this.enemys.add(pinky);

        // Clyde
        this.clyde = new Clyde();
        this.elemArray.add(clyde);
        this.enemys.add(clyde);

        this.strawberry = new Strawberry();
        this.cherry = new Cherry();

        try {
            this.imgScore = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "button_score.png");
            this.imgLife = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "bugman_right.png");

            this.imgNum0 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num0.png");
            this.imgNum1 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num1.png");
            this.imgNum2 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num2.png");
            this.imgNum3 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num3.png");
            this.imgNum4 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num4.png");
            this.imgNum5 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num5.png");
            this.imgNum6 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num6.png");
            this.imgNum7 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num7.png");
            this.imgNum8 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num8.png");
            this.imgNum9 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num9.png");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.executor_scene_1 = Executors.newCachedThreadPool();
        this.executor_scene_1.execute(pinky);
        this.executor_scene_1.execute(bugMan);
        this.executor_scene_1.execute(inky);

        this.controlScene = 0;
        newScene(controlScene);
    }

    private void newScene(int scene) {
        switch (scene) {
            case 0:
                this.scene = new InitStage();

                this.bugMan.setLife(3);

                this.bugMan.resetTotalScore();

                break;

            case 1:
                this.scene = new Stage1();
                this.scene.setBlock("brick.png");

                resetEnemyPac();

                int aux1,
                 aux2;
                do {
                    aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                    aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                } while (this.scene.map(aux1, aux2) == 1);

                this.strawberry.setPosition(aux1, aux2);
                this.addElement(strawberry);

                do {
                    aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                    aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                } while (this.scene.map(aux1, aux2) == 1);

                this.cherry.setPosition(aux1, aux2);
                this.addElement(cherry);

                break;

            case 2:
                this.scene = new Stage2();
                this.scene.setBlock("brick.png");

                resetEnemyPac();

                do {
                    aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                    aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                } while (this.scene.map(aux1, aux2) == 1);

                this.strawberry.setPosition(aux1, aux2);
                this.addElement(strawberry);

                do {
                    aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                    aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                } while (this.scene.map(aux1, aux2) == 1);

                this.cherry.setPosition(aux1, aux2);
                this.addElement(cherry);

                break;

            case 3:
                this.scene = new Stage3();
                this.scene.setBlock("brick.png");

                resetEnemyPac();

                do {
                    aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                    aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                } while (this.scene.map(aux1, aux2) == 1);

                this.strawberry.setPosition(aux1, aux2);
                this.addElement(strawberry);

                do {
                    aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                    aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                } while (this.scene.map(aux1, aux2) == 1);

                this.cherry.setPosition(aux1, aux2);
                this.addElement(cherry);

                break;

            case 4:
                this.scene = new StageGameOver();
                break;

            case 5:

                break;
        }
    }

    public final void addElement(Element elem) {
        elemArray.add(elem);
    }

    public void removeElement(Element elem) {
        elemArray.remove(elem);
    }

    @Override
    public void paint(Graphics gOld) {
        Graphics g = getBufferStrategy().getDrawGraphics();
        Graphics g2 = g.create(getInsets().right, getInsets().top,
                getWidth() - getInsets().left, getHeight() - getInsets().bottom);

        this.controller.drawAllElements(scene, elemArray, g2, controlScene);

        if (controlScene != 0 && controlScene != 4) {

            setBlinkyMovDirection();

            setPinkyMovDirection();

            setInkyMovDirection();

            setClydeMovDirection();

            if (controller.processAllElements(scene, elemArray, enemys)) {

                bugMan.removeLife();

                resetEnemyPac();

                if (bugMan.getLife() == 0) {
                    System.out.println("Enter");
                    this.controlScene = 4;
                    newScene(controlScene);
                    return;
                }
            }

            if (scene.getBalls().isEmpty() && scene.getPowerPellet().isEmpty()) {
                controlScene++;
                newScene(controlScene);
            }

            int aux = Consts.CELL_SIZE * Consts.NUM_CELLS;

            for (int i = 0; i < bugMan.getLife(); i++) {
                g2.drawImage(imgLife, 10 + (32 * i), aux + 10, 30, 30, null);
            }

            if (elemArray.contains(strawberry)) {
                g2.drawImage(strawberry.getImgElement().getImage(), 140, aux + 7, 30, 33, null);
            }

            if (elemArray.contains(cherry)) {
                g2.drawImage(cherry.getImgElement().getImage(), 180, aux + 7, 30, 33, null);
            }

            g2.drawImage(imgScore, 340, aux + 2, 75, 45, null);

            String score = Integer.toString(bugMan.getScore());

            for (int i = 0; i < score.length(); i++) {
                switch (score.charAt(i)) {
                    case '0':
                        g2.drawImage(imgNum0, 410 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '1':
                        g2.drawImage(imgNum1, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '2':
                        g2.drawImage(imgNum2, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '3':
                        g2.drawImage(imgNum3, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '4':
                        g2.drawImage(imgNum4, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '5':
                        g2.drawImage(imgNum5, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '6':
                        g2.drawImage(imgNum6, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '7':
                        g2.drawImage(imgNum7, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '8':
                        g2.drawImage(imgNum8, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '9':
                        g2.drawImage(imgNum9, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    default:
                        break;
                }
            }
        }

        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

    private void setBlinkyMovDirection() {
        switch (bugMan.getMovDirection()) {
            case BugMan.MOVE_DOWN:

                if (bugMan.getPos().getX() > blinky.getPos().getX()) {
                    blinky.setMoveDirection(Enemy.MOVE_DOWN);
                } else {
                    blinky.setMoveDirection(Enemy.MOVE_UP);
                }
                break;

            case BugMan.MOVE_UP:

                if (bugMan.getPos().getX() > blinky.getPos().getX()) {
                    blinky.setMoveDirection(Enemy.MOVE_DOWN);
                } else {
                    blinky.setMoveDirection(Enemy.MOVE_UP);
                }
                break;

            case BugMan.MOVE_LEFT:

                if (bugMan.getPos().getY() > blinky.getPos().getY()) {
                    blinky.setMoveDirection(Enemy.MOVE_RIGHT);
                } else {
                    blinky.setMoveDirection(Enemy.MOVE_LEFT);
                }
                break;

            case BugMan.MOVE_RIGHT:

                if (bugMan.getPos().getY() > blinky.getPos().getY()) {
                    blinky.setMoveDirection(Enemy.MOVE_RIGHT);
                } else {
                    blinky.setMoveDirection(Enemy.MOVE_LEFT);
                }
                break;
        }
    }

    private void setInkyMovDirection() {

        inky.setDistanceBlinky(inky.getPos().getX(), inky.getPos().getY(),
                blinky.getPos().getX(), blinky.getPos().getY());

        if (inky.getDistanceBlinky() < 4 && inky.getStateDirection() == Inky.MOVE_PAC) {
            switch (blinky.getMovDirection()) {
                case Blinky.MOVE_DOWN:

                    if (blinky.getPos().getX() > inky.getPos().getX()) {
                        inky.setMoveDirection(Enemy.MOVE_DOWN);
                    } else {
                        inky.setMoveDirection(Enemy.MOVE_UP);
                    }
                    break;

                case Blinky.MOVE_UP:

                    if (blinky.getPos().getX() > inky.getPos().getX()) {
                        inky.setMoveDirection(Enemy.MOVE_DOWN);
                    } else {
                        inky.setMoveDirection(Enemy.MOVE_UP);
                    }
                    break;

                case Blinky.MOVE_LEFT:

                    if (blinky.getPos().getY() > inky.getPos().getY()) {
                        inky.setMoveDirection(Enemy.MOVE_RIGHT);
                    } else {
                        inky.setMoveDirection(Enemy.MOVE_LEFT);
                    }
                    break;

                case Blinky.MOVE_RIGHT:

                    if (blinky.getPos().getY() > inky.getPos().getY()) {
                        inky.setMoveDirection(Enemy.MOVE_RIGHT);
                    } else {
                        inky.setMoveDirection(Enemy.MOVE_LEFT);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private void setPinkyMovDirection() {
        switch (bugMan.getMovDirection()) {
            case BugMan.MOVE_DOWN:
                if (pinky.getStateDirection() == Pinky.MOVE_PAC) {
                    pinky.setMoveDirection(Enemy.MOVE_DOWN);
                }
                break;

            case BugMan.MOVE_UP:
                if (pinky.getStateDirection() == Pinky.MOVE_PAC) {
                    pinky.setMoveDirection(Enemy.MOVE_UP);
                }
                break;

            case BugMan.MOVE_LEFT:
                if (pinky.getStateDirection() == Pinky.MOVE_PAC) {
                    pinky.setMoveDirection(Enemy.MOVE_LEFT);
                }
                break;

            case BugMan.MOVE_RIGHT:
                if (pinky.getStateDirection() == Pinky.MOVE_PAC) {
                    pinky.setMoveDirection(Enemy.MOVE_RIGHT);
                }
                break;
        }
    }

    private void setClydeMovDirection() {

        clyde.setDistancePacman(clyde.getPos().getX(), clyde.getPos().getY(),
                bugMan.getPos().getX(), bugMan.getPos().getY());

        if (clyde.getDistancePacman() > 5) {
            switch (bugMan.getMovDirection()) {
                case BugMan.MOVE_DOWN:

                    if (bugMan.getPos().getX() > clyde.getPos().getX()) {
                        clyde.setMoveDirection(Enemy.MOVE_DOWN);
                    } else {
                        clyde.setMoveDirection(Enemy.MOVE_UP);
                    }

                    break;

                case BugMan.MOVE_UP:

                    if (bugMan.getPos().getX() > clyde.getPos().getX()) {
                        clyde.setMoveDirection(Enemy.MOVE_DOWN);
                    } else {
                        clyde.setMoveDirection(Enemy.MOVE_UP);
                    }

                    break;

                case BugMan.MOVE_LEFT:

                    if (bugMan.getPos().getY() > clyde.getPos().getY()) {
                        clyde.setMoveDirection(Enemy.MOVE_RIGHT);
                    } else {
                        clyde.setMoveDirection(Enemy.MOVE_LEFT);
                    }

                    break;

                case BugMan.MOVE_RIGHT:

                    if (bugMan.getPos().getY() > blinky.getPos().getY()) {
                        clyde.setMoveDirection(Enemy.MOVE_RIGHT);
                    } else {
                        clyde.setMoveDirection(Enemy.MOVE_LEFT);
                    }
                    break;
            }
        }
    }

    public void go() {
        TimerTask repaint = new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        };

        TimerTask timerStrawberry = new TimerTask() {
            @Override
            public void run() {
                if (controlScene != 0 && controlScene != 4) {
                    if (!strawberry.isVisible()) {
                        int aux1, aux2;
                        do {
                            aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                            aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                        } while (scene.map(aux1, aux2) == 1);

                        strawberry.setPosition(aux1, aux2);
                        strawberry.setVisible(true);
                        strawberry.setTransposable(false);

                    } else {
                        strawberry.setVisible(false);
                        strawberry.setTransposable(true);
                    }
                }
            }
        };

        TimerTask timerCherry = new TimerTask() {
            @Override
            public void run() {
                if (controlScene != 0 && controlScene != 4) {
                    if (!cherry.isVisible()) {

                        int aux1, aux2;
                        do {
                            aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                            aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                        } while (scene.map(aux1, aux2) == 1);

                        strawberry.setPosition(aux1, aux2);

                        cherry.setVisible(true);
                        cherry.setTransposable(false);

                    } else {

                        cherry.setVisible(false);
                        cherry.setTransposable(true);
                    }
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(repaint, 0, Consts.DELAY);
        timer.schedule(timerStrawberry, Consts.TIMER_STRAWBERRY, 15000);
        timer.schedule(timerCherry, Consts.TIMER_CHERRY, 15000);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int aux = controlScene;
        switch (aux) {
            case 0:
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        controlScene = 1;
                        newScene(controlScene);
                        break;
                    
                    case KeyEvent.VK_Q:
                        if (JOptionPane.showConfirmDialog(null,
                                "Do you sure?", "Sure", JOptionPane.YES_NO_OPTION) == 0) {
                            System.exit(0);
                        }
                        break;
                    
                    case KeyEvent.VK_L:
                        ObjectInputStream load;
                        try {
                            load = new ObjectInputStream(new FileInputStream("./src/data/save"));
                            Save saveClass = (Save) load.readObject();
                            load.close();

                            scene = new Stage1();
                            scene.setBlock("brick.png");

                            bugMan = saveClass.bugMan;
                            blinky = saveClass.blinky;
                            inky = saveClass.inky;
                            pinky = saveClass.pinky;
                            clyde = saveClass.clyde;
                            cherry = saveClass.cherry;
                            strawberry = saveClass.strawberry;
                            elemArray = saveClass.elemArray;
                            enemys = saveClass.enemys;
                            scene.setBalls(saveClass.balls);
                            scene.setPowerPellet(saveClass.powerPellet);
                            scene.setWalls(saveClass.walls);
                            this.controlScene = saveClass.controlScene;

                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException | ClassNotFoundException ex) {
                            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                }
                break;

            case 4:
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    if (JOptionPane.showConfirmDialog(null,
                            "Do you want to exit ?", "Sure?", JOptionPane.YES_NO_OPTION) == 0) {
                        System.exit(0);
                    }
                }
                break;

            default:
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:

                        bugMan.setTurn(true);
                        bugMan.setNextDirection(BugMan.MOVE_UP);
                        break;

                    case KeyEvent.VK_DOWN:
                        bugMan.setTurn(true);
                        bugMan.setNextDirection(BugMan.MOVE_DOWN);
                        break;

                    case KeyEvent.VK_LEFT:
                        bugMan.setTurn(true);
                        bugMan.setNextDirection(BugMan.MOVE_LEFT);
                        break;

                    case KeyEvent.VK_RIGHT:
                        bugMan.setTurn(true);
                        bugMan.setNextDirection(BugMan.MOVE_RIGHT);
                        break;

                    case KeyEvent.VK_SPACE:
                        bugMan.setTurn(true);
                        bugMan.setNextDirection(BugMan.STOP);
                        break;
                    
                    case KeyEvent.VK_S:
                        try {
                            Save saveClass = new Save();
                            saveClass.bugMan = bugMan;
                            saveClass.blinky = blinky;
                            saveClass.inky = inky;
                            saveClass.pinky = pinky;
                            saveClass.clyde = clyde;
                            saveClass.cherry = cherry;
                            saveClass.strawberry = strawberry;
                            saveClass.elemArray = elemArray;
                            saveClass.enemys = enemys;
                            saveClass.balls = scene.getBalls();
                            saveClass.powerPellet = scene.getPowerPellet();
                            saveClass.walls = scene.getWalls();
                            saveClass.controlScene = this.controlScene;
                            ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream("./src/data/save"));
                            save.writeObject(saveClass);
                            save.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    default:
                        break;
                }

                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BugEvolution");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(20, 20));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int aux = controlScene;
        switch (aux) {
            case 0:
                int a1 = (Consts.NUM_CELLS * Consts.CELL_SIZE) / 2;
                int x1 = e.getPoint().x;
                int y1 = e.getPoint().y;

                if ((100 <= y1 && y1 <= 160) && (a1 - 110 <= x1 && x1 <= a1 + 110)) {
                    controlScene = 1;
                    newScene(controlScene);

                } else if ((200 <= y1 && y1 <= 260) && (a1 - 110 <= x1 && x1 <= a1 + 110)) {

                    ObjectInputStream load;
                    try {
                        load = new ObjectInputStream(new FileInputStream("./src/data/save"));
                        Save saveClass = (Save) load.readObject();
                        load.close();

                        scene = new Stage1();
                        scene.setBlock("brick.png");

                        bugMan = saveClass.bugMan;
                        blinky = saveClass.blinky;
                        inky = saveClass.inky;
                        pinky = saveClass.pinky;
                        clyde = saveClass.clyde;
                        cherry = saveClass.cherry;
                        strawberry = saveClass.strawberry;
                        elemArray = saveClass.elemArray;
                        enemys = saveClass.enemys;
                        scene.setBalls(saveClass.balls);
                        scene.setPowerPellet(saveClass.powerPellet);
                        scene.setWalls(saveClass.walls);
                        this.controlScene = saveClass.controlScene;

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if ((300 <= y1 && y1 <= 360) && (a1 - 110 <= x1 && x1 <= a1 + 110)) {

                    if (JOptionPane.showConfirmDialog(null,
                            "Do you really want to exit ?", "Sure?", JOptionPane.YES_NO_OPTION) == 0) {
                        System.exit(0);
                    }
                }

                break;

            case 4:
                int a2 = (Consts.NUM_CELLS * Consts.CELL_SIZE) / 2;
                int x2 = e.getPoint().x;
                int y2 = e.getPoint().y;

                if ((350 <= y2 && y2 <= 410) && (a2 - 210 <= x2 && x2 <= a2 - 10)) {
                    controlScene = 0;
                    newScene(controlScene);
                } else if ((350 <= y2 && y2 <= 410) && (a2 + 10 <= x2 && x2 <= a2 + 210)) {
                    if (JOptionPane.showConfirmDialog(null,
                            "Do you really want to exit ?", "Sure?", JOptionPane.YES_NO_OPTION) == 0) {
                        System.exit(0);
                    }
                }
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    public void resetEnemyPac() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        this.bugMan.setPosition(1, 1);
        this.pinky.setPosition(9, 9);
        this.blinky.setPosition(9, 9);
        this.clyde.setPosition(9, 9);
        this.inky.setPosition(9, 9);
    }
}
