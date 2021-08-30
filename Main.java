import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
public class Main {
    public static void main(String[] args) {
        double spacing = 0.0005; // 1 px is this value squared
        double startRe = -2.15;
        double startIm = 1.15;
        double endRe = 0.75;
        double endIm = -1.15;
        int w = (int)((endRe-startRe)/spacing);
        int h = (int)((startIm-endIm)/spacing);
        Color[] colors = {
            Color.BLUE,
            Color.WHITE,
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.CYAN,
            Color.PINK,
            Color.GRAY,
            Color.DARK_GRAY,
            Color.BLACK
        };
        int iterFactor = 15;
        int maxIter = (colors.length-1)*iterFactor;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        // set pixels for Mandelbrot set
        for (double cIm = startIm; cIm >= endIm; cIm -= spacing) {
            for (double cRe = startRe; cRe <= endRe; cRe += spacing) {
                double zRe = 0, zIm = 0;
                int iter = 0;
                for (; iter < maxIter; iter++) {
                    double re = zRe*zRe - zIm*zIm + cRe;
                    double im = 2*zRe*zIm + cIm;
                    zRe = re;
                    zIm = im;
                    if (Math.hypot(zRe, zIm) > 2) {
                        break;
                    }
                }
                int x = (int)((cRe-startRe)/spacing);
                int y = (int)((startIm-cIm)/spacing);
                int rgb = colors[iter/iterFactor].getRGB();
                img.setRGB(x, y, rgb);
            }
        }
        // write image
        try {
            ImageIO.write(img, "png", new File("out.png"));
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }
}
