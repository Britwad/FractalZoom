package Fractals;

import GameEngineV3.Game;
import GameEngineV3.GameInterface;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class Main implements GameInterface {

    public static final double RATIO = 2.24/2.47;
    public static final int WIDTH = 800, HEIGHT = (int)(WIDTH*RATIO);

    public double frameX = -1.5,frameY = 1.12,frameLength = 2.47;
    //public double frameX = -1.7499997999816919,frameY = 2.3465192420997126E-9,frameLength = 8.674973628330918E-9;

    //public BigDecimal frameX = new BigDecimal(-2),frameY = new BigDecimal("1.12"),frameLength = new BigDecimal("2.47");
    public Color[][] image;


    public static void main(String[] args) {
        new Game(new Main(),WIDTH,HEIGHT,"Fractal",1,true);
    }

    public void initialize(Game game) {
        image = new Color[HEIGHT][WIDTH];
        loadMandelbrot();
    }

    boolean mouseDown = false;
    int tempFrameX;
    int tempFrameY;
    int mouseX;
    int mouseY;

    ArrayList<Point> path;

    public void tick(Game game) {
        mouseX = game.getMouseX();
        mouseY = game.getMouseY();
        //System.out.println(frameX + ", " + frameY + " LENGTH: " + frameLength);
        //System.out.println(toMandelbrotFrameX(mouseX) + ", " + toMandelbrotFrameY(mouseY));
        if (!mouseDown) {
            if (game.mouseDown(0)) {
                tempFrameX = mouseX;
                tempFrameY = mouseY;
                mouseDown = true;
            }
        }
        else  {
            if (!game.mouseDown(0)) {
                frameX = toMandelbrotFrameX(tempFrameX);
                frameY = toMandelbrotFrameY(tempFrameY);
                frameLength = toMandelbrotFrameX(mouseX)-toMandelbrotFrameX(tempFrameX);
                //frameLength = toMandelbrotFrameX(mouseX).subtract(toMandelbrotFrameX(tempFrameX));

                loadMandelbrot();
                mouseDown = false;
            }
        }
//        path = getMandelbrotPath(toMandelbrotFrameX(mouseX),toMandelbrotFrameY(mouseY),10);

        path = getMandelbrotPath(toMandelbrotFrameX(mouseX),toMandelbrotFrameY(mouseY),100);

//        for (double[] d: path) {
//            System.out.print(Arrays.toString(d) + ", ");
//        }
//        System.out.println();
    }

    public void renderBackground(Graphics g) {

    }

    public void render(Graphics g) {
        for (int y = 0;y<image.length;y++) {
            for (int x = 0;x<image[0].length;x++) {
                g.setColor(image[y][x]);
                g.drawLine(x,y,x,y);
            }
        }
        g.setColor(Color.red);
        if (mouseDown) g.drawRect(tempFrameX,tempFrameY,mouseX-tempFrameX,(int)((mouseX-tempFrameX)*RATIO));

//        g.setColor(Color.white);
//        if (path!=null) {
//            for (int i = 0;i<path.size()-1;i++) {
//                //g.drawLine(toFrameX(path.get(i).x),toFrameY(path.get(i).y),toFrameX(path.get(i+1).x),toFrameY(path.get(i+1).y));
//                g.drawLine(path.get(i).x,path.get(i).y,path.get(i+1).x,path.get(i+1).y);
//            }
//        }
    }
    private void loadMandelbrot() {
        int maxIteration = 1500;
        for (int y = 0;y<image.length;y++) {
                    for (int x = 0;x<image[0].length;x++) {
                        //System.out.println(100*((y*image[0].length + x)/(double)(image.length*image[0].length)));
                        int iterations = inMandelbrotRange(toMandelbrotFrameX(x),toMandelbrotFrameY(y),maxIteration);
                        if (iterations==maxIteration)
                            image[y][x] = Color.black;
                        else image[y][x] = valueToColorRange(iterations,maxIteration);//Color.white;

                    }
                }
    }
    private double toMandelbrotFrameX(int x) {
        return ((double)x/WIDTH)*frameLength + frameX;
    }
    private double toMandelbrotFrameY(int y) {
        return frameY - ((double)y/HEIGHT)*(frameLength*RATIO);
    }
    private int toFrameX(double x) {
        return (int)(((x-frameX)/frameLength)*WIDTH);
    }
    private int toFrameY(double y) {
        return (int)(((frameY-y)/(frameLength*RATIO))*HEIGHT);
    }
//    private BigDecimal toMandelbrotFrameX(int x) {
//        return frameLength.multiply(new BigDecimal((double)x/WIDTH)).add(frameX);
//    }
//    private BigDecimal toMandelbrotFrameY(int y) {
//        return frameY.subtract(frameLength.multiply(new BigDecimal(RATIO)).multiply(new BigDecimal((double)y/HEIGHT)));
//    }

    public static boolean inMandelbrot(double n, double i, int maxIteration) {
        return inMandelbrotRange(n,i,maxIteration)==maxIteration;
    }
    public static int inMandelbrotRange(double n, double i, int maxIteration) {
        double x = 0;
        double y = 0;
        int iteration = 0;
        while (x*x + y*y < 4 & iteration<maxIteration) {
            double temp = x*x - y*y + n;
            y = 2*x*y + i;
            x = temp;
            iteration++;
        }
        return iteration;
    }
    public static int inMultibrotRange(double n, double i, double d, int maxIteration) {
        double x = 0;
        double y = 0;
        int iteration = 0;
        while (x*x + y*y < 4 & iteration<maxIteration) {
//            double temp = init*Math.cos(init2) + n;
//            y = init*Math.sin(init2) + i;
//            x = temp;
            iteration++;
        }
        return iteration;
    }
//    public static ArrayList<double[]> getMandelbrotPath(double n, double i, int maxIteration) {
//        ArrayList<double[]> points = new ArrayList<>();
//        double x = 0;
//        double y = 0;
//        int iteration = 0;
//        while (x*x + y*y < 4 & iteration<maxIteration) {
//            points.add(new double[]{x,y});
//            double temp = x*x - y*y + n;
//            y = 2*x*y + i;
//            x = temp;
//            iteration++;
//        }
//        return points;
//    }
    public ArrayList<Point> getMandelbrotPath(double n, double i, int maxIteration) {
        ArrayList<Point> points = new ArrayList<>();
        double x = 0;
        double y = 0;
        int iteration = 0;
        while (x*x + y*y < 4 & iteration<maxIteration) {
            points.add(new Point(toFrameX(x),toFrameY(y)));
            double temp = x*x - y*y + n;
            y = 2*x*y + i;
            x = temp;
            iteration++;
        }
        return points;
    }

    public static int inMandelbrotRange(BigDecimal n, BigDecimal i, int maxIteration) {
        BigDecimal x = BigDecimal.ZERO;
        BigDecimal y = BigDecimal.ZERO;
        int iteration = 0;
        while (x.multiply(x).add(y.multiply(y)).compareTo(new BigDecimal(4)) < 0 & iteration<maxIteration) {
            BigDecimal temp = (x.multiply(x).subtract(y.multiply(y))).add(n);
            y = x.multiply(y.multiply(new BigDecimal(2))).add(i);
            x = temp;
            iteration++;
        }
        return iteration;
    }

    public static Color valueToColorRange(int value, int max) {
        return transitionOfHueRange((double)value/max,0,360);
    }

    //NOT MY CODE
    public static Color transitionOfHueRange(double percentage, int startHue, int endHue) {
        // From 'startHue' 'percentage'-many to 'endHue'
        // Finally map from [0°, 360°] -> [0, 1.0] by dividing
        double hue = ((percentage * (endHue - startHue)) + startHue) / 360;

        double saturation = 1.0;
        double lightness = 0.5;

        // Get the color
        return hslColorToRgb(hue, saturation, lightness);
    }
    public static Color hslColorToRgb(double hue, double saturation, double lightness) {
        if (saturation == 0.0) {
            // The color is achromatic (has no color)
            // Thus use its lightness for a grey-scale color
            int grey = percToColor(lightness);
            return new Color(grey, grey, grey);
        }

        double q;
        if (lightness < 0.5) {
            q = lightness * (1 + saturation);
        } else {
            q = lightness + saturation - lightness * saturation;
        }
        double p = 2 * lightness - q;

        double oneThird = 1.0 / 3;
        int red = percToColor(hueToRgb(p, q, hue + oneThird));
        int green = percToColor(hueToRgb(p, q, hue));
        int blue = percToColor(hueToRgb(p, q, hue - oneThird));

        return new Color(red, green, blue);
    }
    public static double hueToRgb(double p, double q, double t) {
        if (t < 0) {
            t += 1;
        }
        if (t > 1) {
            t -= 1;
        }

        if (t < 1.0 / 6) {
            return p + (q - p) * 6 * t;
        }
        if (t < 1.0 / 2) {
            return q;
        }
        if (t < 2.0 / 3) {
            return p + (q - p) * (2.0 / 3 - t) * 6;
        }
        return p;
    }
    public static int percToColor(double percentage) {
        return (int)Math.round(percentage * 255);
    }


}
