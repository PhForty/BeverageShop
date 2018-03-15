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
 * the central class, with the menu and processing of the input through calling
 * the methods in the different rooms, when needed. It furthermore can calculate
 * the Order- and Inventorylist by itself
 * 
 * @author Philipp Fortmann
 *
 */
public class UserInteraction {

	Storageroom storer1 = new Storageroom(1);
	Storageroom storer2 = new Storageroom(2);
	Showroom showr = new Showroom();
	public final String[] beverages = { "Mineralwasser (still)", "Mineralwasser (mit K.)", "Apfelsaft", "Orangensaft",
			"Limonade", "Bier" };

	/**
	 * constructor opens menu
	 */
	public UserInteraction() throws IOException {
	}

	/**
	 * writes the menu in console, scans input, executes code depending on option
	 * chosen and calls menu again at the end
	 */
	// TODO return value, to know in GUI whether it was successful or not (and
	// display that)
	// return 0: something went wrong
	// return 1: successfull
	// return 2: not enough there (when buying/ lowering amount)
	// return 3: cant make amount that high (goes over max)
	// return 4: maxspace cant be lowered by that much
	public int menu(int input, int room, int quantity, int drink) throws IOException {
		int returnvalue = -1;
		if (input == 42) {
			System.out.println("Richtige Antwort! (Wie war nochmal die Frage?)\n");
		}

		// executes menu option, depending on input (Integer 1-7)
		switch (input) {

		// menu option "Buy drinks"
		case 1:
			returnvalue = showr.buydrink(drink, quantity);
			break;
		// menu option "Fill showroom"
		case 2:
			if (room == 1) {
				showr.fillShowRoom(room, storer1, storer2);
				returnvalue = 1;
			} else if (room == 2) {
				showr.fillShowRoom(room, storer1, storer2);
				returnvalue = 1;
			}
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
			// increases/ decreases man in storageroom 1/2, depending on option chosen
			if (quantity > 0) {
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
			// TODO print Orderlist on GUI
			// TODO find a way to give the user the choice to write a file as well
			// prints Orderlist on console
			getOrderList(1);
			returnvalue = 1;

		// menu option "create Inventorylist"
		case 6:
			// TODO print Inventorylist on GUI
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
	 * outputs the orderlist. Either on the console or as a .txt-file the orderlist
	 * is the added difference(max-amount) for all products in all rooms where
	 * max>amount
	 *
	 * @param output
	 *            determines wether the output is on the console or as a .txt-file
	 * @throws IOException
	 *             IOException appears during the creation of file. Throw needed, so
	 *             that creation works
	 */
	public int[] getOrderList(int output) throws IOException {
		Room roomobj = showr;
		int[] missing = {0,0,0,0,0,0};
		// determines the values for the missing beverages over all rooms
		for (int i = 0; i < 3; i++) {
			switch (i) {
			case 0:
				break;
			case 1:
				roomobj = storer1;
				break;
			case 2:
				roomobj = storer2;
				break;
			}
			missing[0] += roomobj.getMax(0) - roomobj.getAmount(0);
			missing[1] += roomobj.getMax(1) - roomobj.getAmount(1);
			missing[2] += roomobj.getMax(2) - roomobj.getAmount(2);
			missing[3] += roomobj.getMax(3) - roomobj.getAmount(3);
			missing[4] += roomobj.getMax(4) - roomobj.getAmount(4);
			missing[5] += roomobj.getMax(5) - roomobj.getAmount(5);
		}
		String s1 = "Insgesamt werden noch " + missing[0] + " Kästen Wasser (still) benötigt.";
		String s2 = "Insgesamt werden noch " + missing[1] + " Kästen Wasser (mit Kohlensäure) benötigt.";
		String s3 = "Insgesamt werden noch " + missing[2] + " Kästen Apfelsaft benötigt.";
		String s4 = "Insgesamt werden noch " + missing[3] + " Kästen Orangensaft benötigt.";
		String s5 = "Insgesamt werden noch " + missing[4] + " Kästen Limonade benötigt.";
		String s6 = "Insgesamt werden noch " + missing[5] + " Kästen Bier benötigt.";
		
		// output to file
		if (output == 2) {
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

			System.out.println("Die Datei wurde geschrieben.");
		}
		return missing;
	}

	/**
	 * outputs the amount for every drink separated for every room
	 * 
	 * @return returns an 2D Integer-Array with room and drinkamount
	 */
	public int[][] getInventoryList() {
		
		//String Array for [room][drinkamount]
		int[][] inventory = new int[3][6];
		
		//iterates over the rooms
		for(int i = 0; i<3 ; i++) {
			//iterates over the drinks
			for(int j = 0; j<6; j++) {
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

	/**
	 * constructs a new UserInteraction object, starts the program
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		UserInteraction ui = new UserInteraction();
	}
}
