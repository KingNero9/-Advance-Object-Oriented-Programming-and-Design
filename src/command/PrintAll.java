package command;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Model;
import view.PrintWindow;
import view.View;

public class PrintAll implements Command {
	Model theModel;
	View theView;
	Stage stage;
	public PrintAll(Model m, View v, Stage s) {
		theModel = m;
		theView = v;
		stage = s;
	}
	@Override
	public void handle() {
		EventHandler<ActionEvent> PrintProducts = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) { 
				if(theModel.isEmpty()) {
					theView.noDataError();
				}
				else {
					PrintWindow print_Window = new PrintWindow(stage);
					try {
						print_Window.showAll(theModel.printData());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}		
		};theView.addEventHandlerToPrintProduct(PrintProducts);
		
	}

}
