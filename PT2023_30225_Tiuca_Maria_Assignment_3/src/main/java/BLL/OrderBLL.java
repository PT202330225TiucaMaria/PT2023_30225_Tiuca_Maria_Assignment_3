package BLL;
import Dao.OrderDAO;
import Model.Order;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
public class OrderBLL {
    private OrderDAO orderDAO=new OrderDAO();
    public OrderBLL(){
    }
    public Order findOrderById(int id){
        Order c=orderDAO.findById(id);
        if(c==null){
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return c;
    }
    public List<Order> findAll() {
        List<Order> c=orderDAO.findAll();
        if(c==null){
            throw new NoSuchElementException("Tabelul Order e gol!");
        }
        return c;
    }
    public Order insert(Order t){
        Order inserat=orderDAO.insert(t);
        return inserat;
    }
    public void delete(HashMap<String, Object> deleteCondition) {
        orderDAO.delete(deleteCondition);
    }
}