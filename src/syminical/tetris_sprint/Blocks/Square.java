package syminical.tetris_sprint;

public class Square extends Block {
    private void initBlock() {
		height = 2; width = 2;
		x = 4; y = 2;
        end_state = 0; canTurn = false;
		type = SQUARE;
        grid = new grid[end_state+1];
        
        grid[0] = new boolean[][] {
			{true, true}, 
			{true, true}
		};
    }
}