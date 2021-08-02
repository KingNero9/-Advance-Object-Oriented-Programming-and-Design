package model;

import java.io.Serializable;
import java.util.Comparator;

public class ABC implements Comparator<String>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(String s1, String s2) {
		return s1.compareTo(s2);
	}

}
