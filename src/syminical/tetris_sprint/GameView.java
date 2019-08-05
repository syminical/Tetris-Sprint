package syminical.tetris_sprint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameView extends JPanel {
    private final GameManager PARENT;
	private Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 32);
	private Font timeFont = new Font("Comic Sans MS", Font.PLAIN, 50);
    private boolean first = false;
    
    public GameView(GameManager GM) {
        super();
        PARENT = GM;
		listeners();
		setBackground(Color.BLACK);
		clearGrid();
		newShape();
    }
    
    public void paint(Graphics g) {
		super.paint(g);		

		if (PARENT.Model().active()) {
			if (PARENT.Model().done()) drawEnd(g); 
			else {
				if (!first) first = true;
				PARENT.Model().shapes().get(PARENT.Model().shapes().activeShape()).drawShape(grid, 0);
				drawGrid(g);
				drawLines(g);
			}
		} else { 	
			drawLines(g);	
			drawMenu(g);

			if (!first) { g.setFont(tipFont); g.setColor(Color.WHITE); g.drawString("press i for more information", 46, 480); }		
		}
		//drawFps(g);
	}
    
    private void drawEnd(Graphics g) {
		drawGrid(g);
		drawLines(g);
		drawTime(g);
		drawButton(g);
	}

	private void drawMenu(Graphics g) {
		drawButton(g);
	}

	private void drawWindow(Graphics g) {
		g.setColor(new Color(200, 200, 200));
		g.fillRoundRect( 17, 51, 224, 114, 20, 20);
		g.setColor(Color.BLACK);
		g.fillRoundRect( 19, 54, 219, 107, 20, 20);
		g.setColor(new Color(150, 150, 150));
		g.drawRoundRect( 21, 57, 214, 100, 20, 20);
	}

	private void drawTime(Graphics g) {
		drawWindow(g);
		endTimeS = "" + ((((int)(endTime / 1000)) >= 60 ) ? ("" + ((int)(endTime / 1000 / 60)) + " m ") : "") + ((((int)(endTime / 1000)) % 60 > 0) ? ((((int)(endTime / 1000)) % 60) + " s") : ((((int)(endTime / 1000)) >= 60) ? "" : "0 s"));
		g.setColor(Color.WHITE);
		g.setFont(timeFont);
        
		if (endTimeS.length() > 0) {
			g.drawString("Duration", ((250 - 198) / 2), (25 * 4));
			g.drawString(endTimeS, ((240 - (endTimeS.length() * 21)) / 2), (25 * 6));
		}
		g.setFont(buttonFont);
	}

	private void drawButton(Graphics g) {
		g.setColor(new Color(200, 200, 200));
		g.fillRoundRect( (47), (222), (155), (57), 40, 40 );
		g.setColor(Color.BLACK);
		g.fillRoundRect( (25 * 2), (25 * 9), (25 * 6), (25 * 2), 40, 40 ); 	
		g.setColor(new Color(150, 150, 150));
		g.drawRoundRect( (52), (228), (145), (43), 40, 40);	
		g.setColor(Color.WHITE);
		g.setFont(buttonFont);
		g.drawString("Click Me", (25 * 3 - 16), (261)); 
    }
    
    private void clearGrid() {

		for (int i = 0; i < grid.length; i++)
		
			for (int i2 = 0; i2 < grid[0].length; i2++)
				grid[i][i2] = 7;
	}

	private void drawGrid(Graphics g) {

		for (int i = 0; i < 20; i++) {

			for (int i2 = 0; i2 < grid[0].length; i2++) {

				if (grid[i + 4][i2] != 7) {
					g.setColor(Shapes.colour(grid[i + 4][i2]));
					g.fillRect( (25 * i2), (25 * i), 25, 25);
				}	
			}
		}
	}

	private void drawFps(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("fps: [" + fps + "]", 5, 15);
	}

	private void drawLines(Graphics g) {
		g.setColor(Color.BLACK);

		for (int i = 0; i < 11; i++) 
			g.drawLine((i * 25), (0), (i * 25), (500));

		for (int i = 0; i < 21; i++)
			g.drawLine((0), (i * 25), (250), (i * 25));
	}
}