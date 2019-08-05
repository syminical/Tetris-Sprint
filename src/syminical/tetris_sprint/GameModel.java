package syminical.tetris_sprint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameModel {
    private final GameManager PARENT;
    public static ArrayList<Shapes> shapes = new ArrayList<Shapes>();
	public static ArrayList<Movement> inputBuffer = new ArrayList<Movement>();
	public static int activeShape = -1, bottomTime = 0;
	public static int[][] grid = new int[25][10];
	public static boolean active = false, done = false, failed = false, force = false;
	private static ArrayList<Integer> clearBuffer = new ArrayList<Integer>();
	private static double startTime, endTime, endTimeTime;
	private double cstart, cend, cwstart, cwend, dtime, clock, rDown = 0, lDown = 0, dDown = 0;
	private String endTimeS = "";
	private static boolean swapped = false;
	private boolean right = false, left = false, down = false;
	private int swapContainer = -1, fps = 0, directionTracker = 0;
	private static int rowsCleared = 0, rowsCap = 20;
	private Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 32);
	private Font timeFont = new Font("Comic Sans MS", Font.PLAIN, 50);
    
    public GameModel(GameManager GM) { PARENT = GM; }
    
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
		newShape();
		bottomTime = 0;
		repaint();
		startTime = System.currentTimeMillis();
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
		newShape();
		active = true;
		bottomTime = 0;
		repaint();
		startTime = System.currentTimeMillis();
	}

	public void paint(Graphics g) {
		super.paint(g);		

		if (active) {
			if (done) drawEnd(g); 
			else {
				if (!first) first = true;
				shapes.get(activeShape).drawShape(grid, 0);
				drawGrid(g);
				drawLines(g);
			}
		} else { 	
			drawLines(g);	
			drawMenu(g);

			if (!first) { g.setFont(tipFont); g.setColor(Color.WHITE); g.drawString("press i for more information", 46, 480); }		
		}
		//drawFps(g);
	}

	private void begin() {
		active = true;
		repaint();
	}

	private void start() {
		double start = 0, end = start, totalTime = 0, totalFrames = 0, tracker = 0, sleepTime = 0, fUpdate = 0, mUpdate = 0, lUpdate = 0, holder;
		startTime = System.currentTimeMillis();		
        
		while (true) {
			start = System.currentTimeMillis();

			if (active && !done) {

				if ((start - mUpdate) >= 1000 && directionTracker != 2) {
					forceDrop();
					mUpdate = System.currentTimeMillis();
				}

				if ((start - lUpdate) >= 50) {
					move();
					lUpdate = System.currentTimeMillis();
				}

				if (inputBuffer.size() > 0) emptyBuffer();
			}

			repaint();
			totalFrames++;
			end = System.currentTimeMillis();
			totalTime += (end - start);
			sleepTime = (((active && !done)? (1000/30) : 1000) - (end - start));

			if (sleepTime <= 0)
				tracker += (-1) * sleepTime;
			else {
                
				if (tracker > 0) {

					if (sleepTime <= tracker) {
						tracker -= sleepTime;
						sleepTime = 0;
					} else {
						sleepTime -= tracker;
						tracker = 0;
					}
				}

				try {
					Thread.sleep((int)sleepTime);
				} catch(InterruptedException e) {
					System.out.println("[sleep fail]");
				}
			}
			totalTime += (System.currentTimeMillis() - end);

			if (totalTime >= 1000) {
				fps = (int)totalFrames;
				totalFrames = 0;
				totalTime = 0;
				end = 0;
				tracker = 0;
			}
		}
	}

	public void checkRows() {
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

	private void clearRows() {

		for (Integer container : clearBuffer) {

			for (int i = 0; i < grid[container].length; i++)
				grid[container][i] = 7;
            
			rowsCleared++;
		}
		deFrag();
	}

	private void deFrag() {
		//shapes.get(activeShape).clearShape(grid, 1);
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
			repaint();
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
		activeShape++;
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

		shapes.get(activeShape).fastDrop(1);
		shapes.get(activeShape).drawShape(grid, 1);
	
	}

	public void forceDrop() {

		if (!shapes.get(activeShape).detectDown(grid, 0)) {

			inputBuffer.add(0, new Movement(0));
			emptyBuffer();
		} else {
			bottomTime++;

			if (force || bottomTime >= 2) {

				if (shapes.get(activeShape).getY() < 4) {
					failed = true;
					done = true;
					endTimeTime = System.currentTimeMillis();
					endTime = System.currentTimeMillis() - startTime;
					repaint();
				} else {
					shapes.get(activeShape).drawShape(grid, 0);
					checkRows();
					clearRows();
					newShape();
					bottomTime = 0;
					force = false;
				}
			}
		}
	}

	private void move() {

		if (directionTracker == 0) return;

		else if (directionTracker == 1 && (System.currentTimeMillis() - rDown) > 200) inputBuffer.add(new Movement(1));

		else if (directionTracker == -1 && (System.currentTimeMillis() - lDown) > 200) inputBuffer.add(new Movement(2));

		else if (directionTracker == 2 && (System.currentTimeMillis() - dDown) > 200) inputBuffer.add(new Movement(0));

	}

	private static void emptyBuffer() {
		inputBuffer.get(0).move();
		inputBuffer.remove(0);
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
					g.setColor(Shapes.colour(grid[i + 4][i2]));
					g.fillRect( (25 * i2), (25 * i), 25, 25);
				}	
			}
		}
	}

	public static void newShape() {
		int container = (int)(Math.random() * 7);
		addShape(container);	
		shapes.get(activeShape).updateShadow();
		swapped = false;
	}

	private void listeners() {
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {	
				//JOptionPane.showMessageDialog(null, "x: [" + e.getX() + "] | y: [" + e.getY() + "]", "The Box", JOptionPane.PLAIN_MESSAGE);

				if (!active && e.getX() >= (48) && e.getX() <= (202) && e.getY() >= (225) && e.getY() <= (282)) begin();
				else if (!active && e.getX() >= 46 && e.getX() <= 208 && e.getY() >= 470 && e.getY() <= 479)

					if (infoBox.isVisible()) infoBox.setVisible(false); 
					else {
						infoBox.setLocationRelativeTo(null);
						infoBox.setVisible(true); 
					}
				else if (done && ((int)(System.currentTimeMillis() - endTimeTime)) > 2000 && e.getX() >= (48) && e.getX() <= (202) && e.getY() >= (225) && e.getY() <= (282))
					again();
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
		this.getActionMap().put("right", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (!right) { inputBuffer.add(new Movement(1)); rDown = System.currentTimeMillis(); }
				right = true;
				directionTracker = 1;
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "notRight");
		this.getActionMap().put("notRight", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				right = false;

				if (left) directionTracker = -1; else directionTracker = 0;
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
		this.getActionMap().put("left", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (!left) { inputBuffer.add(new Movement(2)); lDown = System.currentTimeMillis(); }
				left = true;
				directionTracker = -1;
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "notLeft");
		this.getActionMap().put("notLeft", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
					left = false;

					if (right) directionTracker = 1; else directionTracker = 0;
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
		this.getActionMap().put("up", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (active && !done) {
					shapes.get(activeShape).clearShape(grid, 0);
					inputBuffer.add(new Movement(4));
				}
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		this.getActionMap().put("down", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (!down) { inputBuffer.add(new Movement(0)); dDown = System.currentTimeMillis(); }
				else if (shapes.get(activeShape).detectDown(grid, 0) && (System.currentTimeMillis() - rDown) > 1000) { directionTracker = 0; return; }
				down = true;
				directionTracker = 2;
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "notDown");
		this.getActionMap().put("notDown", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				down = false;
				directionTracker = 0;
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "space");
		this.getActionMap().put("space", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (!active) begin();
				else if (active && !done) inputBuffer.add(new Movement(3));
				else if (done && ((int)(System.currentTimeMillis() - endTimeTime)) > 2000) again();
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "c");
		this.getActionMap().put("c", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (!swapped)
					swap();
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "r");
		this.getActionMap().put("r", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (active && !done) restart();
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "i");
		this.getActionMap().put("i", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (infoBox.isVisible()) infoBox.setVisible(false); 
				else {
					infoBox.setLocationRelativeTo(null);
					infoBox.setVisible(true); 
				}
			}
		});
	}
    
    //view API
    public boolean active() { return active; }
    public boolean done() { return done; }
    public ArrayList<Shapes> Shapes() { return shapes; }
    public int activeShape() { return activeShape; }
}