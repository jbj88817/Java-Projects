
//by Bojie Jiang 
//Nov.5 2012

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.*;
import javax.swing.*;


class CropImagePanel extends JPanel {

	BufferedImage img,img_1;
	JLabel statusBar;
	ArrayList<Rect> boxes;
	Point base, current;
	Rect r;
	static int xmin,ymin,xmax,ymax;
	boolean isDragging;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
		/*if (boxes.size() == 0)
			return;*/
		for (Rect r : boxes){
			r.draw(g);
		}
		/*ci=new CutImage(img);
		img_1=ci.getChildImage(xmin, ymin, xmax-xmin+1, ymax-ymin+1);*/
		
		
	}

	public CropImagePanel() {
		String filename = "LandScape.jpg";
		
		try {
			img = ImageIO.read(new File(filename));

		} catch (IOException e) {
			// System.out.println(e); // e.getMessage());
			System.out.println(filename + " not found");
			System.exit(-1);
		}
		statusBar = new JLabel("defalut");
		isDragging = false;
		boxes = new ArrayList<Rect>();
		Handlerclass handler = new Handlerclass();
		
		addMouseListener(handler);
		addMouseMotionListener(handler);
	}

	private class Handlerclass implements MouseListener, MouseMotionListener {

		public void mouseDragged(MouseEvent event) {
			Graphics g = getGraphics();
			int bx = base.x;
			int by = base.y;
			Color color= new Color(0,0,0);
			//g.setXORMode(color);
			if (isDragging) {
				r.fastdraw(g);
			}
			isDragging = true;
			current = event.getPoint();
			r = new Rect(bx, by, current.x, current.y);
			r.fastdraw(g);
			g.setPaintMode();
			g.setColor(Color.BLACK);
			repaint();
		}

		public void mouseMoved(MouseEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			int color = img.getRGB(e.getX(), e.getY());
			int R = color >> 16 & 0xff;
			int G = color >> 8 & 0xff;
			int B = color & 0xff;
			// Color color = robot.getPixelColor(e.getX(), e.getY());

			statusBar.setText("Mouse at [" + e.getX() + ", " + e.getY() + "]"
					+ ", and Color is " + R + ", " + G + ", " + B + " (RGB)");

		}

		public void mouseClicked(MouseEvent arg0) {
			repaint();

		}

		public void mouseEntered(MouseEvent arg0) {
			repaint();

		}

		public void mouseExited(MouseEvent arg0) {
			repaint();

		}

		public void mousePressed(MouseEvent event) {
			//current = event.getPoint();
			base = event.getPoint();

		}

		public void mouseReleased(MouseEvent event) {
			isDragging = false;
			current = event.getPoint();

			Graphics g = getGraphics();
			//g.setXORMode(new Color(0,0,0,0f));
			r.fastdraw(g);
			//g.setPaintMode();
			g.setColor(Color.BLACK);
			Rect rg = new Rect(base.x, base.y, current.x, current.y);
			if (boxes.size() > 0)
				boxes.remove(0);
			if (rg.area() > 1)
				boxes.add(rg);
			xmin=base.x;
			ymin=base.y;
			xmax=current.x;
			ymax=current.y;
			statusBar.setText("Image Saved as Q2output.jpg");
			img_1=img.getSubimage(xmin, ymin, xmax-xmin+1, ymax-ymin+1);
			try {
				ImageIO.write(img_1, "jpg", new File("Q2output.jpg"));
			} catch (IOException e) {
				System.out.println("write failed");
				e.printStackTrace();
			}
			
			repaint();
			
			
		}

	}

	public Dimension getPreferredSize() {
		if (img == null) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(img.getWidth(null), img.getHeight(null));
		}
	}

}

class mainThread implements Runnable {
	public void run() {
		CropImagePanel panel = new CropImagePanel();
		JFrame frame = new JFrame("Crop image");
		frame.add(panel);
		frame.add(panel.statusBar, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}

public class CropImage extends JApplet {
	public void init() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				CropImagePanel panel = new CropImagePanel();
				add(panel);
				add(panel.statusBar, BorderLayout.SOUTH);
			}
		});
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new mainThread());
	}
}