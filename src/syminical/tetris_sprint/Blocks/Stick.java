package syminical.tetris_sprint;

public class Stick extends Block {
    private void initBlock() {
		height = 4; width = 1;
		x = 4; y = 0;
        end_state = 1; canTurn = true;
		type = STICK;
        grid = new grid[end_state+1];
        
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