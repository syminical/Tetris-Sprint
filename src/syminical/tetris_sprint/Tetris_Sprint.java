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
    public GameController GC;
	private final Dimension MAIN_BOX_SIZE = new Dimension( 251, 501 ), SETTINGS_BOX_SIZE = new Dimension( 200, 300 ), INFO_BOX_SIZE = new Dimension( 350, 300 ), DEV_BOX_SIZE = new Dimension( 251, 75 );
    
    public Tetris_Sprint() {
        GC = new GameController(this);
        createWindows();
        GC.begin();
    }
    
    private void createWindows() {
        //Start view, 5 backgrounds, 3 buttons.
		MainBox = new WindowBox(this, "Tetris - Sprint", MAIN_BOX_SIZE, null, null) {  
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
                this.addKeyListener(new KeyHandler(this));
            }
            
            @Override
            public void mouseClicked(MouseEvent ME) {
                super.mouseClicked(ME);
                
                Parent().tell(20, ME.toString());
            }
        };
        MainBox.addComponent( GC.View() );
        
        //info box
		InfoBox = new WindowBox(this, "Tetris - Sprint Info", INFO_BOX_SIZE, null, MainBox) {  
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
        };
        
        DevBox = new WindowBox(this, "Dev Box", null, null, null) {
            public void buildBox() {
                this.setAlwaysOnTop(true);
                addComponent(new JLabel("Welcome to the Dev Box! =D   [fps: " + GC.Model().fps + "]"));
                addComponent(new JLabel(">>> shoot your shot <<<"));
                addComponent(new JLabel("[ prev key: NONE ]"));
                addComponent(new JLabel("[ curr key: NONE ]"));
                addComponent(new JLabel("[ActiveBlock:]"));
            }
            
            public void tell(int n) {
                //if (!isVisible()) return;
                switch (n) {
                    case 1:
                        ((JLabel)(this.getComponent(4))).setText("[ActiveBlock: " + Parent().GC.Model().ActiveBlock + " ]");
                    default:
                        ((JLabel)(this.getComponent(0))).setText("Welcome to the Dev Box! =D   [fps: " + GC.Model().fps + "]");
                }
                this.pack();
                this.repaint();
            }
            
            public void tell(int n, String S) {
                switch (n) {
                    case 20: ((JLabel)(this.getComponent(1))).setText(">>> " + S + " <<<"); tell(1); break;
                    case 21: ((JLabel)(this.getComponent(2))).setText("[ prev key: " + S + " ]"); break;
                    case 22: ((JLabel)(this.getComponent(3))).setText("[ curr key: " + S + " ]"); break;
                    default:
                }
                repaint();//this may need to be moved later, but it is important
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
            case 3:
                DevBox.tell(42);
                break;
            case 4:
                DevBox.tell(0);
            default:
        }
    }
    
    public void tell(String S) {}
    
    public void tell(int n, String S ) {
        //0:main,1:info,2:dev
        switch ((int)(n/10)) {
            case 2:
                DevBox.tell(n, S);
                break;
            default:
        }
    }
    
    public static void main(String[] Args) {
        if (Instance == null)
            Instance = new Tetris_Sprint();            
    }
    
    private class MouseHandler extends MouseAdapter {
        private final WindowBox PARENT;
        
        public MouseHandler(WindowBox Prnt) { PARENT = Prnt; }
        
        @Override
        public void mouseClicked(MouseEvent ME) {
            PARENT.mouseClicked(ME);   
        }
    }
    
    private class KeyHandler extends KeyAdapter {
        private final WindowBox PARENT;
        private char lastKey, currentKey;
        private boolean idle = true;
        
        public KeyHandler(WindowBox Prnt) { PARENT = Prnt; }
        
        @Override
        public void keyPressed(KeyEvent KE) {
            lastKey = KE.getKeyChar();
            currentKey = lastKey;
            PARENT.tell(21, ""+lastKey);
            PARENT.tell(22, ""+currentKey);
            PARENT.Parent().GC.keyPressed(KE);
        }
        
        @Override
        public void keyReleased(KeyEvent KE) {
            if (KE.getKeyChar() == currentKey)
                PARENT.tell(22, "NONE");
            PARENT.Parent().GC.keyReleased(KE);
        }
    }
}