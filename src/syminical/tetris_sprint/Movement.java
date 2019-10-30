package syminical.tetris_sprint;

public class Movement {

	private int type;

	public Movement(int container) {

		type = container;

	}

	public void move() {

		magic(type);

	}

	private void magic(int container) {

		Sorcery.shapes.get(Sorcery.activeShape).clearShape(Sorcery.grid, 0);

		switch(container) {
			case 0:
				down();
				break;
			case 1:
				Sorcery.shapes.get(Sorcery.activeShape).clearShape(Sorcery.grid, 1);
				right();
				Sorcery.shapes.get(Sorcery.activeShape).updateShadow();
				break;
			case 2:
				Sorcery.shapes.get(Sorcery.activeShape).clearShape(Sorcery.grid, 1);
				left();
				Sorcery.shapes.get(Sorcery.activeShape).updateShadow();
				break;
			case 3:
				fastDrop();
				break;
			case 4:
				Sorcery.shapes.get(Sorcery.activeShape).clearShape(Sorcery.grid, 1);
				turnRight();
				Sorcery.shapes.get(Sorcery.activeShape).updateShadow();
				break;
			default:
		}

	}

	private void down() {

		if (!Sorcery.shapes.get(Sorcery.activeShape).detectDown(Sorcery.grid, 0))

			Sorcery.shapes.get(Sorcery.activeShape).influenceY(1);

	}

	private void right() {

		if (!Sorcery.shapes.get(Sorcery.activeShape).detectRight(Sorcery.grid, 0))

			Sorcery.shapes.get(Sorcery.activeShape).influenceX(1);	

	}

	private void left() {

		if (!Sorcery.shapes.get(Sorcery.activeShape).detectLeft(Sorcery.grid, 0))

			Sorcery.shapes.get(Sorcery.activeShape).influenceX(-1);

	}

	private void fastDrop() {

		Sorcery.shapes.get(Sorcery.activeShape).fastDrop(0);

	}

	private void turnRight() {

		Sorcery.shapes.get(Sorcery.activeShape).turnRight();

	}

	public int getType() {

		return type;

	}

}

