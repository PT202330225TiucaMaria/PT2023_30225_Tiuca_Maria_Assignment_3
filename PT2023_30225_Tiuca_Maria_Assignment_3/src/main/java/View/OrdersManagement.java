package View;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class OrdersManagement {
    private JPanel panel1;
    private JButton clientManagementButton;
    private JButton productManagementButton;
    private JButton orderManagementButton;
    public OrdersManagement(){
        final JFrame frame = new JFrame();
        frame.setTitle("Order Managemet View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        clientManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK CLIENT MANAGEMENT!");
                new ClientView();
            }
        });
        productManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK PRODUCT MANAGEMENT!");
                new ProductView();
            }
        });
        orderManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLICK ORDER MANAGEMENT!");
                new OrderView();
            }
        });
        frame.setVisible(true);
    }
}
