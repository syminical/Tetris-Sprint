package syminical.tetris_sprint;

import java.awt.Graphics;
import java.awt.Color;

public class Shapes {

	protected int id, type, height, width, x, y, sy, mx = -1, my = -1;
	protected boolean saved = false;
	protected int[][] grid = new int[4][4];
	protected static int globalId = 0;

	public Shapes() {
		
		id = globalId;
		globalId++;

	}	

	public boolean detectDown(int[][] container, int container2) {

		boolean answer = false;
		int spot, yvar = ((container2 == 0)? y : sy);

		for (int i = 0; i < width; i++) {

			spot = 0;

			for (int i2 = height - 1; i2 > -1; i2--)

				if (grid[i2][i] == 1) {

					spot = i2;

					break;

				}

			if (grid[spot][i] == 1) {

				if ( (yvar + height == 24) || ((yvar + spot + 1) <= 23 && (container[yvar + spot + 1][x + i] != 7 && container[yvar + spot + 1][x + i] != 8))) {

					answer = true;

				}

			}

		}

		return answer;

	}

	public boolean detectLeft(int[][] container, int container2) {

		boolean answer = false;
		int spot, yvar = ((container2 == 0)? y : sy);
		
		for (int i = 0; i < height; i++) {

			spot = 0;

			for (int i2 = 0; i2 < width; i2++)

				if (grid[i][i2] == 1) {

					spot = i2;

					break;

				}

			if (!((x + spot - 1) > -1) || (x + spot - 1) >= 0 && container[yvar + i][x + spot - 1] != 7 && container[yvar + i][x + spot - 1] != 8)

				answer = true;	

		}	

		return answer;
		
	}

	public boolean detectRight(int[][] container, int container2) {

		boolean answer = false;
		int spot, yvar = ((container2 == 0)? y : sy);

		for (int i = 0; i < height; i++) {

			spot = 0;
	
			for (int i2 = (width - 1); i2 > -1; i2--)

				if (grid[i][i2] == 1) {

					spot = i2;
					
					break;

				}

			if (!((x + spot + 1) < 10) || (x + spot - 1) <= 9 && container[yvar + i][x + spot + 1] != 7 && container[yvar + i][x + spot - 1] != 8)

				answer = true;

		}

		return answer;

	}

	public void clearShape(int[][] container, int container2) {

		int x2 = x, y2 = ((container2 == 0)? y : sy);

		for (int i = 0; i < height; i++) {
		
			for (int i2 = 0; i2 < width; i2++) {

				if (grid[i][i2] != 0) {

					container[y2][x2] = 7;

				}
	
				x2++;

			}

			x2 = x;
			y2++;
		
		} 

	}

	private void bounce() {

		if (x + width > 10) {

			mx = x;
			x = 10 - width;
			saved = true;

		}

		if (x < 0) {

			mx = x;
			x = 0;
			saved = true;

		}

		if (y < 0) {

			my = y;
			y = 0;
			saved = true;
	
		}

		if (y + height > 24) {

			my = y;
			y = 24 - height;
			saved = true;

		}

		for (int i = 0; i < height; i++)

			for (int i2 = 0; i2 < width; i2++)

				if (grid[i][i2] == 1 && Sorcery.grid[y + i][x + i2] != 7 && Sorcery.grid[y + i][x + i2] != 8)
		
					turnLeft();

	}

	public void updateShadow() {

		//clearShape(Sorcery.grid, 1);
		fastDrop(1);
		drawShape(Sorcery.grid, 1);

	}
	
	public void drawShape(int[][] container, int container2) {

		int x2 = x, y2 = ((container2 == 0)? y : sy);

		for (int i = 0; i < height; i++) {
		
			for (int i2 = 0; i2 < width; i2++) {

				if (grid[i][i2] != 0) {

					container[y2][x2] = ((container2 == 0)? type : 8);

				}
	
				x2++;

			}

			x2 = x;
			y2++;
		
		}

	}

	public void fastDrop(int container) {

		if (container == 0) {

			while(!detectDown(Sorcery.grid, 0))

				influenceY(1);

			//Sorcery.shapes.get(Sorcery.activeShape).drawShape(Sorcery.grid, 0);
			Sorcery.force = true;
			((Sorcery)(Sorcery.box.getContentPane())).forceDrop();

		} else {

			sy = y;

			while(!detectDown(Sorcery.grid, 1))

				sy += 1;
		
		}

	}

	public void influenceX(int container) {

		fastDrop(1);

		x += container;

	}

	public void influenceY(int container) {

		y += container;

	}

	public void turnRight() {

		if (saved) {

			saved = false;

			if (mx != -1)

				x = mx;

			if (my != -1)

				y = my;

			mx = -1;
			my = -1;

		}

		int[][] temp = new int[4][4];

		for (int i = 0; i < width; i++)

			for (int i2 = height - 1; i2 > -1; i2--)

				temp[i][i2] = grid[Math.abs(i2 - (height - 1))][i];

		switchDim();

		for (int i = 0; i < height; i++)

			for (int i2 = 0; i2 < width; i2++)

				grid[i][i2] = temp[i][i2];

		bounce();

		sy = y;
		fastDrop(1);

	}

	public void turnLeft() {

		int[][] temp = new int[4][4];

		for (int i = width - 1; i > -1; i--)

			for (int i2 = height - 1; i2 > -1; i2--)

				temp[i][i2] = grid[Math.abs(i2 - (height - 1))][i];

		switchDim();

		for (int i = 0; i < height; i++)

			for (int i2 = 0; i2 < width; i2++)

				grid[i][i2] = temp[i][i2];

		bounce();

	}

	private void switchDim() {

		int h = width, w = height;
		height = h;
		width = w;

	}

	public static Color colour(int container) {

		switch(container) {

			case 0:

				return new Color(255, 255, 255);

			case 1:

				return new Color(235, 235, 235);

			case 2:

				return new Color(215, 215, 215);

			case 3:

				return new Color(195, 195, 195);

			case 4:

				return new Color(175, 175, 175);

			case 5:

				return new Color(155, 155, 155);

			case 6:

				return new Color(135, 135, 135);

			case 7:

				return Color.BLACK;

			case 8:

				return new Color(70, 70, 70);
				//return Color.GRAY;

			default: 
		
				return Color.WHITE;				
	
		}

	}

	public String toString() {

		return "x: [" + x + "] | y: [" + y + "] | sy: [" + sy + "] | height: [" + height + "] | width: [" + width + "]";

	}

	public int getType() {

		return type;
		
	}

	public int getX() {

		return x;

	}

	public int getY() {

		return y;

	}

	public int getHeight() {
		
		return height;

	}

}

