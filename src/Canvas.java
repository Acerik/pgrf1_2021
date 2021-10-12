import raster.Raster;
import raster.RasterBufferedImage;
import rasterize.LineRasterizeTrivial;
import rasterize.LineRasterizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: zobrazeni pixelu
 *
 * @author PGRF FIM UHK
 * @version 2020
 */

public class Canvas {

    private JFrame frame;
    private JPanel panel;
    //private BufferedImage img;
    private Raster raster;
    private LineRasterizer lineRasterizer;

    public Canvas(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        raster = new RasterBufferedImage(width, height);
        lineRasterizer = new LineRasterizeTrivial(raster);

        //img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void clear() {
        raster.clear();
    }

    public void present(Graphics graphics) {
        ((RasterBufferedImage)raster).present(graphics);
    }

    public void draw() {
        clear();
        lineRasterizer.rasterize(10,10,100,50);
        lineRasterizer.rasterize(20,20,120,70);
        lineRasterizer.rasterize(110,60,120,200);
        //drawline(210, 20, 300, 20);
    }

    /*
    public void drawline(int x1, int y1, int x2, int y2) {
        float k,q;

        k=((float)(y2-y1)/(x2-x1))  ;
        q=y1-(k*x1);
        for (int x = x1; x <= x2; x++){
            float y= (k*x)+q;
            img.setRGB(x, (int)y, 0xffff00);
        }
    }
     */

    public void start() {
        draw();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}