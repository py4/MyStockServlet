package ir.Epy.MyStock.DAOs;

import ir.Epy.MyStock.DBConnection;
import ir.Epy.MyStock.Mappers.StockShareMapper;
import ir.Epy.MyStock.exceptions.CustomerAlreadyExistsException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.models.StockShare;

import java.sql.*;

public class StockShareDAO extends DAO{


//    static {
//        TABLE_NAME = "stock_shares";
//    }


    public static void deleteStockShares() throws SQLException {
        Statement st = DBConnection.createStatement();
        st.executeQuery("DELETE FROM customer_stock_shares");
    }
    public static StockShare findStockShare(String customer_id, String stock_symbol) throws SQLException {
        ResultSet rs = null;
        PreparedStatement ps = DBConnection.prepareStatement("SELECT * FROM ? c WHERE c.c_id = ? AND c.symbol = ?");
//        ps.setString(1, TABLE_NAME);
        ps.setString(2, customer_id);
        ps.setString(3, stock_symbol);
        rs = ps.executeQuery();
        if (rs.next())
            return StockShareMapper.mapRow(rs);
        return null;
    }

    public static void addStockShare(String customer_id, String stock_symbol, Integer quantity) throws SQLException{
        ResultSet rs = null;
        PreparedStatement ps = DBConnection.prepareStatement("INSERT INTO ? (c_id, name, family, deposit) VALUES (?,?,?,?)");
//        ps.setString(1, TABLE_NAME);
        ps.setString(2, customer_id);
        ps.setString(3, stock_symbol);
        ps.setInt(4, quantity);
        ps.execute();
    }
}