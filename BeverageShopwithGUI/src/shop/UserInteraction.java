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
	public void getOrderList(int output) throws IOException {
		Room roomobj = showr;
		int missingWaterStill = 0;
		int missingWaterSparkling = 0;
		int missingAppleJuice = 0;
		int missingOrangeJuice = 0;
		int missingLimonade = 0;
		int missingBeer = 0;
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
			missingWaterStill += roomobj.getMax(0) - roomobj.getAmount(0);
			missingWaterSparkling += roomobj.getMax(1) - roomobj.getAmount(1);
			missingAppleJuice += roomobj.getMax(2) - roomobj.getAmount(2);
			missingOrangeJuice += roomobj.getMax(3) - roomobj.getAmount(3);
			missingLimonade += roomobj.getMax(4) - roomobj.getAmount(4);
			missingBeer += roomobj.getMax(5) - roomobj.getAmount(5);
		}
		String s1 = "Insgesamt werden noch " + missingWaterStill + " Kästen Wasser (still) benötigt.";
		String s2 = "Insgesamt werden noch " + missingWaterSparkling + " Kästen Wasser (mit Kohlensäure) benötigt.";
		String s3 = "Insgesamt werden noch " + missingAppleJuice + " Kästen Apfelsaft benötigt.";
		String s4 = "Insgesamt werden noch " + missingOrangeJuice + " Kästen Orangensaft benötigt.";
		String s5 = "Insgesamt werden noch " + missingLimonade + " Kästen Limonade benötigt.";
		String s6 = "Insgesamt werden noch " + missingBeer + " Kästen Bier benötigt.";
		// output to console
		if (output == 1) {
			System.out.println(s1);
			System.out.println(s2);
			System.out.println(s3);
			System.out.println(s4);
			System.out.println(s5);
			System.out.println(s6);
		}
		// output to file
		else if (output == 2) {
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
	}

	/**
	 * outputs the amount for every drink separated for every room on the console
	 */
	public void getInventoryList() {

		// table made from scratch. To make the columns fit, there have to be different
		// amounts of spaces
		System.out.print("Verkaufsraum                     ");
		System.out.print("Lagerraum 1                        ");
		System.out.println("Lagerraum 2");
		System.out.print("------------------------+----     ");
		System.out.print("------------------------+-----      ");
		System.out.println("------------------------+----");
		System.out.print(beverages[0] + "   |  " + showr.getAmount(0) + "     ");
		System.out.print(beverages[0] + "   |  " + storer1.getAmount(0) + "      ");
		System.out.println(beverages[0] + "   |  " + storer2.getAmount(0));

		System.out.print(beverages[1] + "  |  " + showr.getAmount(1) + "     ");
		System.out.print(beverages[1] + "  |  " + storer1.getAmount(1) + "      ");
		System.out.println(beverages[1] + "  |  " + storer2.getAmount(1));

		System.out.print(beverages[2] + "               |  " + showr.getAmount(2) + "     ");
		System.out.print(beverages[2] + "               |  " + storer1.getAmount(2) + "       ");
		System.out.println(beverages[2] + "               |  " + storer2.getAmount(2));

		System.out.print(beverages[3] + "             |  " + showr.getAmount(3) + "      ");
		System.out.print(beverages[3] + "             |  " + storer1.getAmount(3) + "       ");
		System.out.println(beverages[3] + "             |  " + storer2.getAmount(3));

		System.out.print(beverages[4] + "                |  " + showr.getAmount(4) + "     ");
		System.out.print(beverages[4] + "                |  " + storer1.getAmount(4) + "       ");
		System.out.println(beverages[4] + "                |  " + storer2.getAmount(4));

		System.out.print(beverages[5] + "                    |  " + showr.getAmount(5) + "      ");
		System.out.print(beverages[5] + "                    |  " + storer1.getAmount(5) + "       ");
		System.out.println(beverages[5] + "                    |  " + storer2.getAmount(5));
		System.out.print("------------------------+----     ");
		System.out.print("------------------------+-----      ");
		System.out.println("------------------------+----");

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
