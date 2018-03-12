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
import java.util.Scanner;

/**
 * the central class, with the menu and processing of the input through calling the methods in the different
 * rooms, when needed. It furthermore can calculate the Order- and Inventorylist by itself 
 * @author Philipp Fortmann
 *
 */
public class UserInteraction {

	Storageroom storer1 = new Storageroom(1);
	Storageroom storer2 = new Storageroom(2);
	Showroom showr = new Showroom();
	public final String[] beverages = { "Mineralwasser (still)", "Mineralwasser (mit K.)", "Apfelsaft", "Orangensaft", "Limonade",
			"Bier" };

	/**
	 * constructor opens menu
	 */
	public UserInteraction() throws IOException {
		menu();
	}

	/**
	 * writes the menu in console, 
	 * scans input, 
	 * executes code depending on option chosen
	 * and calls menu again at the end
	 */
	public void menu() throws IOException {
		System.out.println("\n---------------------------------------------------------------------");
		System.out.println("Herzlich Willkommen im Getränkemarktlagerplatzverwaltungssoftwaremenü");
		System.out.println("Folgende Optionen stehen zur Verfügung:");
		System.out.println("(1) Getränke kaufen");
		System.out.println("(2) Verkaufsraum auffüllen");
		System.out.println("(3) Aktuellen Lagerraumbestand verändern");
		System.out.println("(4) Maximalen Lagerraumplatz verändern");
		System.out.println("(5) Bestellliste für Lieferanten erstellen");
		System.out.println("(6) Inventarliste anzeigen lassen");
		System.out.println("(7) Programm beenden");
		System.out.println("Bitte geben sie die Nummer der gewünschten Option ein: ");
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		boolean wronginput;
		int room;
		int amount;
		int drink;
		// checks if input is valid. It will be reiterated until input is valid
		do {
			wronginput = false;
			//try-catch catches an input that is not an Integer
			try {
				if (Integer.parseInt(input) == 42) {
					System.out.println("Richtige Antwort! (Wie war nochmal die Frage?)\n");
				}
				
				//executes menu option, depending on input (Integer 1-7)
				switch (Integer.parseInt(input)) {
				
				// menu option "Buy drinks"
				case 1:
					boolean continuebuying;
					//reopens the dialog as often as costumer choose to continue
					do {
						continuebuying = false;
						
						drink = querydrink();
						System.out.println("Wieviele Kästen wollen sie kaufen?");
						amount = queryquantity();
						showr.buydrink(drink, amount);
						
						System.out.println("\n(1) Ja");
						System.out.println("(2) Nein");
						System.out.println("Wollen sie noch etwas kaufen?");
						String input1 = sc.nextLine();
						boolean wronginput1;
						//reopens the dialog as often as costumer choose to continue
						do {
							wronginput1 = false;
							//catches input that is different from an input
							try {
								if(Integer.parseInt(input1) == 42) {
									System.out.println("Richtige Antwort! (Wie war nochmal die Frage?)");
								}
								if(Integer.parseInt(input1) == 1) {
									continuebuying = true; 
								} 
								else if (Integer.parseInt(input1) == 2) {
								}
								else {
									wronginput1 = true;
									System.out.println("Fehler: Sie haben keine valide Zahl eingegeben");
								}
							} 
							catch (NumberFormatException nfe) {
								System.out.println("Fehler: Ihre Eingabe entspricht keiner Zahl");
								wronginput1 = true;
							}
							
						} while (wronginput1);
					} while (continuebuying);
					break;
				
				// menu option "Fill showroom"
				case 2:
					room = queryroom();
					if (room == 1) {
						showr.fillShowRoom(1, storer1, storer2);
					} else {
						showr.fillShowRoom(2, storer1, storer2);
					}
					break;
				
				// menu option "change current storageroom space"
				case 3:
					room = queryroom();
					drink = querydrink();
					System.out.println("Um wieviel wollen sie den aktuellen Lagerbestand verändern?");
					amount = queryquantity();
					//increases/ decreases amount in storageroom 1/2, depending on option chosen
					if (amount > 0) {
						if (room == 1) {
							storer1.increaseAmount(drink, amount);
						} else {
							storer2.increaseAmount(drink, amount);
						}
					} else if (amount < 0) {
						if (room == 1) {
							storer1.decreaseAmount(drink, -amount);
						} else {
							storer2.decreaseAmount(drink, -amount);
						}
					}
					System.out.println(
							"Die Operation wurde erfolgreich durchgeführt (falls keine Fehlermeldung oben aufgetaucht ist");
					break;
				
				// menu option "change max storageroom space"
				case 4:
					room = queryroom();
					drink = querydrink();
					System.out.println("Um wieviel wollen sie den maximalen Lagerbestand verändern?");
					amount = queryquantity();
					//increases/ decreases man in storageroom 1/2, depending on option chosen
					if (amount > 0) {
						if (room == 1) {
							storer1.increaseMax(drink, amount);
						} else {
							storer2.increaseMax(drink, amount);
						}
					} else if (amount < 0) {
						if (room == 1) {
							storer1.decreaseMax(drink, -amount);
						} else {
							storer2.decreaseMax(drink, -amount);
						}
					}
					break;
				
				// menu option "create orderlist"
				case 5:
					//prints Orderlist on console
					getOrderList(1);
					
					System.out.println("(1) Ja");
					System.out.println("(2) Nein");
					System.out.println("Wollen sie die Ausgabe auch als Datei?");
					String input5 = sc.nextLine();
					boolean wronginput5;
					//reiterates over the question until input is valid
					do {
						//catches input that is not an integer
						try {
						wronginput5 = false;
						if (Integer.parseInt(input5) == 42) {
							System.out.println("Richtige Antwort! (Wie war nochmal die Frage?)\n");
						}
						//if yes was chosen, the input is also put in a file
						if (Integer.parseInt(input5) == 1) {
							getOrderList(2);
							System.out.println("Die Datei wurde erstellt");
						} else if (Integer.parseInt(input5) == 2) {
							System.out.println("Es wurde keine Datei erstellt");
						}
						else {
							wronginput5 = true;
							System.out.println("Sie haben keine valide Zahl angegeben, bitte wählen sie eine der oben genannten Optionen");
						}
						}
						catch (NumberFormatException nfe) {
							System.out.println("Fehler: Sie haben keine Zahl eingegeben.");
							wronginput5 = true;
						}
					} while (wronginput5);
					break;
				
				// menu option "create Inventorylist"
				case 6:
					getInventoryList();
					break;
				
				// menu option "exit program"
				case 7:
					System.out.println("Das Programm wurde beendet");
					sc.close();
					endProgramm();
					break;
				default:
					System.out.println(
							"Die Eingabe war keine der genannten Nummern. Bitte geben sie eine der oben genannten Nummern ein:");
					input = sc.nextLine();
					wronginput = true;
					break;
				}
			} catch (NumberFormatException nfe) {
				System.out.println(
						"Ihre Eingabe entsprach keiner Zahl. Bitte geben sie eine der oben genannten Nummern ein:");
				input = sc.nextLine();
				wronginput = true;
			}
		} while (wronginput);
		menu();
	}

