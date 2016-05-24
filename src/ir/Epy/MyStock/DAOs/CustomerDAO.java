package ir.Epy.MyStock.DAOs;

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

    private CustomerDAO() {
        TABLE_NAME = "customers";
        db_fields = new ArrayList<String> (Arrays.asList("ID", "NAME", "FAMILY", "DEPOSIT"));
        db_pks = new ArrayList<String>(Arrays.asList("ID"));
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

    public void create(String id, String name, String family, Integer deposit) throws SQLException, CustomerAlreadyExistsException {
        try {
            super.create(id, name, family, deposit);
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