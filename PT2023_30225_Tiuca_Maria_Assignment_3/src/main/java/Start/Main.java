package Start;
import View.ClientView;
import View.OrderView;
import View.ProductView;
import java.sql.SQLException;
import java.util.logging.Logger;
public class Main {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws SQLException {
        new ClientView();
        new ProductView();
        new OrderView();
    }
}
