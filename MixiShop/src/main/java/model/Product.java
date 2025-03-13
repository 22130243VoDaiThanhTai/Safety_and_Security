package model;

public class Product {
    private int id;
    private String name;
    private String image;
    private int price;
    private int quantity;
    private int categoryID;

    public Product() {
        super();
    }

    public Product(String name) {
        super();
        this.name = name;
    }

    public Product(String name, String image, int price, int quantity, int categoryID) {
		super();
		this.name = name;
		this.image = image;
		this.price = price;
		this.quantity = quantity;
		this.categoryID = categoryID;
	}

	// Constructor đầy đủ bao gồm categoryID
    public Product(int id, String name, String image, int price, int quantity, int categoryID) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.categoryID = categoryID;
    }

    // Getter và Setter cho categoryID
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    // Các getter và setter khác
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", image=" + image + ", price=" + price + ", categoryID=" + categoryID + "]";
    }
}
