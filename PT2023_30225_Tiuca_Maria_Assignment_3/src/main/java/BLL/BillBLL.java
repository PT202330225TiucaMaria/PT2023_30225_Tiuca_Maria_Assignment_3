package BLL;
import Dao.BillDAO;
import Model.Bill;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * This class represents the business logic layer for managing Bill objects.
 * @author Tiuca Maria
 */
public class BillBLL {
    private BillDAO billDAO=new BillDAO();
    /**
     * Constructs a new BillBLL object.
     */
    public BillBLL(){
    }
    /**
     * Retrieves all Bill objects.
     *
     * @return A list of all Bill objects.
     * @throws NoSuchElementException if the table is empty.
     */
    public List<Bill> findAll() {
        List<Bill> c=billDAO.findAllRecordsObjects();
        if(c==null){
            throw new NoSuchElementException("Tabelul Log e gol!");
        }
        return c;
    }
    /**
     * Finds Bill objects by their ID.
     *
     * @param id The ID of the Bill to find.
     * @return A list of Bill objects with the specified ID.
     * @throws NoSuchElementException if the table is empty.
     */
    public List<Bill> findByIdBill(int id) {
        List<Bill> c=billDAO.findByIdBill(id);
        if(c==null){
            throw new NoSuchElementException("Tabelul Log e gol!");
        }
        return c;
    }
    /**
     * Inserts a new Bill object.
     *
     * @param t The Bill object to insert.
     * @return The inserted Bill object.
     */
    public Bill insert(Bill t){
        Bill inserat=billDAO.insert(t);
        return inserat;
    }
}
