import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
public class Main {
    public static void main(String[] args) {
        double spacing = 0.0005; // 1 px is this value squared
        double startRe = -2.15, startIm = 1.15;
        double endRe = 0.75, endIm = -1.15;
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
        String imgName = "out";
        String imgFormat = "png";
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        // set pixels for Mandelbrot set
        for (double cIm = startIm; cIm >= endIm; cIm -= spacing) {
            for (double cRe = startRe; cRe <= endRe; cRe += spacing) {
                double zRe = 0, zIm = 0;
                int iter = 0;
                for (; iter < maxIter; iter++) {
                    double zReSave = zRe;
                    zRe = zRe*zRe - zIm*zIm + cRe;
                    zIm = 2*zReSave*zIm + cIm;
                    if (Math.hypot(zRe, zIm) > 2) {
                        break;
                    }
                }
                img.setRGB((int)((cRe-startRe)/spacing), // x
                    (int)((startIm-cIm)/spacing),        // y
                    colors[iter/iterFactor].getRGB());   // rgb
            }
        }
        // write image
        try {
            ImageIO.write(img, imgFormat, new File(imgName+"."+imgFormat));
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }
}
