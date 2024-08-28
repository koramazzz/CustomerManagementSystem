package View;

import Business.UserController;
import Core.Helper;
import Entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame{
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_title;
    private JTextField fld_mail;
    private JButton btn_logIn;
    private JPanel pnl_bottom;
    private JLabel lbl_mail;
    private JLabel lbl_password;
    private JPasswordField fld_password;
    private UserController userController;

    public LoginUI(){

        this.userController = new UserController();

        this.add(container);
        this.setTitle("Customer Management System");
        this.setSize(400,400);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        this.btn_logIn.addActionListener(e -> {

            JTextField[] checkList = {this.fld_password, this.fld_mail};
            if (Helper.isFieldListEmpty(checkList)){
                Helper.showMsg("fill");
            }
            else if (!Helper.isMailValid(this.fld_mail.getText())){
                Helper.showMsg("Please enter a valid mail address !");
            }
            else {
                User user = this.userController.findByLogin(this.fld_mail.getText(), this.fld_password.getText());
                if (user == null) {
                    Helper.showMsg("User cannot found based on the information entered!!");
                }
                else {
                    this.dispose();
                    DashBoardUI dashBoardUI = new DashBoardUI(user);
                }
            }
        });
    }
}
