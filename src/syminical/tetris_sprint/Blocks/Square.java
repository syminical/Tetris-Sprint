package syminical.tetris_sprint;

public class Square extends Shapes {

	public Square() {

		super();

		height = 2;
		width = 2;
		type = 0;
		x = 4;
		y = 2;
		
		grid = new int[][] {

			{1, 1, 0, 0}, 
			{1, 1, 0, 0}, 
			{0, 0, 0, 0}, 
			{0, 0, 0, 0}

		};

	}	

}

