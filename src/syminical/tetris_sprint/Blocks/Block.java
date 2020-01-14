package syminical.tetris_sprint;

import java.awt.Graphics;
import java.awt.Color;

public abstract class Block {

	protected int height, width, x, y, sy, state = 0, endState = 0;
    protected BlockType type;
	protected canTurn = false;
	protected boolean[][][] grid;//state, y, x
    protected Color BlockColour;
    /*protected boolean saved = false;
    int mx = -1, my = -1;*/

	public Block() { initBlock(); }
    
    //must init: grid, type, BlockColour
    private abstract void initBlock();
    
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
		if (!canTurn) reutrn;
        
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
    
    public void setBlock(Point __) { x = __.getX(); y = __.getY(); }
    public void updateShadow(int __) { shadow_y = __; }
	public void influenceX(int __) { x += container; }
	public void influenceY(int __) { y += container;	}

	public static Color colour(int __) {
		switch(__) {
			case 0:
				return new Color(255, 255, 255);
			case BlockType.L_RIGHT:
				return new Color(235, 235, 235);
			case 2:
				return new Color(215, 215, 215);
			case 3:
				return new Color(195, 195, 195);
			case 4:
				return new Color(175, 175, 175);
			case 5:
				return new Color(155, 155, 155);
			case 6:
				return new Color(135, 135, 135);
			case BlockType.BLANK:
				return Color.BLACK;
			case 8:
				return new Color(70, 70, 70);
				//return Color.GRAY;
			default: 
				return Color.WHITE;
		}
	}
	public int getType() { return type; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int height() { return height; }
    public int width() { Return width; }
    public boolean[][][] data() { return grid; }
    public String toString() { return "x: [" + x + "] | y: [" + y + "] | sy: [" + sy + "] | height: [" + height + "] | width: [" + width + "]"; }
}