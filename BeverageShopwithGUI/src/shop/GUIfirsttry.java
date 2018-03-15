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
//TODO Label erstellen, dass sagt, welche Option gerade gewählt wurde (wie Titel "Menüoptionen")

public class GUIfirsttry extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public GUIfirsttry() throws IOException {
		super();
	}

	UserInteraction ui = new UserInteraction();
	int chosenoption = 0;
	int room = 0;
	int drink = 0;
	int quantity = 0;

	@Override
	public void start(Stage primaryStage) {
		// to memorize the chosen options

		primaryStage.setTitle("JavaFX Titel");

		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new Insets(20, 20, 20, 20));
		
		//title of the menu option
		Text scenetitle = new Text("Menüoptionen");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		//displays chosen option
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

		//labels for Orderlist
		Label orderLabel = new Label();
		orderLabel.setPadding(new Insets(0,0,0,50));
		grid.add(orderLabel, 2, 1, 2, 4);
		orderLabel.setVisible(false);
		
		// label for showing if it went right or wrong
		Label error = new Label();
		grid.add(error, 2, 4);
		error.setVisible(false);

		//table for displaying inventorylist
		TableView table = new TableView();
		table.setEditable(true);
		//TODo change width of Getränk
		TableColumn firstcolumn = new TableColumn("Getränk");
		TableColumn secondcolumn = new TableColumn("Verkaufsraum");
		TableColumn thirdcolumn = new TableColumn("Lagerraum 1");
		TableColumn forthcolumn = new TableColumn("Lagerraum 2");
		firstcolumn.setCellValueFactory(new PropertyValueFactory<>("drink"));
		secondcolumn.setCellValueFactory(new PropertyValueFactory<>("showr"));
		thirdcolumn.setCellValueFactory(new PropertyValueFactory<>("storer1"));
		forthcolumn.setCellValueFactory(new PropertyValueFactory<>("storer2"));
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.getColumns().addAll(firstcolumn, secondcolumn, thirdcolumn, forthcolumn);
		grid.add(table, 2, 1, 2, 4);
		table.setVisible(false);
		
		//button for file output (just for Orderlist)
		Button fileoutput = new Button("Dateiausgabe");
		HBox hbfile = new HBox(10);
		hbfile.setAlignment(Pos.BOTTOM_RIGHT);
		hbfile.getChildren().add(fileoutput);
		grid.add(hbfile, 3, 4);
		hbfile.setVisible(false);
		fileoutput.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				try {
					ui.getOrderList(2);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// button for starting the UserInteraction
		Button enter = new Button("Bestätigen");
		HBox hbEnter = new HBox(10);
		hbEnter.setAlignment(Pos.BOTTOM_RIGHT);
		hbEnter.getChildren().add(enter);
		grid.add(hbEnter, 3, 4);
		hbEnter.setVisible(false);
		enter.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				int returnvalue;
				//converts the chosen room into the number for the drink
				switch (answerroom.getSelectionModel().getSelectedItem().toString()) {
				    case "Lagerraum 1":
				    	room = 1;
				    	break;
				    case "Lagerraum 2":
				    	room = 2;
				    	break;
				}
				//converts the chosen drink into the number for the drink
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
				//sets the value for quantity (unless it is not an integer)
				try {
					quantity = Integer.parseInt(answerquantity.getText());
				}
				catch(NumberFormatException nfe) {
					error.setVisible(true);
					error.setText("Bitte geben sie eine valide Ganzzahl ein");
				}
				//calls the menu method in UserInteraction with the needed values
				switch (chosenoption) {
				// TODO überall die Returnwerte verarbeiten
				case 1:
					try {
						returnvalue = ui.menu(1, room, quantity, drink);
						switch (returnvalue) {
						case -1:
							error.setVisible(true);
							error.setText("Der returnvalue wurde nicht gesetzt");
							break;
						case 0:
							error.setVisible(true);
							error.setText("Es ist ein unbekannter Fehler aufgetreten");
							break;
						case 1:
							error.setVisible(true);
							error.setText("Die Operation wurde erfolgreich durchgeführt");
							break;
						case 2:
							error.setVisible(true);
							error.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug da");
							break;
						case 3:
							error.setVisible(true);
							error.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug Platz da");
							break;
						case 4:
							error.setVisible(true);
							error.setText(
									"Bitte wählen sie eine kleinere Menge, \nder Lagerplatz würde unter die aktuelle Menge/ 0 fallen");
							break;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				case 2:
					try {
						returnvalue = ui.menu(2, room, quantity, drink);
						switch (returnvalue) {
						case -1:
							error.setVisible(true);
							error.setText("Der returnvalue wurde nicht gesetzt");
							break;
						case 0:
							error.setVisible(true);
							error.setText("Es ist ein unbekannter Fehler aufgetreten");
							break;
						case 1:
							error.setVisible(true);
							error.setText("Die Operation wurde erfolgreich durchgeführt");
							break;
						case 2:
							error.setVisible(true);
							error.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug da");
							break;
						case 3:
							error.setVisible(true);
							error.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug Platz da");
							break;
						case 4:
							error.setVisible(true);
							error.setText(
									"Bitte wählen sie eine kleinere Menge, \nder Lagerplatz würde unter die aktuelle Menge/ 0 fallen");
							break;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				case 3:
					try {
						returnvalue = ui.menu(3, room, quantity, drink);
						switch (returnvalue) {
						case -1:
							error.setVisible(true);
							error.setText("Der returnvalue wurde nicht gesetzt");
							break;
						case 0:
							error.setVisible(true);
							error.setText("Es ist ein unbekannter Fehler aufgetreten");
							break;
						case 1:
							error.setVisible(true);
							error.setText("Die Operation wurde erfolgreich durchgeführt");
							break;
						case 2:
							error.setVisible(true);
							error.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug da");
							break;
						case 3:
							error.setVisible(true);
							error.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug Platz da");
							break;
						case 4:
							error.setVisible(true);
							error.setText(
									"Bitte wählen sie eine kleinere Menge, \nder Lagerplatz würde unter die aktuelle Menge/ 0 fallen");
							break;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				case 4:
					try {
						returnvalue = ui.menu(4, room, quantity, drink);
						switch (returnvalue) {
						case -1:
							error.setVisible(true);
							error.setText("Der returnvalue wurde nicht gesetzt");
							break;
						case 0:
							error.setVisible(true);
							error.setText("Es ist ein unbekannter Fehler aufgetreten");
							break;
						case 1:
							error.setVisible(true);
							error.setText("Die Operation wurde erfolgreich durchgeführt");
							break;
						case 2:
							error.setVisible(true);
							error.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug da");
							break;
						case 3:
							error.setVisible(true);
							error.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug Platz da");
							break;
						case 4:
							error.setVisible(true);
							error.setText(
									"Bitte wählen sie eine kleinere Menge, \nder Lagerplatz würde unter die aktuelle Menge/ 0 fallen");
							break;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				case 5:
					try {
						returnvalue = ui.menu(5, room, quantity, drink);
						switch (returnvalue) {
						case -1:
							error.setVisible(true);
							error.setText("Der returnvalue wurde nicht gesetzt");
							break;
						case 0:
							error.setVisible(true);
							error.setText("Es ist ein unbekannter Fehler aufgetreten");
							break;
						case 1:
							error.setVisible(true);
							error.setText("Die Operation wurde erfolgreich durchgeführt");
							break;
						case 2:
							error.setVisible(true);
							error.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug da");
							break;
						case 3:
							error.setVisible(true);
							error.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug Platz da");
							break;
						case 4:
							error.setVisible(true);
							error.setText(
									"Bitte wählen sie eine kleinere Menge, \nder Lagerplatz würde unter die aktuelle Menge/ 0 fallen");
							break;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				case 6:
					try {
						returnvalue = ui.menu(6, room, quantity, drink);
						switch (returnvalue) {
						case -1:
							error.setVisible(true);
							error.setText("Der returnvalue wurde nicht gesetzt");
							break;
						case 0:
							error.setVisible(true);
							error.setText("Es ist ein unbekannter Fehler aufgetreten");
							break;
						case 1:
							error.setVisible(true);
							error.setText("Die Operation wurde erfolgreich durchgeführt");
							break;
						case 2:
							error.setVisible(true);
							error.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug da");
							break;
						case 3:
							error.setVisible(true);
							error.setText("Bitte wählen sie eine kleinere Menge, \nes ist nicht genug Platz da");
							break;
						case 4:
							error.setVisible(true);
							error.setText(
									"Bitte wählen sie eine kleinere Menge, \nder Lagerplatz würde unter die aktuelle Menge/ 0 fallen");
							break;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				case 7:
					try {
						ui.menu(7, room, quantity, drink);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				}
			}
		});

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
				error.setVisible(false);
				table.setVisible(false);
				hbfile.setVisible(false);
				orderLabel.setVisible(false);
				optiontitle.setText(option1.getText());
			}
		});

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
				error.setVisible(false);
				table.setVisible(false);
				hbfile.setVisible(false);
				orderLabel.setVisible(false);
				optiontitle.setText(option2.getText());
			}
		});

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
				error.setVisible(false);
				table.setVisible(false);
				hbfile.setVisible(false);
				orderLabel.setVisible(false);
				optiontitle.setText(option3.getText());
			}
		});

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
				error.setVisible(false);
				table.setVisible(false);
				hbfile.setVisible(false);
				orderLabel.setVisible(false);
				optiontitle.setText(option4.getText());
			}
		});

		Button option5 = new Button("Bestellliste für Lieferanten erstellen");
		grid.add(option5, 0, 5);
		option5.setMinHeight(25);
		option5.setMinWidth(230);
		option5.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Option 5 wird ausgeführt...");
				chosenoption = 5;
				hbEnter.setVisible(false);
				error.setVisible(false);
				table.setVisible(false);
				hbfile.setVisible(true);
				orderLabel.setVisible(true);
				int[] missing = {0,0,0,0,0,0};
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
				
				for(int i = 0; i < 6; i++) {
					data.add(new tabledata(i, inventory[0][i],inventory[1][i],inventory[2][i]));
				}
				
				
				table.setItems(data);
				
				error.setVisible(false);
				optiontitle.setText(option6.getText());
			}
		});

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

		Line line1 = new Line(20, 20, 20, 350);
		grid.add(line1, 1, 1, 1, 7);

		// grid.setGridLinesVisible(true);

		Scene scene = new Scene(grid, 800, 400);
		primaryStage.setScene(scene);

		primaryStage.show();
	}
}
