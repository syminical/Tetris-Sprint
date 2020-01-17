package syminical.tetris_sprint;

public class Zig_Left extends Block {
    private void initBlock() {
		height = 3; width = 2;
		x = 4; y = 1;
        end_state = 1; canTurn = true;
		type = ZIG_LEFT;
        grid = new grid[end_state+1];
        
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