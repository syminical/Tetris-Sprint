package syminical.tetris_sprint;

public class GameManager {
    private static final WindowBox PARENT;
    private GameModel GModel;
    private GameView GView;
    
    public GameManager(WindowBox P, GameModel GMA, GameView GVA) {
        PARENT = P;
        GModel = new GameModel(this);
        GView = new GameView(this);
    }
    
    public GameModel Model() { return GModel; }
    
    public GameView View() { return GView; }
    
    public WindowBox Parent() { return PARENT; }
}