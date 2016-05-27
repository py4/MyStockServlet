package ir.Epy.MyStock.models;

import ir.Epy.MyStock.Constants;

/**
 * Created by py4_ on 5/27/16.
 */
public class Role {
    public String username;
    public String role_name;

    public Role(String username, String role_name) {
        this.username = username;
        this.role_name = role_name;
    }

    public static boolean valid_role(String role_name) {
        return (role_name.equals(Constants.CUSTOMER_ROLE) || role_name.equals(Constants.ACCOUNTANT_ROLE) || role_name.equals(Constants.ADMIN_ROLE) || role_name.equals(Constants.OWNER_ROLE));
    }
}
