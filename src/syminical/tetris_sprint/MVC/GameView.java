package syminical.tetris_sprint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameView extends JPanel {
    private final GameController PARENT;
    private int mode = 0;
	private Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 32);
	private Font timeFont = new Font("Comic Sans MS", Font.PLAIN, 50);
    private static Font tipFont = new Font("Comic Sans MS", Font.BOLD, 12);
    
    public GameView(GameController GM) {
        super();
        PARENT = GM;
		setBackground(Color.BLACK);
    }
    
    /*public void mode(int n) {
        switch (n) {
            case 1: mode = 1; repaint(); break;
            case 2: mode = 2; break;
            default: mode = 0;
        }
    }*/
    
    //View methods
    @Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
        
        switch (PARENT.Model().mode) {
            case 1:
                if (PARENT.Model().dirtyGrid) {
                    clear(g);
                    drawGrid(g);
                }
                drawActiveShape(g);
				drawLines(g);
                break;
            case 2:
                drawEnd(g);
                break;
            default:
                drawLines(g);	
                drawButton(g);
                g.setFont(tipFont); g.setColor(Color.WHITE); g.drawString("press i for more information", 46, 480);
                
        }
		drawFps(g);
	}
    
    private void clear(Graphics g) {
        g.setColor(Block.colour(BlockType.BLANK));
        g.fillRect(0, 0, 25 * PARENT.Model().Grid.WIDTH(), 25 * PARENT.Model().Grid.HEIGHT());
    }
    private void drawEnd(Graphics g) {
		drawGrid(g);
		drawLines(g);
		drawTime(g);
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
		PARENT.Model().endTimeS = "" + ((((int)(PARENT.Model().endTime / 1000)) >= 60 ) ? ("" + ((int)(PARENT.Model().endTime / 1000 / 60)) + " m ") : "") + ((((int)(PARENT.Model().endTime / 1000)) % 60 > 0) ? ((((int)(PARENT.Model().endTime / 1000)) % 60) + " s") : ((((int)(PARENT.Model().endTime / 1000)) >= 60) ? "" : "0 s"));
		g.setColor(Color.WHITE);
		g.setFont(timeFont);
        
		if (PARENT.Model().endTimeS.length() > 0) {
			g.drawString("Duration", ((250 - 198) / 2), (25 * 4));
			g.drawString(PARENT.Model().endTimeS, ((240 - (PARENT.Model().endTimeS.length() * 21)) / 2), (25 * 6));
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
	private void drawGrid(Graphics g) {
        for (int i = 0; i < 20; i++)
			for (int i2 = 0; i2 < PARENT.Model().Grid.data()[0].length; i2++)
				if (PARENT.Model().Grid.data()[i + 4][i2] != BlockType.BLANK) {
					g.setColor(Block.colour(PARENT.Model().Grid.data()[i + 4][i2]));
					g.fillRect( (25 * i2), (25 * i), 25, 25);
				}
    }
    /*private void clearActiveBlock(Graphics g) {
        Block AB = PARENT.Model().ActiveBlock;
        g.setColor(Block.colour(BlockType.BLANK));
        
        for (int i = 0; i < AB.height(); ++i)
            for (int j = 0; j < AB.width(); ++j)
                if (AB.data()[AB.state()][i][j]) {
                    g.fillRect(25 * (j + AB.x()), 25 * (i + AB.y() - 4), 25, 25);
                    g.fillRect(25 * (j + AB.x()), 25 * (i + AB.shadowY() - 4), 25, 25);
                }
    }*/
    private void drawActiveBlock(Graphics g) {
        Block AB = PARENT.Model().ActiveBlock;
        g.setColor(Block.colour(AB.type()));
        
        //if (PARENT.Model().ActiveBlockDirty) clearActiveBlock(g);
        
        for (int i = 0; i < AB.height(); ++i)
            for (int j = 0; j < AB.width(); ++j)
                if (AB.data()[AB.state()][i][j])
                    g.fillRect((25 * (j + AB.x())), (25 * (i + AB.y() - 4)), 25, 25);
    }
	private void drawFps(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("fps: [" + PARENT.Model().fps + "]", 5, 15);
	}
	private void drawLines(Graphics g) {
		g.setColor(Color.BLACK);

		for (int i = 0; i < 11; i++) 
			g.drawLine((i * 25), (0), (i * 25), (500));

		for (int i = 0; i < 21; i++)
			g.drawLine((0), (i * 25), (250), (i * 25));
	}
}