package ir.Epy.MyStock.DAOs;

import ir.Epy.MyStock.DBConnection;
import ir.Epy.MyStock.Mappers.CustomerMapper;
import ir.Epy.MyStock.exceptions.CustomerAlreadyExistsException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.models.Customer;

import java.sql.*;

public class CustomerDAO {

    public static void deleteCustomers() throws SQLException {
        Statement st = DBConnection.createStatement();
        st.executeQuery("DELETE FROM customer");
    }
    public static Customer findCustomer(String id) throws CustomerNotFoundException, SQLException {
        ResultSet rs = null;
        PreparedStatement ps = DBConnection.prepareStatement("SELECT * FROM customers c WHERE c.c_id = ?");
        ps.setString(1, id);
        rs = ps.executeQuery();
        if (rs.next())
            return CustomerMapper.mapRow(rs);
        else
            throw new CustomerNotFoundException();
    }

    public static void addCustomer(String id, String name, String family, Integer deposit) throws SQLException, CustomerAlreadyExistsException {
        ResultSet rs = null;
        PreparedStatement ps = DBConnection.prepareStatement("INSERT INTO customers (c_id, name, family, deposit) VALUES (?,?,?,?)");
        ps.setString(1, id);
        ps.setString(2, name);
        ps.setString(3, family);
        ps.setInt(4, deposit);
        try {
            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new CustomerAlreadyExistsException();
        }
    }
}