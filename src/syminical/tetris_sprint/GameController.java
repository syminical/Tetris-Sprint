package syminical.tetris_sprint;

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
        
		View().repaint();
        
        start();
	}
    
    private void payload() {
        Model().drawShapes();
        if (Model().frameReady) View().repaint();   
    }
    
    private void start() {
        final double SEG_TIME = 1000 / 60; //60 frames per second.
        double start, segStart, segTotal, sleepTime;
        int segCount = 0;
        
        start = System.currentTimeMillis();
        lastForceDrop = start;
        
        while (true) { //tie to model running boolean
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
    
    public GameModel Model() { return GModel; }
    
    public GameView View() { return GView; }
    
    public Tetris_Sprint Parent() { return PARENT; }
}