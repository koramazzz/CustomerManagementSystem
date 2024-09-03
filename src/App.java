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
        LoginUI loginUI = new LoginUI();
    }
}
