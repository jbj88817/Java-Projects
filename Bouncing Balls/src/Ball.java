

import java.awt.*;
import java.util.Random;

public class Ball {
	double px, py;
	double vx, vy;
	double mass;
	int radius;
	double t = 0.0;
	Color color;
	String name;
	static int count = 0;

	Ball() {
		color = new Color(255, 0, 0);
		px = 50;
		py = 60;
		vx = 8;
		vy = 9;
		name = "default";
		set_radius(17);
	}

	Ball(int px, int py, int vx, int vy, Color color) {
		this.px = px;
		this.py = py;
		this.vx = vx;
		this.vy = vy;
		this.color = color;
		name = "ball" + (++count);
		set_radius(17);
	}

	Ball(int px, int py) {
		this.px = px;
		this.py = py;
		vx = vy = 0.0;
		color = Color.blue;
		set_radius(1);
	}

	public void randomize(double v) {
		Random r = new Random();
		px = 400 * r.nextDouble();
		py = 400 * r.nextDouble();
		double theta = r.nextDouble() * 2.0 * Math.PI;
		vx = v * Math.cos(theta);
		vy = v * Math.sin(theta);
	}

	public void set_radius(int r) {
		radius = r;
		mass = r * r;
	}

	public String toString() {
		return String.format("%s pos (%.0f, %.0f) vel (%.1f %.1f) ", name, px,
				py, vx, vy);
	}

	public void move() {
		px += vx;
		py += vy;
	}

	public void move(double delta) {
		px += vx * delta;
		py += vy * delta;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int) (px - radius), (int) (py - radius), (int) 2 * radius,
				(int) 2 * radius);
		double qx, qy;
		if (t > 0.0) {
			qx = px + vx * t;
			qy = py + vy * t;
		}
	}
}
