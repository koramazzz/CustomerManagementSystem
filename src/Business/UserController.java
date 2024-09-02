package Business;

import Core.Helper;
import Dao.UserDao;
import Entity.User;

public class UserController {
    private final UserDao userDao = new UserDao();

    public User findByLogin(String mail, String password){
        if (!Helper.isMailValid(mail)) return null;
        return this.userDao.findByLogin(mail, password);
    }
}
