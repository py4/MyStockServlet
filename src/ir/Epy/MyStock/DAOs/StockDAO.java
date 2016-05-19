package ir.Epy.MyStock.DAOs;

import ir.Epy.MyStock.DBConnection;
import ir.Epy.MyStock.Mappers.StockMapper;
import ir.Epy.MyStock.exceptions.StockAlreadyExistsException;
import ir.Epy.MyStock.exceptions.StockNotFoundException;
import ir.Epy.MyStock.models.Stock;

import java.sql.*;

public class StockDAO {

    public static void deleteStocks() throws SQLException {
        Statement st = DBConnection.createStatement();
        st.executeQuery("DELETE FROM stock");
    }

    public static Stock findStock(String symbol) throws SQLException, StockNotFoundException {
        ResultSet rs = null;
        PreparedStatement ps = DBConnection.prepareStatement("SELECT * FROM stocks s WHERE s.symbol = ?");
        ps.setString(1, symbol);
        rs = ps.executeQuery();
        if (rs.next())
            return StockMapper.mapRow(rs);
        else
            throw new StockNotFoundException();
    }

    public static void addStock(String symbol) throws SQLException, StockAlreadyExistsException {
        ResultSet rs = null;
        PreparedStatement ps = DBConnection.prepareStatement("INSERT INTO stocks (symbol) VALUES (?)");
        ps.setString(1, symbol);
        try {
            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new StockAlreadyExistsException();
        }
    }
}