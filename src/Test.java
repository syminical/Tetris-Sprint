
//theworldisquiethere

import java.awt.*;
import javax.swing.*;

public class Test extends JPanel {

	static JFrame box = new JFrame("The Box");
	Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 32);

	public Test() {

		super();
		this.setBackground(Color.BLACK);

	}

	private void drawLines(Graphics g) {

		g.setColor(Color.WHITE);

		for (int i = 0; i < 11; i++) 

			g.drawLine((i * 25), (0), (i * 25), (500));

		for (int i = 0; i < 21; i++) 

			g.drawLine((0), (i * 25), (250), (i * 25));

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
		drawButton(g);	

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

