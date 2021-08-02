package model;

import java.io.Serializable;

public class Customer implements Serializable, Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String phoneNum;
	private boolean member;

	public Customer(String name, String phoneNum, boolean member) {
		this.name= name;
		this.phoneNum = phoneNum;
		this.member = member;
	}

	public String toString() {
		String memberStr;
		if(member) {
			memberStr = "yes";  
		}
		else {
			memberStr = "no";
		}    
		return " Name: " + name+",  Phone Number: "+ phoneNum+ ",  is a member? "+ memberStr+"\n";

	}

	public boolean getMember() {
		return member;
		
	}
	
	public String getName() {
        return name;
    }
	
	public String getNumber() {
        return phoneNum;
    }
	
	@Override
	public String update(String msg) {
		return name;
	} 
}
