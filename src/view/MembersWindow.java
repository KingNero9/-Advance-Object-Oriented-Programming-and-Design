package view;

import javafx.stage.Stage;

public class MembersWindow extends PrintWindow {

	public MembersWindow(Stage stage) {
		super(stage);
		title.setText("Members Who Approved");
		title.setLayoutX(450);
	}
	

	@Override
	public void showAll(String map) throws InterruptedException {
	
		String[] s = map.split("\n");
		ThreadPrinting obj = new ThreadPrinting(s, list);
	
	}



}
