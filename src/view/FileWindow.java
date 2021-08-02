package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Model;

public class FileWindow {
	private BorderPane bp;
	private Button read, deleteAll, deleteOne, back; 
	private HBox hbButton, hbId;
	private VBox vbDownButton;
	private TextField id;
	private Text title, message, idText;
	private Stage stage;
	private Pane pane;


	public FileWindow(Stage stage1) {
		stage = stage1;
		stage.setResizable(false);

		//error or success text
		message = new Text("");

		//Title
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		ds.setColor(Color.DARKBLUE);
		title = new Text("File Iterator Fucntions");
		title.setFont(Font.font("Verdana", 30));
		title.setEffect(ds);
		title.setLayoutX(160);


		//Center 
		idText = new Text("ID for delete");
		id  = new TextField();
		hbId = new HBox();
		hbId.getChildren().addAll(idText,id);
		hbId.setSpacing(30);
		hbId.setLayoutX(200);
		hbId.setLayoutY(100);



		back = new Button("Back To Main Menu");


		read = new Button("Read to map");
		deleteAll = new Button("Delete All Products");
		deleteOne = new Button("Delete Product");


		hbButton = new HBox();
		hbButton.getChildren().addAll(read,deleteAll);
		hbButton.setSpacing(30);
		hbButton.setLayoutX(200);
		hbButton.setLayoutY(100);


		deleteOne.setLayoutX(257);
		deleteOne.setLayoutY(150);
		vbDownButton = new VBox();
		vbDownButton.setLayoutX(242);
		vbDownButton.setLayoutY(190);
		vbDownButton.getChildren().addAll(hbId, back, message);
		vbDownButton.setSpacing(30);


		pane = new Pane();
		pane.getChildren().addAll(hbButton,deleteOne, vbDownButton);

		//connection everything
		bp = new BorderPane();
		bp.setTop(title);
		bp.setCenter(pane);
		BorderPane.setMargin(bp.getTop(), new Insets(0, 0,0,160));

		int width = 640, height = 480;
		Scene scene = new Scene(bp,width,height);
		stage.setScene(scene);
		stage.show();
	}

	public void close() {
		stage.close();
	}

	public void deleteById(Model theModel) {
		if(id.getText().trim() == null) {
			idDeleteNoText();
			return;		
		}
		try {
			if(theModel.deleteById(id.getText().trim())) 
				idDeleteSuccess();
			else
				idDeleteFailed();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void addEventHandlerToRead(EventHandler<ActionEvent> event) { //Add button
		read.setOnAction(event);
	}

	public void addEventHandlerToDeleteAll(EventHandler<ActionEvent> event) { //delete button
		deleteAll.setOnAction(event);
	}

	public void addEventHandlerToBack(EventHandler<ActionEvent> event) { //print button
		back.setOnAction(event);
	}

	public void addEventHandlerToDeleteOne(EventHandler<ActionEvent> event) { //find button
		deleteOne.setOnAction(event);
	}


	//erroes / success 

	public void fileReadFailed() {
		vbDownButton.getChildren().remove(message);
		message.setFill(Color.RED);
		message.setText("Couldn't read from File, unexpected error");
		vbDownButton.getChildren().add(message);
	}

	public void fileReadSuccess() {
		vbDownButton.getChildren().remove(message);
		message.setFill(Color.GREEN);
		message.setText("Read was a huge success!");
		vbDownButton.getChildren().add(message);
	}

	public void fileDeleteAllSuccess() {
		vbDownButton.getChildren().remove(message);
		message.setFill(Color.GREEN);
		message.setText("Deleted All from the file!");
		vbDownButton.getChildren().add(message);
	}

	public void fileDeleteAllFailed() {
		vbDownButton.getChildren().remove(message);
		message.setFill(Color.RED);
		message.setText("Deleted All Failed!");
		vbDownButton.getChildren().add(message);
	}

	public void idDeleteNoText() {
		vbDownButton.getChildren().remove(message);
		message.setFill(Color.RED);
		message.setText("No ID for delete");
		vbDownButton.getChildren().add(message);
	}

	public void idDeleteFailed() {
		vbDownButton.getChildren().remove(message);
		message.setFill(Color.RED);
		message.setText("No matching ID in file");
		vbDownButton.getChildren().add(message);
	}

	public void idDeleteSuccess() {
		vbDownButton.getChildren().remove(message);
		message.setFill(Color.GREEN);
		message.setText("ID DELETED!");
		vbDownButton.getChildren().add(message);
	}

}
