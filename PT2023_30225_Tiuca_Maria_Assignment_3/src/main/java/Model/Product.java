package Model;
/**
 * The Product class represents a product entity in the system.
 * It contains information such as the product's quantity, price, ID, and name.
 */
public class Product {
    private int quantity;
    private double price;
    private int id;
    private String name;
    /**
     * Constructs a new instance of the Product class with default values.
     */
    public Product(){}
    /**
     * Constructs a new Product object with the specified name, ID, price, and quantity.
     *
     * @param name     the name of the product
     * @param id       the ID of the product
     * @param price    the price of the product
     * @param quantity the quantity of the product
     */
    public Product(String name, int id, double price,int quantity){
        this.name=name;
        this.id=id;
        this.price=price;
        this.quantity=quantity;
    }
    /**
     * Retrieves the quantity of the product.
     *
     * @return the product's quantity
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * Sets the quantity of the product.
     *
     * @param quantity the product's quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    /**
     * Retrieves the price of the product.
     *
     * @return the product's price
     */
    public double getPrice() {
        return price;
    }
    /**
     * Sets the price of the product.
     *
     * @param price the product's price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Retrieves the ID of the product.
     *
     * @return the product's ID
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the ID of the product.
     *
     * @param id the product's ID to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Retrieves the name of the product.
     *
     * @return the product's name
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the product.
     *
     * @param name the product's name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Updates the stock quantity of the product by subtracting the given quantity.
     *
     * @param quantity the quantity to subtract from the stock
     */
    public void actualizareStoc(int quantity){
        this.quantity=this.quantity-quantity;
    }
    /**
     * Returns a string representation of the Product object.
     *
     * @param p the Product object to convert to a string
     * @return a string representation of the Product object
     */
    public String toString(Product p) {
        return "id: "+p.getId()+" name: "+p.getName()+" quantity: "+p.getQuantity()+" price/buc: "+p.getPrice();
    }
}
