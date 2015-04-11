
//theworldisquiethere

import java.awt.Graphics;
import java.awt.Color;

public class Shape {

	protected int id, type, height, width, x, y, sy;
	protected int[][] grid = new int[4][4];
	protected static int globalId = 0;

	public Shape() {
		
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

			if (grid[spot][i] == 1)

				if ( (yvar + height == 24) || ((yvar + spot + 1) <= 23 && (container[yvar + spot + 1][x + i] != 7 && container[yvar + spot + 1][x + i] != 8))) {

					answer = true;

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

		if (x + width > 10)

			x = 10 - width;

		if (x < 0)

			x = 0;

		if (y < 0)

			y = 0;

		if (y + height > 24)

			y = 24 - height;


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

			Sorcery.shapes.get(Sorcery.activeShape).drawShape(Sorcery.grid, 0);
			Sorcery.checkRows();
			Sorcery.newShape();

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

				return Color.YELLOW;

			case 1:

				return Color.CYAN;

			case 2:

				return Color.PINK;

			case 3:

				return Color.BLUE;

			case 4:

				return Color.MAGENTA;

			case 5:

				return Color.GREEN;

			case 6:

				return Color.RED;

			case 7:

				return Color.BLACK;

			case 8:

				return Color.GRAY;

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

	public int getY() {

		return y;

	}

	public int getHeight() {
		
		return height;

	}

}

