package shop;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * this class contains the logic. It knows the show room and the store rooms
 * interacts with them. It furthermore can create an inventory list and an order
 * list. It stands between the GUI and the rooms.
 * 
 * @author Philipp Fortmann
 * @see GUI, ShowRoom, StorageRoom
 */
public class UserInteraction {

	StorageRoom storer1 = new StorageRoom(1);
	StorageRoom storer2 = new StorageRoom(2);
	ShowRoom showr = new ShowRoom();

	/**
	 * this method executes the chosen option from the class GUI
	 * 
	 * @param input
	 *            the chosen menu option. an value between 1 and 7 is expected
	 * @param room
	 *            the storage room number, ignored, when room number is not needed
	 *            for action
	 * @param quantity
	 *            the quantity for which the action will be performed, ignored when
	 *            not needed
	 * @param drink
	 *            the drink for which the action will be performed, ignored when not
	 *            needed
	 * @return returns 0-4, depending whether the execution was successful or not. 0
	 *         = something went really wrong; 1= successful; 2 = amount is not high
	 *         enough; 3 = can't make amount that high; 4 = the maximum space can't
	 *         be lowered that much
	 * @throws IOException
	 *             needed, because creation of file might throw this error
	 * @see GUI, {@link #getOrderList(int)}, {@link #getInventoryList()}
	 */
	public int menu(int input, int room, int quantity, int drink) throws IOException {
		int returnvalue = 0;

		// executes menu option, depending on input (Integer 1-7)
		switch (input) {

		// menu option "Buy drinks"
		case 1:
			returnvalue = showr.sellDrink(drink, quantity);
			break;
		// menu option "Fill showroom"
		case 2:
			showr.fillShowRoom(room, storer1, storer2);
			returnvalue = 1;
			break;

		// menu option "change current storageroom space"
		case 3:
			// increases/ decreases amount in storageroom 1/2, depending on option chosen
			if (quantity > 0) {
				if (room == 1) {
					returnvalue = storer1.increaseAmount(drink, quantity);
				} else {
					returnvalue = storer2.increaseAmount(drink, quantity);
				}
			} else if (quantity < 0) {
				if (room == 1) {
					returnvalue = storer1.decreaseAmount(drink, -quantity);
				} else {
					returnvalue = storer2.decreaseAmount(drink, -quantity);
				}
			}
			break;

		// menu option "change max storageroom space"
		case 4:
			// increases/ decreases max in storageroom 1/2, depending on option chosen
			if (quantity >= 0) {
				if (room == 1) {
					returnvalue = storer1.increaseMax(drink, quantity);
				} else {
					returnvalue = storer2.increaseMax(drink, quantity);
				}
			} else if (quantity < 0) {
				if (room == 1) {
					returnvalue = storer1.decreaseMax(drink, -quantity);
				} else {
					returnvalue = storer2.decreaseMax(drink, -quantity);
				}
			}
			break;

		// menu option "create orderlist"
		case 5:
			// prints Orderlist on GUI
			getOrderList(1);
			returnvalue = 1;

			// menu option "create Inventorylist"
		case 6:
			getInventoryList();
			returnvalue = 1;

			// menu option "exit program"
		case 7:
			endProgramm();
			break;
		}
		return returnvalue;
	}

	/**
	 * outputs the orderlist. As an Integer-Array and optionally as a .txt-file. The
	 * orderlist is the added difference(max-amount) for all products in all rooms
	 * where max>amount
	 * 
	 * @param output
	 *            defines whether file is created or not. If it is "2" a file is
	 *            created
	 * @return returns an Integer-Array, filled with the missing amounts for
	 *         everydrink, over all rooms
	 * @throws IOException
	 *             needed, because creation of file might throw this exception
	 */
	public int[] getOrderList(int output) throws IOException {
		Room roomobj = showr;
		int[] missing = { 0, 0, 0, 0, 0, 0 };
		// determines the values for the missing beverages over all rooms
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 6; i++) {
				switch (j) {
				case 0:
					break;
				case 1:
					roomobj = storer1;
					break;
				case 2:
					roomobj = storer2;
					break;
				}
				missing[i] += roomobj.getMax(i) - roomobj.getAmount(i);
			}
		}
		// output to file
		if (output == 2) {
			// creates the sentences

			String s1 = "Insgesamt werden noch " + missing[0] + " Kästen Wasser (still) benötigt.";
			String s2 = "Insgesamt werden noch " + missing[1] + " Kästen Wasser (mit Kohlensäure) benötigt.";
			String s3 = "Insgesamt werden noch " + missing[2] + " Kästen Apfelsaft benötigt.";
			String s4 = "Insgesamt werden noch " + missing[3] + " Kästen Orangensaft benötigt.";
			String s5 = "Insgesamt werden noch " + missing[4] + " Kästen Limonade benötigt.";
			String s6 = "Insgesamt werden noch " + missing[5] + " Kästen Bier benötigt.";

			// creates timestamp
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("HH:mm - dd.MM.yy");
			String nowstring = String.valueOf(dateFormat.format(date));

			// writes file
			List<String> lines = Arrays.asList("Zeitstempel: " + nowstring, s1, s2, s3, s4, s5, s6);
			// file path should look like "C:/Users/WillySchreiter/Documents/BA-RM/DuI
			// Algorithmen/Projekt"
			String userDirectory = System.getProperty("user.home");
			String dirPath = userDirectory + "/Documents/BA-RM/DuI Algorithmen/Projekt";
			Path dir = Paths.get(dirPath);
			String filePath = dirPath + "/Bestellliste.txt";
			Path file = Paths.get(filePath);
			Files.createDirectories(dir);
			Files.write(file, lines, Charset.forName("UTF-8"));
			// opens file
			ProcessBuilder pb = new ProcessBuilder("Notepad.exe", filePath);
			pb.start();

		}
		return missing;
	}

	/**
	 * outputs the list for all amounts, of all drinks, for every room individually
	 * 
	 * @return the result is stored in an 2D-Array. The columns are for the rooms,
	 *         the rows for the drinkamount
	 */
	public int[][] getInventoryList() {

		// String Array for [room][drinkamount]
		int[][] inventory = new int[3][6];

		// iterates over the rooms
		for (int i = 0; i < 3; i++) {
			// iterates over the drinks
			for (int j = 0; j < 6; j++) {
				// sets the specific values, for every combination of room and drink
				switch (i) {
				case 0:
					inventory[i][j] = showr.getAmount(j);
					break;
				case 1:
					inventory[i][j] = storer1.getAmount(j);
					break;
				case 2:
					inventory[i][j] = storer2.getAmount(j);
					break;
				}
			}
		}
		return inventory;
	}

	/**
	 * terminates the programm
	 */
	public void endProgramm() {
		System.exit(0);
	}
}
