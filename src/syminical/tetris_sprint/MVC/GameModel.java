package syminical.tetris_sprint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameModel {
    private final GameController PARENT;
	public InputBuffer InBuffer = new InputBuffer();
	public int bottomTime = 0;
    public Block ActiveBlock;
    public BlockType SwapBlock;
    public BlockGrid Grid;
	public boolean active = false, done = false, failed = false, force = false, swapped = false, first = false, right = false, left = false, up = false, down = false, frameReady = false, dirtyGrid = false, ActiveBlockDirty = false;
	public double startTime, endTime, endTimeTime;
	public double cstart, cend, cwstart, cwend, dtime, clock, rightDown = 0, leftDown = 0, upDown = 0, downDown = 0;
	public String endTimeS = "";
	public int fps = 0, directionTracker = 0;
	public int rowsCap = 20, mode = 0;

    public GameModel(GameController GM) { PARENT = GM; Grid = new BlockGrid(this); }

    public void begin() {
        active = true;
        clearGrid();
        newBlock();
        mode = 1;
    }
	public void restart() {
		Grid.reset();
		InBuffer.clear();
		clearGrid();
		active = false;
		first = false;
		done = false;
		failed = false;
		endTimeS = "";
		swapped = false;
		SwapBlock = null;
		newBlock();
		bottomTime = 0;
		startTime = System.currentTimeMillis();
	}
	public void again() {
		InBuffer.clear();
		Grid.reset();
		first = false;
		done = false;
		failed = false;
		endTimeS = "";
		swapped = false;
		SwapBlock = null;
		newBlock();
		active = true;
		bottomTime = 0;
		startTime = System.currentTimeMillis();
	}
    public void end() {
        fps = 0;   
    }
    public void goalReached() {
        done = true;
        active = false;
        mode = 2;
        endTimeTime = System.currentTimeMillis();
        endTime = System.currentTimeMillis() - startTime;
        //repaint();
    }
    
    /*public void drawActiveBlock() {
        ActiveBlock.drawShape(grid, 0);
        frameReady = true;
    }*/

    public void emptyInputBuffer() {
		while (InBuffer.size() > 0) {
            PARENT.View().clearActiveBlock();
            switch (InBuffer.next()) {
                case LEFT:
                    if (!BlockGrid.detectLeft(ActiveBlock))
                        ActiveBlock.influenceX(-1);
                    break;
                case RIGHT:
                    if (!BlockGrid.detectRight(ActiveBlock))
                        ActiveBlock.influenceX(1);
                    break;
                case TURN_LEFT:
                    BlockGrid.tryLeftTurn(ActiveBlock);
                    break;
                case TURN_RIGHT:
                    BlockGrid.tryRightTurn(ActiveBlock);
                    break;
                case FAST_DROP:
                    ActiveBlock.move(BlockGrid.fastDrop(ActiveBlock));
                    BlockGrid.addBlock(ActiveBlock);
                    newBlock();
                default:
            }
        }
	}
	public void clearGrid() {
        BlockGrid.clear();
	}
	public void swap() {
		if (SwapBlock == null) {
			SwapBlock = ActiveBlock.type();
			//ActiveBlockDirty = true;
			newBlock();			
		} else {
			BlockType __ = ActiveBlock.type();
            //PARENT.View().clearActiveBlock();
			ActiveBlock = newBlock(SwapBlock);
			SwapBlock = __;
		}
        swapped = true;
	}
	public void forceDrop() {
        if (BlockGrid.detectDown(ActiveBlock)) {
            bottomTime++;
            
            if (bottomTime >= 3)
                if (ActiveBlock.y() < 4) {
                    endTimeTime = System.currentTimeMillis();
                    failed = true;
                    done = true;
                    endTime = endTimeTime - startTime;
                } else {
                    BlockGrid.checkRows(ActiveBlock);
                    newBlock();
                    bottomTime = 0;
                    force = false;
                }
        } else {
            //PARENT.View().clearActiveBlock();
            ActiveBlock.influenceY(1);
        }
        frameReady = false;
	}
    public Block newBlock(BlockType __) {
        switch (__) {
            case SQUARE: __ = return new Square();
            case STICK: __ = return new Stick();
            case L_RIGHT: __ = return new L_Right();
            case L_LEFT: __ = return new L_Left();
            case ZIG_LEFT: __ = return new Tri();
            case TRI: __ = return new Zig_Right();
            case ZIG_RIGHT: __ = return new Zig_Left();
            
            default: return null;
        }
    }
    public void newBlock() {
		Block __;
        swapped = false;
        
        switch ((int)(Math.random() * 7)) {
            case 0: __ = new Square(); break;
            case 1: __ = new Stick(); break;
            case 2: __ = new L_Right(); break;
            case 3: __ = new L_Left(); break;
            case 4: __ = new Tri(); break;
            case 5: __ = new Zig_Right(); break;
            case 6: __ = new Zig_Left(); break;
            default:
        }
        
		if (ActiveBlock != null) { BlockGrid.addBlock(ActiveBlock); BlockGrid.checkRows(ActiveBlock); } 
        ActiveBlock = __;
	}

	public void getInput() {
		if (directionTracker == 0) return;
		else if (directionTracker == 1 && (System.currentTimeMillis() - rightDown) > 200) InBuffer.add(InputActionType.RIGHT);
		else if (directionTracker == -1 && (System.currentTimeMillis() - leftDown) > 200) InBuffer.add(InputActionType.LEFT);
        else if (directionTracker == 2 && (System.currentTimeMillis() - upDown) > 200) InBuffer.add(InputActionType.TURN_RIGHT);
        else if (directionTracker == -2 && (System.currentTimeMillis() - downDown) > 200) InBuffer.add(InputActionType.TURN_LEFT);
	}
    public void rightPressed() {
        InBuffer.add(InputActionType.RIGHT);
        right = true;
        directionTracker = 1;
        rightDown = System.currentTimeMillis();
    }
    public void rightReleased() {
        right = false;
        directionTracker = ((left)? -1 : ((up)? 2 : ((down)? -2 : 0)));
    }
    public void leftPressed() {
        InBuffer.add(InputActionType.LEFT);
        left = true;
        directionTracker = -1;
        leftDown = System.currentTimeMillis();
    }
    public void leftReleased() {
        left = false;
        directionTracker = ((right)? -1 : ((up)? 2 : ((down)? -2 : 0)));
    }
    public void upPressed() {
        InBuffer.add(InputActionType.TURN_LEFT);
        up = true;
        directionTracker = 2;
        upDown = System.currentTimeMillis();
    }
    public void upReleased() {
        up = false;
        directionTracker = ((down)? -1 : ((right)? 2 : ((left)? -2 : 0)));
    }
    public void downPressed() {
        InBuffer.add(InputActionType.TURN_LEFT);
        down = true;
        directionTracker = -2;
        downDown = System.currentTimeMillis();
    }
    public void downReleased() {
        down = false;
        directionTracker = ((up)? -1 : ((right)? 2 : ((left)? -2 : 0)));
    }
    public void rReleased() { restart(); }
    public void cReleased() { swap(); }

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

				if (!right) { InBuffer.add(new Movement(1)); rDown = System.currentTimeMillis(); }
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

				if (!left) { InBuffer.add(new Movement(2)); lDown = System.currentTimeMillis(); }
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
					activeShape.clearShape(grid, 0);
					InBuffer.add(new Movement(4));
				}
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		this.getActionMap().put("down", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (!down) { InBuffer.add(new Movement(0)); dDown = System.currentTimeMillis(); }
				else if (activeShape.detectDown(grid, 0) && (System.currentTimeMillis() - rDown) > 1000) { directionTracker = 0; return; }
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
				else if (active && !done) InBuffer.add(new Movement(3));
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