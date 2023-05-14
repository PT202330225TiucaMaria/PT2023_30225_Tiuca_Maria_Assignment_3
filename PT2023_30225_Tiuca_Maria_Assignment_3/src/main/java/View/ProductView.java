package View;
import BLL.ProductBLL;
import Model.Product;
import Start.ReflectionExample;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class ProductView {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTable table1;
    DefaultTableModel tableModel;
    private JButton newProductButton;
    private JButton editProductButton;
    private JButton deleteProductButton;
    private JButton viewAllProductsButton;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JPanel panel1;

    public ProductView(){
        final JFrame frame = new JFrame();
        frame.setTitle("Products View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        textField1.setVisible(true);
        textField2.setVisible(true);
        textField3.setVisible(true);
        textField4.setVisible(true);
        textField1.setEditable(true);
        textField2.setEditable(true);
        textField3.setEditable(true);
        textField4.setEditable(true);
        textField5.setVisible(true);
        textField6.setVisible(true);
        textField7.setVisible(true);
        textField8.setVisible(true);
        textField5.setEditable(true);
        textField6.setEditable(true);
        textField7.setEditable(true);
        textField8.setEditable(true);
        newProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK NEW PRODUCT!");
                ProductBLL productbll = new ProductBLL();
                String idInput=textField1.getText();
                int id=Integer.parseInt(idInput);
                String quantityInput=textField3.getText();
                int quantity=Integer.parseInt(quantityInput);
                String priceInput=textField4.getText();
                double price=Double.parseDouble(priceInput);
                productbll.insert(new Product(textField2.getText(),id,price,quantity));
            }
        });
        editProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK EDIT PRODUCT!");
                ProductBLL productbll = new ProductBLL();
                Product c = null;
                HashMap<String, Object> fieldsUpdated = new HashMap<>();
                HashMap<String, Object> conditionField = new HashMap<>();
                if(!textField1.getText().equals(""))
                    fieldsUpdated.put("id",Integer.parseInt(textField1.getText()));
                if(!textField2.getText().equals(""))
                    fieldsUpdated.put("name",textField2.getText());
                if(!textField3.getText().equals(""))
                    fieldsUpdated.put("quantity",Integer.parseInt(textField3.getText()));
                if(!textField4.getText().equals(""))
                    fieldsUpdated.put("price",Double.parseDouble(textField4.getText()));
                if(!textField5.getText().equals(""))
                    conditionField.put("id",Integer.parseInt(textField5.getText()));
                if(!textField6.getText().equals(""))
                    conditionField.put("name",textField6.getText());
                if(!textField7.getText().equals(""))
                    conditionField.put("quantity",Integer.parseInt(textField7.getText()));
                if(!textField8.getText().equals(""))
                    conditionField.put("price",Double.parseDouble(textField8.getText()));
                productbll.update(c,fieldsUpdated,conditionField);
            }
        });
        viewAllProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK VIEW ALL PRODUCT!");
                ProductBLL productbll = new ProductBLL();
                List<Product> c=productbll.findAll();
                int nrColoane=Product.class.getDeclaredFields().length;
                String[] numeColoane= new String[nrColoane];
                Object[] valori= new Object[nrColoane];
                int contor=0;
                for(Product c1:c){
                    ReflectionExample.retrieveProperties(c1,numeColoane,valori);
                    if(contor==0)
                        tableModel=new DefaultTableModel(numeColoane,0);
                    tableModel.addRow(valori);
                    contor++;
                }
                table1.setModel(tableModel);
            }
        });
        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK DELETE PRODUCT!");
                ProductBLL productbll = new ProductBLL();
                HashMap<String, Object> conditionField = new HashMap<>();
                if(!textField5.getText().equals(""))
                    conditionField.put("id",Integer.parseInt(textField5.getText()));
                if(!textField6.getText().equals(""))
                    conditionField.put("name",textField6.getText());
                if(!textField7.getText().equals(""))
                    conditionField.put("quantity",Integer.parseInt(textField7.getText()));
                if(!textField8.getText().equals(""))
                    conditionField.put("price",Double.parseDouble(textField8.getText()));
                productbll.delete(conditionField);
            }
        });
        frame.setVisible(true);
    }
}
