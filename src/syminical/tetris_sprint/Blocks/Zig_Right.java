package syminical.tetris_sprint;

public class Zig_Right extends Block {
    protected void initBlock() {
		height = 3; width = 2;
		x = 4; y = 1;
        endState = 1; canTurn = true;
		type = BlockType.ZIG_RIGHT;
        grid = new grid[endState+1];
        
        grid[0] = new boolean[][] {
			{false, true}, 
			{true, true}, 
			{true, false}
		};
        grid[1] = new boolean[][] {
			{true, true, false},
			{false, true, true}
		};
    }
}