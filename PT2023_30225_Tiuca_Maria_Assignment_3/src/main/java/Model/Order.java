package Model;
public class Order {
    private int id;
    private String clientName;
    private String productName;
    private String address;
    private int quantity;
    private double price;
    public Order(){}
    public Order(int id,Client c,Product p){
        this.id=id;
        this.clientName=c.getName();
        this.productName=p.getName();
        this.address=c.getAddress();
        this.quantity=p.getQuantity();
        this.price=p.getQuantity()*p.getPrice();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
        return "id: "+o.getId()+" Client's name: "+o.getClientName()+" address "+o.getAddress()+" product: "+o.getProductName()+" quantity: "+o.getQuantity()+" price: "+o.getPrice();
    }
}