package controller;


import javafx.stage.Stage;
import model.*;
import view.*;
import command.*;



public class Controller {
	Model theModel;
	View theView;
	Stage stage1;

	public Controller(Model m, View v) {
		theModel = m;
		theView = v;


		Stage stage = new Stage();
		stage.setResizable(false);

		//File Button Handler 
		FileWindowHandler fwh = new FileWindowHandler(theModel, theView, stage);
		fwh.handle();

		//Select Button handler	
			SelectOrder so = new SelectOrder(theModel,theView);
			so.handle();


		//Add Product Handler
			AddProduct ap = new AddProduct(theModel, theView, stage);
			ap.handle();

		//Delete Button handler
			DeleteLast dl = new DeleteLast(theModel, theView);
			dl.handle();

		//Print Button Handler
			PrintAll pa = new PrintAll(theModel, theView, stage);
			pa.handle();
			
		//Find Button Handler 
			FindProduct fp = new FindProduct(theModel, theView, stage);
			fp.handle();
			
		//send message button handler
			SendMessage sm = new SendMessage(theModel, theView);
			sm.handle();


		// Print all Observers who approved
			PrintMembers pm = new PrintMembers(theModel, theView, stage);
			pm.handle();
	}
}
