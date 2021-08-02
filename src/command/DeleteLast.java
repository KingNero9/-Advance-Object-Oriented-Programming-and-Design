package command;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Model;
import view.View;

public class DeleteLast implements Command{
	Model theModel;
	View theView;
	public DeleteLast(Model m, View v) {
		theModel = m;
		theView = v;
	}

	@Override
	public void handle() {
		EventHandler<ActionEvent> delete = new EventHandler<ActionEvent>() { //delete last
			public void handle(ActionEvent event) { 
				try {
					if(theModel.deleteLast()) {
						theView.deleteSuccess();
						theView.setProfit(theModel.getProfit());
					}
					else
						theView.deleteFailure();
				} catch (IOException e) {
					theView.errorSavingToFile();
				}
			}
		};theView.addEventHandlerToDeleteProduct(delete);
		
	}
}
