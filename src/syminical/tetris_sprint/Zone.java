package syminical.tetris_sprint;

import java.awt.*;

public class Zone {
    private final WindowBox Parent;
    private Shape S;
    
    public Zone(WindowBox P, Shape Sh) {
        Parent = P;
        S = Sh;
    }
    
    public Shape Shape() {
        return S;
    }
    
    public void entered() {}
    public void clicked() {}
    
    public WindowBox Parent() { return Parent; }
}