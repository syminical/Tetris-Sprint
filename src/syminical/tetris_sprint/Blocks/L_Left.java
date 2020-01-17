package syminical.tetris_sprint;

public class L_Left extends Block {
    private void initBlock() {
		height = 3; width = 2;
		x = 4; y = 1;
        end_state = 3; canTurn = true;
		type = L_LEFT;
        grid = new grid[end_state+1];
        
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