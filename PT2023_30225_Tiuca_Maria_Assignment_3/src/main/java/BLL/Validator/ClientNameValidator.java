package BLL.Validator;
import Model.Client;
import java.util.regex.Pattern;
public class ClientNameValidator implements Validator<Client> {
    private static final String namePattern="^[A-Za-z]+\\s[A-Za-z]+$";
    public void validate(Client t){
        Pattern pattern= Pattern.compile(namePattern);
        if(!pattern.matcher(t.getName()).matches()){
            throw new IllegalArgumentException("The Client's Name is not valid!");
        }
    }
}
