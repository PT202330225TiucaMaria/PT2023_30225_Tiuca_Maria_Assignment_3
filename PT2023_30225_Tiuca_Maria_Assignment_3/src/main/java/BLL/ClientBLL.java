package BLL;
import BLL.Validator.ClientAgeValidator;
import BLL.Validator.ClientNameValidator;
import Dao.ClientDAO;
import Model.Client;
import BLL.Validator.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * This class represents the business logic layer for managing Client objects.
 * It provides methods for retrieving, inserting, updating, and deleting client data.
 * It also includes validation logic for client data using a list of validators.
 *
 * @author Tiuca Maria
 */
public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO=new ClientDAO();
    /**
     * Constructs a new ClientBLL object.
     * Initializes the list of validators with default validators for client data.
     */
    public ClientBLL(){
        validators=new ArrayList<Validator<Client>>();
        validators.add(new ClientNameValidator());
        validators.add(new ClientAgeValidator());
    }
    /**
     * Finds a client by their ID.
     *
     * @param id The ID of the client to find.
     * @return The Client object with the specified ID.
     * @throws NoSuchElementException if the client is not found.
     */
    public Client findClientById(int id){
        Client c=clientDAO.findById(id);
        if(c==null){
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return c;
    }
    /**
     * Retrieves all clients.
     *
     * @return A list of all Client objects.
     * @throws NoSuchElementException if the table is empty.
     */
    public List<Client> findAll() {
        List<Client> c=clientDAO.findAll();
        if(c==null){
            throw new NoSuchElementException("Tabelul Clienti e gol!");
        }
        return c;
    }
    /**
     * Inserts a new client.
     *
     * @param t The Client object to insert.
     * @return The inserted Client object.
     */
    public Client insert(Client t) {
        Client inserat=clientDAO.insert(t);
        return inserat;
    }
    /**
     * Updates a client based on specified fields and conditions.
     *
     * @param t                   The Client object to update.
     * @param fieldsUpdatedValues A HashMap of field-value pairs to update.
     * @param conditionFieldsValues A HashMap of field-value pairs for update conditions.
     * @return The updated Client object.
     */
    public Client update(Client t, HashMap<String, Object> fieldsUpdatedValues, HashMap<String, Object> conditionFieldsValues) {
        Client updated=clientDAO.update(t,fieldsUpdatedValues,conditionFieldsValues);
        return updated;
    }
    /**
     * Deletes clients based on specified conditions.
     *
     * @param deleteCondition A HashMap of field-value pairs for delete conditions.
     */
    public void delete(HashMap<String, Object> deleteCondition) {
        clientDAO.delete(deleteCondition);
    }
}
