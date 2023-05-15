package BLL;
import Dao.BillDAO;
import Model.Bill;
import java.util.List;
import java.util.NoSuchElementException;
public class BillBLL {
    private BillDAO billDAO=new BillDAO();
   // public BillBLL(){
    //}
    public List<Bill> findAll() {
        List<Bill> c=billDAO.findAll();
        if(c==null){
            throw new NoSuchElementException("Tabelul Log e gol!");
        }
        return c;
    }
    public Bill insert(Bill t){
        Bill inserat=billDAO.insert(t);
        return inserat;
    }
}
