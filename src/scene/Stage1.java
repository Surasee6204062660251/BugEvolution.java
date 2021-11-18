
package scene;

import elements.Ball;
import elements.Element;
import elements.PowerPellet;
import elements.Wall;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Consts;

public class Stage1 extends Stage {

    public Stage1() {
        this.drawSceneFinal();
    }

    @Override
    public void paintScene(Graphics g) {
        g.fillRect(0, 0, Consts.CELL_SIZE * Consts.NUM_CELLS, Consts.CELL_SIZE * Consts.NUM_CELLS + 50);

        Iterator<Element> it_wall = walls.listIterator();
        while (it_wall.hasNext()) {
            it_wall.next().autoDraw(g);
        }

        Iterator<Ball> it = balls.listIterator();
        while (it.hasNext()) {
            it.next().autoDraw(g);
        }

        Iterator<PowerPellet> it2 = powerPellet.listIterator();
        while (it2.hasNext()) {
            it2.next().autoDraw(g);
        }

        points = (tballs - balls.size()) * 10;
    }


    @Override
    protected void drawSceneFinal() {

        Scanner mapRead;
        try {
            mapRead = new Scanner(new FileInputStream("./src/maps/map1.txt"));
            for (int i = 0; i < Consts.NUM_CELLS; i++) {
                for (int j = 0; j < Consts.NUM_CELLS; j++) {
                    map[i][j] = (int) mapRead.nextByte();
                }
            }
            mapRead.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error1");
            Logger.getLogger(Stage1.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int x = 0; x < Consts.NUM_CELLS; x++) {
            for (int y = 0; y < Consts.NUM_CELLS; y++) {
                switch (map[x][y]) {
                    case 0:
                        this.balls.add(new Ball("ball.png", 10, x, y));
                        break;
                    case 1:
                        this.walls.add(new Wall("brick.png", x, y));
                        break;
                    case 2:
                        this.powerPellet.add(new PowerPellet("power_pellet.png", 50, x, y));
                        break;
                    default:
                        break;
                }
            }
        }
        
        map[1][1] = 0;

        tballs = balls.size();
    }
}
