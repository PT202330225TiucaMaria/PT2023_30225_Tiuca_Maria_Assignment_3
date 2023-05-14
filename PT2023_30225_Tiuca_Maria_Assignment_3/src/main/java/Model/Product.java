package Model;

public class Product {
    private int quantity;
    private double price;
    private int id;
    private String name;
    public Product(){}
    public Product(String name, int id, double price,int quantity){
        this.name=name;
        this.id=id;
        this.price=price;
        this.quantity=quantity;
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
    public void actualizareStoc(int quantity){
        this.quantity=this.quantity-quantity;
    }
    public String toString(Product p) {
        return "id: "+p.getId()+" name: "+p.getName()+" quantity: "+p.getQuantity()+" price/buc: "+p.getPrice();
    }
}
