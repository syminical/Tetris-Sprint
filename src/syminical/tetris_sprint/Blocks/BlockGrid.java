package syminical.tetris_sprint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.awt.Point;

public class BlockGrid {
    private final GameModel PARENT;
    private final int HIDDEN_HEIGHT = 4;
    private final int HEIGHT = 24;
    private final int WIDTH = 10;
    private int size = 0;
    private int rowsCleared = 0;
    private ArrayList<Integer> ClearBuffer = new ArrayList<Integer>();
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
        int answer = HEIGHT-B.height();
        
        for (int i = 0; i < B.width(); ++i)
            for (int j = B.height()-1; j > -1; --j)
                if (B.data()[B.state()][j][i]) {
                    if (peaks[B.x()+i]-j-1 < answer)
                        answer = peaks[B.x()+i]-j-1;
                    break;
                }
        
        return new Point(B.x(), answer);
    }
    
    public boolean detectDown(Block B) {
        if (B == null) return true;
        if (B.y()+B.height() == HEIGHT) return true;
        
        for (int i = B.height()-1; i > -1; --i)
            for (int j = 0; j < B.width(); ++j)
                if (B.data()[B.state()][i][j])
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
        
        for (int i = B.height()-1; i > -1; --i)
            for (int j = B.width()-1; j > -1; --j)
                if (B.data()[B.state()][i][j])
                    if(grid[B.y()+i][B.x()+j] != null) {
                        success = false; break; }
        if (!success) B.turnLeft();
        return success;
    }
    private void updatePeaks() {
        for (int i = 0; i < WIDTH; ++i)
            if (grid[peaks[i]][i] == null) {
                int j = peaks[i]+1;
                while (j < HEIGHT && grid[j][i] == null) ++j;
                peaks[i] = j;
            }
    }
    private void deFrag() {
		//ActiveBlock.clearShape(grid, 1);
		int precision = 0;
		while (ClearBuffer.size() > 0) {
			for (int i = ClearBuffer.get(0) + precision; i > 3; i--)
				//for (int j = 0; j < WIDTH; ++j)
					grid[i] = grid[i - 1];

	/*		for (int i = 0; i < WIDTH; ++i)
				Arrays.fill(grid[4], BlockType.BLANK);*/
                grid[4] = new BlockType[WIDTH];

			++precision;
			ClearBuffer.remove(0);
		}
        updatePeaks();
        
		if (rowsCleared >= PARENT.rowsCap) PARENT.goalReached();
	}
    private void clearRows() {
        for (Integer __ : ClearBuffer) {
            Arrays.fill(grid[__], null);
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
			if (full) ClearBuffer.add(i);
		}
        if (ClearBuffer.size() > 0) clearRows();
	}
    
    public int HIDDEN_HEIGHT() { return HIDDEN_HEIGHT; }
    public int HEIGHT() { return HEIGHT; }
    public int WIDTH() { return WIDTH; }
    public int rowsCleared() { return rowsCleared; }
    public int size() { return size; }
    public BlockType[][] data() { return grid; }
    public int[] peaks() { return peaks; }
    public void clear() { grid = new BlockType[HEIGHT + HIDDEN_HEIGHT][WIDTH]; ClearBuffer.clear(); }
    public void reset() { clear(); rowsCleared = 0; Arrays.fill(peaks, 24); }
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