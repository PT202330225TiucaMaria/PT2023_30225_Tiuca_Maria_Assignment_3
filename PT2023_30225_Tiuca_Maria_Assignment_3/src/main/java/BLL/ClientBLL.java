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
public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO=new ClientDAO();
    public ClientBLL(){
        validators=new ArrayList<Validator<Client>>();
        validators.add(new ClientNameValidator());
        validators.add(new ClientAgeValidator());
    }
    public Client findClientById(int id){
        Client c=clientDAO.findById(id);
        if(c==null){
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return c;
    }
    public List<Client> findAll() {
        List<Client> c=clientDAO.findAll();
        if(c==null){
            throw new NoSuchElementException("Tabelul Clienti e gol!");
        }
        return c;
    }
    public Client insert(Client t) {
        Client inserat=clientDAO.insert(t);
        return inserat;
    }
    public Client update(Client t, HashMap<String, Object> fieldsUpdatedValues, HashMap<String, Object> conditionFieldsValues) {
        Client updated=clientDAO.update(t,fieldsUpdatedValues,conditionFieldsValues);
        return updated;
    }
    public void delete(HashMap<String, Object> deleteCondition) {
        clientDAO.delete(deleteCondition);
    }
}
