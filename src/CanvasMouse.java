
import model.Line;
import raster.Raster;
import raster.RasterBufferedImage;
import rasterize.LineRasterizeBufferImage;
import rasterize.LineRasterizeTrivial;
import rasterize.LineRasterizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi
 * 
 * @author PGRF FIM UHK
 * @version 2020
 */
public class CanvasMouse {

	private JPanel panel;
	//private BufferedImage img;
	private Raster raster;
	private LineRasterizer lineRasterizer;

	private int x1, y1;

	private List<Line> lines;

	public CanvasMouse(int width, int height) {
		JFrame frame = new JFrame();

		frame.setLayout(new BorderLayout());
		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		raster = new RasterBufferedImage(width, height);
		lineRasterizer = new LineRasterizeBufferImage(raster);

		lines = new ArrayList<>();
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

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					x1 = e.getX();
					y1 = e.getY();
					//raster.setPixel(e.getX(), e.getY(), 0xffff00);
				}
				if (e.getButton() == MouseEvent.BUTTON2)
					raster.setPixel(e.getX(), e.getY(), 0xff00ff);
				if (e.getButton() == MouseEvent.BUTTON3)
					raster.setPixel(e.getX(), e.getY(), 0xffffff);
				panel.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					lineRasterizer.rasterize(x1,y1, e.getX(), e.getY());
					lines.add(new Line(x1, y1, e.getX(), e.getY()));
				}
				panel.repaint();
			}
		});

		panel.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				raster.clear();
				lineRasterizer.rasterize(x1, y1, e.getX(), e.getY());
				draw();
			}
		});
	}

	public void draw(){
		for(int x = 0; x < lines.size(); x++){
			lineRasterizer.rasterize(lines.get(x));
		}
		panel.repaint();
	}

	public void clear() {
		raster.clear();
	}

	public void present(Graphics graphics) {
		((RasterBufferedImage)raster).present(graphics);
	}

	public void start() {
		clear();
		//img.getGraphics().drawString("Use mouse buttons", 5, img.getHeight() - 5);
		panel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CanvasMouse(800, 600).start());
	}

}
