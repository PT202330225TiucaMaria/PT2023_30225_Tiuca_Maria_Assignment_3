package BLL.Validator;
import Model.Client;

/**
 * This class represents a validator for the age of a Client.
 * It implements the Validator interface for Client objects.
 * It checks if the age of a Client is within the specified limits.
 *
 * TODO: Add specific details about the age limits and validation logic.
 *
 * @author Tiuca Maria
 */
public class ClientAgeValidator implements Validator<Client> {
    private static final int MIN_AGE=13;
    private static final int MAX_AGE=60;
    /**
     * Validates the age of a Client.
     *
     * @param t The Client object to validate.
     * @throws IllegalArgumentException if the Client's age is not within the specified limits.
     */
    public void validate(Client t){
      if(t.getAge()<MIN_AGE||t.getAge()>MAX_AGE){
          throw new IllegalArgumentException("The Client Age limit is not respected!");
        }
    }
}
