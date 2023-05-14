package View;
import BLL.ClientBLL;
import Model.Client;
import Start.ReflectionExample;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientView {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton newClientButton;
    private JButton editClientButton;
    private JButton deleteClientButton;
    private JButton viewAllClientsButton;
    private JTable table1;
    DefaultTableModel tableModel;
    private JPanel panel1;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;

    public ClientView(){
        final JFrame frame = new JFrame();
        frame.setTitle("Clients View");
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
        newClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            System.out.println("CLICK NEW CLIENT!");
            ClientBLL clientbll = new ClientBLL();
            String idInput=textField1.getText();
            int id=Integer.parseInt(idInput);
            String ageInput=textField4.getText();
            int age=Integer.parseInt(ageInput);
            clientbll.insert(new Client(textField2.getText(),id,textField3.getText(),age));
            }
        });
        editClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK EDIT CLIENT!");
                ClientBLL clientbll = new ClientBLL();
                Client c = null;
                HashMap<String, Object> fieldsUpdated = new HashMap<>();
                HashMap<String, Object> conditionField = new HashMap<>();
                if(!textField1.getText().equals(""))
                    fieldsUpdated.put("id",Integer.parseInt(textField1.getText()));
                if(!textField2.getText().equals(""))
                     fieldsUpdated.put("name",textField2.getText());
                if(!textField3.getText().equals(""))
                     fieldsUpdated.put("address",textField3.getText());
                if(!textField4.getText().equals(""))
                    fieldsUpdated.put("age",Integer.parseInt(textField4.getText()));
                if(!textField5.getText().equals(""))
                     conditionField.put("id",Integer.parseInt(textField5.getText()));
                if(!textField6.getText().equals(""))
                    conditionField.put("name",textField6.getText());
                if(!textField7.getText().equals(""))
                    conditionField.put("address",textField7.getText());
                if(!textField8.getText().equals(""))
                    conditionField.put("age",Integer.parseInt(textField8.getText()));
                clientbll.update(c,fieldsUpdated,conditionField);
            }
        });
        viewAllClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK VIEW ALL CLIENT!");
                ClientBLL clientbll = new ClientBLL();
                List<Client> c=clientbll.findAll();
                int nrColoane=Client.class.getDeclaredFields().length;
                String[] numeColoane= new String[nrColoane];
                Object[] valori= new Object[nrColoane];
                int contor=0;
                for(Client c1:c){
                    ReflectionExample.retrieveProperties(c1,numeColoane,valori);
                    if(contor==0)
                        tableModel=new DefaultTableModel(numeColoane,0);
                    tableModel.addRow(valori);
                    contor++;
                }
                table1.setModel(tableModel);
            }
        });
        deleteClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK DELETE CLIENT!");
                ClientBLL clientbll = new ClientBLL();
                HashMap<String, Object> conditionField = new HashMap<>();
                if(!textField5.getText().equals(""))
                    conditionField.put("id",Integer.parseInt(textField5.getText()));
                if(!textField6.getText().equals(""))
                    conditionField.put("name",textField6.getText());
                if(!textField7.getText().equals(""))
                    conditionField.put("address",textField7.getText());
                if(!textField8.getText().equals(""))
                    conditionField.put("age",Integer.parseInt(textField8.getText()));
                clientbll.delete(conditionField);
            }
        });
        frame.setVisible(true);
    }
}
