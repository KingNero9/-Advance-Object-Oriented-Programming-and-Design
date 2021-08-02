package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

public class AddWindow {
	private TextField id, name, buyPrice, sellPrice;
	private TextField buyerName, buyerPhone, buyerMembership;
	private Text addProductIdError, inputError; // errors
	private Text title, idText, nameText, buyPriceText, sellPriceText, buyerNameText, buyerPhoneText, buyerMemberText;
	private VBox vbCenter, vbText, vbButton, vbErrors;
	private HBox hb;
	private Pane pane;
	private Button add;
	private Stage stage;


	public AddWindow(Stage stage)
	{
		this.stage = stage;
		//errors 
		vbErrors = new VBox();
		addProductIdError = new Text("You must enter an id");
		addProductIdError.setFill(Color.RED);
		inputError = new Text("");
		inputError.setFill(Color.RED);

		DropShadow ds = new DropShadow();
		ds.setRadius(15);
		ds.setColor(Color.DARKBLUE);

		//TextFields
		id = new TextField();
		id.setEffect(ds);
		name = new TextField();
		buyPrice = new TextField();
		sellPrice = new TextField();
		buyerName = new TextField();
		buyerPhone = new TextField();
		buyerMembership = new TextField();
		vbCenter = new VBox();
		vbCenter.getChildren().addAll(id, name, buyPrice, sellPrice, buyerName, buyerPhone, buyerMembership);

		//Texts
		idText = new Text("Product ID: (can't be empty)  ");
		nameText = new Text("Product name: ");
		buyPriceText = new Text("Product Buying Price: ");
		sellPriceText = new Text("Product Selling Price: ");
		buyerNameText = new Text("Product Buyer Name: ");
		buyerPhoneText = new Text("Product Buyer Number: (10 digits) ");
		buyerMemberText = new Text("Product Buyer Member: (Yes / No) ");
		vbText = new VBox();
		vbText.getChildren().addAll(idText, nameText, buyPriceText, sellPriceText, buyerNameText, buyerPhoneText, buyerMemberText);
		vbText.setSpacing(10);

		//connect all
		hb = new HBox();
		hb.getChildren().addAll(vbText, vbCenter);
		pane = new Pane();
		hb.setLayoutX(200);
		hb.setLayoutY(200);
		title = new Text("Product Adder");
		title.setFont(Font.font("Verdana", 30));
		title.setEffect(ds);
		title.setLayoutX(215);
		title.setLayoutY(100);
		add = new Button("Add Product");
		vbButton = new VBox();
		vbButton.setLayoutX(275);
		vbButton.setLayoutY(400);
		vbButton.setSpacing(5);
		vbButton.getChildren().addAll(add,vbErrors);
		pane.getChildren().addAll(title,hb, vbButton);

		int width = 640, height = 480;
		Scene scene = new Scene(pane,width,height);
		stage.setScene(scene);
		stage.show();

	}

	public void close() {
		stage.close();
	}

	public void addEventHandlerToAdd(EventHandler<ActionEvent> event) { //Add button
		add.setOnAction(event);
	}

	public boolean getId()
	{
		String tmp = id.getText();
		if(tmp.isEmpty())
			return false;
		return true;
	}


	public int getSellingPrice(){
		if(sellPrice.getText().isEmpty())
			return 0;
		return Integer.parseInt(sellPrice.getText());
	}


	public boolean getProduct(Model theModel)
	{

		int buyPriceInt=-1, sellPriceInt=-1; // -1 = EMPTY FIELD
		String nameStr, buyerNameStr, buyerPhoneStr="No Phone";
		boolean buyerMembershipBool= false;

		nameStr = name.getText().trim();
		buyerNameStr = buyerName.getText().trim();

		//checking data

		if(!buyPrice.getText().trim().isEmpty()) // Eliminate the null option 
		{
			buyPriceInt = priceCheck(buyPrice);
			if(buyPriceInt == -1)
				return false;
		}
		if(!sellPrice.getText().trim().isEmpty()) // Eliminate the null option 
		{
			sellPriceInt = priceCheck(sellPrice);
			if(sellPriceInt == -1)
				return false;
		}
		if(!buyerPhone.getText().trim().isEmpty()) // check the phone number
		{
			if(phoneNumberCheck(buyerPhone))
				buyerPhoneStr = buyerPhone.getText();
			else
				return false;
		}

		if(!buyerMembership.getText().trim().isEmpty())
		{
			if(buyerMembership.getText().equalsIgnoreCase("yes"))
				buyerMembershipBool = true;
			else if(buyerMembership.getText().equalsIgnoreCase("no"))
				buyerMembershipBool = false;
			else {
				return false;
				
			}
		}
		Customer c = new Customer(buyerNameStr, buyerPhoneStr, buyerMembershipBool);
		Product p = new Product(nameStr,buyPriceInt,sellPriceInt,c);
		theModel.addProduct(id.getText().trim(),p);
		theModel.checkForMember(c);
		return true;
	}

	public int priceCheck(TextField price) {
		int priceInt=-1;
		try {
			priceInt = Integer.parseInt(price.getText().trim());
		}catch(Exception e) {
			buyingPrice_errorNumbers();
			return -1;
		}
		if(priceInt < 0 ) {
			buyingPrice_errorNegative();
			return -1;
		}	
		return priceInt;

	}

	public boolean  phoneNumberCheck(TextField phoneNum)
	{
		String str = phoneNum.getText();
		if(!str.matches("[0-9]+")){
			phoneNumber_errorNumbers();
			return false;
		}

		if(str.length()!=10)
		{
			phoneNumber_errorLength();
			return false; // check for 10 digits exactly
		}
		return true;

	}

	//ERRORS

	public void idError() {
		vbErrors.getChildren().clear(); //will clean all the errors
		vbErrors.getChildren().add(addProductIdError);

	}

	public void buyingPrice_errorNegative()  {
		vbErrors.getChildren().clear(); //will clean all the errors
		inputError.setText("Price must be greater or equal to zero");
		vbErrors.getChildren().add(inputError);
	}

	public void buyingPrice_errorNumbers()  {
		vbErrors.getChildren().clear(); //will clean all the errors
		inputError.setText("Price must contain only numbers!");
		vbErrors.getChildren().add(inputError);
	}

	public void phoneNumber_errorNumbers()  {
		vbErrors.getChildren().clear(); //will clean all the errors
		inputError.setText("Number must contains only numbers!");
		vbErrors.getChildren().add(inputError);
	}

	public void phoneNumber_errorLength()  {
		vbErrors.getChildren().clear(); //will clean all the errors
		inputError.setText("Number must be 10 digits long exactly!");
		vbErrors.getChildren().add(inputError);
	}
	
	public void membership_error()  {
		vbErrors.getChildren().clear(); //will clean all the errors
		inputError.setText("Member ship me must be- yes/no!");
		vbErrors.getChildren().add(inputError);
	}
	

	
}
