// OnlineDisplayEM		Author: AMH ELijah Muzzi
// 
// A graphics application that visualizes month-by-month average on-time-percentages
// for public transportation in the Pittsburgh area, with the ability to view subsets
// of the data based on transport type, day type, home garage, and year.
//
// Data Source: https://data.wprdc.org/dataset/port-authority-monthly-average-on-time-performance-by-route
// Note that this application works on slightly edited versions of this data
// that have undergone data cleansing.

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.Scanner;
import java.io.*;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

/*
set PATH_TO_FX="C:\Users\emuzz\javafx\javafx-sdk-18.0.1\lib"
javac --module-path %PATH_TO_FX% --add-modules javafx.controls OntimeDisplayEM.java
java --module-path %PATH_TO_FX% --add-modules javafx.controls OntimeDisplayEM
*/

public class OntimeDisplayEM extends Application {
	// filename in current directory to use as data source
	//private final String filename = "ontimeSmall1.csv";
	 //private final String filename = "ontimeSmall2.csv";
//	private final String filename = "ontimeSmall3.csv";
	//private final String filename = "ontimeMedium1.csv";
	//private final String filename = "ontimeMedium2.csv";
	private final String filename = "ontimeLarge.csv";
	
	private final int PLOTSIZE = 400;	// width/height of square plot area
	private final int BORDER = 50;	// border width on all sides of plot area

	private Group root;		// parent Group to hold all graphics elements

	private RouteItemEM[] routeArray;	// per-route data points
	private MonthEM[] months;			// collection of per-month summaries
	
	private Label showing;		// indicates what portion of the data is currently shown
	private Label yearLabel;	// label for year input text field
	private TextField pickYear;	// field for reading desired year to display
	
