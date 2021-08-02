package command;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Model;
import view.FindWindow;
import view.View;

public class FindProduct implements Command {
	Model theModel;
	View theView;
	Stage stage;
	public FindProduct(Model m, View v, Stage s) {
		theModel = m;
		theView = v;
		stage = s;
	}
	@Override
	public void handle() {
		EventHandler<ActionEvent> FindProducts = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) { 
				if(theModel.isEmpty()) {
					theView.noDataError();
				}
				else {
					FindWindow find_Window = new FindWindow(stage);
					EventHandler<ActionEvent> FindTheProduct = new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) { 
							if(find_Window.getMktForFind().isEmpty()) {
								find_Window.noSuchProductError();
							}
							else
								find_Window.printProduct(theModel.getProduct(find_Window.getMktForFind()));

						}		
					};find_Window.addEventHandlerToFindProduct(FindTheProduct);
				}
			}		
		};theView.addEventHandlerToFindProduct(FindProducts);

	}

}
