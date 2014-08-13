

import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Jama.Matrix;

public class JavaCircuitSimulator extends JPanel {
	static ArrayList<Resistor> reslist;
	static ArrayList<IDC_Class> idclist;
	static ArrayList<Wire_Class> wirelist;
	static ArrayList<IDV_Class> idvlist;

	private static void print(String s) {
		System.out.print(s);
	}

	public static void main(String[] args) {
		BufferedReader bufr = null;
		reslist = new ArrayList<Resistor>();
		idclist = new ArrayList<IDC_Class>();
		wirelist = new ArrayList<Wire_Class>();
		idvlist = new ArrayList<IDV_Class>();
		try {
			bufr = new BufferedReader(new FileReader("input.txt"));
			String temp = "";

			while ((temp = bufr.readLine()) != null) {
				System.out.println(temp);
				String delims = "\n";
				String[] tokens = temp.split(delims);

				for (int i = 0; i < tokens.length; i++) {
					if (tokens[i].contains("RES")) {
						String ress = tokens[i];
						Resistor r = new Resistor();
						reslist.add(r.addResistor(ress));
					} else if (tokens[i].contains("IDC")) {
						String idcs = tokens[i];
						IDC_Class idc = new IDC_Class();
						idclist.add(idc.addIDC(idcs));
					} else if (tokens[i].contains("WIRE")) {
						String wirs = tokens[i];
						Wire_Class wir = new Wire_Class();
						wirelist.add(wir.addWire(wirs));
					} else if (tokens[i].contains("IDV")) {
						String idvs = tokens[i];
						IDV_Class idv = new IDV_Class();
						idvlist.add(idv.addIDV(idvs));
					}
				}
			}

			// node analysis

			int nmax = 0; // max nodes

			for (Resistor res : reslist) {
				for (int i = 0; i < 2; i++)
					if (res.node[i] > nmax)
						nmax = res.node[i];
			}
			System.out.println(" ");
			System.out.format("Max node: %d%n", nmax);
			int n = nmax;
			int m = nmax;
			int p = nmax;
			for (IDV_Class idv : idvlist) {
				nmax += 1;
			}

			Matrix A = new Matrix(nmax, nmax);
			int na, nb;

			double g;
			for (Resistor res : reslist) {
				na = res.node[0] - 1;
				nb = res.node[1] - 1;
				g = 1 / res.getValue();
				if (na >= 0) {
					A.set(na, na, A.get(na, na) + g);
					if (nb >= 0)
						A.set(na, nb, A.get(na, nb) - g);
				}
				if (nb >= 0) {
					A.set(nb, nb, A.get(nb, nb) + g);
					if (na >= 0)
						A.set(nb, na, A.get(nb, na) - g);
				}
			}

			for (IDV_Class idv : idvlist) {
				na = idv.node[0] - 1;
				nb = idv.node[1] - 1;
				if (na >= 0) {
					A.set(n, na, -1);
					A.set(na, n, -1);
				}
				if (nb >= 0) {
					A.set(n, nb, 1);
					A.set(nb, n, 1);
				}
				n++;
			}
			System.out.println(" ");
			print("A = ");
			A.print(8, 4);

			Matrix b = new Matrix(nmax, 1);
			int ia, ib;
			for (IDC_Class idc : idclist) {
				ia = idc.node[0] - 1;
				ib = idc.node[1] - 1;
				double i = idc.getValue();
				if (ia >= 0) {
					b.set(ia, 0, b.get(ia, 0) - i);
				}
				if (ib >= 0) {
					b.set(ib, 0, b.get(ib, 0) + i);
				}
			}
			for (IDV_Class idv : idvlist) {
				b.set(m, 0, idv.getValue());
				m++;
			}
			print("b = ");
			b.print(8, 4);
			Matrix x = A.solve(b);
			/*
			 * print("x = "); x.print(8, 4);
			 */
			for (int i = 0; i < p; i++) {
				System.out.println("The node " + (i + 1) + " voltage is "
						+ x.get(i, 0)+"V");
			}

		} catch (IOException e) {
			System.out.println("IO error");
		}

		JavaCircuitSimulator panel = new JavaCircuitSimulator();
		JFrame application = new JFrame("Java Circuit Simulator");
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.add(panel);
		application.setSize(500, 400);
		application.setLocation(400, 200);
		application.setVisible(true);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Resistor res : reslist) {
			res.draw(g);
		}
		for (IDC_Class idc : idclist) {
			idc.draw(g);
		}
		for (IDV_Class idv : idvlist) {
			idv.draw(g);
		}
		for (Wire_Class wire : wirelist) {
			if (wire.getLength() == 5)
				wire.draw2(g);
			if (wire.getLength() == 7)
				wire.draw3(g);
			if (wire.getLength() == 9)
				wire.draw4(g);
		}
	}

}
