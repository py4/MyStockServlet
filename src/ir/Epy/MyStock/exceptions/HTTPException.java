package ir.Epy.MyStock.exceptions;

import com.sun.net.httpserver.HttpExchange;

/**
 * Created customer_id py4_ on 2/17/16.
 */
public class HTTPException extends Exception {
    public int response_code;
    public String msg;

    public HTTPException(int response_code, String msg) {
        this.response_code = response_code;
        this.msg = msg;
    }
}
