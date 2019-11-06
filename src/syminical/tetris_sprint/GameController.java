package syminical.tetris_sprint;

import java.awt.event.KeyEvent;

public class GameController {
    private final Tetris_Sprint PARENT;
    private GameModel GModel;
    private GameView GView;
    private double lastForceDrop = System.currentTimeMillis();
    
    public GameController(Tetris_Sprint P) {
        PARENT = P;
        GModel = new GameModel(this);
        GView = new GameView(this);
    }
    
    public void begin() {
		Model().active = true;
        Model().clearGrid();
        Model().newShape();
        Model().mode = 1;
        
		//View().repaint();
        
        gameLoop();
	}
    
    private void payload() {
        Model().emptyInputBuffer();
        //Model().drawShapes();
        
        //if (Model().frameReady) 
            View().repaint();
        Model().getInput();
    }
    
    private void gameLoop() {
        final double SEG_TIME = 100; //apparently the max at which users still feel instant response time
        double start, segStart, segTotal, sleepTime;
        int segCount = 0;
        
        start = System.currentTimeMillis();
        lastForceDrop = start;
        
        while (Model().active) {
            segStart = System.currentTimeMillis();
            payload();
            segTotal = System.currentTimeMillis() - segStart;
            segCount++;
            
            sleepTime = ((segTotal < SEG_TIME)? 
                            (SEG_TIME - segTotal) : 
                            ((segTotal > SEG_TIME)?
                                (SEG_TIME - (segTotal % SEG_TIME)) : 
                                0));
            
            try {
                Thread.sleep((int)sleepTime);
            } catch (Exception E) { System.out.println("[sleep fail]"); }
            
            if (System.currentTimeMillis() - start >= 1000) {
                
                if (System.currentTimeMillis() - lastForceDrop >= 1000) Model().forceDrop();
                Model().fps = segCount;
                PARENT.tell(3);
                segCount = 0;
                start = System.currentTimeMillis();   
            }
        }
    }
    
    public void keyPressed(KeyEvent KE) {
        switch (KE.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                Model().right = true;
                Model().directionTracker = 1;
                Model().rDown = System.currentTimeMillis();
                break;
            case KeyEvent.VK_LEFT:
                Model().left = true;
                Model().directionTracker = -1;
                Model().lDown = System.currentTimeMillis();
                break;
            case KeyEvent.VK_UP: break;
            case KeyEvent.VK_DOWN: break;
            case KeyEvent.VK_R: break;
            case KeyEvent.VK_C: break;
            default:
        }
    }
    
    public void keyReleased(KeyEvent KE) {
        switch (KE.getKeyCode()) {
            case KeyEvent.VK_RIGHT: Model().right = false; Model().directionTracker = ((Model().left)? -1 : 0); break;
            case KeyEvent.VK_LEFT: Model().left = false; Model().directionTracker = ((Model().right)? 1 : 0); break;
            default:
        }
    }
    
    public GameModel Model() { return GModel; }
    
    public GameView View() { return GView; }
    
    public Tetris_Sprint Parent() { return PARENT; }
}