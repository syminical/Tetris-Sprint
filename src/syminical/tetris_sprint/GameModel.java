package syminical.tetris_sprint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameModel {
    private final GameController PARENT;
    public ArrayList<Shapes> shapes = new ArrayList<Shapes>();
	public ArrayList<Movement> inputBuffer = new ArrayList<Movement>();
	public int activeShape = -1, bottomTime = 0;
	public int[][] grid = new int[25][10];
	public boolean active = false, done = false, failed = false, force = false, swapped = false, first = false, right = false, left = false, frameReady = false;
	public ArrayList<Integer> clearBuffer = new ArrayList<Integer>();
	public double startTime, endTime, endTimeTime;
	public double cstart, cend, cwstart, cwend, dtime, clock, rDown = 0, lDown = 0;
	public String endTimeS = "";
	public int swapContainer = -1, fps = 0, directionTracker = 0;
	public int rowsCleared = 0, rowsCap = 20, mode = 0;
    
    public GameModel(GameController GM) { PARENT = GM; }
    
    public void begin() {
        active = true;
        clearGrid();
        newShape();
        mode = 1;
    }
    
	public void restart() {
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
		startTime = System.currentTimeMillis();
	}

	public void again() {
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
		startTime = System.currentTimeMillis();
	}

    public void end() {
        fps = 0;   
    }
    
    public void drawShapes() {
        shapes.get(activeShape).drawShape(grid, 0);
        frameReady = true;
    }
    
	private void checkRows() {
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
			//repaint();
		}
	}

	private void addShape(int container) {

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

	public void swap() {

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
			/*inputBuffer.add(0, new Movement(0));
			emptyBuffer();*/
            shapes.get(activeShape).clearShape(grid, 0);
            shapes.get(activeShape).influenceY(1);
		} else {
			bottomTime++;

			if (bottomTime >= 2) {

				if (shapes.get(activeShape).getY() < 4) {
					failed = true;
					done = true;
					endTimeTime = System.currentTimeMillis();
					endTime = System.currentTimeMillis() - startTime;
					//repaint();
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
        frameReady = false;
	}

	public void getInput() {

		if (directionTracker == 0) return;

		else if (directionTracker == 1 && (System.currentTimeMillis() - rDown) > 200) inputBuffer.add(new Movement(1));

		else if (directionTracker == -1 && (System.currentTimeMillis() - lDown) > 200) inputBuffer.add(new Movement(2));

	}

	public void emptyInputBuffer() {
		while (InputBuffer.size() > 0) {
            switch (InputBuffer.next()) {
                case InputBuffer.LEFT:
                case InputBuffer.RIGHT:
                case InputBuffer.TURN_LEFT:
                case InputBuffer.TURN_RIGHT:
                case InputBuffer.FAST_DROP;
                default:
            }
        }
	}

	public void clearGrid() {

		for (int i = 0; i < grid.length; i++)
		
			for (int i2 = 0; i2 < grid[0].length; i2++)
				grid[i][i2] = 7;
	}

	public void newShape() {
		int container = (int)(Math.random() * 7);
		addShape(container);	
		shapes.get(activeShape).updateShadow();
		swapped = false;
	}

	/*private void listeners() {
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
	}*/
}