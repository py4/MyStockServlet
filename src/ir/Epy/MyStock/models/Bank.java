package ir.Epy.MyStock.models;

import ir.Epy.MyStock.Database;
import ir.Epy.MyStock.exceptions.CreditRequestNotFoundException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.InvalidCreditValueRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by esihaj on 4/7/16.
 */
public class Bank {
    HashMap<String, CreditRequest> requests = new HashMap<>();
    HashMap<String, CreditRequest> history = new HashMap<>();
    int req_id_counter = 0;

    public Collection<CreditRequest> getRequests() {
        return requests.values();
    }

    public void add_request(String customer_id, int credit, boolean is_deposit) throws InvalidCreditValueRequest, CustomerNotFoundException {
        Database.get_obj().get_customer(customer_id); // just to check if it exists
        if (credit < 0)
            throw new InvalidCreditValueRequest();
        String req_id = gen_next_id();
        requests.put(req_id, new CreditRequest(req_id, customer_id, credit, is_deposit));
    }

    public void process_request(String req_id, CreditRequest.TransactionStatus status) throws CustomerNotFoundException, CreditRequestNotFoundException {
        CreditRequest rq = requests.get(req_id);
        if(rq == null)
            throw new CreditRequestNotFoundException();
        if (status == CreditRequest.TransactionStatus.PENDING)
            return;
        else rq.process_request(status);

        if (status == CreditRequest.TransactionStatus.ACCEPTED) {
            Customer c = Database.get_obj().get_customer(rq.getUser());
            if (rq.is_deposit())
                c.increase_deposit(rq.getAmount());
            else c.decrease_deposit(rq.getAmount());
        }

        //move to history
        requests.remove(req_id);
        history.put(req_id, rq);

    }

    private String gen_next_id(){
        req_id_counter += 1;
        return  "" +req_id_counter;
    }

}
