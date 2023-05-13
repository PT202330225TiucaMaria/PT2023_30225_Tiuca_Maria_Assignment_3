package Start;
import BLL.ClientBLL;
import Model.Client;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException {

        ClientBLL clientbll = new ClientBLL();

        Client c = null;
        //Client cInserat = null;
        Client cUpdate=null;
        List<Client> c2 = null;
        HashMap<String, Object> fieldsUpdated = new HashMap<>();
        fieldsUpdated.put("age", 20);
        fieldsUpdated.put("address", "Tarnaveni");
        HashMap<String, Object> conditionField = new HashMap<>();
        conditionField.put("id", 1);
        HashMap<String, Object> deleteCondition = new HashMap<>();
        deleteCondition.put("id", 7);
        try {
          //  c = clientbll.findClientById(1);
           // cInserat=clientbll.insert(new Client("Stavar Sebastian",7,"Alba Iulia",21));
           // cUpdate=clientbll.update(c,fieldsUpdated,conditionField);
            clientbll.delete(deleteCondition);
          /*  clientbll.insert(new Client("Tiuca Maria",1,"Mures",20));
            clientbll.insert(new Client("Pop Dina",2,"DEJ",20));
            clientbll.insert(new Client("Trif Gabi",3,"Alba Iulia",20));
            clientbll.insert(new Client("Schiop Adi",4,"Oradea",21));
            clientbll.insert(new Client("Ticudean Tudor",5,"Campia Turzii",22));
            clientbll.insert(new Client("Schiau Alex",6,"Maramuress",22));
            clientbll.insert(new Client("Stavar Sebastian",7,"Alba Iulia",21));
           */ c2=clientbll.findAll();
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        }
        // obtain field-value pairs for object through reflection
       // ReflectionExample.retrieveProperties(c);
        //ReflectionExample.retrieveProperties(cInserat);
        for(Client c1:c2)
            ReflectionExample.retrieveProperties(c1);

    }

}
