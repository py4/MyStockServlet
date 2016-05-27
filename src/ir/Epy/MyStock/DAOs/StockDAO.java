package ir.Epy.MyStock.DAOs;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.Mappers.CustomerMapper;
import ir.Epy.MyStock.Mappers.StockMapper;
import ir.Epy.MyStock.exceptions.CustomerAlreadyExistsException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.StockAlreadyExistsException;
import ir.Epy.MyStock.exceptions.StockNotFoundException;
import ir.Epy.MyStock.models.Customer;
import ir.Epy.MyStock.models.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;

public class StockDAO extends DAO {

    private static StockDAO ourInstance = new StockDAO();
    private final int default_status = Constants.PendingStatus;//pending

    private StockDAO() {
        TABLE_NAME = "stocks";
        db_fields = new ArrayList<String> (Arrays.asList("SYMBOL", "STATUS", "OWNER_ID"));
        db_pks = new ArrayList<String>(Arrays.asList("SYMBOL"));
    }


    public static StockDAO I() {
        return ourInstance;
    }

    public Stock find(String symbol) throws StockNotFoundException, SQLException {

        ResultSet rs = super.find(symbol);
        if (rs.next())
            return StockMapper.mapRow(rs);
        else
            throw new StockNotFoundException();
    }

    public void create(String symbol, String owner_id) throws SQLException, StockAlreadyExistsException {
        try {
            super.create(symbol, default_status, owner_id);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new StockAlreadyExistsException();
        }
    }

    public ArrayList<String> get_all(int status) throws SQLException {
        ResultSet rs = super.all("WHERE STATUS="+status);
        ArrayList<String> cs = new ArrayList<>();
        while(rs.next())
            cs.add(StockMapper.mapRow(rs).get_symbol());
        return cs;
    }


}