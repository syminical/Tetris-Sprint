package syminical.tetris_sprint;

public class BlockGrid {
    private final int HIDDEN_HEIGHT = 5;
    private final int HEIGHT = 20;
    private final int WIDTH = 10;
    private BlockType[][] grid = new int[HEIGHT + HIDDEN_HEIGHT][WIDTH];
    private BlockType[] peaks = new int[WIDTH];
    
    public void addBlock(Block __) {
        for (int i = __.height(); i < __.height; ++i)
            for (int j = __.width(); j < __.width; ++j)
                if (__.data()[i][j]) {
                    if (peaks[j] > i) peaks[j] = i;                    
                    grid[i][j] = __.type();
                }
    }
    
    public point fastDrop(Block B) {
        int answer = HEIGHT;
        
        for (int i = 0; i < B.width(); ++i)
            if (peaks[B.x()+i] < answer)
                answer = peaks[B.x()+i];
        
        return new Point(B.x(), answer);
    }
    
    public boolean detectDown(Block B) {
        if (B.y()+B.height() == HEIGHT) return true;
        
        for (int i = B.height()-1; i > -1; --i)
            for (int j = 0; j < B.width(); ++j)
                if (B.data()[i][j])
                    if (grid[B.y()+i+1][B.x()+j] != BlockType.BLANK)
                        return true;
	}
	public boolean detectLeft(Block B) {
        if (B.x() == 0) return true;
        
        for (int i = B.height()-1; i > -1; --i)
            for (int j = 0; j < B.width(); ++j)
                if (B.data()[i][j])
                    if (grid[B.y()+i][B.x()+j-1] != BlockType.BLANK)
                        return true;
	}
	public boolean detectRight(Block B) {
        if (B.x()+B.width() == WIDTH) return true;
        
        for (int i = B.height()-1; i > -1; --i)
            for (int j = 0; j < B.width(); ++j)
                if (B.data()[i][j])
                    if (grid[B.y()+i][B.x()+j+1] != BlockType.BLANK)
                        return true;
    }
    
    public boolean tryLeftTurn(Block B) {
        boolean success = true;
        B.turnLeft();
        
        for (int i = B.height()-1; i > -1; --i)
            for (int j = 0; j < B.width(); ++j)
                if (B.data()[i][j])
                    if(grid[B.y()+i][B.x()+j] != BlockType.BLANK) {
                        success = false; break; }
        if (!success) B.turnRight();
        return success;
    }
    public boolean tryRightTurn(Block B) {
        boolean success = true;
        B.turnRight();
        
        for (int i = B.height(); i > -1; --i)
            for (int j = B.width()-1; j > -1; --j)
                if (B.data()[i][j])
                    if(grid[B.y()+i][B.x()+j] != BlockType.BLANK) {
                        success = false; break; }
        if (!success) B.turnLeft();
        return success;
    }
    
    public int HIDDEN_HEIGHT() { return HIDDEN_HEIGHT; }
    public int HEIGHT() { return HEIGHT; }
    public int WIDTH() { return WIDTH; }
    public BlockType[][] data() { return grid; }
}