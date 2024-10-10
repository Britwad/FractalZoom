package Fractals;

import java.awt.*;

public class Image {

    private Color[][] grid;

    public Image(int width, int height) {
        this.grid = new Color[width][height];
    }

    public void setPixel(int x,int y,Color color) {
        grid[x][y] = color;
    }

    public void render(Graphics g) {
        for (int y = 0;y<grid[0].length;y++) {
            for (int x = 0;x<grid.length;x++) {
                if (grid[x][y]!=null) {
                    g.setColor(grid[x][y]);
                    g.drawLine(x, y, x, y);
                }
            }
        }
    }

}
