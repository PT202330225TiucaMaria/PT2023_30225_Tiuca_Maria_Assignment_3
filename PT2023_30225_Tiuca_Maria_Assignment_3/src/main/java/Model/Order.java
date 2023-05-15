package Model;
public class Order {
    private int id;
    private String clientName;
    private String productName;
    private String clientAddress;
    private int productQuantity;
    private double price;
    private int productId;
    public Order(){}
    public Order(int id,Client client,Product product,int quantity){
        this.id=id;
        this.clientName=client.getName();
        this.productName=product.getName();
        this.clientAddress=client.getAddress();
        this.productQuantity=quantity;
        this.price=quantity*product.getPrice();
        this.productId=product.getId();
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getClientAddress() {
        return clientAddress;
    }
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
    public int getProductQuantity() {
        return productQuantity;
    }
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String toString(Order o){
        return "id: "+o.getId()+" Client's name: "+o.getClientName()+" address "+o.getClientAddress()+" product: "+o.getProductName()+" quantity: "+o.getProductQuantity()+" price: "+o.getPrice();
    }
}