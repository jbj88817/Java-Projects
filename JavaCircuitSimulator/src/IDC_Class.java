

import java.awt.Graphics;

public class IDC_Class {
	private String name;
	private double value;
	public int node[] = new int[2];
	private int x, y;
	private int orientation;
	static int count = 0;

	public IDC_Class() {
		name = " ";
		orientation = 0;
		value = 0;
		node[0] = 0;
		node[1] = 0;
		x = 0;
		y = 0;
	}

	public IDC_Class addIDC(String id) {
		String idc[] = id.split(" ");
		this.setName(idc[1]);
		this.node[0] = Integer.parseInt(idc[2]);
		this.node[1] = Integer.parseInt(idc[3]);
		this.value = Double.parseDouble(idc[4]);
		this.setLocation(Integer.parseInt(idc[5]), Integer.parseInt(idc[6]));
		this.orientation = Integer.parseInt(idc[7]);
		return this;

	}

	public IDC_Class(String name, int m, int n, double value, int orientation) {
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
			return "The IDC is vertical.";
		} else if (orientation == 0) {
			return "The IDC is horizontal.";
		} else
			return "The IDC is neither horizontal nor vertical.";

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
				g.drawLine(x, y - 40, x, y + 40);
				g.drawLine(x, y -20, x - 8, y);
				g.drawLine(x, y - 20, x + 8, y);
				g.drawString(name + " = " + value + " A", x + 20, y + 20);
			} else if(orientation == 270){
				g.drawOval(x - 20, y - 20, 40, 40);
				g.drawLine(x, y - 40, x, y + 40);
				g.drawLine(x+8, y , x , y+20);
				g.drawLine(x-8, y , x , y+20);
				g.drawString(name + " = " + value + " A", x + 20, y + 20);
			}else if (orientation == 0) {
				g.drawOval(x - 20, y - 20, 40, 40);
				g.drawLine(x - 40, y, x + 40, y);
				g.drawLine(x + 20, y, x, y - 8);
				g.drawLine(x + 20, y, x, y +8);
				g.drawString(name + " = " + value + " A", x + 20, y + 20);
			} else if (orientation == 180){
				g.drawOval(x - 20, y - 20, 40, 40);
				g.drawLine(x - 40, y, x + 40, y);
				g.drawLine(x - 20, y, x, y + 8);
				g.drawLine(x - 20, y, x, y - 8);
				g.drawString(name + " = " + value + " A", x + 20, y + 20);
			}
		
	}

	public String toString() {
		if (value < 1000)
			return "The value of " + this.name + " is " + this.value + " A.";
		else
			return "The value of " + this.name + " is " + this.value / 1000
					+ " kA.";

	}

}
