package command;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;
import view.*;

public class SelectOrder implements Command {
	Model theModel;
	View theView;
	public SelectOrder(Model m, View v) {
		theModel = m;
		theView = v;
	}


	@Override
	public void handle() {
		EventHandler<ActionEvent> select = new EventHandler<ActionEvent>() { //select 
			public void handle(ActionEvent event) { 
				int tmp = theView.getSortingOrder();
				theModel.setSortingOrder(tmp);
				theView.disableSelect();
			}
		};theView.addEventHandlerToSelect(select);	
	}

}
