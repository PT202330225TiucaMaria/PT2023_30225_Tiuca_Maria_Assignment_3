package BLL.Validator;
import Model.Client;
public class ClientAgeValidator implements Validator<Client> {
    private static final int MIN_AGE=13;
    private static final int MAX_AGE=60;
    public void validate(Client t){
      if(t.getAge()<MIN_AGE||t.getAge()>MAX_AGE){
          throw new IllegalArgumentException("The Client Age limit is not respected!");
        }
    }
}
