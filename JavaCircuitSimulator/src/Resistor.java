

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Resistor {
	private String name;
	private double value;
	public int node[] = new int[2];
	private int x, y;
	private int orientation;
	static int count = 0;
	String label;

	public Resistor() {
		name = " ";
		orientation = 0;
		value = 0;
		node[0] = 0;
		node[1] = 0;
		x = 0;
		y = 0;
		
	}

	public Resistor addResistor(String re) {
		String res[] = re.split(" ");
		this.setName(res[1]);
		this.node[0] = Integer.parseInt(res[2]);
		this.node[1] = Integer.parseInt(res[3]);
		this.value = Double.parseDouble(res[4]);
		this.setLocation(Integer.parseInt(res[5]), Integer.parseInt(res[6]));
		this.orientation = Integer.parseInt(res[7]);
		return this;

	}

	public Resistor(double x, double y) {
		name = String.format("R%d", ++count);
		this.x = (int) x;
		this.y = (int) y;

	}

	public Resistor(String name, int m, int n, double value, int orientation) {
		this.name = name;
		this.value = value;
		this.orientation = orientation;
		node[0] = m;
		node[1] = n;
		label=this.name + " = " + this.value / 1000 + "kohm";
	}

	public String getLabel() {
		return this.label;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return this.value;
	}

	public String getOrientation() {
		if (orientation == 90) {
			return "The resistor is vertical.";
		} else {
			return "The resistor is horizontal.";
		}

	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public String getPosition() {
		return "The resistor at screen " + x + " " + y;
	}

	public int getLocationX() {
		return x;
	}

	public int getLocationY() {
		return y;
	}

	public boolean contains(Point2D p) {
		if (this.orientation == 0) {
			if (p.getX() > this.x - 70 && p.getX() < this.x + 70
					&& p.getY() > this.y - 15 && p.getY() < this.y + 15)
				return true;
		}
		if (this.orientation == 90) {
			if (p.getY() > this.y - 70 && p.getY() < this.y + 70
					&& p.getX() > this.x - 15 && p.getX() < this.x + 15)
				return true;
		}
		return false;

	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (orientation == 0) {
			g2.drawLine(x - 50, y, x - 30, y);
			g2.drawLine(x - 30, y, x - 20, y + 5);
			g2.drawLine(x - 20, y + 5, x - 10, y - 5);
			g2.drawLine(x - 10, y - 5, x, y + 5);
			g2.drawLine(x, y + 5, x + 10, y - 5);
			g2.drawLine(x + 10, y - 5, x + 20, y + 5);
			g2.drawLine(x + 20, y + 5, x + 30, y);
			g2.drawLine(x + 30, y, x + 50, y);
			g2.drawString(name + " = " + value + "ohm", x - 25, y - 8);

		} else {
			g2.drawLine(x, y - 50, x, y - 30);
			g2.drawLine(x, y - 30, x + 5, y - 20);
			g2.drawLine(x + 5, y - 20, x - 5, y - 10);
			g2.drawLine(x - 5, y - 10, x + 5, y);
			g2.drawLine(x + 5, y, x - 5, y + 10);
			g2.drawLine(x - 5, y + 10, x + 5, y + 20);
			g2.drawLine(x + 5, y + 20, x, y + 30);
			g2.drawLine(x, y + 30, x, y + 50);
			//Rotate 90 degree modify formula for Internet. 
			// Create a rotation transformation for the font.
		    AffineTransform fontAT = new AffineTransform();
		    // get the current font
		    Font theFont = g2.getFont();
		    // Derive a new font using a rotatation transform
		    fontAT.rotate(70.7);
		    Font theDerivedFont = theFont.deriveFont(fontAT);
		    // set the derived font in the Graphics2D context
		    g2.setFont(theDerivedFont);
		    // Render a string using the derived font
		    g2.drawString(name + " = " + value + "ohm", x+8, y-8);
		    // put the original font back
		    g2.setFont(theFont);
		}
			
	}

	public String toString() {
		if (value < 1000) {
			return this.name + " " + node[0] + " " + node[1] + " " + this.value
					+ "ohm";
		} else {
			return this.name + " " + node[0] + " " + node[1] + " " + this.value
					/ 1000 + "kohm";
		}
	}
	
}