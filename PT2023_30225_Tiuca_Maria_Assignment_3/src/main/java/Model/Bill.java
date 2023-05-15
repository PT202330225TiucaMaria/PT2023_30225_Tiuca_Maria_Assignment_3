package Model;
/**
 * The Bill record represents a bill entity in the system.
 * It contains information such as the order ID, client name, client address,
 * product name, product ID, product quantity, and price.
 */
public record Bill(int idOrder, String clientName,String clientAddress, String productName, int productId, int productQuantity, double price) {
}
