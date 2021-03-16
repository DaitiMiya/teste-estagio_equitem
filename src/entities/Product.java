package entities;

public class Product {
	private String name;
	private String quantity;
	private String value;

	public Product(String name, String quantity, String value) {
		this.name = name;
		this.quantity = quantity;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getValue() {
		return value;
	}
	
}
