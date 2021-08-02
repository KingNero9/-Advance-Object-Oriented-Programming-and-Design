package command;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Model;
import view.MembersWindow;
import view.View;

public class PrintMembers implements Command {
	Model theModel;
	View theView;
	Stage stage;
	public PrintMembers(Model m, View v, Stage s) {
		theModel = m;
		theView = v;
		stage = s;
	}
	@Override
	public void handle() {
		EventHandler<ActionEvent> PrintCustomers = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) { 
				if(theModel.isEmpty()) {
					theView.failedToSendMessageToMemebers();
				}
				else if(theModel.printAprrovedMembers() == null){
					theView.failedNoConfirmedMemebers();
				}
				else {
					Stage tmp = new Stage(); // stage so we can do multiple windows at a time 
					MembersWindow members_Window = new MembersWindow(tmp);
					try {
						members_Window.showAll(theModel.printAprrovedMembers());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};theView.addEventHandlerToPrintAllApprovedButton(PrintCustomers);

	}

}
