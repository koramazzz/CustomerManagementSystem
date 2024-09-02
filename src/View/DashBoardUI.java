package View;

import Business.CustomerController;
import Business.ProductController;
import Core.Helper;
import Core.Item;
import Entity.Customer;
import Entity.Product;
import Entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class DashBoardUI extends JFrame {
    private JPanel container;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JTabbedPane tab_menu;
    private JPanel pnl_customer;
    private JScrollPane scrl_customer;
    private JTable tbl_customer;
    private JPanel pnl_customer_filter;
    private JTextField fld_f_customer_name;
    private JComboBox<Customer> cmb_f_customer_type;
    private JButton btn_customer_filter;
    private JButton btn_customer_filter_reset;
    private JButton btn_customer_new;
    private JLabel lbl_f_customer_name;
    private JLabel lbl_f_customer_type;
    private JPanel pnl_product;
    private JScrollPane scr_product;
    private JTable tbl_product;
    private JPanel pnl_product_filter;
    private JTextField fld_f_product_name;
    private JTextField fld_f_product_code;
    private JComboBox<Item> cmb_f_product_stock;
    private JButton btn_product_filter;
    private JButton btn_product_filter_reset;
    private JButton btn_product_new;
    private JLabel lbl_f_product_name;
    private JLabel lbl_f_product_code;
    private JLabel lbl_f_product_stock;
    private User user;
    private CustomerController customerController;
    private ProductController productController;
    private DefaultTableModel mdl_customer_table = new DefaultTableModel();
    private DefaultTableModel mdl_product_table = new DefaultTableModel();
    private JPopupMenu popup_product = new JPopupMenu();
    private JPopupMenu popup_customer = new JPopupMenu();


    public DashBoardUI(User user){

        this.user = user;
        this.customerController = new CustomerController();
        this.productController = new ProductController();

        if (user == null){
            Helper.showMsg("error");
            dispose();
        }

        this.add(container);
        this.setTitle("Customer Management System");
        this.setSize(1000,500);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        this.lbl_welcome.setText("Welcome : " + this.user.getName());

        btn_logout.addActionListener(e -> {
            dispose();
            LoginUI loginUI = new LoginUI();
        });


        // Customer Tab
        loadCustomerTable(null);
        loadCustomerPopupMenu();
        loadCustomerButtonEvent();
        this.cmb_f_customer_type.setModel(new DefaultComboBoxModel(Customer.TYPE.values()));
        this.cmb_f_customer_type.setSelectedItem(null);

        
        //Product Tab
        loadProductTable(null);
        loadProductPopupMenu();
        loadProductButtonEvent();
        this.cmb_f_product_stock.addItem(new Item(1,"In stock"));
        this.cmb_f_product_stock.addItem(new Item(2,"Out of stock"));
        this.cmb_f_product_stock.setSelectedItem(null);


    }

    private void loadProductButtonEvent() {

        this.btn_product_new.addActionListener(e -> {
            ProductUI productUI = new ProductUI(new Product());
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                }
            });


        });

        this.btn_product_filter.addActionListener(e -> {
            ArrayList<Product> filteredProducts = this.productController.filter(
                    this.fld_f_product_name.getText(),
                    this.fld_f_product_code.getText(),
                    (Item) this.cmb_f_product_stock.getSelectedItem()
            );
            loadProductTable(filteredProducts);
        });

        this.btn_product_filter_reset.addActionListener(e -> {
            this.fld_f_product_code.setText(null);
            this.fld_f_product_name.setText(null);
            this.cmb_f_product_stock.setSelectedItem(null);
            loadProductTable(null);
        });

    }
    private void loadProductPopupMenu() {
        this.tbl_product.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_product.rowAtPoint(e.getPoint());
                tbl_product.setRowSelectionInterval(selectedRow,selectedRow);
            }
        });

        this.popup_product.add("Update").addActionListener(e -> {
            int selectId = Integer.parseInt(this.tbl_product.getValueAt(this.tbl_product.getSelectedRow(),0).toString());
            ProductUI productUI = new ProductUI(this.productController.getById(selectId));
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                }
            });
        });

        this.popup_product.add("Delete").addActionListener(e ->{
            int selectId = Integer.parseInt(this.tbl_product.getValueAt(this.tbl_product.getSelectedRow(),0).toString());

            if (Helper.confirm("sure")) {
                if (this.productController.delete(selectId)) {
                    Helper.showMsg("done");
                    loadProductTable(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });;

        this.tbl_product.setComponentPopupMenu(this.popup_product);
    }
    private void loadProductTable(ArrayList<Product> products) {
        Object[] columnProduct = {"ID", "Product Name","Product Code","Price","Stock"};

        if (products == null){
            products = this.productController.findAll();
        }

        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_product.getModel();
        clearModel.setRowCount(0);

        this.mdl_product_table.setColumnIdentifiers(columnProduct);

        for (Product product : products){
            Object[] rowObject = {product.getId(),product.getName(),
                    product.getCode(),product.getPrice(),product.getStock()};
            this.mdl_product_table.addRow(rowObject);
        }

        this.tbl_product.setModel(mdl_product_table);
        this.tbl_product.getTableHeader().setReorderingAllowed(false);
        this.tbl_product.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_product.setEnabled(false);

    }
    private void loadCustomerButtonEvent() {

        this.btn_customer_new.addActionListener(e -> {
            CustomerUI customerUI = new CustomerUI(new Customer());
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);
                }
            });
        });

        this.btn_customer_filter.addActionListener(e -> {
            ArrayList<Customer> filteredCustomers = this.customerController.filter(
                    this.fld_f_customer_name.getText(),
                    (Customer.TYPE) this.cmb_f_customer_type.getSelectedItem()
            );
            loadCustomerTable(filteredCustomers);
        });

        this.btn_customer_filter_reset.addActionListener(e -> {
            this.cmb_f_customer_type.setSelectedItem(null);
            this.fld_f_customer_name.setText(null);
            loadCustomerTable(null);

        });
    }
    private void loadCustomerPopupMenu() {

        this.tbl_customer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_customer.rowAtPoint(e.getPoint());
                tbl_customer.setRowSelectionInterval(selectedRow,selectedRow);
            }
        });

        this.popup_customer.add("Update").addActionListener(e ->{
            int selectId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(),0).toString());
            Customer editedCustomer = this.customerController.getById(selectId);
            CustomerUI customerUI = new CustomerUI(editedCustomer);
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);
                }
            });
        });

        this.popup_customer.add("Delete").addActionListener(e ->{
            int selectId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(),0).toString());
            if (Helper.confirm("sure")) {

                if (this.customerController.delete(selectId)) {
                    Helper.showMsg("done");
                    loadCustomerTable(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });;

        this.tbl_customer.setComponentPopupMenu(this.popup_customer);

    }
    private void loadCustomerTable(ArrayList<Customer> customers) {
        Object[] columnCustomer = {"ID", "Customer Name","Type","Phone","Mail","Address"};

        if (customers == null){
            customers = this.customerController.findAll();
        }

        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_customer.getModel();
        clearModel.setRowCount(0);

        this.mdl_customer_table.setColumnIdentifiers(columnCustomer);

        for (Customer customer : customers){
            Object[] rowObject = {customer.getId(),customer.getName(),
                    customer.getType(),customer.getPhone(),customer.getMail(),customer.getAddress()};
            this.mdl_customer_table.addRow(rowObject);
        }

        this.tbl_customer.setModel(mdl_customer_table);
        this.tbl_customer.getTableHeader().setReorderingAllowed(false);
        this.tbl_customer.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_customer.setEnabled(false);

    }
}
