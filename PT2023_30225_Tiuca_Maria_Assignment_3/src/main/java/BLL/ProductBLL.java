package BLL;
import Dao.ProductDAO;
import Model.Product;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
public class ProductBLL {
    private ProductDAO productDAO=new ProductDAO();
    public ProductBLL(){
    }
    public Product findProductById(int id){
        Product c=productDAO.findById(id);
        if(c==null){
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return c;
    }
    public List<Product> findAll() {
        List<Product> c=productDAO.findAll();
        if(c==null){
            throw new NoSuchElementException("Tabelul Product e gol!");
        }
        return c;
    }
    public Product insert(Product t) {
        Product inserat=productDAO.insert(t);
        return inserat;
    }
    public Product update(Product t, HashMap<String, Object> fieldsUpdatedValues, HashMap<String, Object> conditionFieldsValues) {
        Product updated=productDAO.update(t,fieldsUpdatedValues,conditionFieldsValues);
        return updated;
    }
    public void delete(HashMap<String, Object> deleteCondition) {
        productDAO.delete(deleteCondition);
    }
}
