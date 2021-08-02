package command;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Model;
import view.AddWindow;
import view.View;

public class AddProduct implements Command{
	Model theModel;
	View theView;
	Stage stage;
	public AddProduct(Model m, View v, Stage s) {
		theModel = m;
		theView = v;
		stage = s;
	}

	@Override
	public void handle() {
		EventHandler<ActionEvent> AddProduct = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(!theView.isOrderSelected()) {
					theView.cantAddYet();
				}
				else {
					theView.close();
					AddWindow add_Window = new AddWindow(stage);


					EventHandler<ActionEvent> add = new EventHandler<ActionEvent>() { //adding inside the add peroduct window
						public void handle(ActionEvent event) { // check valid id
							if(!add_Window.getId())
							{
								add_Window.idError();
							}
							else {
								if(add_Window.getProduct(theModel)) {
									try {
										add_Window.close();
										theView.open();
										theModel.saveToFile();
										theView.AddProductSuccess();
										theView.setProfit(theModel.getProfit());
									} catch (IOException e) {
										theView.errorSavingToFile();
									}

								}

							}
						}
					};add_Window.addEventHandlerToAdd(add);
				}
			}		
		};theView.addEventHandlerToAddProduct(AddProduct);
		
	}

}