	// set up Graphics application with all needed initialization for program
    public void start(Stage primaryStage) {		

		//open the file with a Scanner and count the number of data items
		int rows = 0;			// number of rows in filename 
		try {
			Scanner data = new Scanner(new File(filename));
			data.useDelimiter(",|\n");
				while (data.hasNextLine()) {	// adds 1 to row for each row in filename
					rows += 1;
					data.nextLine();
				}
			
		
		//instantiate the routeData array to hold the needed amount of data
		routeArray = new RouteItemEM[(rows-1)];

		// TODO: open the file a second time with a Scanner, this time reading
		// all elements of each data item and using it to instantiate each
		// RouteItemAMH in the routeData array
		// note that if you use the following two lines to instantiate your
		// Scanner and then set its delimiters to be either , or \n you can
		// use filescan.next() to read a token where , now separates tokens
		// rather than whitespace
		
		// Match excel collumn names
		String A;
		String B;
		String C;
		String D;
		String E;
		String F;
		String month;
		String year;
		int F1;
		int F2;
		int G;
		double H;
		String I;
		

		Scanner objectCreation = new Scanner(new File(filename));		// scanner for creating routeEM objects
		objectCreation.useDelimiter(",|\n");
		objectCreation.nextLine();
			for (int i =0; i < (routeArray.length); i++){
				if (objectCreation.hasNext()) {
					A = objectCreation.next();
					B = objectCreation.next();
					C = objectCreation.next();
					D = objectCreation.next();
					E = objectCreation.next();
					F = objectCreation.next();
					int monthIndex = F.indexOf("/");
					month = F.substring(0,monthIndex);
					F1 = Integer.parseInt(month);
					year = F.substring((F.length() - 2),F.length());
					F2 = Integer.parseInt(year);
					G = objectCreation.nextInt();
					H = objectCreation.nextDouble();
					I = objectCreation.nextLine();
					String I2 = I.substring(1,I.length());
					routeArray[i] = new RouteItemEM(A,B,C,D,E,F1,F2,H,I2);
				} else {
					objectCreation.next();
				}
		}
		int counter =0;					// counts how many names the routeArray name is not equal to
		int unique = 0;					// counts the number of unique names added to list
		String[] uniqueNames = new String[routeArray.length];		// placeholder array of unique names
			for (int i =0; i < routeArray.length; i++) {
					counter = 0;
				for (int j = 0; j < uniqueNames.length; j++) {
					if (!routeArray[i].getGarage().equalsIgnoreCase(uniqueNames[j])) {
						counter++;
					} 
					if (counter == (uniqueNames.length)){				// if it is not equal to everything in the routeArray add to uniqueName
						uniqueNames[unique] = routeArray[i].getGarage();
						unique++;	
					}	
				}		
			}
		String[] garage = new String[(unique)];
		garage[0] = "All";												// add the first all option
			for (int i =0; i < garage.length-1; i++) {
				if(uniqueNames[i] != null){								// only adds to list if not null
				garage[i+1] = uniqueNames[i];							// add unique name to dropdown list
			}		
		}
		// create a Rectangle to show the boundaries of the area for the bar chart
		Rectangle plot = new Rectangle(BORDER, BORDER, PLOTSIZE, PLOTSIZE);
		plot.setFill(Color.WHITE);
		plot.setStroke(Color.BLACK);

		// place the label for what subset of data is showing
		showing = new Label("All data");
		showing.setTranslateX(200);
		showing.setTranslateY(BORDER+PLOTSIZE+30);

		// set up the months array to be initialized for one item per month
		// of the year, calling the constructor with the needed information to
		// place and update the corresponding bar in the chart
		int barWidth = PLOTSIZE/12;
		months = new MonthEM[12];
		months[0] = new MonthEM("Jan", PLOTSIZE, BORDER, BORDER);
		months[1] = new MonthEM("Feb", PLOTSIZE, BORDER, BORDER+barWidth);
		months[2] = new MonthEM("Mar", PLOTSIZE, BORDER, BORDER+barWidth*2);
		months[3] = new MonthEM("Apr", PLOTSIZE, BORDER, BORDER+barWidth*3);
		months[4] = new MonthEM("May", PLOTSIZE, BORDER, BORDER+barWidth*4);
		months[5] = new MonthEM("June", PLOTSIZE, BORDER, BORDER+barWidth*5);
		months[6] = new MonthEM("July", PLOTSIZE, BORDER, BORDER+barWidth*6);
		months[7] = new MonthEM("Aug", PLOTSIZE, BORDER, BORDER+barWidth*7);
		months[8] = new MonthEM("Sept", PLOTSIZE, BORDER, BORDER+barWidth*8);
		months[9] = new MonthEM("Oct", PLOTSIZE, BORDER, BORDER+barWidth*9);
		months[10] = new MonthEM("Nov", PLOTSIZE, BORDER, BORDER+barWidth*10);
		months[11] = new MonthEM("Dec", PLOTSIZE, BORDER, BORDER+barWidth*11);

		// call helper method to scan data in array and update months
		
	
		
		countAll();

		// add the Rectangle from each Month object to the plot along with a
		// Label set to the name of the month
		Group plotBars = new Group();
		for (int i=0; i<months.length; i++) {
			plotBars.getChildren().add(months[i].getBar());
			Label l = new Label(months[i].getName());
			l.setTranslateX(BORDER+barWidth*i+5);
			l.setTranslateY(BORDER+PLOTSIZE+10);
			plotBars.getChildren().add(l);
		}

		// create a drop-down menu for the mode of transportation with two
		// options, Bus or Light Rail, and an All option to set the view back
		// to all of the data
		MenuButton modeChoice = new MenuButton("Mode");
		modeChoice.setTranslateX(15);
		modeChoice.setTranslateY(5);
		String[] modes = {"All", "Bus", "Light Rail"};
		for (int i=0; i<modes.length; i++) {
			MenuItem newItem = new MenuItem(modes[i]);
			modeChoice.getItems().add(newItem);
			newItem.setOnAction(this::modeAction);
		}

		// create a drop-down menu for the day type and place it in the window
		MenuButton typeChoice = new MenuButton("Day Type");
		typeChoice.setTranslateX(100);
		typeChoice.setTranslateY(5);
		String[] type = {"All","Weekday", "Sat.", "Sun."};
		for (int i=0; i<type.length; i++) {
			MenuItem newItem = new MenuItem(type[i]);
			typeChoice.getItems().add(newItem);
			newItem.setOnAction(this::typeAction);
		}
		// TODO: add menu items and set action to execute upon selection

		// create a drop-down menu for the home garage and place it in the window
		MenuButton garageChoice = new MenuButton("Garage");
		garageChoice.setTranslateX(200);
		garageChoice.setTranslateY(5);
		for (int i=0; i<garage.length; i++) {
			MenuItem newItem = new MenuItem(garage[i]);
			garageChoice.getItems().add(newItem);
			newItem.setOnAction(this::garageAction);
		}

		// TODO: add menu items and set action to execute upon selection

		// create label and text field for reading in a year
		Label yearLabel = new Label("Year:");
		yearLabel.setTranslateX(280);
		yearLabel.setTranslateY(8);
		pickYear = new TextField();
		pickYear.setPrefColumnCount(5);
		pickYear.setTranslateX(310);
		pickYear.setTranslateY(5);
		pickYear.setOnAction(this::yearAction);
	
		// create a button for reseting visualization to show entire dataset
		Button reset = new Button("Reset");
		reset.setTranslateX(400);
		reset.setTranslateY(5);
		reset.setOnAction(this::resetAction);		

		// build group containing all graphics elements and finish creating
		// the scene and the stage
		
		
 		root = new Group(plot, plotBars, modeChoice, typeChoice, garageChoice,
						yearLabel, pickYear, reset, showing);
		
        Scene scene = new Scene(root, PLOTSIZE+BORDER*2, PLOTSIZE+BORDER*2,
								Color.WHITE);
		
        primaryStage.setTitle("On-Time Percentage Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
		}  catch (IOException e) {System.out.println(e);};
    }
	// helper method to go through the routeData array and for each item
	// update the proper month with the on-time percentage for that item
	public void countAll() {
		for (int i = 0; i < routeArray.length; i++){
				int current_month = routeArray[i].getMonth();
				 MonthEM data_month = months[(current_month)-1];
				data_month.addItem(routeArray[i].getOnTime());		
		}
		
		// TODO: add code to iterate through routeData and update months
		showing.setText("All data");
	}
	// event handler for menu items in the mode drop-down menu
	public void modeAction(ActionEvent event) {
		// choice stores which menu item was selected
		String choice = ((MenuItem)(event.getSource())).getText();
		reset();
		for (int i = 0; i < routeArray.length; i++){
			if (routeArray[i].getMode().equals(choice)) {
				months[(routeArray[i].getMonth()-1)].addItem(routeArray[i].getOnTime());
			} 
			if (choice.equals("All")) {
				reset();
				countAll();		
			}
		}
	}	
	public void garageAction(ActionEvent event) {
		// choice stores which menu item was selected
		String choice = ((MenuItem)(event.getSource())).getText();
		System.out.println(choice);
		for (int i = 0; i < routeArray.length; i++){
			if (routeArray[i].getGarage().equalsIgnoreCase(choice)) {
				months[(routeArray[i].getMonth()-1)].addItem(routeArray[i].getOnTime());
			} 
			if (choice.equals("All")) {
				reset();
				countAll();		
			}
		}
	}
	public void typeAction(ActionEvent event) {
		// choice stores which menu item was selected
		String choice = ((MenuItem)(event.getSource())).getText();
		reset();
		for (int i = 0; i < routeArray.length; i++){
			if (routeArray[i].getDayType().equalsIgnoreCase(choice)) {
				months[(routeArray[i].getMonth()-1)].addItem(routeArray[i].getOnTime());
			}
			if (choice.equals("All")) {
				reset();
				countAll();		
			}
		}
		System.out.println(choice);	
	}
	// event handler for text field for entering a year to view
	public void yearAction(ActionEvent event) {
		String choice = pickYear.getText();
		if (choice.length() == 4) {
		choice = choice.substring(2,choice.length());
		reset();
		for (int i = 0; i < routeArray.length; i++){
			if (routeArray[i].getYear() == Integer.parseInt(choice)) {
				months[(routeArray[i].getMonth()-1)].addItem(routeArray[i].getOnTime());
			} 
		}
		} else {
			System.out.println("Please enter a valid 20XX year:");
			pickYear.setText("");
		}
		pickYear.setText("");
	}
	
	// event handler for the reset button
	public void resetAction(ActionEvent event) {
		reset();
		countAll();
	}
	public void reset() {
		for (int i = 0; i < months.length; i++) {
			months[i].reset();
		}
	}
	
    public static void main(String[] args)
    {
        launch(args);
    }
}
