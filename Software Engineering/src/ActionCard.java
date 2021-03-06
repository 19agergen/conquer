import java.io.File;
import java.util.Random;
import javax.swing.*;

/**
 * This class is the "Action" type of card. It is used to represent the actions any "action" can have.
 * @author DAT Software Engineering
 *
 */
public class ActionCard extends JPanel {
	private String sub_type, title, description;
	private int popChange; //This is the positive or negative number of population the action will cause
	private double actionPercentChange;
	private String image_location;
	private CardInfoModal modal;
	
	/**
	 * 
	 * @param sub_type
	 * @param popChange This is the positive or negative number of population the action will cause
	 * @param actionPercentChange This is the percent change of amount of affect any action will have.
	 */
	public ActionCard(String sub_type, double popChange, double actionPercentChange) {
		this.sub_type = sub_type;
		this.popChange = (int) popChange;
		this.actionPercentChange = actionPercentChange;
		
		Random rand = new Random();
		double positivePop;
		if (popChange < 0) { //This random integer generator needs a positive number so we make this number positive.
			positivePop = -1 * popChange;
		} else {
			positivePop = popChange;
		}
		
		int randChange =  rand.nextInt((int) (positivePop * actionPercentChange)); //This is a number that adds randomness to the amount of change an action can cause to any given population.
		int operator = rand.nextInt(2);
		if (operator == 0) { //zero means add the randChange to the popChange
			this.popChange += randChange;
		}else { //one means subtract the randChage to the popChange
			this.popChange -= randChange;
		}
		
	}
	
	/**
	 * This will return a positive or negative number that will be added to the population of a specific planet.
	 * @return the population change for this specific action.
	 */
	public int getChange() {
		return this.popChange;
	}
	
	/**
	 * This will make the modal object.
	 * @param title The title of the action
	 * @param description The description of the action
	 * @param image_location The image for the action
	 */
	public void makeModal(String title, String description, String image_location) {
		this.title = title;
		this.description = description;
		this.image_location = image_location;
		
		this.modal = new CardInfoModal(title, description, this.popChange, 0, image_location);
	}
	
	/**
	 * This will return the action card's modal.
	 * @return The modal
	 */
	public CardInfoModal getModal() {
		return this.modal;
	}
	
	/**
	 * This will return the type of action
	 * @return the action type
	 */
	public String getSubType() {
		return this.sub_type;
	}
	
	/**
	 * This will create a clone of the current action card
	 * @return the cloned action card
	 */
	public ActionCard clone() {
		ActionCard card = new ActionCard(this.sub_type, this.popChange, this.actionPercentChange);
		card.makeModal(title, description, image_location);
		return card;
	}
	
}
