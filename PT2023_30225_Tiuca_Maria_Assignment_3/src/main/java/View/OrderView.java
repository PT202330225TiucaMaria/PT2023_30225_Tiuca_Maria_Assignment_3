package View;
import BLL.ClientBLL;
import BLL.OrderBLL;
import BLL.ProductBLL;
import Model.Client;
import Model.Order;
import Model.Product;
import Start.ReflectionExample;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class OrderView {
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField1;
    private JButton createOrderButton;
    private JButton updateOrderButton;
    private JButton deleteOrderButton;
    private JButton viewAllOrdesButton;
    private JTable table1;
    DefaultTableModel tableModel;
    private JPanel panel1;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;

    public OrderView(){
        final JFrame frame = new JFrame();
        frame.setTitle("Order View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        textField1.setVisible(true);
        textField1.setEditable(true);
        textField2.setVisible(true);
        textField2.setEditable(true);
        textField3.setVisible(true);
        textField3.setEditable(true);
        textField4.setVisible(true);
        textField4.setEditable(true);
        comboBox1.setVisible(true);
        comboBox2.setVisible(true);
        ProductBLL productbll=new ProductBLL();
        ClientBLL clientbll=new ClientBLL();
        List<Client> clients=clientbll.findAll();
        List<Product> products=productbll.findAll();
        for(Client c:clients){
            comboBox1.addItem(c.getName());
            comboBox3.addItem(c.getId());
        }
        for(Product c:products){
            comboBox2.addItem(c.getName());
            comboBox4.addItem(c.getId());
        }
        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK NEW ORDER!");
                OrderBLL orderbll = new OrderBLL();
                String quantityInput=textField1.getText();
                int quantity=Integer.parseInt(quantityInput);
                ClientBLL clientFind=new ClientBLL();
                ProductBLL productFind=new ProductBLL();
                Object idClient = comboBox3.getSelectedItem();
                Object idProduct = comboBox4.getSelectedItem();
                if(idProduct!=null &&idClient!=null) {
                    int id = Integer.parseInt(idClient.toString());
                    Client c = clientFind.findClientById(id);
                    id = Integer.parseInt(idProduct.toString());
                    Product p = productFind.findProductById(id);
                    if (quantity < p.getQuantity()) {
                        List<Order> o=orderbll.findAll();
                        id=generateUniqueRandomId(o);
                        Order order = new Order(id,c.getName(), p.getName(),c.getAddress(), quantity,p.getPrice());
                        orderbll.insert(order);
                        p.actualizareStoc(quantity);
                        HashMap<String,Object> fieldsUpdated=new HashMap<>();
                        fieldsUpdated.put("quantity",p.getQuantity());
                        HashMap<String,Object> conditionField=new HashMap<>();
                        conditionField.put("name",p.getName());
                        productbll.update(p,fieldsUpdated,conditionField);
                    }
                }
            }
        });
        updateOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK UPDATE ORDER!");
                OrderBLL orderbll = new OrderBLL();
                ProductBLL productbll = new ProductBLL();
                HashMap<String, Object> conditionField = new HashMap<>();
                HashMap<String, Object> updateField = new HashMap<>();
                int id=0,quantity=0;
                if(!textField2.getText().equals("")) {
                    id=Integer.parseInt(textField2.getText());
                    conditionField.put("id", Integer.parseInt(textField2.getText()));
                }
                if(!textField3.getText().equals(""))
                    conditionField.put("clientAddress",textField3.getText());
                if(!textField4.getText().equals("")) {
                    quantity=Integer.parseInt(textField4.getText());
                    conditionField.put("productQuantity", Integer.parseInt(textField4.getText()));
                }
                Order o=orderbll.findOrderById(id);

                Product p=productbll.findProductByName(o.getProductName());
                HashMap<String,Object> fieldsUpdated=new HashMap<>();
                fieldsUpdated.put("quantity",p.getQuantity()+(o.getProductQuantity()-quantity));
                HashMap<String,Object> conditionFieldUpdate=new HashMap<>();
                conditionFieldUpdate.put("name",o.getProductName());
                productbll.update(p,fieldsUpdated,conditionFieldUpdate);

                orderbll.update(o,updateField,conditionField);
            }
        });
        viewAllOrdesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK VIEW ALL ORDERS!");
                OrderBLL orderbll = new OrderBLL();
                List<Order> c=orderbll.findAll();
                int nrColoane=Order.class.getDeclaredFields().length;
                String[] numeColoane= new String[nrColoane];
                Object[] valori= new Object[nrColoane];
                int contor=0;
                for(Order c1:c){
                    ReflectionExample.retrieveProperties(c1,numeColoane,valori);
                    if(contor==0)
                        tableModel=new DefaultTableModel(numeColoane,0);
                    tableModel.addRow(valori);
                    contor++;
                }
                table1.setModel(tableModel);
            }
        });
        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK DELETE ORDER!");
                OrderBLL orderbll = new OrderBLL();
                ProductBLL productbll = new ProductBLL();
                HashMap<String, Object> conditionField = new HashMap<>();
                if(!textField2.getText().equals(""))
                    conditionField.put("id",Integer.parseInt(textField2.getText()));
                Order o=orderbll.findOrderById(Integer.parseInt(textField2.getText()));
                Product p=productbll.findProductByName(o.getProductName());
                HashMap<String,Object> fieldsUpdated=new HashMap<>();
                fieldsUpdated.put("quantity",p.getQuantity()+o.getProductQuantity());
                HashMap<String,Object> conditionFieldUpdate=new HashMap<>();
                conditionFieldUpdate.put("name",o.getProductName());
                productbll.update(p,fieldsUpdated,conditionFieldUpdate);
                orderbll.delete(conditionField);
            }
        });
        frame.setVisible(true);
    }
    private int generateUniqueRandomId(List<Order> o) {
        Random random = new Random();
        int min = 0;
        int max = 999999;
        int id = random.nextInt(max - min + 1) + min;
        for(Order order:o){
            if(order.getId()==id)
                generateUniqueRandomId(o);
        }
        return id;
    }
}
