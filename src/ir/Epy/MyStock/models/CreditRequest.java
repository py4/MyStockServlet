package ir.Epy.MyStock.models;

import ir.Epy.MyStock.DAOs.CreditRequestDAO;
import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;

import java.sql.SQLException;

import static ir.Epy.MyStock.Constants.AcceptStatus;

/**
 * Created customer_id esihaj on 4/7/16.
 */
/* 0: pending, 1: accepted, 2: reject */

public class CreditRequest {
    public int id;
    public String customer_id;
    public int amount;
    public int status;
    public boolean is_deposit;


    public int getId() {
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

    public CreditRequest(int req_id, String user, int amount, int status, boolean is_deposit) {
        this.id = req_id;
        this.customer_id = user;
        this.amount = amount;
        this.status = status;
        this.is_deposit = is_deposit;
    }

    //@Haji: is this OK?
    public void process_request(int status) throws SQLException, CustomerNotFoundException {
        this.status = status;
        CreditRequestDAO.I().update(this);
        if(this.status == AcceptStatus) {
            Customer c = CustomerDAO.I().find(customer_id);
            if(is_deposit)
                c.increase_deposit(amount);
            else c.decrease_deposit(amount);
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
        if(is_deposit)
            sb.append("deposit ");
        else sb.append("withdraw ");
        sb.append("[");
        sb.append(amount);
        sb.append("]");
        return sb.toString();
    }
}
