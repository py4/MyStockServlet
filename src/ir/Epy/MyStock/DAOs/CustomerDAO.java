package ir.Epy.MyStock.DAOs;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DBConnection;
import ir.Epy.MyStock.Mappers.CustomerMapper;
import ir.Epy.MyStock.exceptions.CustomerAlreadyExistsException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomerDAO extends DAO {

    private static CustomerDAO ourInstance = new CustomerDAO();
    private int maxId = 0;
    private int default_deposit = 1000;
    private String default_role = Constants.USER_ROLE_CUSTOMER;

    private CustomerDAO() {
        TABLE_NAME = "customers";
        db_fields = new ArrayList<String> (Arrays.asList("ID", "USERNAME", "PASSWORD", "ROLE", "NAME", "FAMILY", "DEPOSIT"));
        db_pks = new ArrayList<String>(Arrays.asList("ID"));

        ResultSet rs = null;
        try {
            rs = DBConnection.createStatement().executeQuery("select max(ID) as max_id from " + TABLE_NAME);
            if (rs.next()) {
                maxId = rs.getInt("max_id");
                System.out.println("customer new max id = " + rs.getInt("max_id"));
            }
            else System.out.println("couldnt get max id ");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(Constants.DB_EXIT_CODE);
        }
    }


    public static CustomerDAO I() {
        return ourInstance;
    }

    public Customer find(String id) throws CustomerNotFoundException, SQLException {

        ResultSet rs = super.find(id);
        if (rs.next())
            return CustomerMapper.mapRow(rs);
        else
            throw new CustomerNotFoundException();
    }

    public void create(String username, String password, String name, String family) throws SQLException, CustomerAlreadyExistsException {
        try {
            maxId++;
            super.create("" + maxId, username, password, default_role , name, family, default_deposit);

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new CustomerAlreadyExistsException();
        }
    }

    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet rs = super.all();
        ArrayList<Customer> cs = new ArrayList<>();
        while(rs.next())
            cs.add(CustomerMapper.mapRow(rs));
        return cs;
    }

}