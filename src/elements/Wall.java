
package elements;

import java.awt.Graphics;
import utils.Drawing;

public class Wall extends Element {

    public Wall(String image, double x, double y) {
        super(new String[]{image}, 0, 5);
        this.isVisible = true;
        this.isTransposable = false;
        this.setPosition(x, y);
    }

    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, imageIcon, pos.getY(), pos.getX());
    }

}
