import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
import javax.swing.*;

class controlPanel extends JPanel {
	JButton b1;
	JTextField tf1;
	JTextField tf2;
	JLabel lb;
	JLabel lb2;
	JButton b2;
	JButton b22;
	JButton b3;
	JButton b4;
	JButton b5;

	controlPanel() {

		b1 = new JButton("setVelocity");
		tf1 = new JTextField(5);
		tf2 = new JTextField(5);
		lb = new JLabel("Left");
		lb2 = new JLabel("Right");
		b2 = new JButton("Stop!");
		//b3 = new JButton("Turn around!");
		b4 = new JButton("Turn Left!");
		b5 = new JButton("Turn Right!");
		add(b2);
		//add(b3);
		add(lb);
		add(tf1);
		add(lb2);
		add(tf2);
		add(b1);
		add(b4);
		add(b5);

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double a = Double.parseDouble(tf1.getText());
					double b = Double.parseDouble(tf2.getText());
					if (a > 85 || b > 85) {
						System.out
								.println("Overspeeding!(Need number less than 85)");
						game.robot.setVelocity(0, 0);
					} else
						game.robot.setVelocity(b, a);
				} catch (NumberFormatException err) {
					System.out
							.println("Please Input speed number in TextField.");
				}
				b2.setText("Stop!");
				repaint();
			}
		});

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (b2.getText() == "Stop!") {
					game.robot.setVelocity(0, 0);
					b2.setText("Start!");
				} else if (b2.getText() == "Start!") {
					b2.setText("Stop!");
					game.robot.setVelocity(120, 120);
				}
			}
		});

		/*b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.robot.theta -= 180;

			}
		});*/
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.robot.vr += 10;
				b2.setText("Stop!");

			}
		});
		b5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.robot.vl += 10;
				b2.setText("Stop!");
			}
		});
		b2.setMnemonic(KeyEvent.VK_DOWN);
		b4.setMnemonic(KeyEvent.VK_LEFT);
		b5.setMnemonic(KeyEvent.VK_RIGHT);
	}
}

public class game {
	static Gameboard panel;
	static Robot robot;

	public void start() {
		panel.startAnimation();
	}

	public void stop() {
		panel.stopAnimation();
	}

	public static void main(String[] args) {
		panel = new Gameboard();
		robot = panel.robot;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				controlPanel CPanel = new controlPanel();
				JFrame frame = new JFrame("Final Game");
				JFrame frame_1 = new JFrame("ControlPanel");
				frame.add(panel);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(760, 540);
				frame.setVisible(true);
				frame_1.add(CPanel);
				frame_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame_1.setSize(380, 110);
				// frame_1.pack();
				frame_1.setVisible(true);
				frame_1.setLocation(750, 200);
				panel.startAnimation();
				panel.startAnimation();
			}
		});
	}
}
