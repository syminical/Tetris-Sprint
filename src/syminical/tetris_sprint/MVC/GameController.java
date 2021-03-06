package syminical.tetris_sprint;

import java.awt.event.KeyEvent;

public class GameController {
    private final Tetris_Sprint PARENT;
    private GameModel GModel;
    private GameView GView;
    private double lastForceDrop = System.currentTimeMillis(), lastInput = lastForceDrop;
    
    public GameController(Tetris_Sprint P) {
        PARENT = P;
        GModel = new GameModel(this);
        GView = new GameView(this);
    }
    
    public void begin() {
		Model().active = true;
        Model().Grid.clear();
        Model().newBlock();
        Model().State = GameState.RUNNING;
        
		//View().repaint();
        
        gameLoop();
	}
    private void payload() {
        //Model().emptyInputBuffer();
        Model().moveActiveBlock();
        //Model().drawShapes();
        
        //if (Model().frameReady) 
            View().repaint();
        if (System.currentTimeMillis() - lastInput > 100)
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
            case KeyEvent.VK_RIGHT: Model().rightPressed(); break;
            case KeyEvent.VK_LEFT: Model().leftPressed(); break;
            case KeyEvent.VK_UP: Model().upPressed(); break;
            case KeyEvent.VK_DOWN: Model().downPressed(); break;
            default:
         }
    }
    public void keyReleased(KeyEvent KE) {
        switch (KE.getKeyCode()) {
            case KeyEvent.VK_RIGHT: Model().rightReleased(); break;
            case KeyEvent.VK_LEFT: Model().leftReleased(); break;
            case KeyEvent.VK_UP: Model().upReleased(); break;
            case KeyEvent.VK_DOWN: Model().downReleased(); break;
            case KeyEvent.VK_R: Model().rReleased(); break;
            case KeyEvent.VK_C: Model().cReleased(); break;
            case KeyEvent.VK_SPACE: Model().spaceReleased(); break;
            default:
        }
    }
    
    public Tetris_Sprint PARENT() { return PARENT; }
    public GameModel Model() { return GModel; }
    public GameView View() { return GView; }
    public Tetris_Sprint Parent() { return PARENT; }
}