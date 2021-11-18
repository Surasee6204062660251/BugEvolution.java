
package elements;

import java.awt.Graphics;
import utils.Drawing;

public class PowerPellet extends Element{
    private int points;

    // Construtor
    public PowerPellet(final String imageName, final int points) {
        super(new String[]{imageName}, 0, 2);
        this.points = points;
        this.isTransposable = false;
    }

    // Construtor
    public PowerPellet(final String imageName, final int point, final double x, final double y) {
        super(new String[]{imageName}, 0, 2);
        this.points = point;
        this.isTransposable = false;
        this.setPosition(x, y);
    }

    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, imageIcon, pos.getY(), pos.getX());
    }
}
