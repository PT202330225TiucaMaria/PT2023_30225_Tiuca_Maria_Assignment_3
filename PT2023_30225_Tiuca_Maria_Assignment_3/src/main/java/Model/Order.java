package Model;
/**
 * The Order class represents an order entity in the system.
 * It contains information such as the order ID, client's name, product name,
 * client's address, product quantity, price, and product ID.
 */
public class Order {
    private int id;
    private String clientName;
    private String productName;
    private String clientAddress;
    private int productQuantity;
    private double price;
    private int productId;
    /**
     * Constructs a new instance of the Order class with default values.
     */
    public Order(){}
    /**
     * Constructs a new instance of the Order class with the specified values.
     *
     * @param id            the order ID
     * @param client        the client associated with the order
     * @param product       the product associated with the order
     * @param quantity      the quantity of the product in the order
     */
    public Order(int id,Client client,Product product,int quantity){
        this.id=id;
        this.clientName=client.getName();
        this.productName=product.getName();
        this.clientAddress=client.getAddress();
        this.productQuantity=quantity;
        this.price=quantity*product.getPrice();
        this.productId=product.getId();
    }
    /**
     * Retrieves the product ID.
     *
     * @return the product ID
     */
    public int getProductId() {
        return productId;
    }
    /**
     * Sets the product ID.
     *
     * @param productId the product ID to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }
    /**
     * Retrieves the order ID.
     *
     * @return the order ID
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the order ID.
     *
     * @param id the order ID to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Retrieves the client's address.
     *
     * @return the client's address
     */
    public String getClientAddress() {
        return clientAddress;
    }
    /**
     * Sets the client's address.
     *
     * @param clientAddress the client's address to set
     */
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
    /**
     * Retrieves the product quantity.
     *
     * @return the product quantity
     */
    public int getProductQuantity() {
        return productQuantity;
    }
    /**
     * Sets the product quantity.
     *
     * @param productQuantity the product quantity to set
     */
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
    /**
     * Retrieves the total price of the order.
     *
     * @return the total price of the order
     */
    public double getPrice() {
        return price;
    }
    /**
     * Sets the total price of the order.
     *
     * @param price the total price of the order to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Retrieves the client's name.
     *
     * @return the client's name
     */
    public String getClientName() {
        return clientName;
    }
    /**
     * Sets the client's name.
     *
     * @param clientName the client's name to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    /**
     * Retrieves the product name associated with the order.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }
    /**
     * Sets the product name associated with the order.
     *
     * @param productName the product name to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    /**
     * Returns a string representation of the Order object.
     *
     * @param o the Order object to convert to a string
     * @return a string representation of the Order object
     */
    public String toString(Order o){
        return "id: "+o.getId()+" Client's name: "+o.getClientName()+" address "+o.getClientAddress()+" product: "+o.getProductName()+" quantity: "+o.getProductQuantity()+" price: "+o.getPrice();
    }
}