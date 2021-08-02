package model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.NoSuchElementException;

import memento.CareTaker;
import memento.Memento;

import java.util.Set;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Model { // model will be our Originator
	private static final String MESSAGE= "message";
	private static int profit = 0;
	private static final String F_NAME = "products.txt";
	private static ConcreteIteratorTree it;

	private int sortOrder=0;
	private LinkedHashMap<String, Product> ProductsByInsert;
	private TreeMap<String, Product> ProductByAlphabtic;
	private static CareTaker careTaker = new CareTaker();
	private Sender sender;

	public Model() 
	{
		sender = Sender.Instance();
		try {
			it = new ConcreteIteratorTree();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//allProducts = new LinkedHashMap<>();
	}

	public int getSortingOrder() {
		return sortOrder;
	}
	
	public Sender getSender() {
		return sender;
	}

	public boolean addProduct(String key, Product p) // for add product button (create Momento!)
	{
		Memento m1;
		if(sortOrder == 1)
		{
			m1 = new Memento(sortOrder, ProductsByInsert);
			careTaker.save(m1);
			if(ProductsByInsert.containsKey(key)) // removing the profit from the product with same key
				profit -= ProductsByInsert.get(key).getProfit();
			ProductsByInsert.put(key, p);
		}
		else {
			m1 = new Memento(sortOrder, ProductByAlphabtic);
			careTaker.save(m1);
			if(ProductByAlphabtic.containsKey(key)) // removing the profit from the product with same key
				profit -= ProductByAlphabtic.get(key).getProfit();
			ProductByAlphabtic.put(key, p);
		}
		profit += p.getProfit();
		return true;
	}

	public boolean deleteLast() throws IOException // using Memento (UNDO) here we will SET MOMENTO!
	{
		if(careTaker.size() == 0)
			return false;

		Memento  m1 = careTaker.restore();
		Set<Entry<String, Product>> set;
		profit = 0;
		switch(sortOrder)
		{
		case 1:
			ProductsByInsert = m1.getLinkedHashMap();
			set = ProductsByInsert.entrySet();
			for (Entry<String, Product> en : set) 
				profit += en.getValue().getProfit();
			break;
		default:
			ProductByAlphabtic = m1.getTreeMap();
			set = ProductByAlphabtic.entrySet();
			for (Entry<String, Product> en : set) 
				profit += en.getValue().getProfit();
			break;
		}

		saveToFile();
		return true;
	}

	public void setSortingOrder(int order) // for select order button
	{
		switch(order) 
		{ // 1- insert order, 2- a,b,c..., 3- z,y,x...
		case 1:
			ProductsByInsert = new LinkedHashMap<String, Product>();
			break;
		case 2:
			ProductByAlphabtic = new TreeMap<String, Product>(new ABC());
			break;
		case 3:
			ProductByAlphabtic = new TreeMap<String, Product>(new XYZ());
			break;
		}
		sortOrder = order;
	}

	public boolean isEmpty() {
		try {
			switch(sortOrder) 
			{ // 1- insert order, 2- a,b,c..., 3- z,y,x...
			case 1:
				return ProductsByInsert.isEmpty();
			default:
				return ProductByAlphabtic.isEmpty();
			}
		}catch(NullPointerException e) {//no yet defined
			return true;
		}

	}

	public String printData() // for show all button
	{
		StringBuffer sb = new StringBuffer();	
		Set<Entry<String, Product>> set;

		switch(sortOrder) {
		case 1:
			set = ProductsByInsert.entrySet();
			for (Entry<String, Product> en : set) 
				sb.append("MKT num: " + en.getKey() +" " + en.getValue() + " Profit: "+en.getValue().getProfit()+"\n");
			return sb.toString();
		default:
			set = ProductByAlphabtic.entrySet();
			for (Entry<String, Product> en : set)
				sb.append("MKT num: " + en.getKey() +" " + en.getValue()+ "Profit: "+en.getValue().getProfit()+"\n");
			return sb.toString();

		}
	}

	public Product getProduct(String key) { // for find button
		switch(sortOrder) 
		{ 
		case 1:
			return ProductsByInsert.get(key);
		default:
			return ProductByAlphabtic.get(key);
		}
	}

	public void saveToFile() throws IOException {
		it.saveToFile();
	}

	public boolean deleteById(String id) throws IOException {
		if(it.removeId(id)) {
			switch(sortOrder) {
			case 1:
				ProductsByInsert.remove(id);
				return true;
			default:
				ProductByAlphabtic.remove(id);
				return true;
			}
		}
		return false;
	}


	public boolean fileCheck() { // making sure we can work on the file
		File f = new File(F_NAME);
		if(!f.exists())
			return false;
		if(!f.canRead())
			return false;
		return f.canWrite();
	}

	public boolean readFromFile(){
		return it.wasReadOnStartUp();
	}

	public int getProfit() {
		return profit;
	}

	public boolean haveMembers() {
		return sender.haveMembers();
	}

	public void checkForMember(Customer c) {
		if(c.getMember())
			sender.addMember(c);
	}

	public void sendMessage() {
		sender.button_notify(MESSAGE);
	}

	public String printAprrovedMembers() {
		return sender.printAprrovedMembers();
	}

	public void MemberUpdate() {
		Set<Entry<String, Product>> set;
		//profit = 0;
		switch(sortOrder)
		{
		case 1:
			set = ProductsByInsert.entrySet();
			for (Entry<String, Product> en : set) 
				checkForMember(en.getValue().getCustomer());
			break;
		default:
			set = ProductByAlphabtic.entrySet();
			for (Entry<String, Product> en : set) 
				checkForMember(en.getValue().getCustomer());
			break;
		}
	}

	public void cleanFile() throws FileNotFoundException {
		it.removeAll();
	}

	// Itertator 

	private class ConcreteIteratorTree implements  Iterator<Entry<String, Product>> {
		private final String F_NAME = "products.txt";
		private RandomAccessFile f;
		private long location;
		private boolean readOnStart=false;

		public ConcreteIteratorTree() throws FileNotFoundException {
			f = new RandomAccessFile(F_NAME, "rw");
			try {
				if(f.length() > 0) {
					readToMap();
					readOnStart = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void saveToFile() {
			try {
				Set<Entry<String, Product>> set;
				f.setLength(0);
				f.writeInt(sortOrder);	

				switch(sortOrder)
				{
				case 1:
					set = ProductsByInsert.entrySet();
					break;
				default:
					set = ProductByAlphabtic.entrySet();
					break;
				}
				for (Entry<String, Product> en : set) {
					f.writeUTF(en.getKey());
					f.writeUTF(en.getValue().getName());
					f.writeInt(en.getValue().getBuyPrice());
					f.writeInt(en.getValue().getSellPrice());
					f.writeUTF(en.getValue().getCustomer().getName());
					f.writeUTF(en.getValue().getCustomer().getNumber());
					f.writeBoolean(en.getValue().getCustomer().getMember());
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public boolean wasReadOnStartUp() {
			return readOnStart;
		}

		@Override
		public boolean hasNext() {
			try {
				if(f.getFilePointer() < f.length())
					return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}

		private boolean readToMap() throws IOException,FileNotFoundException {
			try {
				setSortingOrder(f.readInt());
				while(hasNext()) {
					Entry<String, Product> tmp = next();
					addProduct(tmp.getKey(), tmp.getValue());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;

		}

		@Override
		public Entry<String, Product> next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Entry<String, Product> tmp = null;
			try {
				location = f.getFilePointer();
				tmp = readEntry();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			return tmp;				
		}

		private Entry<String, Product> readEntry() throws IOException {
			String id = f.readUTF();
			String name = f.readUTF();
			int buyingPrice = f.readInt();
			int sellingPrice = f.readInt();
			String buyerName = f.readUTF();
			String buyerNumber = f.readUTF();
			boolean member = f.readBoolean();
			Customer c1 = new Customer(buyerName, buyerNumber, member);
			Product p1 = new Product(name, buyingPrice, sellingPrice, c1);			
			return new SimpleEntry<String, Product>(id,p1);	
		}

		@Override
		public void remove() {
			try {
				int size = (int) (f.length() - f.getFilePointer());
				byte[] arr = new byte[size];
				f.read(arr);
				f.seek(location);
				f.write(arr);
				f.setLength(f.getFilePointer());
				deleteLast();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public void removeAll() {
			try {
				f.seek(0);
				if(!hasNext())
					return;
				f.readInt();
				location = f.getFilePointer();
				while(hasNext()) {
					next();
					remove();
					f.seek(location);
				}
				f.setLength(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public boolean removeId(String id) {
			try {
				f.seek(0);
				f.readInt();
				while(hasNext()) {
					Entry<String, Product> tmp = null;
					tmp = readEntry();
					if(tmp.getKey().equals(id)) {
						remove();
						return true;
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	}
}

