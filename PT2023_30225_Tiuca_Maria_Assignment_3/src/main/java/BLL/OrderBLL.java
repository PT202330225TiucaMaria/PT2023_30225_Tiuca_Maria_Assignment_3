package BLL;
import Dao.OrderDAO;
import Model.Order;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * This class represents the business logic layer for managing Order objects.
 * It provides methods for retrieving, inserting, and deleting order data.
 *
 * @author Tiuca Maria
 */
public class OrderBLL {
    private OrderDAO orderDAO=new OrderDAO();
    /**
     * Constructs a new OrderBLL object.
     */
    public OrderBLL(){
    }
    /**
     * Finds an order by its ID.
     *
     * @param id The ID of the order to find.
     * @return The Order object with the specified ID.
     * @throws NoSuchElementException if the order is not found.
     */
    public Order findOrderById(int id){
        Order c=orderDAO.findById(id);
        if(c==null){
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return c;
    }
    /**
     * Retrieves all orders.
     *
     * @return A list of all Order objects.
     * @throws NoSuchElementException if the table is empty.
     */
    public List<Order> findAll() {
        List<Order> c=orderDAO.findAll();
        if(c==null){
            throw new NoSuchElementException("Tabelul Order e gol!");
        }
        return c;
    }
    /**
     * Inserts a new order.
     *
     * @param t The Order object to insert.
     * @return The inserted Order object.
     */
    public Order insert(Order t){
        Order inserat=orderDAO.insert(t);
        return inserat;
    }
    /**
     * Deletes orders based on specified conditions.
     *
     * @param deleteCondition A HashMap of field-value pairs for delete conditions.
     */
    public void delete(HashMap<String, Object> deleteCondition) {
        orderDAO.delete(deleteCondition);
    }
}