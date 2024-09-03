package View;

import Business.BasketController;
import Business.CartController;
import Business.ProductController;
import Core.Helper;
import Entity.Basket;
import Entity.Cart;
import Entity.Customer;
import Entity.Product;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CartUI extends JFrame{
    private final CartController cartController;
    private BasketController basketController;
    private ProductController productController;
    private JPanel container;
    private JLabel lbl_title;
    private JLabel lbl_customer_name;
    private JTextField fld_cart_date;
    private JTextArea tarea_cart_note;
    private JButton btn_cart;
    private Customer customer;

    public CartUI(Customer customer){

        this.customer = customer;
        this.basketController = new BasketController();
        this.productController = new ProductController();
        this.cartController = new CartController();


        this.add(container);
        this.setTitle("Add/Edit Product");
        this.setSize(300, 350);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        if (customer.getId() == 0){
            Helper.showMsg("Please select a valid customer");
            dispose();
        }

        ArrayList<Basket> baskets = this.basketController.findAll();
        if (baskets.size() == 0){
            Helper.showMsg("Please add product to the basket");
            dispose();
        }

        this.lbl_customer_name.setText("Customer : " + this.customer.getName());


        btn_cart.addActionListener(e -> {

            if (Helper.isFieldEmpty(this.fld_cart_date)){ Helper.showMsg("fill"); }
            else {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                for (Basket basket : baskets) {

                    if (basket.getProduct().getStock() <= 0 ) continue;

                    Cart cart = new Cart();
                    cart.setCustomer_id(this.customer.getId());
                    cart.setProduct_id(basket.getProductId());
                    cart.setPrice(basket.getProduct().getPrice());
                    cart.setDate(LocalDate.parse(this.fld_cart_date.getText(),formatter));
                    cart.setNote(this.tarea_cart_note.getText());

                    this.cartController.save(cart);

                    Product updatedStockProduct = basket.getProduct();
                    updatedStockProduct.setStock(updatedStockProduct.getStock() - 1);
                    this.productController.update(updatedStockProduct);
                }
                this.basketController.clear();
                Helper.showMsg("done");
                dispose();
            }
        });
    }


    private void createUIComponents() throws ParseException {
        this.fld_cart_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fld_cart_date.setText(formatter.format(LocalDate.now()));
    }
}
