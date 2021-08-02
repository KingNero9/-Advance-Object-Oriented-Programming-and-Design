package view;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PrintWindow {
	protected Text title, list;
	protected Pane pane;
	protected VBox vb;
	public PrintWindow(Stage stage)
	{	
		DropShadow ds = new DropShadow();
		ds.setRadius(15);
		ds.setColor(Color.DARKBLUE);
		
		list = new Text();
		list.setFont(Font.font("Verdana", 14));
		title = new Text("All Products");
		title.setFont(Font.font("Verdana", 30));
		title.setEffect(ds);
		title.setLayoutX(600);
		title.setLayoutY(50);
		

		
		vb = new VBox();
		vb.getChildren().addAll(list);
		vb.setSpacing(15);
		vb.setLayoutX(5);
		vb.setLayoutY(70);
		
		
		
		
		pane = new Pane();
		pane.getChildren().addAll(title,vb);
		int width = 1280, height = 680;
		Scene scene = new Scene(pane,width,height);
		stage.setScene(scene);
		stage.show();
	}
	
	public void showAll(String map) throws InterruptedException { // exception for the threa in MembersWindow
		list.setText(map);
	}

}
