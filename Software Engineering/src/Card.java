import java.awt.*;
import java.io.*;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

/**
 * This is the abstraction of planet and solar system cards. It holds all of the data for any given planet, and allows
 * the system to manipulate the data.
 * @author DAT Software Engineering
 *
 */

public class Card extends JPanel {
	//General Stats about card
	private String title = ""; //The card title
	private String type = ""; //The type of card
	private String description = ""; //The description
	private int population = 0; //Population of card
	private int resources = 0; //A planet or solar system can have natural resources
	private String imageLoc;
	private String sub_type = "";
	private double subTypeRate = 0.0;
	private double popChangeRate = 0.0; //This is the rate at which the population for a planet will change every turn
	private int troopsInArmy = 0; //The current amount of troops this planet has contributed to the army.
	private double defensiveBonus = 0;
	
	//Initializes variables that are going to be used in the display of the card
	private CardIcon icon;
	private JTextArea backText;
	private JPanel topInfo = new JPanel(); //This is the top of the card
	private JPanel bottomInfo = new JPanel();//This is the bottom of the card
	
	//This sets up the general data for the card.
	public Card(String title, String description, String imageLoc, String type) throws IOException {
		//Sets up card
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.title = title;
		this.description = description;
		this.imageLoc = imageLoc;
		this.type = type;
		
		this.defineTop();
		
		this.defineBack();
	}
		
	
	private void defineTop() throws IOException {
		//This creates the icon
		icon = new CardIcon(imageLoc, 200, 250);

		//This section defines the top of the card
		JLabel titleLabel = new JLabel(title); //The cards title
		titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
		topInfo.add(titleLabel);
		JLabel infoLabel = new JLabel("+"); //This is the button that will show the back of the card
		infoLabel.setHorizontalAlignment(JLabel.RIGHT);
		infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
		infoLabel.addMouseListener( new MouseAdapter() { 
			public void mousePressed(MouseEvent evt) {
				printBack();
			} 
		});
		topInfo.add(infoLabel);		
		topInfo.setPreferredSize(new Dimension(200, 320 - 250));
		topInfo.setBackground(new Color(0,0,0,0)); //Allows the card's color to show!

	}
	
	/**
	 * This will update the back of the card
	 * @throws IOException 
	 */
	public void defineBack() throws IOException{
		//If the card has already defined this
		if (bottomInfo != null) {
			remove(bottomInfo);
			revalidate();
			repaint();
		}
		
		bottomInfo = new JPanel();
		
		//This section defines the contents for the back of the card
		backText = new JTextArea("Description: \n" + description, 1, 1);
		JScrollPane scrollPane = new JScrollPane(backText);
		backText.setEditable(false);
		backText.setFont(new Font("Arial", Font.BOLD, 18));
		backText.setLineWrap(true);
		backText.setWrapStyleWord(true);
		backText.setBackground(Color.ORANGE);
		backText.append("\n\nCurrent Population:\n" + population + "M"); //Population
		backText.append("\n\nOriginal Resources:\n" + this.resources);
		backText.setMargin(new Insets(5,5,0,0));
		bottomInfo.add(backText);
		
		JLabel infoLabel = new JLabel("-"); //This button will show the front of the card
		infoLabel.setHorizontalAlignment(JLabel.RIGHT);
		infoLabel.setFont(new Font("Arial", Font.BOLD, 36));
		infoLabel.addMouseListener( new MouseAdapter() { 
			public void mousePressed(MouseEvent evt) {
				printFront();
			} 
		});
		bottomInfo.add(infoLabel);
		bottomInfo.setPreferredSize(new Dimension(200, 320 - 250));
		bottomInfo.setLayout(new BoxLayout(bottomInfo, BoxLayout.Y_AXIS));
		bottomInfo.setBackground(Color.ORANGE); //Allows the card's color to show!
		
		printFront();
	}
	
	/**
	 * If this is a Planet card, the TurnControl class is going to call this to setup the card's button panel.
	 * @param stats The Player's Statistics
	 */
	public void setButtonPanel(PlayerStats stats) {
		this.icon.planetSetUp(stats, this);
	}
	
	/**
	 * This method allows for the game to reuse the card.
	 */
	public Card clone() { 
	   try {
		   Card card = new Card(title, description, imageLoc, type); //Initial card
		   card.setPop(this.population);
		   card.setResources(this.resources);
		   card.setPopChangeRate(this.popChangeRate);
		   if (!(this.sub_type.equals("") && this.subTypeRate != 0)) {
			   card.setSubType(sub_type, subTypeRate);
		   }
		   card.defineBack(); //The back of the card needs to update its description.
		   card.setPreferredSize(new Dimension(200,320));
		   return card;
	   } catch (IOException e) {
		   //Something happend when we were trying to clone the card
		   e.printStackTrace();
	   }
	   return null; //There will always be something returned. 
	} 