	/**
	 * queries for the storage room and returns the number that the user typed in
	 * 
	 * @return returns either "1" or "2", representing storage room 1 or 2
	 */
	public int queryroom() {
		System.out.println("Für welchen Lagerraum wollen sie diese Aktion ausführen? (1) oder (2)?");
		// detects input
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		boolean wronginput;
		//reiterates over the question until input is valid
		do {
			wronginput = false;
			//catches input that is not an integer
			try {
				if (Integer.parseInt(input) == 42) {
					System.out.println("Richtige Antwort! (Wie war nochmal die Frage?)\n");
				}
				// checks whether number is valid (it then returns the value) or not
				if (Integer.parseInt(input) == 1 || Integer.parseInt(input) == 2) {
					return Integer.parseInt(input);
				} else {
					System.out.println(
							"Eingabe entspricht keiner validen Zahl. Bitte geben sie eine der oben genannten Nummern ein: ");
					input = sc.nextLine();
					wronginput = true;
				}
			} catch (NumberFormatException nfe) {
				System.out.println(
						"Ihre Eingabe entspricht keiner Zahl. Bitte geben sie eine der oben genannten Nummern ein");
				input = sc.nextLine();
				wronginput = true;
			}
		} while (wronginput);
		// the method will never end here but the statements are needed so the compiler is happy
		sc.close();
		return -1;
	}
	/**
	 * queries for the drink and returns the number of it
	 * 
	 * @return returns drink number (ranges from 0 to 5)
	 */
	public int querydrink() {

		System.out.println("(1) Wasser, still");
		System.out.println("(2) Wasser, mit Kohlensäure");
		System.out.println("(3) Apfelsaft");
		System.out.println("(4) Orangensaft");
		System.out.println("(5) Limonade");
		System.out.println("(6) Bier");
		System.out.println("Welches Getränk wählen sie?");
		// detects input
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		boolean wronginput;
		// returns number of drink or asks again for input, if input was false
		do {
			wronginput = false;
			//catches wrong input (not an integer)
			try {
				if (Integer.parseInt(input) == 42) {
					System.out.println("Richtige Antwort! (Wie war nochmal die Frage?)\n");
				}
				//returns the number of the drink (depending on user input)
				switch (Integer.parseInt(input)) {
				case 1:
					return Integer.parseInt(input) - 1;
				case 2:
					return Integer.parseInt(input) - 1;
				case 3:
					return Integer.parseInt(input) - 1;
				case 4:
					return Integer.parseInt(input) - 1;
				case 5:
					return Integer.parseInt(input) - 1;
				case 6:
					return Integer.parseInt(input) - 1;
				default:
					System.out.println(
							"Eingabe entspricht keiner validen Zahl. Bitte geben sie eine der oben genannten Nummern ein: ");
					input = sc.nextLine();
					wronginput = true;
					break;
				}
			} catch (NumberFormatException nfe) {
				System.out.println(
						"Ihre Eingabe entsprach keiner Zahl. Bitte geben sie eine der oben genannten Nummern ein");
				input = sc.nextLine();
				wronginput = true;
			}
		} while (wronginput);
		// the method will never end here but the statements are needed so the compiler is happy
		sc.close();
		return -1;
	}

