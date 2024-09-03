package Business;

import Dao.BasketDao;
import Entity.Basket;
import Entity.Customer;

import java.util.ArrayList;

public class BasketController {
    private final BasketDao basketDao = new BasketDao();

    public boolean save(Basket basket){
        return this.basketDao.save(basket);
    }

    public ArrayList<Basket> findAll(){
        return this.basketDao.findAll();
    }

    public boolean clear() { return this.basketDao.clear(); }
}
