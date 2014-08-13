

public class Collision implements Comparable<Collision> {
	double timestep;
	Ball ball, ball2;
	WallSeg seg = null;
	int ndx;

	Collision(double t, Ball b, Ball b2) {
		timestep = t;
		ball = b;
		ball2 = b2;
		ndx = -1;
	}

	Collision(double t, Ball b, int ndx) {
		timestep = t;
		ball = b;
		ball2 = null;
		this.ndx = ndx;
	}

	Collision(double t, Ball b, WallSeg seg) {
		timestep = t;
		ball = b;
		ball2 = null;
		this.seg = seg;
		ndx = 5;
	}

	public String toString() {
		String str = String.format("dt %6.2f ", timestep);
		if (ndx == -1)
			str += " BALL  ";
		else if (ndx == -2)
			str += " PT  ";
		else if (ndx < 4)
			str += " WIN ";
		else if (ndx == 5)
			str += " SEG  ";
		str += ball;
		return str;
	}

	public int compareTo(Collision other) {
		// Collision c = (Collision) other;
		if (timestep > other.timestep)
			return 1;
		if (timestep < other.timestep)
			return -1;
		return 0;
	}

	void update_velocity() {
		if (ndx == -1)
			ball_collision();
		else if (ndx == -2)
			pt_collision();
		else if (ndx == 5)
			seg_collision();
		else
			wall_collision();
	}

	void seg_collision() {
		double kx = seg.kx;
		double ky = seg.ky;
		double px = ball.px - seg.pt0.px;
		double py = ball.py - seg.pt0.py;
		double dist;
		// distance to line
		dist = (kx * py - ky * px);
		double vperp = kx * ball.vy - ky * ball.vx;
		double vtran = kx * ball.vx + ky * ball.vy;
		vperp = -vperp;
		ball.vx = kx * vtran - ky * vperp;
		ball.vy = kx * vperp + ky * vtran;
	}

	void wall_collision() {
		if (ndx < 2)
			ball.vx = -ball.vx;
		else
			ball.vy = -ball.vy;
	}

	void pt_collision() {
		// relative position
		double px = ball.px - ball2.px;
		double py = ball.py - ball2.py;
		// unit vector along line of centers
		double vnorm = Math.hypot(px, py);
		px = px / vnorm;
		py = py / vnorm;
		// velocity component (normal)
		double v1 = ball.vx * px + ball.vy * py;
		// velocity component (transverse)
		double qx = py;
		double qy = -px;
		double u1 = ball.vx * qx + ball.vy * qy;
		// calculate changed velocity
		double v1f = -v1;
		// back to original coordinates.
		ball.vx = v1f * px + u1 * qx;
		ball.vy = v1f * py + u1 * qy;
	}

	void ball_collision() {
		double eta = 1.0; // coefficient of restitution
		// relative position
		double px = ball2.px - ball.px;
		double py = ball2.py - ball.py;
		// unit vector along line of centers
		double vnorm = Math.hypot(px, py);
		px = px / vnorm;
		py = py / vnorm;
		double m1 = ball.mass;
		double m2 = ball2.mass;
		double mt = m1 + m2;
		// velocity components (normal)
		double v1 = ball.vx * px + ball.vy * py;
		double v2 = ball2.vx * px + ball2.vy * py;
		// velocity components (transverse)
		double qx = py;
		double qy = -px;
		double u1 = ball.vx * qx + ball.vy * qy;
		double u2 = ball2.vx * qx + ball2.vy * qy;
		// calculate changed velocity
		double v1f = ((eta + 1.0) * m2 * v2 + v1 * (m1 - eta * m2)) / mt;
		double v2f = ((eta + 1.0) * m1 * v1 + v2 * (m2 - eta * m1)) / mt;
		// back to original coordinates.
		ball.vx = v1f * px + u1 * qx;
		ball.vy = v1f * py + u1 * qy;
		ball2.vx = v2f * px + u2 * qx;
		ball2.vy = v2f * py + u2 * qy;
	}
}
