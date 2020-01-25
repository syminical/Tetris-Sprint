package syminical.tetris_sprint;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Point;

public class BlockGrid {
    private final GameModel PARENT;
    private final int HIDDEN_HEIGHT = 4;
    private final int HEIGHT = 24;
    private final int WIDTH = 10;
    private int size = 0;
    private int rowsCleared = 0;
    private ArrayList<Integer> clearBuffer = new ArrayList<Integer>();
    private BlockType[][] grid = new BlockType[HEIGHT][WIDTH];
    private int[] peaks = new int[WIDTH];
    
    public BlockGrid(GameModel __) { PARENT = __; Arrays.fill(peaks, 24); }
    
    public void addBlock(Block __) {
        if (__ == null) return;
        for (int i = 0; i < __.height; ++i)
            for (int j = 0; j < __.width; ++j)
                if (__.data()[__.state()][i][j]) {
                    if (peaks[j + __.x()] > i + __.y()) peaks[j + __.x()] = i + __.y();
                    grid[i + __.y()][j + __.x()] = __.type();
                }
        ++size;
    }
    
    public Point fastDrop(Block B) {
        if (B == null) return new Point(0, 0);
        int answer = HEIGHT;
        
        for (int i = 0; i < B.width(); ++i)
            if (peaks[B.x()+i] < answer)
                answer = peaks[B.x()+i];
        
        return new Point(B.x(), answer-B.height());
    }
    
    public boolean detectDown(Block B) {
        if (B == null) return true;
        if (B.y()+B.height() == HEIGHT) return true;
        
        for (int i = B.height()-1; i > -1; --i)
            for (int j = 0; j < B.width(); ++j)
                if (B.data()[B.state][i][j])
                    if (grid[B.y()+i+1][B.x()+j] != null)
                        return true;
        return false;
	}
	public boolean detectLeft(Block B) {
        if (B == null) return true;
        if (B.x() == 0) return true;
        
        for (int i = B.height()-1; i > -1; --i)
            for (int j = 0; j < B.width(); ++j)
                if (B.data()[B.state()][i][j])
                    if (grid[B.y()+i][B.x()+j-1] != null)
                        return true;
        return false;
	}
	public boolean detectRight(Block B) {
        if (B == null) return true;
        if (B.x()+B.width() == WIDTH) return true;
        
        for (int i = B.height()-1; i > -1; --i)
            for (int j = 0; j < B.width(); ++j)
                if (B.data()[B.state()][i][j])
                    if (grid[B.y()+i][B.x()+j+1] != null)
                        return true;
        return false;
    }
    
    public boolean tryLeftTurn(Block B) {
        if (B == null) return true;
        boolean success = true;
        B.turnLeft();
        
        for (int i = B.height()-1; i > -1; --i)
            for (int j = 0; j < B.width(); ++j)
                if (B.data()[B.state()][i][j])
                    if(grid[B.y()+i][B.x()+j] != null) {
                        success = false; break; }
        if (!success) B.turnRight();
        return success;
    }
    public boolean tryRightTurn(Block B) {
        if (B == null) return true;
        boolean success = true;
        B.turnRight();
        
        for (int i = B.height(); i > -1; --i)
            for (int j = B.width()-1; j > -1; --j)
                if (B.data()[B.state()][i][j])
                    if(grid[B.y()+i][B.x()+j] != null) {
                        success = false; break; }
        if (!success) B.turnLeft();
        return success;
    }
    
    private void deFrag() {
		//ActiveBlock.clearShape(grid, 1);
		int precision = 0;
		
		while (clearBuffer.size() > 0) {
			for (int i = clearBuffer.get(0) + precision; i > 3; i--)
				//for (int j = 0; j < WIDTH; ++j)
					grid[i] = grid[i - 1];

	/*		for (int i = 0; i < WIDTH; ++i)
				Arrays.fill(grid[4], BlockType.BLANK);*/
                grid[4] = new BlockType[WIDTH];

			++precision;
			clearBuffer.remove(0);
		}

		if (rowsCleared >= PARENT.rowsCap) PARENT.goalReached();
	}
    
    private void clearRows() {
		for (Integer container : clearBuffer) {
			grid[container] = new BlockType[WIDTH];            
			++rowsCleared;
		}
		deFrag();
	}
    public void checkRows(Block __) {
		boolean full;
        
		for (int i = __.y() + __.height() - 1; i > __.y() - 1; --i) {
			full = true;
			for (int j = 0; j < WIDTH; ++j)
				if (grid[i][j] == null) {
					full = false; break;
                }
			if (full) clearBuffer.add(i);
		}
        if (clearBuffer.size() > 0) clearRows();
	}
    
    public int HIDDEN_HEIGHT() { return HIDDEN_HEIGHT; }
    public int HEIGHT() { return HEIGHT; }
    public int WIDTH() { return WIDTH; }
    public int rowsCleared() { return rowsCleared; }
    public int size() { return size; }
    public BlockType[][] data() { return grid; }
    public void clear() { grid = new BlockType[HEIGHT + HIDDEN_HEIGHT][WIDTH]; clearBuffer.clear(); }
    public void reset() { clear(); rowsCleared = 0; }
    public String toString() {
        String __ = "";    

        for (int i = 0; i < HEIGHT; ++i) {
            __ += "[ ";
            for (int j = 0; j < WIDTH; ++j)
                __ += "" + grid[i][j] + " ";
            __ += "]\n";
        }
        
        return __;
    }
}