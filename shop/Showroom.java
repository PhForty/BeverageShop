package shop;

/**
 * This class represents the show room, it inherits from "Room"
 * 
 * @author Philipp Fortmann
 *
 */
public class Showroom extends Room {
	
	/**
	 * constructor creates showroom with specific values for max and amount
	 */
    public Showroom() {
    	
	    setAmount(0, 15);
	    setMax(0, 15);
	    setAmount(1, 15);
	    setMax(1, 15);
	    setAmount(2, 10);
	    setMax(2, 10);
	    setAmount(3, 5);
	    setMax(3, 5);
	    setAmount(4, 10);
	    setMax(4, 10);
	    setAmount(5, 5);
	    setMax(5, 5);
	    
    }
   
    /**
     * this method decreases the amount of a specified drink (if possible)
     * 
     * @param drink specifies for which drink the operation is done
     * @param quantity amount, which shall be bought (and therefore is subtracted)
     */
    public void buydrink(int drink, int quantity) {
		// check if drink exists
		if (drink >= 0 && drink < 6) {
			// check if showroom has enough left
			if (this.getAmount(drink) >= quantity) {
				this.decreaseAmount(drink, quantity);
				System.out.println(quantity + " Kästen wurden erfolgreich verkauft.");
			} else {
				System.out.println("Fehler. Im Verkaufsraum gibt es nur " + this.getAmount(drink) + " Kästen");
			}
		}
	}
    
    /**
     * calculates where the amount and max do not match in the show room
     * then transfers the missing amounts from the chosen storage room to the show room
     * 
     * @param roomnumber the room from where the show room will be filled
     * @param storer1 storage room 1, to apply the possible changes
     * @param storer2 storage room 2, to apply the possible changes
     */
    public void fillShowRoom(int roomnumber, Storageroom storer1, Storageroom storer2) {
		// checks if roomnumber is valid
		if (roomnumber == 1 || roomnumber == 2) {
			// iterates for all drinks
			for(int i = 0; i < 6; i++) {
				int difference = this.getMax(i) - this.getAmount(i);
					// if room 1 was chosen:
					if (roomnumber == 1) {
						// if there is enough left in storageroom:
						if (storer1.getAmount(i) >= difference) {
							storer1.decreaseAmount(i, difference);
							this.increaseAmount(i, difference);
						} 
						// if there is not enough left in storageroom:
						else {
							this.increaseAmount(i, storer1.getAmount(i));
							storer1.setAmount(i, 0);
						}
					} 
					// if room 2 was chosen:
					else {
						// if there is enough left in storageroom:
						if (storer2.getAmount(i) >= difference) {
							storer2.decreaseAmount(i, difference);
							this.increaseAmount(i, difference);
						} 
						// if there is not enough left in storageroom:
						else {
							this.increaseAmount(i, storer2.getAmount(i));
							storer2.setAmount(i, 0);
						}
					}
				}
			System.out.println("Die Getränke im Verkaufsraum wurden, soweit möglich, aus dem Lager " + roomnumber + " wieder aufgestockt");
		} else {
			System.out.println("Diese Raumnummer gibt es nicht. Bitte wählen sie '1' oder '2'");
		}
	}

}