
//theworldisquiethere

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Sorcery extends JPanel implements KeyListener, MouseListener {

	public static JFrame box = new JFrame("Tetris Sprint");
	public static ArrayList<Shape> shapes = new ArrayList<Shape>();
	public static ArrayList<Movement> inputBuffer = new ArrayList<Movement>();
	public static int activeShape = -1;
	public static int[][] grid = new int[25][10];
	public static boolean active = false, first = false, done = false, failed = false;
	private static ArrayList<Integer> clearBuffer = new ArrayList<Integer>();
	private static double startTime, endTime, endTimeTime;
	private double cstart, cend, cwstart, cwend, dtime;
	private String endTimeS = "";
	private static boolean swapped = false;
	private int swapContainer = -1;
	private static int rowsCleared = 0, rowsCap = 20;
	private Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 32);
	private Font timeFont = new Font("Comic Sans MS", Font.PLAIN, 50);
	private static Time keeper;

	public Sorcery() {

		super();
		setBackground(Color.BLACK);
		addKeyListener(this);
		addMouseListener(this);	
		clearGrid();
		newShape();
		JOptionPane.showMessageDialog(null, "                            TETRIS SPRINT\n                                by Tream\n\n* Right / Left arrow keys to move\n\n* Down / Space / wait to go down\n\n* Up to turn\n\n* C to swap shapes\n\n* R to restart\n\n\n Clear 20 lines as fast as you can!\n\n\n\nMAY TEH ODDS BE EVER IN YOUR FAVOUR!1!one!!", "Helpful Usage Box 9000", JOptionPane.PLAIN_MESSAGE);

	}

	private void restart() {

		shapes.clear();
		inputBuffer.clear();
		activeShape = -1;
		clearGrid();
		active = false;
		first = false;
		done = false;
		failed = false;
		clearBuffer.clear();
		endTimeS = "";
		swapped = false;
		swapContainer = -1;
		rowsCleared = 0;
		keeper = null;
		newShape();
		repaint();

	}

	private void again() {

		shapes.clear();
		inputBuffer.clear();
		activeShape = -1;
		clearGrid();
		first = false;
		done = false;
		failed = false;
		clearBuffer.clear();
		endTimeS = "";
		swapped = false;
		swapContainer = -1;
		rowsCleared = 0;
		keeper = null;
		newShape();
		active = true;
		repaint();
	}

	public void paint(Graphics g) {

		super.paint(g);		

		if (active) {
			
			if (done) {

				drawEnd(g);

			} else {

				if (!first) {
				
					keeper = new Time();				
					keeper.start();
					first = true;	
					startTime = System.currentTimeMillis();

				}	

				updateScreen(g);

			}

		} else { 	

			drawLines(g);	
			drawMenu(g);		

		}

	}

	public static void checkRows() {
				
		boolean checker;

		for (int i = shapes.get(activeShape).getY() + shapes.get(activeShape).getHeight() - 1; i > shapes.get(activeShape).getY() - 1; i--) {

			checker = false;

			for (int i2 = 0; i2 < grid[0].length; i2++)

				if (grid[i][i2] == 7)

					checker = true;

			if (!checker)

				clearBuffer.add(i);

		}

	}

	private static void clearRows() {

		for (Integer container : clearBuffer) {

			for (int i = 0; i < grid[container].length; i++)

				grid[container][i] = 7;

			rowsCleared++;

		}

		deFrag();

	}

	private static void deFrag() {

		shapes.get(activeShape).clearShape(grid, 1);

		int holder = 0;
		
		while (clearBuffer.size() > 0) {

			for (int i = clearBuffer.get(0) + holder; i > 3; i--)

				for (int i2 = 0; i2 < grid[0].length; i2++)

					grid[i][i2] = grid[i - 1][i2];

			for (int i = 0; i < grid[0].length; i++)

				grid[4][i] = 7;

			holder++;
			clearBuffer.remove(0);

		}

		if (rowsCleared >= rowsCap) {

			done = true;
			endTimeTime = System.currentTimeMillis();
			endTime = System.currentTimeMillis() - startTime;

		}

	}

	private static void addShape(int container) {

		switch (container) {

			case 0: 

				shapes.add(new Square());
				
				break;

			case 1:

				shapes.add(new Stick());

				break;

			case 2:

				shapes.add(new L1());
	
				break;

			case 3:

				shapes.add(new L2());

				break;
	
			case 4:

				shapes.add(new Tri());

				break;

			case 5:

				shapes.add(new Zig1());

				break;

			case 6:

				shapes.add(new Zig2());

				break;

		}

		activeShape ++;

	}

	private void swap() {

		if (swapContainer == -1) {

			swapContainer = shapes.get(activeShape).getType();

			shapes.get(activeShape).clearShape(grid, 0);
			shapes.get(activeShape).clearShape(grid, 1);

			newShape();			

		} else {

			int temp = shapes.get(activeShape).getType();

			shapes.get(activeShape).clearShape(grid, 0);
			shapes.get(activeShape).clearShape(grid, 1);

			addShape(swapContainer);
		
			swapContainer = temp;

			swapped = true;

		}

		repaint();
	
	}

	public void forceUpdateScreen() {

		if ((System.currentTimeMillis() - dtime) > (keeper.getDelay() * 1000)) {

			if (!shapes.get(activeShape).detectDown(grid, 0)) {
				
				inputBuffer.add(0, new Movement(0));
				emptyBuffer();
				repaint();	

			} else {

				if (shapes.get(activeShape).getY() < 4) {

					failed = true;
					done = true;
					endTimeTime = System.currentTimeMillis();
					endTime = System.currentTimeMillis() - startTime;

					repaint();

				} else {

					checkRows();
					newShape();

				}

			}

		}

	}

	private void updateScreen(Graphics g) {

		cstart = System.currentTimeMillis();	
	
		if (clearBuffer.size() > 0)

			clearRows();

	
		if (activeShape != -1) 

			shapes.get(activeShape).drawShape(grid, 0);

		drawGrid(g);
		drawLines(g);

		cend = System.currentTimeMillis();

		cwstart = System.currentTimeMillis();

		while (inputBuffer.size() > 0 && (System.currentTimeMillis() - cstart) > ( 33 - (cend - cstart) ))
		
			emptyBuffer();

		cwend = System.currentTimeMillis();

		if (cwend - cstart < 33) {

			try {
  
				Thread.sleep( (33 - (int)cwend - (int)cstart ) );

			} catch (Exception e) {

				System.out.println("Sleep failed!");

			}

		}

		//repaint();

	}

	private void emptyBuffer() {

		inputBuffer.get(0).move();
		repaint();

		if (inputBuffer.get(0).getType() == 0)

			dtime = System.currentTimeMillis();

		inputBuffer.remove(0);

	}

	private void drawEnd(Graphics g) {

		drawGrid(g);
		drawLines(g);
		drawTime(g);
		drawButton(g);

	}

	private void drawMenu(Graphics g) {

		drawButton(g);

	}

	private void drawTime(Graphics g) {

		endTimeS = "" + ((((int)(endTime / 1000)) > 60 )? ("" + ((int)(endTime / 1000 / 60)) + " m ") : "") + ( ( ((int)(endTime / 1000)) % 60 > 0)? ((((int)(endTime / 1000)) % 60) + " s") : "");

		if (endTime >= 180000)

			failed = true;

		if (failed)

			g.setColor(Color.DARK_GRAY);
	
		else

			g.setColor(Color.WHITE);

		g.setFont(timeFont);
		if (endTimeS.length() > 0) {

			g.drawString("Duration", ((250 - 198) / 2), (25 * 4));
			g.drawString(endTimeS, ((250 - (endTimeS.length() * 21)) / 2), (25 * 6));
	
		}
		g.setFont(buttonFont);

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

	private void clearGrid() {

		for (int i = 0; i < grid.length; i++)
		
			for (int i2 = 0; i2 < grid[0].length; i2++)

				grid[i][i2] = 7;

	}

	private void drawGrid(Graphics g) {

		for (int i = 0; i < 20; i++) {

			for (int i2 = 0; i2 < grid[0].length; i2++) {

				if (grid[i + 4][i2] != 7) {
			
					g.setColor(Shape.colour(grid[i + 4][i2]));
					g.fillRect( (25 * i2), (25 * i), 25, 25);

				}	
		
			}
	
		}

	}

	private void drawLines(Graphics g) {

		g.setColor(Color.BLACK);

		for (int i = 0; i < 11; i++) 

			g.drawLine((i * 25), (0), (i * 25), (500));

		for (int i = 0; i < 21; i++) 

			g.drawLine((0), (i * 25), (250), (i * 25));

	}

	public static void newShape() {

		int container = (int)(Math.random() * 7);

		addShape(container);	

		shapes.get(activeShape).updateShadow();

		swapped = false;

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

		//JOptionPane.showMessageDialog(null, "x: [" + e.getX() + "] | y: [" + e.getY() + "]", "The Box", JOptionPane.PLAIN_MESSAGE);

		if (!active && e.getX() >= (48) && e.getX() <= (202) && e.getY() >= (225) && e.getY() <= (282)) {
			
			active = true;
			repaint();

		} else if (done && ((int)(System.currentTimeMillis() - endTimeTime)) > 2000 && e.getX() >= (48) && e.getX() <= (202) && e.getY() >= (225) && e.getY() <= (282))

			again();	

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		
		//newShape();
		//repaint();
		//JOptionPane.showMessageDialog(null, "width: " + box.getContentPane().getWidth() + " | height: " + box.getContentPane().getHeight(), "The Box", JOptionPane.PLAIN_MESSAGE);
		if (!active && e.getKeyCode() == KeyEvent.VK_SPACE) {

			active = true;
			repaint();

		} else if (active && !done) {

			switch (e.getKeyCode()) {
				
				case KeyEvent.VK_RIGHT:

					if (!shapes.get(activeShape).detectRight(grid, 0))	
					
						inputBuffer.add(new Movement(1));
					
					break;
	
				case KeyEvent.VK_LEFT:
	
					if (!shapes.get(activeShape).detectLeft(grid, 0))

						inputBuffer.add(new Movement(2));

					break;

				case KeyEvent.VK_UP:

					shapes.get(activeShape).clearShape(grid, 0);
					inputBuffer.add(new Movement(4));
					break;

				case KeyEvent.VK_DOWN:

					if (!shapes.get(activeShape).detectDown(grid, 0))

						inputBuffer.add(new Movement(0));

					break;

				case KeyEvent.VK_SPACE:

					inputBuffer.add(new Movement(3));
					
					break;
/*
				case 157:

					JOptionPane.showMessageDialog(null, "active: [" + active + "] | done: [" + done + "]", "The Box", JOptionPane.PLAIN_MESSAGE);
					
					break;
*/
				case 67:

					if (!swapped)

						swap();

					break;

				case 82:

					restart();
					
					break;

				default:

			 } 

			if (inputBuffer.size() > 0)

				emptyBuffer();	

		} else if (done && ((int)(System.currentTimeMillis() - endTimeTime)) > 2000 && e.getKeyCode() == KeyEvent.VK_SPACE)

			again();

	}

	public void keyTyped(KeyEvent e) {	
	
	}	

	public static void main(String args[]) {

		Sorcery pane = new Sorcery();		
		box.add(pane);
		box.setContentPane(pane);
		box.getContentPane().setPreferredSize(new Dimension(251, 501));
		box.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		box.setVisible(true);
		box.setResizable(false);
		box.pack();
		box.setLocationRelativeTo(null);
		pane.requestFocus();

	}		

}

