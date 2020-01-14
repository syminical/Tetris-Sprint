package syminical.tetris_sprint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public  class WindowAtlas extends JPanel {

	private WindowBox Parent; //This could maybe be better.
    private Dimension Size;
    private Image Asset;
    
    //--= Constructor(s) =--
    //...I do not want to overload constructors...too much clutter.   (._ .)
    
	public WindowAtlas( WindowBox P, Dimension S, Image Im ) {
        
        super();
        
		Parent = P;
        Asset = Im;
        Size = S;
        
        //Atlas settings
        this.setPreferredSize( Size );
        this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
        this.setBackground( new Color( 0, 0, 0, 0 ) );
	}    
    
    //--= public API =--
    
    public WindowBox Parent() { return Parent; }
}