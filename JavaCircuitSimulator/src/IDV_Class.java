

import java.awt.Graphics;

public class IDV_Class {
	private String name;
	private double value;
	public int node[] = new int[2];
	private int x, y;
	private int orientation;
	static int count = 0;

	public IDV_Class() {
		name = " ";
		orientation = 0;
		value = 0;
		node[0] = 0;
		node[1] = 0;
		x = 0;
		y = 0;
	}

	public IDV_Class addIDV(String iv) {
		String idv[] = iv.split(" ");
		this.setName(idv[1]);
		this.node[0] = Integer.parseInt(idv[2]);
		this.node[1] = Integer.parseInt(idv[3]);
		this.value = Double.parseDouble(idv[4]);
		this.setLocation(Integer.parseInt(idv[5]), Integer.parseInt(idv[6]));
		this.orientation = Integer.parseInt(idv[7]);
		return this;

	}

	public IDV_Class(String name, int m, int n, double value, int orientation) {
		this.name = name;
		this.value = value;
		this.orientation = orientation;
		node[0] = m;
		node[1] = n;
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
			return "The IDV is vertical.";
		} else if (orientation == 0) {
			return "The IDV is horizontal.";
		} else
			return "The IDV is neither horizontal nor vertical.";

	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public String getPosition() {
		return "The IDC at screen " + x + " " + y;
	}

	public int getLocationX() {
		return x;
	}

	public int getLocationY() {
		return y;
	}

	public void draw(Graphics g) {
		if (orientation == 90) {
			g.drawOval(x - 20, y - 20, 40, 40);
			g.drawLine(x, y - 20, x, y - 40);
			g.drawLine(x, y + 20, x, y + 40);
			g.drawString("-", x, y + 15);
			g.drawString("+", x - 2, y - 4);
			g.drawString(name + " = " + value + " V", x + 20, y + 20);
		} else if (orientation == 270) {
			g.drawOval(x - 20, y - 20, 40, 40);
			g.drawLine(x, y - 20, x, y - 40);
			g.drawLine(x, y + 20, x, y + 40);
			g.drawString("+", x - 1, y + 15);
			g.drawString("-", x, y - 4);
			g.drawString(name + " = " + value + " V", x + 20, y + 20);
		} else if (orientation == 0) {
			g.drawOval(x - 20, y - 20, 40, 40);
			g.drawLine(x - 20, y, x - 40, y);
			g.drawLine(x + 20, y, x + 40, y);
			g.drawString("+", x + 8, y + 6);
			g.drawString("-", x - 8, y + 6);
			g.drawString(name + " = " + value + " V", x + 20, y + 20);
		} else if (orientation == 180) {
			g.drawOval(x - 20, y - 20, 40, 40);
			g.drawLine(x - 20, y, x - 40, y);
			g.drawLine(x + 20, y, x + 40, y);
			g.drawString("-", x + 8, y + 6);
			g.drawString("+", x - 8, y + 6);
			g.drawString(name + " = " + value + " V", x + 20, y + 20);
		}

	}

	public String toString() {
		if (value < 1000)
			return "The value of " + this.name + " is " + this.value + " V.";
		else
			return "The value of " + this.name + " is " + this.value / 1000
					+ " kV.";

	}
}
