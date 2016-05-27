package ir.Epy.MyStock.Mappers;

import ir.Epy.MyStock.exceptions.InvalidCreditValueRequest;
import ir.Epy.MyStock.models.CreditRequest;
import ir.Epy.MyStock.models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditRequestMapper {
    public static CreditRequest mapRow(ResultSet rs)  {
        try {
            return new CreditRequest(rs.getInt("ID"), rs.getString("CUSTOMER_ID"), rs.getInt("AMOUNT"), rs.getInt("STATUS"), rs.getBoolean("IS_DEPOSIT"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}