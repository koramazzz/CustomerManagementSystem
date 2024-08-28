package Business;

import Dao.CustomerDao;
import Entity.Customer;

import java.util.ArrayList;

public class CustomerController {
    private final CustomerDao customerDao = new CustomerDao();

    public ArrayList<Customer> findAll(){
       return this.customerDao.findAll();
    }

}
