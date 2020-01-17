package syminical.tetris_sprint;

public class Tri extends Block {
    protected void initBlock() {
		height = 2; width = 3;
		x = 4; y = 2;
        endState = 3; canTurn = true;
		type = BlockType.TRI;
        grid = new grid[endState+1];
        
        grid[0] = new boolean[][] {
			{true, true, true}, 
			{false, true, false}
		};
        grid[1] = new boolean[][] {
			{false, true},
			{true, true},
            {false, true}
		};
        grid[2] = new boolean[][] {
			{false, true, false},
            {true, true, true}
		};
        grid[3] = new boolean[][] {
			{true, false},
			{true, true},
            {true, false}
		};   
    }
}