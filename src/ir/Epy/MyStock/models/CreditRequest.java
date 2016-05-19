package ir.Epy.MyStock.models;

import ir.Epy.MyStock.DAOs.CreditRequestDAO;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.InvalidCreditValueRequest;

import java.sql.SQLException;

import static ir.Epy.MyStock.Constants.AcceptStatus;

/**
 * Created customer_id esihaj on 4/7/16.
 */
/* 0: pending, 1: accepted, 2: reject */

public class CreditRequest {
    private String id;
    private String customer_id;
    private int amount;
    private int status;


    public String getId() {
        return id;
    }
    public String getCustomerId() {
        return customer_id;
    }

    public int getAmount() {
        return amount;
    }

    public int getStatus() {
        return status;
    }

    public CreditRequest(String req_id, String user, int amount, int status) {
        this.id = req_id;
        this.customer_id = user;
        this.amount = amount;
        this.status = status;
    }

    //@Haji: is this OK?
    public void process_request(int status) throws SQLException, CustomerNotFoundException {
        this.status = status;
        CreditRequestDAO.I().update(this);
        if(this.status == AcceptStatus) {
            Customer c = CustomerDAO.I().find(customer_id);
            c.increase_deposit(amount);
            CustomerDAO.I().update(c);
        }
    }



    public String log()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append('[');
        sb.append(status);
        sb.append("]: customer_id [");
        sb.append(customer_id);
        sb.append("] requested to ");
        sb.append("[");
        sb.append(amount);
        sb.append("]");
        return sb.toString();
    }
}
