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
import model.Product;

public class FindWindow {
	private Text title, product,mktText, error;
	private TextField mkt;
	private Pane pane;
	private VBox vb;
	private HBox hb;
	private Button find;

	public FindWindow(Stage stage)
	{	
		DropShadow ds = new DropShadow();
		ds.setRadius(15);
		ds.setColor(Color.DARKBLUE);
		
		product = new Text();
		product.setFont(Font.font("Verdana", 12));
		title = new Text("Find a Product");
		title.setFont(Font.font("Verdana", 30));
		title.setEffect(ds);
		title.setLayoutX(300);
		title.setLayoutY(50);
		

		
		find = new Button("Find Product");
		find.setLayoutX(370);
		find.setLayoutY(200);

		error = new Text("");
		error.setFill(Color.RED);
		mktText = new Text("Enter the mkt numer of the product: ");
		mkt = new TextField();
		hb = new HBox();
		hb.getChildren().addAll(mktText, mkt);
		hb.setLayoutX(230);
		hb.setLayoutY(150);
		hb.setSpacing(15);
		
		
		vb = new VBox();
		vb.getChildren().addAll(product);
		vb.setSpacing(15);
		vb.setLayoutX(20);
		vb.setLayoutY(250);
		
		
		
		
		pane = new Pane();
		pane.getChildren().addAll(title, hb, vb, find);
		int width = 820, height = 680;
		Scene scene = new Scene(pane,width,height);
		stage.setScene(scene);
		stage.show();
	}
	
	public void addEventHandlerToFindProduct(EventHandler<ActionEvent> event) { //find button
		find.setOnAction(event);
	}
	
	public void noSuchProductError() {
		error.setText("There is no such product");
		vb.getChildren().remove(error);
		vb.getChildren().add(error);
	}
	
	public String getMktForFind() {
		return mkt.getText().trim();
	}
	
	public void printProduct(Product p) {
		if(p == null)
			noSuchProductError();
		else {
			product.setText(p.toString());
			vb.getChildren().remove(error);

		}
	}

}
