package syminical.tetris_sprint;

public class Tri extends Shapes {

	public Tri() {

		super();

		height = 2;
		width = 3;
		type = 4;
		x = 4;
		y = 2;
		
		grid = new int[][] {
 
			{1, 1, 1, 0}, 
			{0, 1, 0, 0}, 
			{0, 0, 0, 0}, 
			{0, 0, 0, 0}

		};

	}

}

