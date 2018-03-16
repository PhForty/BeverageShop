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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The GUI displays the programm with its options, takes the user input and
 * processes it, by giving it to UserInteraction, in an specified format.
 * Finally it displays the result again
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
	// these are the values that the UserInteraction-method "menu" gets, when called
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

		primaryStage.setTitle("Getränkemarktverkäuferverwaltungssoftwareprogrammmenü");

		// constructs the grid, that is layed over the GUI (to have a table-like
		// structure)
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(21);
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
		ObservableList<String> optionsdrink = FXCollections.observableArrayList(Room.beverages[0], Room.beverages[1],
				Room.beverages[2], Room.beverages[3], Room.beverages[4], Room.beverages[5]);
		final ComboBox answerdrink = new ComboBox(optionsdrink);
		answerdrink.getSelectionModel().select(Room.beverages[0]);
		grid.add(answerdrink, 3, 2);
		questiondrink.setVisible(false);
		answerdrink.setVisible(false);

		// question and answer field for the storage room
		Label questionroom = new Label("Welchen Lagerraum betrifft \ndiese Aktion?");
		questionroom.setPadding(new Insets(0, 0, 0, 50));
		grid.add(questionroom, 2, 3, 2, 1);
		ObservableList<String> optionsroom = FXCollections.observableArrayList("Lagerraum 1", "Lagerraum 2");
		final ComboBox answerroom = new ComboBox(optionsroom);
		answerroom.getSelectionModel().select("Lagerraum 1");
		grid.add(answerroom, 3, 3);
		questionroom.setVisible(false);
		answerroom.setVisible(false);

		// label for displaying Orderlist
		Label orderLabel = new Label();
		orderLabel.setPadding(new Insets(0, 0, 0, 30));
		grid.add(orderLabel, 2, 1, 3, 4);
		orderLabel.setVisible(false);

		// label for the easter egg
		Label easteregg = new Label("Richtige Antwort! \n(Wie war nochmal die Frage?)");
		grid.add(easteregg, 3, 6, 2, 3);
		easteregg.setPadding(new Insets(0, 0, 0, 0));
		easteregg.setVisible(false);

		// label for displaying the status (if something went wrong for example)
		Label status = new Label();
		grid.add(status, 2, 4, 2, 2);
		status.setFont(new Font(12));
		status.setStyle("-fx-font-weight: bold");
		status.setVisible(false);

		// table for displaying inventorylist
		TableView table = new TableView();
		table.setEditable(true);
		TableColumn firstcolumn = new TableColumn("Getränk");
		firstcolumn.setMinWidth(50);
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
				easteregg.setVisible(false);
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
					if (quantity == 42) {
						easteregg.setVisible(true);
					}
					// calls the menu method in UserInteraction with the needed values
					switch (chosenoption) {
					// executed if option "Getränk kaufen" is chosen
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
					// executed if option "Verkaufsraum auffüllen" is chosen
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
					// executed if option "Aktuellen Lagerraumbestand verändern" is chosen
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
					// executed if option "Maximalen Lagerraumplatz verändern" is chosen
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
					// executed if option "Bestellliste für Lieferanten erstellen" is chosen
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
					// executed if option "Inventarliste anzeigen" is chosen
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
					// executed if option "Programm beenden" is chosen
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

		// picture for changing amount/ max in storage room
		Image imageChange = new Image(getClass().getResourceAsStream("mehr_oder_weniger.png"));

		// the menu button for "Getränke kaufen"
		Image imageBottle = new Image(getClass().getResourceAsStream("Bottle_by_Rones.png"));
		Button option1 = new Button("Getränke verkaufen", new ImageView(imageBottle));
		option1.setContentDisplay(ContentDisplay.LEFT);
		grid.add(option1, 0, 1);
		option1.setMinHeight(25);
		option1.setMinWidth(260);
		option1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				chosenoption = 1;
				easteregg.setVisible(false);
				questiondrink.setVisible(true);
				answerdrink.setVisible(true);
				questionquantity.setVisible(true);
				answerquantity.setVisible(true);
				questionroom.setVisible(false);
				answerroom.setVisible(false);
				hbEnter.setVisible(true);
				status.setVisible(false);
				table.setVisible(false);
				hbfile.setVisible(false);
				orderLabel.setVisible(false);
				optiontitle.setText(option1.getText());
			}
		});

		// the menu button for "Verkaufsraum auffüllen"
		Image imageArrow = new Image(getClass().getResourceAsStream("Pfeil.png"));
		Button option2 = new Button("Verkaufsraum auffüllen", new ImageView(imageArrow));
		option2.setContentDisplay(ContentDisplay.LEFT);
		grid.add(option2, 0, 2);
		option2.setMinHeight(25);
		option2.setMinWidth(260);
		option2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				chosenoption = 2;
				easteregg.setVisible(false);
				questionroom.setVisible(true);
				answerroom.setVisible(true);
				questiondrink.setVisible(false);
				answerdrink.setVisible(false);
				questionquantity.setVisible(false);
				answerquantity.setVisible(false);
				hbEnter.setVisible(true);
				status.setVisible(false);
				table.setVisible(false);
				hbfile.setVisible(false);
				orderLabel.setVisible(false);
				optiontitle.setText(option2.getText());
			}
		});

		// the menu button for "Aktuellen Lagerraumbestand verändern"
		Button option3 = new Button("Aktuellen Lagerraumbestand verändern", new ImageView(imageChange));
		option3.setContentDisplay(ContentDisplay.LEFT);
		grid.add(option3, 0, 3);
		option3.setMinHeight(25);
		option3.setMinWidth(260);
		option3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				chosenoption = 3;
				easteregg.setVisible(false);
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

		// the menu button for "Maximalen Lagerraumplatz verändern"
		Button option4 = new Button("Maximalen Lagerraumplatz verändern", new ImageView(imageChange));
		option4.setContentDisplay(ContentDisplay.LEFT);
		grid.add(option4, 0, 4);
		option4.setMinHeight(25);
		option4.setMinWidth(260);
		option4.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				chosenoption = 4;
				easteregg.setVisible(false);
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

		// the menu button for Bestellliste für Lieferanten erstellen"
		Image imageTruck = new Image(getClass().getResourceAsStream("PixelTruck.png"));
		Button option5 = new Button("Bestellliste für Lieferanten erstellen", new ImageView(imageTruck));
		option5.setContentDisplay(ContentDisplay.LEFT);
		grid.add(option5, 0, 5);
		option5.setMinHeight(25);
		option5.setMinWidth(260);
		option5.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				chosenoption = 5;
				easteregg.setVisible(false);
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

		// the menu button for "Inventarliste anzeigen"
		Image imageBucket = new Image(getClass().getResourceAsStream("bucket-list.png"));
		Button option6 = new Button("Inventarliste anzeigen", new ImageView(imageBucket));
		option6.setContentDisplay(ContentDisplay.LEFT);
		grid.add(option6, 0, 6);
		option6.setMinHeight(25);
		option6.setMinWidth(260);
		option6.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				chosenoption = 6;
				easteregg.setVisible(false);
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

				ObservableList<TableData> data = FXCollections.observableArrayList();

				for (int i = 0; i < 6; i++) {
					data.add(new TableData(i, inventory[0][i], inventory[1][i], inventory[2][i]));
				}

				table.setItems(data);

				status.setVisible(false);
				optiontitle.setText(option6.getText());
			}
		});

		// the menu button for "Programm beenden"
		Image imageStop = new Image(getClass().getResourceAsStream("weißesXAufRotemKreis.png"));
		Button option7 = new Button("Programm beenden", new ImageView(imageStop));
		option7.setContentDisplay(ContentDisplay.LEFT);
		grid.add(option7, 0, 7);
		option7.setMinHeight(25);
		option7.setMinWidth(260);
		option7.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				ui.endProgramm();
			}
		});

		// line for separating the left from the right side
		Line line1 = new Line(20, 20, 20, 385);
		grid.add(line1, 1, 0, 1, 8);
		line1.getStrokeDashArray().addAll(25d, 10d);

		Scene scene = new Scene(grid, 800, 410);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
