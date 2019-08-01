package syminical.tetris_sprint;

public class Zig1 extends Shapes {

	public Zig1() {

		super();

		height = 3;
		width = 2;
		type = 5;
		x = 4;
		y = 1;
		
		grid = new int[][] {
 
			{0, 1, 0, 0}, 
			{1, 1, 0, 0}, 
			{1, 0, 0, 0}, 
			{0, 0, 0, 0}

		};

	}

}

