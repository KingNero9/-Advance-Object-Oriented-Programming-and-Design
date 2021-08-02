package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View {
	
	private BorderPane bp;
	private Button add, delete, find, print, select, fileButton, sendMsg, printAllApproved; 
	private VBox vbCenter;
	private HBox hbButton, hbDownButton, hbTitle, hbToggle;
	private ToggleGroup tg;
	private RadioButton insert, abc, zyx;
	private Pane titlePane, Cpane;
	private Text title, message, profitText;
	private Stage stage;
	private boolean orderSelected = false;
	private int profit=0;

	private static int selectedButton = 1;
	private static boolean firstTime = true;

	
	public View(Stage stage1) {

		
		stage = stage1;
		stage.setResizable(false);
		
		//error or success text
		message = new Text("");
		
		//Profit 
		profitText = new Text("Total Profit: "+ profit);
		


		//the sort order
		insert = new RadioButton("Insertion Order");
		abc = new RadioButton("a,b,c,... Order");
		zyx = new RadioButton("z,y,x,... Order");

		tg = new ToggleGroup();
		insert.setToggleGroup(tg);
		abc.setToggleGroup(tg);
		zyx.setToggleGroup(tg);
		hbToggle = new HBox();
		hbToggle.setSpacing(10);
		hbToggle.getChildren().addAll(insert, abc, zyx);
		
		select = new Button("Select order");
		
		//Title
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		ds.setColor(Color.DARKBLUE);
		title = new Text("Store");
		title.setFont(Font.font("Verdana", 30));
		title.setEffect(ds);
		titlePane = new Pane();
		titlePane.setMinWidth(275);
		hbTitle = new HBox();
		hbTitle.getChildren().addAll(titlePane, title);


		//Center 

		sendMsg = new Button("message to Members");
		add = new Button("Add Product");
		delete = new Button("Delete last Product");
		find = new Button("Find Product");
		print = new Button("Show All Products");
		printAllApproved = new Button("Print All who Approved");
		
		hbButton = new HBox();
		hbButton.getChildren().addAll(add,delete, sendMsg);
		hbButton.setSpacing(30);
		
		hbDownButton = new HBox();
		hbDownButton.getChildren().addAll(find,print, printAllApproved);
		hbDownButton.setSpacing(30);
		
		
		fileButton = new Button("File Functions");
		fileButton.setLayoutX(60);
		fileButton.setLayoutY(50);
		vbCenter = new VBox();
		vbCenter.getChildren().addAll(hbButton,hbDownButton , hbToggle, select,profitText, message);
		vbCenter.setLayoutX(-68);
		vbCenter.setLayoutY(100);
		vbCenter.setSpacing(40);
		Cpane = new Pane();
		Cpane.getChildren().addAll(fileButton, vbCenter);
		

		//connection everything
		bp = new BorderPane();
		bp.setTop(hbTitle);
		bp.setCenter(Cpane);
		BorderPane.setMargin(bp.getCenter(), new Insets(0, 0,0,210));
		int width = 640, height = 480;
		Scene scene = new Scene(bp,width,height);
		stage.setScene(scene);
		
		if(!firstTime)
		{
			hbToggle.setDisable(true);
			select.setDisable(true);
			orderSelected = true;
			setSelectedButton();
			//AddProductSuccess();
		}
		else
			insert.setSelected(true);
		stage.show();
		firstTime = false;
	}

	//SHOW AND HIDE FUNCTIONS
	
	public void close() {
		stage.hide();
		vbCenter.getChildren().remove(message);

	}
	
	public void open() {
		stage.show();

	}
	
	
	// BUTTONS HANDLER
	
	public void addEventHandlerToAddProduct(EventHandler<ActionEvent> event) { //Add button
		add.setOnAction(event);
	}
	
	public void addEventHandlerToDeleteProduct(EventHandler<ActionEvent> event) { //delete button
		delete.setOnAction(event);
	}
	
	public void addEventHandlerToSelect(EventHandler<ActionEvent> event) { //select button
		select.setOnAction(event);
	}
	
	public void addEventHandlerToPrintProduct(EventHandler<ActionEvent> event) { //print button
		print.setOnAction(event);
	}
	
	public void addEventHandlerToFindProduct(EventHandler<ActionEvent> event) { //find button
		find.setOnAction(event);
	}
	
	public void addEventHandlerToFileButton(EventHandler<ActionEvent> event) { //FILE button
		fileButton.setOnAction(event);
	}
	
	public void addEventHandlerToSendButton(EventHandler<ActionEvent> event) { //send button
		sendMsg.setOnAction(event);
	}
	
	public void addEventHandlerToPrintAllApprovedButton(EventHandler<ActionEvent> event) { //Print Approved button
		printAllApproved.setOnAction(event);
	}
	
	//ERRORS AND SUCCESS 
	 
	public void deleteSuccess()
	{
		vbCenter.getChildren().remove(message);
		message.setText("Deleted Successfully");
		message.setFill(Color.GREEN);
		vbCenter.getChildren().add(message);
	}
	
	public void AddProductSuccess()
	{
		vbCenter.getChildren().remove(message);
		message.setText("Product Added Successfully");
		message.setFill(Color.GREEN);
		vbCenter.getChildren().add(message);
	}
	
	public void deleteFailure()
	{
		vbCenter.getChildren().remove(message);
		message.setText("Delete Failed");
		message.setFill(Color.RED);
		vbCenter.getChildren().add(message);
	}
	
	public void cantAddYet() {
		vbCenter.getChildren().remove(message);
		message.setText("Can't add a product before you choose a sorting order");
		message.setFill(Color.RED);
		vbCenter.getChildren().add(message);
	}
	
	public void noDataError() {
		vbCenter.getChildren().remove(message);
		message.setText("There are no Products for that action");
		message.setFill(Color.RED);
		vbCenter.getChildren().add(message);
	}
	
	public void errorSavingToFile() {
		vbCenter.getChildren().remove(message);
		message.setFill(Color.RED);
		message.setText("Couldn't save to File, unexpected error");
		vbCenter.getChildren().add(message);
	}
	
	public void errorWithTheFile() {
		vbCenter.getChildren().remove(message);
		message.setFill(Color.RED);
		message.setText("Couldn't work with the File, unexpected error");
		vbCenter.getChildren().add(message);
	}
	
	public void successSendMessageToMemebers() {
		vbCenter.getChildren().remove(message);
		message.setFill(Color.GREEN);
		message.setText("Message Sent !");
		vbCenter.getChildren().add(message);
	}
	
	public void failedToSendMessageToMemebers() {
		vbCenter.getChildren().remove(message);
		message.setFill(Color.RED);
		message.setText("No members can't send a message !");
		vbCenter.getChildren().add(message);
	}
	
	public void failedNoConfirmedMemebers() {
		vbCenter.getChildren().remove(message);
		message.setFill(Color.RED);
		message.setText("No Confirmed members can't send a message !");
		vbCenter.getChildren().add(message);
	}

	//GET AND SETS!
	
	public int getSortingOrder() {
		if(insert.isSelected()) {
			selectedButton = 1;
			return 1;
		}
		else if(abc.isSelected())
		{
			selectedButton =2 ;
			return 2;
		}
		else if(zyx.isSelected())
		{
			selectedButton=3;
			return 3;
		}
		return 1;
	}
	
	private void setSelectedButton()// makes sure the right button will be chosen when window is opening back up
	{
		switch(selectedButton) { 
		case 1:
			insert.setSelected(true);
			return;
		case 2:
			abc.setSelected(true);
			return;
		case 3:
			zyx.setSelected(true);
			return;
		}
	}
	
	public void setSelectedButtonFromFile(int order)// makes sure the right button will be chosen when window is opening back up
	{
		switch(order) { 
		case 1:
			insert.setSelected(true);
			return;
		case 2:
			abc.setSelected(true);
			return;
		case 3:
			zyx.setSelected(true);
			return;
		}
	}
	
	public void setProfit(int n) {
		profit = n;
		profitText.setText("Total Profit: "+ profit);
	}
	
	public void disableSelect() {
		hbToggle.setDisable(true);
		select.setDisable(true);
		orderSelected = true; // we will use to check 
	}
	
	public boolean isOrderSelected() {
		return orderSelected;
	}
}
