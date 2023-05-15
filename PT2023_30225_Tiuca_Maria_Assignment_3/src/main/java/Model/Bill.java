package Model;
public record Bill(int idOrder, String clientName,String clientAddress, String productName, int productId, int productQuantity, double price) {
    // No need for explicit constructors, getters, and setters in records
}
