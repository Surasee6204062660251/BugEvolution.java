
package scene;

import elements.Ball;
import elements.Element;
import elements.PowerPellet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import utils.Consts;

public abstract class Stage {

    protected int map[][];

    protected List<Ball> balls;

    protected List<PowerPellet> powerPellet;

    protected List<Element> walls;

    protected int tballs;

    protected int points;

    protected Image brick;

    public Stage() {
        this.map = new int[Consts.NUM_CELLS][Consts.NUM_CELLS];
        this.balls = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.powerPellet = new ArrayList<>();
        this.points = 0;

        // Bordas
        for (int i = 0; i < Consts.NUM_CELLS; i++) {
            map[i][0] = 1;
            map[0][i] = 1;
            map[i][Consts.NUM_CELLS - 1] = 1;
            map[Consts.NUM_CELLS - 1][i] = 1;
        }
    }

    public void setBlock(String imageName) {
        try {
            this.brick = Toolkit.getDefaultToolkit().getImage(
                    new java.io.File(".").getCanonicalPath() + Consts.PATH + imageName);
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public int[][] getMap() {
        return map;
    }

    public int map(int x, int y) {
        return map[x][y];
    }

    public abstract void paintScene(Graphics g);

    protected abstract void drawSceneFinal();

    public List<Ball> getBalls() {
        return balls;
    }
    
    public void setBalls(List<Ball> balls){
        this.balls = balls;
    }
    
    public void setPowerPellet(List<PowerPellet> powerPellet){
        this.powerPellet = powerPellet;
    }

    public List<Element> getWalls() {
        return walls;
    }
    
    public void setWalls(List<Element> walls){
        this.walls = walls;
    }

    public int getTotalBall() {
        return this.balls.size();
    }

    public int getPoints() {
        return points;
    }

    public List<PowerPellet> getPowerPellet() {
        return powerPellet;
    }
    public int getTotalPowerPellet() {
        return this.powerPellet.size();
    }
}
