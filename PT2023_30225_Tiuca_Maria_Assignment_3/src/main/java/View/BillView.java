package View;
import BLL.BillBLL;
import Model.Bill;
import Start.ReflectionExample;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;
public class BillView {
    private JPanel panel1;
    private JTable table1;
    DefaultTableModel tableModel;
    public BillView(){
        final JFrame frame = new JFrame();
        frame.setTitle("Bill View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);

        BillBLL billbll = new BillBLL();
        List<Bill> c=billbll.findAll();
        int nrColoane=Bill.class.getDeclaredFields().length;
        String[] numeColoane= new String[nrColoane];
        Object[] valori= new Object[nrColoane];
        int i=0;
        for(Field field:c.getClass().getDeclaredFields()){
            numeColoane[i]=field.getName();
            i++;
        }
        for(Bill c1:c){
            ReflectionExample.retrieveProperties(c1,numeColoane,valori);
            tableModel.addRow(valori);
        }
        table1.setModel(tableModel);
        frame.pack();
        frame.setVisible(true);
    }
}
