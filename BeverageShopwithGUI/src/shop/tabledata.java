package shop;
/**
 * this class is needed to fill the tableview in GUIfirsttry. Every row there is one of this class( with the 4 variables)
 * Four variables here means for columns there
 * @author Philipp Fortmann
 *
 */
public class tabledata {
	
	private String drink;
	private int showr;
	private int storer1;
	private int storer2;

	/**
	 * constructs the class by setting all four variables
	 * 
	 * @param drink used for naming the drink
	 * @param showr amount of the drink in the show room
	 * @param storer1 amount of the drink in the store room 1
	 * @param storer2 amount of the drink in the store room 2
	 */
	public tabledata (int drink, int showr, int storer1, int storer2) {
		switch (drink) {
			case 0:
				this.drink = "Mineralwasser (still)";
				break;
			case 1:
				this.drink = "Mineralwasser (mit K.)";
				break;
			case 2:
				this.drink = "Apfelsaft";
				break;
			case 3:
				this.drink = "Orangensaft";
				break;
			case 4:
				this.drink = "Limonade";
				break;
			case 5:
				this.drink = "Bier";
				break;
		}
		this.showr = showr;
		this.storer1 = storer1;
		this.storer2 = storer2;
	}
	/**
	 * simple getter method for drink
	 * @return returns the name of the drink 
	 */
	public String getDrink() {
		return drink;
	}
	/**
	 * simple setter method for the variable drink
	 * 
	 * @param drink takes the name for the drink
	 */
	public void setDrink(String drink) {
		this.drink = drink;
	}
	
	/**
	 * simple getter method for the variable showr
	 * 
	 * @return returns the value of showr
	 */
	public int getShowr() {
		return showr;
	}

	/**
	 * simple setter method for setting the variable showr
	 * 
	 * @param showr the amount of drinks, to which showr will be set
	 */
	public void setShowr(int showr) {
		this.showr = showr;
	}

	/**
	 * simple getter method for the variable storer1
	 * 
	 * @return returns value of storer1
	 */
	public int getStorer1() {
		return storer1;
	}

	/**
	 * simple setter method for the variable storer1
	 * 
	 * @param storer1 the new value for storer1
	 */
	public void setStorer1(int storer1) {
		this.storer1 = storer1;
	}

	/**
	 * simple getter method for the variable storer2
	 * 
	 * @return returns value of storer2
	 */
	public int getStorer2() {
		return storer2;
	}

	/**
	 * simple setter method for the variable storer2
	 * 
	 * @param storer1 the new value for storer2
	 */
	public void setStorer2(int storer2) {
		this.storer2 = storer2;
	}
	
}
