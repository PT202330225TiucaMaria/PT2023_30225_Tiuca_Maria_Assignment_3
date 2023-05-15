package BLL;
import Dao.ProductDAO;
import Model.Product;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * This class represents the business logic layer for managing Product objects.
 * It provides methods for retrieving, inserting, updating, and deleting product data.
 * It interacts with the ProductDAO to perform database operations.
 *
 * * @author Tiuca Maria
 *  */
 public class ProductBLL {
    private ProductDAO productDAO=new ProductDAO();
    /**
     * Constructs a new ProductBLL object.
     */
    public ProductBLL(){
    }
    /**
     * Finds a product by its ID.
     *
     * @param id The ID of the product to find.
     * @return The Product object with the specified ID.
     * @throws NoSuchElementException if the product is not found.
     */
    public Product findProductById(int id){
        Product c=productDAO.findById(id);
        if(c==null){
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return c;
    }
    /**
     * Retrieves all products.
     *
     * @return A list of all Product objects.
     * @throws NoSuchElementException if the table is empty.
     */
    public List<Product> findAll() {
        List<Product> c=productDAO.findAll();
        if(c==null){
            throw new NoSuchElementException("Tabelul Product e gol!");
        }
        return c;
    }
    /**
     * Inserts a new product.
     *
     * @param t The Product object to insert.
     * @return The inserted Product object.
     */
    public Product insert(Product t) {
        Product inserat=productDAO.insert(t);
        return inserat;
    }
    /**
     * Updates a product based on specified fields and conditions.
     *
     * @param t                   The Product object to update.
     * @param fieldsUpdatedValues A HashMap of field-value pairs to update.
     * @param conditionFieldsValues A HashMap of field-value pairs for update conditions.
     * @return The updated Product object.
     */
    public Product update(Product t, HashMap<String, Object> fieldsUpdatedValues, HashMap<String, Object> conditionFieldsValues) {
        Product updated=productDAO.update(t,fieldsUpdatedValues,conditionFieldsValues);
        return updated;
    }
    /**
     * Deletes products based on specified conditions.
     *
     * @param deleteCondition A HashMap of field-value pairs for delete conditions.
     */
    public void delete(HashMap<String, Object> deleteCondition) {
        productDAO.delete(deleteCondition);
    }
}
