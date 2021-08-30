import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
public class Main {
    public static void main(String[] args) {
        final double spacing = 0.0005;                // 1px = this value squared
        final double startRe = -2.15, startIm = 1.15; // first complex number in grid
        final double endRe = 0.75, endIm = -1.15;     // last complex number in grid
        final int iterFactor = 15;                    // used to determine # of iterations
        final String outfile = "out.png";
        final Color[] colors = {
            Color.BLUE,
            Color.WHITE,
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.CYAN,
            Color.PINK,
            Color.GRAY,      // closer to black =
            Color.DARK_GRAY, // closer to being
            Color.BLACK      // part of set
        };
        final int maxIter = (colors.length-1)*iterFactor;
        final BufferedImage img = new BufferedImage(
            (int)((endRe-startRe)/spacing), // width
            (int)((startIm-endIm)/spacing), // height
            BufferedImage.TYPE_INT_ARGB);
        // set pixels for image
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
            ImageIO.write(img, outfile.split("\\.")[1], new File(outfile));
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }
}
