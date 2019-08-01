package syminical.pictag;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.geom.Ellipse2D;

public class PicTag {

	private static PicTag INSTANCE;
	private WindowBox MainBox, SettingsBox, InteractiveBox, DevBox;
	private final Dimension MAIN_BOX_SIZE = new Dimension( 300, 300 ), SETTINGS_BOX_SIZE = new Dimension( 600, 600 ), INTERACTIVE_BOX_SIZE = new Dimension( 1000, 1000 );
    private Image[] MainAssets, SettingsAssets, InteractiveAssets, DevAssets;
    private JFileChooser Chooser;
    
	public PicTag() {
		if (!loadAssets()) {
            if (JOptionPane.showOptionDialog(null, "Failed to load assets.", "Warning!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"OK"}, "OK") == JOptionPane.OK_OPTION)
                System.exit(-1);
        }
		else {
            Chooser = new JFileChooser();
                Chooser.setFileFilter(new FileNameExtensionFilter("jpg, jpeg, gif, png", "jpg", "jpeg", "gif", "png"));
            
            createWindows();
        }
	}

	private boolean loadAssets() {
        
        MainAssets = new Image[5];
        SettingsAssets = new Image[1];
        InteractiveAssets = new Image[1];
        DevAssets = new Image[1];
        
        try {
			MainAssets[0] = ImageIO.read( this.getClass().getResource("/img/main/0.png") );
			MainAssets[1] = ImageIO.read( this.getClass().getResource("/img/main/1.png") );
			MainAssets[2] = ImageIO.read( this.getClass().getResource("/img/main/2.png") );
			MainAssets[3] = ImageIO.read( this.getClass().getResource("/img/main/3.png") );
			MainAssets[4] = ImageIO.read( this.getClass().getResource("/img/main/4.png") );
			SettingsAssets[0] = ImageIO.read( this.getClass().getResource("/img/settings/0.png") );
		} catch (Exception e) { return false; }
        
        return true;
	}

	private void createWindows() {
        //Start view, 5 backgrounds, 3 buttons.
		MainBox = new WindowBox(this, "PicTag", MAIN_BOX_SIZE, MainAssets, null) {  
            public void buildBox() {
                //window mouse event zones  
                //settings zone
                Zones().add(new Zone(this, new Ellipse2D.Double(135, 190, 21, 19)) {
                    @Override
                    public void entered() { Parent().changeBackground(3); }
                    
                    @Override
                    public void clicked() { Parent().tell(0); }
                });
                //files zone
                Zones().add(new Zone(this, new Rectangle(113, 104, 68, 87)) {
                    @Override
                    public void entered() { Parent().changeBackground(2); }
                    
                    @Override
                    public void clicked() { Parent().tell(1); }
                });
                //close
                Zones().add(new Zone(this, new Ellipse2D.Double(182, 46, 45, 45)) {
                    @Override
                    public void entered() { Parent().changeBackground(4); }
                    
                    @Override
                    public void clicked() { Parent().tell(2); }
                });
                
                //window customization
                this.addMouseListener( Drag() );
                this.addMouseMotionListener( Drag() );
                this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                this.setResizable( false );
                this.setUndecorated( true );
                this.setBackground( new Color( 0, 0, 0, 0 ) );
            }
            
            //Object Messaging, to trigger custom 'methods'.  
            public void tell(int n) {
                if ( Parent() == null ) return;
                switch (n) {
                    case 0:
                        Parent().startSettings();
                        break;
                    case 1:
                        Parent().pickFiles();
                        break;
                    case 2:
                        Parent().terminateProgram();
                    default:
                }
            }
            
            //Set to active background if no zone hit.
            @Override
            public void defaultMouseMovedAction() {
                changeBackground(1);
            }
        };
        
		SettingsBox = new WindowBox(this, "PicTag Settings", SETTINGS_BOX_SIZE, SettingsAssets, MainBox) {
            public void buildBox() {
                //window customization
                this.addMouseListener( Drag() );
                this.addMouseMotionListener( Drag() );
                this.setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );
                this.setResizable( false );
                this.setUndecorated( true );
                this.setBackground( new Color( 0, 0, 0, 0 ) );
            }
            
            public void tell(int n) { }
        };
        /*
		InteractiveBox = new WindowBox(this, "PicTag Viewer", INTERACTIVE_BOX_SIZE, InteractiveAssets, MainBox) {
            @Override
            private void buildBox() {
                
            }
            
            @Override
            private void clicked(int zne) {
                
            }
        };
        
		DevBox = new WindowBox(this, "PicTag Dev View", SETTINGS_BOX_SIZE, DevAssets, MainBox) {
            @Override
            private void buildBox() {
                
            }
            
            @Override
            private void clicked(int zne) {
                
            }
        };*/
	}
    
    //--= Helper Methods =--
    
    private void terminateProgram() {
		//main frame
		MainBox.setVisible(false);
        SettingsBox.setVisible(false);
		MainBox.dispose();
        SettingsBox.dispose();
        System.exit(0);
	}
        
    public void pickFiles() {
        if (Chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
            JOptionPane.showMessageDialog(new JFrame(), "File(s) successfully chosen~! :]");
    }
    
    public void startSettings() {
        //JOptionPane.showMessageDialog(new JFrame(), "PRETEND THIS IS A SETTINGS BOX! :D");
        SettingsBox.setLocationRelativeTo(SettingsBox.Anchor());
        //MainBox.setVisible(false);
        SettingsBox.setVisible(true);
    }
    
	public static void main(String[] Args) {
        if (INSTANCE == null)
            INSTANCE = new PicTag();
	}
}

//theworldisquiethere