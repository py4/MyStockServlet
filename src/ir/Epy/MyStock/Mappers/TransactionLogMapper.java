package ir.Epy.MyStock.Mappers;

import ir.Epy.MyStock.models.Customer;
import ir.Epy.MyStock.models.StockTransactionLog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionLogMapper {

    public static StockTransactionLog mapRow(ResultSet rs){
        try {
            return new StockTransactionLog(rs.getInt("ID"),rs.getString("BUYER"), rs.getString("SELLER"), rs.getString("SYMBOL"), rs.getString("TRADE_TYPE"), rs.getInt("QUANTITY"), rs.getInt("BUYER_REMAINED_MONEY"), rs.getInt("SELLER_CURR_MONEY"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}