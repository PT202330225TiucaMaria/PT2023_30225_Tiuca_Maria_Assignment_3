package Start;
import java.lang.reflect.Field;
/**
 * The ReflectionExample class provides methods for retrieving properties using reflection.
 */
public class ReflectionExample {
    /**
     * Retrieves the properties of an object using reflection and populates the provided arrays with column names and values.
     *
     * @param object     the object whose properties are to be retrieved
     * @param columNames an array to store the column names
     * @param values     an array to store the property values
     */
    public static void retrieveProperties(Object object,String[] columNames,Object[] values) {
        int i=0;
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
                columNames[i]=field.getName();
                values[i]=value;
                i++;
                //System.out.println(field.getName() + "=" + value);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}


