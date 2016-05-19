package ir.Epy.MyStock.Mappers;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper {
    public static Customer mapRow(ResultSet rs){
        try {
            return new Customer(rs.getString("ID"), rs.getString("NAME"), rs.getString("FAMILY"), rs.getInt("DEPOSIT"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}