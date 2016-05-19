package ir.Epy.MyStock.Mappers;

import ir.Epy.MyStock.models.StockShare;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockShareMapper {

//    private static StockShareMapper ourInstance = new StockShareMapper();
//    private StockShareMapper() {}
//
//    public StockShareMapper I() {
//        return ourInstance;
//    }

    public static StockShare mapRow(ResultSet rs){
        try {
            return new StockShare(rs.getString("c_id"), rs.getString("symbol"), rs.getInt("quantity"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}