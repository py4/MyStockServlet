package ir.Epy.MyStock.Mappers;

import ir.Epy.MyStock.models.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockMapper  {
    public static Stock mapRow(ResultSet rs) {
        try {
            return new Stock(rs.getString("SYMBOL"), rs.getInt("STATUS"), rs.getString("OWNER_ID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}