package shop;

/**
 * abstract motherclass for show- and storageroom contains an amount- and max
 * array with its corresponding getter and setter Methods and methods to
 * increase and decrease amount, which is needed in every room
 * 
 * @author Philipp Fortmann
 * @see StorageRoom, ShowRoom
 */
public abstract class Room {

	private int[] amount = new int[6];
	private int[] max = new int[6];
	public static final String[] beverages = { "Mineralwasser (still)", "Mineralwasser (mit K.)", "Apfelsaft",
			"Orangensaft", "Limonade", "Bier" };

	/**
	 * gets the amount of a specified drink and returns it
	 * 
	 * @param drink
	 *            drink from which the amount is given back
	 * @return returns the amount of the specified drink
	 */
	public int getAmount(int drink) {
		return amount[drink];
	}

	/**
	 * sets the amount of a given drink to a given value
	 * 
	 * @param drink
	 *            drink whose amount is to be changed
	 * @param value
	 *            value which will be the new amount
	 */
	public void setAmount(int drink, int value) {
		amount[drink] = value;
	}

	/**
	 * increases the amount of a given drink by a given value, if there is not
	 * enough space left the remaining drinks will be ignored
	 * 
	 * @param drink
	 *            drink whose amount is to be increased
	 * @param inc
	 *            value by which the amount is increased
	 * @return returns 1 if successful, 2 when amount would go over max
	 */
	public int increaseAmount(int drink, int inc) {
		// checks if amount can be increased by the full inc value
		// if not: amount is set to max, the remaining drinks are dropped
		if (getAmount(drink) > (getMax(drink) - inc)) {
			setAmount(drink, getMax(drink));
			return 3;
		} else {
			setAmount(drink, getAmount(drink) + inc);
			return 1;
		}
	}

	/**
	 * decreases the amount of a given drink by a given value, if possible
	 * 
	 * @param drink
	 *            drink whose amount is to be decreased
	 * @param dec
	 *            value by which the amount is decreased (expects positive Integer)
	 * @return returns 1 if successful, 2 if amount would be lowered below zero
	 */
	public int decreaseAmount(int drink, int dec) {
		// checks if amount would get negative. If thats the case nothing is decreased
		if (getAmount(drink) < dec) {
			return 2;
		} else {
			setAmount(drink, getAmount(drink) - dec);
			return 1;
		}
	}

	/**
	 * returns the max of a given drink
	 * 
	 * @param drink
	 *            specifies which drink's max is wanted
	 * @return the max value of the given drink
	 */
	public int getMax(int drink) {
		return max[drink];
	}

	/**
	 * sets max of a given drink to a given value
	 * 
	 * @param drink
	 *            specifies which drink's max is to be newly set
	 * @param value
	 *            the value by to which max is set
	 */
	public void setMax(int drink, int value) {
		max[drink] = value;
	}

}