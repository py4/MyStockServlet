package ir.Epy.MyStock.Mappers;

import ir.Epy.MyStock.models.GTCRequest;
import ir.Epy.MyStock.models.StockRequest;
import ir.Epy.MyStock.models.StockShare;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GTCRequestMapper {

    public static GTCRequest mapRow(ResultSet rs){
        try {
            return new GTCRequest(rs.getInt("ID"), rs.getString("CUSTOMER_ID"), rs.getString("STOCK_SYMBOL"), rs.getInt("BASE_PRICE"), rs.getInt("QUANTITY"), "GTC", rs.getBoolean("IS_BUY"), rs.getInt("STATUS"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}