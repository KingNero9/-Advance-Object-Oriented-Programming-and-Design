package view;
import javafx.scene.text.Text;

public class ThreadPrinting implements Runnable  {
	private String[]s;
	private Text list;
	public ThreadPrinting(String[]s1, Text list1){
		s = s1;
		list = list1;
		Thread thread = new Thread(this);
		thread.start();
	}
	@Override
	public void run() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length; i++) {
			sb.append(s[i]+ "\n");
			list.setText(sb.toString());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
