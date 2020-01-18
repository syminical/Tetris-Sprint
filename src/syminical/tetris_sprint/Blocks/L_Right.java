package syminical.tetris_sprint;

public class L_Right extends Block {
    protected void initBlock() {
		height = 3; width = 2;
		x = 4; y = 1;
        endState = 3; canTurn = true;
		type = BlockType.L_RIGHT;
        grid = new boolean[endState+1][][];
        
        grid[0] = new boolean[][] {
			{true, false}, 
			{true, false}, 
			{true, true}
		};
        grid[1] = new boolean[][] {
			{true, true, true},
			{true, false, false}
		};
        grid[2] = new boolean[][] {
			{true, true}, 
			{false, true}, 
			{false, true}
		};
        grid[3] = new boolean[][] {
			{false, false, true},
			{true, true, true}
		};   
    }
}