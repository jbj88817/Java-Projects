// by Bojie Jiang

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.*;

import javax.swing.Timer;
import javax.swing.*;

class TestPanel_4 extends JPanel {
	Timer myTimer;
	ArrayList<Ball> balls;
	ArrayList<Collision> collisions;
	int width, height;
	WallSeg wallseg;
	ArrayList<Ball> fixedPts;
	// JLabel label = new JLabel();
	int wait, mode;
	int time;
	boolean pause = false;

	TestPanel_4(int mode) {
		this.mode = mode;
		balls = new ArrayList<Ball>();
		collisions = new ArrayList<Collision>();
		Ball ball1 = new Ball(50, 50, 7, 5, Color.RED);
		// ball1.randomize(10);
		balls.add(ball1);
		balls.add(new Ball(300, 800, -5, 8, Color.BLUE));
		balls.add(new Ball(30, 200, -9, 7, Color.GREEN));
		fixedPts = new ArrayList<Ball>();
		Ball pt1 = new Ball(180, 100);
		fixedPts.add(pt1);
		Ball pt2 = new Ball(180, 250);
		fixedPts.add(pt2);
		wallseg = new WallSeg(pt1, pt2);
		time = 0;
	}

	public void update_collision_list(double tstep) {
		Collision c;
		collisions.clear();
		for (Ball ball : balls) {
			c = intersect_window(ball, tstep);
			if (c != null)
				collisions.add(c);
			c = wallseg.intersect(ball, tstep);
			if (c != null)
				collisions.add(c);
			for (Ball pt : fixedPts) {
				c = intersect_balls(ball, pt, tstep);
				if (c != null) {
					collisions.add(c);
					c.ndx = -2;
				}
			}
		}
		int nballs = balls.size();
		for (int i = 0; i < nballs - 1; i++) {
			for (int j = i + 1; j < nballs; j++) {
				Ball b1 = balls.get(i);
				Ball b2 = balls.get(j);
				c = intersect_balls(b1, b2, tstep);
				if (c != null)
					collisions.add(c);
			}
		}
		// label.setText(wallseg.str);
	}

	public void update() {
		width = getWidth();
		height = getHeight();
		double tstep, tmore;
		tmore = 1.0;

		while (tmore > 0.0) {
			update_collision_list(tmore);
			if (collisions.size() > 0) {
				java.util.List<Collision> list = collisions;
				Collections.<Collision> sort(list);
				Collision c = collisions.get(0);
				// if (c.ndx==5) System.out.println("time " + time + "  " + c);
				tstep = c.timestep;
				tmore = tmore - tstep;
				for (Ball ball : balls)
					ball.move(tstep);
				c.update_velocity();
			} else {
				tstep = tmore;
				tmore = 0.0;
				for (Ball ball : balls)
					ball.move(tstep);
			}
		}
		time++;
		repaint();
	}

	Collision intersect_window(Ball ball, double tstep) {
		double[] t = new double[4];
		double tmax = 1000;
		if (ball.vx < 0) {
			t[0] = (ball.px - ball.radius) / (-ball.vx);
		} else
			t[0] = tmax;
		if (ball.vx > 0) {
			t[1] = (width - ball.px - ball.radius) / (ball.vx);
		} else
			t[1] = tmax;
		if (ball.vy < 0) {
			t[2] = (ball.py - ball.radius) / (-ball.vy);
		} else
			t[2] = tmax;
		if (ball.vy > 0) {
			t[3] = (height - ball.py - ball.radius) / ball.vy;
		} else
			t[3] = tmax;
		int idx = 0;
		double tx = t[0];
		for (int i = 1; i < 4; i++) {
			if (t[i] > tx)
				continue;
			tx = t[i];
			idx = i;
		}
		if (tx > tstep) {
			return null; // does not intersect
		}
		return new Collision(tx, ball, idx);
	}

	Collision intersect_balls(Ball ball1, Ball ball2, double tstep) {
		// relative velocity
		double rx = ball2.vx - ball1.vx;
		double ry = ball2.vy - ball1.vy;
		// relative position
		double px = ball2.px - ball1.px;
		double py = ball2.py - ball1.py;
		// test radius
		double r = ball1.radius + ball2.radius - 1;
		double C = px * px + py * py - r * r;
		if (C < 0)
			return null; // already intersecting
		double B = rx * px + ry * py;
		double A = rx * rx + ry * ry;
		if (A <= 0.0)
			return null; // no relative velocity
		// quadratic At^2 + 2Bt + C = 0
		double radical = B * B - A * C;
		if (radical <= 0.0)
			return null; // no intersection (balls miss)
		double R = Math.sqrt(radical);
		double t;
		if (B > 0)
			t = (-B + R) / A;
		else
			t = -(B + R) / A;
		if (t <= 0.0 || t > tstep)
			return null; // no intersection within time limit
		return new Collision(t, ball1, ball2);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		wallseg.draw(g);
		for (Ball pt : fixedPts)
			pt.draw(g);
		for (Ball ball : balls)
			ball.draw(g);
	}

	public void startAnimation() {
		if (myTimer == null) {
			myTimer = new Timer(25, new TimerHandler());
			myTimer.start();
		} else if (!myTimer.isRunning())
			myTimer.restart();
	}

	public void stopAnimation() {
		myTimer.stop();
	}

	private class TimerHandler implements ActionListener {
		public void actionPerformed(ActionEvent actionevent) {
			update();
		}
	}
}

public class BouncingBalls extends JApplet {
	TestPanel_4 panel;

	public void init() {
		int n;
		String arg = getParameter("mode");
		if (arg != null)
			n = Integer.parseInt(arg);
		else
			n = 0;
		final int mode = n;
		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					panel = new TestPanel_4(mode);
					add(panel);
				}
			});
		} catch (Exception e) {
		}
	}

	public void start() {
		panel.startAnimation();
	}

	public void stop() {
		panel.stopAnimation();
	}

	public static void main(String[] args) {
		int n = args.length;
		if (n > 0) {
			n = Integer.parseInt(args[0]);
		}
		final int mode = n;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				TestPanel_4 panel = new TestPanel_4(mode);
				JFrame frame = new JFrame("Bouncing Balls");
				frame.add(panel);
				frame.setLocation(400, 200);
				// frame.add(panel.label,BorderLayout.SOUTH);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(400, 400);
				frame.setVisible(true);
				panel.startAnimation();
			}
		});
	}
}
