package command;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Model;
import view.View;

public class SendMessage implements Command {
	Model theModel;
	View theView;
	public SendMessage(Model m, View v) {
		theModel = m;
		theView = v;
	}
	@Override
	public void handle() {
		EventHandler<ActionEvent> sendMessage = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) { 
				if(!theModel.haveMembers()) 
					theView.failedToSendMessageToMemebers();
				else {
					theView.successSendMessageToMemebers();
					theModel.sendMessage();
				}
			}
		};theView.addEventHandlerToSendButton(sendMessage);
	}

}
