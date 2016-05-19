package ir.Epy.MyStock.models;

import org.omg.CORBA.TRANSACTION_MODE;

/**
 * Created customer_id esihaj on 4/7/16.
 */
public class CreditRequest {
    public enum TransactionStatus {
        PENDING, ACCEPTED, REJECTED
    }
    private String req_id;
    private String user;
    private int amount;
    private boolean is_deposit;
    private TransactionStatus status;

    public boolean is_deposit() {
        return is_deposit;
    }

    public String getReq_id() {
        return req_id;
    }
    public String getUser() {
        return user;
    }

    public int getAmount() {
        return amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public CreditRequest(String req_id, String user, int amount, boolean is_deposit) {
        this.req_id = req_id;
        this.user = user;
        this.amount = amount;
        this.is_deposit = is_deposit;
        this.status = TransactionStatus.PENDING;
    }

    public void process_request(TransactionStatus status) {
        this.status = status;
    }

    public String log()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(req_id);
        sb.append('[');
        sb.append(status.toString());
        sb.append("]: user [");
        sb.append(user);
        sb.append("] requested to ");
        sb.append (is_deposit ? "deposit" : "withdraw");
        sb.append("[");
        sb.append(amount);
        sb.append("]");
        return sb.toString();
    }
}
