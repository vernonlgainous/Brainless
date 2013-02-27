package edu.ufl.brainless;

public class Item {
	String itemName;

	private static final String TAG = Item.class.getSimpleName();

	// constructors
	public Item() {
		itemName = "Item";
		// TODO Auto-generated constructor stub
	}

	public Item(String itemName) {
		this.itemName = itemName;
		// TODO Auto-generated constructor stub
	}

	// setters and getters
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
