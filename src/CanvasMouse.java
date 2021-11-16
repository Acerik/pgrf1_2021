
import fill.SeedFiller;
import model.Line;
import raster.Raster;
import raster.RasterBufferedImage;
import rasterize.LineRasterizeBufferImage;
import rasterize.LineRasterizeTrivial;
import rasterize.LineRasterizer;
import transforms.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
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

	private Mat3 trans = new Mat3Identity(); //Mat3Transl2D(100, -50);


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
				if (e.getButton() == MouseEvent.BUTTON2) {
					SeedFiller seedFiller = new SeedFiller(raster); // dodělat implementaci
					seedFiller.setSeedX(e.getX());
					seedFiller.setSeedY(e.getY());
					seedFiller.setBackgroundColor(raster.getPixel(e.getX(),e.getY()));
					seedFiller.fill();
				}
				if (e.getButton() == MouseEvent.BUTTON3){
					clear();
					//trans = trans.mul(new Mat3Rot2D(0.1));
					trans = trans.mul(new Mat3Transl2D(20, 20));
					draw();
				}
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

		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_A){
					trans = trans.mul(new Mat3Rot2D(0.1));
					draw();
				}
			}
		});
		frame.requestFocus();
		frame.requestFocusInWindow();
	}

	public void draw(){
		for(Line line : lines){
			Point2D a = new Point2D(line.getX1(), line.getY1());
			Point2D b = new Point2D(line.getX2(), line.getY2());
			a = a.mul(trans);
			b = b.mul(trans);
			Line newLine = new Line((int)a.getX(), (int)a.getY(), (int)b.getX(), (int)b.getY());
			lineRasterizer.rasterize(line);
			lineRasterizer.rasterize(newLine);
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
