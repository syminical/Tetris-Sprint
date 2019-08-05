package syminical.tetris_sprint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

public abstract class WindowBox<T> extends JFrame {

	private T Parent; //Maybe this could be better.
    private Dimension Size;
	private WindowAtlas WindowMap;
	private ArrayList<Zone> Zones;
	private KeyAction KA1, KA2, KA3;
    private Image[] Assets;
    private int closeOp, lastBackgroundImage;
    private Component Anchor;
    
    //--= Constructor(s) =--
    //...I do not want to overload constructors...too much clutter.   (._ .)
    
	public WindowBox(T P, String N, Dimension S, Image[] As, Component An) {
        
        super(N);
        
		Parent = P;
        Size = S;
        Assets = As;
        Anchor = An;
        Zones = new ArrayList<Zone>();
        
        burdenAtlas();
        buildBox();

        this.setContentPane(WindowMap);
        this.pack();
        this.setLocationRelativeTo(Anchor);
        if (Anchor == null)
            this.setVisible(true);
	}
    
    
    
    //--= Customization Methods =--
    
    //Used to Customize content pane, but it MUST initialize WindowMap.
    private void burdenAtlas() {
        WindowMap = new WindowAtlas(this, Size, null);   
    };
    
    //Customize JFrame.
    public abstract void buildBox();
    
    public abstract void tell(int n);
    
    //v-= Override these to customize mouse event responses. =-v
    
    public void mouseClicked(MouseEvent ME) {
        for (Zone Temp : Zones)
            if ( Temp.Shape().contains( ME.getPoint() )) {
                Temp.clicked(ME);
                return;
            }
    }
    
    public void defaultMouseMovedAction() { };
    
    public void mouseMoved(MouseEvent ME) {
        for (Zone Temp : Zones)
            if ( Temp.Shape().contains( ME.getPoint() )) {
                Temp.entered(ME);
                return;
            }
        defaultMouseMovedAction();
    }
    
    /*public void mouseEntered() { changeBackground(1); }
    
    public void mouseExited() { changeBackground(0); }*/
    
    
    
    //--= public API =--
    
    public void toggleVisibility() { this.setVisible( !this.isVisible() ); }
    
    //Add a component to the window map.
    public void addComponent(Component C) {
        WindowMap.add(C);
    }
    
    public ArrayList Zones() { return Zones; }
    public T Parent() { return Parent; }
    public int lastBackgroundImage() { return lastBackgroundImage; }
    public Component Anchor() { return Anchor; }
}