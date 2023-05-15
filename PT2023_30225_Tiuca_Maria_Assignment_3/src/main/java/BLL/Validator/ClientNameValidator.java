package BLL.Validator;
import Model.Client;
import java.util.regex.Pattern;
/**
 * This class represents a validator for the name of a Client.
 * It implements the Validator interface for Client objects.
 * It checks if the name of a Client matches a specific pattern.
 *
 * TODO: Add specific details about the name pattern and validation logic.
 *
 * @author Tiuca Maria
 */
public class ClientNameValidator implements Validator<Client> {
    private static final String namePattern="^[A-Za-z]+\\s[A-Za-z]+$";
    /**
     * Validates the name of a Client.
     *
     * @param t The Client object to validate.
     * @throws IllegalArgumentException if the Client's name does not match the specified pattern.
     */
    public void validate(Client t){
        Pattern pattern= Pattern.compile(namePattern);
        if(!pattern.matcher(t.getName()).matches()){
            throw new IllegalArgumentException("The Client's Name is not valid!");
        }
    }
}
