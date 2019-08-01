package syminical.tetris_sprint;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Test extends JPanel implements MouseListener {

	static JFrame box = new JFrame("The Box");
	Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 32);
	String endTimeS;
	double endTime = 109000;
	boolean failed = false;
	private Font timeFont = new Font("Comic Sans MS", Font.PLAIN, 50);

	public Test() {

		super();
		this.setBackground(Color.BLACK);
		addMouseListener(this);

	}

	private void drawLines(Graphics g) {

		g.setColor(Color.WHITE);

		for (int i = 0; i < 11; i++) 

			g.drawLine((i * 25), (0), (i * 25), (500));

		for (int i = 0; i < 21; i++) 

			g.drawLine((0), (i * 25), (250), (i * 25));

	}

	private void drawTime(Graphics g) {

		drawWindow(g);

		endTimeS = "" + ((((int)(endTime / 1000)) > 60 )? ("" + ((int)(endTime / 1000 / 60)) + " m ") : "") + ( ( ((int)(endTime / 1000)) % 60 > 0)? ((((int)(endTime / 1000)) % 60) + " s") : "");

		if (endTime >= 180000)

			failed = true;

		if (failed)

			g.setColor(Color.RED);
	
		else

			g.setColor(Color.GREEN);

		g.setFont(timeFont);
		if (endTimeS.length() > 0) {

			g.drawString("Duration", ((250 - 198) / 2), (25 * 4));
			g.drawString(endTimeS, ((250 - (endTimeS.length() * 24)) / 2), (25 * 6));
	
		}
		g.setFont(buttonFont);

	}		

	private void drawWindow(Graphics g) {

		g.setColor(Color.YELLOW);
		g.fillRoundRect( 17, 51, 224, 114, 20, 20);
		g.setColor(Color.BLACK);
		g.fillRoundRect( 19, 54, 219, 107, 20, 20);
		g.setColor(Color.YELLOW);
		g.drawRoundRect( 21, 57, 214, 100, 20, 20);
	}

	private void drawButton(Graphics g) {

		g.setColor(Color.YELLOW);
		g.fillRoundRect( (47), (222), (155), (57), 40, 40 );
		g.setColor(Color.BLACK);
		g.fillRoundRect( (25 * 2), (25 * 9), (25 * 6), (25 * 2), 40, 40 ); 	
		g.setColor(Color.YELLOW);
		g.drawRoundRect( (52), (228), (145), (43), 40, 40);	
		//g.setColor(Color.WHITE);
		g.setFont(buttonFont);
		g.drawString("BEGIN", (25 * 3 - 1), (261)); 

	}	

	public void paint(Graphics g) {
		
		super.paint(g);
		//drawLines(g);
		drawTime(g);
		drawButton(g);	

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}	

	public void mouseReleased(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

		JOptionPane.showMessageDialog(null, "x: [" + e.getX() + "] | y: [" + e.getY() + "]", "The Box", JOptionPane.PLAIN_MESSAGE);

	}

	public static void main(String args[]) {

			Test pane = new Test();
			box.add(pane);
			box.setContentPane(pane);
			box.getContentPane().setBackground(Color.BLACK);
			box.getContentPane().setPreferredSize(new Dimension(251, 501));
			box.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			box.setVisible(true);
			box.setResizable(false);
			box.pack();
			box.setLocationRelativeTo(null);

	}

}

