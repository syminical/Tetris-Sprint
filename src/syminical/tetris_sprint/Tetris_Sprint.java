package syminical.tetris_sprint;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tetris_Sprint {
    
    public static Tetris_Sprint Instance;
    private WindowBox MainBox, SettingsBox, InteractiveBox, DevBox;
	private final Dimension MAIN_BOX_SIZE = new Dimension( 251, 501 ), SETTINGS_BOX_SIZE = new Dimension( 350, 300 ), INFO_BOX_SIZE = new Dimension( 300, 100 );
    
    public Tetris_Sprint() {
        createWindows();
    }
    
    private void createWindows() {
        //Start view, 5 backgrounds, 3 buttons.
		MainBox = new WindowBox<Tetris_Sprint>(this, "Tetris - Sprint", MAIN_BOX_SIZE, null, null) {  
            public void buildBox() {
                //window mouse event zones  
                //main button
                Zones().add(new Zone(this, new Rectangle(48, 225, 154, 57)) {
                    @Override
                    public void clicked() { /*Parent().tell(1);*/ }
                });
                //info button
                Zones().add(new Zone(this, new Rectangle(46, 470, 106, 9)) {
                    @Override
                    public void clicked() { /*Parent().tell(1);*/ }
                });
                
                //window customization
                this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                this.setResizable( false );
                //this.setBackground( new Color( 0, 0, 0, 0 ) );
            }
            
            //Object Messaging, to trigger custom 'methods'.  
            public void tell(int n) {
                if ( Parent() == null ) return;
                /*switch (n) {
                    case 0:
                        Parent().startSettings();
                        break;
                    case 1:
                        Parent().pickFiles();
                        break;
                    case 2:
                        Parent().terminateProgram();
                    default:
                }*/
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
		SettingsBox = new WindowBox<Tetris_Sprint>(this, "Tetris - Sprint Info", SETTINGS_BOX_SIZE, null, null) {  
            public void buildBox() {
                //window customization
                //this.setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );
                //this.setResizable( false );
                //this.setBackground( new Color( 0, 0, 0, 0 ) );
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
    }
    
    public static void main(String[] Args) {
        if (Instance == null)
            Instance = new Tetris_Sprint();            
    }
}