package View;
import BLL.BillBLL;
import Model.Bill;
import Start.ReflectionExample;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
public class BillView {
    private JPanel panel1;
    private JTable table1;
    DefaultTableModel tableModel;
    public BillView(int id){
        final JFrame frame = new JFrame();
        frame.setTitle("Bill View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        BillBLL billbll = new BillBLL();
        List<Bill> c;
        if(id==-1)
             c=billbll.findAll();
        else
             c=billbll.findByIdBill(id);
        int nrColoane=Bill.class.getDeclaredFields().length;
        String[] numeColoane= new String[nrColoane];
        Object[] valori= new Object[nrColoane];
        int contor=0;
        tableModel=new DefaultTableModel(numeColoane,0);
        for(Bill c1:c){
            ReflectionExample.retrieveProperties(c1,numeColoane,valori);
            if(contor==0)
                tableModel=new DefaultTableModel(numeColoane,0);
            tableModel.addRow(valori);
            contor++;
        }
        table1.setModel(tableModel);
        frame.setVisible(true);
    }
}