	/**
	 * queries for the amount, accepts any kind of input, as long as it is an integer
	 * 
	 * @return the amount that the user has chosen
	 */
	public int queryquantity() {

		// detects input
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		boolean wronginput;

		//reiterates until user input is valid
		do {
			wronginput = false;
			
			//catches input that is not an integer
			try {
				if (Integer.parseInt(input) == 42) {
					System.out.println("Richtige Antwort! (Wie war nochmal die Frage?)\n");
				}
				// checks whether input is an int or not
				if (Integer.parseInt(input) >= 0 || Integer.parseInt(input) < 0) {
					return Integer.parseInt(input);
				} else {
					System.out.println(
							"Eingabe entsprach keiner validen Zahl. Bitte geben sie eine der oben genannten Nummern ein: ");
					input = sc.nextLine();
					wronginput = true;
				}
			} catch (NumberFormatException nfe) {
				System.out.println(
						"Ihre Eingabe entsprach keiner Zahl. Bitte geben sie eine der oben genannten Nummern ein");
				input = sc.nextLine();
				wronginput = true;
			}
		} while (wronginput);
		// the method will never end here but the statements are needed so the compiler is happy
		sc.close();
		return -1;
	}

	/**
	 * outputs the orderlist. Either on the console or as a .txt-file
	 * the orderlist is the added difference(max-amount) for all products in all rooms where max>amount
	 *
	 * @param output determines wether the output is on the console or as a .txt-file
	 * @throws IOException IOException appears during the creation of file. Throw needed, so that creation works
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
			// file path should look like "C:/Users/WillySchreiter/Documents/BA-RM/DuI Algorithmen/Projekt"
			String userDirectory = System.getProperty("user.home");
			String dirPath = userDirectory + "/Documents/BA-RM/DuI Algorithmen/Projekt";
			Path dir = Paths.get(dirPath);
			String filePath = dirPath + "/Bestellliste.txt";
			Path file = Paths.get(filePath);
			Files.createDirectories(dir);
			Files.write(file, lines, Charset.forName("UTF-8"));
			// opens file
			ProcessBuilder pb = new ProcessBuilder("Notepad.exe",
					filePath);
			pb.start();

			System.out.println("Die Datei wurde geschrieben.");
		}
	}

	/**
	 * outputs the amount for every drink separated for every room
	 * on the console
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
	 * constructs a new UserInteraction object,
	 * starts the program
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		UserInteraction ui = new UserInteraction();
	}
}
