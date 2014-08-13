

import java.awt.Graphics;

public class Wire_Class {
	private int a, b, c, d, e, f, g, h, l;

	public Wire_Class addWire(String wi) {
		String wire[] = wi.split(" ");
		if (wire.length == 5) {
			a = Integer.parseInt(wire[1]);
			b = Integer.parseInt(wire[2]);
			c = Integer.parseInt(wire[3]);
			d = Integer.parseInt(wire[4]);
			l = 5;
		} else if (wire.length == 7) {
			a = Integer.parseInt(wire[1]);
			b = Integer.parseInt(wire[2]);
			c = Integer.parseInt(wire[3]);
			d = Integer.parseInt(wire[4]);
			e = Integer.parseInt(wire[5]);
			f = Integer.parseInt(wire[6]);
			l = 7;
		} else if (wire.length == 9) {
			a = Integer.parseInt(wire[1]);
			b = Integer.parseInt(wire[2]);
			c = Integer.parseInt(wire[3]);
			d = Integer.parseInt(wire[4]);
			e = Integer.parseInt(wire[5]);
			f = Integer.parseInt(wire[6]);
			g = Integer.parseInt(wire[7]);
			h = Integer.parseInt(wire[8]);
			l = 9;
		}
		return this;

	}

	public int getLength() {
		return l;
	}

	public void draw2(Graphics g) {
		g.drawLine(a, b, c, d);
	}

	public void draw3(Graphics g) {
		g.drawLine(a, b, c, d);
		g.drawLine(c, d, e, f);
	}

	public void draw4(Graphics g2) {
		g2.drawLine(a, b, c, d);
		g2.drawLine(c, d, e, f);
		g2.drawLine(e, f, g, h);
	}
}