package syminical.tetris_sprint;

import java.awt.*;
import java.awt.event.*;

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
    
    public void entered(MouseEvent ME) {}
    public void clicked(MouseEvent ME) {}
    
    public WindowBox Parent() { return Parent; }
}