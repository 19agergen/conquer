
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.*;
/**
 * This is the start of our game. It is the first thing the user will see.
 *
 * @author DAT Software Engineering
 */
public class StartForGame extends JPanel implements ActionListener
{


	/**
     * A main routine allows this class to be run as an application.
     */
    public static void main(String[] args) {   
    	
        JFrame window = new JFrame("Conquer!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(U.width, U.height);
		window.setResizable(false);
        
		StartForGame start = new StartForGame();
		window.setContentPane(start);
		window.pack();
        window.setVisible(true);
        
        //Change the standard output so that all errors will be printed to a file.
        try {
			System.setErr(new PrintStream(new File("error.log")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    public StartForGame()  {
    	//This moves the JPanel up fix pixels to remove any white space
		FlowLayout flow = new FlowLayout();
		flow.setVgap(-5);
		flow.setHgap(0);
		setLayout(flow);
		       
   		//This is the background image
      	JLabel content = new JLabel(new ImageIcon(U.getFile("start_background.png")));
    	//JLabel content = new JLabel(StartForGame.class.getResourceAsStream("images/start_background.png"));
      	content.setLayout(new FlowLayout(FlowLayout.CENTER));
      	
      	//This holds all of the buttons on the start up screen.
      	JPanel buttonBar = new JPanel();
      	buttonBar.setBackground(Color.BLACK);
       	
       //This will start a new game.
       Button button = new Button("New Game");
       button.addMouseListener(new MouseAdapter() {
    	   public void mousePressed(MouseEvent evt) {
    			//This sets up the cards that will be used for the rest of the game.
    			try {
    				CardDB cardDB = new CardDB();
    			} catch (FileNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} 
    			
    			//These are the colors that each player will have
    			Color[] colors = {new Color(0, 0, 204), new Color(0, 153, 51)};
    			
    			//This will start the game.
    			RootGameControl root = new RootGameControl(2, colors);
    			root.startGame();
    			
    			//Display the game.
    			remove(content);
    			add(root);
    			repaint();
    			revalidate();
    	   }
       });
       buttonBar.add(button);
       
       //This will load a setting modal
       button = new Button("Settings");
       button.addActionListener(this);
       buttonBar.add(button);
       
       //This will show the instructions for the game
       button = new Button("Instructions");
       button.addMouseListener(new MouseAdapter() { //This will add the Instructions to the sreen
    	   public void mousePressed(MouseEvent evt) {
    		   InstructionsModal modal = new InstructionsModal();
    		   modal.setPreferredSize(new Dimension(U.width, U.height));
    		   modal.addMouseListener(new MouseAdapter() { //If someone clicks on the gray area the modal will close
    			   public void mousePressed(MouseEvent evt) {
    				   StartForGame.this.remove(modal);
    				   StartForGame.this.add(content);
    				   StartForGame.this.revalidate();
    				   StartForGame.this.repaint();
    			   }
    		   });
    		   
    		   remove(content);
    		   add(modal);
    		   repaint();
    		   revalidate();
    	   }
       });
       buttonBar.add(button);
       
       //This will credit the producers
       button = new Button("Credits");
       button.addActionListener(this);
       buttonBar.add(button);
       
       //This will exit the game.
       button = new Button("Quit");
       button.addActionListener(this);
       buttonBar.add(button);

    	content.add(buttonBar);
      	add(content);
    }
    
    /**
    * Respond to a button click by showing a dialog and setting the 
    * message label to describe the user's response.
    */
   public void actionPerformed(ActionEvent evt) {

      String command = evt.getActionCommand();
      if (command.equals("Settings")) {
         JOptionPane.showMessageDialog(this,
             "This is the button that will show the settings.");
      }
      else if (command.equals("Instructions")) {
         JOptionPane.showMessageDialog(this,
             "This is the button that will show the instructions.");
      }
      else if (command.equals("Credits")) {
         JOptionPane.showMessageDialog(this,
             "The Master Plan\nCreated by:\nDAT Software Engineering\nDakota Edens\nAlex Gergen\nTyler Persons");
      }
      else if (command.equals("Quit")) {
          int response = JOptionPane.showConfirmDialog(this,
             "Do you really want to quit?.\n" );
         switch(response) {
            case JOptionPane.YES_OPTION: 
              System.exit(0);
               break;
            case JOptionPane.NO_OPTION: 
               
               break;
            case JOptionPane.CANCEL_OPTION: 
               
               break;
            case JOptionPane.CLOSED_OPTION: 
               break;
               
            default: 
                //default action or exception
                break;
         }
        
        
      }
    }
}

