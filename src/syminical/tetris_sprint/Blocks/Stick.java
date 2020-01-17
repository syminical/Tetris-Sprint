package syminical.tetris_sprint;

public class Stick extends Block {
    protected void initBlock() {
		height = 4; width = 1;
		x = 4; y = 0;
        endState = 1; canTurn = true;
		type = BlockType.STICK;
        grid = new grid[endState+1];
        
        grid[0] = new boolean[][] {
			{true}, 
			{true}, 
			{true},
            {true}
		};
        grid[1] = new boolean[][] {
			{true, true, true, true}
		};
    }
}