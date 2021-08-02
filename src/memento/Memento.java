package memento;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import model.Product;

public class Memento {
	private int sortOrder;
	private LinkedHashMap<String, Product> ProductsByInsert;
	private TreeMap<String, Product> ProductByAlphabtic;

	@SuppressWarnings("unchecked")
	public Memento(int order, LinkedHashMap<String, Product>ProductsByInsert) {
		sortOrder = order;
		if(sortOrder == 1) {
			this.ProductsByInsert = new LinkedHashMap<String, Product>();
			this.ProductsByInsert = (LinkedHashMap<String, Product>) ProductsByInsert.clone();
		}		
	}

	@SuppressWarnings("unchecked")
	public Memento(int order, TreeMap<String, Product> ProductByAlphabtic) {
		sortOrder = order;
		if(sortOrder != 1 ) {
			this.ProductByAlphabtic = new TreeMap<String, Product>();
			this.ProductByAlphabtic = (TreeMap<String, Product>) ProductByAlphabtic.clone();
		}
	}

	public TreeMap<String, Product> getTreeMap() {
		if(sortOrder == 1)
			return null;
		return ProductByAlphabtic;
	}

	public LinkedHashMap<String, Product> getLinkedHashMap() {
		if(sortOrder != 1)
			return null;
		return ProductsByInsert;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();	
		Set<Entry<String, Product>> set;

		switch(sortOrder) {
		case 1:
			set = ProductsByInsert.entrySet();
			for (Entry<String, Product> en : set) 
				sb.append("MKT num: " + en.getKey() +" " + en.getValue());
			return sb.toString();
		default:
			set = ProductByAlphabtic.entrySet();
			for (Entry<String, Product> en : set)
				sb.append("MKT num: " + en.getKey() +" " + en.getValue());
			return sb.toString();
		}
	}
}