	/**
	 * This overrides the paintComponent method of the panel
	 */
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D bg = new Rectangle2D.Double(0, 0, getWidth(), getHeight()); //This is the background of the card
		g2.setColor(Color.ORANGE);
		g2.fill(bg);		

	}
	
	/**
	 * Draws the front side of the card
	 */
	private void printFront() {
		if (bottomInfo != null) {
			remove(bottomInfo);
		}
		
		add(icon);
		add(topInfo);
		
		repaint(); //Repaints the icon
		revalidate();
	}

	/**
	 * This will print the back of the card
	 */
	public void printBack() {
		remove(topInfo);
		remove(icon);
		add(bottomInfo);
			
		revalidate(); //Tells java you have changed the component structure
		repaint();
	}
	
	//Defacto getter and setter methods
	/**
	 * Gives the card a new Icon
	 * @param image The new image that is going to be the cards icon
	 */
	public void setIcon(String imageLoc) throws IOException {
		icon = new CardIcon(imageLoc, 200, 250);
	}
	
	
	/**
	 * This will return the card's icon Image object
	 * @return The cards icon
	 */
	public CardIcon getIcon() {
		return this.icon;
	}
	
	/**
	 * Returns the type of Card
	 * @return The type of card
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type of card
	 * @param type The new type
	 */
	public void setType(String type) {
		type = this.type;
	}
	
	/**
	 * This method returns this cards sub_type
	 * @return the cards sub_type
	 */
	public String getSubType() {
		return sub_type;
	}
	
	/**
	 * This class will set a cards sub_type. If a card has a sub_type it will be that much more likely 
	 * of being chosen for a given action of the same sub_type.
	 * @param sub_type The cards sub_type.
	 * @param subTypeRate This is the additional chance of a given action occuring to this.
	 */
	public void setSubType(String sub_type, double subTypeRate) {
		this.sub_type = sub_type;
		this.subTypeRate = subTypeRate;
	}
	
	/**
	 * This will get the rate that the card has for a specific sub type. The rate is the extra percent chance that
	 * any given action will occur on this card.
	 *
	 * @return the rate
	 */
	public double getSubTypeRate() {
		return this.subTypeRate;
	}
	
	/**
	 * Sets the description of card
	 * @param description The description of the card
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * This returns the description of the card
	 * @return description of card
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Sets the title of the card
	 * @param title The new title of the card
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * This returns the title of the card
	 * @return title of card
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * This sets the card's population to something new
	 * @param newPop the new population of the card
	 */
	public void setPop(int newPop) {
		this.population = newPop;
	}
	
	/**
	 * This sets the cards population to something new
	 * @param newPop The new population of the card
	 * @param changeRangeRate This is a percentage that will be multiplied by the given population to allow for some randomness in populations per game.
	 */
	public void setPop(double newPop, double changeRangeRate) {
		this.population = (int) newPop;
		//This section is used to calculate the population change.
		Random rand = new Random();
		int popChangeMax = (int) (this.population * changeRangeRate);
		int popChange; //The amount the population of a given card will change for this game.
		if (popChangeMax == 0) {
			popChange = 0;
		} else {
			popChange = rand.nextInt((int) (popChangeMax)); //This is the number that will be either added or subtracted from the default population of the planet/solar system.
		}
		int operator = rand.nextInt(2); //zero is plus, one is negative
		if (operator == 0) {
			this.population += popChange;
		} else {
			this.population -= popChange;
		}
	}
	
	/**
	 * Returns the population of the card
	 * @return The cards current population
	 */
	public int getPop() {
		return this.population;
	}
	
	/**
	 * This method will add more population to the card and return the sum.
	 * @param additive The pop. to add
	 */
	public void addPop(int additive) {
		this.population += additive;
	}
	
	/**
	 * This method will subtract more population to the card and return the difference.
	 * @param amt The pop. to subtract
	 */
	public void removePop(int amt) {
		this.population -= amt;
	}
	
	/**
	 * This will set the resource count for the card
	 * @param resourceAmt The amount to set the resource count to.
	 */
	public void setResources(int resourceAmt) {
		this.resources = resourceAmt;
	}
	
	/**
	 * This will return the current amount of resources in the card.
	 * @return the amount of resources
	 */
	public int getResources() {
		return this.resources;
	}
	
	/**
	 * This will make a modal that will describe this card.
	 * @return the new modal.
	 */
	public CardInfoModal getModal() {
		return new CardInfoModal(this.title, this.description, this.population, this.resources, imageLoc);
	}
	
	/**
	 * This will will return the current amount of troops this planet has contributed to the player's army.
	 * @return current troop amount
	 */
	public int getTroopContribution() {
		return this.troopsInArmy;
	}
	
	/**
	 * This will add to the amount of troops the planet has contributed to the army.
	 * @param the amount.
	 */
	public void addTroops(int additive) {
		this.troopsInArmy += additive;
	}
	
	/**
	 * This will subtract from the amount of troop the planet has contributed to the army.
	 * @param the amount
	 */
	public void subTroops(int sub) {
		this.troopsInArmy -= sub;
	}
	
	/**
	 * This will return this card's defensive bonus
	 * @return the card's defensive bonus
	 */
	public double getDefensiveBonus() {
		return this.defensiveBonus;
	}
	
	/**
	 * This will return the planet's population growth rate
	 * @return the growth rate
	 */
	public double getPopChangeRate() {
		return this.popChangeRate;
	}
	
	/**
	 * This will set the planet's population growth rate
	 * @param rate the new rate
	 */
	public void setPopChangeRate(double rate) {
		this.popChangeRate = rate;
	}
}
