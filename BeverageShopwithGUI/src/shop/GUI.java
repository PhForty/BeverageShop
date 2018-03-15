package shop;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//TODO evt. kleine Logos für die jeweiligen MenüOptionen erstellen
/**
 * this class is the GUI, it takes the user input and processes it, by giving it
 * to UserInteraction, in an specified format
 * 
 * @author Philipp Fortmann
 *
 */
public class GUI extends Application {
	/**
	 * launches GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * explicit constructor because creation of file in UserInteraction might throw
	 * a IOException
	 * 
	 * @throws IOException
	 *             creation of file in UserInteraction might throw a IOException
	 */
	public GUI() throws IOException {
		super();
	}

	// creates the needed objects
	UserInteraction ui = new UserInteraction();
	// these are the values that UserInteraction gets, when called by
	// menu(int,int,int,int);
	int chosenoption = 0;
	int room = 0;
	int drink = 0;
	int quantity = 0;

	@Override
	/**
	 * this class contains every element of the GUI, and its attributes +
	 * keylisteners
	 */
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Getränkemarktverwaltungssoftwareprogrammmenü");

		// constructs the grid, that is layed over the GUI (to have a table-like
		// structure)
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new Insets(20, 20, 20, 20));

		// title of the menu
		Text scenetitle = new Text("Menüoptionen");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		// title for displaying chosen option
		Text optiontitle = new Text("");
		optiontitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(optiontitle, 2, 0, 2, 1);

		// question and answer field for the quantity
		Label questionquantity = new Label("Für wieviele Kästen wollen \nsie diese Option ausführen?");
		questionquantity.setPadding(new Insets(0, 0, 0, 50));
		grid.add(questionquantity, 2, 1);
		TextField answerquantity = new TextField();
		answerquantity.setText("0");
		answerquantity.setPrefWidth(50);
		grid.add(answerquantity, 3, 1);
		questionquantity.setVisible(false);
		answerquantity.setVisible(false);

		// question and answer field for the drink
		Label questiondrink = new Label("Für welches Getränk wollen \nsie diese Option ausführen?");
		questiondrink.setPadding(new Insets(0, 0, 0, 50));
		grid.add(questiondrink, 2, 2);
		ObservableList<String> optionsdrink = FXCollections.observableArrayList("Mineralwasser (still)",
				"Mineralwasser (mit K.)", "Apfelsaft", "Orangensaft", "Limonade", "Bier");
		final ComboBox answerdrink = new ComboBox(optionsdrink);
		answerdrink.getSelectionModel().select("Mineralwasser (still)");
		grid.add(answerdrink, 3, 2);
		questiondrink.setVisible(false);
		answerdrink.setVisible(false);

		// question and answer field for the storage room
		Label questionroom = new Label("Für welchen Lagerraum soll \ndiese Aktion ausgeführt werden?");
		questionroom.setPadding(new Insets(0, 0, 0, 50));
		grid.add(questionroom, 2, 3);
		ObservableList<String> optionsroom = FXCollections.observableArrayList("Lagerraum 1", "Lagerraum 2");
		final ComboBox answerroom = new ComboBox(optionsroom);
		answerroom.getSelectionModel().select("Lagerraum 1");
		grid.add(answerroom, 3, 3);
		questionroom.setVisible(false);
		answerroom.setVisible(false);

		// label for displaying Orderlist
		Label orderLabel = new Label();
		orderLabel.setPadding(new Insets(0, 0, 0, 50));
		grid.add(orderLabel, 2, 1, 2, 4);
		orderLabel.setVisible(false);

		// label for displaying the status (if something went wrong for example)
		Label status = new Label();
		grid.add(status, 2, 4, 2, 2);
		status.setVisible(false);

		// table for displaying inventorylist
		TableView table = new TableView();
		table.setEditable(true);
		TableColumn firstcolumn = new TableColumn("Getränk");
		firstcolumn.setPrefWidth(200);
		TableColumn secondcolumn = new TableColumn("Verkaufsraum");
		TableColumn thirdcolumn = new TableColumn("Lagerraum 1");
		TableColumn forthcolumn = new TableColumn("Lagerraum 2");
		firstcolumn.setCellValueFactory(new PropertyValueFactory<>("drink"));
		secondcolumn.setCellValueFactory(new PropertyValueFactory<>("showr"));
		thirdcolumn.setCellValueFactory(new PropertyValueFactory<>("storer1"));
		forthcolumn.setCellValueFactory(new PropertyValueFactory<>("storer2"));
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.getColumns().addAll(firstcolumn, secondcolumn, thirdcolumn, forthcolumn);
		grid.add(table, 2, 1, 5, 4);
		table.setVisible(false);

		// button for file output (just shown for order list)
		Button fileoutput = new Button("Dateiausgabe");
		HBox hbfile = new HBox(10);
		hbfile.setAlignment(Pos.BOTTOM_RIGHT);
		hbfile.getChildren().add(fileoutput);
		grid.add(hbfile, 3, 4);
		hbfile.setVisible(false);
		fileoutput.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				try {
					// if pressed, the order list will be created as a file and opened
					ui.getOrderList(2);
					// TODO display the path where the file is
					status.setVisible(true);
					String path = System.getProperty("user.home")
							+ "\\Documents\n\\BA-RM\\DuI Algorithmen\\Projekt\\Bestellliste.txt";
					status.setText("Die Datei wurde hier gespeichert: \n" + path);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// button for starting processing the input
		Button enter = new Button("Bestätigen");
		HBox hbEnter = new HBox(10);
		hbEnter.setAlignment(Pos.BOTTOM_RIGHT);
		hbEnter.getChildren().add(enter);
		grid.add(hbEnter, 3, 4);
		hbEnter.setVisible(false);
		enter.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				int returnvalue;
				// converts the chosen room into the number for the room
				switch (answerroom.getSelectionModel().getSelectedItem().toString()) {
				case "Lagerraum 1":
					room = 1;
					break;
				case "Lagerraum 2":
					room = 2;
					break;
				}
				// converts the chosen drink into the number for the drink
				switch (answerdrink.getSelectionModel().getSelectedItem().toString()) {
				case "Mineralwasser (still)":
					drink = 0;
					break;
				case "Mineralwasser (mit K.)":
					drink = 1;
					break;
				case "Apfelsaft":
					drink = 2;
					break;
				case "Orangensaft":
					drink = 3;
					break;
				case "Limonade":
					drink = 4;
					break;
				case "Bier":
					drink = 5;
					break;

				}
				// sets the value for quantity (unless it is not an integer)
				try {
					quantity = Integer.parseInt(answerquantity.getText());

					// calls the menu method in UserInteraction with the needed values
					switch (chosenoption) {
					//executed if option "Getränk kaufen" is chosen
					case 1:
						try {
							returnvalue = ui.menu(1, room, quantity, drink);
							switch (returnvalue) {
							case -1:
								status.setVisible(true);
								status.setText("Der returnvalue wurde nicht gesetzt");
								break;
							case 0:
								status.setVisible(true);
								status.setText("Es ist ein unbekannter Fehler aufgetreten");
								break;
							case 1:
								status.setVisible(true);
								status.setText("Die Operation wurde erfolgreich durchgeführt");
								break;
							case 2:
								status.setVisible(true);
								status.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug da");
								break;
							case 3:
								status.setVisible(true);
								status.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug Platz da");
								break;
							case 4:
								status.setVisible(true);
								status.setText(
										"Bitte wählen sie eine kleinere Menge, \nder Lagerplatz würde unter die aktuelle Menge/ 0 fallen");
								break;
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					//executed if option "Verkaufsraum auffüllen" is chosen
					case 2:
						try {
							returnvalue = ui.menu(2, room, quantity, drink);
							switch (returnvalue) {
							case -1:
								status.setVisible(true);
								status.setText("Der returnvalue wurde nicht gesetzt");
								break;
							case 0:
								status.setVisible(true);
								status.setText("Es ist ein unbekannter Fehler aufgetreten");
								break;
							case 1:
								status.setVisible(true);
								status.setText("Die Operation wurde erfolgreich durchgeführt");
								break;
							case 2:
								status.setVisible(true);
								status.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug da");
								break;
							case 3:
								status.setVisible(true);
								status.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug Platz da");
								break;
							case 4:
								status.setVisible(true);
								status.setText(
										"Bitte wählen sie eine kleinere Menge, \nder Lagerplatz würde unter die aktuelle Menge/ 0 fallen");
								break;
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					//executed if option "Aktuellen Lagerraumbestand verändern" is chosen
					case 3:
						try {
							returnvalue = ui.menu(3, room, quantity, drink);
							switch (returnvalue) {
							case -1:
								status.setVisible(true);
								status.setText("Der returnvalue wurde nicht gesetzt");
								break;
							case 0:
								status.setVisible(true);
								status.setText("Es ist ein unbekannter Fehler aufgetreten");
								break;
							case 1:
								status.setVisible(true);
								status.setText("Die Operation wurde erfolgreich durchgeführt");
								break;
							case 2:
								status.setVisible(true);
								status.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug da");
								break;
							case 3:
								status.setVisible(true);
								status.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug Platz da");
								break;
							case 4:
								status.setVisible(true);
								status.setText(
										"Bitte wählen sie eine kleinere Menge, \nder Lagerplatz würde unter die aktuelle Menge/ 0 fallen");
								break;
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					//executed if option "Maximalen Lagerraumplatz verändern" is chosen
					case 4:
						try {
							returnvalue = ui.menu(4, room, quantity, drink);
							switch (returnvalue) {
							case -1:
								status.setVisible(true);
								status.setText("Der returnvalue wurde nicht gesetzt");
								break;
							case 0:
								status.setVisible(true);
								status.setText("Es ist ein unbekannter Fehler aufgetreten");
								break;
							case 1:
								status.setVisible(true);
								status.setText("Die Operation wurde erfolgreich durchgeführt");
								break;
							case 2:
								status.setVisible(true);
								status.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug da");
								break;
							case 3:
								status.setVisible(true);
								status.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug Platz da");
								break;
							case 4:
								status.setVisible(true);
								status.setText(
										"Bitte wählen sie eine kleinere Menge, \nder Lagerplatz würde unter die aktuelle Menge/ 0 fallen");
								break;
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					//executed if option "Bestellliste für Lieferanten erstellen" is chosen
					case 5:
						try {
							returnvalue = ui.menu(5, room, quantity, drink);
							switch (returnvalue) {
							case -1:
								status.setVisible(true);
								status.setText("Der returnvalue wurde nicht gesetzt");
								break;
							case 0:
								status.setVisible(true);
								status.setText("Es ist ein unbekannter Fehler aufgetreten");
								break;
							case 1:
								status.setVisible(true);
								status.setText("Die Operation wurde erfolgreich durchgeführt");
								break;
							case 2:
								status.setVisible(true);
								status.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug da");
								break;
							case 3:
								status.setVisible(true);
								status.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug Platz da");
								break;
							case 4:
								status.setVisible(true);
								status.setText(
										"Bitte wählen sie eine kleinere Menge, \nder Lagerplatz würde unter die aktuelle Menge/ 0 fallen");
								break;
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					//executed if option "Inventarliste anzeigen lassen" is chosen
					case 6:
						try {
							returnvalue = ui.menu(6, room, quantity, drink);
							switch (returnvalue) {
							case -1:
								status.setVisible(true);
								status.setText("Der returnvalue wurde nicht gesetzt");
								break;
							case 0:
								status.setVisible(true);
								status.setText("Es ist ein unbekannter Fehler aufgetreten");
								break;
							case 1:
								status.setVisible(true);
								status.setText("Die Operation wurde erfolgreich durchgeführt");
								break;
							case 2:
								status.setVisible(true);
								status.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug da");
								break;
							case 3:
								status.setVisible(true);
								status.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug Platz da");
								break;
							case 4:
								status.setVisible(true);
								status.setText(
										"Bitte wählen sie eine kleinere Menge, \nder Lagerplatz würde unter die aktuelle Menge/ 0 fallen");
								break;
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					//executed if option "Programm beenden" is chosen
					case 7:
						try {
							ui.menu(7, room, quantity, drink);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					}
				} catch (NumberFormatException nfe) {
					status.setVisible(true);
					status.setText("Bitte geben sie eine valide Ganzzahl ein");
				}
			}
		});

		//the menu button for "Getränke kaufen"
		Button option1 = new Button("Getränke kaufen");
		grid.add(option1, 0, 1);
		option1.setMinHeight(25);
		option1.setMinWidth(230);
		option1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Option 1 wird ausgeführt...");
				chosenoption = 1;
				questiondrink.setVisible(true);
				answerdrink.setVisible(true);
				questionquantity.setVisible(true);
				answerquantity.setVisible(true);
				hbEnter.setVisible(true);
				status.setVisible(false);
				table.setVisible(false);
				hbfile.setVisible(false);
				orderLabel.setVisible(false);
				optiontitle.setText(option1.getText());
			}
		});

		//the menu button for "Verkaufsraum auffüllen"
		Button option2 = new Button("Verkaufsraum auffüllen");
		grid.add(option2, 0, 2);
		option2.setMinHeight(25);
		option2.setMinWidth(230);
		option2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Option 2 wird ausgeführt...");
				chosenoption = 2;
				questionroom.setVisible(true);
				answerroom.setVisible(true);
				hbEnter.setVisible(true);
				status.setVisible(false);
				table.setVisible(false);
				hbfile.setVisible(false);
				orderLabel.setVisible(false);
				optiontitle.setText(option2.getText());
			}
		});

		//the menu button for "Aktuellen Lagerraumbestand verändern"
		Button option3 = new Button("Aktuellen Lagerraumbestand verändern");
		grid.add(option3, 0, 3);
		option3.setMinHeight(25);
		option3.setMinWidth(230);
		option3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Option 3 wird ausgeführt...");
				chosenoption = 3;
				questiondrink.setVisible(true);
				answerdrink.setVisible(true);
				questionquantity.setVisible(true);
				answerquantity.setVisible(true);
				questionroom.setVisible(true);
				answerroom.setVisible(true);
				hbEnter.setVisible(true);
				status.setVisible(false);
				table.setVisible(false);
				hbfile.setVisible(false);
				orderLabel.setVisible(false);
				optiontitle.setText(option3.getText());
			}
		});

		//the menu button for "Maximalen Lagerraumplatz verändern"
		Button option4 = new Button("Maximalen Lagerraumplatz verändern");
		grid.add(option4, 0, 4);
		option4.setMinHeight(25);
		option4.setMinWidth(230);
		option4.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Option 4 wird ausgeführt...");
				chosenoption = 4;
				questiondrink.setVisible(true);
				answerdrink.setVisible(true);
				questionquantity.setVisible(true);
				answerquantity.setVisible(true);
				questionroom.setVisible(true);
				answerroom.setVisible(true);
				hbEnter.setVisible(true);
				status.setVisible(false);
				table.setVisible(false);
				hbfile.setVisible(false);
				orderLabel.setVisible(false);
				optiontitle.setText(option4.getText());
			}
		});

		//the menu button for Bestellliste für Lieferanten erstellen"
		Button option5 = new Button("Bestellliste für Lieferanten erstellen");
		grid.add(option5, 0, 5);
		option5.setMinHeight(25);
		option5.setMinWidth(230);
		option5.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Option 5 wird ausgeführt...");
				chosenoption = 5;
				questiondrink.setVisible(false);
				answerdrink.setVisible(false);
				questionquantity.setVisible(false);
				answerquantity.setVisible(false);
				questionroom.setVisible(false);
				answerroom.setVisible(false);
				hbEnter.setVisible(false);
				status.setVisible(false);
				table.setVisible(false);
				hbfile.setVisible(true);
				orderLabel.setVisible(true);
				int[] missing = { 0, 0, 0, 0, 0, 0 };
				try {
					missing = ui.getOrderList(1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				orderLabel.setText("Insgesamt werden noch " + missing[0] + " Kästen Wasser (still) benötigt.\n"
						+ "Insgesamt werden noch " + missing[1] + " Kästen Wasser (mit Kohlensäure) benötigt.\n"
						+ "Insgesamt werden noch " + missing[2] + " Kästen Apfelsaft benötigt.\n"
						+ "Insgesamt werden noch " + missing[3] + " Kästen Orangensaft benötigt. \n"
						+ "Insgesamt werden noch " + missing[4] + " Kästen Limonade benötigt. \n"
						+ "Insgesamt werden noch " + missing[5] + " Kästen Bier benötigt.");
				optiontitle.setText(option5.getText());
			}
		});

		//the menu button for "Inventarliste anzeigen lassen"
		Button option6 = new Button("Inventarliste anzeigen lassen");
		grid.add(option6, 0, 6);
		option6.setMinHeight(25);
		option6.setMinWidth(230);
		option6.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Option 6 wird ausgeführt...");
				chosenoption = 6;
				table.setVisible(true);
				questiondrink.setVisible(false);
				answerdrink.setVisible(false);
				questionquantity.setVisible(false);
				answerquantity.setVisible(false);
				questionroom.setVisible(false);
				answerroom.setVisible(false);
				hbEnter.setVisible(false);
				hbfile.setVisible(false);
				orderLabel.setVisible(false);
				int[][] inventory = ui.getInventoryList();

				ObservableList<tabledata> data = FXCollections.observableArrayList();

				for (int i = 0; i < 6; i++) {
					data.add(new tabledata(i, inventory[0][i], inventory[1][i], inventory[2][i]));
				}

				table.setItems(data);

				status.setVisible(false);
				optiontitle.setText(option6.getText());
			}
		});

		//the menu button for "Programm beenden"
		Button option7 = new Button("Programm beenden");
		grid.add(option7, 0, 7);
		option7.setMinHeight(25);
		option7.setMinWidth(230);
		option7.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Option 7 wird ausgeführt...");
				ui.endProgramm();
			}
		});

		//line for separating the left from the right side
		Line line1 = new Line(20, 20, 20, 385);
		grid.add(line1, 1, 0, 1, 8);
		line1.getStrokeDashArray().addAll(25d, 10d);


		Scene scene = new Scene(grid, 800, 400);
		primaryStage.setScene(scene);

		primaryStage.show();
	}
}
