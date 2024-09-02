import Business.UserController;
import Core.Database;
import Core.Helper;
import Entity.User;
import View.DashBoardUI;
import View.LoginUI;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        Helper.setTheme();
        // LoginUI loginUI = new LoginUI();
        UserController userController = new UserController();
        User user = userController.findByLogin("omer@beyza.com","191121");
        DashBoardUI dashBoardUI = new DashBoardUI(user);


    }
}
