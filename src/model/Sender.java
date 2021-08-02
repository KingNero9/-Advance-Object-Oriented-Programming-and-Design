package model;

import java.util.LinkedHashSet;
import java.util.Set;

public class Sender {
	private Set<Observer> allMembers;
	private Set<String> confirmed; // only print the customers who sent a msg back
	private static final Sender SENDER = new Sender(); 
	
	private Sender() {
		allMembers = new LinkedHashSet<Observer>();
	}
	
	public static Sender Instance() {
		return SENDER;
	}
	
	public void addMember(Observer o) {
		allMembers.add(o);
	}
	
	public void SetZeroMmbers() {
		allMembers.clear();
	}

	public void button_notify(String msg) {
		confirmed = new LinkedHashSet<String>();
		for (Observer o : allMembers) {
			Customer c = (Customer)o;
			if(c.getMember()) {
				confirmed.add(c.getName());
			}
		}
	}
	
	public boolean haveMembers() {
		if(allMembers.size() == 0 )
			return false;
		return true;
	}
	
	public String printAprrovedMembers() {
		if(confirmed == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (String name : confirmed) {
			sb.append(name+ "\n");
		}
		return sb.toString();
	}

}
