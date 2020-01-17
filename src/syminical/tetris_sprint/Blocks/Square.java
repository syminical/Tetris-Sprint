package syminical.tetris_sprint;

public class Square extends Block {
    protected void initBlock() {
		height = 2; width = 2;
		x = 4; y = 2;
        endState = 0; canTurn = false;
		type = BlockType.SQUARE;
        grid = new grid[endState+1];
        
        grid[0] = new boolean[][] {
			{true, true}, 
			{true, true}
		};
    }
}