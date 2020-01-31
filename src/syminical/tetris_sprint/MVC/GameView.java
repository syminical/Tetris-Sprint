package syminical.tetris_sprint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameView extends JPanel {
    private final GameController PARENT;
    private int State = 0;
	private Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 32);
	private Font timeFont = new Font("Comic Sans MS", Font.PLAIN, 50);
    private static Font tipFont = new Font("Comic Sans MS", Font.BOLD, 12);
    
    public GameView(GameController GM) {
        super();
        PARENT = GM;
		setBackground(Color.BLACK);
    }
    
    /*public void State(int n) {
        switch (n) {
            case 1: State = 1; repaint(); break;
            case 2: State = 2; break;
            default: State = 0;
        }
    }*/
    
    //View methods
    @Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
        
        switch (PARENT.Model().State) {
            case RUNNING:
                //if (PARENT.Model().dirtyGrid) {
                    clear(g);
                    drawGrid(g);
                //}
                
                drawActiveBlock(g);
                drawPeaks(g);
				drawLines(g);
                break;
            case END:
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
        g.setColor(Block.colour(null));
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
        BlockGrid __ = PARENT.Model().Grid;
        
        for (int i = __.HEIGHT(); i > __.HIDDEN_HEIGHT(); --i)
			for (int j = 0; j < __.WIDTH(); ++j)
				if (__.data()[i][j] != null) {
					g.setColor(Block.colour(__.data()[i][j]));
					g.fillRect( (25 * j), (25 * (i - __.HIDDEN_HEIGHT())), 25, 25);
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
        int offset = PARENT.Model().Grid.HIDDEN_HEIGHT();
        
        //if (PARENT.Model().ActiveBlockDirty) clearActiveBlock(g);
        
        if (AB.shadowNeedsUpdate()) {
            AB.updateShadow((int)(PARENT.Model().Grid.fastDrop(AB).getY()));
        }
        for (int i = 0; i < AB.height(); ++i)
            for (int j = 0; j < AB.width(); ++j)
                if (AB.data()[AB.state()][i][j]) {
                    g.setColor(Block.colour(BlockType.SHADOW));
                    g.fillRect((25 * (j + AB.x())), (25 * (i + AB.shadowY() - offset)), 25, 25);
                    g.setColor(Block.colour(AB.type()));
                    g.fillRect((25 * (j + AB.x())), (25 * (i + AB.y() - offset)), 25, 25);
                }
    }
    private void drawPeaks(Graphics g) {
        g.setColor(Color.RED);
        for (int i = 0; i < PARENT.Model().Grid.WIDTH(); ++i)
            g.fillRect(25 * i + 6, 25 * (PARENT.Model().Grid.peaks()[i] - PARENT.Model().Grid.HIDDEN_HEIGHT()) + 6, 12, 12);
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