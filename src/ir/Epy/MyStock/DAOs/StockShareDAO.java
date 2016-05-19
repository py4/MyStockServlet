package ir.Epy.MyStock.DAOs;

import ir.Epy.MyStock.Mappers.StockShareMapper;
import ir.Epy.MyStock.exceptions.CustomerAlreadyExistsException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.models.StockShare;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;

public class StockShareDAO extends DAO {

    private static StockShareDAO ourInstance = new StockShareDAO();

    private StockShareDAO() {
        TABLE_NAME = "stock_shares";
        db_fields = new ArrayList<String> (Arrays.asList("CUSTOMER_ID", "STOCK_SYMBOL", "QUANTITY"));
        db_pks = new ArrayList<String>(Arrays.asList("CUSTOMER_ID", "STOCK_SYMBOL"));
    }


    public static StockShareDAO I() {
        return ourInstance;
    }

    public StockShare find(String customer_id, String stock_symbol) throws SQLException {

        ResultSet rs = super.find(customer_id, stock_symbol);
        if (rs.next())
            return StockShareMapper.mapRow(rs);
        else return null;
    }

    public void create(String customer_id, String stock_symbol, Integer quantity) throws SQLException {
            super.create(customer_id, stock_symbol, quantity);
    }


}