package ir.Epy.MyStock.Mappers;

import ir.Epy.MyStock.models.StockShare;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockShareMapper {
    public static StockShare mapRow(ResultSet rs){
        try {
            return new StockShare(rs.getString("c_id"), rs.getString("symbol"), rs.getInt("quantity"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}