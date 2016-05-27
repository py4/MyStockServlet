package ir.Epy.MyStock.DAOs;

import ir.Epy.MyStock.Mappers.StockMapper;
import ir.Epy.MyStock.exceptions.StockAlreadyExistsException;
import ir.Epy.MyStock.exceptions.StockNotFoundException;
import ir.Epy.MyStock.models.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;

public class ConfigDAO extends DAO {

    private static ConfigDAO ourInstance = new ConfigDAO();
    private int default_status = 0;//pending

    private ConfigDAO() {
        TABLE_NAME = "stocks";
        db_fields = new ArrayList<String> (Arrays.asList("limit"));
        db_pks = new ArrayList<String>(Arrays.asList("limit"));
    }


    public static ConfigDAO I() {
        return ourInstance;
    }

    public int get_limit() throws SQLException {
        ResultSet rs = super.all();
        if(rs.next())
            return rs.getInt("limit");
        else return Integer.MAX_VALUE;
    }
    public void set_limit() throws SQLException {
        /*(ResultSet rs = super.find();
        if(rs.next())
            return rs.getInt("limit");
        else return Integer.MAX_VALUE;*/
    }

}