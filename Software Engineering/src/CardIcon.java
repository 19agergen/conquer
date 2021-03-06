import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class is the actual picture on any given card. It is separate from the card class, because
 *   there was a need to give it a special listener to display more options that can be taken on the card.
 * @author DAT Software Engineering
 *
 */
public class CardIcon extends JPanel{
	private Image icon;
	private Image history;
	private int width, height;
	
			
	public CardIcon(String imageLoc, int width, int height) throws IOException {
		//This section sets up the card
		this.setPreferredSize(new Dimension(width, height)); //Sets size of icon
		this.height = height; //height of icon
		this.width = width; //width of icon
		this.setIcon(imageLoc); //Sets the cards icon
		history = icon; //Used for the hover function
		
		//Gets rid of vertical gap
		FlowLayout layout = new FlowLayout();
		layout.setVgap(0);
		layout.setHgap(0);
		this.setLayout(layout);
	}

	public void planetSetUp(PlayerStats stats, Card card) {
			CardButtonPanel buttonPanel = new CardButtonPanel(width, height, stats, card);
		
			buttonPanel.soldiersModal.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					icon = history;
					remove(buttonPanel);
					revalidate();
					repaint();
				}
			});
			buttonPanel.getGeneral().addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent evt) {
					if(!isComponentInPanel(buttonPanel)) { //Is the mouse even in the panel?
						icon = null;
						int enemyStage = RootGameControl.getCurUser().getStats().getStage();
						//Cannot be this card's owner's turn, Must be the fight stage, and there must be soliders
						if (stats.getStage() == -1 && enemyStage == 3 && card.getTroopContribution() > 0) {
							buttonPanel.showFightPhase();
						} else if (stats.getStage() == 2){
							buttonPanel.showGeneral();
						} else {
							return;
						}
						add(buttonPanel);
						revalidate();
						repaint();
					}
				}
				
					public void mouseExited(MouseEvent evt) {
						Point p = new Point(evt.getLocationOnScreen());
						SwingUtilities.convertPointFromScreen(p, evt.getComponent());
						if (evt.getComponent().contains(p)) { //Is the point inside of the panel still? This was added because I was having trouble with the parent calling this listener every time the mouse went from a child panel to the parent.
							return;
						} else {
							buttonPanel.showGeneral();
							buttonPanel.showFightPhase();
							icon = history;
							remove(buttonPanel);
							repaint();
							revalidate();
						}
					}
			
			});
			
			buttonPanel.getFightPhasePanel().addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent evt) {
					int enemyStage = RootGameControl.getCurUser().getStats().getStage();

					if (stats.getStage() == -1 && enemyStage == 3 && card.getTroopContribution() > 0) {
						buttonPanel.showFightPhase();
					}
				}
				
				public void mouseExited(MouseEvent evt) {
					Point p = new Point(evt.getLocationOnScreen());
					SwingUtilities.convertPointFromScreen(p, evt.getComponent());
					if (evt.getComponent().contains(p)) { //Is the point inside of the panel still? This was added because I was having trouble with the parent calling this listener every time the mouse went from a child panel to the parent.
						return;
					} else {
						buttonPanel.showGeneral();
						buttonPanel.showFightPhase();
						icon = history;
						remove(buttonPanel);
						repaint();
						revalidate();
					}
				}
			});
			
			this.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent evt) {
						if(!isComponentInPanel(CardIcon.this)) { //Is the mouse even in the panel?
							icon = null;
							int enemyStage = RootGameControl.getCurUser().getStats().getStage();
							//Cannot be this card's owner's turn, Must be the fight stage, and there must be soliders
							if (stats.getStage() == -1 && enemyStage == 3 && card.getTroopContribution() > 0) {
								buttonPanel.showFightPhase();
							} else if (stats.getStage() == 2){
								buttonPanel.showGeneral();
							} else {
								return;
							}
							add(buttonPanel);
							revalidate();
							repaint();
						}
				}
				
				public void mouseExited(MouseEvent evt) {
					Point p = new Point(evt.getLocationOnScreen());
					SwingUtilities.convertPointFromScreen(p, evt.getComponent());
					if (evt.getComponent().contains(p)) { //Is the point inside of the panel still? This was added because I was having trouble with the parent calling this listener every time the mouse went from a child panel to the parent.
						return;
					} else {
						icon = history;
						remove(buttonPanel);
						repaint();
						revalidate();
					}
				}
				
		});
			
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		//The background
		g2.setColor(Color.ORANGE);
		g2.fillRect(0, 0, width, height);
		
		g2.drawImage(icon, 0, 0, this); //Card Image
		
	}
	
	
	/**
	 * Gives the card a new Icon
	 * @param image The new image that is going to be the cards icon
	 */
	public void setIcon(String imageLoc) throws IOException {
		Image newImg = ImageIO.read(U.getFile(imageLoc));
		Image scaledImg = newImg.getScaledInstance(this.width, this.height, Image.SCALE_AREA_AVERAGING);
		icon = scaledImg;
	}
	
	private boolean isComponentInPanel(Component component) {
		return java.util.Arrays.asList(this.getComponents()).contains(component);
	}
	
}
