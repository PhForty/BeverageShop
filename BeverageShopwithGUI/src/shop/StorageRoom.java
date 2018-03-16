package shop;

/**
 * this class represents the storage room, it inherits from "Room"
 * 
 * @author Philipp Fortmann
 * @see Room
 */
public class StorageRoom extends Room {

	/**
	 * Constructor creates the room with distinct amount and max values depending on
	 * which storage room is to be created
	 * 
	 * @param room
	 *            representing either storage room 1 or 2
	 */
	public StorageRoom(int room) {
		// if storageroom one is created:
		if (room == 1) {

			setAmount(0, 100);
			setMax(0, 100);
			setAmount(1, 100);
			setMax(1, 100);
			setAmount(2, 50);
			setMax(2, 50);
			setAmount(3, 50);
			setMax(3, 50);
			setAmount(4, 60);
			setMax(4, 60);
			setAmount(5, 40);
			setMax(5, 40);
			// if storageroom two is created:
		} else if (room == 2) {

			setAmount(0, 0);
			setMax(0, 0);
			setAmount(1, 0);
			setMax(1, 0);
			setAmount(2, 50);
			setMax(2, 50);
			setAmount(3, 50);
			setMax(3, 50);
			setAmount(4, 40);
			setMax(4, 40);
			setAmount(5, 40);
			setMax(5, 40);

		}
	}

	/**
	 * increases the max value of a drink
	 * 
	 * @param drink
	 *            the drink where max will be increased
	 * @param inc
	 *            value by which max is increased
	 * @return returns one if successful
	 */
	public int increaseMax(int drink, int inc) {
		setMax(drink, getMax(drink) + inc);
		return 1;
	}

	/**
	 * decreases the max value of a drink, if possible
	 * 
	 * @param drink
	 *            the drink where max will be decreased
	 * @param dec
	 *            value by which max is decreased (positive integer)
	 * @return returns 1 if successful, 4 if max can't be lowered (because it would
	 *         go under amount or because it would go under zero
	 */
	public int decreaseMax(int drink, int dec) {
		// checks if new max would be lower than current amount
		if ((this.getMax(drink) - dec) > this.getAmount(drink)) {
			return 4;
		}
		// checks if new max would be lower than zero
		else if (this.getMax(drink) < dec) {
			return 4;
		}
		// decreases max
		else {
			setMax(drink, this.getMax(drink) - dec);
			return 1;
		}
	}
}