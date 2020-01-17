package syminical.tetris_sprint;

public class L_Left extends Block {
    protected void initBlock() {
		height = 3; width = 2;
		x = 4; y = 1;
        endState = 3; canTurn = true;
		type = BlockType.L_LEFT;
        grid = new grid[endState+1];
        
        grid[0] = new boolean[][] {
			{false, true}, 
			{false, true}, 
			{true, true}
		};
        grid[1] = new boolean[][] {
			{true, false, false},
            {true, true, true}
		};
        grid[2] = new boolean[][] {
			{true, true}, 
			{true, false}, 
			{true, false}
		};
        grid[3] = new boolean[][] {
			{true, true, true},
            {false, false, true}
		};   
    }
}