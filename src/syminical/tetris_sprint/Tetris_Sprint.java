package syminical.tetris_sprint;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tetris_Sprint {
    
    public static Tetris_Sprint Instance;
    private WindowBox MainBox, SettingsBox, InfoBox, DevBox;
	private final Dimension MAIN_BOX_SIZE = new Dimension( 251, 501 ), SETTINGS_BOX_SIZE = new Dimension( 200, 300 ), INFO_BOX_SIZE = new Dimension( 350, 300 ), DEV_BOX_SIZE = new Dimension( 251, 75 );
    private String JavaLimitation = "what a let down lol";
    
    public Tetris_Sprint() {
        createWindows();
    }
    
    private void createWindows() {
        //Start view, 5 backgrounds, 3 buttons.
		MainBox = new WindowBox<Tetris_Sprint>(this, "Tetris - Sprint", MAIN_BOX_SIZE, null, null) {  
            public void buildBox() {
                //window mouse event zones  
                //main button
                Zones().add(new Zone(this, new Rectangle(49, 248, 155, 56)) {
                    @Override
                    public void clicked(MouseEvent ME) { /*Parent().tell(0);*/ }
                });
                //info button
                Zones().add(new Zone(this, new Rectangle(47, 496, 165, 11)) {
                    @Override
                    public void clicked(MouseEvent ME) { 
                        if (ME.getModifiers() == (MouseEvent.CTRL_MASK + MouseEvent.BUTTON1_MASK))
                            Parent().tell(2);
                        else
                            Parent().tell(1);
                    }
                });
                
                //window customization
                this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                this.setResizable( false );
                //this.setBackground( new Color( 0, 0, 0, 0 ) );
                
                this.addMouseListener(new MouseHandler(this));
            }
            
            @Override
            public void mouseClicked(MouseEvent ME) {
                super.mouseClicked(ME);
                
                Parent().tell(ME.toString());
            }
            
            //Object Messaging, to trigger custom 'methods'.  
            public void tell(int n) {
                Parent().tell(n);
            }
        };
        
        //game canvas
        MainBox.addComponent( new JPanel() {            
            public void paint(Graphics g) {
                super.paint(g);
                
                this.setBackground(Color.BLACK);
                
                //click me button
                this.setOpaque(true);
                g.setColor(new Color(200, 200, 200));
                g.fillRoundRect( (47), (222), (155), (57), 40, 40 );
                g.setColor(Color.BLACK);
                g.fillRoundRect( (25 * 2), (25 * 9), (25 * 6), (25 * 2), 40, 40 ); 	
                g.setColor(new Color(150, 150, 150));
                g.drawRoundRect( (52), (228), (145), (43), 40, 40);	
                g.setColor(Color.WHITE);
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
                g.drawString("Click Me", (25 * 3 - 16), (261));
                
                //information message button
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 12)); 
                g.setColor(Color.WHITE);
                g.drawString("press i for more information", 46, 480);
            }
        });
        
        //info box
		InfoBox = new WindowBox<Tetris_Sprint>(this, "Tetris - Sprint Info", INFO_BOX_SIZE, null, MainBox) {  
            public void buildBox() {
                JEditorPane JEP = new JEditorPane();
                JEP.setBackground(Color.BLACK);
                JEP.setContentType("text/html");
                JEP.setText("<p style = 'text-align: center;color: white;font-size: 15px;'><b>TETRIS - SPRINT<br>syminical 2019<br><br>* Right / Left arrow keys to move<br>* Down / Space / wait to go down<br>* Up to turn<br>* C to swap shapes<br>* R to restart<br><br>Clear 20 lines as fast as you can!<br><br><br></b></p>");
                JEP.setFont( new Font("Comic Sans MS", Font.BOLD, 20) );
                JEP.setEditable(false);
                JEP.setHighlighter(null);
                JEP.setOpaque(true);
                this.addComponent( JEP );
            }
            
            //Object Messaging, to trigger custom 'methods'.  
            public void tell(int n) {}
        };
        
        DevBox = new WindowBox<Tetris_Sprint>(this, "Dev Box", null, null, MainBox) {
            
            public void buildBox() { addComponent(new JLabel("Welcome to the Dev Box! =D")); }
            
            public void tell(int n) {
                if (!isVisible()) return;
                switch (n) {
                    default:
                        ((JLabel)(this.getComponent(0))).setText(Parent().bandaid());
                        this.pack();
                        this.repaint();
                }
            }
        };
    }
    
    public void tell(int m) {
        switch (m) {
            case 0:
                break;
            case 1:
                InfoBox.toggleVisibility();
                break;
            case 2:
                DevBox.toggleVisibility();
                break;
            default:
        }
    }
    
    public void tell(String S) {
        JavaLimitation = S;
        DevBox.tell(42);    
    }
    
    //outrageous
    public String bandaid() {
        return JavaLimitation;   
    }
    
    public static void main(String[] Args) {
        if (Instance == null)
            Instance = new Tetris_Sprint();            
    }
    
    private class MouseHandler extends MouseAdapter {
        
        private final WindowBox PARENT;
        
        public MouseHandler(WindowBox Prnt) {
           PARENT = Prnt;
        }
        
        @Override
        public void mouseClicked(MouseEvent ME) {
            PARENT.mouseClicked(ME);   
        }
    }
}