package Model;
public class Order {
    private int id;
    private String clientName;
    private String productName;
    private String clientAddress;
    private int productQuantity;
    private double price;
    public Order(){}
    public Order(int id,String clientName,String productName,String address,int quantity,double price){
        this.id=id;
        this.clientName=clientName;
        this.productName=productName;
        this.clientAddress=address;
        this.productQuantity=quantity;
        this.price=quantity*price;
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