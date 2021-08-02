package main;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view.View;

public class Prog extends Application  {
	private static Model theModel;
	private static View theView;
	private static Controller TheController;
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		theModel = new Model();
		theView = new View(stage);

		try { // loading from file on startup
			if(theModel.readFromFile()) {
				theView.setSelectedButtonFromFile(theModel.getSortingOrder());
				theView.disableSelect();
				theModel.MemberUpdate();
				theView.setProfit(theModel.getProfit());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TheController = new Controller(theModel, theView);
	}

	public static void startOver(Stage stage) {
		theView = new View(stage);
		TheController = new Controller(theModel, theView);
	}
}
