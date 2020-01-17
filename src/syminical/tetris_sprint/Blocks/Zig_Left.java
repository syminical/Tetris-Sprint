package syminical.tetris_sprint;

public class Zig_Left extends Block {
    protected void initBlock() {
		height = 3; width = 2;
		x = 4; y = 1;
        endState = 1; canTurn = true;
		type = BlockType.ZIG_LEFT;
        grid = new grid[endState+1];
        
        grid[0] = new boolean[][] {
			{true, false},
			{true, true}, 
            {false, true}
		};
        grid[1] = new boolean[][] {
			{false, true, true},
            {true, true, false}			
		};
    }
}