package model;

import java.io.Serializable;

public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int buyPrice; // store buying the product for that price
	
	// sellPrice can't be negative must check while reading info
	private int sellPrice; // store sell the product to the customer in that price 
	
	private Customer buyer;
		
	public Product(String name, int buy, int sell, Customer c1)
	{
		this.name = name;
		buyPrice = buy;
		sellPrice = sell;
		buyer = c1;
	}

	public String getName() {
		return name;
	}

	public int getBuyPrice() {
		return buyPrice;
	}

	public int getSellPrice() {
		return sellPrice;
	}

	@Override
	public String toString() {
		return "Product name: " + name + ",  buyPrice: " + buyPrice + ",  sellPrice: " + sellPrice + ",  buyer: " + buyer+ ", ";
	}
	
	public int getProfit() {
		return sellPrice - buyPrice;
	}
	
	public Customer getCustomer() {
		return buyer;
	}
	
	

}
