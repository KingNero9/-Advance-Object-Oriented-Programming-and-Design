package command;

import java.io.FileNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Model;
import view.FileWindow;
import view.View;

public class FileWindowHandler implements Command{ // Contains all the buttons at the file window
	Model theModel;
	View theView;
	Stage stage;
	public FileWindowHandler(Model m, View v, Stage s) {
		theModel = m;
		theView = v;
		stage = s;
	}
	@Override
	public void handle() {
		EventHandler<ActionEvent> fileFunc = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(!theModel.fileCheck()) {
					theView.errorWithTheFile();
				}
				else {
					theView.close();
					FileWindow file_Window = new FileWindow(stage);
					//Read From file to map handler
					EventHandler<ActionEvent> FileToMap = new EventHandler<ActionEvent>() { 
						public void handle(ActionEvent event) { 
							try {
								if(!theModel.readFromFile()) {
									file_Window.fileReadFailed();
								}
								else {
									file_Window.fileReadSuccess();
									theView.setSelectedButtonFromFile(theModel.getSortingOrder());
									theView.disableSelect();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						}		
					};file_Window.addEventHandlerToRead(FileToMap);

					//DeleteALL From file handler
					EventHandler<ActionEvent> DeleteAll = new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) { 
							try {
								theModel.cleanFile();
								file_Window.fileDeleteAllSuccess();
								theModel.getSender().SetZeroMmbers();
							} catch (FileNotFoundException e) {
								file_Window.fileDeleteAllFailed();
							}
						}		
					};file_Window.addEventHandlerToDeleteAll(DeleteAll);
					
					
					EventHandler<ActionEvent> DeleteId = new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) { 
								file_Window.deleteById(theModel);
							}
								
					};file_Window.addEventHandlerToDeleteOne(DeleteId);

					//Back to Main menu handler
					EventHandler<ActionEvent> BackToMenu = new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) { 
							file_Window.close();
							theView.open();
							theView.setProfit(theModel.getProfit());
						}		
					};file_Window.addEventHandlerToBack(BackToMenu);
				}
			}		
		};theView.addEventHandlerToFileButton(fileFunc);
	}

}
