package Start;
import java.lang.reflect.Field;
public class ReflectionExample {
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


