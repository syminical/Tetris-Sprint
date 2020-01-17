package syminical.tetris_sprint;

public class Tri extends Block {
    private void initBlock() {
		height = 2; width = 3;
		x = 4; y = 2;
        end_state = 3; canTurn = true;
		type = TRI;
        grid = new grid[end_state+1];
        
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