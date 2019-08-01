package syminical.tetris_sprint;

public class Stick extends Shapes {

	public Stick() {

		super();

		height = 4;
		width = 1;
		type = 1;
		x = 4;
		y = 0;

		grid = new int[][] {

			{1, 0, 0, 0}, 
			{1, 0, 0, 0}, 
			{1, 0, 0, 0}, 
			{1, 0, 0, 0}

		};

	}

}

