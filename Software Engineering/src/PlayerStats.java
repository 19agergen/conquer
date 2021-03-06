import java.awt.Color;
import java.util.ArrayList;

/**
 * This class holds all of the data for a user.
 * @author DAT Software Engineering
 *
 */

public class PlayerStats {
	private int totalPop = 0;
	private int armySize = 0;
	private int maxArmySize = 500;
	private int resourceCount = 0;
	private int turnStage = -1;
	private Color playerColor;
	private User user; //The current user.
	public ArrayList<CardSet> sets = new ArrayList<CardSet>(); //The solar systems.
	private TurnControl turnControl; //The class that controls a turn
	private CardSet prevSolar; //These are the last cards' to be added to the deck.
	private Card prevPlanet; 
	
	/**
	 * This is the default constructor
	 * @param playerColor This the players color
	 */
	public PlayerStats(Color playerColor) {
		this.playerColor = playerColor;

	}
	
	/**
	 * This constructor give a player default population, army size, and resource counts
	 * @param pop Player's population size
	 * @param armySize Size of the player's army
	 * @param resources The amount of resources a player has.
	 */
	public PlayerStats(int pop, int armySize, int resources) {
		this.totalPop = pop;
		this.armySize = armySize;
		this.resourceCount = resources;
	}
	
	/**
	 * This method will return the amount of resources a player has.
	 * @return The amount of resource
	 */
	public int getResource() {
		return resourceCount;
	}
	
	/**
	 * This method will add resources to a player's total count.
	 * @param additive the amount of resources to add
	 * @return The amount of resource
	 */
	public int addResources(int additive) {
		resourceCount += additive;
		return resourceCount;
	}
	
	/**
	 * This method will subtract resources from a player's total count.
	 * @param sub the amount of resources to subtract
	 * @return The amount of resource
	 */
	public int removeResources(int sub) {
		resourceCount -= sub;
		return resourceCount;
	}
	
	
	/**
	 * This will return the total population of the player's civilization
	 * @return total population
	 */
	public int getPopulation() {
		return totalPop;
	}
	
	/**
	 * This will set the player's population
	 * @param newPop the new population
	 */
	public void setPopulation(int newPop) {
		totalPop = newPop;
	}
	
	/**
	 * This will return the size of the player's army
	 * @return army size
	 */
	public int getArmySize() {
		return armySize;
	}
	
	/**
	 * This will set the army size for the player
	 * @param newSize the new size
	 */
	public void setArmySize(int newSize) {
		armySize = newSize;
	}
	
	/**
	 * This will subtract troops from the army.
	 * @param sub Troops to take away.
	 */
	public void subTroops(int sub) {
		this.armySize -= sub;

		//We need to subtract troops from the planets of the universe.
		while (sub > 0) {
			Card tmp = this.getUser().getRandomCard();
			double troops = tmp.getTroopContribution();
			int deadTroops = (int) (troops * 0.1);
			if (deadTroops > sub) { //If the number of dead troops is bigger than the number we need to get
				deadTroops = sub; //Make the dead troops equal to sub
				sub = 0; //Make sub equal to zero.
			}
			tmp.subTroops(deadTroops);
			sub -= deadTroops;
		}
	}
	/**
	 * Get the player's color
	 */
	public Color getColor() {
		return playerColor;
	}
	
	/**
	 * This method returns the current stage of the turn
	 * @return 1 => draw, 2 => build, 3 => fight, -1 => Not their TURN
	 */
	public int getStage() {
		return turnStage;
	}
	
	/**
	 * This will return the actual names of the stages not just their number
	 * @param niceNames Should it return the names
	 * @return A name of a stage.
	 */
	public String getStage(boolean niceNames) {
		switch (turnStage) {
			case 1:
				return "Draw";
			case 2:
				return "Build";
			case 3:
				return "Fight";
			default:
				return "Not your Turn";
		}
	}
	
	/**
	 * Sets the turn to the next phase. -1 => 1, 1 => 2, 2 => 3, 3 => -1
	 * Phase 1: Draw
	 * Phase 2: Build
	 * Phase 3: Fight
	 * Phase -1: Not your TURN
	 */
	public void nextStage() {
		turnStage++;
		if (turnStage > 3) {
			turnStage = -1;
		}else if (turnStage == 0) {
			turnStage = 1;
		}
		
	}
	
	/**
	 * This will add another CardSet (i.e. solar system) to the arraylist
	 * @param set the new set to be added
	 */
	public void addCardSet(CardSet set) {
		this.sets.add(set);
	}
	
	/**
	 * This will set the user class for this player
	 * @param user The user class
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * This will returns this player's user class.
	 * @return
	 */
	public User getUser() {
		return this.user;
	}
	
	/**
	 * This will return the user's max army size.
	 */
	public int getMaxArmySize() {
		return this.maxArmySize;
	}
	
	/**
	 * This will get the user's turn control
	 * @return the turn control object
	 */
	public TurnControl getTurnControl() {
		return this.turnControl;
	}
	
	/**
	 * This will set the user's turn control object
	 * @param control
	 */
	public void setTurnControl(TurnControl control) {
		this.turnControl = control;
	}
	
	/**
	 * This will return the last planet to be added to the player's deck.
	 * @return A planet card.
	 */
	public Card getPrevPlanet() {
		return this.prevPlanet;
	}
	
	/**
	 * This will make the last card to have been added to the player's deck this card.
	 * @param planet The new card
	 */
	public void setPrevPlanet(Card planet) {
		this.prevPlanet = planet;
	}
	
	/**
	 * This will return the last solar system to be added to the player's deck.
	 * @return A solar system card.
	 */
	public CardSet getPrevSolar() {
		return this.prevSolar;
	}
	
	/**
	 * This will set the last solar system to be added to the player's deck.
	 * @param solar The new set.
	 */
	public void setPrevSolar(CardSet solar) {
		this.prevSolar = solar;
	}
}
