package View;
import BLL.BillBLL;
import BLL.ClientBLL;
import BLL.OrderBLL;
import BLL.ProductBLL;
import Model.Bill;
import Model.Client;
import Model.Order;
import Model.Product;
import Start.ReflectionExample;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
public class OrderView {
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField1;
    private JButton createOrderButton;
    private JButton deleteOrderButton;
    private JButton viewAllOrdesButton;
    private JTable table1;
    DefaultTableModel tableModel;
    private JPanel panel1;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JTextField textField2;
    private JTextField textField3;
    private JButton viewAllBillsButton;
    private JButton viewBillByIdButton;

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
        textField3.setEditable(false);
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
                BillBLL billbll=new BillBLL();
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
                    if (quantity <= p.getQuantity()) {
                        List<Order> o=orderbll.findAll();
                        id=generateUniqueRandomId(o);
                        textField3.setText("PRODUSE  DISPONIBILE IN STOC");
                        Order order = new Order(id,c,p,quantity);
                        Bill bill=new Bill(order.getId(),c.getName(),c.getAddress(),p.getName(),p.getId(),order.getProductQuantity(), order.getPrice());
                        orderbll.insert(order);
                        billbll.insert(bill);
                        p.actualizareStoc(quantity);
                        HashMap<String,Object> fieldsUpdated=new HashMap<>();
                        fieldsUpdated.put("quantity",p.getQuantity());
                        HashMap<String,Object> conditionField=new HashMap<>();
                        conditionField.put("id",p.getId());
                        productbll.update(p,fieldsUpdated,conditionField);
                        }
                    else
                        textField3.setText("NU EXISTA ATATEA PRODUSE IN STOC");
                    }
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
                tableModel=new DefaultTableModel(numeColoane,0);
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
                Product p=productbll.findProductById(o.getProductId());
                HashMap<String,Object> fieldsUpdated=new HashMap<>();
                fieldsUpdated.put("quantity",p.getQuantity()+o.getProductQuantity());
                HashMap<String,Object> conditionFieldUpdate=new HashMap<>();
                conditionFieldUpdate.put("id",p.getId());
                productbll.update(p,fieldsUpdated,conditionFieldUpdate);
                orderbll.delete(conditionField);
            }
        });
        viewAllBillsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK VIEW ALL BILLS!");
                int id=-1;
                new BillView(id);
            }
        });
        viewBillByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK VIEW ALL BILLS!");
                int id=-1;
                if(!textField2.getText().equals(""))
                     id=Integer.parseInt(textField2.getText());
                new BillView(id);
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
