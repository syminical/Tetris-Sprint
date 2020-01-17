package syminical.tetris_sprint;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

public abstract class Block {

	protected int height, width, x, y, shadowY, state = 0, endState = 0;
    protected BlockType type;
	protected boolean canTurn = false;
	protected boolean[][][] grid;//state, y, x
    protected Color BlockColour;
    /*protected boolean saved = false;
    int mx = -1, my = -1;*/

	public Block() { initBlock(); }
    
    //must init: grid, type
    protected abstract void initBlock();
    
    //public API
	/*public void clearShape(int[][] container, int container2) {

		int x2 = x, y2 = ((container2 == 0)? y : sy);

		for (int i = 0; i < height; i++) {
		
			for (int i2 = 0; i2 < width; i2++) {

				if (grid[i][i2] != 0) {

					container[y2][x2] = 7;

				}
	
				x2++;

			}

			x2 = x;
			y2++;
		
		} 

	}*/
    
    public void turnRight() {
		if (!canTurn) return;
        
        if (state == endState) state = 0;
        else ++state;
        
        switchDim();
		//bounce();

		updateShadow(y);
	}
	public void turnLeft() {
		if (!canTurn) return;
        
        if (state == 0) state = endState;
        else --state;
        
        switchDim();
		//bounce();
	}
    private void switchDim() {
		int t = width;
        width = height;
        height = t;
	}
	/*private void bounce() {
		if (x + width > 10) {
			mx = x;
			x = 10 - width;
			saved = true;
		}
		if (x < 0) {
			mx = x;
			x = 0;
			saved = true;
		}
		if (y < 0) {
			my = y;
			y = 0;
			saved = true;
		}
		if (y + height > 24) {
			my = y;
			y = 24 - height;
			saved = true;
		}
		for (int i = 0; i < height; i++)
			for (int i2 = 0; i2 < width; i2++)
				if (grid[i][i2] == 1 && Sorcery.grid[y + i][x + i2] != 7 && Sorcery.grid[y + i][x + i2] != 8)
					turnLeft();
	}*/
    
    public void move(Point __) { x = (int)(__.getX()); y = (int)(__.getY()); shadowY = y; }
    public void updateShadow(int __) { shadowY = __; }
	public void influenceX(int __) { x += __; }
	public void influenceY(int __) { y += __;	}

	public static Color colour(BlockType __) {
		switch(__) {
			case SQUARE:
				return new Color(255, 255, 255);
			case STICK:
				return new Color(235, 235, 235);
			case L_RIGHT:
				return new Color(215, 215, 215);
			case L_LEFT:
				return new Color(195, 195, 195);
			case TRI:
				return new Color(175, 175, 175);
			case ZIG_RIGHT:
				return new Color(155, 155, 155);
			case ZIG_LEFT:
				return new Color(135, 135, 135);
			case BLANK:
				return Color.BLACK;
			case 8:
				return new Color(70, 70, 70);
				//return Color.GRAY;
			default: 
				return Color.WHITE;
		}
	}
	public BlockType type() { return type; }
	public int x() { return x; }
	public int y() { return y; }
    public int shadowY() { return shadowY; }
	public int height() { return height; }
    public int width() { return width; }
    public int state() { return state; }
    public boolean[][][] data() { return grid; }
    public String toString() { return "x: [" + x + "] | y: [" + y + "] | shadowY: [" + shadowY + "] | height: [" + height + "] | width: [" + width + "] | state: [" + state + "]"; }
}